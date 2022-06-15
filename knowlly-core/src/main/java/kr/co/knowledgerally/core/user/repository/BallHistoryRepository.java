package kr.co.knowledgerally.core.user.repository;

import kr.co.knowledgerally.core.user.entity.BallHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BallHistoryRepository extends JpaRepository<BallHistory, Long> {
}