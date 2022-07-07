package kr.co.knowledgerally.api.user.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import kr.co.knowledgerally.api.core.annotation.CurrentUser;
import kr.co.knowledgerally.api.core.dto.ApiResult;
import kr.co.knowledgerally.api.user.component.BallHistoryMapper;
import kr.co.knowledgerally.api.user.component.NotificationMapper;
import kr.co.knowledgerally.api.user.dto.BallHistoryDto;
import kr.co.knowledgerally.api.user.dto.NotificationDto;
import kr.co.knowledgerally.core.user.entity.User;
import kr.co.knowledgerally.core.user.repository.BallHistoryRepository;
import kr.co.knowledgerally.core.user.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 알림 내역 조회 관련 엔드포인트
 */
@Api(value = "알림 내역 조회 관련 엔드포인트", tags = "알림 내역")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notification")
public class NotificationController {
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    @ApiOperation(value = "로그인한 사용자의 알림 내역 가져오기", notes = "현재 로그인한 사용자의 알림 내역을 가져옵니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회 성공"),
    })
    @GetMapping("/me")
    public ResponseEntity<ApiResult<List<NotificationDto>>> getHistories(@ApiIgnore @CurrentUser User loggedInUser) {
        return ResponseEntity.ok(ApiResult.ok(
                notificationRepository.findAllByUserOrCoachUser(loggedInUser).stream()
                        .map(notificationMapper::toDto).collect(Collectors.toList())));
    }
}
