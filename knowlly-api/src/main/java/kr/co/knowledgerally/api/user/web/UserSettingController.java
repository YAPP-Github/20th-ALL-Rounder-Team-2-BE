package kr.co.knowledgerally.api.user.web;

import io.swagger.annotations.*;
import kr.co.knowledgerally.api.core.annotation.CurrentUser;
import kr.co.knowledgerally.api.core.dto.ApiResult;
import kr.co.knowledgerally.api.user.dto.UserProfileDto;
import kr.co.knowledgerally.api.user.service.UserProfileService;
import kr.co.knowledgerally.core.user.entity.User;
import kr.co.knowledgerally.core.user.service.UserService;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 사용자 설정 관련 엔드포인트
 */
@Api(value = "사용자 설정 관련 엔드포인트", tags = "사용자 설정")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/setting")
public class UserSettingController {
    private final UserService userService;

    @ApiOperation(value = "푸시 알림 설정", notes = "로그인한 사용자의 푸시 알림 수신 여부를 설정합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "수정 성공"),
    })
    @PatchMapping("/push")
    public ResponseEntity<ApiResult<PushSettingDto>> setPush(@ApiIgnore @CurrentUser User loggedInUser,
                                                             @RequestBody PushSettingDto pushSettingDto) {
        loggedInUser.setPushActive(pushSettingDto.isPushActive);
        userService.saveUser(loggedInUser);
        return ResponseEntity.ok(ApiResult.ok(pushSettingDto));
    }

    @ApiModel(value = "사용자 푸시 알림 모델", description = "사용자 푸시 알림 설정 여부를 나타내는 모델")
    @NoArgsConstructor
    @Getter @Setter
    public static class PushSettingDto {
        private boolean isPushActive;
    }
}
