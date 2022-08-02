package kr.co.knowledgerally.api.lecture.web;

import kr.co.knowledgerally.api.annotation.WithMockKnowllyUser;
import kr.co.knowledgerally.api.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class LectureControllerTest extends AbstractControllerTest {
    private static final String LECTURE_SCHEDULE_LECTUREINFO_URL = "/api/lecture-schedule/lectureinfo/";
    private static final String LECTURE_SCHEDULE_URL = "/api/lecture-schedule/";

    @WithMockKnowllyUser
    @Test
    public void 클래스_일정_등록_테스트() throws Exception {
        String json = "{" +
                "   \"schedules\": [" +
                "       {\"startAt\": \"2022-06-15T15:30\", \"endAt\": \"2022-06-15T16:30\"}," +
                "       {\"startAt\": \"2022-06-15T18:30\", \"endAt\": \"2022-06-15T19:30\"}" +
                "   ]" +
                "}";

        mockMvc.perform(
                post(LECTURE_SCHEDULE_LECTUREINFO_URL + 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].startAt").value("2022-06-15T15:30"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].endAt").value("2022-06-15T16:30"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].state").value("ON_BOARD"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].reviewWritten").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].startAt").value("2022-06-15T18:30"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].endAt").value("2022-06-15T19:30"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].state").value("ON_BOARD"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].reviewWritten").value(false))
                .andDo(print());
    }

    @WithMockKnowllyUser
    @Test
    public void 클래스_일정_등록_테스트_자기거_아니면_400() throws Exception {
        String json = "{" +
                "   \"schedules\": [" +
                "       {\"startAt\": \"2022-06-15T15:30\", \"endAt\": \"2022-06-15T16:30\"}," +
                "       {\"startAt\": \"2022-06-15T18:30\", \"endAt\": \"2022-06-15T19:30\"}" +
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
                "       {\"startAt\": \"2022-06-15T25:30\", \"endAt\": \"2022-06-15T26:30\"}," +
                "       {\"startAt\": \"2022-06-15T18:30\", \"endAt\": \"2022-06-15T19:30\"}" +
                "   ]" +
                "}";

        mockMvc.perform(
                        post(LECTURE_SCHEDULE_LECTUREINFO_URL + 1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                ).andExpect(status().isBadRequest())
                .andDo(print());
    }

    @WithMockKnowllyUser
    @Test
    public void 클래스_일정_등록_테스트_시작일이_종료일_넘어가면_400() throws Exception {
        String json = "{" +
                "   \"schedules\": [" +
                "       {\"startAt\": \"2022-06-15T18:30\", \"endAt\": \"2022-06-15T16:30\"}," +
                "       {\"startAt\": \"2022-06-15T11:30\", \"endAt\": \"2022-06-15T12:30\"}" +
                "   ]" +
                "}";

        mockMvc.perform(
                        post(LECTURE_SCHEDULE_LECTUREINFO_URL + 1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                ).andExpect(status().isBadRequest())
                .andDo(print());
    }

    @WithMockKnowllyUser
    @Test
    public void 클래스_일정_삭제_테스트() throws Exception {
        mockMvc.perform(
                        delete(LECTURE_SCHEDULE_URL + 3)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.forms[0].state").value("REJECT"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.forms[1].state").value("REJECT"));
    }

    @WithMockKnowllyUser
    @Test public void 클래스_일정_삭제_예정된_클래스_일정이면_400() throws Exception {
        mockMvc.perform(
                        delete(LECTURE_SCHEDULE_URL + 2)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isBadRequest())
                .andDo(print());
    }
}