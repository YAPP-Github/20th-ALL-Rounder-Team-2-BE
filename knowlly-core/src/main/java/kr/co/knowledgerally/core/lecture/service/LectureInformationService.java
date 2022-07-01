package kr.co.knowledgerally.core.lecture.service;

import kr.co.knowledgerally.core.coach.entity.Coach;
import kr.co.knowledgerally.core.lecture.entity.Category;
import kr.co.knowledgerally.core.lecture.entity.LectureInformation;
import kr.co.knowledgerally.core.lecture.repository.LectureInformationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<LectureInformation> findAllWithPageable(Pageable pageable) {
        return lectureInformationRepository.findAllTop10ByIsActiveOrderByIdDesc(true, pageable);
    }

    /**
     * 해당 카테고리 속하는 클래스-info 목록을 조회합니다.
     * @param category 검색하고자 하는 카테고리
     * @return 클래스-info 리스트
     */
    @Transactional
    public Page<LectureInformation> findAllByCategoryWithPageable(Category category, Pageable pageable) {
        return lectureInformationRepository.findAllByCategoryAndIsActiveOrderByIdDesc(category, true, pageable);
    }

    /**
     * 해당 카테고리 이름으로 클래스-info 목록을 검색합니다.
     * @param categoryName 검색하고자 하는 카테고리 이름
     * @return 클래스-info 리스트
     */
    @Transactional
    public List<LectureInformation> searchAllByCategoryName(String categoryName) {
        return lectureInformationRepository.findAllByCategoryCategoryNameAndIsActiveOrderByIdDesc(categoryName, true);
    }

    /**
     * 제목으로 클래스-info 목록을 검색합니다.
     * @param topic 검색하고자 하는 클래스 제목
     * @return 클래스-info 리스트
     */
    @Transactional
    public List<LectureInformation> searchAllByTopic(String topic) {
        return lectureInformationRepository.findAllByTopicContainingAndIsActiveOrderByIdDesc(topic, true);
    }
}
