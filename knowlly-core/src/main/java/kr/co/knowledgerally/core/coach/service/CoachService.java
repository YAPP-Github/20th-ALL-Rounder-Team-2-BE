package kr.co.knowledgerally.core.coach.service;

import kr.co.knowledgerally.core.coach.entity.Coach;
import kr.co.knowledgerally.core.coach.repository.CoachRepository;
import kr.co.knowledgerally.core.core.exception.ResourceNotFoundException;
import kr.co.knowledgerally.core.core.message.ErrorMessage;
import kr.co.knowledgerally.core.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    /**
     * 코치 프로필을 조회합니다.
     * @param coachId 조회하고자 하는 코치 Id
     * @return 코치 엔티티
     * @throws ResourceNotFoundException 존재하지 않는 사용자 Id의 경우
     */
    @Transactional(readOnly = true)
    public Coach findById(Long coachId) throws ResourceNotFoundException {
        return coachRepository.findById(coachId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.NOT_EXIST_COACH));
    }
}
