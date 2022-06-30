package kr.co.knowledgerally.core.coach.repository;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import kr.co.knowledgerally.core.annotation.KnowllyDataTest;
import kr.co.knowledgerally.core.coach.entity.Review;
import kr.co.knowledgerally.core.coach.util.TestCoachEntityFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.*;

@KnowllyDataTest
@DatabaseSetup({
        "classpath:dbunit/entity/user.xml",
        "classpath:dbunit/entity/coach.xml",
        "classpath:dbunit/entity/review.xml",
})
class ReviewRepositoryTest {
    @Autowired
    ReviewRepository reviewRepository;

    TestCoachEntityFactory testCoachEntityFactory = new TestCoachEntityFactory();

    @Test
    void 후기_대상자로_후기_찾기_페이징_테스트() {
        Page<Review> reviews = reviewRepository.findAllByRevieweeAndIsActiveOrderByCreatedAtDesc(
                testCoachEntityFactory.createEntity(3L, 3L), true, PageRequest.of(0, 2));

        assertEquals(2, reviews.getNumberOfElements());
        assertEquals(2, reviews.getTotalElements());
        assertEquals(0, reviews.getPageable().getPageNumber());
        assertEquals(2, reviews.getPageable().getPageSize());
        assertEquals(1, reviews.getTotalPages());
        assertEquals(2, reviews.getContent().size());
        assertEquals("테스트3 코치는 좀 별로였습니다", reviews.getContent().get(0).getContent());
        assertEquals("테스트3은 좋은 코치입니다!", reviews.getContent().get(1).getContent());
    }

    @Test
    void 후기_대상자로_후기_찾기_공개여부_포함_페이징_테스트() {
        Page<Review> reviews = reviewRepository.findAllByRevieweeAndIsPublicAndIsActiveOrderByCreatedAtDesc(
                testCoachEntityFactory.createEntity(3L, 3L), false, true, PageRequest.of(0, 2));

        assertEquals(1, reviews.getNumberOfElements());
        assertEquals(1, reviews.getTotalElements());
        assertEquals(0, reviews.getPageable().getPageNumber());
        assertEquals(2, reviews.getPageable().getPageSize());
        assertEquals(1, reviews.getTotalPages());
        assertEquals(1, reviews.getContent().size());
        assertEquals("테스트3 코치는 좀 별로였습니다", reviews.getContent().get(0).getContent());
    }
}