package kr.co.knowledgerally.api.core.jwt.component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import kr.co.knowledgerally.core.core.component.DateFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;

/**
 * JWT 검증기
 */
@Component
@RequiredArgsConstructor
public class JwtValidator {

    @Value("${spring.jwt.secret}")
    private String secretKey;

    private final DateFactory dateFactory;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    /**
     * jwt 토큰을 검증합니다.
     * @param jwtToken jwt 토큰
     * @return 검증 유효시 참, 아닐 시 거짓
     */
    public boolean validateToken(String jwtToken){
        return validateTokenBefore(jwtToken, dateFactory.now());
    }

    /**
     * jwt 토큰을 검증합니다.
     * @param jwtToken jwt 토큰
     * @param date 검증 기준 날짜
     * @return 검증 유효시 참, 아닐 시 거짓
     */
    public boolean validateTokenBefore(String jwtToken, Date date){
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(date);
        } catch (Exception e) {
            return false;
        }
    }
}
