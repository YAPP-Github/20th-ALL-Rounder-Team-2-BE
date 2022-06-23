package kr.co.knowledgerally.api.user.service;

import kr.co.knowledgerally.api.core.component.FileNameGenerator;
import kr.co.knowledgerally.api.core.component.FileUploader;
import kr.co.knowledgerally.api.user.dto.UserImageDto;
import kr.co.knowledgerally.core.user.entity.User;
import kr.co.knowledgerally.core.user.entity.UserImage;
import kr.co.knowledgerally.core.user.service.UserImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserImageUploadService {
    public static final String MEMBER_IMAGE_DIR_NAME = "user-image";
    public static final String DASH = "/";

    private final FileNameGenerator fileNameGenerator;
    private final FileUploader fileUploader;
    private final UserImageService userImageService;

    /**
     * Multipart File 을 저장하고, 저장 경로를 리턴한다.
     *
     * @param imageFile 요청으로 들어온 Multipart File
     * @return 웹상에서 저장된 경로
     */
    @Transactional
    public UserImageDto upload(MultipartFile imageFile, User loggedInUser) {
        String generatedFileName = fileNameGenerator.generate(imageFile.getOriginalFilename());
        generatedFileName = MEMBER_IMAGE_DIR_NAME + DASH + loggedInUser.getId() + DASH + generatedFileName;
        String downloadableUrl = fileUploader.uploadMultiPartFile(imageFile, generatedFileName);

        userImageService.saveUserImage(UserImage.builder()
                        .user(loggedInUser)
                        .userImgUrl(downloadableUrl)
                .build());

        return new UserImageDto(downloadableUrl);
    }
}
