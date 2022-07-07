package kr.co.knowledgerally.core.lecture.repository.crud;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import kr.co.knowledgerally.core.core.repository.AbstractRepositoryCrudTest;
import kr.co.knowledgerally.core.lecture.entity.LectureImage;
import kr.co.knowledgerally.core.lecture.repository.LectureImageRepository;
import kr.co.knowledgerally.core.lecture.util.TestLectureImageEntityFactory;
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
        "classpath:dbunit/entity/lecture_image.xml",
})
class LectureImageRepositoryCrudTest extends AbstractRepositoryCrudTest {

    @Autowired
    LectureImageRepository lectureImageRepository;

    TestLectureImageEntityFactory testLectureImageEntityFactory = new TestLectureImageEntityFactory();

    @Override
    @Test
    protected void selectTest() {
        LectureImage lectureImage = lectureImageRepository.findById(1L).orElseThrow();

        assertEquals(1L, lectureImage.getId());
        assertEquals(1L, lectureImage.getLectureInformation().getId());
        assertEquals("http://lecture1.img.url", lectureImage.getLectureImgUrl());
        assertTrue(lectureImage.isActive());
        assertEquals(LocalDateTime.of(2022, 6, 13, 21, 26, 58), lectureImage.getCreatedAt());
        assertEquals(LocalDateTime.of(2022, 6, 13, 21, 27, 01), lectureImage.getUpdatedAt());
    }

    @Override
    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/crud/lecture_image_insert_test.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    protected void insertTest() {
        LectureImage lectureImage = testLectureImageEntityFactory.createEntity(8);
        lectureImageRepository.saveAndFlush(lectureImage);
    }

    @Override
    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/crud/lecture_image_update_test.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    protected void updateTest() {
        LectureImage lectureImage = lectureImageRepository.findById(1L).orElseThrow();
        lectureImage.setLectureImgUrl("http://lecturechanged.img.url");
        entityManager.flush();
    }

    @Override
    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/crud/lecture_image_delete_test.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    protected void deleteTest() {
        lectureImageRepository.deleteById(1L);
        entityManager.flush();
    }
}