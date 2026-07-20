package com.schoolar.lynx.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/storage")
@RequiredArgsConstructor
public class StorageController {

    private final StorageService storageService;
//    Classe removida, foi utilizada somente para teste
//    e primeira implementação do upload ao bucket

//    @PostMapping("/upload")
//    public ResponseEntity<String> upload(
//            @RequestParam MultipartFile file
//    ) throws IOException {
//
//        String key = storageService.upload(file, "users");
//
//        return ResponseEntity.ok(key);
//    }
}
