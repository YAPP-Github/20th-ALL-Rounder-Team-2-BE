package kr.co.knowledgerally.core.user.repository;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import kr.co.knowledgerally.core.annotation.KnowllyDataTest;
import kr.co.knowledgerally.core.core.repository.AbstractRepositoryCrudTest;
import kr.co.knowledgerally.core.user.entity.RefreshToken;
import kr.co.knowledgerally.core.user.entity.User;
import kr.co.knowledgerally.core.user.util.TestUserEntityFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@KnowllyDataTest
@DatabaseSetup({
        "classpath:dbunit/entity/user.xml",
        "classpath:dbunit/entity/refresh_token.xml",
})
class RefreshTokenRepositoryTest {
    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    TestUserEntityFactory testUserEntityFactory = new TestUserEntityFactory();

    @Test
    void 사용자로_리프레시_토큰_찾기_테스트() {
        User user = testUserEntityFactory.createEntity(1L);

        RefreshToken refreshToken = refreshTokenRepository.findByUser(user).orElseThrow();

        assertEquals(1L, refreshToken.getId());
        assertEquals(1L, refreshToken.getUser().getId());
        assertEquals("refresh_token_value1", refreshToken.getValue());
        assertEquals(LocalDateTime.of(2022, 6, 13, 21, 52, 34), refreshToken.getCreatedAt());
        assertEquals(LocalDateTime.of(2022, 6, 13, 21, 52, 35), refreshToken.getUpdatedAt());
    }
}