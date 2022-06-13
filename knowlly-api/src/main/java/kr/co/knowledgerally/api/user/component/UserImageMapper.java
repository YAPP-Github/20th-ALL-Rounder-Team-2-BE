package kr.co.knowledgerally.api.user.component;

import kr.co.knowledgerally.api.user.dto.UserDto;
import kr.co.knowledgerally.api.user.dto.UserImageDto;
import kr.co.knowledgerally.core.user.entity.User;
import kr.co.knowledgerally.core.user.entity.UserImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserImageMapper {
    UserImageDto toDto(UserImage userImage);

    @Mapping(target = "user", source = "user")
    UserImage toEntity(UserImageDto userImageDto, User user);
}
