package kr.co.knowledgerally.core.lecture.service;

import kr.co.knowledgerally.core.coach.entity.Coach;
import kr.co.knowledgerally.core.core.exception.ResourceNotFoundException;
import kr.co.knowledgerally.core.core.message.ErrorMessage;
import kr.co.knowledgerally.core.lecture.entity.Form;
import kr.co.knowledgerally.core.lecture.entity.Lecture;
import kr.co.knowledgerally.core.lecture.repository.FormRepository;
import kr.co.knowledgerally.core.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
@Service
@RequiredArgsConstructor
public class FormService {
    private final FormRepository formRepository;

    /**
     * id로 신청서를 조회합니다.
     * @param id 신청서 id
     * @return 신청서 객체
     * @throws ResourceNotFoundException 해당하는 id가 존재하지 않을 때
     */
    @Transactional(readOnly = true)
    public Form findById(long id) throws ResourceNotFoundException {
        return formRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.NOT_EXIST_FORM));
    }

    /**
     * 사용자로 신청서 목록을 조회합니다.
     * @param user 사용자
     * @return 신청서 리스트
     */
    @Transactional(readOnly = true)
    public List<Form> findAllByUser(User user) {
        return formRepository.findAllByUserAndIsActiveOrderByCreatedAtDesc(user, true);
    }

    /**
     * 사용자로 신청서 목록을 조회합니다.
     * @param user 사용자
     * @param state 신청서 상태
     * @return 신청서 리스트
     */
    @Transactional(readOnly = true)
    public List<Form> findAllByUserAndState(User user, Form.State state) {
        return formRepository.findAllByUserAndStateAndIsActiveOrderByCreatedAtDesc(user, state,true);
    }

    /**
     * 신청서를 저장합니다.
     * @param form 저장하고자 하는 사용자 엔티티
     * @return 저장된 신청서 엔티티
     */
    @Transactional
    public Form saveForm(@Valid Form form) {
        return formRepository.saveAndFlush(form);
    }

     /**
     * 사용자로 신청서 목록을 조회합니다.
     * @param user 사용자
     * @param state 클래스 일정 상태
     * @return 신청서 리스트
     */
    @Transactional(readOnly = true)
    public List<Form> findAllByUserAndLectureState(User user, Lecture.State state) {
        return formRepository.findAllByUserAndLecture_StateAndIsActiveOrderByCreatedAtDesc(user, state,true);
    }

    /**
     * 코치로 신청서 목록을 조회합니다.
     * @param coach 코치
     * @return 신청서 리스트
     */
    @Transactional(readOnly = true)
    public List<Form> findAllByLectureCoach(Coach coach) {
        return formRepository.findAllByLecture_LectureInformation_CoachAndIsActiveOrderByCreatedAtDesc(coach, true);
    }

    /**
     * 클래스 일정으로 신청서 목록을 조회합니다.
     * @param lecture 클래스 일정
     * @return 신청서 리스트
     */
    @Transactional(readOnly = true)
    public  List<Form> findAllByLecture(Lecture lecture) {
        return formRepository.findAllByLectureAndIsActiveOrderByCreatedAtDesc(lecture, true);
    }

}
