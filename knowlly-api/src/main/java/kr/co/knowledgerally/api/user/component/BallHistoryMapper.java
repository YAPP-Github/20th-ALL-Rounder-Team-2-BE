package kr.co.knowledgerally.api.user.component;

import kr.co.knowledgerally.api.user.dto.BallHistoryDto;
import kr.co.knowledgerally.api.user.dto.UserDto;
import kr.co.knowledgerally.core.user.entity.BallHistory;
import kr.co.knowledgerally.core.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BallHistoryMapper {
    BallHistoryDto toDto(BallHistory user);
}
