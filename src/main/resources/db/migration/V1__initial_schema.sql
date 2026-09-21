CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- =====================================================
-- USERS
-- =====================================================
CREATE SCHEMA seguranca;
CREATE SCHEMA organizacao;
CREATE SCHEMA academico;
CREATE SCHEMA configuracao;

CREATE TABLE seguranca.users (
    id UUID NOT NULL,
    name VARCHAR(150) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    birth DATE,
    profile_photo VARCHAR(255),
    is_teacher BOOLEAN NOT NULL,
    is_active BOOLEAN NOT NULL,

    CONSTRAINT pk_users PRIMARY KEY (id),
    CONSTRAINT uq_users_email UNIQUE (email)
);

-- =====================================================
-- COMPANY
-- =====================================================

CREATE TABLE organizacao.company (
    id UUID NOT NULL,
    public_name VARCHAR(150) NOT NULL,
    company_name VARCHAR(400) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(255),
    cnpj VARCHAR(255) NOT NULL,
    address VARCHAR(255),
    has_online BOOLEAN,
    is_active BOOLEAN,
    principal_teacher_id UUID,

    CONSTRAINT pk_company PRIMARY KEY (id),
    CONSTRAINT uq_company_email UNIQUE (email),
    CONSTRAINT uq_company_cnpj UNIQUE (cnpj),

    CONSTRAINT fk_company_principal_teacher
        FOREIGN KEY (principal_teacher_id)
        REFERENCES seguranca.users(id)
);

-- =====================================================
-- SOCIAL NETWORK
-- =====================================================

CREATE TABLE configuracao.social_network (
    id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    icon VARCHAR(255) NOT NULL,

    CONSTRAINT pk_social_network PRIMARY KEY (id),
    CONSTRAINT uq_social_network_name UNIQUE (name)
);

-- =====================================================
-- USER COMPANY
-- =====================================================

CREATE TABLE seguranca.user_company (
    id UUID NOT NULL,
    company_id UUID NOT NULL,
    user_id UUID NOT NULL,
    role VARCHAR(255) NOT NULL,
    added_at TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL,
    active BOOLEAN NOT NULL,

    CONSTRAINT pk_user_company PRIMARY KEY (id),
    CONSTRAINT uq_user_company_user_company
        UNIQUE (user_id, company_id),

    CONSTRAINT chk_user_company_role
        CHECK (
            role IN (
                'STUDENT',
                'TEACHER',
                'HEADTEACHER',
                'PRINCIPAL',
                'ADMIN'
            )
        ),

    CONSTRAINT fk_user_company_user
        FOREIGN KEY (user_id)
        REFERENCES seguranca.users(id),

    CONSTRAINT fk_user_company_company
        FOREIGN KEY (company_id)
        REFERENCES organizacao.company(id)
);

-- =====================================================
-- COURSE CLASS
-- =====================================================

CREATE TABLE academico.course_class (
    id UUID NOT NULL,
    name VARCHAR(200),
    level VARCHAR(255) NOT NULL,
    language VARCHAR(255) NOT NULL,
    max_students INTEGER NOT NULL,
    teacher_id UUID,
    company_id UUID,
    start_date TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL,
    end_date TIMESTAMP(6) WITHOUT TIME ZONE,
    created_at TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL,

    CONSTRAINT pk_course_class PRIMARY KEY (id),

    CONSTRAINT chk_course_class_level
        CHECK (
            level IN (
                'A1',
                'A2',
                'B1',
                'B2',
                'C1',
                'C2'
            )
        ),

    CONSTRAINT chk_course_class_language
        CHECK (
            language IN (
                'ENGLISH',
                'SPANISH',
                'FRENCH',
                'GERMAN',
                'ITALIAN',
                'PORTUGUESE'
            )
        ),

    CONSTRAINT fk_course_class_teacher
        FOREIGN KEY (teacher_id)
        REFERENCES seguranca.users(id),

    CONSTRAINT fk_course_class_company
        FOREIGN KEY (company_id)
        REFERENCES organizacao.company(id)
);

-- =====================================================
-- COURSE CLASS STUDENT
-- =====================================================

CREATE TABLE academico.course_class_student (
    id UUID NOT NULL,
    course_class_id UUID,
    student_id UUID,
    enrollment_date TIMESTAMP(6) WITHOUT TIME ZONE,

    CONSTRAINT pk_course_class_student PRIMARY KEY (id),

    CONSTRAINT fk_course_class_student_class
        FOREIGN KEY (course_class_id)
        REFERENCES academico.course_class(id),

    CONSTRAINT fk_course_class_student_student
        FOREIGN KEY (student_id)
        REFERENCES seguranca.users(id)
);
