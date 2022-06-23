package kr.co.knowledgerally.core.coach.repository;

import kr.co.knowledgerally.core.coach.entity.Coach;
import kr.co.knowledgerally.core.user.entity.RefreshToken;
import kr.co.knowledgerally.core.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoachRepository extends JpaRepository<Coach, Long> {
    /**
     * 사용자로 코치 엔티티 찾기
     * @param user 사용자
     * @return 코치 엔티티 Optional
     */
    Optional<Coach> findByUser(User user);
}