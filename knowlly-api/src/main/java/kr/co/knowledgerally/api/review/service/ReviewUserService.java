package kr.co.knowledgerally.api.review.service;

import kr.co.knowledgerally.core.coach.entity.Coach;
import kr.co.knowledgerally.core.coach.entity.Review;
import kr.co.knowledgerally.core.coach.service.CoachService;
import kr.co.knowledgerally.core.coach.service.ReviewService;
import kr.co.knowledgerally.core.core.exception.BadRequestException;
import kr.co.knowledgerally.core.core.message.ErrorMessage;
import kr.co.knowledgerally.core.user.entity.User;
import kr.co.knowledgerally.core.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewUserService {
    private final UserService userService;
    private final ReviewService reviewService;
    private final CoachService coachService;

    /**
     * 후기 대상자 Id로 후기를 페이징 해서 찾는다.
     * @param revieweeUserId 후기 대상자 userId
     * @param includePrivate 비공개까지 포함할 지 여부
     * @param pageable 페이징 객체
     * @return 후기 페이징 객체
     */
    public Page<Review> findAllByRevieweeWithPageable(Long revieweeUserId, boolean includePrivate, Pageable pageable) {
        User user = userService.findById(revieweeUserId);
        Coach coach = coachService.findByUser(user);

        if (coach == null) {
            throw new BadRequestException(ErrorMessage.USER_NOT_COACH);
        }

        if (includePrivate) {
            return reviewService.findAllByRevieweeWithPageable(coach, pageable);
        }
        return reviewService.findAllByRevieweeAndIsPublicWithPageable(coach, true, pageable);
    }
}
