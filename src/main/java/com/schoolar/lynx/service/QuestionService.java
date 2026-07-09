package com.schoolar.lynx.service;

import com.schoolar.lynx.domain.dto.QuestionResponseDTO;
import com.schoolar.lynx.domain.dto.RegisterQuestionDTO;
import com.schoolar.lynx.domain.dto.UpdateQuestionDTO;
import com.schoolar.lynx.domain.mapper.QuestionMapper;
import com.schoolar.lynx.domain.model.Company;
import com.schoolar.lynx.domain.model.Question;
import com.schoolar.lynx.domain.model.User;
import com.schoolar.lynx.repository.CompanyRepository;
import com.schoolar.lynx.repository.QuestionRepository;
import com.schoolar.lynx.security.AuthenticatedUserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;

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
                    "Apenas o professor pode alterar questão"
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

    public QuestionResponseDTO update(UUID id, UpdateQuestionDTO dto){
        User loggedUser = authUserService.get();

        if (!loggedUser.isAdmin()) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Apenas o professor pode alterar questão"
            );
        }

        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Questão não encontrada"
                ));

        QuestionMapper.updateEntity(question, dto);
        Question saved = questionRepository.save(question);
        return QuestionMapper.toResponseDTO(saved);
    }

    public String delete (UUID id){
        User loggedUser = authUserService.get();
        if (!loggedUser.isAdmin()){
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Apenas o professor principal pode alterar esta empresa"
            );
        }

        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Questão não encontrada"
                ));
        question.setUpdatedAt(LocalDateTime.now());
        question.setDeletedAt(LocalDateTime.now());
        questionRepository.save(question);
        return "Questão deletada com sucesso";
    }

    public Page<QuestionResponseDTO> findAll(String search, Pageable pageable) {
        return questionRepository.findAllActive(search, pageable)
                .map(QuestionMapper::toResponseDTO);
    }

    public QuestionResponseDTO findById(UUID id){
        User loggedUser = authUserService.get();
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Questão não encontrada"
                ));
        return QuestionMapper.toResponseDTO(question);
    }
}
