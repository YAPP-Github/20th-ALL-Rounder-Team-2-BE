package kr.co.knowledgerally.api.lecture.component;

import kr.co.knowledgerally.api.lecture.dto.CategoryDto;
import kr.co.knowledgerally.api.lecture.dto.LectureImageDto;
import kr.co.knowledgerally.api.lecture.util.TestCategoryDtoFactory;
import kr.co.knowledgerally.api.lecture.util.TestLectureImageDtoFactory;
import kr.co.knowledgerally.core.lecture.entity.Category;
import kr.co.knowledgerally.core.lecture.entity.LectureImage;
import kr.co.knowledgerally.core.lecture.util.TestCategoryEntityFactory;
import kr.co.knowledgerally.core.lecture.util.TestLectureImageEntityFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class LectureImageMapperTest {
    @Autowired
    private LectureImageMapper lectureImageMapper;

    @Test
    void 엔티티에서_DTO변환_테스트() {
        LectureImage lectureImage = new TestLectureImageEntityFactory().createEntity(1L);

        LectureImageDto lectureImageDto = lectureImageMapper.toDto(lectureImage);
        assertEquals("http://lecture1.img.url", lectureImageDto.getLectureImgUrl());
    }

    @Test
    void DTO에서_엔티티변환_테스트() {
        LectureImageDto lectureImageDto = new TestLectureImageDtoFactory().createDto(1L);

        LectureImage lectureImage = lectureImageMapper.toEntity(lectureImageDto);
        assertEquals("http://lecture.img1.url", lectureImage.getLectureImgUrl());
    }

}
