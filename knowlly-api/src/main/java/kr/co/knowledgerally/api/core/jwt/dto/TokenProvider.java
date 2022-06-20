package kr.co.knowledgerally.api.core.jwt.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import kr.co.knowledgerally.core.core.exception.BadRequestException;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.stream.Stream;

@Getter
public enum TokenProvider {
    KAKAO;

    @JsonCreator
    public static TokenProvider create(String requestValue) {
        if (! StringUtils.hasLength(requestValue)) {
            return TokenProvider.KAKAO;
        }

        return Stream.of(values())
                .filter(v -> v.toString().equalsIgnoreCase(requestValue))
                .findFirst()
                .orElseThrow(() -> new BadRequestException("잘못된 프로바이더입니다."));
    }
}
