package kr.co.knowledgerally.api.lecture.web;

import kr.co.knowledgerally.api.annotation.WithMockKnowllyUser;
import kr.co.knowledgerally.api.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class LectureControllerTest extends AbstractControllerTest {
    private static final String LECTURE_SCHEDULE_LECTUREINFO_URL = "/api/lecture-schedule/lectureinfo/";

    @WithMockKnowllyUser
    @Test
    public void 클래스_일정_등록_테스트() throws Exception {
        String json = "{" +
                "   \"schedules\": [" +
                "       {\"startAt\": \"2022-06-15 15:30:50\", \"endAt\": \"2022-06-15 16:30:50\"}," +
                "       {\"startAt\": \"2022-06-15 18:30:50\", \"endAt\": \"2022-06-15 19:30:50\"}" +
                "   ]" +
                "}";

        mockMvc.perform(
                post(LECTURE_SCHEDULE_LECTUREINFO_URL + 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].startAt").value("2022-06-15 15:30:50"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].endAt").value("2022-06-15 16:30:50"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].state").value("ON_BOARD"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].reviewWritten").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].startAt").value("2022-06-15 18:30:50"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].endAt").value("2022-06-15 19:30:50"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].state").value("ON_BOARD"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].reviewWritten").value(false))
                .andDo(print());
    }

    @WithMockKnowllyUser
    @Test
    public void 클래스_일정_등록_테스트_자기거_아니면_400() throws Exception {
        String json = "{" +
                "   \"schedules\": [" +
                "       {\"startAt\": \"2022-06-15 15:30:50\", \"endAt\": \"2022-06-15 16:30:50\"}," +
                "       {\"startAt\": \"2022-06-15 18:30:50\", \"endAt\": \"2022-06-15 19:30:50\"}" +
                "   ]" +
                "}";

        mockMvc.perform(
                        post(LECTURE_SCHEDULE_LECTUREINFO_URL + 2)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                ).andExpect(status().isBadRequest())
                .andDo(print());
    }

    @WithMockKnowllyUser
    @Test
    public void 클래스_일정_등록_테스트_형식_잘못되면_400() throws Exception {
        String json = "{" +
                "   \"schedules\": [" +
                "       {\"startAt\": \"2022-06-15 25:30:50\", \"endAt\": \"2022-06-15 26:30:50\"}," +
                "       {\"startAt\": \"2022-06-15 18:30:50\", \"endAt\": \"2022-06-15 19:30:50\"}" +
                "   ]" +
                "}";

        mockMvc.perform(
                        post(LECTURE_SCHEDULE_LECTUREINFO_URL + 1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                ).andExpect(status().isBadRequest())
                .andDo(print());
    }
}