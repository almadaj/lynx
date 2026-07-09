package com.schoolar.lynx.controller;

import com.schoolar.lynx.domain.dto.QuestionResponseDTO;
import com.schoolar.lynx.domain.dto.RegisterQuestionDTO;
import com.schoolar.lynx.domain.dto.UpdateQuestionDTO;
import com.schoolar.lynx.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @GetMapping("/{id}")
    public ResponseEntity<QuestionResponseDTO> findById(@PathVariable UUID id){
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<QuestionResponseDTO>> findAll(
            String search,
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            )
            Pageable pageable
    ) {
        return ResponseEntity.ok(service.findAll(search, pageable));
        //TODO: inserir o search para buscar por header e body
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
