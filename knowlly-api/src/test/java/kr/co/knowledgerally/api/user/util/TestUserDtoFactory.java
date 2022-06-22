package kr.co.knowledgerally.api.user.util;

import kr.co.knowledgerally.api.core.util.TestDtoFactory;
import kr.co.knowledgerally.api.user.dto.UserDto;
import kr.co.knowledgerally.core.core.util.TestEntityFactory;
import kr.co.knowledgerally.core.user.entity.User;

/**
 * 테스트용 사용자 Dto 생성 팩토리
 */
public class TestUserDtoFactory implements TestDtoFactory<UserDto> {

    /**
     * 테스트용 사용자 Dto를 생성한다.
     *
     * @param id 생성될 Dto Id
     * @return 생성된 사용자 Dto
     */
    @Override
    public UserDto createDto(long id) {
        return UserDto.builder()
                .username("테스트1")
                .intro("안녕하세요. 저는 테스트1이라고 합니다.")
                .kakaoId("kakao_test1")
                .portfolio("포트폴리오1")
                .build();
    }

    /**
     * 테스트용 읽기전용 사용자 Dto를 생성한다.
     *
     * @param id 생성될 Dto Id
     * @return 생성된 사용자 Dto
     */
    public UserDto.ReadOnly createReadOnlyDto(long id) {
        return UserDto.ReadOnly.builder()
                .id(id)
                .username("테스트1")
                .intro("안녕하세요. 저는 테스트1이라고 합니다.")
                .kakaoId("kakao_test1")
                .portfolio("포트폴리오1")
                .identifier("identifier1")
                .build();
    }
}
