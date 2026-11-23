package com.schoolar.lynx.service;

import com.schoolar.lynx.domain.dto.ClassifyQuestionRequestDTO;
import com.schoolar.lynx.domain.dto.ClassifyQuestionResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class QuestionClassificationService {

    private final RestClient restClient;

    @Value("${integration.classifier-ai}")
    private String classificationUrl;

    public ClassifyQuestionResponseDTO classify(ClassifyQuestionRequestDTO request) {
        return restClient.post()
                .uri(classificationUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(ClassifyQuestionResponseDTO.class);
    }
}