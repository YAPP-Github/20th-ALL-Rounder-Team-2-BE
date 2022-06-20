package kr.co.knowledgerally.core.user.repository;

import kr.co.knowledgerally.core.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * 사용자를 식별자와 활성화 여부로 존재하는지 여부 검색
     * @param identifier 사용자 식별자
     * @param isActive 활성화 여부
     * @return 사용자가 존재하면 true, 그렇지 않다면 false
     */
    boolean existsByIdentifierAndIsActive(String identifier, boolean isActive);

    /**
     * 사용자를 식별자와 활성화 여부로 검색
     * @param identifier 사용자 식별자
     * @param isActive 활성화 여부
     * @return 사용자 Optional
     */
    Optional<User> findByIdentifierAndIsActive(String identifier, boolean isActive);
}