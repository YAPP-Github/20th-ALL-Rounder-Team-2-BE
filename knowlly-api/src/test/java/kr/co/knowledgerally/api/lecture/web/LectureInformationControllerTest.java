package kr.co.knowledgerally.api.lecture.web;

import kr.co.knowledgerally.api.annotation.WithMockKnowllyUser;
import kr.co.knowledgerally.api.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LectureInformationControllerTest extends AbstractControllerTest {
    private static final String LECTUREINFORMATION_URL = "/api/lectureinfo";
    private  static final String LECTUREINFORMATION_SEARCH_URL = "/api/lectureinfo/search";

    @WithMockKnowllyUser
    @Test
    public void 클래스_info_목록_조회_테스트() throws Exception {
        mockMvc.perform(
                        get(LECTUREINFORMATION_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].topic").value("요리 클래스"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].introduce").value("맛있는 요리 만들기"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].price").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].coach.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].category.categoryName").value("기타"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].topic").value("그래픽 디자인"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].introduce").value("그래픽을 그래그래"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].price").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].coach.id").value("3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].category.categoryName").value("디자인"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[2].topic").value("자바 개발"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[2].introduce").value("자바를 자바라"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[2].price").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[2].coach.id").value("2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[2].category.categoryName").value("개발"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[3].topic").value("마케팅 수업"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[3].introduce").value("효과적인 마케팅에 대해 배웁니다"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[3].price").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[3].coach.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[3].category.categoryName").value("마케팅"));
    }

    @WithMockKnowllyUser
    @Test
    public void 클래스_info_카테고리로_목록_조회_테스트() throws Exception {
        String categoryId = "categoryId";

        mockMvc.perform(
                        get(LECTUREINFORMATION_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .queryParam(categoryId, String.valueOf(6))
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].topic").value("요리 클래스"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].introduce").value("맛있는 요리 만들기"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].price").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].coach.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].category.categoryName").value("기타"));
    }

    @WithMockKnowllyUser
    @Test
    public void 클래스_info_keyword로_검색_테스트() throws Exception {
        String keyword = "keyword";

        mockMvc.perform(
                get(LECTUREINFORMATION_SEARCH_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParam(keyword, "마케팅"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].topic").value("마케팅 수업"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].introduce").value("효과적인 마케팅에 대해 배웁니다"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].price").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].coach.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].category.categoryName").value("마케팅"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].lectureImageSet.size()").value(2)
        );
    }
}
