package com.schoolar.lynx.controller;

import com.schoolar.lynx.domain.dto.StudentsToCourseClassDTO;
import com.schoolar.lynx.domain.dto.TransferStudentDTO;
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

    //TODO: futuramente fazer add, transfer e remove em massa
    @PostMapping("/{courseId}/student")
    public void addStudent (@PathVariable UUID courseId, @RequestBody StudentsToCourseClassDTO dto){
        service.addStudent(courseId, dto);
    }

    @DeleteMapping("/{courseId}/student/{studentId}")
    public void removeStudent (@PathVariable UUID courseId, @PathVariable UUID studentId){
        service.removeStudent(courseId, studentId);
    }

    @PutMapping("/{courseId}/student/{studentId}")
    public void transferStudent(@PathVariable UUID courseId, @PathVariable UUID studentId, @RequestBody TransferStudentDTO dto){
        service.transferStudent(courseId, studentId, dto.getNewClassId());
    }
}
