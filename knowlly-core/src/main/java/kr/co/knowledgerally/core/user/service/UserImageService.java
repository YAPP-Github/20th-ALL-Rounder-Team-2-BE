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

    @Transactional
    public UserImage saveUserImage(@Valid UserImage newUserImage) {
        List<UserImage> userImages = userImageRepository.findAllByUser(newUserImage.getUser());
        for (UserImage userImage : userImages) {
            userImage.makeInactive();
        }
        newUserImage.setActive(true);
        return userImageRepository.saveAndFlush(newUserImage);
    }
}
