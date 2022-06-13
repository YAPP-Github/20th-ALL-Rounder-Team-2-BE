package kr.co.knowledgerally.api.core.util;

/**
 * 테스트용 Dto 생성 팩토리 인터페이스
 * @param <T> 엔티티 클래스 제네릭
 */
public interface TestDtoFactory<T> {
    /**
     * 테스트용 Dto를 생성한다.
     * @param id 생성될 Dto Id
     * @return 생성된 Dto
     */
    T createDto(long id);
}
