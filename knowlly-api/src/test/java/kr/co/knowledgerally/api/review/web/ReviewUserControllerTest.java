package kr.co.knowledgerally.api.review.web;

import kr.co.knowledgerally.api.annotation.WithMockKnowllyUser;
import kr.co.knowledgerally.api.web.AbstractControllerTest;
import kr.co.knowledgerally.core.core.message.ErrorMessage;
import kr.co.knowledgerally.core.core.message.ResponseMessage;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ReviewUserControllerTest extends AbstractControllerTest {
    private static final String REVIEW_USER_URL = "/api/review/user/";
    private static final String REVIEW_USER_ME_URL = "/api/review/user/me";

    @WithMockKnowllyUser
    @Test
    public void 사용자_리뷰_조회_테스트() throws Exception {
        mockMvc.perform(
                        get(REVIEW_USER_URL + 4)
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("include_private", "false")
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(ResponseMessage.OK))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].writer.username").value("테스트1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].reviewee.id").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].lectureName").value("클래스1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].content").value("테스트3은 좋은 코치입니다!"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].public").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].writtenDate").value("2022-06-13"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPage").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(1))
                .andDo(print());
    }

    @WithMockKnowllyUser
    @Test
    public void 사용자_리뷰_전체_조회_테스트() throws Exception {
        mockMvc.perform(
                        get(REVIEW_USER_URL + 4)
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("include_private", "true")
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(ResponseMessage.OK))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].writer.username").value("테스트4"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].reviewee.id").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].content").value("테스트3 코치는 좀 별로였습니다"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].public").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].writtenDate").value("2022-06-13"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].writer.username").value("테스트1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].reviewee.id").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].content").value("테스트3은 좋은 코치입니다!"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].public").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].writtenDate").value("2022-06-13"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPage").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(2))
                .andDo(print());
    }

    @WithMockKnowllyUser
    @Test
    public void 사용자_리뷰_조회_실패_테스트() throws Exception {
        mockMvc.perform(
                        get(REVIEW_USER_URL + 2)
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("include_private", "false")
                ).andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(ErrorMessage.USER_NOT_COACH))
                .andDo(print());
    }

    @WithMockKnowllyUser
    @Test
    public void 로그인된_사용자_리뷰_조회_테스트() throws Exception {
        mockMvc.perform(
                        get(REVIEW_USER_ME_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("include_private", "false")
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(ResponseMessage.OK))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].writer.username").value("테스트3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].reviewee.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].content").value("테스트1 코치가 최고에요~"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].public").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].writtenDate").value("2022-06-13"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].writer.username").value("테스트2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].reviewee.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].content").value("테스트1 코치는 친절했어요~"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].public").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].writtenDate").value("2022-06-13"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPage").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(2))
                .andDo(print());
    }

    @WithMockKnowllyUser
    @Test
    public void 로그인된_사용자_리뷰_전체_조회_테스트() throws Exception {
        mockMvc.perform(
                        get(REVIEW_USER_ME_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("include_private", "true")
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(ResponseMessage.OK))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].writer.username").value("테스트3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].reviewee.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].content").value("테스트1 코치가 최고에요~"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].public").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].writtenDate").value("2022-06-13"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].writer.username").value("테스트2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].reviewee.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].content").value("테스트1 코치는 친절했어요~"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].public").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].writtenDate").value("2022-06-13"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPage").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(2))
                .andDo(print());
    }
}