package kr.co.knowledgerally.core.user.util;

import kr.co.knowledgerally.core.core.util.TestEntityFactory;
import kr.co.knowledgerally.core.user.entity.Coach;
import kr.co.knowledgerally.core.user.entity.Review;
import kr.co.knowledgerally.core.user.entity.User;

/**
 * 테스트용 리뷰 엔티티 생성 팩토리
 */
public class TestReviewEntityFactory implements TestEntityFactory<Review> {
    private final TestEntityFactory<User> testUserEntityFactory = new TestUserEntityFactory();
    private final TestEntityFactory<Coach> testCoachEntityFactory = new TestCoachEntityFactory();

    /**
     * 테스트용 리뷰 엔티티를 생성한다.
     *
     * @param entityId 생성될 엔티티 Id
     * @return 생성된 리뷰 엔티티
     */
    @Override
    public Review createEntity(long entityId) {
        return createEntity(entityId, 1L, 2L);
    }

    /**
     * 테스트용 리뷰 엔티티를 생성한다.
     *
     * @param entityId 생성될 엔티티 Id
     * @param userId 생성될 엔티티의 사용자 Id
     * @param coachId 생성될 엔티티 코치 Id
     * @return 생성된 리뷰 엔티티
     */
    public Review createEntity(long entityId, long userId, long coachId) {
        return Review.builder()
                .id(entityId)
                .user(testUserEntityFactory.createEntity(userId))
                .coach(testCoachEntityFactory.createEntity(coachId))
                .content(String.format("테스트%d 내용", entityId))
                .build();
    }
}
