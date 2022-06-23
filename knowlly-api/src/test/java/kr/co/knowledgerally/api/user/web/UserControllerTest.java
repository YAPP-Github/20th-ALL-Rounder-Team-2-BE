package kr.co.knowledgerally.api.user.web;

import kr.co.knowledgerally.api.annotation.WithMockKnowllyUser;
import kr.co.knowledgerally.api.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends AbstractControllerTest {
    private static final String USER_URL = "/api/user/";

    @WithMockKnowllyUser
    @Test
    public void 사용자_프로필_조회_테스트() throws Exception {
        mockMvc.perform(
                        get(USER_URL + 1)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.user.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.user.username").value("테스트1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.user.intro").value("안녕하세요. 저는 테스트1이라고 합니다."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.user.kakaoId").value("kakao_test1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.user.portfolio").value("포트폴리오1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.user.identifier").value("identifier1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.user.coach").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.userImage.userImgUrl").value("http://test1.img.url"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.coach.introduce").value("안녕하세요. 테스트1 코치입니다."))
                .andDo(print());
    }

    @WithMockKnowllyUser
    @Test
    public void 코치가_아닌_사용자_프로필_조회_테스트() throws Exception {
        mockMvc.perform(
                        get(USER_URL + 2)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.user.id").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.user.username").value("테스트2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.user.intro").value("안녕하세요. 저는 테스트2이라고 합니다."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.user.kakaoId").value("kakao_test2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.user.portfolio").value("포트폴리오2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.user.identifier").value("identifier2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.user.coach").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.userImage.userImgUrl").value("http://test2.img2.url"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.coach").isEmpty())
                .andDo(print());
    }
}