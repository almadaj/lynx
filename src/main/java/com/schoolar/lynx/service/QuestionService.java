package com.schoolar.lynx.service;

import com.schoolar.lynx.domain.dto.AlternativesResponseDTO;
import com.schoolar.lynx.domain.dto.QuestionResponseDTO;
import com.schoolar.lynx.domain.dto.RegisterQuestionDTO;
import com.schoolar.lynx.domain.dto.UpdateQuestionDTO;
import com.schoolar.lynx.domain.enums.Difficulty;
import com.schoolar.lynx.domain.enums.Language;
import com.schoolar.lynx.domain.enums.QuestionType;
import com.schoolar.lynx.domain.enums.Role;
import com.schoolar.lynx.domain.mapper.QuestionAlternativesMapper;
import com.schoolar.lynx.domain.mapper.QuestionMapper;
import com.schoolar.lynx.domain.model.Company;
import com.schoolar.lynx.domain.model.Question;
import com.schoolar.lynx.domain.model.User;
import com.schoolar.lynx.repository.CompanyRepository;
import com.schoolar.lynx.repository.QuestionAlternativesRepository;
import com.schoolar.lynx.repository.QuestionRepository;
import com.schoolar.lynx.security.AuthenticatedUserService;
import com.schoolar.lynx.specification.QuestionSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.schoolar.lynx.domain.enums.QuestionType.MULTIPLE_CHOICE;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final QuestionAlternativesRepository alternativesRepository;
    private final AuthenticatedUserService authUserService;
    private final CompanyRepository companyRepository;
    private final CompanyAuthorizationService authorizationService;


    public QuestionResponseDTO create(RegisterQuestionDTO dto){
        authorizationService.require(Role.TEACHER, dto.getCompanyId());
        User loggedUser = authUserService.get();
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Empresa não encontrada"
                ));

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
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Questão não encontrada"
                ));

        authorizationService.require(Role.TEACHER, question.getCompany().getId());

        if (!loggedUser.getId().equals(question.getAuthor().getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Somente o autor pode alterar");
        }

        QuestionMapper.updateEntity(question, dto);
        Question saved = questionRepository.save(question);
        return QuestionMapper.toResponseDTO(saved);
    }

    public String delete (UUID id){
        User loggedUser = authUserService.get();

        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Questão não encontrada"
                ));
        authorizationService.require(Role.TEACHER, question.getCompany().getId());

        if (!loggedUser.getId().equals(question.getAuthor().getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Somente o autor pode excluir");
        }

        question.setUpdatedAt(LocalDateTime.now());
        question.setDeletedAt(LocalDateTime.now());
        questionRepository.save(question);
        return "Questão deletada com sucesso";
    }

    public Page<QuestionResponseDTO> findAll(
            String search,
            Difficulty difficulty,
            QuestionType questionType,
            Language language,
            Integer privacy,
            Pageable pageable
    ) {
        Specification<Question> specification = QuestionSpecification.filter(
                search,
                difficulty,
                questionType,
                language,
                privacy,
                null,
                null
        );

        //TODO: prioridadade, fazer os filtros de privacy, author e company

        return questionRepository.findAll(specification, pageable)
                .map(QuestionMapper::toResponseDTO);
    }

    public QuestionResponseDTO findById(UUID id){
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Questão não encontrada"
                ));
        return QuestionMapper.toResponseDTO(question);
    }

    public List<AlternativesResponseDTO> findAlternatives(UUID questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Questão não encontrada"
                ));

        if (question.getQuestionType() != QuestionType.MULTIPLE_CHOICE) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Essa questão não possui alternativas"
            );
        }

        return alternativesRepository
                .findAllByQuestion_IdOrderByAltOrderAsc(questionId)
                .stream()
                .map(QuestionAlternativesMapper::toResponseDTO)
                .toList();
    }
}
