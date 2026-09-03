-- =====================================================
-- ASSESSMENT
-- =====================================================

CREATE TABLE assessment (
    id UUID NOT NULL,
    title VARCHAR(50) NOT NULL,
    max_score DOUBLE PRECISION,
    date DATE,
    limit_date DATE,
    course_class_id UUID NOT NULL,

    CONSTRAINT pk_assessment PRIMARY KEY (id),

    CONSTRAINT fk_assessment_course_class
        FOREIGN KEY (course_class_id)
        REFERENCES course_class(id)
);

-- =====================================================
-- GRADE
-- =====================================================

CREATE TABLE grade (
    id UUID NOT NULL,
    user_id UUID NOT NULL,
    assessment_id UUID NOT NULL,
    score DOUBLE PRECISION,
    feedback VARCHAR(255),

    CONSTRAINT pk_grade PRIMARY KEY (id),

    CONSTRAINT fk_grade_user
        FOREIGN KEY (user_id)
        REFERENCES users(id),

    CONSTRAINT fk_grade_assessment
        FOREIGN KEY (assessment_id)
        REFERENCES assessment(id)
);

-- =====================================================
-- QUESTION
-- =====================================================

CREATE TABLE question (
    id UUID NOT NULL,
    author_id UUID NOT NULL,
    company_id UUID NOT NULL,
    privacy INTEGER NOT NULL,
    difficulty VARCHAR(255) NOT NULL,
    question_type VARCHAR(255) NOT NULL,
    header TEXT,
    body TEXT,
    footer TEXT,
    expected_answer TEXT,
    language VARCHAR(255) NOT NULL,
    created_at TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL,
    deleted_at TIMESTAMP(6) WITHOUT TIME ZONE,

    CONSTRAINT pk_question PRIMARY KEY (id),

    CONSTRAINT chk_question_privacy
        CHECK (privacy >= 0 AND privacy <= 2),

    CONSTRAINT chk_question_difficulty
        CHECK (
            difficulty IN (
                'VERY_EASY',
                'EASY',
                'MEDIUM',
                'HARD',
                'VERY_HARD'
            )
        ),

    CONSTRAINT chk_question_type
        CHECK (
            question_type IN (
                'MULTIPLE_CHOICE',
                'ESSAY_QUESTION'
            )
        ),

    CONSTRAINT chk_question_language
        CHECK (
            language IN (
                'ENGLISH',
                'SPANISH',
                'FRENCH',
                'GERMAN',
                'ITALIAN',
                'PORTUGUESE',
                'DUTCH',
                'ARABIC'
            )
        ),

    CONSTRAINT fk_question_author
        FOREIGN KEY (author_id)
        REFERENCES users(id),

    CONSTRAINT fk_question_company
        FOREIGN KEY (company_id)
        REFERENCES company(id)
);

-- =====================================================
-- COMPANY SOCIAL NETWORK
-- =====================================================

CREATE TABLE company_social_network (
    id UUID NOT NULL,
    company_id UUID NOT NULL,
    social_network_id UUID NOT NULL,
    url VARCHAR(255) NOT NULL,

    CONSTRAINT pk_company_social_network PRIMARY KEY (id),

    CONSTRAINT fk_company_social_network_company
        FOREIGN KEY (company_id)
        REFERENCES company(id),

    CONSTRAINT fk_company_social_network_social_network
        FOREIGN KEY (social_network_id)
        REFERENCES social_network(id)
);

-- =====================================================
-- IMPORTED FILE
-- =====================================================

CREATE TABLE imported_file (
    id UUID NOT NULL,
    filename VARCHAR(255),
    status VARCHAR(255) NOT NULL,
    created_at TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL,
    finished_at TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL,

    CONSTRAINT pk_imported_file PRIMARY KEY (id),

    CONSTRAINT chk_imported_file_status
        CHECK (
            status IN (
                'PENDING',
                'PROCESSING',
                'SUCCESS',
                'ERROR'
            )
        )
);

-- =====================================================
-- REFRESH TOKEN
-- =====================================================

CREATE TABLE refresh_token (
    id UUID NOT NULL,
    user_id UUID NOT NULL,
    token_hash VARCHAR(255) NOT NULL,
    expires_at TIMESTAMP(6) WITH TIME ZONE NOT NULL,
    revoked_at TIMESTAMP(6) WITH TIME ZONE,
    created_at TIMESTAMP(6) WITH TIME ZONE NOT NULL,

    CONSTRAINT pk_refresh_token PRIMARY KEY (id),
    CONSTRAINT uq_refresh_token_hash UNIQUE (token_hash),

    CONSTRAINT fk_refresh_token_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
);

-- =====================================================
-- SEED - SOCIAL NETWORK
-- =====================================================

INSERT INTO social_network (id, name, icon)
VALUES
    ('4b5a47f5-a815-4a1f-81c0-f57f6e3ef103', 'Instagram', 'brand-instagram'),
    ('e8519c5f-8851-4106-a4fd-47d2d911e360', 'Facebook', 'brand-facebook'),
    ('7c17c701-ad2b-45ed-906f-9cc8b099ec0f', 'YouTube', 'brand-youtube'),
    ('7f4e0ac3-45d5-47cd-bd1e-aa7bba449d22', 'TikTok', 'brand-tiktok'),
    ('9634679c-d96e-40cc-b1f1-3f424ae18b6f', 'LinkedIn', 'brand-linkedin'),
    ('f7148c57-0216-46a3-be10-6a60695af496', 'WhatsApp', 'brand-whatsapp'),
    ('a1013acf-ad10-49eb-9e48-115f66feef78', 'Telegram', 'brand-telegram'),
    ('037fad81-44c1-4328-912c-02a119a6cde4', 'Pinterest', 'brand-pinterest'),
    ('8a9c0791-d936-41b8-a335-001558d46830', 'VK', 'brand-vk'),
    ('6595e0be-29a7-4c61-885d-d63277733108', 'X', 'brand-x')
ON CONFLICT (name) DO NOTHING;