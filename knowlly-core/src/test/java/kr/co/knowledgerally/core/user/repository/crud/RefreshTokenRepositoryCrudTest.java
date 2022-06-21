package kr.co.knowledgerally.core.user.repository.crud;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import kr.co.knowledgerally.core.core.repository.AbstractRepositoryCrudTest;
import kr.co.knowledgerally.core.user.entity.BallHistory;
import kr.co.knowledgerally.core.user.entity.RefreshToken;
import kr.co.knowledgerally.core.user.repository.RefreshTokenRepository;
import kr.co.knowledgerally.core.user.util.TestRefreshTokenEntityFactory;
import liquibase.pro.packaged.T;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DatabaseSetup({
        "classpath:dbunit/entity/user.xml",
        "classpath:dbunit/entity/refresh_token.xml",
})
class RefreshTokenRepositoryCrudTest extends AbstractRepositoryCrudTest {

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    TestRefreshTokenEntityFactory testRefreshTokenEntityFactory = new TestRefreshTokenEntityFactory();

    @Override
    @Test
    protected void selectTest() {
        RefreshToken refreshToken = refreshTokenRepository.findById(1L).orElseThrow();

        assertEquals(1L, refreshToken.getId());
        assertEquals(1L, refreshToken.getUser().getId());
        assertEquals("refresh_token_value1", refreshToken.getValue());
        assertEquals(LocalDateTime.of(2022, 6, 13, 21, 52, 34), refreshToken.getCreatedAt());
        assertEquals(LocalDateTime.of(2022, 6, 13, 21, 52, 35), refreshToken.getUpdatedAt());
    }

    @Override
    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/crud/refresh_token_insert_test.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    protected void insertTest() {
        RefreshToken refreshToken = testRefreshTokenEntityFactory.createEntity(5L, 5L);
        refreshTokenRepository.saveAndFlush(refreshToken);
    }

    @Override
    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/crud/refresh_token_update_test.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    protected void updateTest() {
        RefreshToken refreshToken = refreshTokenRepository.findById(1L).orElseThrow();
        refreshToken.setValue("changed_value");
        entityManager.flush();
    }

    @Override
    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/crud/refresh_token_delete_test.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    protected void deleteTest() {
        refreshTokenRepository.deleteById(1L);
        entityManager.flush();
    }
}