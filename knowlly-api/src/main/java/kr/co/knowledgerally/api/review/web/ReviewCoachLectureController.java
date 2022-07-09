package kr.co.knowledgerally.api.review.web;

import io.swagger.annotations.*;
import kr.co.knowledgerally.api.review.dto.ReviewDto;
import kr.co.knowledgerally.api.core.annotation.CurrentUser;
import kr.co.knowledgerally.api.core.dto.ApiResult;
import kr.co.knowledgerally.api.review.service.ReviewCoachService;
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
public class ReviewCoachLectureController {
    private final ReviewCoachService reviewCoachService;

    @ApiOperation(value = "완료된 클래스로부터 코치에게 리뷰 쓰기", notes = "완료된 클래스로부터 코치에게 리뷰를 작성합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "작성 성공"),
    })
    @PostMapping("/lecture/{lectureId}")
    public ResponseEntity<ApiResult<ReviewDto.ReadOnly>> postReview(@ApiIgnore @CurrentUser
                                                                        User loggedInUser,
                                                                    @ApiParam(value = "클래스 일정 id", required = true)
                                                                        @PathVariable
                                                                        Long lectureId,
                                                                    @ApiParam(value = "작성할 리뷰", required = true)
                                                                        @RequestBody @Valid
                                                                        ReviewDto reviewDto) {

        return ResponseEntity.ok(ApiResult.ok(reviewCoachService.writeReview(lectureId, reviewDto, loggedInUser)));
    }
}
