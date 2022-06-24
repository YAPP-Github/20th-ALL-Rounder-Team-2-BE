package kr.co.knowledgerally.api.lecture.component;

import kr.co.knowledgerally.api.lecture.dto.CategoryDto;
import kr.co.knowledgerally.api.lecture.util.TestCategoryDtoFactory;
import kr.co.knowledgerally.core.lecture.entity.Category;
import kr.co.knowledgerally.core.lecture.util.TestCategoryEntityFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryMapperTest {
    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    void 엔티티에서_DTO변환_테스트() {
        Category category = new TestCategoryEntityFactory().createEntity(1L);

        CategoryDto categoryDto = categoryMapper.toDto(category);
        assertEquals("테스트 카테고리1", categoryDto.getCategoryName());
    }

    @Test
    void DTO에서_엔티티변환_테스트() {
        CategoryDto categoryDto = new TestCategoryDtoFactory().createDto(1L);

        Category category = categoryMapper.toEntity(categoryDto);
        assertEquals("테스트 카테고리1", category.getCategoryName());
    }
}
