package kr.co.knowledgerally.api.lecture.web;

import io.swagger.annotations.*;
import kr.co.knowledgerally.api.core.annotation.CurrentUser;
import kr.co.knowledgerally.api.core.dto.ApiPageRequest;
import kr.co.knowledgerally.api.core.dto.ApiPageResult;
import kr.co.knowledgerally.api.core.dto.ApiResult;
import kr.co.knowledgerally.api.lecture.component.FormMapper;
import kr.co.knowledgerally.api.lecture.dto.FormDto;
import kr.co.knowledgerally.api.lecture.dto.LectureInformationDto;
import kr.co.knowledgerally.api.lecture.dto.LectureRegisterDto;
import kr.co.knowledgerally.api.lecture.service.FormRegisterService;
import kr.co.knowledgerally.core.lecture.entity.Form;
import kr.co.knowledgerally.core.lecture.service.FormService;
import kr.co.knowledgerally.core.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Api(value = "신청서 관련 엔드포인트", tags = "신청서")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/form")
public class FormController {
    private final FormMapper formMapper;
    private final FormService formService;
    private final FormRegisterService formRegisterService;

    @Deprecated
    @ApiOperation(value = "내 신청서 조회", notes = "로그인한 사용자의 신청서 목록을 조회합니다. 수강 클래스 조회에서 사용 가능합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    @GetMapping("/me")
    public ResponseEntity<ApiResult<List<FormDto.ReadOnly>>> getFormMe(@ApiIgnore @CurrentUser User loggedInUser,
                                                                       @ApiParam(value = "신청서 상태, 없을 시 전체 조회 \n" +
                                                                               "REQUEST : 요청된\n" +
                                                                               "ACCEPT : 수락된\n" +
                                                                               "REJECT : 거절된", required = false)
                                                                       @RequestParam(value = "state", required = false)
                                                                       Form.State state) {
        if (state != null) {
            return ResponseEntity.ok(ApiResult.ok(
                    formService.findAllByUserAndState(loggedInUser, state)
                            .stream().map(formMapper::toDto).collect(Collectors.toList())));
        }

        return ResponseEntity.ok(ApiResult.ok(
                formService.findAllByUser(loggedInUser)
                .stream().map(formMapper::toDto).collect(Collectors.toList())));
    }

    @ApiOperation(value = "신청서 조회", notes = "특정 신청서를 id로 조회합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    @GetMapping("/{formId}")
    public ResponseEntity<ApiResult<FormDto.ReadOnly>> getFormById(
            @ApiParam(value = "신청서 Id", required = true)
            @PathVariable Long formId) {
        return ResponseEntity.ok(ApiResult.ok(formMapper.toDto(formService.findById(formId))));
    }

    @ApiOperation(value = "신청서 작성", notes = "신청서를 작성합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    @PostMapping("/lecture/{lectureId}")
    public ResponseEntity<ApiResult<FormDto.ReadOnly>> postForm(
            @ApiIgnore @CurrentUser User loggedInUser,
            @ApiParam(value = "클래스 일정 Id", required = true)
            @PathVariable Long lectureId,
            @ApiParam(value = "신청서 모델", required = true)
            @RequestBody @Valid FormDto formDto) {
        return ResponseEntity.ok(ApiResult.ok(formRegisterService.register(lectureId, formDto, loggedInUser)));
    }
}
