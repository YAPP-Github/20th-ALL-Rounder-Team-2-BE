package kr.co.knowledgerally.api.core.web;

import kr.co.knowledgerally.api.core.jwt.dto.TokenProvider;
import kr.co.knowledgerally.api.core.oauth2.dto.OAuth2Profile;
import kr.co.knowledgerally.api.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TestControllerTest extends AbstractControllerTest {
    private static final String API_TEST_URL = "/api/test";
    private static final String API_AUTHENTICATED_TEST_URL = "/api/authenticated-test";

    private static final String X_AUTH_TOKEN = "X-AUTH-TOKEN";
    private static final String TEST_ACCESS_TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJpZGVudGlmaWVyMSIsInVzZXJuYW1lIjoi" +
            "7YWM7Iqk7Yq4MSIsImlhdCI6MzQ5NjcwNTExMCwiZXhwIjozNDk2NzkxNTEwfQ.GDahW4oCvj6eu_Nj6_-XAEE4gHmwJaoucqFmHtAZ9U" +
            "w76MzW3hxz3P8BA6itIuAj0Q6d3gl59iY2CUSot3x8-g";

    private static final String TEST_NOT_VALID_ACCESS_TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJub24tZXhpc3QtaWRlbnRpZm" +
            "llciIsInVzZXJuYW1lIjoi7YWM7Iqk7Yq4MSIsImlhdCI6MzQ5NjcwNTExMCwiZXhwIjozNDk2NzkxNTEwfQ.mE1-xQ5Yr1Txkqz9d1_2" +
            "wM5fC6KgFLQssALKe7c8lN7qivYHkMGXN21pFzJt_W8977QrpvGodMYK0Q1VtsQBgA";


    @Test
    void JWT_토큰_테스트() throws Exception {
        mockMvc.perform(
                        get(API_AUTHENTICATED_TEST_URL)
                                .header(X_AUTH_TOKEN, TEST_ACCESS_TOKEN)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andDo(print());
    }

    @Test
    void JWT_유효하지않은_토큰_테스트() throws Exception {
        mockMvc.perform(
                        get(API_AUTHENTICATED_TEST_URL)
                                .header(X_AUTH_TOKEN, TEST_NOT_VALID_ACCESS_TOKEN)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isUnauthorized())
                .andDo(print());
    }
}