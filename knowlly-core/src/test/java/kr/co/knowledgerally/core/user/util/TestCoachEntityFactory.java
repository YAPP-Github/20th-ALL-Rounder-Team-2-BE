package kr.co.knowledgerally.core.user.util;

import kr.co.knowledgerally.core.core.util.TestEntityFactory;
import kr.co.knowledgerally.core.user.entity.Coach;
import kr.co.knowledgerally.core.user.entity.User;

/**
 * 테스트용 코치 엔티티 생성 팩토리
 */
public class TestCoachEntityFactory implements TestEntityFactory<Coach> {
    private final TestEntityFactory<User> testUserEntityFactory = new TestUserEntityFactory();

    /**
     * 테스트용 코치 엔티티를 생성한다.
     *
     * @param entityId 생성될 엔티티 Id
     * @return 생성된 코치 엔티티
     */
    @Override
    public Coach createEntity(long entityId) {
        return createEntity(entityId, 1L);
    }

    /**
     * 테스트용 코치 엔티티를 생성한다.
     *
     * @param entityId 생성될 엔티티 Id
     * @param userId   생성될 엔티티의 사용자 Id
     * @return 생성된 코치 엔티티
     */
    public Coach createEntity(long entityId, long userId) {
        return Coach.builder()
                .id(entityId)
                .user(testUserEntityFactory.createEntity(userId))
                .introduce(String.format("안녕하세요. 테스트%d 코치입니다.", entityId))
                .build();
    }
}
