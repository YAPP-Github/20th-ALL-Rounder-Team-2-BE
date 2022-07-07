package kr.co.knowledgerally.core.lecture.service;

import kr.co.knowledgerally.core.lecture.entity.LectureImage;
import kr.co.knowledgerally.core.lecture.repository.LectureImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureImageService {
    private final LectureImageRepository lectureImageRepository;

    /**
     * 클래스 이미지 저장
     * @param lectureImages 클래스 이미지 리스트
     * @return 저장 결과 이미지 리스트
     */
    @Transactional
    public List<LectureImage> saveAllLectureImage(@Valid List<LectureImage> lectureImages) {
        return lectureImageRepository.saveAllAndFlush(lectureImages);
    }
}
