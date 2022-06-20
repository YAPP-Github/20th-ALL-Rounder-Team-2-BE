package kr.co.knowledgerally.api.core.oauth2.service;

import kr.co.knowledgerally.api.core.oauth2.dto.OAuth2Profile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
class KakaoOAuth2ServiceTest {
    private static final String SOCIAL_KAKAO_PROFILE_URL = "https://kapi.kakao.com/v2/user/me";
    private static final String TEST_PROVIDER_ACCESS_TOKEN = "testProviderToken";
    private static final String TEST_KAKAO_ID = "123456789";
    private static final String TEST_KAKAO_NICKNAME = "홍길동";

    @Autowired
    @Resource(name = "kakaoOAuth2Service")
    OAuth2Service kakaoOAuth2Service;

    @MockBean
    RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        final String mockKakaoResponse = String.format(
                "{\n" +
                "    \"id\":%s,\n" +
                "    \"kakao_account\": { \n" +
                "        \"profile\": {\n" +
                "            \"nickname\": \"%s\"\n" +
                "        }\n" +
                "    }  \n" +
                "}", TEST_KAKAO_ID, TEST_KAKAO_NICKNAME);
        when(restTemplate.postForEntity(eq(SOCIAL_KAKAO_PROFILE_URL), any(), eq(String.class))).thenReturn(
                ResponseEntity.of(Optional.of(mockKakaoResponse)));
    }

    @Test
    void 카카오_프로필_조회_Mock_테스트() {
        OAuth2Profile oAuth2Profile = kakaoOAuth2Service.getProfile(TEST_PROVIDER_ACCESS_TOKEN);
        assertEquals("kakao_" + TEST_KAKAO_ID, oAuth2Profile.getIdentifier());
        assertEquals(TEST_KAKAO_NICKNAME, oAuth2Profile.getName());
    }
}