package kr.co.knowledgerally.api.user.component;

import kr.co.knowledgerally.api.user.dto.BallHistoryDto;
import kr.co.knowledgerally.api.user.dto.NotificationDto;
import kr.co.knowledgerally.api.user.dto.UserDto;
import kr.co.knowledgerally.core.user.entity.BallHistory;
import kr.co.knowledgerally.core.user.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class NotificationMapper {
    @Autowired
    protected UserMapper userMapper;

    @Mapping(target = "receiver", expression = "java(userMapper.toDto(notification.getNotiType() == Notification.NotiType.PLAYER ?" +
            "notification.getUser() : notification.getCoach().getUser()))", resultType = UserDto.ReadOnly.class)
    @Mapping(target = "sender", expression = "java(userMapper.toDto(notification.getNotiType() == Notification.NotiType.PLAYER ?" +
            "notification.getCoach().getUser() : notification.getUser()))", resultType = UserDto.ReadOnly.class)
    @Mapping(target = "sentDate", expression = "java(notification.getCreatedAt().toString())")
    @Mapping(target = "isRead", source = "notification.read")
    public abstract NotificationDto toDto(Notification notification);
}
