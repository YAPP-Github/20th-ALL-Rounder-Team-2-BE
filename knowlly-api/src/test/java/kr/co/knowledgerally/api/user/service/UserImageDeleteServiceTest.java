package kr.co.knowledgerally.api.user.service;

import kr.co.knowledgerally.core.user.entity.User;
import kr.co.knowledgerally.core.user.entity.UserImage;
import kr.co.knowledgerally.core.user.repository.UserImageRepository;
import kr.co.knowledgerally.core.user.repository.UserRepository;
import kr.co.knowledgerally.core.user.util.TestUserEntityFactory;
import kr.co.knowledgerally.core.user.util.TestUserImageEntityFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserImageDeleteServiceTest {
    @Autowired
    UserImageDeleteService userImageDeleteService;

    @Autowired
    UserImageRepository userImageRepository;

    @Autowired
    UserRepository userRepository;
    TestUserEntityFactory testUserEntityFactory = new TestUserEntityFactory();
    TestUserImageEntityFactory testUserImageEntityFactory = new TestUserImageEntityFactory();

    @Test
    void 사용자_이미지_삭제_테스트() {
        User user = testUserEntityFactory.createEntity(1L);
        UserImage userImage = testUserImageEntityFactory.createEntity(1L, 1L);
        userImage.setUser(user);
        userRepository.save(user);
        userImageRepository.saveAndFlush(userImage);
        userImage = userImageDeleteService.deleteImage(user);

        assertEquals(1L, userImage.getId());
        assertEquals(1L, userImage.getUser().getId());
        assertEquals("http://test1.userimg.url", userImage.getUserImgUrl());
        assertFalse(userImage.isActive());
    }
}
