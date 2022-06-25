package kr.co.knowledgerally.core.user.repository;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import kr.co.knowledgerally.core.annotation.KnowllyDataTest;
import kr.co.knowledgerally.core.user.entity.BallHistory;
import kr.co.knowledgerally.core.user.util.TestUserEntityFactory;
import liquibase.pro.packaged.T;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@KnowllyDataTest
@DatabaseSetup({
        "classpath:dbunit/entity/user.xml",
        "classpath:dbunit/entity/ball_history.xml",
})
class BallHistoryRepositoryTest {
    @Autowired
    BallHistoryRepository ballHistoryRepository;

    TestUserEntityFactory testUserEntityFactory = new TestUserEntityFactory();

    @Test
    void 사용자로_볼내역_목록_찾기() {
        List<BallHistory> ballHistories = ballHistoryRepository.findAllByUserOrderByCreatedAtDesc(
                testUserEntityFactory.createEntity(1L));

        assertEquals(2L, ballHistories.get(0).getId());
        assertEquals(1L, ballHistories.get(0).getUser().getId());
        assertEquals("수강 클래스", ballHistories.get(0).getTitle());
        assertEquals("영어 수업", ballHistories.get(0).getContent());
        assertEquals(-1, ballHistories.get(0).getCount());
        assertTrue(ballHistories.get(0).isActive());
        assertEquals(LocalDateTime.of(2022, 6, 13, 21, 52, 35), ballHistories.get(0).getCreatedAt());
        assertEquals(LocalDateTime.of(2022, 6, 13, 21, 52, 36), ballHistories.get(0).getUpdatedAt());
        assertEquals(1L, ballHistories.get(1).getId());
        assertEquals(1L, ballHistories.get(1).getUser().getId());
        assertEquals("운영 클래스", ballHistories.get(1).getTitle());
        assertEquals("프랑스어 수업", ballHistories.get(1).getContent());
        assertEquals(1, ballHistories.get(1).getCount());
        assertTrue(ballHistories.get(1).isActive());
        assertEquals(LocalDateTime.of(2022, 6, 13, 21, 52, 34), ballHistories.get(1).getCreatedAt());
        assertEquals(LocalDateTime.of(2022, 6, 13, 21, 52, 35), ballHistories.get(1).getUpdatedAt());
    }
}