package kr.co.knowledgerally.api.lecture.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import kr.co.knowledgerally.api.core.dto.ApiPageRequest;
import kr.co.knowledgerally.api.core.dto.ApiPageResult;
import kr.co.knowledgerally.api.lecture.component.LectureInformationMapper;
import kr.co.knowledgerally.api.lecture.dto.LectureInformationDto;
import kr.co.knowledgerally.core.lecture.service.CategoryService;
import kr.co.knowledgerally.core.lecture.service.LectureInformationSearchService;
import kr.co.knowledgerally.core.lecture.service.LectureInformationService;
import kr.co.knowledgerally.core.lecture.entity.Category;
import kr.co.knowledgerally.core.lecture.entity.LectureInformation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "클래스-info 관련 엔드포인트")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lectureinfo")
public class LectureInformationController {
    private final LectureInformationService lectureInformationService;
    private final LectureInformationSearchService lectureInformationSearchService;
    private final CategoryService categoryService;
    private final LectureInformationMapper lectureInformationMapper;

    @ApiOperation(value = "클래스-info", notes = "클래스-info 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    @GetMapping("")
    public ResponseEntity<ApiPageResult<LectureInformationDto.ReadOnly>> getAllLectureInformation (
            @RequestParam(name = "categoryId", required = false) Long categoryId, ApiPageRequest pageRequest
    ) {
        Page<LectureInformation> result;

        if(categoryId == null) {
            result = lectureInformationService.findAllWithPageable(pageRequest.convert());
        }
        else {
            Category category = categoryService.findById(categoryId).get();
            result = lectureInformationService.findAllByCategoryWithPageable(category, pageRequest.convert());
        }
        return ResponseEntity.ok(ApiPageResult.ok(
                result
                        .map(lectureInformationMapper::toDto)
        ));
    }

    @ApiOperation(value = "클래스-info 검색", notes = "키워드로 클래스-info 목록을 검색합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    @GetMapping("/search")
    public ResponseEntity<ApiPageResult<LectureInformationDto.ReadOnly>> searchAllLectureInformation (
            @RequestParam(name = "keyword") String keyword, ApiPageRequest pageRequest
    ) {
        return ResponseEntity.ok(ApiPageResult.ok(
                lectureInformationSearchService.searchAllByKeyword(keyword, pageRequest.convert())
                        .map(lectureInformationMapper::toDto)
        ));
    }
}
