package kr.co.knowledgerally.core.user.repository;

import kr.co.knowledgerally.core.user.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}