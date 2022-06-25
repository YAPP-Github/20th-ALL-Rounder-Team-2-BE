package kr.co.knowledgerally.api.lecture.component;

import kr.co.knowledgerally.api.lecture.dto.LectureInformationDto;
import kr.co.knowledgerally.core.lecture.entity.LectureInformation;
import kr.co.knowledgerally.core.lecture.util.TestLectureEntityFactory;
import kr.co.knowledgerally.core.lecture.util.TestLectureInformationEntityFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LectureInformationMapperTest {
    @Autowired
    private LectureInformationMapper lectureInformationMapper;

    @Test
    void 엔티티에서_DTO변환_테스트() {
        LectureInformation lectureInformation = new TestLectureInformationEntityFactory().createEntity(1L, 1L, 1L);

        LectureInformationDto.ReadOnly lectureInformationDto = lectureInformationMapper.toDto(lectureInformation);
        assertEquals("테스트1 제목", lectureInformationDto.getTopic());
        assertEquals("안녕하세요. 테스트1 입니다.", lectureInformationDto.getIntroduce());
        assertEquals(1, lectureInformationDto.getPrice());
        assertEquals(1, lectureInformationDto.getCoach().getId());
        assertEquals("테스트 카테고리1", lectureInformationDto.getCategory().getCategoryName());
    }
}
