package kr.co.knowledgerally.core.user.repository;

import kr.co.knowledgerally.core.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}