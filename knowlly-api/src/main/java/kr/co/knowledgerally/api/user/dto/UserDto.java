package kr.co.knowledgerally.api.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.knowledgerally.api.core.validation.annotation.KorEngOnly;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "사용자 모델", description = "사용자를 나타내는 모델")
public class UserDto {

    @NotBlank
    @ApiModelProperty(value = "사용자 이름", required = true, position = PropertyDisplayOrder.USERNAME)
    @JsonProperty(index = PropertyDisplayOrder.USERNAME)
    private String username;

    @KorEngOnly
    @NotBlank
    @ApiModelProperty(value = "사용자 자기 소개", required = true, position = PropertyDisplayOrder.INTRO)
    @JsonProperty(index = PropertyDisplayOrder.INTRO)
    private String intro;

    @ApiModelProperty(value = "사용자 카카오 ID", position = PropertyDisplayOrder.KAKAO_ID)
    @JsonProperty(index = PropertyDisplayOrder.KAKAO_ID)
    private String kakaoId;

    @ApiModelProperty(value = "사용자 소개 웹사이트 / 포트폴리오", position = PropertyDisplayOrder.PORTFOLIO)
    @JsonProperty(index = PropertyDisplayOrder.PORTFOLIO)
    private String portfolio;

    @SuperBuilder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel(value = "회원 읽기 모델", description = "읽기 전용 회원 모델")
    public static class ReadOnly extends UserDto {
        @ApiModelProperty(value = "회원 고유 아이디", accessMode = ApiModelProperty.AccessMode.READ_ONLY,
                position = PropertyDisplayOrder.ID)
        @JsonProperty(index = PropertyDisplayOrder.ID)
        private Long id;

        @ApiModelProperty(value = "볼 토큰 개수", accessMode = ApiModelProperty.AccessMode.READ_ONLY,
                position = PropertyDisplayOrder.BALLCNT)
        @JsonProperty(index = PropertyDisplayOrder.BALLCNT)
        private int ballCnt;

        @ApiModelProperty(value = "코치 여부", accessMode = ApiModelProperty.AccessMode.READ_ONLY,
                position = PropertyDisplayOrder.IS_COACH)
        @JsonProperty(index = PropertyDisplayOrder.IS_COACH)
        private boolean isCoach;

        @ApiModelProperty(value = "푸시 알림 활성화 여부", accessMode = ApiModelProperty.AccessMode.READ_ONLY,
                position = PropertyDisplayOrder.IS_PUSH_ACTIVE)
        @JsonProperty(index = PropertyDisplayOrder.IS_PUSH_ACTIVE)
        private boolean isPushActive;

        @ApiModelProperty(value = "사용자 식별값", position = PropertyDisplayOrder.IDENTIFIER)
        @JsonProperty(index = PropertyDisplayOrder.IDENTIFIER)
        private String identifier;
    }

    private static class PropertyDisplayOrder {
        private static final int ID = 0;
        private static final int USERNAME = 1;
        private static final int BALLCNT = 2;
        private static final int INTRO = 3;
        private static final int KAKAO_ID = 4;
        private static final int PORTFOLIO = 5;
        private static final int IDENTIFIER = 6;
        private static final int IS_COACH = 7;
        private static final int IS_PUSH_ACTIVE = 8;
    }
}
