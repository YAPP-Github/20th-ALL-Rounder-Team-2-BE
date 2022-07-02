package kr.co.knowledgerally.api.lecture.util;

import kr.co.knowledgerally.api.core.util.TestDtoFactory;
import kr.co.knowledgerally.api.lecture.dto.LectureImageDto;

/**
 * 테스트용 클래스 이미지 Dto 생성 팩토리
 */
public class TestLectureImageDtoFactory implements TestDtoFactory<LectureImageDto> {

    /**
     * 테스트용 클래스 이미지 Dto를 생성한다.
     * @param id 생성될 Dto Id
     * @return 생성된 클래스 이미지 Dto
     */
    @Override
    public LectureImageDto createDto(long id) {
        return LectureImageDto.builder()
                .id(id)
                .lectureImgUrl(String.format("http://lecture.img%d.url", id))
                .build();
    }
}
