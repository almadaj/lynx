package com.schoolar.lynx.service;

import com.schoolar.lynx.repository.CourseClassRepository;
import com.schoolar.lynx.repository.CourseClassStudentRepository;
import com.schoolar.lynx.security.AuthenticatedUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseClassService {
    private final CourseClassRepository courseRepository;
    private final CourseClassStudentRepository courseStudentRepository;
    private final AuthenticatedUserService authenticatedUserService;

    //TODO: implementar métodos
    //private CourseClassCreateDTO create

}
