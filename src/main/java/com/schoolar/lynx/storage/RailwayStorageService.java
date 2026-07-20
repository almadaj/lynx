package com.schoolar.lynx.storage;

import com.schoolar.lynx.storage.validator.FileValidator;
import com.schoolar.lynx.storage.validator.ImageValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.io.IOException;
import java.time.Duration;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RailwayStorageService implements StorageService {
    private final S3Client s3Client;
    private final S3Presigner presigner;
    private final ImageValidator imgValidator;

    @Value("${storage.bucket}")
    private String bucket;

    @Value("${storage.endpoint}")
    private String endpoint;

    @Override
    public String upload(MultipartFile file, String folder) throws IOException {

        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        imgValidator.validate(file);
        String fileName = UUID.randomUUID() + "." + extension;

        String key = folder + "/" + fileName;

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(
                request,
                RequestBody.fromBytes(file.getBytes())
        );
        log.debug("Arquivo enviado para o bucket. key={}", key);
        return key;
    }

    @Override
    public void delete(String key) {
        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();

        log.debug("Arquivo removido do bucket. key={}", key);
        s3Client.deleteObject(request);
    }

    @Override
    public byte[] download(String key) {
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();

        ResponseBytes<GetObjectResponse> response =
                s3Client.getObjectAsBytes(request);
        return response.asByteArray();
    }

    @Override
    public String getUrl(String key) {
        if (key == null || key.isBlank()) {
            return null;
        }

        GetObjectRequest objectRequest = GetObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();

        GetObjectPresignRequest presignRequest =
                GetObjectPresignRequest.builder()
                        .signatureDuration(Duration.ofHours(3))
                        .getObjectRequest(objectRequest)
                        .build();

        return presigner
                .presignGetObject(presignRequest)
                .url()
                .toString();
    }
}
