package kr.co.knowledgerally.core.lecture.repository;

import kr.co.knowledgerally.core.lecture.entity.Category;
import kr.co.knowledgerally.core.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * 카테고리를 활성화 여부로 검색
     * @param isActive 활성화 여부
     * @return 카테고리 Optional
     */
    List<Category> findAllByIsActive(boolean isActive);
}