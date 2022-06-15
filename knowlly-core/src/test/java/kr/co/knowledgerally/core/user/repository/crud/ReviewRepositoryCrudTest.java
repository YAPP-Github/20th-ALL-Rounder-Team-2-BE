package kr.co.knowledgerally.core.user.repository.crud;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import kr.co.knowledgerally.core.core.repository.AbstractRepositoryCrudTest;
import kr.co.knowledgerally.core.user.entity.Review;
import kr.co.knowledgerally.core.user.repository.ReviewRepository;
import kr.co.knowledgerally.core.user.util.TestReviewEntityFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DatabaseSetup({
        "classpath:dbunit/entity/user.xml",
        "classpath:dbunit/entity/coach.xml",
        "classpath:dbunit/entity/review.xml",
})
class ReviewRepositoryCrudTest extends AbstractRepositoryCrudTest {

    @Autowired
    ReviewRepository reviewRepository;

    TestReviewEntityFactory testReviewEntityFactory = new TestReviewEntityFactory();

    @Override
    @Test
    protected void selectTest() {
        Review review = reviewRepository.findById(1L).orElseThrow();

        assertEquals(1L, review.getId());
        assertEquals(1L, review.getUser().getId());
        assertEquals(3L, review.getCoach().getId());
        assertEquals(4L, review.getCoach().getUser().getId());
        assertEquals("테스트3은 좋은 코치입니다!", review.getContent());
        assertTrue(review.isActive());
        assertEquals(LocalDateTime.of(2022, 6, 13, 22, 19, 14), review.getCreatedAt());
        assertEquals(LocalDateTime.of(2022, 6, 13, 22, 19, 15), review.getUpdatedAt());
    }

    @Override
    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/crud/review_insert_test.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    protected void insertTest() {
        Review review = testReviewEntityFactory.createEntity(5L, 1L, 2L);
        reviewRepository.saveAndFlush(review);
    }

    @Override
    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/crud/review_update_test.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    protected void updateTest() {
        Review review = reviewRepository.findById(1L).orElseThrow();
        review.setContent("내용변경");
        entityManager.flush();
    }

    @Override
    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/crud/review_delete_test.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    protected void deleteTest() {
        reviewRepository.deleteById(1L);
        entityManager.flush();
    }
}