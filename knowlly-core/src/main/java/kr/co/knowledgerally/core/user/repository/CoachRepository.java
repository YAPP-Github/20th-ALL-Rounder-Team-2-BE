package kr.co.knowledgerally.core.user.repository;

import kr.co.knowledgerally.core.user.entity.Coach;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoachRepository extends JpaRepository<Coach, Long> {
}