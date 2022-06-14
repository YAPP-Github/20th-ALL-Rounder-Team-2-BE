package kr.co.knowledgerally.core.user.util;

import kr.co.knowledgerally.core.core.util.TestEntityFactory;
import kr.co.knowledgerally.core.user.entity.BallHistory;
import kr.co.knowledgerally.core.user.entity.User;

/**
 * 테스트용 볼 내역 엔티티 생성 팩토리
 */
public class TestBallHistoryEntityFactory implements TestEntityFactory<BallHistory> {
    private final TestEntityFactory<User> testUserEntityFactory = new TestUserEntityFactory();

    /**
     * 테스트용 볼 내역 엔티티를 생성한다.
     *
     * @param entityId 생성될 엔티티 Id
     * @return 생성된 볼 내역 엔티티
     */
    @Override
    public BallHistory createEntity(long entityId) {
        return createEntity(entityId, 1L);
    }

    /**
     * 테스트용 볼 내역 엔티티를 생성한다.
     *
     * @param entityId 생성될 엔티티 Id
     * @param userId   생성될 엔티티의 사용자 Id
     * @return 생성된 볼 내역 엔티티
     */
    public BallHistory createEntity(long entityId, long userId) {
        return BallHistory.builder()
                .id(entityId)
                .user(testUserEntityFactory.createEntity(userId))
                .title(String.format("테스트%d 제목", entityId))
                .content(String.format("테스트%d 내용", entityId))
                .count(1)
                .build();
    }
}
