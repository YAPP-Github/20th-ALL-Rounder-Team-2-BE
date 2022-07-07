package kr.co.knowledgerally.api.user.web;

import kr.co.knowledgerally.api.annotation.WithMockKnowllyUser;
import kr.co.knowledgerally.api.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class NotificationControllerTest extends AbstractControllerTest {
    private static final String NOTIFICATION_ME_URL = "/api/notification/me";

    @WithMockKnowllyUser
    @Test
    public void 알림_조회_테스트() throws Exception {
        mockMvc.perform(
                        get(NOTIFICATION_ME_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].receiver.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].sender.id").value(3L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].title").value("제목3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].content").value("알림 내용"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].notiType").value("PLAYER"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].read").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].sentDate").value("2022-06-13T22:17:28"))

                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].receiver.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].sender.id").value(3L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].title").value("제목2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].content").value("알림 내용2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].notiType").value("COACH"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].read").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].sentDate").value("2022-06-13T22:17:18"))

                .andExpect(MockMvcResultMatchers.jsonPath("$.data[2].receiver.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[2].sender.id").value(4L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[2].title").value("제목1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[2].content").value("알림 내용"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[2].notiType").value("PLAYER"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[2].read").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[2].sentDate").value("2022-06-13T22:17:08"))

                .andDo(print());
    }
}