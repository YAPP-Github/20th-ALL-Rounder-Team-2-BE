package kr.co.knowledgerally.api.user.component;

import kr.co.knowledgerally.api.user.dto.UserDto;
import kr.co.knowledgerally.api.user.dto.UserImageDto;
import kr.co.knowledgerally.api.user.util.TestUserDtoFactory;
import kr.co.knowledgerally.core.user.entity.User;
import kr.co.knowledgerally.core.user.entity.UserImage;
import kr.co.knowledgerally.core.user.util.TestUserEntityFactory;
import kr.co.knowledgerally.core.user.util.TestUserImageEntityFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserImageMapperTest {
    @Autowired
    UserImageMapper userImageMapper;

    @Test
    void 엔티티에서_DTO변환_테스트() {
        UserImage userImage = new TestUserImageEntityFactory().createEntity(1L);

        UserImageDto userImageDto = userImageMapper.toDto(userImage);
        assertEquals("http://test1.userimg.url", userImageDto.getUserImgUrl());
    }

    @Test
    void DTO에서_엔티티변환_테스트() {
        UserImageDto userImageDto = new UserImageDto("http://test1.userimg.url");
        User user = new TestUserEntityFactory().createEntity(1L);

        UserImage userImage = userImageMapper.toEntity(userImageDto, user);
        assertEquals("http://test1.userimg.url", userImage.getUserImgUrl());
        assertEquals(1L, userImage.getUser().getId());
        assertEquals("테스트1", userImage.getUser().getUsername());
        assertEquals("안녕하세요. 저는 테스트1이라고 합니다.", userImage.getUser().getIntro());
        assertEquals("kakao_test1", userImage.getUser().getKakaoId());
        assertEquals("포트폴리오1", userImage.getUser().getPortfolio());
        assertEquals("identifier1", userImage.getUser().getIdentifier());
    }
}