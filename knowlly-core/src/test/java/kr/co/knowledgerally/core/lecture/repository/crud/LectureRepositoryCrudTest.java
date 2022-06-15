package kr.co.knowledgerally.core.lecture.repository.crud;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import kr.co.knowledgerally.core.core.repository.AbstractRepositoryCrudTest;
import kr.co.knowledgerally.core.lecture.entity.Lecture;
import kr.co.knowledgerally.core.lecture.repository.LectureRepository;
import kr.co.knowledgerally.core.lecture.util.TestLectureEntityFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DatabaseSetup({
        "classpath:dbunit/entity/user.xml",
        "classpath:dbunit/entity/coach.xml",
        "classpath:dbunit/entity/category.xml",
        "classpath:dbunit/entity/lecture_information.xml",
        "classpath:dbunit/entity/lecture.xml",
})
class LectureRepositoryCrudTest extends AbstractRepositoryCrudTest {

    @Autowired
    LectureRepository lectureRepository;

    TestLectureEntityFactory testLectureEntityFactory = new TestLectureEntityFactory();

    @Override
    @Test
    protected void selectTest() {
        Lecture lecture = lectureRepository.findById(1L).orElseThrow();

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

    @Override
    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/crud/lecture_insert_test.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    protected void insertTest() {
        Lecture lecture = testLectureEntityFactory.createEntity(7L, 3L);
        lectureRepository.saveAndFlush(lecture);
    }

    @Override
    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/crud/lecture_update_test.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    protected void updateTest() {
        Lecture lecture = lectureRepository.findById(2L).orElseThrow();
        lecture.setReviewWritten(true);
        entityManager.flush();
    }

    @Override
    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/crud/lecture_delete_test.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    protected void deleteTest() {
        lectureRepository.deleteById(1L);
        entityManager.flush();
    }
}