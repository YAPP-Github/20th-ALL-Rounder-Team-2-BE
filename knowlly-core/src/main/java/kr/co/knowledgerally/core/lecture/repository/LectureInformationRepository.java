package kr.co.knowledgerally.core.lecture.repository;

import kr.co.knowledgerally.core.lecture.entity.Category;
import kr.co.knowledgerally.core.lecture.entity.LectureInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectureInformationRepository extends JpaRepository<LectureInformation, Long> {
    /**
     * 클래스-info들을 활성화 여부로 검색
     * @param isActive 활성화 여부
     * @return 클래스-info List
     */
    List<LectureInformation> findAllByIsActiveOrderByIdDesc(boolean isActive);

    /**
     * 클래스-info들을 카테고리와 활성화 여부로 검색
     * @param isActive 활성화 여부
     * @param category 카테고리
     * @return 클래스-info List
     */
    List<LectureInformation> findAllByCategoryAndIsActiveOrderByIdDesc(Category category, boolean isActive);
}