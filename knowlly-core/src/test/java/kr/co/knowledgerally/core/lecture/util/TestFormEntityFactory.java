package kr.co.knowledgerally.core.lecture.util;

import kr.co.knowledgerally.core.core.util.TestEntityFactory;
import kr.co.knowledgerally.core.lecture.entity.Form;
import kr.co.knowledgerally.core.lecture.entity.Lecture;
import kr.co.knowledgerally.core.user.entity.User;
import kr.co.knowledgerally.core.user.util.TestUserEntityFactory;

/**
 * 테스트용 신청서 엔티티 생성 팩토리
 */
public class TestFormEntityFactory implements TestEntityFactory<Form> {
    private final TestEntityFactory<User> testUserEntityFactory = new TestUserEntityFactory();
    private final TestEntityFactory<Lecture> testLectureEntityFactory = new TestLectureEntityFactory();

    /**
     * 테스트용 신청서 엔티티를 생성한다.
     *
     * @param entityId 생성될 엔티티 Id
     * @return 생성된 신청서 엔티티
     */
    @Override
    public Form createEntity(long entityId) {
        return createEntity(entityId, 1L, 1L);
    }

    /**
     * 테스트용 신청서 엔티티를 생성한다.
     *
     * @param entityId  생성될 엔티티 Id
     * @param lectureId 생성될 엔티티의 강의 Id
     * @param userId    생성될 엔티티의 사용자 Id
     * @return 생성된 신청서 엔티티
     */
    public Form createEntity(long entityId, long lectureId, long userId) {
        return Form.builder()
                .id(entityId)
                .lecture(testLectureEntityFactory.createEntity(lectureId))
                .user(testUserEntityFactory.createEntity(userId))
                .content(String.format("테스트%d의 신청 내용", entityId))
                .build();
    }
}
