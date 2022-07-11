package kr.co.knowledgerally.core.lecture.repository.crud;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import kr.co.knowledgerally.core.core.repository.AbstractRepositoryCrudTest;
import kr.co.knowledgerally.core.lecture.entity.LectureInformation;
import kr.co.knowledgerally.core.lecture.repository.LectureInformationRepository;
import kr.co.knowledgerally.core.lecture.util.TestLectureInformationEntityFactory;
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
        "classpath:dbunit/entity/lecture_image.xml"
})
class LectureInformationRepositoryCrudTest extends AbstractRepositoryCrudTest {

    @Autowired
    LectureInformationRepository lectureInformationRepository;

    TestLectureInformationEntityFactory testLectureInformationEntityFactory = new TestLectureInformationEntityFactory();

    @Override
    @Test
    protected void selectTest() {
        LectureInformation lectureInformation = lectureInformationRepository.findById(1L).orElseThrow();

        assertEquals(1L, lectureInformation.getId());
        assertEquals(1L, lectureInformation.getCoach().getId());
        assertEquals(4L, lectureInformation.getCategory().getId());
        assertEquals("마케팅 수업", lectureInformation.getTopic());
        assertEquals("효과적인 마케팅에 대해 배웁니다", lectureInformation.getIntroduce());
        assertEquals(2, lectureInformation.getLectureImages().size());
        assertEquals(1, lectureInformation.getPrice());
        assertTrue(lectureInformation.isActive());
        assertEquals(LocalDateTime.of(2022, 6, 13, 22, 39, 40), lectureInformation.getCreatedAt());
        assertEquals(LocalDateTime.of(2022, 6, 13, 22, 39, 54), lectureInformation.getUpdatedAt());
    }

    @Override
    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/crud/lecture_information_insert_test.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    protected void insertTest() {
        LectureInformation lectureInformation = testLectureInformationEntityFactory.createEntity(6L, 2, 5, 3, 2);
        lectureInformationRepository.saveAndFlush(lectureInformation);
    }

    @Override
    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/crud/lecture_information_update_test.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    protected void updateTest() {
        LectureInformation lectureInformation = lectureInformationRepository.findById(1L).orElseThrow();
        lectureInformation.setTopic("주제 변경");
        entityManager.flush();
    }

    @Override
    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/crud/lecture_information_delete_test.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    protected void deleteTest() {
        lectureInformationRepository.deleteById(1L);
        entityManager.flush();
    }
}