package kr.co.knowledgerally.core.lecture.service;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import kr.co.knowledgerally.core.annotation.KnowllyDataTest;
import kr.co.knowledgerally.core.coach.service.CoachService;
import kr.co.knowledgerally.core.core.exception.ResourceNotFoundException;
import kr.co.knowledgerally.core.lecture.entity.Category;
import kr.co.knowledgerally.core.lecture.entity.LectureInformation;
import kr.co.knowledgerally.core.lecture.entity.Tag;
import kr.co.knowledgerally.core.lecture.util.TestCategoryEntityFactory;
import kr.co.knowledgerally.core.lecture.util.TestLectureInformationEntityFactory;
import kr.co.knowledgerally.core.lecture.util.TestTagEntityFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@KnowllyDataTest
@Import({LectureInformationService.class, CoachService.class, CategoryService.class})
@DatabaseSetup({
        "classpath:dbunit/entity/user.xml",
        "classpath:dbunit/entity/coach.xml",
        "classpath:dbunit/entity/category.xml",
        "classpath:dbunit/entity/lecture_information.xml",
        "classpath:dbunit/entity/lecture_image.xml",
        "classpath:dbunit/entity/tag.xml"
})
public class LectureInformationServiceTest {
    @Autowired
    LectureInformationService lectureInformationService;

    TestLectureInformationEntityFactory testLectureInformationEntityFactory = new TestLectureInformationEntityFactory();
    TestCategoryEntityFactory testCategoryEntityFactory = new TestCategoryEntityFactory();
    TestTagEntityFactory testTagEntityFactory = new TestTagEntityFactory();

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

    @Test
    void 클래스_info_ID로_검색() {
        LectureInformation lectureInformation = lectureInformationService.findById(1L);

        assertEquals(1L, lectureInformation.getId());
        assertEquals(1L, lectureInformation.getCoach().getId());
        assertEquals(4L, lectureInformation.getCategory().getId());
        assertEquals("마케팅 수업", lectureInformation.getTopic());
        assertEquals("효과적인 마케팅에 대해 배웁니다", lectureInformation.getIntroduce());
        assertEquals(2, lectureInformation.getLectureImageSet().size());
        assertEquals(1, lectureInformation.getPrice());
        assertTrue(lectureInformation.isActive());
        assertEquals(LocalDateTime.of(2022, 6, 13, 22, 39, 40), lectureInformation.getCreatedAt());
        assertEquals(LocalDateTime.of(2022, 6, 13, 22, 39, 54), lectureInformation.getUpdatedAt());
    }

    @Test
    void 클래스_info_ID로_검색_없으면_throw() {
        assertThrows(ResourceNotFoundException.class, () -> {
            lectureInformationService.findById(9999L);
        });
    }

    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/crud/lecture_information_insert_test.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    void 클래스_info_등록() {
        LectureInformation lectureInformation = testLectureInformationEntityFactory.createEntity(6L, 2L, 5L,3L, 2);
        lectureInformationService.saveLectureInformation(5L, lectureInformation, lectureInformation.getCoach().getUser());
    }

    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/crud/tag_insert_test.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    void 태그_등록() {
        Set<Tag> tagSet = new LinkedHashSet<>();
        tagSet.add(testTagEntityFactory.createEntity(6L, 3L));
        tagSet.add(testTagEntityFactory.createEntity(7L, 3L));

        LectureInformation lectureInformation = lectureInformationService.findById(3L);
        lectureInformation.setTagSet(tagSet);
        lectureInformationService.saveLectureInformation(5L, lectureInformation, lectureInformation.getCoach().getUser());
    }
}
