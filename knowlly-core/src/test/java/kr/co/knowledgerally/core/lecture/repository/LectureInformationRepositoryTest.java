package kr.co.knowledgerally.core.lecture.repository;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import kr.co.knowledgerally.core.annotation.KnowllyDataTest;
import kr.co.knowledgerally.core.coach.entity.Coach;
import kr.co.knowledgerally.core.coach.util.TestCoachEntityFactory;
import kr.co.knowledgerally.core.lecture.entity.Category;
import kr.co.knowledgerally.core.lecture.entity.Lecture;
import kr.co.knowledgerally.core.lecture.entity.LectureInformation;
import kr.co.knowledgerally.core.lecture.util.TestCategoryEntityFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@KnowllyDataTest
@DatabaseSetup({
        "classpath:dbunit/entity/user.xml",
        "classpath:dbunit/entity/coach.xml",
        "classpath:dbunit/entity/category.xml",
        "classpath:dbunit/entity/lecture_information.xml",
        "classpath:dbunit/entity/lecture.xml",
        "classpath:dbunit/entity/form.xml",
        "classpath:dbunit/entity/lecture_image.xml",
        "classpath:dbunit/entity/tag.xml"
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
        assertEquals("요리 클래스", lectureInformations.getContent().get(0).getTopic());
        assertEquals(1,
                lectureInformations
                        .getContent()
                        .get(0)
                        .getLectureImages().size()
        );
        assertEquals("그래픽 디자인", lectureInformations.getContent().get(1).getTopic());
        assertEquals(1,
                lectureInformations
                        .getContent()
                        .get(1)
                        .getLectureImages().size()
        );
    }

    @Test
    void 클래스_info_목록_카테고리와_활성화_여부로_검색() {
        Category category = testCategoryEntityFactory.createEntity(3L);
        Page<LectureInformation> lectureInformations = lectureInformationRepository.findAllByCategoryAndIsActiveOrderByIdDesc(category, true, PageRequest.of(0, 2));

        assertEquals("자바 개발", lectureInformations.getContent().get(0).getTopic());
    }

    @Test
    void 클래스_info_목록_카테고리_이름과_활성화_여부로_검색() {
        List<LectureInformation> lectureInformationList = lectureInformationRepository.findAllByCategoryNameAndIsActiveOrderByIdDesc(Category.Name.ETC, true);

        assertEquals("요리 클래스", lectureInformationList.get(0).getTopic());
    }

    @Test
    void 클래스_info_목록_Topic과_활성화_여부로_검색() {
        List<LectureInformation> lectureInformationList = lectureInformationRepository.findAllByTopicContainingAndIsActiveOrderByIdDesc("수업", true);

        assertEquals("마케팅 수업", lectureInformationList.get(0).getTopic());
    }

    @Test
    void 클래스_info_활성화_여부로_상세조회() {
        LectureInformation lectureInformation = lectureInformationRepository.findByIdAndIsActive(1L, true).orElseThrow();

        assertEquals( "마케팅 수업", lectureInformation.getTopic());
    }

    @Test
    void 클래스_info_목록_코치로_조회() {
        Coach coach = new TestCoachEntityFactory().createEntity(1L);

        List<LectureInformation> lectureInformations = lectureInformationRepository.findAllByCoach(coach);

        assertEquals(2, lectureInformations.size());

        assertEquals(5, lectureInformations.get(0).getId());
        assertEquals(0, lectureInformations.get(0).getLectures().size());
        assertEquals(1, lectureInformations.get(0).getLectureImages().size());
        assertEquals("http://lecture5.img.url", lectureInformations.get(0).getLectureImages().stream()
                .findFirst().orElseThrow().getLectureImgUrl());
        assertEquals(0, lectureInformations.get(0).getTags().size());

        assertEquals(1, lectureInformations.get(1).getId());
        assertEquals(1, lectureInformations.get(1).getLectureImages().size());
        assertEquals(2, lectureInformations.get(1).getTags().size());

        List<Lecture> lectures = lectureInformations.get(1).getLectures();
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
    void 클래스_info_목록_코치와_상태로_조회() {
        Coach coach = new TestCoachEntityFactory().createEntity(1L);

        List<LectureInformation> lectureInformations = lectureInformationRepository.findAllByCoachAndState(coach, Lecture.State.ON_BOARD);

        assertEquals(1, lectureInformations.size());

        assertEquals(1, lectureInformations.get(0).getId());
        assertEquals(1, lectureInformations.get(0).getLectureImages().size());
        assertEquals(2, lectureInformations.get(0).getTags().size());

        List<Lecture> lectures = lectureInformations.get(0).getLectures();

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
