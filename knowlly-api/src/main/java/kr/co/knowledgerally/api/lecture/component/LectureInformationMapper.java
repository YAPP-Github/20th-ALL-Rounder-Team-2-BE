package kr.co.knowledgerally.api.lecture.component;

import kr.co.knowledgerally.api.coach.component.CoachMapper;
import kr.co.knowledgerally.api.lecture.dto.LectureInformationDto;
import kr.co.knowledgerally.core.lecture.entity.LectureInformation;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {
                CoachMapper.class, CategoryMapper.class, LectureImageMapper.class, TagMapper.class, LectureMapper.class
        }
)
public interface LectureInformationMapper {
    LectureInformationDto.ReadOnly toDto(LectureInformation lectureInformation);
    LectureInformation toEntity(LectureInformationDto lectureInformationDto);
}
