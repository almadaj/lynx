package com.schoolar.lynx.service;

import com.schoolar.lynx.domain.dto.StudentsToCourseClassDTO;
import com.schoolar.lynx.domain.model.CourseClass;
import com.schoolar.lynx.domain.model.CourseClassStudent;
import com.schoolar.lynx.domain.model.User;
import com.schoolar.lynx.repository.CourseClassRepository;
import com.schoolar.lynx.repository.CourseClassStudentRepository;
import com.schoolar.lynx.repository.UserRepository;
import com.schoolar.lynx.security.AuthenticatedUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseClassStudentsService {
    private final CourseClassStudentRepository repository;
    private final CourseClassRepository courseClassRepository;
    private final UserRepository userRepository;
    private final AuthenticatedUserService authService;

    //TODO: terminar métodos
    @Transactional
    public void addStudent(UUID classId, StudentsToCourseClassDTO dto) {
        User loggedUser = authService.get();

        if (!loggedUser.isAdmin()){
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Somente professores podem fazer alterações"
            );
        }

        CourseClass courseClass = courseClassRepository.findById(classId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Turma não encontrada"
                ));

        if (!loggedUser.getId().equals(courseClass.getTeacher().getId()) &&
                !loggedUser.getId().equals(courseClass.getCompany().getPrincipalTeacher().getId())
        ){
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Somente professor da disciplina ou coordenador podem fazer alterações"
            );
        }

        User student = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Aluno não encontrado"
                ));

        if (student.isAdmin()){
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Professores não podem se matricular em disciplinas"
            );
        }

        if(courseClass.getStudents().size() >= courseClass.getMaxStudents()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Turma já atingiu o limite de alunos"
            );
        }

        if (repository.existsByCourseClassIdAndStudentId(classId, student.getId())){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Aluno já cadastrado nessa turma"
            );
        }


        CourseClassStudent enrollment = new CourseClassStudent();
        enrollment.setCourseClass(courseClass);
        enrollment.setStudent(student);
        enrollment.setEnrollmentDate(LocalDateTime.now());

        repository.save(enrollment);
    }
}
