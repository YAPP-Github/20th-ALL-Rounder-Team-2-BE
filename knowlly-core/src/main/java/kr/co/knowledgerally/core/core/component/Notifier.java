package kr.co.knowledgerally.core.core.component;

import kr.co.knowledgerally.core.user.entity.Notification;

public interface Notifier {
    void sendNotification(Notification notification);
}
