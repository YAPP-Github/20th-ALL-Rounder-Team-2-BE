package kr.co.knowledgerally.api.user.web;

import kr.co.knowledgerally.api.annotation.WithMockKnowllyUser;
import kr.co.knowledgerally.api.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BallHistoryControllerTest extends AbstractControllerTest {
    private static final String BALLHISTORY_ME_URL = "/api/ballhistory/me";

    @WithMockKnowllyUser
    @Test
    public void 사용자_볼내역_조회_테스트() throws Exception {
        mockMvc.perform(
                        get(BALLHISTORY_ME_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].title").value("수강 클래스"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].content").value("영어 수업"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].count").value(-1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].historyDate").value("2022-06-13"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].title").value("운영 클래스"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].content").value("프랑스어 수업"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].count").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].historyDate").value("2022-06-13"))
                .andDo(print());
    }
}