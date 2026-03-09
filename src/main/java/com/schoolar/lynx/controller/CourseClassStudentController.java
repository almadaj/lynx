package com.schoolar.lynx.controller;

import com.schoolar.lynx.domain.dto.CourseClassCreateDTO;
import com.schoolar.lynx.domain.dto.CourseClassResponseDTO;
import com.schoolar.lynx.domain.dto.StudentsToCourseClassDTO;
import com.schoolar.lynx.service.CourseClassService;
import com.schoolar.lynx.service.CourseClassStudentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
public class CourseClassStudentController {
    @Autowired
    CourseClassStudentsService service;

    @PostMapping("/{courseId}/student")
    public void addStudent (@PathVariable UUID courseId, @RequestBody StudentsToCourseClassDTO dto){
        service.addStudent(courseId, dto);
    }
}
