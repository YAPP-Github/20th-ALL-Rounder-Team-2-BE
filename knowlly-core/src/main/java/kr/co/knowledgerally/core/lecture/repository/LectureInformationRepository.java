package kr.co.knowledgerally.core.lecture.repository;

import com.sun.xml.bind.v2.TODO;
import kr.co.knowledgerally.core.coach.entity.Coach;
import kr.co.knowledgerally.core.lecture.entity.Category;
import kr.co.knowledgerally.core.lecture.entity.LectureInformation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
//TODO: N+1 문제 해결을 위한 join fetch 도입
public interface LectureInformationRepository extends JpaRepository<LectureInformation, Long> {
    /**
     * 클래스-info들을 활성화 여부로 검색
     * @param isActive 활성화 여부
     * @return 클래스-info List
     */
    List<LectureInformation> findAllTop10ByIsActiveOrderByIdDesc(boolean isActive);

    /**
     * 클래스-info들을 카테고리와 활성화 여부로 검색
     * @param isActive 활성화 여부
     * @param category 카테고리
     * @return 클래스-info List
     */
    List<LectureInformation> findAllByCategoryAndIsActiveOrderByIdDesc(Category category, boolean isActive);

    /**
     * 클래스-info들을 topic과 활성화 여부로 검색
     * @param isActive 활성화 여부
     * @param topic 제목
     * @return 클래스-info List
     */
    List<LectureInformation> findAllByTopicContainingAndIsActiveOrderByIdDesc(@Param("topic") String topic, boolean isActive);
}