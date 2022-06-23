package kr.co.knowledgerally.api.coach.component;

import kr.co.knowledgerally.api.coach.dto.CoachDto;
import kr.co.knowledgerally.api.user.component.UserMapper;
import kr.co.knowledgerally.api.user.dto.UserDto;
import kr.co.knowledgerally.core.coach.entity.Coach;
import kr.co.knowledgerally.core.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = { UserMapper.class })
public interface CoachMapper {
    CoachDto.ReadOnly toDto(Coach user);
}
