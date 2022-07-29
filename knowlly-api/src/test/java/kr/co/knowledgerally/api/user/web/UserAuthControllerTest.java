package kr.co.knowledgerally.api.user.web;

import kr.co.knowledgerally.api.annotation.WithMockKnowllyUser;
import kr.co.knowledgerally.api.core.jwt.dto.JwtToken;
import kr.co.knowledgerally.api.core.jwt.dto.ProviderToken;
import kr.co.knowledgerally.api.core.jwt.dto.TokenProvider;
import kr.co.knowledgerally.api.core.jwt.service.JwtService;
import kr.co.knowledgerally.api.core.oauth2.dto.OAuth2Profile;
import kr.co.knowledgerally.api.core.oauth2.service.OAuth2ServiceFactory;
import kr.co.knowledgerally.api.web.AbstractControllerTest;
import kr.co.knowledgerally.core.user.service.BallTransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserAuthControllerTest extends AbstractControllerTest {
    private static final String USER_EXISTS_URL = "/api/user/user-exists";
    private static final String USER_SIGNUP_URL = "/api/user/signup";
    private static final String USER_SIGNIN_URL = "/api/user/signin";
    private static final String USER_REFRESH_URL = "/api/user/refresh";

    private static final String TEST_PROVIDER_ACCESS_TOKEN = "testProviderToken";
    private static final String TEST_KNOWLLY_ACCESS_TOKEN = "testKnowllyAccessToken";
    private static final String TEST_KNOWLLY_REFRESH_TOKEN = "testKnowllyRefreshToken";
    private static final String TEST_EXIST_IDENTIFIER = "identifier1";
    private static final String TEST_NON_EXIST_IDENTIFIER = "non-exist-identifier";

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
    }

    @Test
    void 사용자_존재체크_테스트() throws Exception {
        when(oAuth2ServiceFactory.getInstance(eq(TokenProvider.KAKAO)))
                .thenReturn(providerAccessToken -> new OAuth2Profile(TEST_EXIST_IDENTIFIER, "테스트이름"));

        mockMvc.perform(
                        get(USER_EXISTS_URL)
                                .param("provider_token", TEST_PROVIDER_ACCESS_TOKEN)
                                .param("provider_name", "KAKAO")
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.exists").value(true))
                .andDo(print());
    }

    @Test
    void 사용자_존재체크_테스트2() throws Exception {
        when(oAuth2ServiceFactory.getInstance(eq(TokenProvider.KAKAO)))
                .thenReturn(providerAccessToken -> new OAuth2Profile(TEST_NON_EXIST_IDENTIFIER, "테스트이름"));

        mockMvc.perform(
                        get(USER_EXISTS_URL)
                                .param("provider_token", TEST_PROVIDER_ACCESS_TOKEN)
                                .param("provider_name", "KAKAO")
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.exists").value(false))
                .andDo(print());
    }

    @Test
    void 사용자_등록_테스트() throws Exception {
        when(oAuth2ServiceFactory.getInstance(eq(TokenProvider.KAKAO)))
                .thenReturn(providerAccessToken -> new OAuth2Profile(TEST_NON_EXIST_IDENTIFIER, "테스트이름"));

        String body = String.format("{\"providerName\":\"KAKAO\", " +
                "\"providerAccessToken\":\"%s\"}", TEST_PROVIDER_ACCESS_TOKEN);
        mockMvc.perform(
                        post(USER_SIGNUP_URL)
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.jwtToken.knowllyAccessToken").value(TEST_KNOWLLY_ACCESS_TOKEN))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.jwtToken.knowllyRefreshToken").value(TEST_KNOWLLY_REFRESH_TOKEN))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.user.username").value("테스트이름"))
                .andDo(print()).andReturn();
    }

    @Test
    void 사용자_등록_이미_존재_테스트() throws Exception {
        when(oAuth2ServiceFactory.getInstance(eq(TokenProvider.KAKAO)))
                .thenReturn(providerAccessToken -> new OAuth2Profile(TEST_EXIST_IDENTIFIER, "테스트이름"));

        String body = String.format("{\"providerName\":\"KAKAO\", " +
                "\"providerAccessToken\":\"%s\"}", TEST_PROVIDER_ACCESS_TOKEN);
        mockMvc.perform(
                        post(USER_SIGNUP_URL)
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("사용자가 이미 존재합니다."))
                .andDo(print());
    }

    @Test
    void 사용자_로그인_테스트() throws Exception {
        String body = String.format("{\"providerName\":\"KAKAO\", " +
                "\"providerAccessToken\":\"%s\"}", TEST_PROVIDER_ACCESS_TOKEN);
        mockMvc.perform(
                        post(USER_SIGNIN_URL)
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.knowllyAccessToken").value(TEST_KNOWLLY_ACCESS_TOKEN))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.knowllyRefreshToken").value(TEST_KNOWLLY_REFRESH_TOKEN))
                .andDo(print());
    }

    @Test
    @WithMockKnowllyUser
    void 사용자_리프레시_테스트() throws Exception {
        mockMvc.perform(
                        get(USER_REFRESH_URL)
                                .param("refresh_token", TEST_KNOWLLY_REFRESH_TOKEN)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.knowllyAccessToken").value(TEST_KNOWLLY_ACCESS_TOKEN))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.knowllyRefreshToken").value(TEST_KNOWLLY_REFRESH_TOKEN))
                .andDo(print());
    }
}