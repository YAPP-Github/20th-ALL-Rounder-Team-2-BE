package kr.co.knowledgerally.api.lecture.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "태그 모델", description = "태그 모델")
public class TagDto {

    @ApiModelProperty(value = "태그 내용", position = PropertyDisplayOrder.CONTENT)
    @JsonProperty(index = PropertyDisplayOrder.CONTENT)
    private String content;

    @SuperBuilder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel(value = "태그 읽기 모델", description = "읽기 전용 태그 모델")
    public static class ReadOnly extends TagDto {
        @ApiModelProperty(
                value = "태그 id",
                position = PropertyDisplayOrder.ID,
                accessMode = ApiModelProperty.AccessMode.READ_ONLY
        )
        @JsonProperty(index = PropertyDisplayOrder.ID)
        private Long id;
    }

    private static class PropertyDisplayOrder {
        private static final int ID = 0;
        private static final int CONTENT = 1;
    }
}
