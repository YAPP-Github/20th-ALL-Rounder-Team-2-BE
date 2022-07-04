package kr.co.knowledgerally.api.lecture.service;

import kr.co.knowledgerally.api.lecture.component.CoachLectureMapper;
import kr.co.knowledgerally.api.lecture.component.UserLectureMapper;
import kr.co.knowledgerally.api.lecture.dto.CoachLectureDto;
import kr.co.knowledgerally.api.lecture.dto.FormDto;
import kr.co.knowledgerally.api.lecture.dto.UserLectureDto;
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
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserLectureService {
    private final FormService formService;
    private final UserLectureMapper userLectureMapper;

    /**
     * 수강 클래스를 조회합니다.
     * @param loggedInUser 로그인된 사용자
     * @param state 클래스 상태, null시 전체 조회
     * @return 운영 클래스 dto 목록
     */
    @Transactional(readOnly = true)
    public List<UserLectureDto> getUserLecture(User loggedInUser, @Nullable Lecture.State state) {
        return getForms(loggedInUser, state).stream()
                .map(userLectureMapper::toDto)
                .collect(Collectors.toList());
    }

    private List<Form> getForms(User user, Lecture.State state) {
        if (state != null) {
            return formService.findAllByUserAndLectureState(user, state);
        }
        return formService.findAllByUser(user);
    }
}
