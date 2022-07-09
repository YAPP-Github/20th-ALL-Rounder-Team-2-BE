package kr.co.knowledgerally.api.lecture.component;

import kr.co.knowledgerally.api.lecture.dto.LectureImageDto;
import kr.co.knowledgerally.core.lecture.entity.LectureImage;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LectureImageMapper {
    LectureImageDto.ReadOnly toDto(LectureImage lectureImage);
    LectureImage toEntity(LectureImageDto lectureImageDto);
}
