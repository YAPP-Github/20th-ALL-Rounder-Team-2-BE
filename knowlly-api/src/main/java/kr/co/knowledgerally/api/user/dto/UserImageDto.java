package kr.co.knowledgerally.api.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.knowledgerally.api.core.validation.annotation.KorEngOnly;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "사용자 프로필 이미지 모델", description = "사용자 프로필 이미지를 나타내는 모델")
public class UserImageDto {
    @ApiModelProperty(value = "프로필 이미지 경로")
    @JsonProperty
    private String userImgUrl;
}
