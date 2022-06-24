package kr.co.knowledgerally.api.coach.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.knowledgerally.api.user.dto.UserDto;
import kr.co.knowledgerally.core.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "코치 모델", description = "코치를 나타내는 모델")
public class CoachDto {
    @ApiModelProperty(value = "코치 소개", accessMode = ApiModelProperty.AccessMode.READ_ONLY,
            position = PropertyDisplayOrder.INTRODUCE)
    @JsonProperty(index = PropertyDisplayOrder.INTRODUCE)
    private String introduce;

    @SuperBuilder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel(value = "코치 읽기 모델", description = "읽기 전용 코치 모델")
    public static class ReadOnly extends CoachDto {
        @ApiModelProperty(value = "코치 고유 아이디", accessMode = ApiModelProperty.AccessMode.READ_ONLY,
                position = PropertyDisplayOrder.ID)
        @JsonProperty(index = PropertyDisplayOrder.ID)
        private Long id;

        @ApiModelProperty(value = "사용자 정보", accessMode = ApiModelProperty.AccessMode.READ_ONLY,
                position = PropertyDisplayOrder.USER)
        @JsonProperty(index = PropertyDisplayOrder.USER)
        private UserDto.ReadOnly user;
    }

    private static class PropertyDisplayOrder {
        private static final int ID = 0;
        private static final int INTRODUCE = 1;
        private static final int USER = 2;
    }
}
