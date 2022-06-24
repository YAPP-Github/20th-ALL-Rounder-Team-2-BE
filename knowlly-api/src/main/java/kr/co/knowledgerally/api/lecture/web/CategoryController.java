package kr.co.knowledgerally.api.lecture.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import kr.co.knowledgerally.api.core.dto.ApiResult;
import kr.co.knowledgerally.api.lecture.component.CategoryMapper;
import kr.co.knowledgerally.api.lecture.dto.CategoryDto;
import kr.co.knowledgerally.core.lecture.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 클래스 카테고리 관련 엔드포인트
 */
@Api(value = "클래스 카테고리 관련 엔드포인트")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @ApiOperation(value = "클래스 카테고리", notes = "클래스의 카테고리 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    @GetMapping("")
    public ResponseEntity<ApiResult<List<CategoryDto>>> getAllCategory() {
        return ResponseEntity.ok(
                ApiResult.ok(
                        categoryService.findAll().stream()
                                .map(x-> categoryMapper.toDto(x))
                                .collect(Collectors.toList())
                )
        );
    }
}
