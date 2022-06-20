package kr.co.knowledgerally.core.user.service;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import kr.co.knowledgerally.core.annotation.KnowllyDataTest;
import kr.co.knowledgerally.core.user.entity.UserImage;
import kr.co.knowledgerally.core.user.util.TestUserImageEntityFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@KnowllyDataTest
@Import(UserImageService.class)
@DatabaseSetup({
        "classpath:dbunit/entity/user.xml",
        "classpath:dbunit/entity/user_image.xml",
})
class UserImageServiceTest {
    @Autowired
    UserImageService userImageService;

    TestUserImageEntityFactory testUserImageEntityFactory = new TestUserImageEntityFactory();

    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/crud/user_image_insert_test.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    void 유저_이미지_저장_테스트() {
        UserImage userImage = testUserImageEntityFactory.createEntity(8L, 1L);
        userImageService.saveUserImage(userImage);
    }
}