package kr.co.knowledgerally.api.user.component;

import kr.co.knowledgerally.api.user.dto.UserDto;
import kr.co.knowledgerally.api.user.dto.UserImageDto;
import kr.co.knowledgerally.core.user.entity.User;
import kr.co.knowledgerally.core.user.entity.UserImage;
import kr.co.knowledgerally.core.user.service.UserImageService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class UserMapper {
    @Autowired
    protected UserImageService userImageService;

    @Mapping(target = "isCoach", source = "user.coach")
    @Mapping(target = "isPushActive", source = "user.pushActive")
    @Mapping(target = "userImgUrl", expression = "java( findAndMapUserImage(user) )")
    public abstract UserDto.ReadOnly toDto(User user);
    public abstract User toEntity(UserDto userDto);

    protected String findAndMapUserImage(User user) {
        UserImage userImage = userImageService.findByUser(user);
        return userImage == null ? null : userImage.getUserImgUrl();
    }
}
