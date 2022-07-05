package kr.co.knowledgerally.api.lecture.component;

import kr.co.knowledgerally.api.lecture.dto.LectureInformationDto;
import kr.co.knowledgerally.api.lecture.dto.UserLectureDto;
import kr.co.knowledgerally.core.lecture.entity.Category;
import kr.co.knowledgerally.core.lecture.entity.Form;
import kr.co.knowledgerally.core.lecture.util.TestFormEntityFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserLectureMapperTest {
    @Autowired
    UserLectureMapper userLectureMapper;

    @Test
    void 엔티티에서_DTO변환_테스트() {
        Form form = new TestFormEntityFactory().createEntity(1L);

        UserLectureDto userLectureDto = userLectureMapper.toDto(form);

        assertEquals(1L, userLectureDto.getForm().getId());
        assertEquals(1L, userLectureDto.getForm().getLecture().getId());
        assertEquals(1L, userLectureDto.getForm().getUser().getId());
        assertEquals("테스트1의 신청 내용", userLectureDto.getForm().getContent());
        assertEquals(Form.State.REQUEST, userLectureDto.getForm().getState());

        LectureInformationDto.ReadOnly lectureInformationDto = userLectureDto.getLectureInformation();
        assertEquals("테스트1 제목", lectureInformationDto.getTopic());
        assertEquals("안녕하세요. 테스트1 입니다.", lectureInformationDto.getIntroduce());
        assertEquals(1, lectureInformationDto.getPrice());
        assertEquals(1, lectureInformationDto.getCoach().getId());
        assertEquals(Category.Name.ETC, lectureInformationDto.getCategory());
        assertEquals(2, lectureInformationDto.getLectureImages().size());
    }
}