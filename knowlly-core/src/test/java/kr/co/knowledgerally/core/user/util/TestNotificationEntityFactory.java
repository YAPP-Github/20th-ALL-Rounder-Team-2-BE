package kr.co.knowledgerally.core.user.util;

import kr.co.knowledgerally.core.coach.util.TestCoachEntityFactory;
import kr.co.knowledgerally.core.core.util.TestEntityFactory;
import kr.co.knowledgerally.core.coach.entity.Coach;
import kr.co.knowledgerally.core.user.entity.Notification;
import kr.co.knowledgerally.core.user.entity.User;

/**
 * 테스트용 알림내역 엔티티 생성 팩토리
 */
public class TestNotificationEntityFactory implements TestEntityFactory<Notification> {
    private final TestEntityFactory<User> testUserEntityFactory = new TestUserEntityFactory();
    private final TestEntityFactory<Coach> testCoachEntityFactory = new TestCoachEntityFactory();

    /**
     * 테스트용 알림내역 엔티티를 생성한다.
     *
     * @param entityId 생성될 엔티티 Id
     * @return 생성된 알림내역 엔티티
     */
    @Override
    public Notification createEntity(long entityId) {
        return createEntity(entityId, 1L, 2L, Notification.NotiType.PLAYER);
    }

    /**
     * 테스트용 알림내역 엔티티를 생성한다.
     *
     * @param entityId 생성될 엔티티 Id
     * @param userId   생성될 엔티티의 사용자 Id
     * @param coachId  생성될 엔티티의 코치 Id
     * @return 생성된 알림내역 엔티티
     */
    public Notification createEntity(long entityId, long userId, long coachId) {
        return createEntity(entityId, userId, coachId, Notification.NotiType.PLAYER);
    }

    /**
     * 테스트용 알림내역 엔티티를 생성한다.
     *
     * @param entityId 생성될 엔티티 Id
     * @param userId   생성될 엔티티의 사용자 Id
     * @param coachId  생성될 엔티티의 코치 Id
     * @param notiType 생성될 엔티티의 알림 타입
     * @return 생성된 알림내역 엔티티
     */
    public Notification createEntity(long entityId, long userId, long coachId, Notification.NotiType notiType) {
        return Notification.builder()
                .id(entityId)
                .user(testUserEntityFactory.createEntity(userId))
                .coach(testCoachEntityFactory.createEntity(coachId))
                .content(String.format("테스트%d 내용", entityId))
                .notiType(notiType)
                .build();
    }
}
