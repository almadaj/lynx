package com.schoolar.lynx.domain.mapper;

import com.schoolar.lynx.domain.dto.*;
import com.schoolar.lynx.domain.model.Company;
import com.schoolar.lynx.domain.model.Question;
import com.schoolar.lynx.domain.model.QuestionAlternatives;
import com.schoolar.lynx.domain.model.User;

import java.util.List;

public class QuestionMapper {
    public static QuestionResponseDTO toResponseDTO(Question question) {
        QuestionResponseDTO dto = new QuestionResponseDTO();

        dto.setId(question.getId());
        dto.setAuthorId(question.getAuthor().getId());
        dto.setCompanyId(question.getCompany().getId());
        dto.setPrivacy(question.getPrivacy());
        dto.setDifficulty(question.getDifficulty());
        dto.setQuestionType(question.getQuestionType());
        dto.setHeader(question.getHeader());
        dto.setBody(question.getBody());
        dto.setFooter(question.getFooter());
        dto.setExpectedAnswer(question.getExpectedAnswer());
        dto.setLanguage(question.getLanguage());

        return dto;
    }

    public static QuestionResponseDTO toResponseDTO(
            Question question,
            List<AlternativesResponseDTO> alternatives
    ) {
        QuestionResponseDTO dto = toResponseDTO(question);
        dto.setAlternatives(alternatives);

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
        question.setHeader(dto.getHeader());
        question.setBody(dto.getBody());
        question.setFooter(dto.getFooter());
        question.setExpectedAnswer(dto.getExpectedAnswer());
        question.setDifficulty(dto.getDifficulty());
        question.setLanguage(dto.getLanguage());
        return question;
    }

    public static void updateEntity(Question question, UpdateQuestionDTO dto) {
        question.setPrivacy(dto.getPrivacy());
        question.setDifficulty(dto.getDifficulty());
        question.setQuestionType(dto.getQuestionType());
        question.setHeader(dto.getHeader());
        question.setBody(dto.getBody());
        question.setFooter(dto.getFooter());
        question.setExpectedAnswer(dto.getExpectedAnswer());
        question.setLanguage(dto.getLanguage());
    }

    public static AlternativesResponseDTO toAlternativesResponseDTO(
            QuestionAlternatives alternative
    ) {
        AlternativesResponseDTO dto = new AlternativesResponseDTO();

        dto.setId(alternative.getId());
        dto.setAlternativeText(alternative.getAlternativeText());
        dto.setAltOrder(alternative.getAltOrder());
        dto.setCorrect(alternative.isCorrect());

        return dto;
    }

    public static QuestionAlternatives toAlternativeEntity(
            RegisterAlternativeDTO dto,
            Question question
    ) {
        return QuestionAlternatives.builder()
                .question(question)
                .alternativeText(dto.getAlternativeText())
                .altOrder(dto.getAltOrder())
                .isCorrect(dto.isCorrect())
                .build();
    }
}
