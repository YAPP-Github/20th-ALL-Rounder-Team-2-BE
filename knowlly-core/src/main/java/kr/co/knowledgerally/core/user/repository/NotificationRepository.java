package kr.co.knowledgerally.core.user.repository;

import kr.co.knowledgerally.core.user.entity.Notification;
import kr.co.knowledgerally.core.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    /**
     * 사용자로 수강 및 운영 클래스 관련 모든 알림을 가져옵니다.
     * @param user 사용자
     * @return 알림 목록
     */
    @Query("select n from Notification n " +
            "where (n.user = :user or n.coach.user = :user) and n.isActive = true " +
            "order by n.createdAt desc")
    List<Notification> findAllByUserOrCoachUser(User user);
}