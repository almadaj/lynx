package com.schoolar.lynx.service;

import com.schoolar.lynx.domain.dto.CourseClassCreateDTO;
import com.schoolar.lynx.domain.dto.CourseClassResponseDTO;
import com.schoolar.lynx.domain.dto.CourseClassUpdateDTO;
import com.schoolar.lynx.domain.model.Company;
import com.schoolar.lynx.domain.model.CourseClass;
import com.schoolar.lynx.domain.model.User;
import com.schoolar.lynx.repository.CompanyRepository;
import com.schoolar.lynx.repository.CourseClassRepository;
import com.schoolar.lynx.repository.CourseClassStudentRepository;
import com.schoolar.lynx.repository.UserRepository;
import com.schoolar.lynx.security.AuthenticatedUserService;
import com.schoolar.lynx.utils.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseClassService {
    private final CourseClassRepository courseRepository;
    private final CourseClassStudentRepository courseStudentRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final AuthenticatedUserService authenticatedUserService;

    public CourseClassResponseDTO create (CourseClassCreateDTO dto){
        CourseClass finalDto = new CourseClass();
        User loggedUser = authenticatedUserService.get();

        Company company = companyRepository.findById(dto.getCompanyId())
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Empresa não encontrada"
            ));

        User teacher = userRepository.findById(dto.getTeacherId())
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Professor não encontrado"
            ));

        if (!teacher.isAdmin()){
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Estudantes não podem gerenciar turmas"
            );
        }

        if (!loggedUser.isAdmin()) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Estudantes não podem criar turmas"
            );
        }

        if (!company.getPrincipalTeacher().getId().equals(loggedUser.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Apenas o diretor pode alterar esta turma"
            );
        }

        finalDto.setName(dto.getName());
        finalDto.setMaxStudents(dto.getMaxStudents());
        finalDto.setLanguage(dto.getLanguage());
        finalDto.setLevel(dto.getLevel());
        finalDto.setCompany(company);
        finalDto.setTeacher(teacher);
        finalDto.setStartDate(dto.getStartDate());
        finalDto.setStartDate(dto.getStartDate());

        courseRepository.save(finalDto);
        return MapperUtil.parseObject(finalDto, CourseClassResponseDTO.class);
    }


    public CourseClassResponseDTO findById(UUID id){
        User loggedUser = authenticatedUserService.get();

        if (loggedUser == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED
            );
        }

        CourseClass course = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Turma não encontrada"
                ));
        return MapperUtil.parseObject(course, CourseClassResponseDTO.class);
    }

    @Transactional
    public CourseClassResponseDTO update(UUID id, CourseClassUpdateDTO dto){

        User loggedUser = authenticatedUserService.get();

        if (!loggedUser.isAdmin()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        CourseClass course = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Turma não encontrada"
                ));

        Company company = course.getCompany();

        if (!loggedUser.getId().equals(company.getPrincipalTeacher().getId())){
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Somente diretor pode fazer alterações"
            );
        }

        if (dto.getTeacherId() != null) {
            User teacher = userRepository.findById(dto.getTeacherId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Professor não encontrado"
                    ));

            if (!teacher.isAdmin()){
                throw new ResponseStatusException(
                        HttpStatus.FORBIDDEN,
                        "Estudantes não podem gerenciar turmas"
                );
            }

            course.setTeacher(teacher);
        }

        if (dto.getLanguage() != null) {
            course.setLanguage(dto.getLanguage());
        }

        if (dto.getLevel() != null) {
            course.setLevel(dto.getLevel());
        }

        if (dto.getName() != null) {
            course.setName(dto.getName());
        }

        if (dto.getMaxStudents() != null) {
            course.setMaxStudents(dto.getMaxStudents());
        }

        if (dto.getStartDate() != null) {
            course.setStartDate(dto.getStartDate());
        }

        if (dto.getEndDate() != null) {
            course.setEndDate(dto.getEndDate());
        }

        return MapperUtil.parseObject(course, CourseClassResponseDTO.class);
    }
}
