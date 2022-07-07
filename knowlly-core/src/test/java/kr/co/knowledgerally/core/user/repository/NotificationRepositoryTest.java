package kr.co.knowledgerally.core.user.repository;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import kr.co.knowledgerally.core.annotation.KnowllyDataTest;
import kr.co.knowledgerally.core.user.entity.Notification;
import kr.co.knowledgerally.core.user.entity.User;
import kr.co.knowledgerally.core.user.util.TestUserEntityFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@KnowllyDataTest
@DatabaseSetup({
        "classpath:dbunit/entity/user.xml",
        "classpath:dbunit/entity/coach.xml",
        "classpath:dbunit/entity/notification.xml",
})
class NotificationRepositoryTest {
    @Autowired
    NotificationRepository notificationRepository;

    @Test
    void 사용자로_알림_목록_찾기_테스트() {
        User user = new TestUserEntityFactory().createEntity(1L);

        List<Notification> notifications = notificationRepository.findAllByUserOrCoachUser(user);

        assertEquals(3, notifications.size());
        assertEquals(3L, notifications.get(0).getId());
        assertEquals(1L, notifications.get(0).getUser().getId());
        assertEquals(2L, notifications.get(0).getCoach().getId());
        assertEquals("제목3", notifications.get(0).getTitle());
        assertEquals("알림 내용", notifications.get(0).getContent());
        assertEquals(Notification.NotiType.PLAYER, notifications.get(0).getNotiType());
        assertFalse(notifications.get(0).isRead());
        assertTrue(notifications.get(0).isActive());
        assertEquals(LocalDateTime.of(2022, 6, 13, 22, 17, 28), notifications.get(0).getCreatedAt());
        assertEquals(LocalDateTime.of(2022, 6, 13, 22, 17, 29), notifications.get(0).getUpdatedAt());

        assertEquals(2L, notifications.get(1).getId());
        assertEquals(3L, notifications.get(1).getUser().getId());
        assertEquals(1L, notifications.get(1).getCoach().getId());
        assertEquals("제목2", notifications.get(1).getTitle());
        assertEquals("알림 내용2", notifications.get(1).getContent());
        assertEquals(Notification.NotiType.COACH, notifications.get(1).getNotiType());
        assertTrue(notifications.get(1).isRead());
        assertTrue(notifications.get(1).isActive());
        assertEquals(LocalDateTime.of(2022, 6, 13, 22, 17, 18), notifications.get(1).getCreatedAt());
        assertEquals(LocalDateTime.of(2022, 6, 13, 22, 17, 19), notifications.get(1).getUpdatedAt());

        assertEquals(1L, notifications.get(2).getId());
        assertEquals(1L, notifications.get(2).getUser().getId());
        assertEquals(3L, notifications.get(2).getCoach().getId());
        assertEquals("제목1", notifications.get(2).getTitle());
        assertEquals("알림 내용", notifications.get(2).getContent());
        assertEquals(Notification.NotiType.PLAYER, notifications.get(2).getNotiType());
        assertFalse(notifications.get(2).isRead());
        assertTrue(notifications.get(2).isActive());
        assertEquals(LocalDateTime.of(2022, 6, 13, 22, 17, 8), notifications.get(2).getCreatedAt());
        assertEquals(LocalDateTime.of(2022, 6, 13, 22, 17, 9), notifications.get(2).getUpdatedAt());
    }
}