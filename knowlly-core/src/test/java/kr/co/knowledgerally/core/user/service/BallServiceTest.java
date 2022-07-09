package kr.co.knowledgerally.core.user.service;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import kr.co.knowledgerally.core.annotation.KnowllyDataTest;
import kr.co.knowledgerally.core.user.vo.BallTransactionVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@KnowllyDataTest
@Import({BallService.class, UserService.class})
@DatabaseSetup({
        "classpath:dbunit/entity/user.xml",
        "classpath:dbunit/entity/ball_history.xml",
})
class BallServiceTest {
    @Autowired
    BallService ballService;

    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/사용자_볼_증가_테스트.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    void 사용자_볼_증가_테스트() {
        ballService.giveBall(BallTransactionVo.builder()
                        .targetUserId(1L)
                        .title("testTitle")
                        .content("testContent")
                        .count(1)
                .build());
    }
}