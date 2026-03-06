package com.schoolar.lynx.controller;

import com.schoolar.lynx.domain.dto.CourseClassCreateDTO;
import com.schoolar.lynx.domain.dto.CourseClassResponseDTO;
import com.schoolar.lynx.service.CourseClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
public class CourseClassController {
    @Autowired
    CourseClassService service;

    @PostMapping
    public CourseClassResponseDTO create (@RequestBody CourseClassCreateDTO dto){
        return service.create(dto);
    }

    @GetMapping
    public CourseClassResponseDTO findById(@PathVariable UUID id){
        return service.findById(id);
    }
}
