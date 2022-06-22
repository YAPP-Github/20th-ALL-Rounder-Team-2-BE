package kr.co.knowledgerally.core.user.repository;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import kr.co.knowledgerally.core.annotation.KnowllyDataTest;
import kr.co.knowledgerally.core.user.entity.User;
import kr.co.knowledgerally.core.user.entity.UserImage;
import kr.co.knowledgerally.core.user.util.TestUserEntityFactory;
import kr.co.knowledgerally.core.user.util.TestUserImageEntityFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;


@KnowllyDataTest
@DatabaseSetup({
        "classpath:dbunit/entity/user.xml",
        "classpath:dbunit/entity/user_image.xml",
})
class UserImageRepositoryTest {
    @Autowired
    UserImageRepository userImageRepository;

    TestUserEntityFactory testUserEntityFactory = new TestUserEntityFactory();

    @Test
    void 사용자로_이미지_목록_찾기() {
        List<UserImage> userImages = userImageRepository.findAllByUser(testUserEntityFactory.createEntity(1L));

        assertEquals(2, userImages.size());
        assertEquals(1L, userImages.get(0).getId());
        assertEquals(2L, userImages.get(1).getId());
    }
}