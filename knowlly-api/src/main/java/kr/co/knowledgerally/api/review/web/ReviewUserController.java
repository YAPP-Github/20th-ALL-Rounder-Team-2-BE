package kr.co.knowledgerally.api.review.web;

import io.swagger.annotations.*;
import kr.co.knowledgerally.api.coach.component.ReviewMapper;
import kr.co.knowledgerally.api.coach.dto.ReviewDto;
import kr.co.knowledgerally.api.core.annotation.CurrentUser;
import kr.co.knowledgerally.api.core.dto.ApiPageRequest;
import kr.co.knowledgerally.api.core.dto.ApiPageResult;
import kr.co.knowledgerally.api.review.service.ReviewUserService;
import kr.co.knowledgerally.core.coach.entity.Review;
import kr.co.knowledgerally.core.coach.service.ReviewService;
import kr.co.knowledgerally.core.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * 사용자 리뷰 관련 엔드포인트
 */
@Api(value = "사용자 리뷰 관련 엔드포인트")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review/user")
public class ReviewUserController {
    private final ReviewUserService reviewUserService;
    private final ReviewMapper reviewMapper;

    @ApiOperation(value = "특정 사용자의 리뷰 가져오기", notes = "특정 사용자의 리뷰 목록을 가져옵니다. 이때 사용자는 coach로 등록되어 있어야 합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회 성공"),
    })
    @GetMapping("/{userId}")
    public ResponseEntity<ApiPageResult<ReviewDto.ReadOnly>> getReviews(@ApiParam(value = "사용자 id", required = true)
                                                                        @PathVariable
                                                                        Long userId,
                                                                        @ApiParam(value = "비공개된 리뷰까지 모두 가져올 것인지",
                                                                                defaultValue = "false")
                                                                        @RequestParam(value = "include_private",
                                                                                required = false,
                                                                                defaultValue = "false")
                                                                        boolean includePrivate,
                                                                        ApiPageRequest pageRequest) {
        return ResponseEntity.ok(ApiPageResult.ok(
                reviewUserService.findAllByRevieweeWithPageable(userId, includePrivate, pageRequest.convert())
                        .map(reviewMapper::toDto)));
    }

    @ApiOperation(value = "로그인한 사용자의 리뷰 가져오기", notes = "로그인한 사용자의 리뷰 목록을 가져옵니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회 성공"),
    })
    @GetMapping("/me")
    public ResponseEntity<ApiPageResult<ReviewDto.ReadOnly>> getMyReviews(@ApiParam(value = "비공개된 리뷰까지 모두 가져올 것인지", defaultValue = "true")
                                                                          @RequestParam(value = "include_private",
                                                                                  required = false,
                                                                                  defaultValue = "true")
                                                                          boolean includePrivate,
                                                                          ApiPageRequest pageRequest,
                                                                          @ApiIgnore @CurrentUser User loggedInUser) {
        return ResponseEntity.ok(ApiPageResult.ok(
                reviewUserService.findAllByRevieweeWithPageable(loggedInUser.getId(), includePrivate, pageRequest.convert())
                        .map(reviewMapper::toDto)));
    }
}
