package kr.co.knowledgerally.api.lecture.web;

import kr.co.knowledgerally.api.annotation.WithMockKnowllyUser;
import kr.co.knowledgerally.api.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


class CategoryControllerTest extends AbstractControllerTest {
    private static final String CATEGORY_URL = "/api/category";

    @WithMockKnowllyUser
    @Test
    public void 카테고리_목록_조회_테스트() throws Exception {
        mockMvc.perform(
                get(CATEGORY_URL)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.[0]").value("PM"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.[1]").value("DESIGN"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.[2]").value("DEVELOP"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.[3]").value("MARKETING"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.[4]").value("LANGUAGE"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.[5]").value("ETC"));
    }

}
