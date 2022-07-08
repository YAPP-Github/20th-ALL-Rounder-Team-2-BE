package kr.co.knowledgerally.api.lecture.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.knowledgerally.api.user.dto.UserDto;
import kr.co.knowledgerally.api.user.dto.UserImageDto;
import kr.co.knowledgerally.core.lecture.entity.Form;
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
@ApiModel(value = "신청서 모델", description = "신청서를 나타내는 모델")
public class FormDto {
    @ApiModelProperty(value = "신청 내용", position = PropertyDisplayOrder.CONTENT)
    @JsonProperty(index = PropertyDisplayOrder.CONTENT)
    private String content;

    @SuperBuilder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel(value = "신청서 읽기 모델", description = "읽기 전용 신청서 모델")
    public static class ReadOnly extends FormDto {
        @ApiModelProperty(
                value = "신청서 id",
                position = PropertyDisplayOrder.ID,
                accessMode = ApiModelProperty.AccessMode.READ_ONLY
        )
        @JsonProperty(index = PropertyDisplayOrder.ID)
        private Long id;

        @ApiModelProperty(
                value = "클래스 일정 읽기 모델",
                position = PropertyDisplayOrder.LECTURE,
                accessMode = ApiModelProperty.AccessMode.READ_ONLY
        )
        @JsonProperty(index = PropertyDisplayOrder.LECTURE)
        private LectureDto.ReadOnly lecture;

        @ApiModelProperty(
                value = "신청자 읽기 모델",
                position = PropertyDisplayOrder.USER,
                accessMode = ApiModelProperty.AccessMode.READ_ONLY
        )
        @JsonProperty(index = PropertyDisplayOrder.USER)
        private UserDto.ReadOnly user;

        @ApiModelProperty(value = "신청자 프로필 이미지", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
        @JsonProperty
        private UserImageDto userImage;

        @ApiModelProperty(
                value = "클래스 일정 현재 상태 \n" +
                        "REQUEST : 요청된\n" +
                        "ACCEPT : 수락된\n" +
                        "REJECT : 거절된",
                position = PropertyDisplayOrder.STATE,
                accessMode = ApiModelProperty.AccessMode.READ_ONLY
        )
        @JsonProperty(index = PropertyDisplayOrder.STATE)
        private Form.State state;

        @ApiModelProperty(value = "신청 내용",
                position = PropertyDisplayOrder.EXPIRATION_DATE,
                accessMode = ApiModelProperty.AccessMode.READ_ONLY)
        @JsonProperty(index = PropertyDisplayOrder.EXPIRATION_DATE)
        private String expirationDate;
    }

    private static class PropertyDisplayOrder {
        private static final int ID = 0;
        private static final int CONTENT = 1;
        private static final int LECTURE = 2;
        private static final int USER = 3;
        private static final int STATE = 4;
        private static final int EXPIRATION_DATE = 5;
    }
}
