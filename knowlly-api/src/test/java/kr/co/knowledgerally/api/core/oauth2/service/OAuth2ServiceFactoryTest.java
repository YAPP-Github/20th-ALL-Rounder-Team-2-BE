package kr.co.knowledgerally.api.core.oauth2.service;

import kr.co.knowledgerally.api.core.jwt.dto.TokenProvider;
import kr.co.knowledgerally.core.core.exception.BadRequestException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OAuth2ServiceFactoryTest {
    @Autowired
    OAuth2ServiceFactory oAuth2ServiceFactory;

    @Test
    void 카카오_서비스_가져오기_테스트() {
        OAuth2Service oAuth2Service = oAuth2ServiceFactory.getInstance(TokenProvider.KAKAO);
        assertEquals(oAuth2Service.getClass(), KakaoOAuth2Service.class);
    }

    @Test
    void 서비스_가져오기_실패_테스트() {
        assertThrows(BadRequestException.class, () -> {
            oAuth2ServiceFactory.getInstance(null);
        });
    }
}