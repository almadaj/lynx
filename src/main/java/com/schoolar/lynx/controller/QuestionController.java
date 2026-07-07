package com.schoolar.lynx.controller;

import com.schoolar.lynx.domain.dto.QuestionResponseDTO;
import com.schoolar.lynx.domain.dto.RegisterQuestionDTO;
import com.schoolar.lynx.domain.dto.UpdateQuestionDTO;
import com.schoolar.lynx.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/question")
public class QuestionController {
    @Autowired
    private final QuestionService service;

    @PostMapping
    public ResponseEntity<QuestionResponseDTO> create(@Valid @RequestBody RegisterQuestionDTO question){
        return ResponseEntity.ok(service.create(question));
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuestionResponseDTO> update(@PathVariable UUID id, @RequestBody UpdateQuestionDTO question){
        return ResponseEntity.ok(service.update(id, question));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id){
        return ResponseEntity.ok(service.delete(id));
    }
}
