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
import kr.co.knowledgerally.core.lecture.service.FormService;
import kr.co.knowledgerally.core.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Api(value = "신청서 관련 엔드포인트")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/form")
public class FormController {
    private final FormMapper formMapper;
    private final FormService formService;

    @ApiOperation(value = "내 신청서 조회", notes = "로그인한 사용자의 신청서 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    @GetMapping("/me")
    public ResponseEntity<ApiResult<List<FormDto.ReadOnly>>> getFormMe(@ApiIgnore @CurrentUser User loggedInUser) {
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
}
