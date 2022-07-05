package kr.co.knowledgerally.core.lecture.repository;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import kr.co.knowledgerally.core.annotation.KnowllyDataTest;
import kr.co.knowledgerally.core.lecture.entity.Category;
import kr.co.knowledgerally.core.lecture.entity.LectureInformation;
import kr.co.knowledgerally.core.lecture.util.TestCategoryEntityFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@KnowllyDataTest
@DatabaseSetup({
        "classpath:dbunit/entity/user.xml",
        "classpath:dbunit/entity/coach.xml",
        "classpath:dbunit/entity/category.xml",
        "classpath:dbunit/entity/lecture_information.xml",
        "classpath:dbunit/entity/lecture_image.xml"
})
public class LectureInformationRepositoryTest {

    @Autowired
    LectureInformationRepository lectureInformationRepository;

    TestCategoryEntityFactory testCategoryEntityFactory = new TestCategoryEntityFactory();

    @Test
    void 클래스_info_목록_활성화_여부로_검색() {
        Page<LectureInformation> lectureInformations = lectureInformationRepository.findAllTop10ByIsActiveOrderByIdDesc(true, PageRequest.of(0, 2));

        assertEquals(2, lectureInformations.getNumberOfElements());
        assertEquals(4, lectureInformations.getTotalElements());
        assertEquals(2, lectureInformations.getTotalPages());
        assertEquals(2, lectureInformations.getContent().size());
        assertEquals(lectureInformations.getContent().get(0).getTopic(), "요리 클래스");
        assertEquals(
                lectureInformations
                        .getContent()
                        .get(0)
                        .getLectureImages().size(), 1
        );
        assertEquals(lectureInformations.getContent().get(1).getTopic(), "그래픽 디자인");
        assertEquals(
                lectureInformations
                        .getContent()
                        .get(1)
                        .getLectureImages().size(), 1
        );
    }

    @Test
    void 클래스_info_목록_카테고리와_활성화_여부로_검색() {
        Category category = testCategoryEntityFactory.createEntity(3L);
        Page<LectureInformation> lectureInformations = lectureInformationRepository.findAllByCategoryAndIsActiveOrderByIdDesc(category, true, PageRequest.of(0, 2));

        assertEquals(lectureInformations.getContent().get(0).getTopic(), "자바 개발");
    }

    @Test
    void 클래스_info_목록_카테고리_이름과_활성화_여부로_검색() {
        List<LectureInformation> lectureInformationList = lectureInformationRepository.findAllByCategoryNameAndIsActiveOrderByIdDesc(Category.Name.ETC, true);

        assertEquals(lectureInformationList.get(0).getTopic(), "요리 클래스");
    }

    @Test
    void 클래스_info_목록_Topic과_활성화_여부로_검색() {
        List<LectureInformation> lectureInformationList = lectureInformationRepository.findAllByTopicContainingAndIsActiveOrderByIdDesc("수업", true);

        assertEquals(lectureInformationList.get(0).getTopic(), "마케팅 수업");
    }

    @Test
    void 클래스_info_활성화_여부로_상세조회() {
        LectureInformation lectureInformation = lectureInformationRepository.findByIdAndIsActive(1L, true).orElseThrow();

        assertEquals(lectureInformation.getTopic(), "마케팅 수업");
    }
}
