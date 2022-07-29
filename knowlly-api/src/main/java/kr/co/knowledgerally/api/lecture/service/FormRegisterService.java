package kr.co.knowledgerally.api.lecture.service;

import kr.co.knowledgerally.api.lecture.component.FormMapper;
import kr.co.knowledgerally.api.lecture.dto.FormDto;
import kr.co.knowledgerally.core.lecture.event.FormRegisterEvent;
import kr.co.knowledgerally.core.lecture.entity.Form;
import kr.co.knowledgerally.core.lecture.entity.Lecture;
import kr.co.knowledgerally.core.lecture.service.FormService;
import kr.co.knowledgerally.core.lecture.service.LectureService;
import kr.co.knowledgerally.core.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FormRegisterService {
    private final FormService formService;
    private final FormMapper formMapper;
    private final LectureService lectureService;
    private final ApplicationEventPublisher eventPublisher;

    public FormDto.ReadOnly register(Long lectureId, FormDto formDto, User loggedInUser) {
        Form newForm = formMapper.toEntity(formDto);
        Lecture lecture = lectureService.findById(lectureId);
        newForm.setLecture(lecture);
        newForm.setUser(loggedInUser);
        setFormExpirationDate(newForm);

        eventPublisher.publishEvent(new FormRegisterEvent(newForm));

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
