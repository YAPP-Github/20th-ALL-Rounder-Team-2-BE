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
import kr.co.knowledgerally.core.user.entity.User;
import kr.co.knowledgerally.core.user.util.TestUserEntityFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
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
    TestUserEntityFactory testUserEntityFactory = new TestUserEntityFactory();

    @Test
    void ?????????_info_??????_??????() {
        Page<LectureInformation> lectureInformations = lectureInformationService.findAllWithPageable(PageRequest.of(0, 2));

        assertEquals(2, lectureInformations.getNumberOfElements());
        assertEquals(4, lectureInformations.getTotalElements());
        assertEquals(2, lectureInformations.getTotalPages());
        assertEquals(2, lectureInformations.getContent().size());
        assertEquals(lectureInformations.getContent().get(0).getTopic(), "?????? ?????????");
        assertEquals(
                lectureInformations
                        .getContent()
                        .get(0)
                        .getLectureImages().size(), 1
        );
        assertEquals(lectureInformations.getContent().get(1).getTopic(), "????????? ?????????");
        assertEquals(
                lectureInformations
                        .getContent()
                        .get(1)
                        .getLectureImages().size(), 1
        );
    }

    @Test
    void ?????????_info_???????????????_??????_??????() {
        Category category = testCategoryEntityFactory.createEntity(3L);
        Page<LectureInformation> lectureInformations = lectureInformationService.findAllByCategoryWithPageable(category, PageRequest.of(0, 2));

        assertEquals(lectureInformations.getContent().get(0).getTopic(), "?????? ??????");
    }

    @Test
    void ?????????_info_????????????_????????????_??????() {
        List<LectureInformation> lectureInformationList = lectureInformationService.searchAllByCategoryName("ETC");

        assertEquals(lectureInformationList.get(0).getTopic(), "?????? ?????????");
    }

    @Test
    void ?????????_info_????????????_??????() {
        List<LectureInformation> lectureInformationList = lectureInformationService.searchAllByTopic("??????");

        assertEquals(lectureInformationList.get(0).getTopic(), "?????? ??????");
    }

    @Test
    void ?????????_info_ID???_??????() {
        LectureInformation lectureInformation = lectureInformationService.findById(1L);

        assertEquals(1L, lectureInformation.getId());
        assertEquals(1L, lectureInformation.getCoach().getId());
        assertEquals(4L, lectureInformation.getCategory().getId());
        assertEquals("????????? ??????", lectureInformation.getTopic());
        assertEquals("???????????? ???????????? ?????? ????????????", lectureInformation.getIntroduce());
        assertEquals(2, lectureInformation.getLectureImages().size());
        assertEquals(1, lectureInformation.getPrice());
        assertTrue(lectureInformation.isActive());
        assertEquals(LocalDateTime.of(2022, 6, 13, 22, 39, 40), lectureInformation.getCreatedAt());
        assertEquals(LocalDateTime.of(2022, 6, 13, 22, 39, 54), lectureInformation.getUpdatedAt());
    }

    @Test
    void ?????????_info_ID???_??????_?????????_throw() {
        assertThrows(ResourceNotFoundException.class, () -> {
            lectureInformationService.findById(9999L);
        });
    }

    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/crud/lecture_information_insert_test.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    @ExpectedDatabase(value = "classpath:dbunit/expected/crud/tag_insert_test_2.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    void ?????????_info???_?????????_??????_??????() {
        Set<Tag> tagSet = new LinkedHashSet<>();

        tagSet.add(Tag.builder().content("????????? ??????6").build());
        tagSet.add(Tag.builder().content("????????? ??????7").build());

        LectureInformation lectureInformation = testLectureInformationEntityFactory.createEntity(6L, 2L, 6L,3L, 2);
        lectureInformation.setTags(tagSet);
        lectureInformation.setCategory(Category.builder().name(Category.Name.LANGUAGE).build());
        lectureInformationService.saveLectureInformation(lectureInformation, lectureInformation.getCoach().getUser());
    }

    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/crud/lecture_information_insert_without_coach_test.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    void ?????????_???_?????????_info???_?????????_??????_??????() {

        LectureInformation lectureInformation = testLectureInformationEntityFactory.createEntityWithoutCoach(7L, 2L,2);
        lectureInformation.setCategory(Category.builder().name(Category.Name.LANGUAGE).build());
        User user = testUserEntityFactory.createEntity(2L);
        assertFalse(user.isCoach());
        LectureInformation newLectureInfo = lectureInformationService.saveLectureInformation(lectureInformation, user);
        assertTrue(newLectureInfo.getCoach().getUser().isCoach());
    }
}
