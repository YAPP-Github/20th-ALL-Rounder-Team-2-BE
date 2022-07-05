package kr.co.knowledgerally.api.lecture.web;

import kr.co.knowledgerally.api.annotation.WithMockKnowllyUser;
import kr.co.knowledgerally.api.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CoachLectureControllerTest extends AbstractControllerTest {
    private static final String API_COACH_LECTURE_ME = "/api/coach/lecture/me";

    @WithMockKnowllyUser
    @Test
    void 운영_클래스_조회_테스트() throws Exception {
        mockMvc.perform(
                        get(API_COACH_LECTURE_ME)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].lecture.id").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].lectureInformation.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].forms[0].id").value(6))

                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].lecture.id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].lectureInformation.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].forms[0].id").value(2))

                .andExpect(MockMvcResultMatchers.jsonPath("$.data[2].lecture.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[2].lectureInformation.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[2].forms[0].id").value(1));
    }

    @WithMockKnowllyUser
    @Test
    void 운영_클래스_완료된_조회_테스트() throws Exception {
        mockMvc.perform(
                        get(API_COACH_LECTURE_ME)
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("state", "ON_BOARD")
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].lecture.id").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].lecture.state").value("ON_BOARD"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].lectureInformation.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].forms[0].id").value(6));
    }

    @WithMockKnowllyUser(userId = 2)
    @Test
    void 운영_클래스_조회_사용자_코치아님_테스트() throws Exception {
        mockMvc.perform(
                        get(API_COACH_LECTURE_ME)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isBadRequest());
    }
}