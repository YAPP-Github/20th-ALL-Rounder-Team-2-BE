package kr.co.knowledgerally.api.lecture.component;

import kr.co.knowledgerally.api.lecture.dto.LectureDto;
import kr.co.knowledgerally.api.lecture.util.TestLectureDtoFactory;
import kr.co.knowledgerally.core.lecture.entity.Lecture;
import kr.co.knowledgerally.core.lecture.util.TestLectureEntityFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LectureMapperTest {
    @Autowired
    private LectureMapper lectureMapper;

    @Test
    void 엔티티에서_DTO변환_테스트() {
        Lecture lecture = new TestLectureEntityFactory().createEntity(1L);

        LectureDto.ReadOnly lectureDto = lectureMapper.toDto(lecture);
        assertEquals(1L, lectureDto.getId());
        assertEquals("2022-06-15T10:30:50", lectureDto.getStartAt());
        assertEquals("2022-06-15T11:30:50", lectureDto.getEndAt());
        assertEquals(Lecture.State.ON_BOARD, lectureDto.getState());
        assertFalse(lectureDto.isReviewWritten());
    }

    @Test
    void DTO에서_엔티티변환_테스트() {
        LectureDto lectureDto = new TestLectureDtoFactory().createDto(1L);

        Lecture lecture = lectureMapper.toEntity(lectureDto);
        assertEquals(LocalDateTime.of(2022, 6, 15, 10, 30, 50), lecture.getStartAt());
        assertEquals(LocalDateTime.of(2022, 6, 15, 11, 30, 50), lecture.getEndAt());
    }
}