package kr.co.knowledgerally.core.user.service;

import kr.co.knowledgerally.core.core.exception.ResourceNotFoundException;
import kr.co.knowledgerally.core.user.entity.User;
import kr.co.knowledgerally.core.user.entity.UserImage;
import kr.co.knowledgerally.core.user.repository.UserImageRepository;
import kr.co.knowledgerally.core.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

import static kr.co.knowledgerally.core.core.message.ErrorMessage.NOT_EXIST_USER;

@Validated
@Service
@RequiredArgsConstructor
public class UserImageService {
    private final UserImageRepository userImageRepository;

    /**
     * 사용자 이미지 저장
     * @param newUserImage 신규 이미지
     * @return 저장 결과 이미지 객체
     */
    @Transactional
    public UserImage saveUserImage(@Valid UserImage newUserImage) {
        List<UserImage> userImages = userImageRepository.findAllByUser(newUserImage.getUser());
        for (UserImage userImage : userImages) {
            userImage.makeInactive();
        }
        newUserImage.setActive(true);
        return userImageRepository.saveAndFlush(newUserImage);
    }

    /**
     * 사용자 프로필 이미지 검색
     * @param user 사용자
     * @return 사용자의 프로필 이미지 객체, 없다면 null 리턴
     */
    @Transactional
    public UserImage findByUser(User user) {
        return userImageRepository.findAllByUser(user).stream()
                .filter(UserImage::isActive)
                .findFirst()
                .orElse(null);
    }
}
