package kr.co.knowledgerally.core.lecture.service;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import kr.co.knowledgerally.core.annotation.KnowllyDataTest;
import kr.co.knowledgerally.core.lecture.entity.Category;
import kr.co.knowledgerally.core.lecture.entity.LectureInformation;
import kr.co.knowledgerally.core.lecture.util.TestCategoryEntityFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@KnowllyDataTest
@Import(LectureInformationService.class)
@DatabaseSetup({
        "classpath:dbunit/entity/user.xml",
        "classpath:dbunit/entity/coach.xml",
        "classpath:dbunit/entity/category.xml",
        "classpath:dbunit/entity/lecture_information.xml",
        "classpath:dbunit/entity/lecture_image.xml"
})
public class LectureInformationServiceTest {
    @Autowired
    LectureInformationService lectureInformationService;

    TestCategoryEntityFactory testCategoryEntityFactory = new TestCategoryEntityFactory();

    @Test
    void 클래스_info_목록_조회() {
        Page<LectureInformation> lectureInformations = lectureInformationService.findAllWithPageable(PageRequest.of(0, 2));

        assertEquals(2, lectureInformations.getNumberOfElements());
        assertEquals(4, lectureInformations.getTotalElements());
        assertEquals(2, lectureInformations.getTotalPages());
        assertEquals(2, lectureInformations.getContent().size());
        assertEquals(lectureInformations.getContent().get(0).getTopic(), "요리 클래스");
        assertEquals(
                lectureInformations
                        .getContent()
                        .get(0)
                        .getLectureImageSet().size(), 1
        );
        assertEquals(lectureInformations.getContent().get(1).getTopic(), "그래픽 디자인");
        assertEquals(
                lectureInformations
                        .getContent()
                        .get(1)
                        .getLectureImageSet().size(), 1
        );
    }

    @Test
    void 클래스_info_카테고리로_목록_조회() {
        Category category = testCategoryEntityFactory.createEntity(3L);
        Page<LectureInformation> lectureInformations = lectureInformationService.findAllByCategoryWithPageable(category, PageRequest.of(0, 2));

        assertEquals(lectureInformations.getContent().get(0).getTopic(), "자바 개발");
    }

    @Test
    void 클래스_info_카테고리_이름으로_검색() {
        List<LectureInformation> lectureInformationList = lectureInformationService.searchAllByCategoryName("기타");

        assertEquals(lectureInformationList.get(0).getTopic(), "요리 클래스");
    }

    @Test
    void 클래스_info_제목으로_검색() {
        List<LectureInformation> lectureInformationList = lectureInformationService.searchAllByTopic("자바");

        assertEquals(lectureInformationList.get(0).getTopic(), "자바 개발");
    }
}
