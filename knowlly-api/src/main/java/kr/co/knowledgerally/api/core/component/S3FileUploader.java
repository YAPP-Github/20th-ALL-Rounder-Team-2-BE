package kr.co.knowledgerally.api.core.component;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.internal.Mimetypes;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import kr.co.knowledgerally.api.core.util.PathUtil;
import kr.co.knowledgerally.core.core.exception.KnowllyException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class S3FileUploader implements FileUploader {
    private final AmazonS3 s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.domain-uri}")
    private String domainUri;

    /**
     * 멀티파트 파일을 업로드합니다.
     * @param mFile 업로드 될 파일
     * @param saveFilePath 파일 경로
     * @return 다운로드 가능한 URL
     */
    @Override
    public String uploadMultiPartFile(MultipartFile mFile, String saveFilePath) {
        String fullFilePath = PathUtil.replaceWindowPathToLinuxPath(saveFilePath);
        S3ObjectUploadDto s3ObjectUploadDto = buildObjectUploadDto(mFile);

        s3Client.putObject(new PutObjectRequest(
                bucket, fullFilePath, s3ObjectUploadDto.getByteArrayInputStream(), s3ObjectUploadDto.getObjectMetadata()
        ).withCannedAcl(CannedAccessControlList.PublicRead));

        return domainUri + saveFilePath;
    }

    private S3ObjectUploadDto buildObjectUploadDto(MultipartFile file) {
        try {
            ObjectMetadata objMeta = new ObjectMetadata();
            objMeta.setContentType(Mimetypes.getInstance().getMimetype(file.getName()));
            byte[] bytes = IOUtils.toByteArray(file.getInputStream());
            objMeta.setContentLength(bytes.length);
            return new S3ObjectUploadDto(
                    new ByteArrayInputStream(bytes),
                    objMeta
            );
        } catch (IOException e) {
            throw new KnowllyException(e);
        }
    }

    @Getter
    @RequiredArgsConstructor
    private static class S3ObjectUploadDto {
        private final ByteArrayInputStream byteArrayInputStream;
        private final ObjectMetadata objectMetadata;
    }
}
