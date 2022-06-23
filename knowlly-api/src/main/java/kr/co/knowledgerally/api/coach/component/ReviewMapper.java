package kr.co.knowledgerally.api.coach.component;

import kr.co.knowledgerally.api.user.component.UserMapper;
import kr.co.knowledgerally.api.coach.dto.ReviewDto;
import kr.co.knowledgerally.core.coach.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        imports = DateTimeFormatter.class,
        uses = { CoachMapper.class, UserMapper.class })
public interface ReviewMapper {
    @Mapping(target = "writtenDate", expression = "java(review.getCreatedAt()" +
            ".format(DateTimeFormatter.ofPattern(\"yyyy-MM-dd\")) )")
    @Mapping(target = "isPublic", source = "review.public")
    ReviewDto.ReadOnly toDto(Review review);
}
