package com.schoolar.lynx.storage.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static com.schoolar.lynx.storage.StorageConstants.*;

@Component
public class ImageValidator extends FileValidator {

    @Override
    public void validate(MultipartFile file) {
        super.validate(file);

        validateExtension(file);
        validateContentType(file);
        validateSize(file);
        validateImage(file);
    }

    protected void validateExtension(MultipartFile file) {
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());

        if (extension == null ||
                !ALLOWED_EXTENSIONS_IMAGE.contains(extension.toLowerCase())) {

            throw new IllegalArgumentException(
                    "Formato de imagem inválido. Formatos permitidos: jpg, jpeg, png e webp."
            );
        }
    }

    protected void validateContentType(MultipartFile file) {
        String contentType = file.getContentType();

        if (contentType == null ||
                !ALLOWED_CONTENT_TYPES_IMAGE.contains(contentType.toLowerCase())) {

            throw new IllegalArgumentException(
                    "Tipo de arquivo inválido."
            );
        }
    }

    protected void validateImage(MultipartFile file) {
        try {
            BufferedImage image = ImageIO.read(file.getInputStream());

            if (image == null) {
                throw new IllegalArgumentException(
                        "O arquivo enviado não é uma imagem válida."
                );
            }

        } catch (IOException e) {
            throw new IllegalArgumentException(
                    "Não foi possível validar a imagem.",
                    e
            );
        }
    }

    protected void validateSize(MultipartFile file) {
        if (file.getSize() > MAX_FILE_SIZE_IMAGE) {
            throw new IllegalArgumentException(
                    "O arquivo deve possuir no máximo 5 MB."
            );
        }
    }
}
