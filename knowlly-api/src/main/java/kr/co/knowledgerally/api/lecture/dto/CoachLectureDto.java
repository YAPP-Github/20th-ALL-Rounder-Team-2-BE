package kr.co.knowledgerally.api.lecture.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.knowledgerally.api.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "운영 클래스 조회 모델", description = "운영 클래스를 나타내는 모델")
public class CoachLectureDto {
    @ApiModelProperty(
            value = "클래스 일정 읽기 모델",
            position = PropertyDisplayOrder.LECTURE,
            accessMode = ApiModelProperty.AccessMode.READ_ONLY
    )
    @JsonProperty(index = PropertyDisplayOrder.LECTURE)
    private LectureDto.ReadOnly lecture;

    @ApiModelProperty(
            value = "클래스-info 읽기 모델",
            position = PropertyDisplayOrder.LECTURE_INFORMATION,
            accessMode = ApiModelProperty.AccessMode.READ_ONLY
    )
    @JsonProperty(index = PropertyDisplayOrder.LECTURE_INFORMATION)
    private LectureInformationDtoReadOnly lectureInformation;

    @ApiModelProperty(
            value = "신청서 읽기 모델 목록. 이때 예정된, 혹은 완료된 상태일 경우 크기는 1",
            position = PropertyDisplayOrder.FORMS,
            accessMode = ApiModelProperty.AccessMode.READ_ONLY
    )
    @JsonProperty(index = PropertyDisplayOrder.FORMS)
    private List<FormDto.ReadOnly> forms;

    @ApiModelProperty(
            value = "매칭되었는지에 대한 여부",
            position = PropertyDisplayOrder.IS_MATCHED,
            accessMode = ApiModelProperty.AccessMode.READ_ONLY
    )
    @JsonProperty(index = PropertyDisplayOrder.IS_MATCHED)
    private boolean isMatched;

    @ApiModelProperty(
            value = "매칭된 유저. 아직 매칭 중인 상태라면 null",
            position = PropertyDisplayOrder.MATCHED_USER,
            accessMode = ApiModelProperty.AccessMode.READ_ONLY
    )
    @JsonProperty(index = PropertyDisplayOrder.MATCHED_USER)
    private UserDto.ReadOnly matchedUser;

    private static class PropertyDisplayOrder {
        private static final int LECTURE = 0;
        private static final int LECTURE_INFORMATION = 1;
        private static final int FORMS = 2;
        private static final int IS_MATCHED = 3;
        private static final int MATCHED_USER = 4;
    }
}
