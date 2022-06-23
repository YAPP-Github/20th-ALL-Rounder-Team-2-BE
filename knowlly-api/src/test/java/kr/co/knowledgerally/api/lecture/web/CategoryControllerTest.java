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
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].categoryName").value("기획 / PM"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].categoryName").value("디자인"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[2].categoryName").value("개발"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[3].categoryName").value("마케팅"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[4].categoryName").value("외국어"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[5].categoryName").value("기타"));
    }

}
