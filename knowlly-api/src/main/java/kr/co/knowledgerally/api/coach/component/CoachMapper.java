package kr.co.knowledgerally.api.coach.component;

import kr.co.knowledgerally.api.coach.dto.CoachDto;
import kr.co.knowledgerally.api.user.component.UserMapper;
import kr.co.knowledgerally.api.user.dto.UserDto;
import kr.co.knowledgerally.core.coach.entity.Coach;
import kr.co.knowledgerally.core.coach.repository.ReviewRepository;
import kr.co.knowledgerally.core.coach.service.ReviewService;
import kr.co.knowledgerally.core.lecture.entity.Lecture;
import kr.co.knowledgerally.core.lecture.entity.LectureInformation;
import kr.co.knowledgerally.core.lecture.repository.LectureRepository;
import kr.co.knowledgerally.core.lecture.service.LectureInformationService;
import kr.co.knowledgerally.core.lecture.service.LectureService;
import kr.co.knowledgerally.core.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = { UserMapper.class })
public abstract class CoachMapper {
    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private ReviewService reviewService;

    @Mapping(target = "currentLectureCount", expression = "java( countOnGoingLectures(coach) )")
    @Mapping(target = "reviewCount", expression = "java( countReviews(coach) )")
    public abstract CoachDto.ReadOnly toDto(Coach coach);

    protected int countOnGoingLectures(Coach coach) {
        return (int) lectureRepository.findAllByCoach(coach).stream()
                .filter(x -> x.getState() != Lecture.State.DONE)
                .count();
    }

    protected int countReviews(Coach coach) {
        return (int) reviewService.findAllByRevieweeWithPageable(coach, Pageable.unpaged())
                .getTotalElements();
    }
}
