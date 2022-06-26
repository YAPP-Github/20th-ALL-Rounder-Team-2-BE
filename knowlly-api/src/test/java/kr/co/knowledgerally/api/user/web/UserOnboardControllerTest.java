package kr.co.knowledgerally.api.user.web;

import kr.co.knowledgerally.api.annotation.WithMockKnowllyUser;
import kr.co.knowledgerally.api.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserOnboardControllerTest extends AbstractControllerTest {
    private static final String USER_ONBOARD_CHECK_URL = "/api/user/check-onboard";
    private static final String USER_ONBOARD_URL = "/api/user/onboard";

    @WithMockKnowllyUser
    @Test
    public void 사용자_온보딩체크_테스트() throws Exception {
        mockMvc.perform(
                        get(USER_ONBOARD_CHECK_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.onboard").value(false))
                .andDo(print());
    }

    @WithMockKnowllyUser
    @Test
    public void 사용자_온보딩_테스트() throws Exception {
        final String newMemberJsonString =
                "{" +
                    "\"username\": \"테스트이름\"," +
                    "\"intro\": \"테스트 자기소개\"," +
                    "\"kakaoId\": \"test_kakao_id\"," +
                    "\"portfolio\": \"portfolio.io\"" +
                "}";

        mockMvc.perform(
                        patch(USER_ONBOARD_URL)
                                .content(newMemberJsonString)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.username").value("테스트이름"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.intro").value("테스트 자기소개"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.kakaoId").value("test_kakao_id"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.portfolio").value("portfolio.io"))
                .andDo(print());
    }
}