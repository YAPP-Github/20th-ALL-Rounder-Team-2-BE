package kr.co.knowledgerally.api.core.oauth2.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.knowledgerally.api.core.oauth2.dto.OAuth2Profile;
import kr.co.knowledgerally.core.core.exception.KnowllyException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * 카카오 OAuth2로부터 조회할 수 있는 서비스
 */
@RequiredArgsConstructor
@Service("kakaoOAuth2Service")
public class KakaoOAuth2Service implements OAuth2Service {
    private static final String SOCIAL_KAKAO_PROFILE_URL = "https://kapi.kakao.com/v2/user/me";
    private static final String KAKAO_IDENTIFIER_PREFIX = "kakao_";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    /**
     * 프로바이더로부터 {@link OAuth2Profile}를 조회한다.
     * @param providerAccessToken 프로바이더 제공 토큰
     * @return 프로바이더에서 가져온 사용자 프로필
     */
    public OAuth2Profile getProfile(String providerAccessToken) {
        JsonNode profileJson = fetchProfileInfoFromProvider(providerAccessToken);
        return buildKakaoProfile(profileJson);
    }

    private JsonNode fetchProfileInfoFromProvider(String providerAccessToken) throws HttpClientErrorException {
        ResponseEntity<String> response = restTemplate.postForEntity(
                SOCIAL_KAKAO_PROFILE_URL,
                buildRequest(providerAccessToken),
                String.class
        );

        try {
            return objectMapper.readTree(response.getBody());
        } catch (JsonProcessingException e) {
            throw new KnowllyException("Provider 응답 호출 중 예외 발생", e);
        }
    }

    private HttpEntity<?> buildRequest(String providerAccessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBearerAuth(providerAccessToken);

        return new HttpEntity<>(null, headers);
    }

    private OAuth2Profile buildKakaoProfile(JsonNode jsonNode) {
        long identifier = jsonNode.get("id").asLong();
        String name = jsonNode.get("kakao_account").get("profile").get("nickname").asText();

        return new OAuth2Profile(KAKAO_IDENTIFIER_PREFIX + identifier, name);
    }
}
