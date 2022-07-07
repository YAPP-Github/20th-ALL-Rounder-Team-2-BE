package kr.co.knowledgerally.api.lecture.component;

import kr.co.knowledgerally.api.lecture.dto.FormDto;
import kr.co.knowledgerally.api.lecture.util.TestFormDtoFactory;
import kr.co.knowledgerally.core.core.util.TestEntityFactory;
import kr.co.knowledgerally.core.lecture.entity.Form;
import kr.co.knowledgerally.core.lecture.util.TestFormEntityFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FormMapperTest {
    @Autowired
    FormMapper formMapper;

    @Test
    void 엔티티에서_DTO변환_테스트() {
        Form form = new TestFormEntityFactory().createEntity(1L, 1L, 4L);

        FormDto.ReadOnly formDto = formMapper.toDto(form);

        assertEquals(1L, formDto.getId());
        assertEquals(1L, formDto.getLecture().getId());
        assertEquals(4L, formDto.getUser().getId());
        assertEquals("테스트1의 신청 내용", formDto.getContent());
        assertEquals(Form.State.REQUEST, formDto.getState());
        assertEquals("2022-06-16T22:48:17", formDto.getExpirationDate());
    }

    @Test
    void DTO에서_엔티티변환_테스트() {
        FormDto formDto = new TestFormDtoFactory().createDto(1L);

        Form form = formMapper.toEntity(formDto);

        assertEquals("테스트1의 신청 내용", form.getContent());
    }
}