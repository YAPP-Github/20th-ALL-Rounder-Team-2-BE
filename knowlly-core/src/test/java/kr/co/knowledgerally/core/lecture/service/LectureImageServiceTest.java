package kr.co.knowledgerally.core.lecture.service;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import kr.co.knowledgerally.core.annotation.KnowllyDataTest;
import kr.co.knowledgerally.core.lecture.entity.LectureImage;
import kr.co.knowledgerally.core.lecture.util.TestLectureImageEntityFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;

@KnowllyDataTest
@Import({LectureImageService.class})
@DatabaseSetup({
        "classpath:dbunit/entity/user.xml",
        "classpath:dbunit/entity/coach.xml",
        "classpath:dbunit/entity/category.xml",
        "classpath:dbunit/entity/lecture_information.xml",
        "classpath:dbunit/entity/lecture_image.xml",
})
public class LectureImageServiceTest {

    @Autowired
    LectureImageService lectureImageService;

    TestLectureImageEntityFactory lectureImageEntityFactory = new TestLectureImageEntityFactory();

    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/crud/lecture_image_insert_test.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    void 클래스_이미지_등록() {
        List<LectureImage> lectureImageList = new ArrayList<>();
        lectureImageList.add(lectureImageEntityFactory.createEntity(8L));

        lectureImageService.saveAllLectureImage(lectureImageList);

    }
}
