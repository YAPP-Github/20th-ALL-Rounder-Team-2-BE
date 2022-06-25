package kr.co.knowledgerally.core.lecture.service;

import kr.co.knowledgerally.core.lecture.entity.Category;
import kr.co.knowledgerally.core.lecture.entity.LectureInformation;
import kr.co.knowledgerally.core.lecture.repository.LectureInformationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
@Service
@RequiredArgsConstructor
public class LectureInformationService {
    private final LectureInformationRepository lectureInformationRepository;

    /**
     * 클래스-info 목록을 조회합니다.
     * @return 클래스-info 리스트
     */
    @Transactional
    public List<LectureInformation> findAll() {
        return lectureInformationRepository.findAllByIsActiveOrderByIdDesc(true);
    }

    /**
     * 카테고리에 해당하는 클래스-info 목록을 조회합니다.
     * @param category 검색하고자 하는 카테고리
     * @return 클래스-info 리스트
     */
    @Transactional
    public List<LectureInformation> findAllByCategory(Category category) {
        return lectureInformationRepository.findAllByCategoryAndIsActiveOrderByIdDesc(category, true);
    }
}
