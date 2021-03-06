package kr.co.knowledgerally.api.lecture.component;

import kr.co.knowledgerally.api.lecture.dto.LectureInformationDto;
import kr.co.knowledgerally.api.lecture.dto.LectureInformationDtoReadOnly;
import kr.co.knowledgerally.api.lecture.util.TestLectureInformationDtoFactory;
import kr.co.knowledgerally.core.lecture.entity.Category;
import kr.co.knowledgerally.core.lecture.entity.Lecture;
import kr.co.knowledgerally.core.lecture.entity.LectureInformation;
import kr.co.knowledgerally.core.lecture.util.TestLectureEntityFactory;
import kr.co.knowledgerally.core.lecture.util.TestLectureInformationEntityFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LectureInformationMapperTest {
    @Autowired
    private LectureInformationMapper lectureInformationMapper;

    @Test
    void 엔티티에서_DTO변환_테스트() {
        LectureInformation lectureInformation = new TestLectureInformationEntityFactory().createEntity(1L, 1L, 1L,1L, 2L);
        Lecture lecture = new TestLectureEntityFactory().createEntity(1L);
        lectureInformation.setLectures(List.of(lecture));

        LectureInformationDtoReadOnly lectureInformationDto = lectureInformationMapper.toDto(lectureInformation);
        assertEquals("테스트1 제목", lectureInformationDto.getTopic());
        assertEquals("안녕하세요. 테스트1 입니다.", lectureInformationDto.getIntroduce());
        assertEquals(1, lectureInformationDto.getPrice());
        assertEquals(1, lectureInformationDto.getCoach().getId());
        assertEquals(Category.Name.ETC, lectureInformationDto.getCategory());
        assertEquals(2, lectureInformationDto.getLectureImages().size());
        assertEquals(1, lectureInformationDto.getTags().size());
        assertEquals(1, lectureInformationDto.getLectures().size());
        assertEquals(1, lectureInformationDto.getLectures().stream().findFirst().orElseThrow().getId());
    }

    @Test
    void DTO에서_엔티티변환_테스트() {
        LectureInformationDto lectureInformationDto = new TestLectureInformationDtoFactory().createDto(1L);

        LectureInformation lectureInformation = lectureInformationMapper.toEntity(lectureInformationDto);
        assertEquals("테스트1 제목", lectureInformation.getTopic());
        assertEquals("안녕하세요. 테스트1 입니다.", lectureInformation.getIntroduce());
        assertEquals(lectureInformation.getCategory().getName(), Category.Name.ETC);
    }
}
