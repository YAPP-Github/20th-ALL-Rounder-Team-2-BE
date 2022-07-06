package kr.co.knowledgerally.api.lecture.component;

import kr.co.knowledgerally.api.lecture.dto.TagDto;
import kr.co.knowledgerally.api.lecture.util.TestTagDtoFactory;
import kr.co.knowledgerally.core.lecture.entity.Tag;
import kr.co.knowledgerally.core.lecture.util.TestTagEntityFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TagMapperTest {
    @Autowired
    private TagMapper tagMapper;

    @Test
    void 엔티티에서_DTO변환_테스트() {
        Tag tag = new TestTagEntityFactory().createEntity(1L);

        TagDto tagDto = tagMapper.toDto(tag);
        assertEquals("테스트 내용1", tagDto.getContent());
    }

    @Test
    void DTO에서_엔티티변환_테스트() {
        TagDto tagDto = new TestTagDtoFactory().createDto(1L);

        Tag tag = tagMapper.toEntity(tagDto);
        assertEquals("테스트 내용1", tag.getContent());
    }
}
