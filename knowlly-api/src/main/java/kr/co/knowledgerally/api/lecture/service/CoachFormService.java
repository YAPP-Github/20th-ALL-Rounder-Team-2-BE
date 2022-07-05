package kr.co.knowledgerally.api.lecture.service;

import kr.co.knowledgerally.api.lecture.component.CoachLectureMapper;
import kr.co.knowledgerally.api.lecture.component.FormMapper;
import kr.co.knowledgerally.api.lecture.dto.CoachLectureDto;
import kr.co.knowledgerally.api.lecture.dto.FormDto;
import kr.co.knowledgerally.core.coach.entity.Coach;
import kr.co.knowledgerally.core.coach.service.CoachService;
import kr.co.knowledgerally.core.core.exception.BadRequestException;
import kr.co.knowledgerally.core.core.message.ErrorMessage;
import kr.co.knowledgerally.core.lecture.entity.Form;
import kr.co.knowledgerally.core.lecture.entity.Lecture;
import kr.co.knowledgerally.core.lecture.repository.LectureRepository;
import kr.co.knowledgerally.core.lecture.service.FormService;
import kr.co.knowledgerally.core.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CoachFormService {
    private final CoachService coachService;
    private final FormService formService;
    private final FormMapper formMapper;

    /**
     * 신청서의 상태를 변경합니다.
     * @param formId 신청서 ID
     * @param state 변경할 신청서 상태
     * @param loggedInUser 로그인한 사용자
     * @return 변경된 신청서 결과 읽기 모델
     */
    @Transactional
    public FormDto.ReadOnly setFormState(Long formId, Form.State state, User loggedInUser) {
        if (state == null || state == Form.State.REQUEST) {
            throw new BadRequestException(ErrorMessage.FORM_STATE_NOT_VALID);
        }

        Coach coach = coachService.findByUser(loggedInUser);
        if (coach == null) {
            throw new BadRequestException(ErrorMessage.USER_NOT_COACH);
        }

        Form form = formService.findById(formId);
        if (isNotFormLectureBelongToCoach(form, coach)) {
            throw new BadRequestException(ErrorMessage.FORM_IS_NOT_BELONG_TO_THIS_COACH);
        }

        form.setState(state);
        return formMapper.toDto(formService.saveForm(form));
    }

    private boolean isNotFormLectureBelongToCoach(Form form, Coach coach) {
        return ! Objects.equals(form.getLecture().getLectureInformation().getCoach().getId(), coach.getId());
    }
}
