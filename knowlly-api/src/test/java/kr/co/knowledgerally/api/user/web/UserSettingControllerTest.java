package kr.co.knowledgerally.api.user.web;

import kr.co.knowledgerally.api.annotation.WithMockKnowllyUser;
import kr.co.knowledgerally.api.web.AbstractControllerTest;
import kr.co.knowledgerally.core.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserSettingControllerTest  extends AbstractControllerTest {
    private static final String USER_SETTING_PUSH_URL = "/api/user/setting/push";

    @Autowired
    UserRepository userRepository;

    @WithMockKnowllyUser
    @Test
    public void 사용자_푸시알림설정_변경_테스트() throws Exception {
        final String newMemberJsonString =
                "{" +
                    "\"pushActive\": false" +
                "}";

        mockMvc.perform(
                        patch(USER_SETTING_PUSH_URL)
                                .content(newMemberJsonString)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.pushActive").value(false))
                .andDo(print());

        assertFalse(userRepository.findById(1L).orElseThrow().isPushActive());
    }

    @WithMockKnowllyUser(userId = 3)
    @Test
    public void 사용자_푸시알림설정_변경_테스트2() throws Exception {
        final String newMemberJsonString =
                "{" +
                        "\"pushActive\": true" +
                        "}";

        mockMvc.perform(
                        patch(USER_SETTING_PUSH_URL)
                                .content(newMemberJsonString)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.pushActive").value(true))
                .andDo(print());

        assertTrue(userRepository.findById(1L).orElseThrow().isPushActive());
    }
}