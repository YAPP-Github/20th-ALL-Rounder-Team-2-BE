package kr.co.knowledgerally.core.lecture.event;

import kr.co.knowledgerally.core.core.component.Notifier;
import kr.co.knowledgerally.core.lecture.entity.Form;
import kr.co.knowledgerally.core.user.entity.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FormStateChangedEventListener {
    private final List<Notifier> notifiers;

    @Async
    @TransactionalEventListener
    public void handleFormStateChangedEvent(FormStateChangedEvent event) {
        Form form = event.getValue();

        for (Notifier notifier : notifiers) {
            notifier.sendNotification(buildPlayerNotificationFromForm(form));
            log.info("id : {} 유저에게 알림 전송 완료, 알림 타입 : {}", form.getUser().getId(), notifier);

            if (form.getState() == Form.State.ACCEPT) {
                notifier.sendNotification(buildCoachNotificationFromForm(form));
                log.info("id : {} 코치에게 알림 전송 완료, 알림 타입 : {}",
                        form.getLecture().getLectureInformation().getCoach().getId(), notifier);
            }
        }
    }

    private Notification buildPlayerNotificationFromForm(Form form) {
        Notification notification = Notification.builder()
                .user(form.getUser())
                .coach(form.getLecture().getLectureInformation().getCoach())
                .title(form.getLecture().getLectureInformation().getTopic())
                .notiType(Notification.NotiType.PLAYER)
                .build();

        String content;
        if (form.getState() == Form.State.ACCEPT) {
            content = String.format("%s님, 신청한 매칭이 수락되었습니다. 클래스 진행 장소 협의를 위해 코치의 연락처로 연락해주세요. ",
                    form.getUser().getUsername());
        } else {
            content = String.format("%s님, 아쉽게도 신청한 매칭이 거절됐습니다. 새로운 클래스를 탐색해보고 매칭 신청을 해보세요.",
                    form.getUser().getUsername());
        }
        notification.setContent(content);
        return notification;
    }

    private Notification buildCoachNotificationFromForm(Form form) {
        return Notification.builder()
                .user(form.getUser())
                .coach(form.getLecture().getLectureInformation().getCoach())
                .title(form.getLecture().getLectureInformation().getTopic())
                .content(String.format("%s님, 예정된 클래스의 진행 장소 협의를 위해 플레이어의 연락처로 연락해주세요. ",
                        form.getLecture().getLectureInformation().getCoach().getUser().getUsername()))
                .notiType(Notification.NotiType.COACH)
                .build();
    }
}
