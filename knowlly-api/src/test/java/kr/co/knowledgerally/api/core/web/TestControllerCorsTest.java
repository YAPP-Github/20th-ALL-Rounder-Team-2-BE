package kr.co.knowledgerally.api.core.web;

import kr.co.knowledgerally.api.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TestControllerCorsTest extends AbstractControllerTest {
    private static final String API_TEST_URL = "/api/test";
    private static final String API_TEST_CORS_URL = "/test-cors";

    private static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
    private static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
    private static final String ORIGIN = "Origin";

    @Test
    void CORS_PREFLIGHT_LOCAL_테스트() throws Exception {
        mockMvc.perform(
                        options(API_TEST_URL)
                                .header(ACCESS_CONTROL_ALLOW_METHODS, "GET")
                                .header(ORIGIN, "http://localhost:3000")
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.header().string(ACCESS_CONTROL_ALLOW_ORIGIN,"http://localhost:3000"))
                .andDo(print());
    }

    @Test
    void CORS_PREFLIGHT_LOCAL_FAIL_테스트() throws Exception {
        mockMvc.perform(
                        options(API_TEST_CORS_URL)
                                .header(ACCESS_CONTROL_ALLOW_METHODS, "GET")
                                .header(ORIGIN, "http://localhost:3000")
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.header().doesNotExist(ACCESS_CONTROL_ALLOW_ORIGIN))
                .andDo(print());
    }
}