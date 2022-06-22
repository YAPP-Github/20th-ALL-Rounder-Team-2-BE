package kr.co.knowledgerally.api.user.service;

import kr.co.knowledgerally.api.user.component.UserImageMapper;
import kr.co.knowledgerally.api.user.component.UserMapper;
import kr.co.knowledgerally.api.user.dto.UserOnboardDto;
import kr.co.knowledgerally.core.user.entity.User;
import kr.co.knowledgerally.core.user.entity.UserImage;
import kr.co.knowledgerally.core.user.service.UserImageService;
import kr.co.knowledgerally.core.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

/**
 * 사용자 온보딩 서비스
 */
@Validated
@Service
@RequiredArgsConstructor
public class UserOnboardService {
    private final UserService userService;
    private final UserImageService userImageService;
    private final UserMapper userMapper;
    private final UserImageMapper userImageMapper;

    /**
     * 사용자 온보딩 서비스
     * @param userOnboardDto 유저 온보딩 정보
     * @param loggedInUser 현재 세션 로그인 유저
     * @return 온보딩 저장 결과 리턴
     */
    @Transactional
    public UserOnboardDto.ReadOnly onBoard(@Valid UserOnboardDto userOnboardDto,
                                           User loggedInUser) {
        User onBoardingUser = userMapper.toEntity(userOnboardDto.getUser());
        onBoardingUser.setId(loggedInUser.getId());
        onBoardingUser.setIdentifier(loggedInUser.getIdentifier());
        User savedUser = userService.saveUser(onBoardingUser);
        UserImage savedUserImage = userImageService.saveUserImage(userImageMapper.toEntity(userOnboardDto.getUserImage(), savedUser));

        return UserOnboardDto.ReadOnly.builder()
                .user(userMapper.toDto(savedUser))
                .userImage(userImageMapper.toDto(savedUserImage))
                .build();
    }
}
