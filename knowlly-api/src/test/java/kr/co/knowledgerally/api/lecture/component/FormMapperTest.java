package kr.co.knowledgerally.api.lecture.component;

import kr.co.knowledgerally.api.lecture.dto.FormDto;
import kr.co.knowledgerally.api.lecture.util.TestFormDtoFactory;
import kr.co.knowledgerally.core.core.util.TestEntityFactory;
import kr.co.knowledgerally.core.lecture.entity.Form;
import kr.co.knowledgerally.core.lecture.util.TestFormEntityFactory;
import kr.co.knowledgerally.core.user.entity.UserImage;
import kr.co.knowledgerally.core.user.service.UserImageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class FormMapperTest {
    @Autowired
    FormMapper formMapper;

    @MockBean
    UserImageService userImageService;

    @Test
    void 엔티티에서_DTO변환_테스트() {
        when(userImageService.findByUser(any())).thenReturn(UserImage.builder().userImgUrl("http://test4.img.url").build());
        Form form = new TestFormEntityFactory().createEntity(1L, 1L, 4L);

        FormDto.ReadOnly formDto = formMapper.toDto(form);

        assertEquals(1L, formDto.getId());
        assertEquals(4L, formDto.getUser().getId());
        assertEquals("http://test4.img.url", formDto.getUserImage().getUserImgUrl());
        assertEquals("테스트1의 신청 내용", formDto.getContent());
        assertEquals(Form.State.REQUEST, formDto.getState());
        assertEquals("2022-06-16T22:48:17", formDto.getExpirationDate());
        assertEquals("2022-06-15T10:30:50", formDto.getStartAt());
        assertEquals("2022-06-15T11:30:50", formDto.getEndAt());
    }

    @Test
    void DTO에서_엔티티변환_테스트() {
        FormDto formDto = new TestFormDtoFactory().createDto(1L);

        Form form = formMapper.toEntity(formDto);

        assertEquals("테스트1의 신청 내용", form.getContent());
    }
}