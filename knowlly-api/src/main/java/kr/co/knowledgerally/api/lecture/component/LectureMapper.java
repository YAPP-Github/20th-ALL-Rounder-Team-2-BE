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
    @Mapping(target = "startAt", expression = "java(lecture.getStartAt().toString())")
    @Mapping(target = "endAt", expression = "java(lecture.getEndAt().toString())")
    LectureDto.ReadOnly toDto(Lecture lecture);

    @Mapping(target = "startAt", expression = "java(LocalDateTime.parse(lectureDto.getStartAt()) )")
    @Mapping(target = "endAt", expression = "java(LocalDateTime.parse(lectureDto.getEndAt()) )")
    Lecture toEntity(LectureDto lectureDto);
}
