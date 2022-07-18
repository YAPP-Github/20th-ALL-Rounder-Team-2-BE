package kr.co.knowledgerally.core.core.component;

import kr.co.knowledgerally.core.user.entity.Notification;
import kr.co.knowledgerally.core.user.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InAppNotifier implements Notifier {
    private final NotificationRepository notificationRepository;

    @Override
    @Transactional
    public void sendNotification(Notification notification) {
        notificationRepository.saveAndFlush(notification);
    }
}
