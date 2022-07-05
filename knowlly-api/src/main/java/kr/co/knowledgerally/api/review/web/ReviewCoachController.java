package kr.co.knowledgerally.api.review.web;

import io.swagger.annotations.*;
import kr.co.knowledgerally.api.coach.component.ReviewMapper;
import kr.co.knowledgerally.api.coach.dto.ReviewDto;
import kr.co.knowledgerally.api.core.annotation.CurrentUser;
import kr.co.knowledgerally.api.core.dto.ApiPageResult;
import kr.co.knowledgerally.api.core.dto.ApiResult;
import kr.co.knowledgerally.api.review.service.ReviewCoachService;
import kr.co.knowledgerally.core.coach.entity.Review;
import kr.co.knowledgerally.core.coach.service.ReviewService;
import kr.co.knowledgerally.core.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * 코치 리뷰 관련 엔드포인트
 */
@Api(value = "코치 리뷰 관련 엔드포인트", tags = "코치 리뷰")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review/coach")
public class ReviewCoachController {
    private final ReviewCoachService reviewCoachService;

    @ApiOperation(value = "특정 코치에게 리뷰 쓰기", notes = "특정 코치에게 리뷰를 작성합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "작성 성공"),
    })
    @PostMapping("/{coachId}")
    public ResponseEntity<ApiResult<ReviewDto.ReadOnly>> postReview(@ApiIgnore @CurrentUser
                                                                        User loggedInUser,
                                                                    @ApiParam(value = "코치 id", required = true)
                                                                        @PathVariable
                                                                        Long coachId,
                                                                    @ApiParam(value = "작성할 리뷰", required = true)
                                                                        @RequestBody @Valid
                                                                        ReviewDto reviewDto) {

        return ResponseEntity.ok(ApiResult.ok(reviewCoachService.writeReview(coachId, reviewDto, loggedInUser)));
    }
}
