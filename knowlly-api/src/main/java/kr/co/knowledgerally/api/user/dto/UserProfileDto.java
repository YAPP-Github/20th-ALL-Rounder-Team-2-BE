package kr.co.knowledgerally.api.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.knowledgerally.api.coach.dto.CoachDto;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "사용자 프로필 모델", description = "사용자 프로필 모델")
public class UserProfileDto {
    @ApiModelProperty(value = "사용자 프로필", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    @JsonProperty
    private UserDto.ReadOnly user;

    @Deprecated
    @ApiModelProperty(value = "프로필 이미지 (삭제 예정)", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    @JsonProperty
    private UserImageDto userImage;

    @ApiModelProperty(value = "코치 정보, 코치 아닐시 null", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    @JsonProperty
    private CoachDto.ReadOnly coach;
}
