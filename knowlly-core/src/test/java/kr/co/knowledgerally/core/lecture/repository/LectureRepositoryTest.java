package kr.co.knowledgerally.core.lecture.repository;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import kr.co.knowledgerally.core.annotation.KnowllyDataTest;
import kr.co.knowledgerally.core.coach.entity.Coach;
import kr.co.knowledgerally.core.coach.util.TestCoachEntityFactory;
import kr.co.knowledgerally.core.lecture.entity.Lecture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@KnowllyDataTest
@DatabaseSetup({
        "classpath:dbunit/entity/user.xml",
        "classpath:dbunit/entity/coach.xml",
        "classpath:dbunit/entity/category.xml",
        "classpath:dbunit/entity/lecture_information.xml",
        "classpath:dbunit/entity/lecture.xml",
         "classpath:dbunit/entity/form.xml",
})
class LectureRepositoryTest {
    @Autowired
    LectureRepository lectureRepository;

    @Test
    void 코치로_클래스_찾기_테스트() {
        Coach coach = new TestCoachEntityFactory().createEntity(1L);

        List<Lecture> lectures = lectureRepository.findAllByCoach(coach);

        assertEquals(3, lectures.size());
        assertEquals(3L, lectures.get(0).getId());
        assertEquals(1L, lectures.get(0).getLectureInformation().getId());
        assertEquals(LocalDateTime.of(2022, 6, 10, 22, 44, 10), lectures.get(0).getStartAt());
        assertEquals(LocalDateTime.of(2022, 6, 10, 23, 44, 4), lectures.get(0).getEndAt());
        assertEquals(Lecture.State.ON_BOARD, lectures.get(0).getState());
        assertFalse(lectures.get(0).isReviewWritten());
        assertTrue(lectures.get(0).isActive());
        assertEquals(LocalDateTime.of(2022, 6, 8, 22, 44, 12), lectures.get(0).getCreatedAt());
        assertEquals(LocalDateTime.of(2022, 6, 8, 22, 44, 12), lectures.get(0).getUpdatedAt());
        assertEquals(2, lectures.get(0).getForms().size());
        assertEquals(6, lectures.get(0).getForms().get(0).getId());
        assertEquals(5, lectures.get(0).getForms().get(1).getId());

        assertEquals(2L, lectures.get(1).getId());
        assertEquals(1L, lectures.get(1).getLectureInformation().getId());
        assertEquals(LocalDateTime.of(2022, 6, 9, 22, 44, 10), lectures.get(1).getStartAt());
        assertEquals(LocalDateTime.of(2022, 6, 9, 23, 44, 4), lectures.get(1).getEndAt());
        assertEquals(Lecture.State.ON_GOING, lectures.get(1).getState());
        assertFalse(lectures.get(1).isReviewWritten());
        assertTrue(lectures.get(1).isActive());
        assertEquals(LocalDateTime.of(2022, 6, 8, 22, 44, 11), lectures.get(1).getCreatedAt());
        assertEquals(LocalDateTime.of(2022, 6, 8, 22, 44, 11), lectures.get(1).getUpdatedAt());
        assertEquals(1, lectures.get(1).getForms().size());
        assertEquals(2, lectures.get(1).getForms().get(0).getId());

        assertEquals(1L, lectures.get(2).getId());
        assertEquals(1L, lectures.get(2).getLectureInformation().getId());
        assertEquals(LocalDateTime.of(2022, 6, 8, 22, 44, 10), lectures.get(2).getStartAt());
        assertEquals(LocalDateTime.of(2022, 6, 8, 23, 44, 4), lectures.get(2).getEndAt());
        assertEquals(Lecture.State.DONE, lectures.get(2).getState());
        assertTrue(lectures.get(2).isReviewWritten());
        assertTrue(lectures.get(2).isActive());
        assertEquals(LocalDateTime.of(2022, 6, 8, 22, 44, 10), lectures.get(2).getCreatedAt());
        assertEquals(LocalDateTime.of(2022, 6, 8, 22, 44, 10), lectures.get(2).getUpdatedAt());
        assertEquals(2, lectures.get(2).getForms().size());
        assertEquals(3, lectures.get(2).getForms().get(0).getId());
        assertEquals(1, lectures.get(2).getForms().get(1).getId());
    }

    @Test
    void 코치와_상태로_클래스_찾기_테스트() {
        Coach coach = new TestCoachEntityFactory().createEntity(1L);

        List<Lecture> lectures = lectureRepository.findAllByCoachAndState(coach, Lecture.State.ON_BOARD);

        assertEquals(1, lectures.size());
        assertEquals(3L, lectures.get(0).getId());
        assertEquals(1L, lectures.get(0).getLectureInformation().getId());
        assertEquals(LocalDateTime.of(2022, 6, 10, 22, 44, 10), lectures.get(0).getStartAt());
        assertEquals(LocalDateTime.of(2022, 6, 10, 23, 44, 4), lectures.get(0).getEndAt());
        assertEquals(Lecture.State.ON_BOARD, lectures.get(0).getState());
        assertFalse(lectures.get(0).isReviewWritten());
        assertTrue(lectures.get(0).isActive());
        assertEquals(LocalDateTime.of(2022, 6, 8, 22, 44, 12), lectures.get(0).getCreatedAt());
        assertEquals(LocalDateTime.of(2022, 6, 8, 22, 44, 12), lectures.get(0).getUpdatedAt());
        assertEquals(2, lectures.get(0).getForms().size());
        assertEquals(6, lectures.get(0).getForms().get(0).getId());
        assertEquals(5, lectures.get(0).getForms().get(1).getId());
    }
}