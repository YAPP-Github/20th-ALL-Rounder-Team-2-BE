package kr.co.knowledgerally.api.core.component;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class S3FileUploaderTest {
    @InjectMocks
    private S3FileUploader s3FileUploader;

    @Mock
    private AmazonS3 amazonS3;

    private static final String TEST_BUCKET = "testbucket";
    private static final String TEST_DOMAIN_URI = "http://testurl.com/";
    private static final String TEST_FILE_PATH = "hello.txt";
    private static final MultipartFile TEST_MULTIPART_FILE = new MockMultipartFile(
            "image",
            "hello.txt",
            MediaType.TEXT_PLAIN_VALUE,
            "Hello, World!".getBytes()
    );

    @Test
    void 파일_업로더_테스트() {
        ReflectionTestUtils.setField(s3FileUploader, "bucket", TEST_BUCKET);
        ReflectionTestUtils.setField(s3FileUploader, "domainUri", TEST_DOMAIN_URI);
        ArgumentCaptor<PutObjectRequest> argumentCaptor = ArgumentCaptor.forClass(PutObjectRequest.class);
        when(amazonS3.putObject(argumentCaptor.capture())).thenReturn(new PutObjectResult());

        String downloadableUrl = s3FileUploader.uploadMultiPartFile(TEST_MULTIPART_FILE, TEST_FILE_PATH);

        assertEquals(TEST_DOMAIN_URI + TEST_FILE_PATH, downloadableUrl);
        PutObjectRequest captured = argumentCaptor.getValue();
        assertEquals(TEST_BUCKET, captured.getBucketName());
        assertEquals(TEST_FILE_PATH, captured.getKey());
    }

}