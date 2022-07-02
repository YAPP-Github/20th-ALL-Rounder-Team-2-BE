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

    @ApiModelProperty(value = "클래스 이미지 id", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    @JsonProperty
    private Long id;

    @ApiModelProperty(value = "클래스 이미지 경로")
    @JsonProperty
    private String lectureImgUrl;
}
