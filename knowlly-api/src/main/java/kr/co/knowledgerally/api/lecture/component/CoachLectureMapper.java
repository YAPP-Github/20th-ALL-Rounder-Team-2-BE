package kr.co.knowledgerally.api.lecture.component;

import kr.co.knowledgerally.api.lecture.dto.CoachLectureDto;
import kr.co.knowledgerally.core.lecture.entity.Lecture;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {LectureMapper.class, LectureInformationMapper.class, FormMapper.class}
)
public interface CoachLectureMapper {
    @Mapping(target = "lecture", source = "lecture")
    @Mapping(target = "lectureInformation", source = "lecture.lectureInformation")
    @Mapping(target = "forms", source = "lecture.forms")
    CoachLectureDto toDto(Lecture lecture);
}
