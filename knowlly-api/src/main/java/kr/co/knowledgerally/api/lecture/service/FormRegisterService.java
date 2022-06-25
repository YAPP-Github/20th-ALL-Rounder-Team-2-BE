package kr.co.knowledgerally.api.lecture.service;

import kr.co.knowledgerally.api.lecture.component.FormMapper;
import kr.co.knowledgerally.api.lecture.component.LectureMapper;
import kr.co.knowledgerally.api.lecture.dto.FormDto;
import kr.co.knowledgerally.api.lecture.dto.LectureDto;
import kr.co.knowledgerally.api.lecture.dto.LectureRegisterDto;
import kr.co.knowledgerally.core.coach.service.CoachService;
import kr.co.knowledgerally.core.core.exception.BadRequestException;
import kr.co.knowledgerally.core.lecture.entity.Form;
import kr.co.knowledgerally.core.lecture.entity.Lecture;
import kr.co.knowledgerally.core.lecture.entity.LectureInformation;
import kr.co.knowledgerally.core.lecture.repository.LectureRepository;
import kr.co.knowledgerally.core.lecture.service.FormService;
import kr.co.knowledgerally.core.lecture.service.LectureInformationService;
import kr.co.knowledgerally.core.lecture.service.LectureService;
import kr.co.knowledgerally.core.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FormRegisterService {
    private final FormService formService;
    private final FormMapper formMapper;
    private final LectureService lectureService;

    public FormDto.ReadOnly register(Long lectureId, FormDto formDto, User loggedInUser) {
        Form newForm = formMapper.toEntity(formDto);
        newForm.setLecture(lectureService.findById(lectureId));
        newForm.setUser(loggedInUser);
        setFormExpirationDate(newForm);

        // TODO 볼 차감

        return formMapper.toDto(formService.saveForm(newForm));
    }

    private void setFormExpirationDate(Form newForm) {
        LocalDateTime expirationDate = newForm.getCreatedAt().plusDays(3);
        if (expirationDate.isBefore(newForm.getLecture().getStartAt())) {
            expirationDate = newForm.getLecture().getStartAt();
        }
        newForm.setExpirationDate(expirationDate);
    }
}
