package kr.co.knowledgerally.api.core.jwt.component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kr.co.knowledgerally.core.core.component.DateFactory;
import kr.co.knowledgerally.core.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;

/**
 * JWT 토큰 생성기
 */
@Component
@RequiredArgsConstructor
public class JwtCreator {
    @Value("${spring.jwt.access-token-valid-miliseconds}")
    private long ACCESS_TOKEN_VALID_MILISECONDS;

    @Value("${spring.jwt.refresh-token-valid-miliseconds}")
    private long REFRESH_TOKEN_VALID_MILISECONDS;

    @Value("${spring.jwt.secret}")
    private String secretKey;

    private final DateFactory dateFactory;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    /**
     * JWT Access Token 발급
     * @param user 사용자
     * @return Jwt 토큰
     */
    public String createAccessToken(User user) {
        return createToken(user, ACCESS_TOKEN_VALID_MILISECONDS);
    }

    /**
     * JWT Refresh Token 발급
     * @param user 사용자
     * @return Jwt 토큰
     */
    public String createRefreshToken(User user) {
        return createToken(user, REFRESH_TOKEN_VALID_MILISECONDS);
    }

    private String createToken(User user, long expirationMilisecond) {
        Claims claims = Jwts.claims().setSubject(user.getIdentifier()); // claim 임의로 설정
        claims.put("username", user.getUsername());
        Date now = dateFactory.now();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expirationMilisecond))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }
}
