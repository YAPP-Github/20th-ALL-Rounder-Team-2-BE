package kr.co.knowledgerally.core.user.entity;

import kr.co.knowledgerally.core.user.util.TestUserEntityFactory;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserEntityTest {
    TestUserEntityFactory testUserEntityFactory = new TestUserEntityFactory();

    @Test
    void modifyTest() {
        User user = testUserEntityFactory.createEntity(1L);
        User modifyUser = testUserEntityFactory.createEntity(2L);

        user.modify(modifyUser);

        assertEquals(1L, user.getId());
        assertEquals("test1@email.com", user.getEmail());
        assertEquals("테스트2", user.getUsername());
        assertEquals(1, user.getBallCnt());
        assertEquals("안녕하세요. 저는 테스트2이라고 합니다.", user.getIntro());
        assertEquals("kakao_test2", user.getKakaoId());
        assertEquals("포트폴리오2", user.getPortfolio());
        assertEquals("identifier1", user.getIdentifier());
        assertFalse(user.isCoach());
        assertTrue(user.isPushActive());
        assertFalse(user.isOnboard());
        assertTrue(user.isActive());
    }
}