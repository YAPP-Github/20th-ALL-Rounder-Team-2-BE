package kr.co.knowledgerally.api.user.web;

import io.swagger.annotations.*;
import kr.co.knowledgerally.api.core.dto.ApiResult;
import kr.co.knowledgerally.api.core.jwt.dto.JwtToken;
import kr.co.knowledgerally.api.core.jwt.dto.ProviderToken;
import kr.co.knowledgerally.api.core.jwt.dto.TokenProvider;
import kr.co.knowledgerally.api.core.jwt.service.JwtService;
import kr.co.knowledgerally.api.core.oauth2.dto.OAuth2Profile;
import kr.co.knowledgerally.api.core.oauth2.service.OAuth2ServiceFactory;
import kr.co.knowledgerally.api.user.service.UserSignUpService;
import kr.co.knowledgerally.core.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * 회원 인증 관련 엔드포인트
 */
@Api(value = "회원 인증 관련 엔드포인트")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserAuthController {
    private final OAuth2ServiceFactory oAuth2ServiceFactory;
    private final JwtService jwtService;
    private final UserService userService;
    private final UserSignUpService signUpService;

    @ApiOperation(value = "사용자 존재 체크", notes = "기존에 있던 사용자인지 체크합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회 성공"),
    })
    @GetMapping("/user-exists")
    public ResponseEntity<ApiResult<UserExistsDto>> userExists(
            @ApiParam(value = "프로바이더 제공 액세스 토큰", required = true)
            @RequestParam(name = "provider_token") String providerToken,
            @ApiParam(value = "프로바이더 이름 (현재는 KAKAO만 가능) (생략가능)")
            @RequestParam(name = "provider_name", required = false, defaultValue = "KAKAO") String providerName) {
        OAuth2Profile profile = oAuth2ServiceFactory.getInstance(TokenProvider.valueOf(providerName)).getProfile(providerToken);
        return ResponseEntity.ok(ApiResult.ok(
                new UserExistsDto(userService.isMemberExistByIdentifier(profile.getIdentifier()))
        ));
    }

    @ApiOperation(value = "사용자 등록 (JWT 발급)", notes = "사용자를 신규로 등록하고, JWT를 발급합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "등록 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청, 이미 등록한 사용자"),
    })
    @PostMapping("/signup")
    public ResponseEntity<ApiResult<JwtToken>> signup(
            @ApiParam(value = "액세스 토큰", required = true) @RequestBody @Valid ProviderToken providerToken) {
        return ResponseEntity.ok(ApiResult.ok(
                signUpService.signUp(providerToken)));
    }

    @ApiOperation(value = "사용자 로그인 (JWT 발급)", notes = "사용자에게 JWT를 발급합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "발급 성공"),
    })
    @PostMapping("/signin")
    public ResponseEntity<ApiResult<JwtToken>> signin(
            @ApiParam(value = "액세스 토큰", required = true) @RequestBody @Valid ProviderToken providerToken) {
        return ResponseEntity.ok(ApiResult.ok(
                jwtService.issue(providerToken)));
    }

    @ApiOperation(value = "JWT 발급 새로 고침", notes = "refreshToken을 사용하여 JWT를 새로고침 합니다")
    @ApiResponses({
            @ApiResponse(code = 200, message = "발급 성공")
    })
    @GetMapping("/refresh")
    public ResponseEntity<ApiResult<JwtToken>> refresh(
            @ApiParam(value = "프로바이더 제공 액세스 토큰", required = true) @RequestParam(name = "refresh_token") String knowllyRefreshToken) {
        return ResponseEntity.ok(ApiResult.ok(
                jwtService.refresh(new JwtToken.Refresh(knowllyRefreshToken))));
    }

    @ApiIgnore
    @AllArgsConstructor
    @Getter
    public static class UserExistsDto {
        private final boolean exists;
    }
}
