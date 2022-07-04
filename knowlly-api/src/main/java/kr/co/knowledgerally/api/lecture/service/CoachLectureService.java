package kr.co.knowledgerally.api.lecture.service;

import kr.co.knowledgerally.api.lecture.component.CoachLectureMapper;
import kr.co.knowledgerally.api.lecture.component.FormMapper;
import kr.co.knowledgerally.api.lecture.component.LectureInformationMapper;
import kr.co.knowledgerally.api.lecture.component.LectureMapper;
import kr.co.knowledgerally.api.lecture.dto.CoachLectureDto;
import kr.co.knowledgerally.api.lecture.dto.FormDto;
import kr.co.knowledgerally.api.lecture.dto.LectureDto;
import kr.co.knowledgerally.api.lecture.dto.LectureRegisterDto;
import kr.co.knowledgerally.core.coach.entity.Coach;
import kr.co.knowledgerally.core.coach.service.CoachService;
import kr.co.knowledgerally.core.core.exception.BadRequestException;
import kr.co.knowledgerally.core.core.message.ErrorMessage;
import kr.co.knowledgerally.core.lecture.entity.Form;
import kr.co.knowledgerally.core.lecture.entity.Lecture;
import kr.co.knowledgerally.core.lecture.entity.LectureInformation;
import kr.co.knowledgerally.core.lecture.repository.LectureRepository;
import kr.co.knowledgerally.core.lecture.service.FormService;
import kr.co.knowledgerally.core.lecture.service.LectureInformationService;
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
public class CoachLectureService {
    private final CoachLectureMapper coachLectureMapper;
    private final LectureRepository lectureRepository;
    private final CoachService coachService;

    /**
     * 운영 클래스를 조회합니다.
     * @param loggedInUser 로그인된 사용자
     * @param state 클래스 상태, null시 전체 조회
     * @return 운영 클래스 dto 목록
     */
    @Transactional(readOnly = true)
    public List<CoachLectureDto> getCoachLecture(User loggedInUser, @Nullable Lecture.State state) {
        Coach coach = coachService.findByUser(loggedInUser);
        if (coach == null) {
            throw new BadRequestException(ErrorMessage.USER_NOT_COACH);
        }

        return getLectures(coach, state).stream()
                .map(coachLectureMapper::toDto)
                .peek(x -> x.setForms(filterLectureForms(x.getForms())))
                .peek(this::checkForms)
                .collect(Collectors.toList());
    }

    private List<Lecture> getLectures(Coach coach, Lecture.State state) {
        if (state != null) {
            return lectureRepository.findAllByCoachAndState(coach, state);
        }

        return lectureRepository.findAllByCoach(coach);
    }

    private List<FormDto.ReadOnly> filterLectureForms(List<FormDto.ReadOnly> forms) {
        return forms.stream()
                .filter(x -> {
                    if (x.getLecture().getState() == Lecture.State.ON_BOARD) { // 클래스 일정이 매칭 중일 경우
                        return x.getState() == Form.State.REQUEST; // 요청 상태만 필터링
                    } else {
                        return x.getState() == Form.State.ACCEPT; // 클래스 일정이 예정된, 완료된 일 경우, 수락 상태만 필터링
                    }
                }).collect(Collectors.toList());
    }

    private void checkForms(CoachLectureDto coachLectureDto) {
        if (coachLectureDto.getLecture().getState() != Lecture.State.ON_BOARD && coachLectureDto.getForms().size() != 1) {
            log.warn("예정된, 혹은 완료된 클래스의 신청서 숫자가 1이 아님");
        }
    }
}
