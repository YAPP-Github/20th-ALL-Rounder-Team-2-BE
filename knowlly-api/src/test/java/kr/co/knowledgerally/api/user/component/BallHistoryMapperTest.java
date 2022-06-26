package kr.co.knowledgerally.api.user.component;

import kr.co.knowledgerally.api.user.dto.BallHistoryDto;
import kr.co.knowledgerally.api.user.dto.UserImageDto;
import kr.co.knowledgerally.core.user.entity.BallHistory;
import kr.co.knowledgerally.core.user.entity.UserImage;
import kr.co.knowledgerally.core.user.util.TestBallHistoryEntityFactory;
import kr.co.knowledgerally.core.user.util.TestUserImageEntityFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BallHistoryMapperTest {
    @Autowired
    BallHistoryMapper ballHistoryMapper;

    @Test
    void 엔티티에서_DTO변환_테스트() {
        BallHistory ballHistory = new TestBallHistoryEntityFactory().createEntity(1L, 1L);

        BallHistoryDto ballHistoryDto = ballHistoryMapper.toDto(ballHistory);
        assertEquals("테스트1 제목", ballHistoryDto.getTitle());
        assertEquals("테스트1 내용", ballHistoryDto.getContent());
        assertEquals(1, ballHistoryDto.getCount());

        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        assertEquals(today, ballHistoryDto.getHistoryDate());
    }
}