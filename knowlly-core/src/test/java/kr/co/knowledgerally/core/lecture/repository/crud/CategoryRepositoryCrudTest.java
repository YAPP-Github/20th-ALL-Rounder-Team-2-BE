package kr.co.knowledgerally.core.lecture.repository.crud;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import kr.co.knowledgerally.core.core.repository.AbstractRepositoryCrudTest;
import kr.co.knowledgerally.core.lecture.entity.Category;
import kr.co.knowledgerally.core.lecture.repository.CategoryRepository;
import kr.co.knowledgerally.core.lecture.util.TestCategoryEntityFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DatabaseSetup({
        "classpath:dbunit/entity/category.xml",
})
class CategoryRepositoryCrudTest extends AbstractRepositoryCrudTest {

    @Autowired
    CategoryRepository categoryRepository;

    TestCategoryEntityFactory testCategoryEntityFactory = new TestCategoryEntityFactory();

    @Override
    @Test
    protected void selectTest() {
        Category category = categoryRepository.findById(1L).orElseThrow();

        assertEquals(1L, category.getId());
        assertEquals("기획 / PM", category.getCategoryName());
        assertTrue(category.isActive());
    }

    @Override
    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/crud/category_insert_test.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    protected void insertTest() {
        Category category = testCategoryEntityFactory.createEntity(7L);
        categoryRepository.saveAndFlush(category);
    }

    @Override
    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/crud/category_update_test.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    protected void updateTest() {
        Category category = categoryRepository.findById(1L).orElseThrow();
        category.setCategoryName("카테고리 변경");
        entityManager.flush();
    }

    @Override
    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/crud/category_delete_test.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    protected void deleteTest() {
        categoryRepository.deleteById(1L);
        entityManager.flush();
    }
}