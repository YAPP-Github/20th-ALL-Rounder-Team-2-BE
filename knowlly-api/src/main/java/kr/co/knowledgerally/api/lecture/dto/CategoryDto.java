package kr.co.knowledgerally.api.lecture.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.knowledgerally.core.lecture.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "카테고리 모델", description = "클래스 카테고리를 나타내는 모델")
@Deprecated
public class CategoryDto {
    @ApiModelProperty(value = "카테고리 이름", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    @JsonProperty
    private Category.Name name;
}
