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
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].id").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].lectures.size()").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].lectureImages.size()").value(1))

                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].tags.size()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].lectureImages.size()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].lectures[0].id").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].lectures[0].state").value("ON_BOARD"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].lectures[0].matched").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].lectures[0].matchedUser").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].lectures[0].forms[0].id").value(6))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].lectures[0].forms[0].user.userImgUrl").value("http://test4.img.url"))

                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].lectures[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].lectures[1].state").value("ON_GOING"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].lectures[1].matched").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].lectures[1].matchedUser").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].lectures[1].forms[0].id").value(2))

                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].lectures[2].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].lectures[2].state").value("DONE"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].lectures[2].forms[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].lectures[2].matched").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].lectures[2].matchedUser").isNotEmpty());
    }

    @WithMockKnowllyUser
    @Test
    void 운영_클래스_매칭중_조회_테스트() throws Exception {
        mockMvc.perform(
                        get(API_COACH_LECTURE_ME)
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("state", "ON_BOARD")
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].tags.size()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].lectureImages.size()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].lectures[0].id").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].lectures[0].state").value("ON_BOARD"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].lectures[0].matched").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].lectures[0].matchedUser").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].lectures[0].forms[0].id").value(6))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].lectures[0].forms[0].user.userImgUrl").value("http://test4.img.url"));
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