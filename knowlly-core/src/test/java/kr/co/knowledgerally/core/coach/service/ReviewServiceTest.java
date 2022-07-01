package kr.co.knowledgerally.core.coach.service;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import kr.co.knowledgerally.core.annotation.KnowllyDataTest;
import kr.co.knowledgerally.core.coach.entity.Review;
import kr.co.knowledgerally.core.coach.util.TestCoachEntityFactory;
import kr.co.knowledgerally.core.coach.util.TestReviewEntityFactory;
import kr.co.knowledgerally.core.user.entity.User;
import liquibase.pro.packaged.T;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.*;

@KnowllyDataTest
@Import(ReviewService.class)
@DatabaseSetup({
        "classpath:dbunit/entity/user.xml",
        "classpath:dbunit/entity/coach.xml",
        "classpath:dbunit/entity/review.xml",
})
class ReviewServiceTest {
    @Autowired
    ReviewService reviewService;

    TestCoachEntityFactory testCoachEntityFactory = new TestCoachEntityFactory();
    TestReviewEntityFactory testReviewEntityFactory = new TestReviewEntityFactory();

    @Test
    void 후기_대상자로_후기_찾기_페이징_테스트() {
        Page<Review> reviews = reviewService.findAllByRevieweeWithPageable(
                testCoachEntityFactory.createEntity(3L, 3L), PageRequest.of(0, 2));

        assertEquals(2, reviews.getNumberOfElements());
        assertEquals(2, reviews.getTotalElements());
        assertEquals(1, reviews.getTotalPages());
        assertEquals(2, reviews.getContent().size());
        assertEquals("테스트3 코치는 좀 별로였습니다", reviews.getContent().get(0).getContent());
        assertEquals("테스트3은 좋은 코치입니다!", reviews.getContent().get(1).getContent());
    }

    @Test
    void 후기_대상자로_후기_찾기_공개여부_포함_페이징_테스트() {
        Page<Review> reviews = reviewService.findAllByRevieweeAndIsPublicWithPageable(
                testCoachEntityFactory.createEntity(3L, 3L), false, PageRequest.of(0, 2));

        assertEquals(1, reviews.getNumberOfElements());
        assertEquals(1, reviews.getTotalElements());
        assertEquals(1, reviews.getTotalPages());
        assertEquals(1, reviews.getContent().size());
        assertEquals("테스트3 코치는 좀 별로였습니다", reviews.getContent().get(0).getContent());
    }

    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/crud/review_insert_test.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    void 후기_저장_테스트() {
        Review review = testReviewEntityFactory.createEntity(5L, 1L, 2L);
        reviewService.saveReview(review);
    }
}