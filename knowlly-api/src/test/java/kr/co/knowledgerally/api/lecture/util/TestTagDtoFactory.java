package kr.co.knowledgerally.api.lecture.util;

import kr.co.knowledgerally.api.core.util.TestDtoFactory;
import kr.co.knowledgerally.api.lecture.dto.TagDto;

/**
 * 테스트용 태그 Dto 생성 팩토리
 */
public class TestTagDtoFactory implements TestDtoFactory<TagDto> {
    /**
     * 테스트용 태그 Dto를 생성한다.
     *
     * @param id 생성될 Dto Id
     * @return 생성된 태그 Dto
     */
    @Override
    public TagDto.ReadOnly createDto(long id) {
        return TagDto.ReadOnly.builder()
                .id(id)
                .content(String.format("테스트 내용%d", id))
                .build();

    }
}
