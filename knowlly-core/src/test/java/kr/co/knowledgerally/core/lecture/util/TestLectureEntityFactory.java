package kr.co.knowledgerally.core.lecture.util;

import kr.co.knowledgerally.core.core.util.TestEntityFactory;
import kr.co.knowledgerally.core.lecture.entity.Lecture;
import kr.co.knowledgerally.core.lecture.entity.LectureInformation;

import java.time.LocalDateTime;

/**
 * 테스트용 강의 엔티티 생성 팩토리
 */
public class TestLectureEntityFactory implements TestEntityFactory<Lecture> {
    private final TestEntityFactory<LectureInformation> testLectureInformationEntityFactory = new TestLectureInformationEntityFactory();

    /**
     * 테스트용 강의 엔티티를 생성한다.
     *
     * @param entityId 생성될 엔티티 Id
     * @return 생성된 강의 엔티티
     */
    @Override
    public Lecture createEntity(long entityId) {
        return createEntity(entityId, 1L);
    }

    /**
     * 테스트용 강의 엔티티를 생성한다.
     *
     * @param entityId      생성될 엔티티 Id
     * @param lectureInfoId 생성될 엔티티의 강의 정보 Id
     * @return 생성된 강의 엔티티
     */
    public Lecture createEntity(long entityId, long lectureInfoId) {
        return Lecture.builder()
                .id(entityId)
                .lectureInformation(testLectureInformationEntityFactory.createEntity(lectureInfoId))
                .startAt(LocalDateTime.of(2022, 6, 15, 10, 30, 50))
                .endAt(LocalDateTime.of(2022, 6, 15, 11, 30, 50))
                .build();
    }
}
