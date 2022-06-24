package kr.co.knowledgerally.api.user.service;

import kr.co.knowledgerally.api.coach.component.CoachMapper;
import kr.co.knowledgerally.api.user.component.UserImageMapper;
import kr.co.knowledgerally.api.user.component.UserMapper;
import kr.co.knowledgerally.api.user.dto.UserProfileDto;
import kr.co.knowledgerally.core.coach.service.CoachService;
import kr.co.knowledgerally.core.core.exception.ResourceNotFoundException;
import kr.co.knowledgerally.core.core.message.ErrorMessage;
import kr.co.knowledgerally.core.user.entity.User;
import kr.co.knowledgerally.core.user.repository.UserRepository;
import kr.co.knowledgerally.core.user.service.UserImageService;
import kr.co.knowledgerally.core.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * 사용자 프로필 서비스
 */
@Validated
@Service
@RequiredArgsConstructor
public class UserProfileService {
    private final UserMapper userMapper;
    private final UserImageMapper userImageMapper;
    private final CoachMapper coachMapper;
    private final UserService userService;
    private final UserImageService userImageService;
    private final CoachService coachService;

    /**
     * 사용자 프로필을 조회합니다.
     * @param userId 조회하고자 하는 사용자 Id
     * @return 사용자 프로필 dto
     * @throws ResourceNotFoundException 존재하지 않는 사용자 Id의 경우
     */
    @Transactional(readOnly = true)
    public UserProfileDto getProfile(Long userId) throws ResourceNotFoundException {
        return getProfile(userService.findById(userId));
    }

    /**
     * 사용자 프로필을 조회합니다.
     * @param user 조회하고자 하는 사용자
     * @return 사용자 프로필 dto
     */
    @Transactional(readOnly = true)
    public UserProfileDto getProfile(User user) {
        return UserProfileDto.builder()
                .user(userMapper.toDto(user))
                .userImage(userImageMapper.toDto(userImageService.findByUser(user)))
                .coach(coachMapper.toDto(coachService.findByUser(user)))
                .build();
    }
}
