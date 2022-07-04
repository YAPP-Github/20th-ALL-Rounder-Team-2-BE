package kr.co.knowledgerally.core.lecture.repository;

import kr.co.knowledgerally.core.lecture.entity.Form;
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
}