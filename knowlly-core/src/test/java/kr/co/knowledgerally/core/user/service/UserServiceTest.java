package kr.co.knowledgerally.core.user.service;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import kr.co.knowledgerally.core.annotation.KnowllyDataTest;
import kr.co.knowledgerally.core.core.exception.ResourceNotFoundException;
import kr.co.knowledgerally.core.user.entity.User;
import kr.co.knowledgerally.core.user.util.TestUserEntityFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@KnowllyDataTest
@Import(UserService.class)
@DatabaseSetup({
        "classpath:dbunit/entity/user.xml",
})
class UserServiceTest {
    @Autowired
    UserService userService;

    TestUserEntityFactory testUserEntityFactory = new TestUserEntityFactory();

    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/crud/user_insert_test.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    void 사용자_저장_테스트() {
        User user = testUserEntityFactory.createEntity(6L);
        userService.saveUser(user);
    }

    @Test
    void 식별자로_사용자_존재여부_판별() {
        assertTrue(userService.isMemberExistByIdentifier("identifier1"));
        assertFalse(userService.isMemberExistByIdentifier("identifier5"));
    }

    @Test
    void 식별자로_사용자_찾기() {
        User user = userService.findByIdentifier("identifier1");

        assertEquals(1L, user.getId());
        assertEquals("test1@email.com", user.getEmail());
        assertEquals("테스트1", user.getUsername());
        assertEquals(1, user.getBallCnt());
        assertEquals("안녕하세요. 저는 테스트1이라고 합니다.", user.getIntro());
        assertEquals("kakao_test1", user.getKakaoId());
        assertEquals("포트폴리오1", user.getPortfolio());
        assertEquals("identifier1", user.getIdentifier());
        assertTrue(user.isCoach());
        assertTrue(user.isPushActive());
        assertTrue(user.isActive());
        assertEquals(LocalDateTime.of(2022, 6, 10, 21, 18, 58), user.getCreatedAt());
        assertEquals(LocalDateTime.of(2022, 6, 10, 21, 19, 0), user.getUpdatedAt());
    }

    @Test
    void 식별자로_사용자_찾기_실패() {
        assertThrows(ResourceNotFoundException.class, () -> {
            userService.findByIdentifier("non-exist-identifier");
        });
    }
}