package kr.co.knowledgerally.api.lecture.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "클래스 일정 등록 모델", description = "클래스 일정 등록용 모델")
public class LectureRegisterDto {
    @Valid
    private List<LectureDto> schedules;
}
