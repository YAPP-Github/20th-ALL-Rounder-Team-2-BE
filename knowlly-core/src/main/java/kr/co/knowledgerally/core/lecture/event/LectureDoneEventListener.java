package kr.co.knowledgerally.core.lecture.event;

import kr.co.knowledgerally.core.core.component.Notifier;
import kr.co.knowledgerally.core.lecture.entity.Form;
import kr.co.knowledgerally.core.lecture.entity.Lecture;
import kr.co.knowledgerally.core.lecture.entity.LectureInformation;
import kr.co.knowledgerally.core.user.entity.Notification;
import kr.co.knowledgerally.core.user.service.BallTransactionService;
import kr.co.knowledgerally.core.user.vo.BallTransactionVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LectureDoneEventListener {
    private final List<Notifier> notifiers;
    private final BallTransactionService ballTransactionService;

    @Async
    @TransactionalEventListener
    public void sendNotificationFromEvent(LectureDoneEvent event) {
        Lecture lecture = event.getValue();
        if (lecture.getState() != Lecture.State.DONE || lecture.getAcceptedForm().isEmpty()) {
            log.warn("Lecture id : {} 완료되지 않거나, Accept된 Form이 없음", lecture.getId());
            return;
        }

        for (Notifier notifier : notifiers) {
            notifier.sendNotification(buildPlayerNotificationFromLecture(lecture));
            Form form = lecture.getAcceptedForm().get();
            log.info("id : {} 유저에게 알림 전송 완료, 알림 타입 : {}", form.getUser().getId(), notifier);
        }
    }

    private Notification buildPlayerNotificationFromLecture(Lecture lecture) {
        Form form = lecture.getAcceptedForm().orElseThrow();
        return Notification.builder()
                .user(form.getUser())
                .coach(lecture.getLectureInformation().getCoach())
                .title(lecture.getLectureInformation().getTopic())
                .content(String.format("%s님, 클래스 어떠셨나요? 소중한 후기를 남겨주세요. ", form.getUser().getUsername()))
                .notiType(Notification.NotiType.PLAYER)
                .build();
    }

    @Async
    @TransactionalEventListener
    public void giveBallFromEvent(LectureDoneEvent event) {
        Lecture lecture = event.getValue();
        if (lecture.getState() != Lecture.State.DONE) {
            log.warn("Lecture id : {} 완료되지 않음", lecture.getId());
            return;
        }

        LectureInformation lectureInformation = lecture.getLectureInformation();
        final String TITLE = lectureInformation.getTopic();
        final String CONTENT = "클래스 운영";

        BallTransactionVo ballTransactionVo = BallTransactionVo.builder()
                .targetUserId(lectureInformation.getCoach().getUser().getId())
                .title(TITLE)
                .content(CONTENT)
                .count(1)
                .build();
        ballTransactionService.makeBallTransaction(ballTransactionVo);
        log.info("id : {} 유저에게 볼 지급 완료", event.getValue().getId());

        // 볼 지급 알림 전송
        for (Notifier notifier : notifiers) {
            notifier.sendNotification(buildCoachNotificationFromLecture(lecture));
            log.info("id : {} 코치에게 알림 전송 완료, 알림 타입 : {}",
                    lecture.getLectureInformation().getCoach().getId(), notifier);
        }
    }

    private Notification buildCoachNotificationFromLecture(Lecture lecture) {
        Form form = lecture.getAcceptedForm().orElseThrow();
        return Notification.builder()
                .user(form.getUser())
                .coach(form.getLecture().getLectureInformation().getCoach())
                .title(form.getLecture().getLectureInformation().getTopic())
                .content(String.format("%s님, 클래스 운영에 대한 리워드로 볼 1개가 적립됐어요. 볼을 활용해 다양한 클래스를 들어보세요! ",
                        form.getLecture().getLectureInformation().getCoach().getUser().getUsername()))
                .notiType(Notification.NotiType.COACH)
                .build();
    }
}
