package kr.co.knowledgerally.core.user.service;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import kr.co.knowledgerally.core.annotation.KnowllyDataTest;
import kr.co.knowledgerally.core.user.entity.User;
import kr.co.knowledgerally.core.user.entity.UserImage;
import kr.co.knowledgerally.core.user.util.TestUserEntityFactory;
import kr.co.knowledgerally.core.user.util.TestUserImageEntityFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;

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

    TestUserEntityFactory testUserEntityFactory = new TestUserEntityFactory();
    TestUserImageEntityFactory testUserImageEntityFactory = new TestUserImageEntityFactory();

    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/유저_이미지_저장_테스트.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    void 유저_이미지_저장_테스트() {
        UserImage userImage = testUserImageEntityFactory.createEntity(8L, 1L);
        userImageService.saveUserImage(userImage);
    }

    @Test
    void 사용자_이미지_찾기_테스트() {
        User user = testUserEntityFactory.createEntity(1L);
        UserImage userImage = userImageService.findByUser(user);

        assertEquals(1L, userImage.getId());
        assertEquals(1L, userImage.getUser().getId());
        assertEquals("http://test1.img.url", userImage.getUserImgUrl());
        assertTrue(userImage.isActive());
        assertEquals(LocalDateTime.of(2022, 6, 13, 21, 26, 58), userImage.getCreatedAt());
        assertEquals(LocalDateTime.of(2022, 6, 13, 21, 27, 1), userImage.getUpdatedAt());
    }
}