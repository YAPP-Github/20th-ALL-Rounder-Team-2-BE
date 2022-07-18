package kr.co.knowledgerally.batch.lecture;

import kr.co.knowledgerally.core.core.component.DateFactory;
import kr.co.knowledgerally.core.lecture.entity.Lecture;
import kr.co.knowledgerally.core.lecture.event.FormStateChangedEvent;
import kr.co.knowledgerally.core.lecture.event.LectureStateChangedEvent;
import kr.co.knowledgerally.core.lecture.repository.LectureRepository;
import kr.co.knowledgerally.core.lecture.service.LectureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LectureDoneService {
    private final LectureRepository lectureRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void makeLecturesDone(List<Lecture> lectures) {
        for (Lecture lecture : lectures) {
            if (lecture.getState() != Lecture.State.ON_GOING || lecture.getAcceptedForm().isEmpty()) { // 클래스가 완료될 수 없는 상황
                log.warn("Lecture id : {} 완료될 수 없는 상태임", lecture.getId());
                continue;
            }

            lecture.setState(Lecture.State.DONE);
            eventPublisher.publishEvent(new LectureStateChangedEvent(lecture));
        }
        lectureRepository.saveAllAndFlush(lectures);
    }
}
