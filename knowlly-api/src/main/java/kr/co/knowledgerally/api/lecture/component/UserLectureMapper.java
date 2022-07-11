package kr.co.knowledgerally.api.lecture.component;

import kr.co.knowledgerally.api.lecture.dto.CoachLectureDto;
import kr.co.knowledgerally.api.lecture.dto.UserLectureDto;
import kr.co.knowledgerally.core.lecture.entity.Form;
import kr.co.knowledgerally.core.lecture.entity.Lecture;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {LectureInformationMapper.class, LectureMapper.class, FormMapper.class}
)
public interface UserLectureMapper {
    @Mapping(target = "form", source = "form")
    @Mapping(target = "lecture", source = "form.lecture")
    @Mapping(target = "lectureInformation", source = "form.lecture.lectureInformation")
    UserLectureDto toDto(Form form);
}
