package kr.co.knowledgerally.api.lecture.web;

import io.swagger.annotations.*;
import kr.co.knowledgerally.api.core.annotation.CurrentUser;
import kr.co.knowledgerally.api.core.dto.ApiPageRequest;
import kr.co.knowledgerally.api.core.dto.ApiPageResult;
import kr.co.knowledgerally.api.core.dto.ApiResult;
import kr.co.knowledgerally.api.lecture.component.LectureInformationMapper;
import kr.co.knowledgerally.api.lecture.dto.LectureImageDto;
import kr.co.knowledgerally.api.lecture.dto.LectureInformationDto;
import kr.co.knowledgerally.api.lecture.dto.LectureInformationDtoReadOnly;
import kr.co.knowledgerally.api.lecture.service.LectureImageUploadService;
import kr.co.knowledgerally.core.lecture.service.CategoryService;
import kr.co.knowledgerally.core.lecture.service.LectureInformationSearchService;
import kr.co.knowledgerally.core.lecture.service.LectureInformationService;
import kr.co.knowledgerally.core.lecture.entity.Category;
import kr.co.knowledgerally.core.lecture.entity.LectureInformation;
import kr.co.knowledgerally.core.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

@Api(value = "클래스-info 관련 엔드포인트", tags = "클래스-info")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lectureinfo")
public class LectureInformationController {
    private final LectureInformationService lectureInformationService;
    private final LectureInformationSearchService lectureInformationSearchService;
    private final LectureImageUploadService lectureImageUploadService;
    private final CategoryService categoryService;
    private final LectureInformationMapper lectureInformationMapper;

    @ApiOperation(value = "클래스-info", notes = "클래스-info 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    @GetMapping("")
    public ResponseEntity<ApiPageResult<LectureInformationDtoReadOnly>> getAllLectureInformation(
            @ApiParam(value = "categoryName를 통해 조회하기")
            @RequestParam(name = "categoryName", required = false) Category.Name categoryName, ApiPageRequest pageRequest
    ) {
        Page<LectureInformation> result;

        if(categoryName == null) {
            result = lectureInformationService.findAllWithPageable(pageRequest.convert());
        }
        else {
            Category category = categoryService.findByName(categoryName).get();
            result = lectureInformationService.findAllByCategoryWithPageable(category, pageRequest.convert());
        }
        return ResponseEntity.ok(ApiPageResult.ok(
                result
                        .map(lectureInformationMapper::toDto)
        ));
    }

    @ApiOperation(value = "클래스-info", notes = "클래스-info 객체를 상세조회합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    @GetMapping("/{lectureInfoId}")
    public ResponseEntity<ApiResult<LectureInformationDtoReadOnly>> getLectureInformation(
            @ApiParam(value = "lectureInfoId를 통해 상세조회")
            @PathVariable Long lectureInfoId
    ) {
        return ResponseEntity.ok(ApiResult.ok(
                lectureInformationMapper.toDto(
                        lectureInformationService.findById(lectureInfoId)
                )
        ));
    }

    @ApiOperation(value = "클래스-info, 태그 등록", notes = "클래스-info와 태그들을 등록합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    @PostMapping("")
    public ResponseEntity<ApiResult<LectureInformationDtoReadOnly>> postLectureInformation(
            @ApiIgnore @CurrentUser
            User loggedInUser,
            @ApiParam(value = "입력한 클래스-info 정보", required = true)
            @RequestBody @Valid
            LectureInformationDto lectureInformationDto
    ) {
        return ResponseEntity.ok(ApiResult.ok(
                lectureInformationMapper.toDto(
                        lectureInformationService.saveLectureInformation(
                                lectureInformationMapper.toEntity(lectureInformationDto), loggedInUser
                        )
                )
        ));
    }

    @ApiOperation(value = "클래스 이미지 등록", notes = "여러개의 클래스 이미지들을 등록합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    @PostMapping("/images")
    public ResponseEntity<ApiResult<List<LectureImageDto>>> uploadImage(
            @ApiParam(value = "업로드할 클래스 이미지 리스트")
            @RequestParam(name = "images") List<MultipartFile> images
    ) {
        return ResponseEntity.ok(ApiResult.ok(lectureImageUploadService.uploadLectureImage(images)));
    }

    @ApiOperation(value = "클래스-info 검색", notes = "키워드로 클래스-info 목록을 검색합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    @GetMapping("/search")
    public ResponseEntity<ApiPageResult<LectureInformationDtoReadOnly>> searchAllLectureInformation (
            @ApiParam(value = "keyword를 통해 검색하기")
            @RequestParam(name = "keyword") String keyword, ApiPageRequest pageRequest
    ) {
        return ResponseEntity.ok(ApiPageResult.ok(
                lectureInformationSearchService.searchAllByKeyword(keyword, pageRequest.convert())
                        .map(lectureInformationMapper::toDto)
        ));
    }
}
