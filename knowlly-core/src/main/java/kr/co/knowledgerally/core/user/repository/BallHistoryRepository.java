package kr.co.knowledgerally.core.user.repository;

import kr.co.knowledgerally.core.user.entity.BallHistory;
import kr.co.knowledgerally.core.user.entity.User;
import kr.co.knowledgerally.core.user.entity.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BallHistoryRepository extends JpaRepository<BallHistory, Long> {
    /**
     * 사용자로 볼 내역을 최신 순으로 찾는다.
     * @param user 사용자
     * @return 사용자 볼 내역 목록
     */
    List<BallHistory> findAllByUserOrderByCreatedAtDesc(User user);
}