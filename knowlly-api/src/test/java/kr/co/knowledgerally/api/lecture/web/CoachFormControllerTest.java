package kr.co.knowledgerally.api.lecture.web;

import kr.co.knowledgerally.api.annotation.WithMockKnowllyUser;
import kr.co.knowledgerally.api.web.AbstractControllerTest;
import kr.co.knowledgerally.core.lecture.entity.Form;
import kr.co.knowledgerally.core.lecture.repository.FormRepository;
import kr.co.knowledgerally.core.lecture.service.FormService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CoachFormControllerTest extends AbstractControllerTest {
    private static final String COACH_FORM_FORMAT_STATE = "/api/coach/form/%d/state";

    @Autowired
    FormService formService;

    @WithMockKnowllyUser(userId = 3)
    @Test
    void 신청서_상태_변경_수락_테스트() throws Exception {
        final String json = "{" +
                "   \"state\": \"ACCEPT\"" +
                "}";

        assertEquals(Form.State.REQUEST, formService.findById(4L).getState());
        assertEquals(Form.State.REQUEST, formService.findById(7L).getState());

        mockMvc.perform(
                        patch(String.format(COACH_FORM_FORMAT_STATE, 4))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content").value("신청서를 받아주세요!"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.user.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.state").value("ACCEPT"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.expirationDate").value("2022-06-16T22:48:20"))
                .andDo(print());

        // 다른 신청서는 reject 처리되어야 함
        assertEquals(Form.State.ACCEPT, formService.findById(4L).getState());
        assertEquals(Form.State.REJECT, formService.findById(7L).getState());
    }

    @WithMockKnowllyUser(userId = 3)
    @Test
    void 신청서_상태_변경_거절_테스트() throws Exception {
        final String json = "{" +
                "   \"state\": \"REJECT\"" +
                "}";

        assertEquals(Form.State.REQUEST, formService.findById(4L).getState());
        assertEquals(Form.State.REQUEST, formService.findById(7L).getState());

        mockMvc.perform(
                        patch(String.format(COACH_FORM_FORMAT_STATE, 4))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content").value("신청서를 받아주세요!"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.user.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.state").value("REJECT"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.expirationDate").value("2022-06-16T22:48:20"))
                .andDo(print());

        // 다른 신청서는 reject 처리되어야 함
        assertEquals(Form.State.REJECT, formService.findById(4L).getState());
        assertEquals(Form.State.REQUEST, formService.findById(7L).getState());
    }

    @WithMockKnowllyUser(userId = 3)
    @Test
    void 신청서_상태_변경_유효하지_않은_상태_테스트() throws Exception {
        final String json = "{" +
                "   \"state\": \"REQUEST\"" +
                "}";

        mockMvc.perform(
                        patch(String.format(COACH_FORM_FORMAT_STATE, 4))
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
                patch(String.format(COACH_FORM_FORMAT_STATE, 4))
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
                patch(String.format(COACH_FORM_FORMAT_STATE, 4))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isBadRequest());
    }
}
