package kr.co.knowledgerally.batch.lecture;

import kr.co.knowledgerally.core.lecture.entity.Form;
import kr.co.knowledgerally.core.lecture.entity.Lecture;
import kr.co.knowledgerally.core.lecture.event.FormStateChangedEvent;
import kr.co.knowledgerally.core.lecture.event.LectureDoneEvent;
import kr.co.knowledgerally.core.lecture.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                log.warn("Lecture id : {} 완료될 수 없는 상태임, 비활성으로 전환", lecture.getId());
                lecture.setActive(false);
                if (lecture.getForms() != null) { // 관련 신청서 모두 REJECT 처리
                    lecture.getForms().forEach(x -> {
                        x.setState(Form.State.REJECT);
                        eventPublisher.publishEvent(new FormStateChangedEvent(x));
                    });
                }
                continue;
            }

            lecture.setState(Lecture.State.DONE);
            eventPublisher.publishEvent(new LectureDoneEvent(lecture));
        }
        lectureRepository.saveAllAndFlush(lectures);
    }
}
