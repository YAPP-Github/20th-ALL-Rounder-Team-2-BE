package kr.co.knowledgerally.core.lecture.repository;

import kr.co.knowledgerally.core.coach.entity.Coach;
import kr.co.knowledgerally.core.lecture.entity.Category;
import kr.co.knowledgerally.core.lecture.entity.Lecture;
import kr.co.knowledgerally.core.lecture.entity.LectureInformation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

//TODO: N+1 문제 해결을 위한 join fetch 도입
public interface LectureInformationRepository extends
        JpaRepository<LectureInformation, Long>, ExtendedLectureInformationRepository {

    /**
     * 클래스-info들을 활성화 여부로 검색
     * @param isActive 활성화 여부
     * @param pageable 페이징 객체
     * @return 클래스-info 페이징 객체
     */
    @Query(value = "select distinct li from LectureInformation li " +
            "left join fetch li.lectures lecs " +
            "join fetch li.category join fetch li.coach co join fetch co.user " +
            "left join li.lectureImages imgs left join li.tags t " +
            "where li.isActive = true " +
            "and (lecs.isActive = true or lecs.isActive is null)" +
            "and (imgs.isActive = true or imgs.isActive is null) " +
            "and (t.isActive = true or t.isActive is null) " +
            "order by li.id desc",
    countQuery = "select count(distinct li) from LectureInformation li " +
            "left join li.lectures lecs " +
            "join li.category join li.coach co join co.user " +
            "left join li.lectureImages imgs left join li.tags t " +
            "where li.isActive = true " +
            "and (lecs.isActive = true or lecs.isActive is null)" +
            "and (imgs.isActive = true or imgs.isActive is null) " +
            "and (t.isActive = true or t.isActive is null) ")
    Page<LectureInformation> findAllTop10ByIsActiveOrderByIdDesc(boolean isActive, Pageable pageable);

    /**
     * 클래스-info들을 카테고리와 활성화 여부로 검색
     * @param isActive 활성화 여부
     * @param category 카테고리
     * @param pageable 페이징 객체
     * @return 클래스-info 페이징 객체
     */
    Page<LectureInformation> findAllByCategoryAndIsActiveOrderByIdDesc(Category category, boolean isActive, Pageable pageable);

    /**
     * 클래스-info들을 카테고리 이름과 활성화 여부로 검색
     * @param isActive 활성화 여부
     * @param name 카테고리 이름
     * @return 클래스-info List
     */
    List<LectureInformation> findAllByCategoryNameAndIsActiveOrderByIdDesc(Category.Name name, boolean isActive);

    /**
     * 클래스-info들을 topic과 활성화 여부로 검색
     * @param isActive 활성화 여부
     * @param topic 제목
     * @return 클래스-info List
     */
    List<LectureInformation> findAllByTopicContainingAndIsActiveOrderByIdDesc(@Param("topic") String topic, boolean isActive);

    /**
     * 클래스-info를 활성화 여부로 검색
     * @param lectureInfoId 클래스-info id
     * @param isActive 활성화 여부
     * @return 클래스-info 객체
     */
    Optional<LectureInformation> findByIdAndIsActive(Long lectureInfoId, boolean isActive);
}