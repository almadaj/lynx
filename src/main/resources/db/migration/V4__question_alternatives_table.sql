CREATE TABLE academico.question_alternative (
    id UUID PRIMARY KEY,
    question_id UUID NOT NULL,
    alternative_text TEXT,
    alt_order INTEGER NOT NULL,
    is_correct BOOLEAN NOT NULL DEFAULT FALSE,

    CONSTRAINT fk_question_alternative_question
        FOREIGN KEY (question_id)
        REFERENCES academico.question(id)
        ON DELETE CASCADE,

    CONSTRAINT chk_question_alt_order
        CHECK (alt_order >= 1)
);

CREATE INDEX idx_question_alternative_question_id
    ON academico.question_alternative(question_id);