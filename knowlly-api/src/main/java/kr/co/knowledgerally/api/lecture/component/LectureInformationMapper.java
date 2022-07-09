package kr.co.knowledgerally.api.lecture.component;

import kr.co.knowledgerally.api.coach.component.CoachMapper;
import kr.co.knowledgerally.api.lecture.dto.LectureInformationDto;
import kr.co.knowledgerally.core.lecture.entity.LectureInformation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {
                CoachMapper.class, CategoryMapper.class, LectureImageMapper.class, TagMapper.class, LectureMapper.class
        }
)
public interface LectureInformationMapper {
    @Mapping(target = "category", source = "lectureInformation.category.name")
    LectureInformationDto.ReadOnly toDto(LectureInformation lectureInformation);
    @Mapping(target = "category", source = "lectureInformationDto.category")
    LectureInformation toEntity(LectureInformationDto lectureInformationDto);
}
