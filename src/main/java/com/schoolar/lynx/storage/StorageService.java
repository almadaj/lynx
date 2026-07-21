package com.schoolar.lynx.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {
    String upload(MultipartFile file, String folder) throws IOException;

    void delete(String key);

    byte[] download(String key);

    String getUrl(String key);
}
