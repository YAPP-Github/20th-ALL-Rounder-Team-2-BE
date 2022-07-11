package kr.co.knowledgerally.api.lecture.service;

import kr.co.knowledgerally.api.lecture.component.CoachLectureMapper;
import kr.co.knowledgerally.api.lecture.component.FormMapper;
import kr.co.knowledgerally.api.lecture.component.LectureInformationMapper;
import kr.co.knowledgerally.api.lecture.component.LectureMapper;
import kr.co.knowledgerally.api.lecture.dto.*;
import kr.co.knowledgerally.core.coach.entity.Coach;
import kr.co.knowledgerally.core.coach.service.CoachService;
import kr.co.knowledgerally.core.core.exception.BadRequestException;
import kr.co.knowledgerally.core.core.message.ErrorMessage;
import kr.co.knowledgerally.core.lecture.entity.Form;
import kr.co.knowledgerally.core.lecture.entity.Lecture;
import kr.co.knowledgerally.core.lecture.entity.LectureInformation;
import kr.co.knowledgerally.core.lecture.repository.LectureInformationRepository;
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
    private final LectureInformationMapper lectureInformationMapper;
    private final LectureRepository lectureRepository;
    private final LectureInformationRepository lectureInformationRepository;
    private final CoachService coachService;

    /**
     * 운영 클래스를 조회합니다.
     * @param loggedInUser 로그인된 사용자
     * @param state 클래스 상태, null시 전체 조회
     * @return 운영 클래스 dto 목록
     */
    @Transactional(readOnly = true)
    public List<LectureInformationDtoReadOnly> getCoachLecture(User loggedInUser, @Nullable Lecture.State state) {
        Coach coach = coachService.findByUser(loggedInUser);
        if (coach == null) {
            throw new BadRequestException(ErrorMessage.USER_NOT_COACH);
        }

        return getLectures(coach, state).stream()
                .peek(x -> {
                    for (Lecture lecture : x.getLectures()) {
                        lecture.setForms(filterLectureForms(lecture.getForms()));
                        checkForms(lecture);
                    }
                })
                .map(lectureInformationMapper::toDto)
                .collect(Collectors.toList());
    }

    private List<LectureInformation> getLectures(Coach coach, Lecture.State state) {
        if (state != null) {
            return lectureInformationRepository.findAllByCoachAndState(coach, state);
        }

        return lectureInformationRepository.findAllByCoach(coach);
    }

    private List<Form> filterLectureForms(List<Form> forms) {
        return forms.stream()
                .filter(x -> {
                    if (x.getLecture().getState() == Lecture.State.ON_BOARD) { // 클래스 일정이 매칭 중일 경우
                        return x.getState() == Form.State.REQUEST; // 요청 상태만 필터링
                    } else {
                        return x.getState() == Form.State.ACCEPT; // 클래스 일정이 예정된, 완료된 일 경우, 수락 상태만 필터링
                    }
                }).collect(Collectors.toList());
    }

    private void checkForms(Lecture lecture) {
        if (lecture.getState() != Lecture.State.ON_BOARD && lecture.getForms().size() != 1) {
            log.warn("예정된, 혹은 완료된 클래스의 신청서 숫자가 1이 아님. lectureId : {}, form count : {}",
                    lecture.getId(), lecture.getForms().size());
        }
    }
}
