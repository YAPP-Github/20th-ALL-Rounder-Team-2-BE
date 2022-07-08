package kr.co.knowledgerally.api.lecture.web;

import kr.co.knowledgerally.api.annotation.WithMockKnowllyUser;
import kr.co.knowledgerally.api.core.component.FileNameGenerator;
import kr.co.knowledgerally.api.core.component.FileUploader;
import kr.co.knowledgerally.api.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LectureInformationControllerTest extends AbstractControllerTest {
    private static final String LECTUREINFORMATION_URL = "/api/lectureinfo";
    private static final String LECTUREINFORMATION_SEARCH_URL = "/api/lectureinfo/search";
    private static final String LECTUREIMAGE_URL = "/api/lectureinfo/images";

    @MockBean
    private FileNameGenerator fileNameGenerator;

    @MockBean
    private FileUploader fileUploader;

    @WithMockKnowllyUser
    @Test
    public void 클래스_info_목록_조회_테스트() throws Exception {
        mockMvc.perform(
                        get(LECTUREINFORMATION_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].topic").value("요리 클래스"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].introduce").value("맛있는 요리 만들기"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].price").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].coach.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].category.categoryName").value("기타"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].topic").value("그래픽 디자인"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].introduce").value("그래픽을 그래그래"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].price").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].coach.id").value("3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].category.categoryName").value("디자인"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[2].topic").value("자바 개발"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[2].introduce").value("자바를 자바라"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[2].price").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[2].coach.id").value("2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[2].category.categoryName").value("개발"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[3].topic").value("마케팅 수업"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[3].introduce").value("효과적인 마케팅에 대해 배웁니다"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[3].price").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[3].coach.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[3].category.categoryName").value("마케팅"));
    }

    @WithMockKnowllyUser
    @Test
    public void 클래스_info_상세_조회_테스트() throws Exception {
        mockMvc.perform(
                        get(LECTUREINFORMATION_URL + "/3")
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.topic").value("그래픽 디자인"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.introduce").value("그래픽을 그래그래"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.price").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.coach.id").value("3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.category.categoryName").value("디자인"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.lectureImages.size()").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.tags.size()").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.lectures.size()").value("1"));
    }

    @WithMockKnowllyUser
    @Test
    public void 클래스_info_카테고리로_목록_조회_테스트() throws Exception {
        String categoryId = "categoryId";

        mockMvc.perform(
                        get(LECTUREINFORMATION_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .queryParam(categoryId, String.valueOf(6))
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].topic").value("요리 클래스"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].introduce").value("맛있는 요리 만들기"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].price").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].coach.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].category.categoryName").value("기타"));
    }

    @WithMockKnowllyUser
    @Test
    public void 클래스_info_keyword로_검색_테스트() throws Exception {
        String keyword = "keyword";

        //클래스 제목 기반 검색
        mockMvc.perform(
                get(LECTUREINFORMATION_SEARCH_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParam(keyword, "수업"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].topic").value("마케팅 수업"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].introduce").value("효과적인 마케팅에 대해 배웁니다"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].price").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].coach.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].category.categoryName").value("마케팅"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].lectureImages.size()").value(2));

        //카테고리 이름 기반 검색
        mockMvc.perform(
                        get(LECTUREINFORMATION_SEARCH_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .queryParam(keyword, "기타"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].topic").value("요리 클래스"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].introduce").value("맛있는 요리 만들기"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].price").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].coach.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].category.categoryName").value("기타"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].lectureImages[0].lectureImgUrl").value("http://lecture5.img.url"));

    }

    @WithMockKnowllyUser
    @Test
    public void 클래스_info_태그_등록_테스트() throws Exception {
        String categoryId = "categoryId";
        final String json =
                "{\"topic\": \"테스트 제목1\"," +
                "\"introduce\": \"테스트 소개1\"," +
                "\"tags\":" + "[" +
                "{\"content\": \"태그1\"}," +
                "{\"content\": \"태그2\"}" + "]," +
                        "\"lectureImages\":" + "[" +
                        "{\"id\": \"4\"}," +
                        "{\"id\": \"5\"}" +
                        "]" +
                        "}";

        mockMvc.perform(
                post(LECTUREINFORMATION_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParam(categoryId, "1")
                        .content(json)
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.topic").value("테스트 제목1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.introduce").value("테스트 소개1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.tags.size()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.lectureImages.size()").value(2));
    }

    @WithMockKnowllyUser
    @Test
    public void 클래스_이미지_등록_테스트() throws Exception {
        List<MockMultipartFile> mockMultipartFiles = new ArrayList<>();

        MockMultipartFile mockMultipartFile1 = new MockMultipartFile(
                "images",
                "hello1.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );

        MockMultipartFile mockMultipartFile2 = new MockMultipartFile(
                "images",
                "hello2.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );

        mockMultipartFiles.add(mockMultipartFile1);
        mockMultipartFiles.add(mockMultipartFile2);

        when(fileNameGenerator.generate(eq("hello1.txt"))).thenReturn("hello1.txt_generated");
        when(fileUploader.uploadMultiPartFile(any(), eq("lecture-image/hello1.txt_generated")))
                .thenReturn("http://testurl.com/lecture-image/hello1.txt_generated");
        when(fileNameGenerator.generate(eq("hello2.txt"))).thenReturn("hello2.txt_generated");
        when(fileUploader.uploadMultiPartFile(any(), eq("lecture-image/hello2.txt_generated")))
                .thenReturn("http://testurl.com/lecture-image/hello2.txt_generated");

        mockMvc.perform(
                        multipart(LECTUREIMAGE_URL)
                                .file(mockMultipartFiles.get(0))
                                .file(mockMultipartFiles.get(1))
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].lectureImgUrl")
                        .value("http://testurl.com/lecture-image/hello1.txt_generated"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].lectureImgUrl")
                        .value("http://testurl.com/lecture-image/hello2.txt_generated"))
                .andDo(print());
    }
}
