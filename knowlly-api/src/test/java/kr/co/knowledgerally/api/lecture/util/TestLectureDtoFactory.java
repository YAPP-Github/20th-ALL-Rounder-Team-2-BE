package kr.co.knowledgerally.api.lecture.util;

import kr.co.knowledgerally.api.core.util.TestDtoFactory;
import kr.co.knowledgerally.api.lecture.dto.LectureDto;
import kr.co.knowledgerally.api.lecture.dto.LectureImageDto;
import kr.co.knowledgerally.api.lecture.dto.LectureInformationDto;
import kr.co.knowledgerally.core.lecture.entity.Lecture;

/**
 * 테스트용 클래스 일정 Dto 생성 팩토리
 */
public class TestLectureDtoFactory implements TestDtoFactory<LectureDto> {

    /**
     * 테스트용 클래스 일정 Dto를 생성한다.
     * @param id 생성될 Dto Id
     * @return 생성된 클래스 일정 Dto
     */
    @Override
    public LectureDto createDto(long id) {
        return LectureDto.builder()
                .startAt("2022-06-15T10:30:50")
                .endAt("2022-06-15T11:30:50")
                .build();
    }

    /**
     * 테스트용 읽기전용 클래스 info Dto를 생성한다.
     *
     * @param id 생성될 Dto Id
     * @return 생성된 읽기전용 클래스 info Dto
     */
    public LectureDto.ReadOnly createReadOnlyDto(long id) {
        return LectureDto.ReadOnly.builder()
                .id(id)
                .startAt("2022-06-15T10:30:50")
                .endAt("2022-06-15T11:30:50")
                .state(Lecture.State.ON_BOARD)
                .isReviewWritten(false)
                .build();
    }
}
