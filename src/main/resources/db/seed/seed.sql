-- ============================================================
-- USERS
-- ============================================================
--
-- BCrypt de "123456":
-- $2y$10$gYHvRPiYkwe7xWhDfvVRi.4gPOmjC8vmRc1ZARQw5S9SZsg/mcrS.
--
-- Spring Security BCryptPasswordEncoder consegue validar
-- hashes BCrypt desse formato.
-- ============================================================

INSERT INTO seguranca.users (
    id,
    name,
    email,
    password,
    birth,
    profile_photo,
    is_teacher,
    is_active
)
VALUES
(
    '22222222-2222-2222-2222-222222222222',
    'Admin',
    'admin@mail.com',
    '$2y$10$gYHvRPiYkwe7xWhDfvVRi.4gPOmjC8vmRc1ZARQw5S9SZsg/mcrS.',
    NULL,
    NULL,
    FALSE,
    TRUE
),
(
    '33333333-3333-3333-3333-333333333333',
    'Student',
    'student@mail.com',
    '$2y$10$gYHvRPiYkwe7xWhDfvVRi.4gPOmjC8vmRc1ZARQw5S9SZsg/mcrS.',
    NULL,
    NULL,
    FALSE,
    TRUE
)
ON CONFLICT (email) DO NOTHING;

-- ============================================================
-- COMPANY
-- ============================================================

INSERT INTO organizacao.company (
    id,
    public_name,
    company_name,
    email,
    phone,
    cnpj,
    address,
    has_online,
    is_active,
    principal_teacher_id
)
VALUES (
    '11111111-1111-1111-1111-111111111111',
    'Lynx Demo',
    'Lynx Demo Company',
    'company@mail.com',
    '85999999999',
    '00000000000191',
    'Fortaleza - CE',
    TRUE,
    TRUE,
    '22222222-2222-2222-2222-222222222222'
)
ON CONFLICT (id) DO NOTHING;

-- ============================================================
-- USER COMPANY
-- ============================================================
--
-- Admin:
--   admin@mail.com -> Lynx Demo -> ADMIN
--
-- Student:
--   student@mail.com -> Lynx Demo -> STUDENT
-- ============================================================

INSERT INTO seguranca.user_company (
    id,
    company_id,
    user_id,
    role,
    added_at,
    updated_at,
    active
)
VALUES
(
    '44444444-4444-4444-4444-444444444444',
    '11111111-1111-1111-1111-111111111111',
    '22222222-2222-2222-2222-222222222222',
    'PRINCIPAL',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    TRUE
),
(
    '55555555-5555-5555-5555-555555555555',
    '11111111-1111-1111-1111-111111111111',
    '33333333-3333-3333-3333-333333333333',
    'STUDENT',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    TRUE
)
ON CONFLICT (user_id, company_id) DO NOTHING;