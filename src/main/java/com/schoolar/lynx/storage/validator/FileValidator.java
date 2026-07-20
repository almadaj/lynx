package com.schoolar.lynx.storage.validator;

import org.springframework.web.multipart.MultipartFile;

public abstract class FileValidator {
    public void validate(MultipartFile file) {
        validateNotNull(file);
        validateNotEmpty(file);
    }

    protected void validateNotNull(MultipartFile file) {
        if (file == null) {
            throw new IllegalArgumentException("Nenhum arquivo foi enviado.");
        }
    }

    protected void validateNotEmpty(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("O arquivo está vazio.");
        }
    }
}