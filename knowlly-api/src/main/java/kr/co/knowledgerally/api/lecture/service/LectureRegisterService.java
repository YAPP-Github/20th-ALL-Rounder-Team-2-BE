package kr.co.knowledgerally.api.lecture.service;

import kr.co.knowledgerally.api.lecture.component.LectureMapper;
import kr.co.knowledgerally.api.lecture.dto.LectureDto;
import kr.co.knowledgerally.api.lecture.dto.LectureRegisterDto;
import kr.co.knowledgerally.core.coach.entity.Coach;
import kr.co.knowledgerally.core.coach.service.CoachService;
import kr.co.knowledgerally.core.core.exception.BadRequestException;
import kr.co.knowledgerally.core.lecture.entity.Lecture;
import kr.co.knowledgerally.core.lecture.entity.LectureInformation;
import kr.co.knowledgerally.core.lecture.repository.LectureInformationRepository;
import kr.co.knowledgerally.core.lecture.repository.LectureRepository;
import kr.co.knowledgerally.core.lecture.service.LectureInformationService;
import kr.co.knowledgerally.core.user.entity.User;
import kr.co.knowledgerally.core.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LectureRegisterService {
    private final LectureMapper lectureMapper;
    private final LectureInformationService lectureInformationService;
    private final LectureRepository lectureRepository;
    private final CoachService coachService;

    @Transactional
    public List<LectureDto.ReadOnly> register(Long lectureInfoId, LectureRegisterDto registerDto, User loggedInUser) {
        LectureInformation lectureInformation = lectureInformationService.findById(lectureInfoId);
        checkIsValidUser(lectureInformation, loggedInUser);

        List<Lecture> newLectures = registerDto.getSchedules().stream()
                .map(lectureMapper::toEntity)
                .peek(this::checkIsStartBeforeEnd)
                .peek(lecture -> lecture.setLectureInformation(lectureInformation))
                .collect(Collectors.toList());
        return lectureRepository.saveAllAndFlush(newLectures).stream()
                .map(lectureMapper::toDto)
                .collect(Collectors.toList());
    }

    private void checkIsValidUser(LectureInformation lectureInformation, User loggedInUser) {
        if (! Objects.equals(lectureInformation.getCoach().getUser().getId(), loggedInUser.getId())) {
            throw new BadRequestException("해당 클래스-info의 주인이 아닙니다.");
        }
    }

    private void checkIsStartBeforeEnd(Lecture lecture) {
        if (lecture.getStartAt().isAfter(lecture.getEndAt())) {
            throw new BadRequestException("시작일이 종료일보다 늦습니다.");
        }
    }
}
