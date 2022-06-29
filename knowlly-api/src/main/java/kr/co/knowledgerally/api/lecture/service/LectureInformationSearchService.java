package kr.co.knowledgerally.api.lecture.service;

import kr.co.knowledgerally.api.user.dto.UserProfileDto;
import kr.co.knowledgerally.core.core.exception.ResourceNotFoundException;
import kr.co.knowledgerally.core.lecture.entity.Category;
import kr.co.knowledgerally.core.lecture.entity.LectureInformation;
import kr.co.knowledgerally.core.lecture.service.CategoryService;
import kr.co.knowledgerally.core.lecture.service.LectureInformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public List<LectureInformation> searchAllByKeyword(String keyword) {
        List<LectureInformation> result = new ArrayList<>();
        Optional<Category> category = categoryService.findByName(keyword);

        if(category.isPresent()) {
            result.addAll(lectureInformationService.findAllByCategory(category.get()));
        }
        result.addAll(lectureInformationService.searchAllByTopic(keyword));
        return result;
    }
}
