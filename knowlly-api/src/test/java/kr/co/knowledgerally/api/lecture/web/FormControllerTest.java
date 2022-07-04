package kr.co.knowledgerally.api.lecture.web;

import kr.co.knowledgerally.api.annotation.WithMockKnowllyUser;
import kr.co.knowledgerally.api.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FormControllerTest extends AbstractControllerTest {
    private static final String API_FORM = "/api/form/";
    private static final String API_FORM_ME = "/api/form/me";
    private static final String API_FORM_LECTURE = "/api/form/lecture/";

    @WithMockKnowllyUser
    @Test
    void 내_신청서_조회_테스트() throws Exception {
        mockMvc.perform(
                        get(API_FORM_ME)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].id").value(4))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].content").value("신청서를 받아주세요!"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].lecture.id").value(4))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].user.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].state").value("REQUEST"));
    }

    @WithMockKnowllyUser
    @Test
    void 내_신청서_조회_상태_필터_테스트() throws Exception {
        mockMvc.perform(
                        get(API_FORM_ME)
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("state", "REQUEST")
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].id").value(4))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].content").value("신청서를 받아주세요!"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].lecture.id").value(4))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].user.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].state").value("REQUEST"));
    }

    @WithMockKnowllyUser
    @Test
    void 내_신청서_조회_상태_필터_테스트2() throws Exception {
        mockMvc.perform(
                        get(API_FORM_ME)
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("state", "ACCEPT")
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());
    }

    @WithMockKnowllyUser
    @Test
    void 신청서_조회_테스트() throws Exception {
        mockMvc.perform(
                        get(API_FORM + 1)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content").value("신청서를 받아주세요!"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.lecture.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.user.id").value(4))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.state").value("ACCEPT"));
    }

    @WithMockKnowllyUser
    @Test
    void 신청서_등록_테스트() throws Exception {
        final String json = "{" +
                "   \"content\": \"테스트 내용\"" +
                "}";

        mockMvc.perform(
                        get(API_FORM_LECTURE + 1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content").value("테스트 내용"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.lecture.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.user.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.state").value("REQUEST"));
    }
}