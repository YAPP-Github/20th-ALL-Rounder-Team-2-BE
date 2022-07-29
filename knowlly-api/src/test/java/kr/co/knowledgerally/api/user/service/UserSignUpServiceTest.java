package kr.co.knowledgerally.api.user.service;

import kr.co.knowledgerally.api.core.jwt.dto.JwtToken;
import kr.co.knowledgerally.api.core.jwt.dto.ProviderToken;
import kr.co.knowledgerally.api.core.jwt.dto.TokenProvider;
import kr.co.knowledgerally.api.core.jwt.service.JwtService;
import kr.co.knowledgerally.api.core.oauth2.dto.OAuth2Profile;
import kr.co.knowledgerally.api.core.oauth2.service.OAuth2ServiceFactory;
import kr.co.knowledgerally.api.user.dto.UserSignUpDto;
import kr.co.knowledgerally.core.user.service.BallTransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserSignUpServiceTest {
    private static final String TEST_PROVIDER_ACCESS_TOKEN = "testProviderToken";
    private static final String TEST_KNOWLLY_ACCESS_TOKEN = "testKnowllyAccessToken";
    private static final String TEST_KNOWLLY_REFRESH_TOKEN = "testKnowllyRefreshToken";
    private static final String TEST_NON_EXIST_IDENTIFIER = "non-exist-identifier";

    @Autowired
    UserSignUpService userSignUpService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private OAuth2ServiceFactory oAuth2ServiceFactory;

    @MockBean
    private BallTransactionService ballTransactionService;

    @BeforeEach
    void setUp() {
        ProviderToken provider = new ProviderToken(TokenProvider.KAKAO, TEST_PROVIDER_ACCESS_TOKEN);
        when(jwtService.issue(eq(provider))).thenReturn(
                new JwtToken(TEST_KNOWLLY_ACCESS_TOKEN, TEST_KNOWLLY_REFRESH_TOKEN));

        JwtToken.Refresh refresh = new JwtToken.Refresh(TEST_KNOWLLY_REFRESH_TOKEN);
        when(jwtService.refresh(eq(refresh))).thenReturn(
                new JwtToken(TEST_KNOWLLY_ACCESS_TOKEN, TEST_KNOWLLY_REFRESH_TOKEN));

        when(oAuth2ServiceFactory.getInstance(eq(TokenProvider.KAKAO)))
                .thenReturn(providerAccessToken -> new OAuth2Profile(TEST_NON_EXIST_IDENTIFIER, "테스트이름"));
    }

    @Test
    void 회원가입_테스트() throws Exception {
        ProviderToken providerToken = new ProviderToken(TokenProvider.KAKAO, TEST_PROVIDER_ACCESS_TOKEN);
        UserSignUpDto userSignUpDto = userSignUpService.signUp(providerToken);

        assertEquals(TEST_KNOWLLY_ACCESS_TOKEN, userSignUpDto.getJwtToken().getKnowllyAccessToken());
        assertEquals(TEST_KNOWLLY_REFRESH_TOKEN, userSignUpDto.getJwtToken().getKnowllyRefreshToken());

        Thread.sleep(1000); // 비동기 실행때문에 삽입

        verify(ballTransactionService, times(1)).makeBallTransaction(any());
    }

}