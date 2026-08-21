package com.schoolar.lynx.controller;

import com.schoolar.lynx.domain.dto.CourseClassCreateDTO;
import com.schoolar.lynx.domain.dto.CourseClassResponseDTO;
import com.schoolar.lynx.domain.dto.CourseClassUpdateDTO;
import com.schoolar.lynx.service.CourseClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @GetMapping("/{id}")
    public CourseClassResponseDTO findById(@PathVariable UUID id){
        return service.findById(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CourseClassResponseDTO> update(@PathVariable UUID id, @RequestBody CourseClassUpdateDTO dto){
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id){
        service.deleteById(id);
    }

    @GetMapping("/my")
    public List<CourseClassResponseDTO> getMyClassCourses(){
       return service.findMyCourseClasses();
    }

    @GetMapping("/company/{companyId}")
    public List<CourseClassResponseDTO> getCoursesByCompanyId(@PathVariable UUID companyId){
        return service.findCoursesByCompany(companyId);
    }
}
