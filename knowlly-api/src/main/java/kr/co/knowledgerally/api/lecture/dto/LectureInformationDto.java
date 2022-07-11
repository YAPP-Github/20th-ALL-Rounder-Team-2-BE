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

    private static class PropertyDisplayOrder {
        private static final int TOPIC = 0;
        private static final int INTRODUCE = 1;
        private static final int CATEGORY = 2;
        private static final int IMAGE = 3;
        private static final int TAG = 4;
    }
}
