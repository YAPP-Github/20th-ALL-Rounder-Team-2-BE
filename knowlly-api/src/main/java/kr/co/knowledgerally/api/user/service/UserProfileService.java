package kr.co.knowledgerally.api.user.service;

import kr.co.knowledgerally.api.user.component.UserImageMapper;
import kr.co.knowledgerally.api.user.component.UserMapper;
import kr.co.knowledgerally.api.user.dto.UserProfileDto;
import kr.co.knowledgerally.core.user.entity.User;
import kr.co.knowledgerally.core.user.service.UserImageService;
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
    private final UserImageService userImageService;

    @Transactional
    public UserProfileDto getProfile(User loggedInUser) {
        return UserProfileDto.builder()
                .user(userMapper.toDto(loggedInUser))
                .userImage(userImageMapper.toDto(userImageService.findByUser(loggedInUser)))
                .build();
    }
}
