package kr.co.knowledgerally.api.user.web;

import kr.co.knowledgerally.api.annotation.WithMockKnowllyUser;
import kr.co.knowledgerally.api.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserMeControllerTest extends AbstractControllerTest {
    private static final String USER_ONBOARD_URL = "/api/user/onboard";
    private static final String USER_ME_URL = "/api/user/me";

    @WithMockKnowllyUser
    @Test
    public void 사용자_프로필_조회_테스트() throws Exception {
        mockMvc.perform(
                        get(USER_ME_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.user.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.user.username").value("테스트1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.user.intro").value("안녕하세요. 저는 테스트1이라고 합니다."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.user.kakaoId").value("kakao_test1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.user.portfolio").value("포트폴리오1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.user.identifier").value("identifier1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.userImage.userImgUrl").value("http://test1.img.url"))
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.portfolio").value("portfolio.io"));
    }

    @WithMockKnowllyUser
    @Test
    public void 사용자_프로필_수정_테스트() throws Exception {
        final String newMemberJsonString =
                "{" +
                        "\"username\": \"테스트이름\"," +
                        "\"intro\": \"테스트 자기소개\"," +
                        "\"kakaoId\": \"test_kakao_id\"," +
                        "\"portfolio\": \"portfolio.io\"" +
                        "}";

        mockMvc.perform(
                        patch(USER_ME_URL)
                                .content(newMemberJsonString)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.username").value("테스트이름"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.intro").value("테스트 자기소개"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.kakaoId").value("test_kakao_id"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.portfolio").value("portfolio.io"));
    }

    @WithMockKnowllyUser
    @Test
    public void 사용자_삭제_테스트() throws Exception {
        mockMvc.perform(
                        delete(USER_ME_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andDo(print());
    }
}