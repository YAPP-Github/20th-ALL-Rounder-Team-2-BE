package kr.co.knowledgerally.api.user.component;

import kr.co.knowledgerally.api.user.dto.NotificationDto;
import kr.co.knowledgerally.core.user.entity.Notification;
import kr.co.knowledgerally.core.user.util.TestNotificationEntityFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NotificationMapperTest {
    @Autowired
    NotificationMapper notificationMapper;

    @Test
    void 엔티티에서_DTO변환_테스트() {
        Notification notification = new TestNotificationEntityFactory().createEntity(1L, 1L, 3L);

        NotificationDto notificationDto = notificationMapper.toDto(notification);
        notificationDto.setRead(true);

        assertEquals(1L, notificationDto.getReceiver().getId());
        assertEquals(3L, notificationDto.getSender().getId());
        assertEquals("테스트1 내용", notificationDto.getContent());
        assertEquals(Notification.NotiType.PLAYER, notificationDto.getNotiType());
        assertTrue(notificationDto.isRead());
    }
}