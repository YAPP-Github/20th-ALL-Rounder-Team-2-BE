package kr.co.knowledgerally.api.core.oauth2.service;

import kr.co.knowledgerally.api.core.oauth2.dto.OAuth2Profile;

/**
 * {@link OAuth2Profile}을 조회할 수 있는 서비스
 */
public interface OAuth2Service {
    /**
     * 프로바이더로부터 {@link OAuth2Profile}를 조회한다.
     * @param providerAccessToken 프로바이더 제공 토큰
     * @return 프로바이더에서 가져온 사용자 프로필
     */
    OAuth2Profile getProfile(String providerAccessToken);
}
