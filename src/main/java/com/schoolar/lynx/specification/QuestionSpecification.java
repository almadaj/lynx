package com.schoolar.lynx.specification;

import com.schoolar.lynx.domain.enums.Difficulty;
import com.schoolar.lynx.domain.enums.Language;
import com.schoolar.lynx.domain.enums.QuestionType;
import com.schoolar.lynx.domain.model.Question;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.criteria.Predicate;

public class QuestionSpecification {
    public static Specification<Question> filter(
            String search,
            Difficulty difficulty,
            QuestionType questionType,
            Language language,
            Integer privacy,
            UUID authorId,
            UUID companyId
    ) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            // Questões não excluídas
            predicates.add(cb.isNull(root.get("deletedAt")));

            // Busca textual
            if (search != null && !search.isBlank()) {
                String term = "%" + search.toLowerCase() + "%";

                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("header")), term),
                        cb.like(cb.lower(root.get("body")), term),
                        cb.like(cb.lower(root.get("footer")), term)
                ));
            }

            // Dificuldade
            if (difficulty != null) {
                predicates.add(
                        cb.equal(root.get("difficulty"), difficulty)
                );
            }

            // Tipo
            if (questionType != null) {
                predicates.add(
                        cb.equal(root.get("questionType"), questionType)
                );
            }

            // Idioma
            if (language != null) {
                predicates.add(
                        cb.equal(root.get("language"), language)
                );
            }

            // Privacidade selecionada
            if (privacy != null) {
                predicates.add(
                        cb.equal(root.get("privacy"), privacy)
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}