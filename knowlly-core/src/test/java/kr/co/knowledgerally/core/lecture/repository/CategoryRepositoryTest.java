package kr.co.knowledgerally.core.lecture.repository;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import kr.co.knowledgerally.core.annotation.KnowllyDataTest;
import kr.co.knowledgerally.core.lecture.entity.Category;
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

        assertEquals(Category.Name.PM, categoryList.get(0).getName());
        assertEquals(Category.Name.DESIGN, categoryList.get(1).getName());
        assertEquals(Category.Name.DEVELOP, categoryList.get(2).getName());
        assertEquals(Category.Name.MARKETING, categoryList.get(3).getName());
        assertEquals(Category.Name.LANGUAGE, categoryList.get(4).getName());
        assertEquals(Category.Name.ETC, categoryList.get(5).getName());
    }

    @Test
    void 카테고리를_id와_활성화_여부로_조회() {
        Category category = categoryRepository.findByIdAndIsActive(2L, true).orElseThrow();

        assertEquals(Category.Name.DESIGN, category.getName());
    }

    @Test
    void 카테고리를_이름과_활성화_여부로_조회() {
        Category category = categoryRepository.findByNameAndIsActive(Category.Name.LANGUAGE, true).orElseThrow();

        assertEquals(5L, category.getId());
    }
}
