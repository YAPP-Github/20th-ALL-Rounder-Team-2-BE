package kr.co.knowledgerally.api.lecture.component;

import kr.co.knowledgerally.api.coach.component.CoachMapper;
import kr.co.knowledgerally.api.lecture.dto.LectureDto;
import kr.co.knowledgerally.api.lecture.dto.LectureInformationDto;
import kr.co.knowledgerally.core.lecture.entity.Lecture;
import kr.co.knowledgerally.core.lecture.entity.LectureInformation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        imports = {DateTimeFormatter.class, LocalDateTime.class}
)
public interface LectureMapper {
    @Mapping(target = "startAt", expression = "java(lecture.getStartAt()" +
            ".format(DateTimeFormatter.ofPattern(\"yyyy-MM-dd HH:mm:ss\")) )")
    @Mapping(target = "endAt", expression = "java(lecture.getEndAt()" +
            ".format(DateTimeFormatter.ofPattern(\"yyyy-MM-dd HH:mm:ss\")) )")
    LectureDto.ReadOnly toDto(Lecture lecture);

    @Mapping(target = "startAt", expression = "java(LocalDateTime.parse(lectureDto.getStartAt()" +
            ", DateTimeFormatter.ofPattern(\"yyyy-MM-dd HH:mm:ss\")) )")
    @Mapping(target = "endAt", expression = "java(LocalDateTime.parse(lectureDto.getEndAt()" +
            ", DateTimeFormatter.ofPattern(\"yyyy-MM-dd HH:mm:ss\")) )")
    Lecture toEntity(LectureDto lectureDto);
}
