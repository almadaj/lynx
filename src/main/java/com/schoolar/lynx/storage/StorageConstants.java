package com.schoolar.lynx.storage;

import java.util.Set;

public final class StorageConstants {
    private StorageConstants(){}

    public static final long MAX_FILE_SIZE_IMAGE = 5 * 1024 * 1024; // 5MB
    public static final long MAX_FILE_SIZE_PDF = 50 * 1024 * 1024; // 50MB
    public static final Set<String> ALLOWED_EXTENSIONS_IMAGE = Set.of(
            "jpg",
            "jpeg",
            "png",
            "webp"
    );

    public static final Set<String> ALLOWED_CONTENT_TYPES_IMAGE = Set.of(
            "image/jpeg",
            "image/png",
            "image/webp"
    );

}
