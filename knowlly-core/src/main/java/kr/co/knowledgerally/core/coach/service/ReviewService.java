package kr.co.knowledgerally.core.coach.service;

import kr.co.knowledgerally.core.coach.entity.Coach;
import kr.co.knowledgerally.core.coach.entity.Review;
import kr.co.knowledgerally.core.coach.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    /**
     * 후기 대상자로 후기를 페이징 해서 찾는다.
     * @param reviewee 후기 대상자
     * @param pageable 페이징 요청 객체
     * @return 후기 객체 페이징
     */
    @Transactional(readOnly = true)
    public Page<Review> findAllByRevieweeWithPageable(Coach reviewee, Pageable pageable) {
        return reviewRepository.findAllByRevieweeAndIsActiveOrderByCreatedAtDesc(reviewee, true, pageable);
    }

    /**
     * 작성자로 후기를 페이징 해서 찾는다.
     * @param reviewee 후기 대상자
     * @param isPublic 공개 여부
     * @param pageable 페이징 요청 객체
     * @return 후기 객체 페이징
     */
    @Transactional(readOnly = true)
    public Page<Review> findAllByRevieweeAndIsPublicWithPageable(Coach reviewee, boolean isPublic, Pageable pageable) {
        return reviewRepository.findAllByRevieweeAndIsPublicAndIsActiveOrderByCreatedAtDesc(reviewee, isPublic, true, pageable);
    }

    /**
     * 후기를 저장합니다.
     * @param review 저장하고자 하는 후기 엔티티
     * @return 저장된 후기 엔티티
     */
    public Review saveReview(@Valid Review review) {
        return reviewRepository.saveAndFlush(review);
    }
}
