package kr.co.knowledgerally.core.user.repository.crud;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import kr.co.knowledgerally.core.core.repository.AbstractRepositoryCrudTest;
import kr.co.knowledgerally.core.user.entity.User;
import kr.co.knowledgerally.core.user.repository.UserRepository;
import kr.co.knowledgerally.core.user.util.TestUserEntityFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DatabaseSetup({
        "classpath:dbunit/entity/user.xml",
})
class UserRepositoryCrudTest extends AbstractRepositoryCrudTest {

    @Autowired
    UserRepository userRepository;

    TestUserEntityFactory testUserEntityFactory = new TestUserEntityFactory();

    @Override
    @Test
    protected void selectTest() {
        User user = userRepository.findById(1L).orElseThrow();

        assertEquals(1L, user.getId());
        assertEquals("test1@email.com", user.getEmail());
        assertEquals("테스트1", user.getUsername());
        assertEquals(1, user.getBallCnt());
        assertEquals("안녕하세요. 저는 테스트1이라고 합니다.", user.getIntro());
        assertTrue(user.isCoach());
        assertTrue(user.isPushActive());
        assertTrue(user.isActive());
        assertEquals(LocalDateTime.of(2022, 6, 10, 21, 18, 58), user.getCreatedAt());
        assertEquals(LocalDateTime.of(2022, 6, 10, 21, 19, 0), user.getUpdatedAt());
    }

    @Override
    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/crud/user_insert_test.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    protected void insertTest() {
        User user = testUserEntityFactory.createEntity(6L);
        userRepository.saveAndFlush(user);
    }

    @Override
    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/crud/user_update_test.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    protected void updateTest() {
        User user = userRepository.findById(1L).orElseThrow();
        user.setUsername("테스트1변경");
        entityManager.flush();
    }

    @Override
    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/crud/user_delete_test.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    protected void deleteTest() {
        userRepository.deleteById(1L);
        entityManager.flush();
    }
}