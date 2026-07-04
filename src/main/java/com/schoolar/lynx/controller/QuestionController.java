package com.schoolar.lynx.controller;

import com.schoolar.lynx.domain.dto.QuestionResponseDTO;
import com.schoolar.lynx.domain.dto.RegisterQuestionDTO;
import com.schoolar.lynx.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/question")
public class QuestionController {
    @Autowired
    private final QuestionService service;

    @PostMapping
    public ResponseEntity<QuestionResponseDTO> createCompany(@Valid @RequestBody RegisterQuestionDTO question){
        return ResponseEntity.ok(service.create(question));
    }

}
