package kr.co.knowledgerally.api.user.web;

import io.swagger.annotations.*;
import kr.co.knowledgerally.api.core.annotation.CurrentUser;
import kr.co.knowledgerally.api.core.dto.ApiResult;
import kr.co.knowledgerally.api.user.component.BallHistoryMapper;
import kr.co.knowledgerally.api.user.dto.BallHistoryDto;
import kr.co.knowledgerally.api.user.dto.UserDto;
import kr.co.knowledgerally.api.user.dto.UserProfileDto;
import kr.co.knowledgerally.api.user.service.UserDropoutService;
import kr.co.knowledgerally.api.user.service.UserModifyService;
import kr.co.knowledgerally.api.user.service.UserProfileService;
import kr.co.knowledgerally.core.core.exception.BadRequestException;
import kr.co.knowledgerally.core.user.entity.User;
import kr.co.knowledgerally.core.user.repository.BallHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 볼 내역 조회 관련 엔드포인트
 */
@Api(value = "볼 내역 조회 관련 엔드포인트")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ballhistory")
public class BallHistoryController {
    private final BallHistoryRepository ballHistoryRepository;
    private final BallHistoryMapper ballHistoryMapper;

    @ApiOperation(value = "로그인한 사용자의 볼 내역 가져오기", notes = "현재 로그인한 사용자의 볼 내역을 가져옵니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회 성공"),
    })
    @GetMapping("/me")
    public ResponseEntity<ApiResult<List<BallHistoryDto>>> getHistories(@ApiIgnore @CurrentUser User loggedInUser) {
        return ResponseEntity.ok(ApiResult.ok(
                ballHistoryRepository.findAllByUserOrderByCreatedAtDesc(loggedInUser)
                        .stream()
                        .map(ballHistoryMapper::toDto)
                        .collect(Collectors.toList())
        ));
    }
}
