package kr.co.knowledgerally.core.user.repository;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import kr.co.knowledgerally.core.annotation.KnowllyDataTest;
import kr.co.knowledgerally.core.user.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;


@KnowllyDataTest
@DatabaseSetup({
        "classpath:dbunit/entity/user.xml",
})
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Test
    void 식별자로_사용자_존재하는지_테스트() {
        assertTrue(userRepository.existsByIdentifierAndIsActive("identifier1", true));
        assertFalse(userRepository.existsByIdentifierAndIsActive("identifier5", true));
    }

    @Test
    void 식별자로_사용자_찾기_테스트() {
        User user = userRepository.findByIdentifierAndIsActive("identifier1", true).orElseThrow();

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
    void 식별자로_비활성_사용자_찾기_테스트() {
        assertThrows(NoSuchElementException.class, () -> {
            // 5번 사용자는 비활성화 되어있기 때문에 못 찾아야 함
            userRepository.findByIdentifierAndIsActive("identifier5", true).orElseThrow();
        });
    }
}