package kr.co.knowledgerally.core.coach.service;

import kr.co.knowledgerally.core.coach.entity.Coach;
import kr.co.knowledgerally.core.coach.repository.CoachRepository;
import kr.co.knowledgerally.core.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
@RequiredArgsConstructor
public class CoachService {
    private final CoachRepository coachRepository;

    /**
     * 사용자 정보로 코치 검색
     * @param user 사용자
     * @return 코치 객체, 없다면 null 리턴
     */
    public Coach findByUser(User user) {
        return coachRepository.findByUser(user).orElse(null);
    }
}
