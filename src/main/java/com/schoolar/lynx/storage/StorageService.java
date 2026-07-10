package com.schoolar.lynx.storage;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    String upload(MultipartFile file, String folder);

    void delete(String key);

    byte[] download(String key);

    String getUrl(String key);
}
