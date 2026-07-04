package com.schoolar.lynx.domain.mapper;

import com.schoolar.lynx.domain.dto.QuestionResponseDTO;
import com.schoolar.lynx.domain.dto.RegisterQuestionDTO;
import com.schoolar.lynx.domain.model.Company;
import com.schoolar.lynx.domain.model.Question;
import com.schoolar.lynx.domain.model.User;

public class QuestionMapper {
    public static QuestionResponseDTO toResponseDTO(Question question){
        if (question == null) {
            return null;
        }

        QuestionResponseDTO dto = new QuestionResponseDTO();
        dto.setId(question.getId());
        dto.setAuthorId(question.getAuthor().getId());
        dto.setCompanyId(question.getCompany().getId());
        dto.setPrivacy(question.getPrivacy());
        dto.setQuestionType(question.getQuestionType());
        dto.setHeader(question.getHeader());
        dto.setBody(question.getBody());
        dto.setFooter(question.getFooter());
        dto.setExpectedAnswer(question.getExpectedAnswer());
        dto.setLanguage(question.getLanguage());
        dto.setDifficulty(question.getDifficulty());
        return dto;
    }

    public static Question toEntity(RegisterQuestionDTO dto, User user, Company company){
        if (dto == null) {
            return null;
        }

        Question question = new Question();
        question.setQuestionType(dto.getQuestionType());
        question.setAuthor(user);
        question.setPrivacy(dto.getPrivacy());
        question.setCompany(company);
        question.setHeader(dto.getHead());
        question.setBody(dto.getBody());
        question.setFooter(dto.getFoot());
        question.setExpectedAnswer(dto.getExpectAnswer());
        question.setDifficulty(dto.getDifficulty());
        question.setLanguage(dto.getLanguage());
        return question;
    }
}
