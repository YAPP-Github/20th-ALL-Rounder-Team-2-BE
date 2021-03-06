package kr.co.knowledgerally.api.lecture.util;

import kr.co.knowledgerally.api.core.util.TestDtoFactory;
import kr.co.knowledgerally.api.lecture.dto.CategoryDto;
import kr.co.knowledgerally.api.user.dto.UserDto;
import kr.co.knowledgerally.core.lecture.entity.Category;

/**
 * 테스트용 카테고리 Dto 생성 팩토리
 */
public class TestCategoryDtoFactory implements TestDtoFactory<CategoryDto> {

    /**
     * 테스트용 카테고리 Dto를 생성한다.
     *
     * @param id 생성될 Dto Id
     * @return 생성된 카테고리 Dto
     */
    @Override
    public CategoryDto createDto(long id) {
        return CategoryDto.builder()
                .name(Category.Name.ETC)
                .build();
    }
}
