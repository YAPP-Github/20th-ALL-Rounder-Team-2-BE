package kr.co.knowledgerally.api.core.oauth2.service;

import kr.co.knowledgerally.api.core.jwt.dto.TokenProvider;
import kr.co.knowledgerally.core.core.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * {@link OAuth2Service} 팩토리 클래스
 */
@Component
@RequiredArgsConstructor
public class OAuth2ServiceFactory {
    @Resource(name = "kakaoOAuth2Service")
    private final OAuth2Service kakaoOAuth2Service;

    /**
     * Provider 명에 따라서 {@link OAuth2Service} 구현 클래스를 리턴합니다.
     * @param provider OAuth2 프로바이더 enum, 현재는 KAKAO만 지원합니다.
     * @return {@link OAuth2Service} 구현 클래스
     * @throws BadRequestException provider명이 유효하지 않거나, 구현되어 있지 않으면 던집니다.
     */
    public OAuth2Service getInstance(TokenProvider provider) throws BadRequestException {
        if (provider == TokenProvider.KAKAO) {
            return kakaoOAuth2Service;
        }
        throw new BadRequestException("잘못된 provider 입니다.");
    }
}
