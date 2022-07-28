package kr.co.knowledgerally.core.lecture.event;

import kr.co.knowledgerally.core.core.component.Notifier;
import kr.co.knowledgerally.core.lecture.entity.Form;
import kr.co.knowledgerally.core.lecture.entity.LectureInformation;
import kr.co.knowledgerally.core.user.entity.Notification;
import kr.co.knowledgerally.core.user.service.BallTransactionService;
import kr.co.knowledgerally.core.user.vo.BallTransactionVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FormRegisterEventListener {
    private final BallTransactionService ballTransactionService;
    private final List<Notifier> notifiers;

    @Async
    @TransactionalEventListener
    public void decreaseBall(FormRegisterEvent event) {
        LectureInformation lectureInformation = event.getValue().getLecture().getLectureInformation();
        final String TITLE = lectureInformation.getTopic();
        final String CONTENT = "클래스 수강";

        BallTransactionVo ballTransactionVo = BallTransactionVo.builder()
                .targetUserId(lectureInformation.getCoach().getUser().getId())
                .title(TITLE)
                .content(CONTENT)
                .count(-1)
                .build();
        ballTransactionService.makeBallTransaction(ballTransactionVo);
        log.info("id : {} 유저에게 볼 차감 완료", event.getValue().getId());
    }

    @Async
    @TransactionalEventListener
    public void sendNotification(FormRegisterEvent event) {
        Form form = event.getValue();

        for (Notifier notifier : notifiers) {
            notifier.sendNotification(buildCoachNotificationFromForm(form));
            log.info("id : {} 코치에게 {} 알림 전송 완료",
                    form.getLecture().getLectureInformation().getCoach().getId(), notifier);
        }
    }

    private Notification buildCoachNotificationFromForm(Form form) {
        return Notification.builder()
                .user(form.getUser())
                .coach(form.getLecture().getLectureInformation().getCoach())
                .title(form.getLecture().getLectureInformation().getTopic())
                .content(String.format("%s님, 클래스 매칭 신청이 들어왔습니다. 신청서 확인 후 응답해주세요. ",
                        form.getLecture().getLectureInformation().getCoach().getUser().getUsername()))
                .notiType(Notification.NotiType.COACH)
                .build();
    }
}
