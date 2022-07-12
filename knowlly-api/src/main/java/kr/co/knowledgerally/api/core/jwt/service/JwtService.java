package kr.co.knowledgerally.api.core.jwt.service;

import kr.co.knowledgerally.api.core.jwt.component.JwtCreator;
import kr.co.knowledgerally.api.core.jwt.component.JwtResolver;
import kr.co.knowledgerally.api.core.jwt.component.JwtValidator;
import kr.co.knowledgerally.api.core.jwt.dto.JwtToken;
import kr.co.knowledgerally.api.core.jwt.dto.ProviderToken;
import kr.co.knowledgerally.api.core.oauth2.service.OAuth2ServiceFactory;
import kr.co.knowledgerally.core.core.component.DateFactory;
import kr.co.knowledgerally.core.core.exception.UnauthorizedException;
import kr.co.knowledgerally.core.user.entity.RefreshToken;
import kr.co.knowledgerally.core.user.entity.User;
import kr.co.knowledgerally.core.user.repository.RefreshTokenRepository;
import kr.co.knowledgerally.core.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * JWT 관련 로직 서비스
 */
@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtCreator jwtCreator;
    private final JwtResolver jwtResolver;
    private final JwtValidator jwtValidator;
    private final OAuth2ServiceFactory oAuth2ServiceFactory;
    private final UserService userService;
    private final DateFactory dateFactory;
    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * JWT 토큰 발급
     * @param providerToken 프로바이더에서 제공하는 토큰 정보
     * @return jwt 토큰
     */
    @Transactional
    public JwtToken issue(ProviderToken providerToken){
        String identifier = oAuth2ServiceFactory.getInstance(providerToken.getProviderName()).getProfile(providerToken.getProviderAccessToken()).getIdentifier();
        User user = userService.findByIdentifier(identifier);
        RefreshToken refreshTokenFromDb = refreshTokenRepository.findByUser(user)
                .orElse(RefreshToken.builder().user(user).build());

        String accessToken = jwtCreator.createAccessToken(user);
        String refreshToken = jwtCreator.createRefreshToken(user);
        refreshTokenFromDb.setValue(refreshToken);
        refreshTokenRepository.saveAndFlush(refreshTokenFromDb);
        return new JwtToken(accessToken, refreshToken);
    }

    /**
     * JWT 리프레시 토큰 발급
     * @param knowllyRefreshToken 리프레시 JWT 토큰
     * @return jwt 토큰
     */
    @Transactional
    public JwtToken refresh(JwtToken.Refresh knowllyRefreshToken){
        if (isExpired(knowllyRefreshToken)) {
            throw new UnauthorizedException("Refresh Token이 만료되었습니다. 다시 로그인 해주세요.");
        }

        String identifier = jwtResolver.getUserIdentifier(knowllyRefreshToken.getKnowllyRefreshToken());
        User user = userService.findByIdentifier(identifier);
        RefreshToken refreshTokenFromDb = refreshTokenRepository.findByUser(user)
                .orElse(RefreshToken.builder().user(user).build());
        if (isTokenNotMatchedWithDb(refreshTokenFromDb, knowllyRefreshToken)) {
            throw new UnauthorizedException("Refresh Token이 DB값과 일치하지 않습니다. 다시 로그인 해주세요.");
        }

        String accessToken = jwtCreator.createAccessToken(user);
        String refreshToken = knowllyRefreshToken.getKnowllyRefreshToken();
        if (isRefreshable(knowllyRefreshToken)) {
            refreshToken = jwtCreator.createRefreshToken(user);
        }
        refreshTokenFromDb.setValue(refreshToken);
        refreshTokenRepository.saveAndFlush(refreshTokenFromDb);
        return new JwtToken(accessToken, refreshToken);
    }

    private boolean isExpired(JwtToken.Refresh knowllyRefreshToken) {
        return ! jwtValidator.validateToken(knowllyRefreshToken.getKnowllyRefreshToken());
    }

    private boolean isTokenNotMatchedWithDb(RefreshToken refreshTokenFromDb, JwtToken.Refresh knowllyRefreshToken) {
        return ! Objects.equals(refreshTokenFromDb.getValue(), knowllyRefreshToken.getKnowllyRefreshToken());
    }

    private boolean isRefreshable(JwtToken.Refresh knowllyRefreshToken) {
        // 유효기간이 1달 이내로 남았다면 리프레시 대상
        Date datePlusOneMonth = dateFactory.addFromNow(Calendar.MONTH, 1);
        return ! jwtValidator.validateTokenBefore(knowllyRefreshToken.getKnowllyRefreshToken(), datePlusOneMonth);
    }
}
