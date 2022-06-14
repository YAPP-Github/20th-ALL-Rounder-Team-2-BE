package kr.co.knowledgerally.core.user.repository.crud;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import kr.co.knowledgerally.core.core.repository.AbstractRepositoryCrudTest;
import kr.co.knowledgerally.core.user.entity.UserImage;
import kr.co.knowledgerally.core.user.repository.UserImageRepository;
import kr.co.knowledgerally.core.user.util.TestUserImageEntityFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DatabaseSetup({
        "classpath:dbunit/entity/user.xml",
        "classpath:dbunit/entity/user_image.xml",
})
class UserImageRepositoryCrudTest extends AbstractRepositoryCrudTest {

    @Autowired
    UserImageRepository userImageRepository;

    TestUserImageEntityFactory testUserImageEntityFactory = new TestUserImageEntityFactory();

    @Override
    @Test
    protected void selectTest() {
        UserImage userImage = userImageRepository.findById(1L).orElseThrow();

        assertEquals(1L, userImage.getId());
        assertEquals(1L, userImage.getUser().getId());
        assertEquals("http://test1.img.url", userImage.getUserImgUrl());
        assertTrue(userImage.isActive());
        assertEquals(LocalDateTime.of(2022, 6, 13, 21, 26, 58), userImage.getCreatedAt());
        assertEquals(LocalDateTime.of(2022, 6, 13, 21, 27, 1), userImage.getUpdatedAt());
    }

    @Override
    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/crud/user_image_insert_test.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    protected void insertTest() {
        UserImage userImage = testUserImageEntityFactory.createEntity(8L, 1L);
        userImageRepository.saveAndFlush(userImage);
    }

    @Override
    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/crud/user_image_update_test.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    protected void updateTest() {
        UserImage userImage = userImageRepository.findById(1L).orElseThrow();
        userImage.setUserImgUrl("http://test1changed.img.url");
        entityManager.flush();
    }

    @Override
    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/crud/user_image_delete_test.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    protected void deleteTest() {
        userImageRepository.deleteById(1L);
        entityManager.flush();
    }
}