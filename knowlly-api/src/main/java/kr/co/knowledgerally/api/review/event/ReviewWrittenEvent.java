package kr.co.knowledgerally.api.review.event;

import kr.co.knowledgerally.core.coach.entity.Coach;
import kr.co.knowledgerally.core.core.vo.EventType;
import kr.co.knowledgerally.core.lecture.entity.Lecture;
import kr.co.knowledgerally.core.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ReviewWrittenEvent implements EventType<Lecture> {
    @Getter
    Lecture value;
}
