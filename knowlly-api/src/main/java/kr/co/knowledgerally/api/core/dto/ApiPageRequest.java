package kr.co.knowledgerally.api.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.knowledgerally.core.core.exception.BadRequestException;
import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.stream.Stream;

/**
 * 페이징 API 요청 모델
 */
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="페이징 API 요청 모델")
@EqualsAndHashCode
public class ApiPageRequest {
    @ApiModelProperty(value = "요청 페이지 번호 (0부터 시작)", example = "0")
    private int page = 0;

    @ApiModelProperty(value = "요청 페이지 크기 (기본 : 10)", example = "10")
    private int size = 10;

    public Pageable convert() {
        return PageRequest.of(page, size);
    }

    public Pageable convertWithNewestSort() {
        return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
    }

}
