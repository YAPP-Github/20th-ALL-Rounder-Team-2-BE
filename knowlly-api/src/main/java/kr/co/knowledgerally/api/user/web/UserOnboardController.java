package kr.co.knowledgerally.api.user.web;

import io.swagger.annotations.*;
import kr.co.knowledgerally.api.core.annotation.CurrentUser;
import kr.co.knowledgerally.api.core.dto.ApiResult;
import kr.co.knowledgerally.api.user.dto.UserDto;
import kr.co.knowledgerally.api.user.dto.UserProfileDto;
import kr.co.knowledgerally.api.user.service.UserOnboardService;
import kr.co.knowledgerally.api.user.service.UserProfileService;
import kr.co.knowledgerally.core.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * 사용자 온보딩 관련 엔드포인트
 */
@Api(value = "사용자 온보딩 관련 엔드포인트", tags = "사용자 온보딩")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserOnboardController {
    private final UserOnboardService userOnboardService;

    @ApiOperation(value = "로그인한 사용자 온보딩 체크", notes = "현재 로그인한 사용자가 온보딩 되었는지 확인합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회 성공"),
    })
    @GetMapping("/check-onboard")
    public ResponseEntity<ApiResult<OnboardCheckDto>> onboardCheck(@ApiIgnore @CurrentUser User loggedInUser) {
        return ResponseEntity.ok(ApiResult.ok(new OnboardCheckDto(loggedInUser.isOnboard())));
    }

    @ApiOperation(value = "로그인한 사용자 온보딩", notes = "현재 로그인한 사용자를 온보딩합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "온보딩 성공"),
    })
    @PatchMapping("/onboard")
    public ResponseEntity<ApiResult<UserDto.ReadOnly>> onboard(@ApiIgnore @CurrentUser User loggedInUser,
                                                               @ApiParam(value = "수정할 회원 정보", required = true)
                                                               @RequestBody @Valid UserDto userDto) {
        return ResponseEntity.ok(ApiResult.ok(userOnboardService.onboard(userDto, loggedInUser)));
    }

    @ApiIgnore
    @AllArgsConstructor
    @Getter
    public static class OnboardCheckDto {
        private final boolean isOnboard;
    }
}
