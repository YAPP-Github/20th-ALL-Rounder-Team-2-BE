package kr.co.knowledgerally.core.lecture.service;

import kr.co.knowledgerally.core.core.exception.ResourceNotFoundException;
import kr.co.knowledgerally.core.lecture.entity.Category;
import kr.co.knowledgerally.core.lecture.entity.LectureInformation;
import kr.co.knowledgerally.core.lecture.service.CategoryService;
import kr.co.knowledgerally.core.lecture.service.LectureInformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.*;

@Validated
@Service
@RequiredArgsConstructor
public class LectureInformationSearchService {

    private final LectureInformationService lectureInformationService;
    private final CategoryService categoryService;

    /**
     * keyword로 클래스-info 목록을 조회합니다.
     * @param keyword 검색하고자 하는 키워드
     * @return 클래스-info 리스트
     */
    @Transactional
    public Page<LectureInformation> searchAllByKeyword(String keyword, Pageable pageable) {
        Set<LectureInformation> result = new LinkedHashSet<>();
        Optional<Category> category = categoryService.findByName(keyword);

        if(category.isPresent()) {
            result.addAll(lectureInformationService.findAllByCategory(category.get()));
        }
        result.addAll(lectureInformationService.searchAllByTopic(keyword));
        List<LectureInformation> lectureInformationList = new ArrayList<>(result);

        return paginateResult(lectureInformationList, pageable);
    }

    @Transactional
    public Page<LectureInformation> paginateResult(List<LectureInformation> lectureInformations, Pageable pageable) {
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), lectureInformations.size());
        final Page<LectureInformation> page = new PageImpl<>(lectureInformations.subList(start, end), pageable, lectureInformations.size());
        return page;
    }
}
