package kr.co.knowledgerally.api.review.web;

import kr.co.knowledgerally.api.annotation.WithMockKnowllyUser;
import kr.co.knowledgerally.api.web.AbstractControllerTest;
import kr.co.knowledgerally.core.core.message.ResponseMessage;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ReviewCoachControllerTest extends AbstractControllerTest {
    private static final String REVIEW_COACH_URL = "/api/review/coach/";

    @WithMockKnowllyUser
    @Test
    public void 사용자_리뷰_작성_테스트() throws Exception {
        final String json = "{" +
                "   \"content\": \"테스트4는 좋은 코치입니다!\"," +
                "   \"public\": true" +
                "}";

        mockMvc.perform(
                        post(REVIEW_COACH_URL + 4)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(ResponseMessage.OK))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.writer.username").value("테스트1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.reviewee.id").value(4))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content").value("테스트4는 좋은 코치입니다!"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.public").value(true))
                .andDo(print());
    }
}