package kr.co.knowledgerally.core.user.repository.crud;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import kr.co.knowledgerally.core.core.repository.AbstractRepositoryCrudTest;
import kr.co.knowledgerally.core.user.entity.Coach;
import kr.co.knowledgerally.core.user.repository.CoachRepository;
import kr.co.knowledgerally.core.user.util.TestCoachEntityFactory;
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