package kr.co.knowledgerally.core.lecture.service;

import kr.co.knowledgerally.core.lecture.entity.Category;
import kr.co.knowledgerally.core.lecture.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    /**
     * 클래스 카테고리 목록을 조회합니다.
     * @return 카테고리 리스트
     */
    @Transactional(readOnly = true)
    public List<Category> findAll() {
        return categoryRepository.findAllByIsActive(true);
    }
}
