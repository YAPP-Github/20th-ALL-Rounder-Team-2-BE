package kr.co.knowledgerally.api.user.event;

import kr.co.knowledgerally.core.user.service.BallTransactionService;
import kr.co.knowledgerally.core.user.vo.BallTransactionVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserSignupEventListener {
    private final BallTransactionService ballTransactionService;

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
        ballTransactionService.makeBallTransaction(ballTransactionVo);
        log.info("id : {} 유저에게 볼 지급 완료", event.getValue().getId());
    }
}
