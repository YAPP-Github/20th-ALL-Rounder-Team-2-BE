package kr.co.knowledgerally.api.core.jwt.component;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 추상 Jwt 테스트 클래스
 * 토큰 유효기간을 2080 년과 같이 먼 미래로 설정한 이유는 JWT Claim 파싱 시 유효기간이 지나면 ExpiredJwtException을 발생시키기 때문
 */
@ExtendWith(MockitoExtension.class)
abstract class AbstractJwtTest {
    protected static final String TEST_SECRET_KEY = "dGVzdEtleQ==";

    /**
     * 2080-10-21 11:58:30 기준 + 24시간 생성
     */
    protected static final String TEST_ACCESS_TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJpZGVudGlmaWVyMSIsInVzZXJuYW1lI" +
            "joi7YWM7Iqk7Yq4MSIsImlhdCI6MzQ5NjcwNTExMCwiZXhwIjozNDk2NzkxNTEwfQ.TdqYBCkU2wU4a91d-GFoc2giREt1juhgBObYmF3" +
            "jXJO1oeTgCglehVt1FHCe8stTw8mNc-_ixeTk_JcOTg6lfw";

    /**
     * 2080-10-21 11:58:30 기준 + 2달 생성
     */
    protected static final String TEST_REFRESH_TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJpZGVudGlmaWVyMSIsInVzZXJuYW1lI" +
            "joi7YWM7Iqk7Yq4MSIsImlhdCI6MzQ5NjcwNTExMCwiZXhwIjozNTAxODg5MTEwfQ.y9xemg1_dV94r1Dbd9_C0sJetdmvGO_IkQTl713" +
            "8T52D6SYFk92Axgdc8uO0FlZOtPbzYooNW6SkkJ9I-BA9YA";
}