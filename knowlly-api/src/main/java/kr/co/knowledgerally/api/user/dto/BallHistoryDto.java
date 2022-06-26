package kr.co.knowledgerally.api.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "볼 내역 모델", description = "볼 내역을 나타내는 모델")
public class BallHistoryDto {
    @ApiModelProperty(value = "제목", accessMode = ApiModelProperty.AccessMode.READ_ONLY,
            position = PropertyDisplayOrder.TITLE)
    @JsonProperty(index = PropertyDisplayOrder.TITLE)
    private String title;

    @ApiModelProperty(value = "내용", accessMode = ApiModelProperty.AccessMode.READ_ONLY,
            position = PropertyDisplayOrder.CONTENT)
    @JsonProperty(index = PropertyDisplayOrder.CONTENT)
    private String content;

    @ApiModelProperty(value = "볼 개수", accessMode = ApiModelProperty.AccessMode.READ_ONLY,
            position = PropertyDisplayOrder.COUNT)
    @JsonProperty(index = PropertyDisplayOrder.COUNT)
    private int count;

    @ApiModelProperty(value = "내역 날짜", accessMode = ApiModelProperty.AccessMode.READ_ONLY,
            position = PropertyDisplayOrder.HISTORY_DATE)
    @JsonProperty(index = PropertyDisplayOrder.HISTORY_DATE)
    private String historyDate;

    private static class PropertyDisplayOrder {
        private static final int TITLE = 0;
        private static final int CONTENT = 1;
        private static final int COUNT = 2;
        private static final int HISTORY_DATE = 3;
    }
}
