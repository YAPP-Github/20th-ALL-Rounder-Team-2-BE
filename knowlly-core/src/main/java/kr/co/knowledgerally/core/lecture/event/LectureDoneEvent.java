package kr.co.knowledgerally.core.lecture.event;

import kr.co.knowledgerally.core.core.vo.EventType;
import kr.co.knowledgerally.core.lecture.entity.Lecture;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class LectureDoneEvent implements EventType<Lecture> {
    @Getter
    Lecture value;
}