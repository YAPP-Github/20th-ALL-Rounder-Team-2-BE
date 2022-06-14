package kr.co.knowledgerally.core.user.repository.crud;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import kr.co.knowledgerally.core.core.repository.AbstractRepositoryCrudTest;
import kr.co.knowledgerally.core.user.entity.BallHistory;
import kr.co.knowledgerally.core.user.repository.BallHistoryRepository;
import kr.co.knowledgerally.core.user.util.TestBallHistoryEntityFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DatabaseSetup({
        "classpath:dbunit/entity/user.xml",
        "classpath:dbunit/entity/ball_history.xml",
})
class BallHistoryRepositoryCrudTest extends AbstractRepositoryCrudTest {

    @Autowired
    BallHistoryRepository ballHistoryRepository;

    TestBallHistoryEntityFactory testBallHistoryEntityFactory = new TestBallHistoryEntityFactory();

    @Override
    @Test
    protected void selectTest() {
        BallHistory ballHistory = ballHistoryRepository.findById(1L).orElseThrow();

        assertEquals(1L, ballHistory.getId());
        assertEquals(1L, ballHistory.getUser().getId());
        assertEquals("운영 클래스", ballHistory.getTitle());
        assertEquals("프랑스어 수업", ballHistory.getContent());
        assertEquals(1, ballHistory.getCount());
        assertTrue(ballHistory.isActive());
        assertEquals(LocalDateTime.of(2022, 6, 13, 21, 52, 34), ballHistory.getCreatedAt());
        assertEquals(LocalDateTime.of(2022, 6, 13, 21, 52, 35), ballHistory.getUpdatedAt());
    }

    @Override
    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/crud/ball_history_insert_test.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    protected void insertTest() {
        BallHistory ballHistory = testBallHistoryEntityFactory.createEntity(8L, 1L);
        ballHistoryRepository.saveAndFlush(ballHistory);
    }

    @Override
    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/crud/ball_history_update_test.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    protected void updateTest() {
        BallHistory ballHistory = ballHistoryRepository.findById(1L).orElseThrow();
        ballHistory.setTitle("제목 변경");
        entityManager.flush();
    }

    @Override
    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/crud/ball_history_delete_test.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    protected void deleteTest() {
        ballHistoryRepository.deleteById(1L);
        entityManager.flush();
    }
}