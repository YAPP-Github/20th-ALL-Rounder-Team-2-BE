package kr.co.knowledgerally.api.coach.util;

import kr.co.knowledgerally.api.coach.dto.CoachDto;
import kr.co.knowledgerally.api.core.util.TestDtoFactory;
import kr.co.knowledgerally.api.user.util.TestUserDtoFactory;

/**
 * 테스트용 코치 Dto 생성 팩토리
 */
public class TestCoachDtoFactory implements TestDtoFactory<CoachDto> {

    TestUserDtoFactory testUserDtoFactory = new TestUserDtoFactory();

    /**
     * 테스트용 코치 Dto를 생성한다.
     *
     * @param id 생성될 Dto Id
     * @return 생성된 코치 Dto
     */
    @Override
    public CoachDto createDto(long id) {
        return CoachDto.builder()
                .introduce(String.format("테스트 내용%d", id))
                .build();
    }

    /**
     * 테스트용 읽기전용 코치 Dto를 생성한다.
     *
     * @param id 생성될 Dto Id
     * @return 생성된 코치 Dto
     */
    public CoachDto.ReadOnly createReadOnlyDto(long id) {
        return CoachDto.ReadOnly.builder()
                .id(id)
                .user(testUserDtoFactory.createReadOnlyDto(id))
                .introduce(String.format("테스트 내용%d", id))
                .build();
    }
}
