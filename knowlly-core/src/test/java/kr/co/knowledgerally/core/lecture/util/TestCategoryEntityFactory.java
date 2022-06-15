package kr.co.knowledgerally.core.lecture.util;

import kr.co.knowledgerally.core.core.util.TestEntityFactory;
import kr.co.knowledgerally.core.lecture.entity.Category;

/**
 * 테스트용 카테고리 엔티티 생성 팩토리
 */
public class TestCategoryEntityFactory implements TestEntityFactory<Category> {

    /**
     * 테스트용 카테고리 엔티티를 생성한다.
     *
     * @param entityId 생성될 엔티티 Id
     * @return 생성된 카테고리 엔티티
     */
    @Override
    public Category createEntity(long entityId) {
        return Category.builder()
                .id(entityId)
                .categoryName(String.format("테스트 카테고리%d", entityId))
                .build();
    }
}
