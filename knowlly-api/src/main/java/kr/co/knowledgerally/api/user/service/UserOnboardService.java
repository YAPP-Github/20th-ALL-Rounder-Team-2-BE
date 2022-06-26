package kr.co.knowledgerally.api.user.service;

import kr.co.knowledgerally.api.user.component.UserMapper;
import kr.co.knowledgerally.api.user.dto.UserDto;
import kr.co.knowledgerally.core.user.entity.User;
import kr.co.knowledgerally.core.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

/**
 * 사용자 수정 서비스
 */
@Validated
@Service
@RequiredArgsConstructor
public class UserOnboardService {
    private final UserModifyService userModifyService;

    /**
     * 사용자 온보딩 서비스
     * @param userDto 온보딩 유저 정보
     * @param loggedInUser 현재 세션 로그인 유저
     * @return 온보딩 결과 리턴
     */
    @Transactional
    public UserDto.ReadOnly onboard(@Valid UserDto userDto,
                                           User loggedInUser) {
        loggedInUser.setOnboard(true);
        return userModifyService.modify(userDto, loggedInUser);
    }
}
