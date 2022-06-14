package kr.co.knowledgerally.core.core.util;

/**
 * 테스트용 엔티티 생성 팩토리 인터페이스
 * @param <T> 엔티티 클래스 제네릭
 */
public interface TestEntityFactory<T> {
    /**
     * 테스트용 엔티티를 생성한다.
     * @param entityId 생성될 엔티티 Id
     * @return 생성된 엔티티
     */
    T createEntity(long entityId);
}
