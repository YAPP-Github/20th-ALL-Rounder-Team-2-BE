package kr.co.knowledgerally.api.lecture.web;

import io.swagger.annotations.*;
import kr.co.knowledgerally.api.core.annotation.CurrentUser;
import kr.co.knowledgerally.api.core.dto.ApiResult;
import kr.co.knowledgerally.api.lecture.component.FormMapper;
import kr.co.knowledgerally.api.lecture.dto.FormDto;
import kr.co.knowledgerally.api.lecture.service.CoachFormService;
import kr.co.knowledgerally.core.core.exception.BadRequestException;
import kr.co.knowledgerally.core.lecture.entity.Form;
import kr.co.knowledgerally.core.lecture.service.FormService;
import kr.co.knowledgerally.core.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Api(value = "운영 클래스 신청서 관리 엔드포인트", tags = "운영 클래스 신청서 관리")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coach/form")
public class CoachFormController {
    private final CoachFormService coachFormService;

    @ApiOperation(value = "신청서 수락 / 거절", notes = "사용자의 신청서를 수락 혹은 거절합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "로그인한 사용자(코치)의 소유가 아닙니다."),
            @ApiResponse(code = 400, message = "로그인한 사용자가 코치가 아닙니다."),
            @ApiResponse(code = 400, message = "유효하지 않은 state 값입니다."),
    })
    @PatchMapping("/{formId}")
    public ResponseEntity<ApiResult<FormDto.ReadOnly>> getFormMe(@ApiIgnore @CurrentUser User loggedInUser,
                                                                       @ApiParam(value = "신청서 ID", required = true)
                                                                       @PathVariable Long formId,
                                                                       @ApiParam(value = "신청서 상태를 다음으로 업데이트\n" +
                                                                               "ACCEPT : 수락\n" +
                                                                               "REJECT : 거절", required = true)
                                                                       @RequestBody @Valid
                                                                       FormStateChangeDto changeDto) {

        return ResponseEntity.ok(ApiResult.ok(coachFormService.setFormState(formId, changeDto.getState(), loggedInUser)));
    }

    @ApiModel(value = "신청서 상태 변경 모델", description = "신청서 상태를 변경할 수 있는 모델")
    @NoArgsConstructor
    @Getter
    public static class FormStateChangeDto {
        @NotNull
        private Form.State state;
    }
}
