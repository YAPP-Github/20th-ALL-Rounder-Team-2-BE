package kr.co.knowledgerally.api.lecture.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.knowledgerally.api.coach.dto.CoachDto;
import kr.co.knowledgerally.api.core.validation.annotation.DateFormat;
import kr.co.knowledgerally.api.core.validation.annotation.DateTimeFormat;
import kr.co.knowledgerally.core.lecture.entity.Lecture;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;
import java.util.Set;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "클래스 일정 모델", description = "클래스 일정을 나타내는 모델")
public class LectureDto {

    @DateTimeFormat
    @ApiModelProperty(value = "클래스 일정 시작 시간, 형식 yyyy-MM-ddTHH:mm (ISO 8601)", position = PropertyDisplayOrder.START_AT)
    @JsonProperty(index = PropertyDisplayOrder.START_AT)
    private String startAt;

    @DateTimeFormat
    @ApiModelProperty(value = "클래스 일정 종료 시간, 형식 yyyy-MM-ddTHH:mm (ISO 8601)", position = PropertyDisplayOrder.END_AT)
    @JsonProperty(index = PropertyDisplayOrder.END_AT)
    private String endAt;

    @SuperBuilder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel(value = "클래스 일정 읽기 모델", description = "읽기 전용 클래스 일정 모델")
    public static class ReadOnly extends LectureDto {
        @ApiModelProperty(
                value = "클래스 일정 id",
                position = PropertyDisplayOrder.ID,
                accessMode = ApiModelProperty.AccessMode.READ_ONLY
        )
        @JsonProperty(index = PropertyDisplayOrder.ID)
        private Long id;

        @ApiModelProperty(
                value = "클래스 일정 현재 상태 \n" +
                        "ON_BOARD : 예정\n" +
                        "ON_GOING : 진행 중\n" +
                        "DONE : 완료",
                position = PropertyDisplayOrder.STATE,
                accessMode = ApiModelProperty.AccessMode.READ_ONLY
        )
        @JsonProperty(index = PropertyDisplayOrder.STATE)
        private Lecture.State state;

        @ApiModelProperty(
                value = "후기가 작성되었는지 여부",
                position = PropertyDisplayOrder.IS_REVIEW_WRITTEN,
                accessMode = ApiModelProperty.AccessMode.READ_ONLY
        )
        @JsonProperty(index = PropertyDisplayOrder.IS_REVIEW_WRITTEN)
        private boolean isReviewWritten;
    }

    private static class PropertyDisplayOrder {
        private static final int ID = 0;
        private static final int START_AT = 1;
        private static final int END_AT = 2;
        private static final int STATE = 3;
        private static final int IS_REVIEW_WRITTEN = 4;
    }
}
