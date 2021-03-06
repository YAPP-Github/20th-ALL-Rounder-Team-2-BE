package kr.co.knowledgerally.api.lecture.util;

import kr.co.knowledgerally.api.coach.util.TestCoachDtoFactory;
import kr.co.knowledgerally.api.core.util.TestDtoFactory;
import kr.co.knowledgerally.api.lecture.dto.CategoryDto;
import kr.co.knowledgerally.api.lecture.dto.LectureImageDto;
import kr.co.knowledgerally.api.lecture.dto.LectureInformationDto;
import kr.co.knowledgerally.api.lecture.dto.LectureInformationDtoReadOnly;
import kr.co.knowledgerally.core.lecture.entity.Category;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 테스트용 클래스 info Dto 생성 팩토리
 */
public class TestLectureInformationDtoFactory implements TestDtoFactory<LectureInformationDto> {

    private final TestCoachDtoFactory testCoachDtoFactory = new TestCoachDtoFactory();
    private final TestDtoFactory<CategoryDto> testCategoryDtoFactory = new TestCategoryDtoFactory();
    private final TestLectureImageDtoFactory testLectureImageDtoFactory = new TestLectureImageDtoFactory();

    /**
     * 테스트용 클래스 info Dto를 생성한다.
     *
     * @param id 생성될 Dto Id
     * @return 생성된 클래스 info Dto
     */
    @Override
    public LectureInformationDto createDto(long id) {
        return LectureInformationDto.builder()
                .topic("테스트1 제목")
                .introduce("안녕하세요. 테스트1 입니다.")
                .category(Category.Name.ETC)
                .build();
    }

    /**
     * 테스트용 읽기전용 클래스 info Dto를 생성한다.
     *
     * @param id 생성될 Dto Id
     * @return 생성된 읽기전용 클래스 info Dto
     */
    public LectureInformationDtoReadOnly createReadOnlyDto(long id) {

        Set<LectureImageDto.ReadOnly> lectureImageDtos = new LinkedHashSet<>();
        lectureImageDtos.add(testLectureImageDtoFactory.createReadonlyDto(1L));

        return LectureInformationDtoReadOnly.builder()
                .id(id)
                .topic("테스트1 제목")
                .introduce("안녕하세요. 테스트1 입니다.")
                .price(1)
                .coach(testCoachDtoFactory.createReadOnlyDto(1L))
                .category(Category.Name.ETC)
                .lectureImages(lectureImageDtos)
                .build();
    }
}
