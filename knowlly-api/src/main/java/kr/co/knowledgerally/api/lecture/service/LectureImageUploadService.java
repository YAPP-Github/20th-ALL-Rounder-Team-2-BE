package kr.co.knowledgerally.api.lecture.service;

import kr.co.knowledgerally.api.core.component.FileNameGenerator;
import kr.co.knowledgerally.api.core.component.FileUploader;
import kr.co.knowledgerally.api.lecture.component.LectureImageMapper;
import kr.co.knowledgerally.api.lecture.dto.LectureImageDto;
import kr.co.knowledgerally.core.lecture.entity.LectureImage;
import kr.co.knowledgerally.core.lecture.service.LectureImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LectureImageUploadService {
    public static final String LECTURE_IMAGE_DIR_NAME = "lecture-image";
    public static final String DASH = "/";

    private final FileNameGenerator fileNameGenerator;
    private final FileUploader fileUploader;
    private final LectureImageService lectureImageService;
    private final LectureImageMapper lectureImageMapper;

    /**
     * Multipart File 들을 저장하고, 저장 경로들을 리턴한다.
     *
     * @param imageFiles 요청으로 들어온 Multipart File List
     * @return 저장 결과 LectureImageDto 리스트
     */
    @Transactional
    public List<LectureImageDto> uploadLectureImage(List<MultipartFile> imageFiles) {
        List<LectureImage> lectureImages = new ArrayList<>();

        for (MultipartFile imageFile : imageFiles) {
            String generatedFileName = fileNameGenerator.generate(imageFile.getOriginalFilename());
            generatedFileName = LECTURE_IMAGE_DIR_NAME + DASH + generatedFileName;
            String downloadableUrl = fileUploader.uploadMultiPartFile(imageFile, generatedFileName);

            lectureImages.add(LectureImage.builder()
                    .lectureImgUrl(downloadableUrl)
                    .build());
        }
        lectureImages = lectureImageService.saveAllLectureImage(lectureImages);

        return lectureImages
                .stream()
                .map(s->lectureImageMapper.toDto(s))
                .collect(Collectors.toList());
    }
}
