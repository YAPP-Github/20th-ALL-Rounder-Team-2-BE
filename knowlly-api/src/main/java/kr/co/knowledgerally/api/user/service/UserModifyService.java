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
public class UserModifyService {
    private final UserService userService;
    private final UserMapper userMapper;

    /**
     * 사용자 수정 서비스
     * @param userDto 수정할 유저 정보
     * @param loggedInUser 현재 세션 로그인 유저
     * @return 수정 저장 결과 리턴
     */
    @Transactional
    public UserDto.ReadOnly modify(@Valid UserDto userDto,
                                           User loggedInUser) {
        User modifyUser = userMapper.toEntity(userDto);
        modifyUser.setId(loggedInUser.getId());
        modifyUser.setIdentifier(loggedInUser.getIdentifier());
        User savedUser = userService.saveUser(modifyUser);

        return userMapper.toDto(savedUser);
    }
}
