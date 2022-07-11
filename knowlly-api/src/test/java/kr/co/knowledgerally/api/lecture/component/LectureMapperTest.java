package kr.co.knowledgerally.api.lecture.component;

import kr.co.knowledgerally.api.lecture.dto.LectureDto;
import kr.co.knowledgerally.api.lecture.util.TestLectureDtoFactory;
import kr.co.knowledgerally.core.lecture.entity.Form;
import kr.co.knowledgerally.core.lecture.entity.Lecture;
import kr.co.knowledgerally.core.lecture.util.TestFormEntityFactory;
import kr.co.knowledgerally.core.lecture.util.TestLectureEntityFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LectureMapperTest {
    @Autowired
    private LectureMapper lectureMapper;

    @Test
    void 엔티티에서_DTO변환_매칭중_테스트() {
        Lecture lecture = new TestLectureEntityFactory().createEntity(1L);

        LectureDto.ReadOnly lectureDto = lectureMapper.toDto(lecture);
        assertEquals(1L, lectureDto.getId());
        assertEquals("2022-06-15T10:30:50", lectureDto.getStartAt());
        assertEquals("2022-06-15T11:30:50", lectureDto.getEndAt());
        assertEquals(Lecture.State.ON_BOARD, lectureDto.getState());
        assertFalse(lectureDto.isReviewWritten());
        assertFalse(lectureDto.isMatched());
        assertNull(lectureDto.getMatchedUser());
    }

    @Test
    void 엔티티에서_DTO변환_매칭완료_테스트() {
        Lecture lecture = new TestLectureEntityFactory().createEntity(1L);
        lecture.setState(Lecture.State.ON_GOING);
        Form form = new TestFormEntityFactory().createEntity(1L);
        form.setState(Form.State.ACCEPT);
        lecture.setForms(List.of(form));

        LectureDto.ReadOnly lectureDto = lectureMapper.toDto(lecture);
        assertEquals(1L, lectureDto.getId());
        assertEquals("2022-06-15T10:30:50", lectureDto.getStartAt());
        assertEquals("2022-06-15T11:30:50", lectureDto.getEndAt());
        assertEquals(Lecture.State.ON_GOING, lectureDto.getState());
        assertFalse(lectureDto.isReviewWritten());
        assertTrue(lectureDto.isMatched());
        assertNotNull(lectureDto.getMatchedUser());
        assertEquals(1L, lectureDto.getMatchedUser().getId());
    }

    @Test
    void 엔티티에서_DTO변환_완료_테스트() {
        Lecture lecture = new TestLectureEntityFactory().createEntity(1L);
        lecture.setState(Lecture.State.DONE);
        lecture.setReviewWritten(true);
        Form form = new TestFormEntityFactory().createEntity(1L);
        form.setState(Form.State.ACCEPT);
        lecture.setForms(List.of(form));

        LectureDto.ReadOnly lectureDto = lectureMapper.toDto(lecture);
        assertEquals(1L, lectureDto.getId());
        assertEquals("2022-06-15T10:30:50", lectureDto.getStartAt());
        assertEquals("2022-06-15T11:30:50", lectureDto.getEndAt());
        assertEquals(Lecture.State.DONE, lectureDto.getState());
        assertTrue(lectureDto.isReviewWritten());
        assertTrue(lectureDto.isMatched());
        assertNotNull(lectureDto.getMatchedUser());
        assertEquals(1L, lectureDto.getMatchedUser().getId());
    }

    @Test
    void DTO에서_엔티티변환_테스트() {
        LectureDto lectureDto = new TestLectureDtoFactory().createDto(1L);

        Lecture lecture = lectureMapper.toEntity(lectureDto);
        assertEquals(LocalDateTime.of(2022, 6, 15, 10, 30, 50), lecture.getStartAt());
        assertEquals(LocalDateTime.of(2022, 6, 15, 11, 30, 50), lecture.getEndAt());
    }
}