package kr.co.knowledgerally.api.core.jwt.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ApiModel(value="Knowlly Jwt Token Dto", description="Knowlly 제공 JWT 및 Refresh 토큰 모델")
public class JwtToken {
    @ApiModelProperty(value = "Knowlly 제공 Access Token (jwt)")
    private String knowllyAccessToken;

    @ApiModelProperty(value = "Knowlly 제공 Refresh Token (jwt)")
    private String knowllyRefreshToken;

    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @ApiModel(value="Knowlly Refresh Token Dto", description="Knowlly 제공 Refresh 토큰 모델")
    public static class Refresh {
        @ApiModelProperty(value = "Mureng 제공 Refresh Token (jwt)")
        private String knowllyRefreshToken;
    }
}
