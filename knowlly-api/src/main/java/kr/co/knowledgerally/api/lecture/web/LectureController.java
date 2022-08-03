package kr.co.knowledgerally.api.lecture.web;

import io.swagger.annotations.*;
import kr.co.knowledgerally.api.core.annotation.CurrentUser;
import kr.co.knowledgerally.api.core.dto.ApiPageRequest;
import kr.co.knowledgerally.api.core.dto.ApiPageResult;
import kr.co.knowledgerally.api.core.dto.ApiResult;
import kr.co.knowledgerally.api.lecture.component.LectureInformationMapper;
import kr.co.knowledgerally.api.lecture.component.LectureMapper;
import kr.co.knowledgerally.api.lecture.dto.LectureDto;
import kr.co.knowledgerally.api.lecture.dto.LectureInformationDto;
import kr.co.knowledgerally.api.lecture.dto.LectureRegisterDto;
import kr.co.knowledgerally.api.lecture.service.LectureDeleteService;
import kr.co.knowledgerally.api.lecture.service.LectureRegisterService;
import kr.co.knowledgerally.core.lecture.entity.Category;
import kr.co.knowledgerally.core.lecture.entity.LectureInformation;
import kr.co.knowledgerally.core.lecture.service.CategoryService;
import kr.co.knowledgerally.core.lecture.service.LectureInformationSearchService;
import kr.co.knowledgerally.core.lecture.service.LectureInformationService;
import kr.co.knowledgerally.core.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

@Api(value = "클래스 일정 관련 엔드포인트", tags = "클래스 일정")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lecture-schedule")
public class LectureController {
    private final LectureRegisterService lectureRegisterService;
    private final LectureDeleteService lectureDeleteService;
    private final LectureMapper lectureMapper;

    @ApiOperation(value = "클래스 일정 등록", notes = "클래스 일정을 등록합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    @PostMapping("/lectureinfo/{lectureInfoId}")
    public ResponseEntity<ApiResult<List<LectureDto.ReadOnly>>> registerLecture (
            @ApiParam(value = "클래스 정보 Id", required = true)
            @PathVariable Long lectureInfoId,
            @ApiParam(value = "등록할 클래스 일정 리스트", required = true)
            @RequestBody @Valid LectureRegisterDto lectureRegisterDto,
            @ApiIgnore @CurrentUser User loggedInUser) {

        return ResponseEntity.ok(ApiResult.ok(
                lectureRegisterService.register(lectureInfoId, lectureRegisterDto, loggedInUser)));
    }

    @ApiOperation(value = "클래스 일정 삭제", notes = "클래스 일정을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    @DeleteMapping("/{lectureId}")
    public ResponseEntity<ApiResult<LectureDto.ReadOnly>> deleteLecture (
            @ApiParam(value = "클래스 일정 Id", required = true)
            @PathVariable Long lectureId) {

        return ResponseEntity.ok(ApiResult.ok(
                lectureMapper.toDto(
                lectureDeleteService.delete(lectureId)))
        );
    }
}
