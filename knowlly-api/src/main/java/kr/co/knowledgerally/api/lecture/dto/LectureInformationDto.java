package kr.co.knowledgerally.api.lecture.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.knowledgerally.api.coach.dto.CoachDto;
import kr.co.knowledgerally.core.coach.entity.Coach;
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
@ApiModel(value = "클래스-info 모델", description = "클래스-info를 나타내는 모델")
public class LectureInformationDto {
    @ApiModelProperty(value = "클래스-info 제목", position = PropertyDisplayOrder.TOPIC)
    @JsonProperty(index = PropertyDisplayOrder.TOPIC)
    private String topic;

    @ApiModelProperty(value = "클래스-info 소개", position = PropertyDisplayOrder.INTRODUCE)
    @JsonProperty(index = PropertyDisplayOrder.INTRODUCE)
    private String introduce;

    @SuperBuilder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel(value = "클래스-info 읽기 모델", description = "읽기 전용 클래스-info 모델")
    public static class ReadOnly extends LectureInformationDto {
        @ApiModelProperty(value = "클래스-info 가격", position = PropertyDisplayOrder.PRICE)
        @JsonProperty(index = PropertyDisplayOrder.PRICE)
        private int price;

        @ApiModelProperty(value = "코치 정보", position = PropertyDisplayOrder.COACH)
        @JsonProperty(index = PropertyDisplayOrder.COACH)
        private CoachDto.ReadOnly coach;

        @ApiModelProperty(value = "카테고리 정보", position = PropertyDisplayOrder.CATEGORY)
        @JsonProperty(index = PropertyDisplayOrder.CATEGORY)
        private CategoryDto category;
    }

    private static class PropertyDisplayOrder {
        private static final int TOPIC = 0;
        private static final int INTRODUCE = 1;
        private static final int PRICE = 2;
        private static final int COACH = 3;
        private static final int CATEGORY = 4;
    }
}
