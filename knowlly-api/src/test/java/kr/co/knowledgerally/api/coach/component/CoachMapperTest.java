package kr.co.knowledgerally.api.coach.component;

import kr.co.knowledgerally.api.coach.dto.CoachDto;
import kr.co.knowledgerally.core.coach.entity.Coach;
import kr.co.knowledgerally.core.coach.util.TestCoachEntityFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CoachMapperTest {
    @Autowired
    CoachMapper coachMapper;

    @Test
    void 엔티티에서_DTO변환_테스트() {
        Coach coach = new TestCoachEntityFactory().createEntity(1L, 1L);

        CoachDto.ReadOnly coachDto = coachMapper.toDto(coach);
        assertEquals(1L, coachDto.getId());
        assertEquals("안녕하세요. 테스트1 코치입니다.", coachDto.getIntroduce());
        assertEquals(1L, coachDto.getUser().getId());
        assertEquals("테스트1", coachDto.getUser().getUsername());
        assertEquals(1, coachDto.getUser().getBallCnt());
        assertEquals("안녕하세요. 저는 테스트1이라고 합니다.", coachDto.getUser().getIntro());
        assertEquals("kakao_test1", coachDto.getUser().getKakaoId());
        assertEquals("포트폴리오1", coachDto.getUser().getPortfolio());
        assertEquals("identifier1", coachDto.getUser().getIdentifier());
        assertFalse(coachDto.getUser().isCoach());
        assertTrue(coachDto.getUser().isPushActive());
    }
}