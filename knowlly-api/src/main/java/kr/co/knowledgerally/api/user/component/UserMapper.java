package kr.co.knowledgerally.api.user.component;

import kr.co.knowledgerally.api.user.dto.UserDto;
import kr.co.knowledgerally.core.user.entity.User;
import kr.co.knowledgerally.core.user.entity.UserImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    @Mapping(target = "isCoach", source = "user.coach")
    @Mapping(target = "isPushActive", source = "user.pushActive")
    UserDto.ReadOnly toDto(User user);
    User toEntity(UserDto userDto);
}
