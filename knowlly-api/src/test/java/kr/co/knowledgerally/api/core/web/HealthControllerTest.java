package kr.co.knowledgerally.api.core.web;

import kr.co.knowledgerally.api.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class HealthControllerTest extends AbstractControllerTest {
    private static final String HEALTHZ_LIVENESS_TEST = "/healthz/liveness";
    private static final String HEALTHZ_READINESS_TEST = "/healthz/readiness";
    private static final String HEALTHZ_STARTUP_TEST = "/healthz/startup";

    @Test
    void HEALTHZ_liveness_TEST() throws Exception {
        mockMvc.perform(
                        get(HEALTHZ_LIVENESS_TEST)
                ).andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void HEALTHZ_readiness_TEST() throws Exception {
        mockMvc.perform(
                        get(HEALTHZ_READINESS_TEST)
                ).andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void HEALTHZ_startup_TEST() throws Exception {
        mockMvc.perform(
                        get(HEALTHZ_STARTUP_TEST)
                ).andExpect(status().isOk())
                .andDo(print());
    }
}