package kr.co.knowledgerally.api.lecture.service;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import kr.co.knowledgerally.core.annotation.KnowllyDataTest;
import kr.co.knowledgerally.core.lecture.entity.Category;
import kr.co.knowledgerally.core.lecture.entity.LectureInformation;
import kr.co.knowledgerally.core.lecture.util.TestCategoryEntityFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@KnowllyDataTest
@Import(LectureInformationService.class)
@DatabaseSetup({
        "classpath:dbunit/entity/lecture_information.xml",
        "classpath:dbunit/entity/coach.xml",
        "classpath:dbunit/entity/category.xml"
})
public class LectureInformationServiceTest {
    @Autowired
    LectureInformationService lectureInformationService;

    TestCategoryEntityFactory testCategoryEntityFactory = new TestCategoryEntityFactory();

    @Test
    void 클래스_info_목록_조회() {
        List<LectureInformation> lectureInformationList = lectureInformationService.findAll();

        assertEquals(lectureInformationList.get(0).getTopic(), "마케팅 수업");
        assertEquals(lectureInformationList.get(1).getTopic(), "자바 개발");
        assertEquals(lectureInformationList.get(2).getTopic(), "그래픽 디자인");
        assertEquals(lectureInformationList.get(3).getTopic(), "좋은 기획이란 무엇인가");
        assertEquals(lectureInformationList.get(4).getTopic(), "요리 클래스");
    }

    @Test
    void 클래스_info_카테고리로_목록_조회() {
        Category category = testCategoryEntityFactory.createEntity(1L);
        List<LectureInformation> lectureInformationList = lectureInformationService.findAllByCategory(category);

        assertEquals(lectureInformationList.get(0).getTopic(), "좋은 기획이란 무엇인가");
    }
}
