package kr.co.knowledgerally.core.lecture.repository.crud;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import kr.co.knowledgerally.core.core.repository.AbstractRepositoryCrudTest;
import kr.co.knowledgerally.core.lecture.entity.Tag;
import kr.co.knowledgerally.core.lecture.repository.TagRepository;
import kr.co.knowledgerally.core.lecture.util.TestTagEntityFactory;
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
        "classpath:dbunit/entity/tag.xml",
})
class TagRepositoryCrudTest extends AbstractRepositoryCrudTest {

    @Autowired
    TagRepository tagRepository;

    TestTagEntityFactory testTagEntityFactory = new TestTagEntityFactory();

    @Override
    @Test
    protected void selectTest() {
        Tag tag = tagRepository.findById(1L).orElseThrow();

        assertEquals(1L, tag.getId());
        assertEquals(1L, tag.getLectureInformation().getId());
        assertEquals("마케팅", tag.getContent());
        assertTrue(tag.isActive());
        assertEquals(LocalDateTime.of(2022, 6, 13, 22, 42, 23), tag.getCreatedAt());
        assertEquals(LocalDateTime.of(2022, 6, 13, 22, 42, 23), tag.getUpdatedAt());
    }

    @Override
    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/crud/tag_insert_test.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    protected void insertTest() {
        Tag tag = testTagEntityFactory.createEntity(6L, 3);
        tagRepository.saveAndFlush(tag);
    }

    @Override
    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/crud/tag_update_test.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    protected void updateTest() {
        Tag tag = tagRepository.findById(1L).orElseThrow();
        tag.setContent("변경내용");
        entityManager.flush();
    }

    @Override
    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/crud/tag_delete_test.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    protected void deleteTest() {
        tagRepository.deleteById(1L);
        entityManager.flush();
    }
}