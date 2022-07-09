package kr.co.knowledgerally.api.user.event;

import kr.co.knowledgerally.api.lecture.event.FormRegisterEvent;
import kr.co.knowledgerally.api.review.event.ReviewWrittenEvent;
import kr.co.knowledgerally.core.lecture.entity.Lecture;
import kr.co.knowledgerally.core.lecture.entity.LectureInformation;
import kr.co.knowledgerally.core.user.service.BallService;
import kr.co.knowledgerally.core.user.vo.BallTransactionVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Service
@RequiredArgsConstructor
public class BallEventListener {
    private final BallService ballService;

    @Async
    @TransactionalEventListener
    public void handleUserSignupEvent(UserSignupEvent event) {
        final String TITLE = "첫 가입 축하 볼";
        final String CONTENT = "첫 가입 축하 증정";

        BallTransactionVo ballTransactionVo = BallTransactionVo.builder()
                .targetUserId(event.getValue().getId())
                .title(TITLE)
                .content(CONTENT)
                .count(1)
                .build();
        ballService.giveBall(ballTransactionVo);
        log.info("id : {} 유저에게 볼 지급 완료", event.getValue().getId());
    }

    @Async
    @TransactionalEventListener
    public void handleReviewWrittenEvent(ReviewWrittenEvent event) {
        LectureInformation lectureInformation = event.getValue().getLectureInformation();
        final String TITLE = lectureInformation.getTopic();
        final String CONTENT = "클래스 운영";

        BallTransactionVo ballTransactionVo = BallTransactionVo.builder()
                .targetUserId(lectureInformation.getCoach().getUser().getId())
                .title(TITLE)
                .content(CONTENT)
                .count(1)
                .build();
        ballService.giveBall(ballTransactionVo);
        log.info("id : {} 유저에게 볼 지급 완료", event.getValue().getId());
    }

    @Async
    @TransactionalEventListener
    public void handleFormRegisterEvent(FormRegisterEvent event) {
        LectureInformation lectureInformation = event.getValue().getLectureInformation();
        final String TITLE = lectureInformation.getTopic();
        final String CONTENT = "클래스 수강";

        BallTransactionVo ballTransactionVo = BallTransactionVo.builder()
                .targetUserId(lectureInformation.getCoach().getUser().getId())
                .title(TITLE)
                .content(CONTENT)
                .count(-1)
                .build();
        ballService.giveBall(ballTransactionVo);
        log.info("id : {} 유저에게 볼 차감 완료", event.getValue().getId());
    }
}
