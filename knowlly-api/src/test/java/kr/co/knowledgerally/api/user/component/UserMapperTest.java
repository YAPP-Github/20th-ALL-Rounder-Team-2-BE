package kr.co.knowledgerally.api.user.component;

import kr.co.knowledgerally.api.user.dto.UserDto;
import kr.co.knowledgerally.api.user.util.TestUserDtoFactory;
import kr.co.knowledgerally.core.user.entity.User;
import kr.co.knowledgerally.core.user.entity.UserImage;
import kr.co.knowledgerally.core.user.util.TestUserEntityFactory;
import kr.co.knowledgerally.core.user.util.TestUserImageEntityFactory;
import liquibase.pro.packaged.T;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    void 엔티티에서_DTO변환_테스트() {
        User user = new TestUserEntityFactory().createEntity(1L);

        UserDto.ReadOnly userDto = userMapper.toDto(user);
        assertEquals(1L, userDto.getId());
        assertEquals("테스트1", userDto.getUsername());
        assertEquals(1, userDto.getBallCnt());
        assertEquals("안녕하세요. 저는 테스트1이라고 합니다.", userDto.getIntro());
        assertEquals("kakao_test1", userDto.getKakaoId());
        assertEquals("포트폴리오1", userDto.getPortfolio());
        assertEquals("identifier1", userDto.getIdentifier());
        assertFalse(userDto.isCoach());
        assertTrue(userDto.isPushActive());
    }

    @Test
    void DTO에서_엔티티변환_테스트() {
        UserDto userDto = new TestUserDtoFactory().createDto(1L);

        User user = userMapper.toEntity(userDto);
        assertEquals("테스트1", user.getUsername());
        assertEquals("안녕하세요. 저는 테스트1이라고 합니다.", user.getIntro());
        assertEquals("kakao_test1", user.getKakaoId());
        assertEquals("포트폴리오1", user.getPortfolio());
    }
}