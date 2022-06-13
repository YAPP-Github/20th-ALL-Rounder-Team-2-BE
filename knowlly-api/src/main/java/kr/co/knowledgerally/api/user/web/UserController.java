package kr.co.knowledgerally.api.user.web;

import io.swagger.annotations.*;
import kr.co.knowledgerally.api.core.annotation.CurrentUser;
import kr.co.knowledgerally.api.core.dto.ApiResult;
import kr.co.knowledgerally.api.user.dto.UserOnboardDto;
import kr.co.knowledgerally.api.user.service.UserOnboardService;
import kr.co.knowledgerally.core.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * 회원 정보 관련 엔드포인트
 */
@Api(value = "회원 정보 관련 엔드포인트")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserOnboardService userOnboardService;

    @ApiOperation(value = "온보딩", notes = "온보딩입니다. 소셜 로그인 사용자의 정보를 업데이트 합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "수정 성공"),
    })
    @PatchMapping("/onboard")
    public ResponseEntity<ApiResult<UserOnboardDto.ReadOnly>> signup(
            @ApiIgnore @CurrentUser User loggedInUser,
            @ApiParam(value = "온보딩 회원 정보", required = true)
            @RequestBody @Valid UserOnboardDto userOnboardDto) {
        return ResponseEntity.ok(ApiResult.ok(userOnboardService.onBoard(userOnboardDto, loggedInUser)));
    }
}
