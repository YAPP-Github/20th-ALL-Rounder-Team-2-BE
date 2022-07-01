package kr.co.knowledgerally.api.review.service;

import kr.co.knowledgerally.api.coach.component.ReviewMapper;
import kr.co.knowledgerally.api.coach.dto.ReviewDto;
import kr.co.knowledgerally.core.coach.entity.Coach;
import kr.co.knowledgerally.core.coach.entity.Review;
import kr.co.knowledgerally.core.coach.service.CoachService;
import kr.co.knowledgerally.core.coach.service.ReviewService;
import kr.co.knowledgerally.core.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class ReviewCoachService {
    private final ReviewService reviewService;
    private final CoachService coachService;
    private final ReviewMapper reviewMapper;

    /**
     * 코치에게 리뷰를 작성한다.
     * @param revieweeId 리뷰 대상자 코치 ID
     * @param reviewDto 리뷰 작성 객체
     * @param loggedInUser 로그인된 사용자
     * @return 작성된 리뷰 객체
     */
    public ReviewDto.ReadOnly writeReview(Long revieweeId, @Valid ReviewDto reviewDto, User loggedInUser) {
        Coach coach = coachService.findById(revieweeId);
        Review review = reviewMapper.toEntity(reviewDto);
        review.setReviewee(coach);
        review.setWriter(loggedInUser);
        return reviewMapper.toDto(reviewService.saveReview(review));
    }
}
