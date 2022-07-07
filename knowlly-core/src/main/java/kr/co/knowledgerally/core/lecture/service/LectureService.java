package kr.co.knowledgerally.core.lecture.service;

import kr.co.knowledgerally.core.core.exception.ResourceNotFoundException;
import kr.co.knowledgerally.core.core.message.ErrorMessage;
import kr.co.knowledgerally.core.lecture.entity.Form;
import kr.co.knowledgerally.core.lecture.entity.Lecture;
import kr.co.knowledgerally.core.lecture.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
@RequiredArgsConstructor
public class LectureService {
    private final LectureRepository lectureRepository;

    /**
     * id로 클래스를 조회합니다.
     * @param id 클래스 id
     * @return 클래스 객체
     * @throws ResourceNotFoundException 해당하는 id가 존재하지 않을 때
     */
    @Transactional(readOnly = true)
    public Lecture findById(long id) throws ResourceNotFoundException {
        return lectureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.NOT_EXIST_LECTURE));
    }
}
