package com.schoolar.lynx.service;

import com.schoolar.lynx.domain.dto.*;
import com.schoolar.lynx.domain.enums.Difficulty;
import com.schoolar.lynx.domain.enums.Language;
import com.schoolar.lynx.domain.enums.QuestionType;
import com.schoolar.lynx.domain.enums.Role;
import com.schoolar.lynx.domain.mapper.QuestionAlternativesMapper;
import com.schoolar.lynx.domain.mapper.QuestionMapper;
import com.schoolar.lynx.domain.model.Company;
import com.schoolar.lynx.domain.model.Question;
import com.schoolar.lynx.domain.model.QuestionAlternatives;
import com.schoolar.lynx.domain.model.User;
import com.schoolar.lynx.repository.CompanyRepository;
import com.schoolar.lynx.repository.QuestionAlternativesRepository;
import com.schoolar.lynx.repository.QuestionRepository;
import com.schoolar.lynx.repository.UserCompanyRepository;
import com.schoolar.lynx.security.AuthenticatedUserService;
import com.schoolar.lynx.specification.QuestionSpecification;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    private final UserCompanyRepository userCompanyRepository;


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

        if(
                dto.getLanguage() == null ||
                dto.getBody() == null ||
                dto.getQuestionType() == null ||
                dto.getDifficulty() == null
        ){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Há campos obrigatórios nulos"
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

    @Transactional
    public String delete(UUID id) {
        User loggedUser = authUserService.get();

        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Questão não encontrada"
                ));

        authorizationService.require(Role.TEACHER, question.getCompany().getId());

        if (!loggedUser.getId().equals(question.getAuthor().getId())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Somente o autor pode excluir"
            );
        }

        LocalDateTime now = LocalDateTime.now();

        question.setUpdatedAt(now);
        question.setDeletedAt(now);

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
        User loggedUser = authUserService.get();

        List<UUID> companyIds = userCompanyRepository
                .findAllByUserIdAndActiveTrue(loggedUser.getId())
                .stream()
                .filter(uc -> uc.getRole().hasPermission(Role.TEACHER))
                .map(uc -> uc.getCompany().getId())
                .toList();

        Specification<Question> filters = QuestionSpecification.filter(
                search,
                difficulty,
                questionType,
                language,
                privacy,
                null,
                null
        );

        Specification<Question> visibility = visibleTo(
                loggedUser.getId(),
                companyIds
        );

        return questionRepository.findAll(
                visibility.and(filters),
                pageable
        ).map(QuestionMapper::toResponseDTO);
    }

    public QuestionResponseDTO findById(UUID id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Questão não encontrada"
                ));

        User loggedUser = authUserService.get();

        if (!canAccess(question, loggedUser)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Não é possível visualizar questão"
            );
        }

        return QuestionMapper.toResponseDTO(question);
    }

    //QuestionAlternativas
    public List<AlternativesResponseDTO> findAlternatives(UUID questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Questão não encontrada"
                ));

        User loggedUser = authUserService.get();

        if (!canAccess(question, loggedUser)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Questão não encontrada"
            );
        }

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

    @Transactional
    public void addAlternativesToQuestion(UUID questionId, List<RegisterAlternativeDTO> dto){
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Questão não encontrada"
                ));

        User loggedUser = authUserService.get();

        if (!canAccess(question, loggedUser)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Questão não encontrada"
            );
        }

        if (question.getQuestionType() != QuestionType.MULTIPLE_CHOICE) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Essa questão não possui alternativas"
            );
        }

        if (dto == null || dto.size() < 2) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "A questão deve possuir pelo menos duas alternativas"
            );
        }

        long correctCount = dto.stream()
                .filter(RegisterAlternativeDTO::isCorrect)
                .count();

        if (correctCount != 1) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "A questão deve possuir pelo menos uma alternativa correta"
            );
        }

        if (dto.stream().anyMatch(item ->
                item.getAlternativeText() == null ||
                        item.getAlternativeText().isBlank()
        )) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Todas as alternativas devem possuir um texto"
            );
        }

        Set<Integer> orders = new HashSet<>();

        for (RegisterAlternativeDTO item : dto) {
            if (item.getAltOrder() < 1 || !orders.add(item.getAltOrder())) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "A ordem das alternativas é inválida ou duplicada"
                );
            }
        }

        List<QuestionAlternatives> alternatives = dto.stream()
                .map(item -> QuestionAlternatives.builder()
                        .question(question)
                        .alternativeText(item.getAlternativeText())
                        .altOrder(item.getAltOrder())
                        .isCorrect(item.isCorrect())
                        .build())
                .toList();

        alternativesRepository.saveAll(alternatives);
    }

    private boolean canAccess(Question question, User loggedUser) {
        return switch (question.getPrivacy()) {
            case 0 -> true;

            case 1 -> authorizationService
                    .getUserRole(
                            loggedUser.getId(),
                            question.getCompany().getId()
                    )
                    .hasPermission(Role.TEACHER);

            case 2 -> question.getAuthor().getId().equals(loggedUser.getId());

            default -> false;
        };
    }

    public static Specification<Question> visibleTo(
            UUID userId,
            List<UUID> companyIds
    ) {
        return (root, query, cb) -> {
            Predicate publicQuestions = cb.equal(root.get("privacy"), 0);

            Predicate privateQuestions = cb.and(
                    cb.equal(root.get("privacy"), 2),
                    cb.equal(root.get("author").get("id"), userId)
            );

            Predicate institutionalQuestions = companyIds.isEmpty()
                    ? cb.disjunction()
                    : cb.and(
                    cb.equal(root.get("privacy"), 1),
                    root.get("company").get("id").in(companyIds)
            );

            return cb.or(
                    publicQuestions,
                    privateQuestions,
                    institutionalQuestions
            );
        };
    }
}
