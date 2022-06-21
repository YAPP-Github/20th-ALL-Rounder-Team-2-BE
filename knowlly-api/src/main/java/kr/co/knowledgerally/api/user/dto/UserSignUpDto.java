package kr.co.knowledgerally.api.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.knowledgerally.api.core.jwt.dto.JwtToken;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "사용자 회원가입 응답 모델", description = "사용자 회원가입 응답 모델")
public class UserSignUpDto {
    @ApiModelProperty(value = "발급된 jwt 토큰")
    @JsonProperty
    private JwtToken jwtToken;

    @ApiModelProperty(value = "사용자 프로필")
    @JsonProperty
    private UserDto user;
}
