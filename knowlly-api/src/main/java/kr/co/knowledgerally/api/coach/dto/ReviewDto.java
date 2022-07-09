package kr.co.knowledgerally.api.coach.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.knowledgerally.api.user.dto.UserDto;
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
@ApiModel(value = "후기 모델", description = "후기를 나타내는 모델")
public class ReviewDto {

    @ApiModelProperty(value = "내용", position = PropertyDisplayOrder.CONTENT)
    @JsonProperty(index = PropertyDisplayOrder.CONTENT)
    private String content;

    @ApiModelProperty(value = "공개 여부", position = PropertyDisplayOrder.IS_PUBLIC)
    @JsonProperty(index = PropertyDisplayOrder.IS_PUBLIC)
    private boolean isPublic;

    @SuperBuilder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel(value = "후기 읽기 모델", description = "읽기 전용 후기 모델")
    public static class ReadOnly extends ReviewDto {
        @ApiModelProperty(value = "작성자", accessMode = ApiModelProperty.AccessMode.READ_ONLY,
                position = PropertyDisplayOrder.WRITER)
        @JsonProperty(index = PropertyDisplayOrder.WRITER)
        private UserDto.ReadOnly writer;

        @ApiModelProperty(value = "대상 코치", accessMode = ApiModelProperty.AccessMode.READ_ONLY,
                position = PropertyDisplayOrder.REVIEWEE)
        @JsonProperty(index = PropertyDisplayOrder.REVIEWEE)
        private CoachDto.ReadOnly reviewee;

        @ApiModelProperty(value = "클래스 명", accessMode = ApiModelProperty.AccessMode.READ_ONLY,
                position = PropertyDisplayOrder.CLASS_NAME)
        @JsonProperty(index = PropertyDisplayOrder.CLASS_NAME)
        private String className;

        @ApiModelProperty(value = "작성된 날짜", accessMode = ApiModelProperty.AccessMode.READ_ONLY,
                position = PropertyDisplayOrder.WRITTEN_DATE)
        @JsonProperty(index = PropertyDisplayOrder.WRITTEN_DATE)
        private String writtenDate;
    }

    private static class PropertyDisplayOrder {
        private static final int WRITER = 0;
        private static final int REVIEWEE = 1;
        private static final int CLASS_NAME = 2;
        private static final int CONTENT = 3;
        private static final int IS_PUBLIC = 4;
        private static final int WRITTEN_DATE = 5;
    }
}
