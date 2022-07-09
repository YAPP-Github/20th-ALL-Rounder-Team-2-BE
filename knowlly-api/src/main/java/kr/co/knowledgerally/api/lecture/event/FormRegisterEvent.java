package kr.co.knowledgerally.api.lecture.event;

import kr.co.knowledgerally.core.core.vo.EventType;
import kr.co.knowledgerally.core.lecture.entity.Lecture;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class FormRegisterEvent implements EventType<Lecture> {
    @Getter
    Lecture value;
}