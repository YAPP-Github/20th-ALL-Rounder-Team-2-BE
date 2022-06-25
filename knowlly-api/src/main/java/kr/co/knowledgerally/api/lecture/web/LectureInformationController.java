package kr.co.knowledgerally.api.lecture.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import kr.co.knowledgerally.api.core.dto.ApiResult;
import kr.co.knowledgerally.api.lecture.component.LectureInformationMapper;
import kr.co.knowledgerally.api.lecture.dto.LectureInformationDto;
import kr.co.knowledgerally.core.lecture.service.LectureInformationService;
import kr.co.knowledgerally.core.lecture.entity.Category;
import kr.co.knowledgerally.core.lecture.entity.LectureInformation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Api(value = "클래스-info 관련 엔드포인트")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lecture-information")
public class LectureInformationController {
    private final LectureInformationService lectureInformationService;
    private final LectureInformationMapper lectureInformationMapper;

    @ApiOperation(value = "클래스-info", notes = "클래스-info 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    @GetMapping("")
    public ResponseEntity<ApiResult<List<LectureInformationDto>>> getAllLectureInformation (
            @RequestParam(required = false) Category category
    ) {
        if(category == null) {
            List<LectureInformation> result = lectureInformationService.findAll();
        }
        List<LectureInformation> result = lectureInformationService.findAllByCategory(category);
        return ResponseEntity.ok(ApiResult.ok(
                result
                        .stream()
                        .map(lectureInformationMapper::toDto)
                        .collect(Collectors.toList())
        ));
    }
}
