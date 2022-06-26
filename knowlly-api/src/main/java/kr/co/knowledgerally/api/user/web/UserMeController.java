package kr.co.knowledgerally.api.user.web;

import io.swagger.annotations.*;
import kr.co.knowledgerally.api.core.annotation.CurrentUser;
import kr.co.knowledgerally.api.core.dto.ApiResult;
import kr.co.knowledgerally.api.user.component.UserImageMapper;
import kr.co.knowledgerally.api.user.component.UserMapper;
import kr.co.knowledgerally.api.user.dto.UserDto;
import kr.co.knowledgerally.api.user.dto.UserProfileDto;
import kr.co.knowledgerally.api.user.service.UserDropoutService;
import kr.co.knowledgerally.api.user.service.UserModifyService;
import kr.co.knowledgerally.api.user.service.UserProfileService;
import kr.co.knowledgerally.core.core.exception.BadRequestException;
import kr.co.knowledgerally.core.user.entity.User;
import kr.co.knowledgerally.core.user.service.UserImageService;
import kr.co.knowledgerally.core.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * 내 정보 관련 엔드포인트
 */
@Api(value = "내 정보 관련 엔드포인트")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserMeController {
    private final UserProfileService userProfileService;
    private final UserModifyService userModifyService;
    private final UserDropoutService userDropoutService;

    @ApiOperation(value = "로그인한 사용자 가져오기", notes = "현재 로그인한 사용자를 가져옵니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회 성공"),
    })
    @GetMapping("/me")
    public ResponseEntity<ApiResult<UserProfileDto>> meGet(@ApiIgnore @CurrentUser User loggedInUser) {
        return ResponseEntity.ok(ApiResult.ok(userProfileService.getProfile(loggedInUser)));
    }

    @ApiOperation(value = "로그인한 사용자 수정하기", notes = "현재 로그인한 사용자를 수정합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "수정 성공"),
    })
    @PatchMapping("/me")
    public ResponseEntity<ApiResult<UserDto.ReadOnly>> mePatch(@ApiIgnore @CurrentUser User loggedInUser,
                                                             @ApiParam(value = "수정할 회원 정보", required = true)
                                                             @RequestBody @Valid UserDto userDto) {
        return ResponseEntity.ok(ApiResult.ok(userModifyService.modify(userDto, loggedInUser)));
    }

    @ApiOperation(value = "로그인한 사용자 탈퇴하기", notes = "현재 로그인한 사용자를 탈퇴합니다.\n*주의 : 테스트용 JWT를 사용한 채로 이 엔드포인트를 호출하지 마세요. 데이터 꼬입니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "삭제 성공"),
    })
    @DeleteMapping("/me")
    public ResponseEntity<ApiResult<String>> meDelete(@ApiIgnore @CurrentUser User loggedInUser) {
        // 임시조치 - 테스트용 JWT 삭제 방지, 프론트에서 개발이 어느정도 완료되면 삭제
        if ("kakao_identifier1".equals(loggedInUser.getIdentifier())) {
            throw new BadRequestException("테스트용 JWT로 삭제요청하지 마세요!!");
        }

        userDropoutService.dropOut(loggedInUser);
        return ResponseEntity.ok(ApiResult.ok("로그인된 사용자가 삭제되었습니다."));
    }
}
