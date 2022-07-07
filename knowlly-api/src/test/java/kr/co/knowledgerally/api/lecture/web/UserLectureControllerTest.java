package kr.co.knowledgerally.api.lecture.web;

import kr.co.knowledgerally.api.annotation.WithMockKnowllyUser;
import kr.co.knowledgerally.api.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserLectureControllerTest extends AbstractControllerTest {
    private static final String API_USER_LECTURE_ME = "/api/user/lecture/me";

    @WithMockKnowllyUser
    @Test
    void 수강_클래스_조회_테스트() throws Exception {
        mockMvc.perform(
                        get(API_USER_LECTURE_ME)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].form.id").value(4))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].form.lecture.id").value(4))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].lectureInformation.id").value(2));
    }

    @WithMockKnowllyUser
    @Test
    void 수강_클래스_매칭중_조회_테스트() throws Exception {
        mockMvc.perform(
                        get(API_USER_LECTURE_ME)
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("state", "ON_BOARD")
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].form.id").value(4))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].form.lecture.id").value(4))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].form.lecture.state").value("ON_BOARD"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].lectureInformation.id").value(2));
    }
}