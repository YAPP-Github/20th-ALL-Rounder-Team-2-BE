package kr.co.knowledgerally.api.user.service;

import kr.co.knowledgerally.api.user.component.UserMapper;
import kr.co.knowledgerally.api.user.dto.UserDto;
import kr.co.knowledgerally.core.user.entity.RefreshToken;
import kr.co.knowledgerally.core.user.entity.User;
import kr.co.knowledgerally.core.user.repository.RefreshTokenRepository;
import kr.co.knowledgerally.core.user.repository.UserImageRepository;
import kr.co.knowledgerally.core.user.repository.UserRepository;
import kr.co.knowledgerally.core.user.service.UserImageService;
import kr.co.knowledgerally.core.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Optional;

/**
 * 사용자 탈퇴 서비스
 */
@Validated
@Service
@RequiredArgsConstructor
public class UserDropoutService {
    private final UserRepository userRepository;
    private final UserImageRepository userImageRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * 사용자 탈퇴 서비스
     * @param loggedInUser 현재 세션 로그인 유저
     */
    @Transactional // TODO 추후에 삭제로직에서 비활성화(inactive) 로직으로 변경
    public void dropOut(User loggedInUser) {
        deleteUserImages(loggedInUser);
        deleteRefreshToken(loggedInUser);
        userRepository.delete(loggedInUser);
    }

    private void deleteUserImages(User loggedInUser) {
        userImageRepository.deleteAll(userImageRepository.findAllByUser(loggedInUser));
    }

    private void deleteRefreshToken(User loggedInUser) {
        refreshTokenRepository.findByUser(loggedInUser).ifPresent(refreshTokenRepository::delete);
    }
}
