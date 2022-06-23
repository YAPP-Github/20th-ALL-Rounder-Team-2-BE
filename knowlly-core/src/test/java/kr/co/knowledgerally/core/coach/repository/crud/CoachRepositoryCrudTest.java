package kr.co.knowledgerally.core.coach.repository.crud;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import kr.co.knowledgerally.core.core.repository.AbstractRepositoryCrudTest;
import kr.co.knowledgerally.core.coach.entity.Coach;
import kr.co.knowledgerally.core.coach.repository.CoachRepository;
import kr.co.knowledgerally.core.coach.util.TestCoachEntityFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DatabaseSetup({
        "classpath:dbunit/entity/user.xml",
        "classpath:dbunit/entity/coach.xml",
})
class CoachRepositoryCrudTest extends AbstractRepositoryCrudTest {

    @Autowired
    CoachRepository coachRepository;

    TestCoachEntityFactory testCoachEntityFactory = new TestCoachEntityFactory();

    @Override
    @Test
    protected void selectTest() {
        Coach coach = coachRepository.findById(1L).orElseThrow();

        assertEquals(1L, coach.getId());
        assertEquals("안녕하세요. 테스트1 코치입니다.", coach.getIntroduce());
        assertTrue(coach.isActive());
        assertEquals(LocalDateTime.of(2022, 6, 13, 21, 57, 17), coach.getCreatedAt());
        assertEquals(LocalDateTime.of(2022, 6, 13, 21, 57, 17), coach.getUpdatedAt());
        assertEquals(1L, coach.getUser().getId());
        assertEquals("test1@email.com", coach.getUser().getEmail());
        assertEquals("테스트1", coach.getUser().getUsername());
        assertEquals(1, coach.getUser().getBallCnt());
        assertEquals("안녕하세요. 저는 테스트1이라고 합니다.", coach.getUser().getIntro());
        assertEquals("kakao_test1", coach.getUser().getKakaoId());
        assertEquals("포트폴리오1", coach.getUser().getPortfolio());
        assertEquals("identifier1", coach.getUser().getIdentifier());
        assertTrue(coach.getUser().isCoach());
        assertTrue(coach.getUser().isPushActive());
        assertTrue(coach.getUser().isActive());
        assertEquals(LocalDateTime.of(2022, 6, 10, 21, 18, 58), coach.getUser().getCreatedAt());
        assertEquals(LocalDateTime.of(2022, 6, 10, 21, 19, 0), coach.getUser().getUpdatedAt());
    }

    @Override
    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/crud/coach_insert_test.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    protected void insertTest() {
        Coach coach = testCoachEntityFactory.createEntity(5, 2);
        coachRepository.saveAndFlush(coach);
    }

    @Override
    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/crud/coach_update_test.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    protected void updateTest() {
        Coach coach = coachRepository.findById(1L).orElseThrow();
        coach.setIntroduce("자기소개 변경");
        entityManager.flush();
    }

    @Override
    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/crud/coach_delete_test.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    protected void deleteTest() {
        coachRepository.deleteById(1L);
        entityManager.flush();
    }
}