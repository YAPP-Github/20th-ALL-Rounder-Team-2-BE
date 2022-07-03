package kr.co.knowledgerally.api.core.web;

import kr.co.knowledgerally.api.core.dto.ApiResult;
import kr.co.knowledgerally.core.core.exception.KnowllyException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 테스트용 컨트롤러
 */
@ApiIgnore
@RestController
@RequestMapping("/healthz")
public class HealthController {
    @GetMapping("/liveness")
    public ResponseEntity<ApiResult<TestObject>> liveness() {
        return ResponseEntity.ok(ApiResult.ok(new TestObject("ok")));
    }

    @GetMapping("/readiness")
    public ResponseEntity<ApiResult<TestObject>> readiness() {
        return ResponseEntity.ok(ApiResult.ok(new TestObject("ok")));
    }

    @GetMapping("/startup")
    public ResponseEntity<ApiResult<TestObject>> startup() {
        return ResponseEntity.ok(ApiResult.ok(new TestObject("ok")));
    }

    @AllArgsConstructor
    @Getter
    @Setter
    public static class TestObject {
        private String result;
    }
}
