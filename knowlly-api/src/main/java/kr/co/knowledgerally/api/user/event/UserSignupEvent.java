package kr.co.knowledgerally.api.user.event;

import kr.co.knowledgerally.core.core.vo.EventType;
import kr.co.knowledgerally.core.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class UserSignupEvent implements EventType<User> {
    @Getter
    User value;
}
