package kr.co.knowledgerally.core.lecture.service;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import kr.co.knowledgerally.core.annotation.KnowllyDataTest;
import kr.co.knowledgerally.core.lecture.entity.Category;
import kr.co.knowledgerally.core.lecture.util.TestCategoryEntityFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@KnowllyDataTest
@Import(CategoryService.class)
@DatabaseSetup({
        "classpath:dbunit/entity/category.xml",
})
class CategoryServiceTest {
    @Autowired
    CategoryService categoryService;

    TestCategoryEntityFactory testCategoryEntityFactory = new TestCategoryEntityFactory();

    @Test
    void 카테고리_목록_조회() {
        List<Category> categoryList = categoryService.findAll();

        assertEquals(Category.Name.PM, categoryList.get(0).getName());
        assertEquals(Category.Name.DESIGN, categoryList.get(1).getName());
        assertEquals(Category.Name.DEVELOP, categoryList.get(2).getName());
        assertEquals(Category.Name.MARKETING, categoryList.get(3).getName());
        assertEquals(Category.Name.LANGUAGE, categoryList.get(4).getName());
        assertEquals(Category.Name.ETC, categoryList.get(5).getName());
    }

    @Test
    void Id로_카테고리_조회() {
        Optional<Category> category = categoryService.findById(1L);
        assertEquals(category.get().getName(), Category.Name.PM);
    }

    @Test
    void Name으로_카테고리_조회() {
        Optional<Category> category = categoryService.findByName(Category.Name.DESIGN);
        assertEquals(category.get().getId(), 2L);
    }
}
