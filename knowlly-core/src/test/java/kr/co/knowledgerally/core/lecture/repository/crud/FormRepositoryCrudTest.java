package kr.co.knowledgerally.core.lecture.repository.crud;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import kr.co.knowledgerally.core.core.repository.AbstractRepositoryCrudTest;
import kr.co.knowledgerally.core.lecture.entity.Form;
import kr.co.knowledgerally.core.lecture.repository.FormRepository;
import kr.co.knowledgerally.core.lecture.util.TestFormEntityFactory;
import liquibase.pro.packaged.T;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DatabaseSetup({
        "classpath:dbunit/entity/user.xml",
        "classpath:dbunit/entity/coach.xml",
        "classpath:dbunit/entity/category.xml",
        "classpath:dbunit/entity/lecture_information.xml",
        "classpath:dbunit/entity/lecture.xml",
        "classpath:dbunit/entity/form.xml",
})
class FormRepositoryCrudTest extends AbstractRepositoryCrudTest {

    @Autowired
    FormRepository formRepository;

    TestFormEntityFactory testFormEntityFactory = new TestFormEntityFactory();

    @Override
    @Test
    protected void selectTest() {
        Form form = formRepository.findById(1L).orElseThrow();

        assertEquals(1L, form.getId());
        assertEquals(1L, form.getLecture().getId());
        assertEquals(4L, form.getUser().getId());
        assertEquals("신청서를 받아주세요!", form.getContent());
        assertEquals(Form.State.ACCEPT, form.getState());
        assertTrue(form.isActive());
        assertEquals(LocalDateTime.of(2022, 6, 13, 22, 48, 17), form.getCreatedAt());
        assertEquals(LocalDateTime.of(2022, 6, 13, 22, 48, 17), form.getUpdatedAt());
    }

    @Override
    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/crud/form_insert_test.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    protected void insertTest() {
        Form form = testFormEntityFactory.createEntity(7L, 4, 2);
        formRepository.saveAndFlush(form);
    }

    @Override
    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/crud/form_update_test.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    protected void updateTest() {
        Form form = formRepository.findById(1L).orElseThrow();
        form.setContent("신청 내용 변경");
        entityManager.flush();
    }

    @Override
    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/crud/form_delete_test.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    protected void deleteTest() {
        formRepository.deleteById(1L);
        entityManager.flush();
    }
}