package kr.co.knowledgerally.api.review.component;

import kr.co.knowledgerally.api.coach.component.CoachMapper;
import kr.co.knowledgerally.api.user.component.UserMapper;
import kr.co.knowledgerally.api.review.dto.ReviewDto;
import kr.co.knowledgerally.core.coach.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = { CoachMapper.class, UserMapper.class })
public interface ReviewMapper {
    @Mapping(target = "writtenDate", expression = "java(review.getCreatedAt().toLocalDate().toString())")
    @Mapping(target = "isPublic", source = "review.public")
    ReviewDto.ReadOnly toDto(Review review);

    Review toEntity(ReviewDto reviewDto);
}
