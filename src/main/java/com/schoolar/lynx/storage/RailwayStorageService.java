package com.schoolar.lynx.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RailwayStorageService implements StorageService {
    private final S3Client s3Client;
    private final S3Presigner s3Presigner; //TODO: ver a questão do retorno de getUrl

    @Value("${storage.bucket}")
    private String bucket;

    @Value("${storage.endpoint}")
    private String endpoint;

    @Override
    public String upload(MultipartFile file, String folder) throws IOException {

        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());

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

        return key;
    }

    @Override
    public void delete(String key) {
        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();

        s3Client.deleteObject(request);
    }

    @Override
    public byte[] download(String key) {
        return new byte[0];
    }

    @Override
    public String getUrl(String key) {
        if (key == null || key.isBlank()) {
            return null;
        }

        return endpoint + "/" + bucket + "/" + key;
    }
}
