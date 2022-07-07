package kr.co.knowledgerally.core.lecture.service;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import kr.co.knowledgerally.core.annotation.KnowllyDataTest;
import kr.co.knowledgerally.core.core.exception.ResourceNotFoundException;
import kr.co.knowledgerally.core.lecture.entity.Form;
import kr.co.knowledgerally.core.lecture.entity.Lecture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@KnowllyDataTest
@DatabaseSetup({
        "classpath:dbunit/entity/user.xml",
        "classpath:dbunit/entity/coach.xml",
        "classpath:dbunit/entity/category.xml",
        "classpath:dbunit/entity/lecture_information.xml",
        "classpath:dbunit/entity/lecture.xml",
})
@Import(LectureService.class)
class LectureServiceTest {
    @Autowired
    LectureService lectureService;

    @Test
    void ID로_클래스_일정_찾기_테스트() {
        Lecture lecture = lectureService.findById(1L);

        assertEquals(1L, lecture.getId());
        assertEquals(1L, lecture.getLectureInformation().getId());
        assertEquals(LocalDateTime.of(2022, 6, 8, 22, 44, 10), lecture.getStartAt());
        assertEquals(LocalDateTime.of(2022, 6, 8, 23, 44, 4), lecture.getEndAt());
        assertEquals(Lecture.State.DONE, lecture.getState());
        assertTrue(lecture.isReviewWritten());
        assertTrue(lecture.isActive());
        assertEquals(LocalDateTime.of(2022, 6, 8, 22, 44, 10), lecture.getCreatedAt());
        assertEquals(LocalDateTime.of(2022, 6, 8, 22, 44, 10), lecture.getUpdatedAt());
    }

    @Test
    void ID로_클래스_일정_찾기_테스트_없으면_throw() {
        assertThrows(ResourceNotFoundException.class, () -> {
            lectureService.findById(9999L);
        });
    }
}