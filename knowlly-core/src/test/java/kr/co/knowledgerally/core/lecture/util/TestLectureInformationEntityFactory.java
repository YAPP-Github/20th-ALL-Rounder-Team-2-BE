package kr.co.knowledgerally.core.lecture.util;

import kr.co.knowledgerally.core.coach.entity.Coach;
import kr.co.knowledgerally.core.core.util.TestEntityFactory;
import kr.co.knowledgerally.core.lecture.entity.*;
import kr.co.knowledgerally.core.coach.util.TestCoachEntityFactory;

import java.time.LocalDateTime;
import java.util.*;

/**
 * 테스트용 강의정보 엔티티 생성 팩토리
 */
public class TestLectureInformationEntityFactory implements TestEntityFactory<LectureInformation> {
    private final TestCoachEntityFactory testCoachEntityFactory = new TestCoachEntityFactory();
    private final TestEntityFactory<Category> testCategoryEntityFactory = new TestCategoryEntityFactory();

    /**
     * 테스트용 강의정보 엔티티를 생성한다.
     *
     * @param entityId 생성될 엔티티 Id
     * @return 생성된 강의정보 엔티티
     */
    @Override
    public LectureInformation createEntity(long entityId) {
        return createEntity(entityId, 1L, 1L,1L, 2);
    }

    /**
     * 테스트용 강의정보 엔티티를 생성한다.
     *
     * @param entityId   생성될 엔티티 Id
     * @param coachId    생성될 엔티티의 코치 Id
     * @param categoryId 생성될 엔티티 카테고리 Id
     * @return 생성된 강의정보 엔티티
     */
    public LectureInformation createEntity(long entityId, long coachId, long categoryId, long userId, long lectureImageNum) {
        Set<LectureImage> lectureImages = new LinkedHashSet<>();
        Set<Tag> tags = new LinkedHashSet<>();
        List<Lecture> lectures = new ArrayList<>();

        tags.add(Tag.builder()
                .lectureInformation(LectureInformation.builder().build())
                .build());

        lectures.add(Lecture.builder()
                .lectureInformation(LectureInformation.builder().build())
                .startAt(LocalDateTime.of(2022, 6, 15, 10, 30, 50))
                .endAt(LocalDateTime.of(2022, 6, 15, 11, 30, 50))
                .build());

        for (long index=1; index<=lectureImageNum; index++) {
            lectureImages.add(
                    LectureImage.builder()
                            .id(index)
                            .lectureInformation(LectureInformation.builder().build())
                            .lectureImgUrl(String.format("http://lecture%d.img%d.url", entityId, index))
                            .build()
            );
        }

        return LectureInformation.builder()
                .id(entityId)
                .coach(testCoachEntityFactory.createEntity(coachId, userId))
                .category(testCategoryEntityFactory.createEntity(categoryId))
                .lectureImages(lectureImages)
                .tags(tags)
                .lectures(lectures)
                .topic(String.format("테스트%d 제목", entityId))
                .introduce(String.format("안녕하세요. 테스트%d 입니다.", entityId))
                .price(1)
                .build();
    }

    public LectureInformation createEntityWithoutCoach(long entityId, long categoryId, long lectureImageNum) {
        Set<LectureImage> lectureImages = new LinkedHashSet<>();
        Set<Tag> tags = new LinkedHashSet<>();
        List<Lecture> lectures = new ArrayList<>();

        tags.add(Tag.builder()
                .lectureInformation(LectureInformation.builder().build())
                .content("test tag")
                .build());

        lectures.add(Lecture.builder()
                .lectureInformation(LectureInformation.builder().build())
                .startAt(LocalDateTime.of(2022, 6, 15, 10, 30, 50))
                .endAt(LocalDateTime.of(2022, 6, 15, 11, 30, 50))
                .build());

        for (long index=1; index<=lectureImageNum; index++) {
            lectureImages.add(
                    LectureImage.builder()
                            .id(index)
                            .lectureInformation(LectureInformation.builder().build())
                            .lectureImgUrl(String.format("http://lecture%d.img%d.url", entityId, index))
                            .build()
            );
        }

        return LectureInformation.builder()
                .id(entityId)
                .coach(Coach.builder().build())
                .category(testCategoryEntityFactory.createEntity(categoryId))
                .lectureImages(lectureImages)
                .tags(tags)
                .lectures(lectures)
                .topic(String.format("테스트%d 제목", entityId))
                .introduce(String.format("안녕하세요. 테스트%d 입니다.", entityId))
                .price(1)
                .build();
    }
}
