package kr.co.knowledgerally.api.coach.util;

import kr.co.knowledgerally.api.review.dto.ReviewDto;
import kr.co.knowledgerally.api.core.util.TestDtoFactory;
import kr.co.knowledgerally.api.user.util.TestUserDtoFactory;

/**
 * 테스트용 후기 Dto 생성 팩토리
 */
public class TestReviewDtoFactory implements TestDtoFactory<ReviewDto> {

    TestUserDtoFactory testUserDtoFactory = new TestUserDtoFactory();
    TestCoachDtoFactory testCoachDtoFactory = new TestCoachDtoFactory();

    /**
     * 테스트용 후기 Dto를 생성한다.
     *
     * @param id 생성될 Dto Id
     * @return 생성된 후기 Dto
     */
    @Override
    public ReviewDto createDto(long id) {
        return ReviewDto.builder()
                .content(String.format("테스트%d 내용", id))
                .isPublic(true)
                .build();
    }

    /**
     * 테스트용 읽기전용 후기 Dto를 생성한다.
     *
     * @param id 생성될 Dto Id
     * @return 생성된 후기 Dto
     */
    public ReviewDto.ReadOnly createReadOnlyDto(long id, long writerId, long revieweeId) {
        return ReviewDto.ReadOnly.builder()
                .writer(testUserDtoFactory.createReadOnlyDto(writerId))
                .reviewee(testCoachDtoFactory.createReadOnlyDto(revieweeId))
                .content(String.format("테스트%d 내용", id))
                .isPublic(true)
                .build();
    }
}
