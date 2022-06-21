package kr.co.knowledgerally.api.user.service;

import kr.co.knowledgerally.api.core.jwt.dto.JwtToken;
import kr.co.knowledgerally.api.core.jwt.dto.ProviderToken;
import kr.co.knowledgerally.api.core.jwt.service.JwtService;
import kr.co.knowledgerally.api.core.oauth2.dto.OAuth2Profile;
import kr.co.knowledgerally.api.core.oauth2.service.OAuth2ServiceFactory;
import kr.co.knowledgerally.api.user.component.UserMapper;
import kr.co.knowledgerally.api.user.dto.UserSignUpDto;
import kr.co.knowledgerally.core.core.exception.BadRequestException;
import kr.co.knowledgerally.core.user.entity.User;
import kr.co.knowledgerally.core.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

/**
 * 사용자 등록 서비스
 */
@Validated
@Service
@RequiredArgsConstructor
public class UserSignUpService {
    private final UserService userService;
    private final OAuth2ServiceFactory oAuth2ServiceFactory;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    /**
     * 사용자 등록
     * @param providerToken 프로바이더에서 제공하는 토큰 정보
     * @return jwt 토큰
     */
    @Transactional
    public UserSignUpDto signUp(@Valid ProviderToken providerToken) {
        OAuth2Profile oAuth2Profile = oAuth2ServiceFactory.getInstance(providerToken.getProviderName())
                .getProfile(providerToken.getProviderAccessToken());

        if (userService.isMemberExistByIdentifier(oAuth2Profile.getIdentifier())) {
            throw new BadRequestException("사용자가 이미 존재합니다.");
        }

        User savedUser = userService.saveUser(User.builder()
                        .username(oAuth2Profile.getName())
                        .identifier(oAuth2Profile.getIdentifier())
                .build());

        // TODO 후처리 추가 (볼 지급 등)

        return UserSignUpDto.builder()
                .jwtToken(jwtService.issue(providerToken))
                .user(userMapper.toDto(savedUser))
                .build();
    }
}
