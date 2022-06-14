package kr.co.knowledgerally.core.user.util;

import kr.co.knowledgerally.core.core.util.TestEntityFactory;
import kr.co.knowledgerally.core.user.entity.User;
import kr.co.knowledgerally.core.user.entity.UserImage;

/**
 * 테스트용 사용자 프로필 이미지 엔티티 생성 팩토리
 */
public class TestUserImageEntityFactory implements TestEntityFactory<UserImage> {
    private final TestEntityFactory<User> testUserEntityFactory = new TestUserEntityFactory();

    /**
     * 테스트용 사용자 프로필 이미지 엔티티를 생성한다.
     *
     * @param entityId 생성될 엔티티 Id
     * @return 생성된 사용자 프로필 이미지 엔티티
     */
    @Override
    public UserImage createEntity(long entityId) {
        return createEntity(entityId, 1L);
    }

    /**
     * 테스트용 사용자 프로필 이미지 엔티티를 생성한다.
     *
     * @param entityId 생성될 엔티티 Id
     * @param userId   생성될 엔티티의 소속 사용자 Id
     * @return 생성된 사용자 프로필 이미지 엔티티
     */
    public UserImage createEntity(long entityId, long userId) {
        return UserImage.builder()
                .id(entityId)
                .user(testUserEntityFactory.createEntity(userId))
                .userImgUrl(String.format("http://test%d.userimg.url", entityId))
                .build();
    }
}
