package kr.co.knowledgerally.api.coach.component;

import kr.co.knowledgerally.api.review.component.ReviewMapper;
import kr.co.knowledgerally.api.review.dto.ReviewDto;
import kr.co.knowledgerally.api.coach.util.TestReviewDtoFactory;
import kr.co.knowledgerally.core.coach.entity.Review;
import kr.co.knowledgerally.core.coach.util.TestReviewEntityFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ReviewMapperTest {
    @Autowired
    ReviewMapper reviewMapper;

    @Test
    void 엔티티에서_DTO변환_테스트() {
        Review review = new TestReviewEntityFactory().createEntity(1L, 1L, 2L);

        ReviewDto.ReadOnly reviewDto = reviewMapper.toDto(review);
        assertEquals(1L, reviewDto.getWriter().getId());
        assertEquals(2L, reviewDto.getReviewee().getId());
        assertTrue(reviewDto.isPublic());
        assertEquals("클래스1", reviewDto.getLectureName());
        assertEquals("테스트1 내용", reviewDto.getContent());
    }

    @Test
    void DTO에서_엔티티변환_테스트() {
        ReviewDto reviewDto = new TestReviewDtoFactory().createReadOnlyDto(1L, 1L, 2L);

        Review review = reviewMapper.toEntity(reviewDto);
        assertEquals("테스트1 내용", review.getContent());
        assertTrue(review.isPublic());
    }
}