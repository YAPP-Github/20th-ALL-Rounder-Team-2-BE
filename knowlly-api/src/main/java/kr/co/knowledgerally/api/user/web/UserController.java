package kr.co.knowledgerally.api.user.web;

import io.swagger.annotations.*;
import kr.co.knowledgerally.api.core.annotation.CurrentUser;
import kr.co.knowledgerally.api.core.dto.ApiResult;
import kr.co.knowledgerally.api.user.dto.UserProfileDto;
import kr.co.knowledgerally.api.user.service.UserProfileService;
import kr.co.knowledgerally.core.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 사용자 정보 관련 엔드포인트
 */
@Api(value = "사용자 정보 관련 엔드포인트")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserProfileService userProfileService;

    @ApiOperation(value = "특정 사용자의 프로필 가져오기", notes = "특정 사용자의 프로필 정보를 가져옵니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회 성공"),
    })
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResult<UserProfileDto>> getUserInfo(@ApiParam(value = "사용자 id", required = true)
                                                           @PathVariable Long userId) {
        return ResponseEntity.ok(ApiResult.ok(userProfileService.getProfile(userId)));
    }
}
