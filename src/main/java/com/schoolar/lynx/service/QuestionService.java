package com.schoolar.lynx.service;

import com.schoolar.lynx.domain.dto.QuestionResponseDTO;
import com.schoolar.lynx.domain.dto.RegisterQuestionDTO;
import com.schoolar.lynx.domain.model.User;
import com.schoolar.lynx.repository.QuestionRepository;
import com.schoolar.lynx.security.AuthenticatedUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final AuthenticatedUserService authUserService;

    public QuestionResponseDTO createQuestion(RegisterQuestionDTO dto){
        User loggedUser = authUserService.get();
        QuestionResponseDTO questionResponseDTO = new QuestionResponseDTO();
        if (!loggedUser.isAdmin()){
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Apenas o professor principal pode alterar esta empresa"
            );
        }
        dto.setAuthorId(loggedUser.getId());
        //questionRepository.save();
        return questionResponseDTO;
        //TODO: finalizar função
    }
}
