package kr.co.knowledgerally.api.lecture.service;

import kr.co.knowledgerally.api.lecture.component.LectureMapper;
import kr.co.knowledgerally.api.lecture.dto.LectureDto;
import kr.co.knowledgerally.core.core.exception.BadRequestException;
import kr.co.knowledgerally.core.core.message.ErrorMessage;
import kr.co.knowledgerally.core.lecture.entity.Form;
import kr.co.knowledgerally.core.lecture.entity.Lecture;
import kr.co.knowledgerally.core.lecture.repository.FormRepository;
import kr.co.knowledgerally.core.lecture.repository.LectureRepository;
import kr.co.knowledgerally.core.lecture.service.FormService;
import kr.co.knowledgerally.core.lecture.service.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LectureDeleteService {
    private final LectureService lectureService;
    private final FormService formService;
    private final LectureRepository lectureRepository;
    private final FormRepository formRepository;
    private final LectureMapper lectureMapper;

    /**
     * 클래스 일정을 삭제할 수 있다.
     * @param lectureId
     * @return 삭제한 LectureDto
     */
    @Transactional
    public Lecture delete(long lectureId) {
        Lecture lecture = lectureService.findById(lectureId);

        if (lecture.getState() != Lecture.State.ON_BOARD) {
            throw new BadRequestException(ErrorMessage.SCHEDULE_CAN_NOT_DELETE);
        }
        List<Form> forms = formService.findAllByLecture(lecture);

        for (Form form: forms) {
            form.setState(Form.State.REJECT);
        }

        formRepository.saveAll(forms);
        lecture.setActive(false);

        return lectureRepository.saveAndFlush(lecture);
    }
}
