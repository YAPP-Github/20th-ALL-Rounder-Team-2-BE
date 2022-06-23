package kr.co.knowledgerally.core.lecture.service;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import kr.co.knowledgerally.core.annotation.KnowllyDataTest;
import kr.co.knowledgerally.core.lecture.entity.Category;
import kr.co.knowledgerally.core.lecture.util.TestCategoryEntityFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;

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
    void IsActive로_카테고리_목록_조회() {
        List<Category> categoryList = categoryService.findAll();

        assertEquals(categoryList.get(0).getCategoryName(), "기획 / PM");
        assertEquals(categoryList.get(1).getCategoryName(), "디자인");
        assertEquals(categoryList.get(2).getCategoryName(), "개발");
        assertEquals(categoryList.get(3).getCategoryName(), "마케팅");
        assertEquals(categoryList.get(4).getCategoryName(), "외국어");
        assertEquals(categoryList.get(5).getCategoryName(), "기타");
    }
}
