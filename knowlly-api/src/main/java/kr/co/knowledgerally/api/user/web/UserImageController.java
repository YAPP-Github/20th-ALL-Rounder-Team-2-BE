package kr.co.knowledgerally.api.user.web;

import io.swagger.annotations.*;
import kr.co.knowledgerally.api.core.annotation.CurrentUser;
import kr.co.knowledgerally.api.core.dto.ApiResult;
import kr.co.knowledgerally.api.user.dto.UserImageDto;
import kr.co.knowledgerally.api.user.service.UserImageUploadService;
import kr.co.knowledgerally.core.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 회원 정보 관련 엔드포인트
 */
@Api(value = "회원 정보 관련 엔드포인트", tags = "회원 정보")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserImageController {
    private final UserImageUploadService userImageUploadService;

    @ApiOperation(value = "프로필 이미지 업로드", notes = "프로필 이미지를 업로드할 수 있습니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "업로드 성공"),
    })
    @PostMapping("/image")
    public ResponseEntity<ApiResult<UserImageDto>> signup(
            @ApiIgnore @CurrentUser User loggedInUser,
            @ApiParam(value = "업로드할 이미지", required = true)
            @RequestParam("image") MultipartFile image) {
        return ResponseEntity.ok(ApiResult.ok(userImageUploadService.upload(image, loggedInUser)));
    }
}
