package kr.co.knowledgerally.api.review.service;

import kr.co.knowledgerally.api.review.component.ReviewMapper;
import kr.co.knowledgerally.api.review.dto.ReviewDto;
import kr.co.knowledgerally.core.coach.entity.Coach;
import kr.co.knowledgerally.core.coach.entity.Review;
import kr.co.knowledgerally.core.coach.service.ReviewService;
import kr.co.knowledgerally.core.core.exception.BadRequestException;
import kr.co.knowledgerally.core.lecture.entity.Lecture;
import kr.co.knowledgerally.core.lecture.service.LectureService;
import kr.co.knowledgerally.core.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class ReviewCoachService {
    private final LectureService lectureService;
    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;
    private final ApplicationEventPublisher eventPublisher;

    /**
     * 코치에게 리뷰를 작성한다.
     * @param lectureId 리뷰 대상 클래스 ID
     * @param reviewDto 리뷰 작성 객체
     * @param loggedInUser 로그인된 사용자
     * @return 작성된 리뷰 객체
     */
    @Transactional
    public ReviewDto.ReadOnly writeReview(Long lectureId, @Valid ReviewDto reviewDto, User loggedInUser) {
        Lecture lecture = lectureService.findById(lectureId);
        if (lecture.getState() != Lecture.State.DONE) {
            throw new BadRequestException("클래스가 완료된 상태가 아닙니다.");
        }

        lecture.setReviewWritten(true);
        Coach coach = lecture.getLectureInformation().getCoach();
        Review review = reviewMapper.toEntity(reviewDto);
        review.setReviewee(coach);
        review.setWriter(loggedInUser);
        review.setLectureName(lecture.getLectureInformation().getTopic());
        return reviewMapper.toDto(reviewService.saveReview(review));
    }
}
