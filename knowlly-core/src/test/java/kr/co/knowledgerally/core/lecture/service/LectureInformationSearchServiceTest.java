package kr.co.knowledgerally.core.lecture.service;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import kr.co.knowledgerally.core.annotation.KnowllyDataTest;
import kr.co.knowledgerally.core.lecture.entity.LectureInformation;
import kr.co.knowledgerally.core.lecture.service.LectureInformationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@KnowllyDataTest
@Import({LectureInformationSearchService.class, LectureInformationService.class, CategoryService.class})
@DatabaseSetup({
        "classpath:dbunit/entity/user.xml",
        "classpath:dbunit/entity/coach.xml",
        "classpath:dbunit/entity/category.xml",
        "classpath:dbunit/entity/lecture_information.xml",
        "classpath:dbunit/entity/lecture_image.xml"
})
public class LectureInformationSearchServiceTest {
    @Autowired
    LectureInformationSearchService lectureInformationSearchService;

    @Test
    void 클래스_info_keyword로_검색() {
        Page<LectureInformation> lectureInformations = lectureInformationSearchService.searchAllByKeyword("마케팅", PageRequest.of(0, 2));

        assertEquals(1, lectureInformations.getNumberOfElements());
        assertEquals(1, lectureInformations.getTotalElements());
        assertEquals(1, lectureInformations.getTotalPages());
        assertEquals(1, lectureInformations.getContent().size());
        assertEquals("마케팅 수업", lectureInformations.getContent().get(0).getTopic());
        assertEquals("마케팅", lectureInformations.getContent().get(0).getCategory().getCategoryName());
    }
}
