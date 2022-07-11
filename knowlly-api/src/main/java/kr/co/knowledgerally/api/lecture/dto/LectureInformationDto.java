package kr.co.knowledgerally.api.lecture.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.knowledgerally.api.coach.dto.CoachDto;
import kr.co.knowledgerally.core.lecture.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "클래스-info 모델", description = "클래스-info를 나타내는 모델")
public class LectureInformationDto {
    @ApiModelProperty(value = "클래스-info 제목", position = PropertyDisplayOrder.TOPIC)
    @JsonProperty(index = PropertyDisplayOrder.TOPIC)
    private String topic;

    @ApiModelProperty(value = "클래스-info 소개", position = PropertyDisplayOrder.INTRODUCE)
    @JsonProperty(index = PropertyDisplayOrder.INTRODUCE)
    private String introduce;

    @ApiModelProperty(value = "클래스-info 이미지", position = PropertyDisplayOrder.IMAGE)
    @JsonProperty(index = PropertyDisplayOrder.IMAGE)
    private Set<LectureImageDto> lectureImages;

    @ApiModelProperty(value = "클래스 태그", position = PropertyDisplayOrder.TAG)
    @JsonProperty(index = PropertyDisplayOrder.TAG)
    private Set<TagDto> tags;

    @ApiModelProperty(
            value = "카테고리 정보",
            position = PropertyDisplayOrder.CATEGORY
    )
    @JsonProperty(index = PropertyDisplayOrder.CATEGORY)
    private Category.Name category;

    @SuperBuilder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel(value = "클래스-info 읽기 모델", description = "읽기 전용 클래스-info 모델")
    public static class ReadOnly extends LectureInformationDto {
        @ApiModelProperty(
                value = "클래스-info id",
                position = PropertyDisplayOrder.ID,
                accessMode = ApiModelProperty.AccessMode.READ_ONLY
        )
        @JsonProperty(index = PropertyDisplayOrder.ID)
        private Long id;

        @ApiModelProperty(
                value = "클래스-info 가격",
                position = PropertyDisplayOrder.PRICE,
                accessMode = ApiModelProperty.AccessMode.READ_ONLY
        )
        @JsonProperty(index = PropertyDisplayOrder.PRICE)
        private int price;

        @ApiModelProperty(
                value = "코치 정보",
                position = PropertyDisplayOrder.COACH,
                accessMode = ApiModelProperty.AccessMode.READ_ONLY
        )
        @JsonProperty(index = PropertyDisplayOrder.COACH)
        private CoachDto.ReadOnly coach;

        @ApiModelProperty(
                value = "클래스 일정",
                position = PropertyDisplayOrder.TAG,
                accessMode = ApiModelProperty.AccessMode.READ_ONLY
        )
        @JsonProperty(index = PropertyDisplayOrder.LECTURE)
        private List<LectureDto.ReadOnly> lectures;
    }

    private static class PropertyDisplayOrder {
        private static final int ID = 0;
        private static final int TOPIC = 1;
        private static final int INTRODUCE = 2;
        private static final int PRICE = 3;
        private static final int COACH = 4;
        private static final int CATEGORY = 5;
        private static final int IMAGE = 6;
        private static final int TAG = 7;
        private static final int LECTURE = 8;
    }
}
