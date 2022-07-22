package kr.co.knowledgerally.api.user.service;

import kr.co.knowledgerally.core.core.exception.ResourceNotFoundException;
import kr.co.knowledgerally.core.user.entity.User;
import kr.co.knowledgerally.core.user.entity.UserImage;
import kr.co.knowledgerally.core.user.repository.UserImageRepository;
import kr.co.knowledgerally.core.user.service.UserImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserImageDeleteService {
    private final UserImageService userImageService;
    private final UserImageRepository userImageRepository;

    /**
     * Multipart File 을 저장하고, 저장 경로를 리턴한다.
     *
     * @param loggedInUser 로그인 된 유저
     * @return UserImage 객체
     */
    @Transactional
    public UserImage deleteImage(User loggedInUser) {

        UserImage userImage = userImageService.findByUser(loggedInUser);

        if (userImage == null) {
            throw new ResourceNotFoundException();
        }

        userImage.setActive(false);
        userImageRepository.saveAndFlush(userImage);

        return userImage;
    }
}
