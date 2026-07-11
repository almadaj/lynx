package com.schoolar.lynx.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RailwayStorageService implements StorageService {
    private final S3Client s3Client;

    @Value("${storage.bucket}")
    private String bucket;

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

    }

    @Override
    public byte[] download(String key) {
        return new byte[0];
    }

    @Override
    public String getUrl(String key) {
        return "";
    }
}
