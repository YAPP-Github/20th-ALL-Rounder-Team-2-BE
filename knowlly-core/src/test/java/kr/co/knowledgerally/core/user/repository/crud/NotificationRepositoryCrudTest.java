package kr.co.knowledgerally.core.user.repository.crud;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import kr.co.knowledgerally.core.core.repository.AbstractRepositoryCrudTest;
import kr.co.knowledgerally.core.user.entity.Notification;
import kr.co.knowledgerally.core.user.repository.NotificationRepository;
import kr.co.knowledgerally.core.user.util.TestNotificationEntityFactory;
import org.aspectj.weaver.ast.Not;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DatabaseSetup({
        "classpath:dbunit/entity/user.xml",
        "classpath:dbunit/entity/coach.xml",
        "classpath:dbunit/entity/notification.xml",
})
class NotificationRepositoryCrudTest extends AbstractRepositoryCrudTest {

    @Autowired
    NotificationRepository notificationRepository;

    TestNotificationEntityFactory testNotificationEntityFactory = new TestNotificationEntityFactory();

    @Override
    @Test
    protected void selectTest() {
        Notification notification = notificationRepository.findById(1L).orElseThrow();

        assertEquals(1L, notification.getId());
        assertEquals(1L, notification.getUser().getId());
        assertEquals(3L, notification.getCoach().getId());
        assertEquals("제목1", notification.getTitle());
        assertEquals("알림 내용", notification.getContent());
        assertEquals(Notification.NotiType.PLAYER, notification.getNotiType());
        assertFalse(notification.isRead());
        assertTrue(notification.isActive());
        assertEquals(LocalDateTime.of(2022, 6, 13, 22, 17, 8), notification.getCreatedAt());
        assertEquals(LocalDateTime.of(2022, 6, 13, 22, 17, 9), notification.getUpdatedAt());
    }

    @Override
    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/crud/notification_insert_test.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    protected void insertTest() {
        Notification notification = testNotificationEntityFactory.createEntity(5L, 2, 1);
        notificationRepository.saveAndFlush(notification);
    }

    @Override
    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/crud/notification_update_test.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    protected void updateTest() {
        Notification notification = notificationRepository.findById(1L).orElseThrow();
        notification.setContent("알림 내용 변경");
        entityManager.flush();
    }

    @Override
    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/crud/notification_delete_test.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    protected void deleteTest() {
        notificationRepository.deleteById(1L);
        entityManager.flush();
    }
}