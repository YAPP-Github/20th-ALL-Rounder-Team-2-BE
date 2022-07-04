package kr.co.knowledgerally.api.lecture.component;

import kr.co.knowledgerally.api.lecture.dto.CoachLectureDto;
import kr.co.knowledgerally.api.lecture.dto.FormDto;
import kr.co.knowledgerally.core.lecture.entity.Form;
import kr.co.knowledgerally.core.lecture.entity.Lecture;
import kr.co.knowledgerally.core.lecture.util.TestFormEntityFactory;
import kr.co.knowledgerally.core.lecture.util.TestLectureEntityFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CoachLectureMapperTest {
    @Autowired
    CoachLectureMapper coachLectureMapper;

    TestFormEntityFactory testFormEntityFactory = new TestFormEntityFactory();

    @Test
    void 엔티티에서_DTO변환_테스트() {
        Lecture lecture = new TestLectureEntityFactory().createEntity(1L);
        lecture.setForms(List.of(testFormEntityFactory.createEntity(1L),
                testFormEntityFactory.createEntity(2L)));

        CoachLectureDto coachLectureDto = coachLectureMapper.toDto(lecture);

        assertEquals(1L, coachLectureDto.getLecture().getId());
        assertEquals("2022-06-15T10:30:50", coachLectureDto.getLecture().getStartAt());
        assertEquals("2022-06-15T11:30:50", coachLectureDto.getLecture().getEndAt());
        assertEquals(Lecture.State.ON_BOARD, coachLectureDto.getLecture().getState());
        assertFalse(coachLectureDto.getLecture().isReviewWritten());

        assertEquals("테스트1 제목", coachLectureDto.getLectureInformation().getTopic());
        assertEquals("안녕하세요. 테스트1 입니다.", coachLectureDto.getLectureInformation().getIntroduce());
        assertEquals(1, coachLectureDto.getLectureInformation().getPrice());
        assertEquals(1, coachLectureDto.getLectureInformation().getCoach().getId());
        assertEquals("테스트 카테고리1", coachLectureDto.getLectureInformation().getCategory().getCategoryName());
        assertEquals(2, coachLectureDto.getLectureInformation().getLectureImageSet().size());

        assertEquals(2, coachLectureDto.getForms().size());
        assertEquals(1L, coachLectureDto.getForms().get(0).getId());
        assertEquals(1L, coachLectureDto.getForms().get(0).getLecture().getId());
        assertEquals(1L, coachLectureDto.getForms().get(0).getUser().getId());
        assertEquals("테스트1의 신청 내용", coachLectureDto.getForms().get(0).getContent());
        assertEquals(Form.State.REQUEST, coachLectureDto.getForms().get(0).getState());
        assertEquals(2L, coachLectureDto.getForms().get(1).getId());
        assertEquals(1L, coachLectureDto.getForms().get(1).getLecture().getId());
        assertEquals(1L, coachLectureDto.getForms().get(1).getUser().getId());
        assertEquals("테스트2의 신청 내용", coachLectureDto.getForms().get(1).getContent());
        assertEquals(Form.State.REQUEST, coachLectureDto.getForms().get(1).getState());
    }
}