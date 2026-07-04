package com.schoolar.lynx.service;

import com.schoolar.lynx.domain.dto.CompanyResponseDTO;
import com.schoolar.lynx.domain.dto.QuestionResponseDTO;
import com.schoolar.lynx.domain.dto.RegisterQuestionDTO;
import com.schoolar.lynx.domain.mapper.QuestionMapper;
import com.schoolar.lynx.domain.model.Company;
import com.schoolar.lynx.domain.model.Question;
import com.schoolar.lynx.domain.model.User;
import com.schoolar.lynx.repository.CompanyRepository;
import com.schoolar.lynx.repository.QuestionRepository;
import com.schoolar.lynx.security.AuthenticatedUserService;
import com.schoolar.lynx.utils.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final AuthenticatedUserService authUserService;
    private final CompanyRepository companyRepository;

    public QuestionResponseDTO create(RegisterQuestionDTO dto){
        User loggedUser = authUserService.get();
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Empresa não encontrada"
                ));
        if (!loggedUser.isAdmin()){
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Apenas o professor principal pode alterar esta empresa"
            );
        }

        if (dto.getCompanyId() == null){
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Nenhuma empresa vinculada"
            );
        }

        Question question = QuestionMapper.toEntity(dto, loggedUser, company);
        Question saved = questionRepository.save(question);
        return QuestionMapper.toResponseDTO(saved);
    }
}
