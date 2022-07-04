package kr.co.knowledgerally.api.lecture.util;

import kr.co.knowledgerally.api.core.util.TestDtoFactory;
import kr.co.knowledgerally.api.lecture.dto.FormDto;
import kr.co.knowledgerally.api.lecture.dto.LectureDto;
import kr.co.knowledgerally.api.user.util.TestUserDtoFactory;
import kr.co.knowledgerally.core.lecture.entity.Form;
import kr.co.knowledgerally.core.lecture.entity.Lecture;

/**
 * 테스트용 신청서 Dto 생성 팩토리
 */
public class TestFormDtoFactory implements TestDtoFactory<FormDto> {

    TestUserDtoFactory testUserDtoFactory = new TestUserDtoFactory();
    TestLectureDtoFactory testLectureDtoFactory = new TestLectureDtoFactory();

    /**
     * 테스트용 신청서 Dto를 생성한다.
     * @param id 생성될 Dto Id
     * @return 생성된 신청서 Dto
     */
    @Override
    public FormDto createDto(long id) {
        return FormDto.builder()
                .content(String.format("테스트%d의 신청 내용", id))
                .build();
    }

    /**
     * 테스트용 읽기전용 신청서 Dto를 생성한다.
     *
     * @param id 생성될 Dto Id
     * @param lectureId 생성될 Dto 내부 lectureId
     * @param userId 생성될 Dto 내부 userId
     * @return 생성된 읽기전용 신청서 Dto
     */
    public FormDto.ReadOnly createReadOnlyDto(long id, long lectureId, long userId) {
        return FormDto.ReadOnly.builder()
                .id(id)
                .content(String.format("테스트%d의 신청 내용", id))
                .user(testUserDtoFactory.createReadOnlyDto(userId))
                .lecture(testLectureDtoFactory.createReadOnlyDto(lectureId))
                .state(Form.State.REQUEST)
                .build();
    }
}
