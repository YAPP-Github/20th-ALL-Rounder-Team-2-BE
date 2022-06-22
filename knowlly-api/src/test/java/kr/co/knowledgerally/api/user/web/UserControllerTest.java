package kr.co.knowledgerally.api.user.web;

import kr.co.knowledgerally.api.annotation.WithMockKnowllyUser;
import kr.co.knowledgerally.api.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends AbstractControllerTest {
    private static final String USER_ONBOARD_URL = "/api/user/onboard";

    @WithMockKnowllyUser
    @Test
    public void 사용자_온보딩_테스트() throws Exception {
        final String newMemberJsonString =
                "{" +
                    "\"user\" : {" +
                        "\"username\": \"테스트이름\"," +
                        "\"intro\": \"테스트 자기소개\"," +
                        "\"kakaoId\": \"test_kakao_id\"," +
                        "\"portfolio\": \"portfolio.io\"" +
                "   }," +
                    "\"userImage\" : {" +
                        "\"userImgUrl\": \"http://testImgUrl.com\"" +
                    "}" +
                "}";

        mockMvc.perform(
                        patch(USER_ONBOARD_URL)
                                .content(newMemberJsonString)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.user.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.user.username").value("테스트이름"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.user.intro").value("테스트 자기소개"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.user.kakaoId").value("test_kakao_id"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.user.portfolio").value("portfolio.io"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data.user.identifier").value("kakao_12341234"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.userImage.userImgUrl").value("http://testImgUrl.com"));
    }

}