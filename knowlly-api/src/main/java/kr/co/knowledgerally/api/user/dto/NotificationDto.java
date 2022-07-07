package kr.co.knowledgerally.api.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.knowledgerally.core.user.entity.Notification;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "알림 내역 모델", description = "알림 내역을 나타내는 모델")
public class NotificationDto {

    @ApiModelProperty(value = "수신자 정보 (로그인한 사용자)", accessMode = ApiModelProperty.AccessMode.READ_ONLY,
            position = PropertyDisplayOrder.RECEIVER)
    @JsonProperty(index = PropertyDisplayOrder.RECEIVER)
    private UserDto.ReadOnly receiver;

    @ApiModelProperty(value = "송신자 정보 (알림 보낸 사람)", accessMode = ApiModelProperty.AccessMode.READ_ONLY,
            position = PropertyDisplayOrder.SENDER)
    @JsonProperty(index = PropertyDisplayOrder.SENDER)
    private UserDto.ReadOnly sender;

    @ApiModelProperty(value = "제목", accessMode = ApiModelProperty.AccessMode.READ_ONLY,
            position = PropertyDisplayOrder.TITLE)
    @JsonProperty(index = PropertyDisplayOrder.TITLE)
    private String title;

    @ApiModelProperty(value = "내용", accessMode = ApiModelProperty.AccessMode.READ_ONLY,
            position = PropertyDisplayOrder.CONTENT)
    @JsonProperty(index = PropertyDisplayOrder.CONTENT)
    private String content;

    @ApiModelProperty(value = "알림 타입\n" +
            "PLAYER : 수강 클래스\n" +
            "COACH : 운영 클래스", accessMode = ApiModelProperty.AccessMode.READ_ONLY,
            position = PropertyDisplayOrder.NOTI_TYPE)
    @JsonProperty(index = PropertyDisplayOrder.NOTI_TYPE)
    private Notification.NotiType notiType;

    @ApiModelProperty(value = "읽었는지 여부", accessMode = ApiModelProperty.AccessMode.READ_ONLY,
            position = PropertyDisplayOrder.IS_READ)
    @JsonProperty(index = PropertyDisplayOrder.IS_READ)
    private boolean isRead;

    @ApiModelProperty(value = "전송 날짜", accessMode = ApiModelProperty.AccessMode.READ_ONLY,
            position = PropertyDisplayOrder.SENT_DATE)
    @JsonProperty(index = PropertyDisplayOrder.SENT_DATE)
    private String sentDate;

    private static class PropertyDisplayOrder {
        private static final int RECEIVER = 0;
        private static final int SENDER = 1;
        private static final int TITLE = 2;
        private static final int CONTENT = 3;
        private static final int NOTI_TYPE = 4;
        private static final int IS_READ = 5;
        private static final int SENT_DATE = 6;
    }
}
