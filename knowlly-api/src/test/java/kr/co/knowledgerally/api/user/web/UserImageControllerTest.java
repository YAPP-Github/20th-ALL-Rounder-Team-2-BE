package kr.co.knowledgerally.api.user.web;

import kr.co.knowledgerally.api.annotation.WithMockKnowllyUser;
import kr.co.knowledgerally.api.core.component.FileNameGenerator;
import kr.co.knowledgerally.api.core.component.FileUploader;
import kr.co.knowledgerally.api.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserImageControllerTest extends AbstractControllerTest {
    private static final String USER_IMAGE_URL = "/api/user/image";

    @MockBean
    private FileNameGenerator fileNameGenerator;

    @MockBean
    private FileUploader fileUploader;

    @Test
    @WithMockKnowllyUser
    public void 이미지_업로드_테스트() throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "image",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );

        when(fileNameGenerator.generate(eq("hello.txt"))).thenReturn("hello.txt_generated");
        when(fileUploader.uploadMultiPartFile(any(), eq("user-image/1/hello.txt_generated")))
                .thenReturn("http://testurl.com/user-image/1/hello.txt_generated");

        mockMvc.perform(
                        multipart(USER_IMAGE_URL)
                                .file(mockMultipartFile)
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.userImgUrl")
                        .value("http://testurl.com/user-image/1/hello.txt_generated"))
                .andDo(print());
    }

    @Test
    @WithMockKnowllyUser
    public void 이미지_삭제_테스트() throws Exception{
        mockMvc.perform(
                delete(USER_IMAGE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.userImgUrl")
                        .value("http://test1.img.url"))
                .andDo(print());
    }
}