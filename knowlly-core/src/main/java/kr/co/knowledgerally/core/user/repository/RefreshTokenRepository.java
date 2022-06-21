package kr.co.knowledgerally.core.user.repository;

import kr.co.knowledgerally.core.user.entity.BallHistory;
import kr.co.knowledgerally.core.user.entity.RefreshToken;
import kr.co.knowledgerally.core.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    /**
     * 사용자로 리프레시 토큰 찾기
     * @param user 사용자
     * @return 리프레시 토큰 Optional
     */
    Optional<RefreshToken> findByUser(User user);
}