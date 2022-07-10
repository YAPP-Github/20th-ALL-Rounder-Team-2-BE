package kr.co.knowledgerally.api.lecture.dto;

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
@ApiModel(value = "클래스 이미지 모델", description = "클래스 이미지를 나타내는 모델")
public class LectureImageDto {

    @ApiModelProperty(value = "클래스 이미지 id", position = PropertyDisplayOrder.ID)
    @JsonProperty(index = PropertyDisplayOrder.ID)
    private Long id;
    @SuperBuilder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel(value = "클래스 이미지 읽기 모델", description = "읽기 전용 클래스 이미지 모델")
    public static class ReadOnly extends LectureImageDto {
        @ApiModelProperty(
                value = "클래스 이미지 경로",
                accessMode = ApiModelProperty.AccessMode.READ_ONLY,
                position = PropertyDisplayOrder.IMAGE
        )
        @JsonProperty(index = PropertyDisplayOrder.IMAGE)
        private String lectureImgUrl;
    }

    private static class PropertyDisplayOrder {
        private static final int ID = 0;
        private static final int IMAGE = 1;
    }
}
