package kr.co.knowledgerally.api.user.component;

import kr.co.knowledgerally.api.user.dto.BallHistoryDto;
import kr.co.knowledgerally.api.user.dto.UserDto;
import kr.co.knowledgerally.core.user.entity.BallHistory;
import kr.co.knowledgerally.core.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        imports = DateTimeFormatter.class)
public interface BallHistoryMapper {
    @Mapping(target = "historyDate", expression = "java(ballHistory.getCreatedAt()" +
            ".format(DateTimeFormatter.ofPattern(\"yyyy-MM-dd\")) )")
    BallHistoryDto toDto(BallHistory ballHistory);
}
