package kr.co.knowledgerally.core.user.service;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import kr.co.knowledgerally.core.annotation.KnowllyDataTest;
import kr.co.knowledgerally.core.user.vo.BallTransactionVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

@KnowllyDataTest
@Import({BallTransactionService.class, UserService.class})
@DatabaseSetup({
        "classpath:dbunit/entity/user.xml",
        "classpath:dbunit/entity/ball_history.xml",
})
class BallTransactionServiceTest {
    @Autowired
    BallTransactionService ballTransactionService;

    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/사용자_볼_증가_테스트.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    void 사용자_볼_증가_테스트() {
        ballTransactionService.makeBallTransaction(BallTransactionVo.builder()
                        .targetUserId(1L)
                        .title("testTitle")
                        .content("testContent")
                        .count(1)
                .build());
    }
}