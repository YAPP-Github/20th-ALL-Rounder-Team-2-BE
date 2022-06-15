package kr.co.knowledgerally.core.user.repository;

import kr.co.knowledgerally.core.user.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}