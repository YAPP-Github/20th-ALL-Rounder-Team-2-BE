package kr.co.knowledgerally.core.coach.repository;

import kr.co.knowledgerally.core.coach.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}