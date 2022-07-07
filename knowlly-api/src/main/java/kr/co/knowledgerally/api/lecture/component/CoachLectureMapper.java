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
    @Mapping(target = "isMatched", expression = "java(lecture.getState() != " +
            "kr.co.knowledgerally.core.lecture.entity.Lecture.State.ON_BOARD)")
    @Mapping(target = "matchedUser", expression = "java(lecture.getState() != " + // forms에는 매칭된 사람 신청서만 있다는 전제조건을 깔고 감
            "kr.co.knowledgerally.core.lecture.entity.Lecture.State.ON_BOARD ? lecture.getForms().get(0).getUser() : null)")
    CoachLectureDto toDto(Lecture lecture);
}
