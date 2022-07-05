package kr.co.knowledgerally.api.lecture.web;

import kr.co.knowledgerally.api.annotation.WithMockKnowllyUser;
import kr.co.knowledgerally.api.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CoachFormControllerTest extends AbstractControllerTest {
    private static final String COACH_FORM = "/api/coach/form/";

    @WithMockKnowllyUser(userId = 3)
    @Test
    void 신청서_상태_변경_테스트() throws Exception {
        final String json = "{" +
                "   \"state\": \"ACCEPT\"" +
                "}";

        mockMvc.perform(
                        patch(COACH_FORM + 4)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content").value("신청서를 받아주세요!"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.lecture.id").value(4))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.user.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.state").value("ACCEPT"));
    }

    @WithMockKnowllyUser(userId = 3)
    @Test
    void 신청서_상태_변경_유효하지_않은_상태_테스트() throws Exception {
        final String json = "{" +
                "   \"state\": \"REQUEST\"" +
                "}";

        mockMvc.perform(
                        patch(COACH_FORM + 4)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                ).andExpect(status().isBadRequest());
    }

    @WithMockKnowllyUser
    @Test
    void 신청서_상태_변경_유효하지_않은_사용자_테스트() throws Exception {
        final String json = "{" +
                "   \"state\": \"ACCEPT\"" +
                "}";

        mockMvc.perform(
                patch(COACH_FORM + 4)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isBadRequest());
    }

    @WithMockKnowllyUser(userId = 2)
    @Test
    void 신청서_상태_변경_로그인_사용자_코치아님_테스트() throws Exception {
        final String json = "{" +
                "   \"state\": \"ACCEPT\"" +
                "}";

        mockMvc.perform(
                patch(COACH_FORM + 4)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isBadRequest());
    }
}