package kr.co.knowledgerally.core.lecture.repository;

import kr.co.knowledgerally.core.coach.entity.Coach;
import kr.co.knowledgerally.core.lecture.entity.Lecture;
import kr.co.knowledgerally.core.lecture.entity.LectureInformation;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExtendedLectureInformationRepository {
    /**
     * 코치로 모든 클래스-info와 연관 정보들을 찾습니다.
     * @param coach 코치 객체
     * @return 클래스-info 목록
     */
    List<LectureInformation> findAllByCoach(Coach coach);

    /**
     * 코치와 클래스 상태로 모든 클래스-info와 연관 정보들을 찾습니다.
     * @param coach 코치 객체
     * @param state 상태 enum 값
     * @return 클래스-info 목록
     */
    List<LectureInformation> findAllByCoachAndState(Coach coach, Lecture.State state);
}
