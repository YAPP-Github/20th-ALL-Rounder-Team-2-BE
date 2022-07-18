package kr.co.knowledgerally.core.lecture.repository;

import kr.co.knowledgerally.core.coach.entity.Coach;
import kr.co.knowledgerally.core.lecture.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.OrderBy;
import java.util.List;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
    /**
     * 코치 정보로 클래스 일정 목록을 찾습니다.
     * @param coach 코치 엔티티
     * @return 클래스 일정 목록
     */
    @Query("select distinct lec from Lecture lec " +
            "left join fetch lec.forms f left join fetch f.user " +
            "join fetch lec.lectureInformation li join fetch li.category join fetch li.coach co join fetch co.user " +
            "where lec.lectureInformation.coach = :coach " +
            "and lec.isActive = true " +
            "and (f.isActive = true or f.isActive is null) " +
            "order by lec.createdAt desc, f.createdAt desc")
    List<Lecture> findAllByCoach(Coach coach);

    /**
     * 코치 정보로 클래스 일정 목록을 찾습니다.
     * @param coach 코치 엔티티
     * @param state 클래스 상태
     * @return 클래스 일정 목록
     */
    @Query("select distinct lec from Lecture lec " +
            "left join fetch lec.forms f left join fetch f.user " +
            "join fetch lec.lectureInformation li join fetch li.category join fetch li.coach co join fetch co.user " +
            "where lec.lectureInformation.coach = :coach " +
            "and lec.state = :state " +
            "and lec.isActive = true " +
            "and (f.isActive = true or f.isActive is null) " +
            "order by lec.createdAt desc, f.createdAt desc")
    List<Lecture> findAllByCoachAndState(Coach coach, Lecture.State state);
}