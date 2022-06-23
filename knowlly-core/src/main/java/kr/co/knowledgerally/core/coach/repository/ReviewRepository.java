package kr.co.knowledgerally.core.coach.repository;

import kr.co.knowledgerally.core.coach.entity.Coach;
import kr.co.knowledgerally.core.coach.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    /**
     * 후기 페이징 조회
     * @param reviewee 리뷰 대상자
     * @param isActive 활성화 여부
     * @param pageable 페이징 요청 객체
     * @return 후기 객체 페이징
     */
    Page<Review> findAllByRevieweeAndIsActiveOrderByCreatedAtDesc(Coach reviewee, boolean isActive, Pageable pageable);

    /**
     * 후기 페이징 조회
     * @param reviewee 리뷰 대상자
     * @param isPublic 공개 여부
     * @param isActive 페이징 요청 객체
     * @param pageable 페이징 요청 객체
     * @return 후기 객체 페이징
     */
    Page<Review> findAllByRevieweeAndIsPublicAndIsActiveOrderByCreatedAtDesc(Coach reviewee, boolean isPublic, boolean isActive, Pageable pageable);
}