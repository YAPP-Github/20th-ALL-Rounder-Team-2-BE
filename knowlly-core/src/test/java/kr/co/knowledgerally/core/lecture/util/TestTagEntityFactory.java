package kr.co.knowledgerally.core.lecture.util;

import kr.co.knowledgerally.core.core.util.TestEntityFactory;
import kr.co.knowledgerally.core.lecture.entity.LectureInformation;
import kr.co.knowledgerally.core.lecture.entity.Tag;

/**
 * 테스트용 태그 엔티티 생성 팩토리
 */
public class TestTagEntityFactory implements TestEntityFactory<Tag> {
    private final TestEntityFactory<LectureInformation> testLectureInformationEntityFactory = new TestLectureInformationEntityFactory();

    /**
     * 테스트용 태그 엔티티를 생성한다.
     *
     * @param entityId 생성될 엔티티 Id
     * @return 태그 생성된 엔티티
     */
    @Override
    public Tag createEntity(long entityId) {
        return Tag.builder()
                .id(entityId)
                .lectureInformation(testLectureInformationEntityFactory.createEntity(entityId))
                .content(String.format("테스트 내용%d", entityId))
                .build();
    }

    /**
     * 테스트용 태그 엔티티를 생성한다.
     *
     * @param entityId      생성될 엔티티 Id
     * @param lectureInfoId 생성될 엔티티의 클래스 정보 Id
     * @return 태그 생성된 엔티티
     */
    public Tag createEntity(long entityId, long lectureInfoId) {
        return Tag.builder()
                .id(entityId)
                .lectureInformation(testLectureInformationEntityFactory.createEntity(lectureInfoId))
                .content(String.format("테스트 내용%d", entityId))
                .build();
    }
}
