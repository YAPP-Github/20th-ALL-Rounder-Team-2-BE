package kr.co.knowledgerally.api.lecture.component;

import kr.co.knowledgerally.api.coach.component.CoachMapper;
import kr.co.knowledgerally.api.lecture.dto.LectureDto;
import kr.co.knowledgerally.api.lecture.dto.LectureInformationDto;
import kr.co.knowledgerally.api.user.component.UserMapper;
import kr.co.knowledgerally.api.user.dto.UserDto;
import kr.co.knowledgerally.core.lecture.entity.Lecture;
import kr.co.knowledgerally.core.lecture.entity.LectureInformation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        imports = {DateTimeFormatter.class, LocalDateTime.class},
        uses = { FormMapper.class }
)
public abstract class LectureMapper {
    @Autowired
    protected UserMapper userMapper;

    @Mapping(target = "startAt", expression = "java(lecture.getStartAt().toString())")
    @Mapping(target = "endAt", expression = "java(lecture.getEndAt().toString())")
    @Mapping(target = "isReviewWritten", source = "lecture.reviewWritten")
    @Mapping(target = "isMatched", expression = "java( isMatched(lecture) )")
    @Mapping(target = "matchedUser", expression = "java( matchedUser(lecture) )")
    public abstract LectureDto.ReadOnly toDto(Lecture lecture);

    @Mapping(target = "startAt", expression = "java(LocalDateTime.parse(lectureDto.getStartAt()) )")
    @Mapping(target = "endAt", expression = "java(LocalDateTime.parse(lectureDto.getEndAt()) )")
    public abstract Lecture toEntity(LectureDto lectureDto);

    protected boolean isMatched(Lecture lecture) {
        return lecture.getState() != Lecture.State.ON_BOARD;
    }

    protected UserDto.ReadOnly matchedUser(Lecture lecture) {
        if (lecture.getForms() == null || lecture.getForms().size() == 0) {
            return null;
        }

        // forms에는 매칭된 사람 신청서만 있다는 전제조건을 깔고 감
        return ! isMatched(lecture) ? null : userMapper.toDto(lecture.getForms().get(0).getUser());
    }
}
