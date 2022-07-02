package kr.co.knowledgerally.core.lecture.repository;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import kr.co.knowledgerally.core.annotation.KnowllyDataTest;
import kr.co.knowledgerally.core.lecture.entity.Category;
import kr.co.knowledgerally.core.lecture.repository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@KnowllyDataTest
@DatabaseSetup({
        "classpath:dbunit/entity/category.xml"
})
public class CategoryRepositoryTest {
    @Autowired
    CategoryRepository categoryRepository;

    @Test
    void 카테고리_목록을_활성화_여부로_조회() {
        List<Category> categoryList = categoryRepository.findAllByIsActive(true);

        assertEquals("기획 / PM", categoryList.get(0).getCategoryName());
        assertEquals("디자인", categoryList.get(1).getCategoryName());
        assertEquals("개발", categoryList.get(2).getCategoryName());
        assertEquals("마케팅", categoryList.get(3).getCategoryName());
        assertEquals("외국어", categoryList.get(4).getCategoryName());
        assertEquals("기타", categoryList.get(5).getCategoryName());
    }

    @Test
    void 카테고리를_id와_활성화_여부로_조회() {
        Category category = categoryRepository.findByIdAndIsActive(2L, true).orElseThrow();

        assertEquals("디자인", category.getCategoryName());
    }

    @Test
    void 카테고리를_이름과_활성화_여부로_조회() {
        Category category = categoryRepository.findByCategoryNameAndIsActive("외국어", true).orElseThrow();

        assertEquals(5L, category.getId());
    }
}
