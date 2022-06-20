package kr.co.knowledgerally.api.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "사용자 회원가입 모델", description = "사용자 회원가입용 모델")
public class UserOnboardDto {
    @ApiModelProperty(value = "사용자 프로필")
    @JsonProperty
    private UserDto user;

    @ApiModelProperty(value = "프로필 이미지")
    @JsonProperty
    private UserImageDto userImage;

    @Builder
    @Getter
    public static class ReadOnly {
        @ApiModelProperty(value = "사용자 프로필")
        @JsonProperty
        private UserDto.ReadOnly user;

        @ApiModelProperty(value = "프로필 이미지")
        @JsonProperty
        private UserImageDto userImage;
    }
}
