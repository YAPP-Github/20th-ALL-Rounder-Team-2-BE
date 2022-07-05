package kr.co.knowledgerally.api.lecture.web;

import io.swagger.annotations.*;
import kr.co.knowledgerally.api.core.annotation.CurrentUser;
import kr.co.knowledgerally.api.core.dto.ApiResult;
import kr.co.knowledgerally.api.lecture.dto.CoachLectureDto;
import kr.co.knowledgerally.api.lecture.dto.UserLectureDto;
import kr.co.knowledgerally.api.lecture.service.CoachLectureService;
import kr.co.knowledgerally.api.lecture.service.UserLectureService;
import kr.co.knowledgerally.core.lecture.entity.Lecture;
import kr.co.knowledgerally.core.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(value = "수강 클래스 관련 엔드포인트", tags = "수강 클래스")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/lecture")
public class UserLectureController {
    private final UserLectureService userLectureService;

    @ApiOperation(value = "수강 클래스 조회", notes = "로그인한 사용자의 수강 클래스를 조회합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    @GetMapping("/me")
    public ResponseEntity<ApiResult<List<UserLectureDto>>> getCoachLectureMe(@ApiIgnore @CurrentUser User loggedInUser,
                                                                             @ApiParam(value = "클래스 진행 상태, 없을 시 전체 조회\n" +
                                                                                      "ON_BOARD : 예정\n" +
                                                                                      "ON_GOING : 진행 중\n" +
                                                                                      "DONE : 완료", required = false)
                                                                              @RequestParam(value = "state", required = false)
                                                                              Lecture.State state) {
        return ResponseEntity.ok(ApiResult.ok(userLectureService.getUserLecture(loggedInUser, state)));
    }
}
