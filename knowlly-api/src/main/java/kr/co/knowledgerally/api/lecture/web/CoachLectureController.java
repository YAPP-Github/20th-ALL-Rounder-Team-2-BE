package kr.co.knowledgerally.api.lecture.web;

import io.swagger.annotations.*;
import kr.co.knowledgerally.api.core.annotation.CurrentUser;
import kr.co.knowledgerally.api.core.dto.ApiPageRequest;
import kr.co.knowledgerally.api.core.dto.ApiPageResult;
import kr.co.knowledgerally.api.core.dto.ApiResult;
import kr.co.knowledgerally.api.lecture.component.FormMapper;
import kr.co.knowledgerally.api.lecture.component.LectureInformationMapper;
import kr.co.knowledgerally.api.lecture.dto.CoachLectureDto;
import kr.co.knowledgerally.api.lecture.dto.FormDto;
import kr.co.knowledgerally.api.lecture.dto.LectureInformationDto;
import kr.co.knowledgerally.api.lecture.service.CoachLectureService;
import kr.co.knowledgerally.core.lecture.entity.Category;
import kr.co.knowledgerally.core.lecture.entity.Form;
import kr.co.knowledgerally.core.lecture.entity.Lecture;
import kr.co.knowledgerally.core.lecture.entity.LectureInformation;
import kr.co.knowledgerally.core.lecture.service.CategoryService;
import kr.co.knowledgerally.core.lecture.service.FormService;
import kr.co.knowledgerally.core.lecture.service.LectureInformationSearchService;
import kr.co.knowledgerally.core.lecture.service.LectureInformationService;
import kr.co.knowledgerally.core.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.stream.Collectors;

@Api(value = "운영 클래스 관련 엔드포인트", tags = "운영 클래스")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coach/lecture")
public class CoachLectureController {
    private final CoachLectureService coachLectureService;

    @ApiOperation(value = "운영 클래스 조회", notes = "로그인한 사용자의 운영 클래스를 조회합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    @GetMapping("/me")
    public ResponseEntity<ApiResult<List<CoachLectureDto>>> getCoachLectureMe(@ApiIgnore @CurrentUser User loggedInUser,
                                                                              @ApiParam(value = "클래스 진행 상태, 없을 시 전체 조회\n" +
                                                                                      "ON_BOARD : 예정\n" +
                                                                                      "ON_GOING : 진행 중\n" +
                                                                                      "DONE : 완료", required = false)
                                                                              @RequestParam(value = "state", required = false)
                                                                              Lecture.State state) {
        return ResponseEntity.ok(ApiResult.ok(coachLectureService.getCoachLecture(loggedInUser, state)));
    }
}
