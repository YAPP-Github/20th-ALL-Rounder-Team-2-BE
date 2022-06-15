package kr.co.knowledgerally.core.user.util;

import kr.co.knowledgerally.core.core.util.TestEntityFactory;
import kr.co.knowledgerally.core.user.entity.User;

/**
 * 테스트용 사용자 엔티티 생성 팩토리
 */
public class TestUserEntityFactory implements TestEntityFactory<User> {

    /**
     * 테스트용 사용자 엔티티를 생성한다.
     *
     * @param entityId 생성될 엔티티 Id
     * @return 생성된 사용자 엔티티
     */
    @Override
    public User createEntity(long entityId) {
        return User.builder()
                .id(entityId)
                .email(String.format("test%d@email.com", entityId))
                .username(String.format("테스트%d", entityId))
                .ballCnt(1)
                .intro(String.format("안녕하세요. 저는 테스트%d이라고 합니다.", entityId))
                .kakaoId(String.format("kakao_test%d", entityId))
                .build();
    }
}
