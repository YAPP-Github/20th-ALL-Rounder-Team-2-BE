package kr.co.knowledgerally.core.lecture.repository;

import kr.co.knowledgerally.core.coach.entity.Coach;
import kr.co.knowledgerally.core.lecture.entity.Form;
import kr.co.knowledgerally.core.lecture.entity.Lecture;
import kr.co.knowledgerally.core.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FormRepository extends JpaRepository<Form, Long> {
    /**
     * 사용자로 신청서 목록 조회
     * @param user 사용자
     * @param isActive 활성화 여부
     * @return 신청서 목록
     */
    List<Form> findAllByUserAndIsActiveOrderByCreatedAtDesc(User user, boolean isActive);

    /**
     * 사용자로 신청서 목록 조회
     * @param user 사용자
     * @param state 신청서 상태
     * @param isActive 활성화 여부
     * @return 신청서 목록
     */
    List<Form> findAllByUserAndStateAndIsActiveOrderByCreatedAtDesc(User user, Form.State state, boolean isActive);

    /**
     * 사용자로 신청서 목록 조회
     * @param user 사용자
     * @param state 클래스 일정 상태
     * @param isActive 활성화 여부
     * @return 신청서 목록
     */
    List<Form> findAllByUserAndLecture_StateAndIsActiveOrderByCreatedAtDesc(User user, Lecture.State state, boolean isActive);

    /**
     * 코치 앞으로 온 신청서 목록 조회
     * @param coach 코치
     * @param isActive 활성화 여부
     * @return 신청서 목록
     */
    List<Form> findAllByLecture_LectureInformation_CoachAndIsActiveOrderByCreatedAtDesc(Coach coach, boolean isActive);

    /**
     * 클래스 일정으로 신청서 목록 조회
     * @param lecture 클래스 일정
     * @param isActive 활성화 여부
     * @return 신청서 목록
     */
    List<Form> findAllByLectureAndIsActiveOrderByCreatedAtDesc(Lecture lecture, boolean isActive);
}