package com.schoolar.lynx.service;

import com.schoolar.lynx.domain.dto.AssessmentRequestDTO;
import com.schoolar.lynx.domain.model.Assessment;
import com.schoolar.lynx.domain.model.CourseClass;
import com.schoolar.lynx.domain.model.User;
import com.schoolar.lynx.repository.AssessmentRepository;
import com.schoolar.lynx.repository.CompanyRepository;
import com.schoolar.lynx.repository.CourseClassRepository;
import com.schoolar.lynx.repository.UserRepository;
import com.schoolar.lynx.security.AuthenticatedUserService;
import com.schoolar.lynx.utils.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AssessmentService {
    private final AuthenticatedUserService authUserService;
    private final CourseClassRepository courseRepository;
    private final AssessmentRepository assessmentRepository;

    public Assessment create (AssessmentRequestDTO dto){
        User loggedUser = authUserService.get();
        CourseClass course = courseRepository.findById(dto.getCourseClassId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Turma não encontrada"
                ));

        if (!loggedUser.isAdmin() ||
                !loggedUser.getId().equals(course.getTeacher().getId())
        ){
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Somente o professor da disciplina pode criar avaliações"
            );
        }

        if (dto.getLimitDate() != null &&
                dto.getLimitDate().isBefore(LocalDate.now())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Data Limite de entrega não pode estar no passado"
            );
        }

        Assessment assessment = MapperUtil.parseObject(dto, Assessment.class);
        assessment.setCourseClass(course);
        return assessmentRepository.save(assessment);
    }
}
