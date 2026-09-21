-- ============================================================
-- LYNX - DEVELOPMENT SEED
-- ============================================================
--
-- Senha de desenvolvimento para todos os usuários:
-- 123456
--
-- BCrypt:
-- $2y$10$gYHvRPiYkwe7xWhDfvVRi.4gPOmjC8vmRc1ZARQw5S9SZsg/mcrS.
--
-- ATENÇÃO:
-- Este arquivo é destinado exclusivamente ao ambiente de DEV.
-- ============================================================


-- ============================================================
-- SOCIAL NETWORK
-- ============================================================

INSERT INTO configuracao.social_network (
    id,
    name,
    icon
)
VALUES
(
    '4b5a47f5-a815-4a1f-81c0-f57f6e3ef103',
    'Instagram',
    'brand-instagram'
),
(
    'e8519c5f-8851-4106-a4fd-47d2d911e360',
    'Facebook',
    'brand-facebook'
),
(
    '7c17c701-ad2b-45ed-906f-9cc8b099ec0f',
    'YouTube',
    'brand-youtube'
),
(
    '7f4e0ac3-45d5-47cd-bd1e-aa7bba449d22',
    'TikTok',
    'brand-tiktok'
),
(
    '9634679c-d96e-40cc-b1f1-3f424ae18b6f',
    'LinkedIn',
    'brand-linkedin'
),
(
    'f7148c57-0216-46a3-be10-6a60695af496',
    'WhatsApp',
    'brand-whatsapp'
),
(
    'a1013acf-ad10-49eb-9e48-115f66feef78',
    'Telegram',
    'brand-telegram'
),
(
    '037fad81-44c1-4328-912c-02a119a6cde4',
    'Pinterest',
    'brand-pinterest'
),
(
    '8a9c0791-d936-41b8-a335-001558d46830',
    'VK',
    'brand-vk'
),
(
    '6595e0be-29a7-4c61-885d-d63277733108',
    'X',
    'brand-x'
)
ON CONFLICT (name) DO NOTHING;


-- ============================================================
-- USERS
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

-- ------------------------------------------------------------
-- ADMIN
-- ------------------------------------------------------------
(
    '22222222-2222-2222-2222-222222222222',
    'Admin',
    'admin@email.com',
    '$2y$10$gYHvRPiYkwe7xWhDfvVRi.4gPOmjC8vmRc1ZARQw5S9SZsg/mcrS.',
    '1990-01-10',
    NULL,
    FALSE,
    TRUE
),

-- ------------------------------------------------------------
-- STUDENT EXISTENTE
-- ------------------------------------------------------------
(
    '33333333-3333-3333-3333-333333333333',
    'Student',
    'student@email.com',
    '$2y$10$gYHvRPiYkwe7xWhDfvVRi.4gPOmjC8vmRc1ZARQw5S9SZsg/mcrS.',
    '2008-05-15',
    NULL,
    FALSE,
    TRUE
),

-- ------------------------------------------------------------
-- PRINCIPAL
-- ------------------------------------------------------------
(
    '66666666-6666-6666-6666-666666666666',
    'Maria Principal',
    'principal@email.com',
    '$2y$10$gYHvRPiYkwe7xWhDfvVRi.4gPOmjC8vmRc1ZARQw5S9SZsg/mcrS.',
    '1985-03-20',
    NULL,
    TRUE,
    TRUE
),

-- ------------------------------------------------------------
-- HEADTEACHERS
-- ------------------------------------------------------------
(
    '77777777-7777-7777-7777-777777777777',
    'Carlos Headteacher',
    'headteacher1@email.com',
    '$2y$10$gYHvRPiYkwe7xWhDfvVRi.4gPOmjC8vmRc1ZARQw5S9SZsg/mcrS.',
    '1987-07-12',
    NULL,
    TRUE,
    TRUE
),
(
    '88888888-8888-8888-8888-888888888888',
    'Ana Headteacher',
    'headteacher2@email.com',
    '$2y$10$gYHvRPiYkwe7xWhDfvVRi.4gPOmjC8vmRc1ZARQw5S9SZsg/mcrS.',
    '1989-11-03',
    NULL,
    TRUE,
    TRUE
),

-- ------------------------------------------------------------
-- TEACHERS
-- ------------------------------------------------------------
(
    '99999999-9999-9999-9999-999999999999',
    'Joao Teacher',
    'teacher1@email.com',
    '$2y$10$gYHvRPiYkwe7xWhDfvVRi.4gPOmjC8vmRc1ZARQw5S9SZsg/mcrS.',
    '1992-02-18',
    NULL,
    TRUE,
    TRUE
),
(
    'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
    'Julia Teacher',
    'teacher2@email.com',
    '$2y$10$gYHvRPiYkwe7xWhDfvVRi.4gPOmjC8vmRc1ZARQw5S9SZsg/mcrS.',
    '1994-08-25',
    NULL,
    TRUE,
    TRUE
),
(
    'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb',
    'Pedro Teacher',
    'teacher3@email.com',
    '$2y$10$gYHvRPiYkwe7xWhDfvVRi.4gPOmjC8vmRc1ZARQw5S9SZsg/mcrS.',
    '1991-12-09',
    NULL,
    TRUE,
    TRUE
),

-- ------------------------------------------------------------
-- STUDENTS
-- ------------------------------------------------------------
(
    'cccccccc-cccc-cccc-cccc-cccccccccccc',
    'Lucas Student',
    'student2@email.com',
    '$2y$10$gYHvRPiYkwe7xWhDfvVRi.4gPOmjC8vmRc1ZARQw5S9SZsg/mcrS.',
    '2008-01-15',
    NULL,
    FALSE,
    TRUE
),
(
    'dddddddd-dddd-dddd-dddd-dddddddddddd',
    'Beatriz Student',
    'student3@email.com',
    '$2y$10$gYHvRPiYkwe7xWhDfvVRi.4gPOmjC8vmRc1ZARQw5S9SZsg/mcrS.',
    '2007-06-21',
    NULL,
    FALSE,
    TRUE
),
(
    'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee',
    'Gabriel Student',
    'student4@email.com',
    '$2y$10$gYHvRPiYkwe7xWhDfvVRi.4gPOmjC8vmRc1ZARQw5S9SZsg/mcrS.',
    '2009-03-11',
    NULL,
    FALSE,
    TRUE
),
(
    'ffffffff-ffff-ffff-ffff-ffffffffffff',
    'Laura Student',
    'student5@email.com',
    '$2y$10$gYHvRPiYkwe7xWhDfvVRi.4gPOmjC8vmRc1ZARQw5S9SZsg/mcrS.',
    '2008-09-30',
    NULL,
    FALSE,
    TRUE
),
(
    '10101010-1010-1010-1010-101010101010',
    'Rafael Student',
    'student6@email.com',
    '$2y$10$gYHvRPiYkwe7xWhDfvVRi.4gPOmjC8vmRc1ZARQw5S9SZsg/mcrS.',
    '2007-12-14',
    NULL,
    FALSE,
    TRUE
),
(
    '12121212-1212-1212-1212-121212121212',
    'Mariana Student',
    'student7@email.com',
    '$2y$10$gYHvRPiYkwe7xWhDfvVRi.4gPOmjC8vmRc1ZARQw5S9SZsg/mcrS.',
    '2008-04-07',
    NULL,
    FALSE,
    TRUE
),
(
    '13131313-1313-1313-1313-131313131313',
    'Felipe Student',
    'student8@email.com',
    '$2y$10$gYHvRPiYkwe7xWhDfvVRi.4gPOmjC8vmRc1ZARQw5S9SZsg/mcrS.',
    '2009-10-19',
    NULL,
    FALSE,
    TRUE
),
(
    '14141414-1414-1414-1414-141414141414',
    'Camila Student',
    'student9@email.com',
    '$2y$10$gYHvRPiYkwe7xWhDfvVRi.4gPOmjC8vmRc1ZARQw5S9SZsg/mcrS.',
    '2008-07-23',
    NULL,
    FALSE,
    TRUE
),

-- ------------------------------------------------------------
-- INACTIVE USER
-- ------------------------------------------------------------
(
    '15151515-1515-1515-1515-151515151515',
    'Inactive User',
    'inactive@email.com',
    '$2y$10$gYHvRPiYkwe7xWhDfvVRi.4gPOmjC8vmRc1ZARQw5S9SZsg/mcrS.',
    '2007-02-17',
    NULL,
    FALSE,
    FALSE
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
VALUES
(
    '11111111-1111-1111-1111-111111111111',
    'Lynx Demo',
    'Lynx Demo Company',
    'company@mail.com',
    '85999999999',
    '00000000000191',
    'Fortaleza - CE',
    TRUE,
    TRUE,
    '66666666-6666-6666-6666-666666666666'
)
ON CONFLICT (id) DO NOTHING;


-- ============================================================
-- USER COMPANY
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

-- ------------------------------------------------------------
-- ADMIN
-- ------------------------------------------------------------
(
    '44444444-4444-4444-4444-444444444444',
    '11111111-1111-1111-1111-111111111111',
    '22222222-2222-2222-2222-222222222222',
    'ADMIN',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    TRUE
),

-- ------------------------------------------------------------
-- STUDENT
-- ------------------------------------------------------------
(
    '55555555-5555-5555-5555-555555555555',
    '11111111-1111-1111-1111-111111111111',
    '33333333-3333-3333-3333-333333333333',
    'STUDENT',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    TRUE
),

-- ------------------------------------------------------------
-- PRINCIPAL
-- ------------------------------------------------------------
(
    '16161616-1616-1616-1616-161616161616',
    '11111111-1111-1111-1111-111111111111',
    '66666666-6666-6666-6666-666666666666',
    'PRINCIPAL',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    TRUE
),

-- ------------------------------------------------------------
-- HEADTEACHERS
-- ------------------------------------------------------------
(
    '17171717-1717-1717-1717-171717171717',
    '11111111-1111-1111-1111-111111111111',
    '77777777-7777-7777-7777-777777777777',
    'HEADTEACHER',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    TRUE
),
(
    '18181818-1818-1818-1818-181818181818',
    '11111111-1111-1111-1111-111111111111',
    '88888888-8888-8888-8888-888888888888',
    'HEADTEACHER',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    TRUE
),

-- ------------------------------------------------------------
-- TEACHERS
-- ------------------------------------------------------------
(
    '19191919-1919-1919-1919-191919191919',
    '11111111-1111-1111-1111-111111111111',
    '99999999-9999-9999-9999-999999999999',
    'TEACHER',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    TRUE
),
(
    '20202020-2020-2020-2020-202020202020',
    '11111111-1111-1111-1111-111111111111',
    'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
    'TEACHER',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    TRUE
),
(
    '21212121-2121-2121-2121-212121212121',
    '11111111-1111-1111-1111-111111111111',
    'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb',
    'TEACHER',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    TRUE
),

-- ------------------------------------------------------------
-- STUDENTS
-- ------------------------------------------------------------
(
    '23232323-2323-2323-2323-232323232323',
    '11111111-1111-1111-1111-111111111111',
    'cccccccc-cccc-cccc-cccc-cccccccccccc',
    'STUDENT',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    TRUE
),
(
    '24242424-2424-2424-2424-242424242424',
    '11111111-1111-1111-1111-111111111111',
    'dddddddd-dddd-dddd-dddd-dddddddddddd',
    'STUDENT',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    TRUE
),
(
    '25252525-2525-2525-2525-252525252525',
    '11111111-1111-1111-1111-111111111111',
    'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee',
    'STUDENT',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    TRUE
),
(
    '26262626-2626-2626-2626-262626262626',
    '11111111-1111-1111-1111-111111111111',
    'ffffffff-ffff-ffff-ffff-ffffffffffff',
    'STUDENT',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    TRUE
),
(
    '27272727-2727-2727-2727-272727272727',
    '11111111-1111-1111-1111-111111111111',
    '10101010-1010-1010-1010-101010101010',
    'STUDENT',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    TRUE
),
(
    '28282828-2828-2828-2828-282828282828',
    '11111111-1111-1111-1111-111111111111',
    '12121212-1212-1212-1212-121212121212',
    'STUDENT',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    TRUE
),
(
    '29292929-2929-2929-2929-292929292929',
    '11111111-1111-1111-1111-111111111111',
    '13131313-1313-1313-1313-131313131313',
    'STUDENT',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    TRUE
),
(
    '30303030-3030-3030-3030-303030303030',
    '11111111-1111-1111-1111-111111111111',
    '14141414-1414-1414-1414-141414141414',
    'STUDENT',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    TRUE
),

-- ------------------------------------------------------------
-- INACTIVE USER
-- ------------------------------------------------------------
(
    '31313131-3131-3131-3131-313131313131',
    '11111111-1111-1111-1111-111111111111',
    '15151515-1515-1515-1515-151515151515',
    'STUDENT',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    FALSE
)

ON CONFLICT (user_id, company_id) DO NOTHING;


-- ============================================================
-- COURSE CLASS
-- ============================================================

INSERT INTO academico.course_class (
    id,
    name,
    level,
    language,
    max_students,
    teacher_id,
    company_id,
    start_date,
    end_date,
    created_at,
    updated_at
)
VALUES

-- ------------------------------------------------------------
-- ENGLISH A1
-- ------------------------------------------------------------
(
    '32323232-3232-3232-3232-323232323232',
    'English A1 - Turma 01',
    'A1',
    'ENGLISH',
    20,
    '99999999-9999-9999-9999-999999999999',
    '11111111-1111-1111-1111-111111111111',
    '2026-08-01 08:00:00',
    '2026-12-15 18:00:00',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),

-- ------------------------------------------------------------
-- ENGLISH A2
-- ------------------------------------------------------------
(
    '33333334-3333-3333-3333-333333333333',
    'English A2 - Turma 01',
    'A2',
    'ENGLISH',
    20,
    'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
    '11111111-1111-1111-1111-111111111111',
    '2026-08-01 08:00:00',
    '2026-12-15 18:00:00',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),

-- ------------------------------------------------------------
-- ENGLISH B1
-- ------------------------------------------------------------
(
    '34343434-3434-3434-3434-343434343434',
    'English B1 - Turma 01',
    'B1',
    'ENGLISH',
    25,
    'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb',
    '11111111-1111-1111-1111-111111111111',
    '2026-08-01 08:00:00',
    '2026-12-15 18:00:00',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),

-- ------------------------------------------------------------
-- SPANISH A1
-- ------------------------------------------------------------
(
    '35353535-3535-3535-3535-353535353535',
    'Spanish A1 - Turma 01',
    'A1',
    'SPANISH',
    20,
    '99999999-9999-9999-9999-999999999999',
    '11111111-1111-1111-1111-111111111111',
    '2026-08-15 08:00:00',
    '2026-12-20 18:00:00',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),

-- ------------------------------------------------------------
-- FRENCH A2
-- ------------------------------------------------------------
(
    '36363636-3636-3636-3636-363636363636',
    'French A2 - Turma 01',
    'A2',
    'FRENCH',
    15,
    'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
    '11111111-1111-1111-1111-111111111111',
    '2026-08-15 08:00:00',
    '2026-12-20 18:00:00',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
)

ON CONFLICT (id) DO NOTHING;


-- ============================================================
-- COURSE CLASS STUDENT
-- ============================================================

INSERT INTO academico.course_class_student (
    id,
    course_class_id,
    student_id,
    enrollment_date
)
VALUES

-- ------------------------------------------------------------
-- ENGLISH A1
-- ------------------------------------------------------------
(
    '37373737-3737-3737-3737-373737373737',
    '32323232-3232-3232-3232-323232323232',
    '33333333-3333-3333-3333-333333333333',
    '2026-08-01 08:00:00'
),
(
    '38383838-3838-3838-3838-383838383838',
    '32323232-3232-3232-3232-323232323232',
    'cccccccc-cccc-cccc-cccc-cccccccccccc',
    '2026-08-01 08:15:00'
),
(
    '39393939-3939-3939-3939-393939393939',
    '32323232-3232-3232-3232-323232323232',
    'dddddddd-dddd-dddd-dddd-dddddddddddd',
    '2026-08-01 08:20:00'
),
(
    '40404040-4040-4040-4040-404040404040',
    '32323232-3232-3232-3232-323232323232',
    'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee',
    '2026-08-01 08:30:00'
),
(
    '41414141-4141-4141-4141-414141414141',
    '32323232-3232-3232-3232-323232323232',
    'ffffffff-ffff-ffff-ffff-ffffffffffff',
    '2026-08-01 08:35:00'
),

-- ------------------------------------------------------------
-- ENGLISH A2
-- ------------------------------------------------------------
(
    '42424242-4242-4242-4242-424242424242',
    '33333334-3333-3333-3333-333333333333',
    '10101010-1010-1010-1010-101010101010',
    '2026-08-01 09:00:00'
),
(
    '43434343-4343-4343-4343-434343434343',
    '33333334-3333-3333-3333-333333333333',
    '12121212-1212-1212-1212-121212121212',
    '2026-08-01 09:05:00'
),
(
    '44444445-4444-4444-4444-444444444444',
    '33333334-3333-3333-3333-333333333333',
    '13131313-1313-1313-1313-131313131313',
    '2026-08-01 09:10:00'
),

-- ------------------------------------------------------------
-- ENGLISH B1
-- ------------------------------------------------------------
(
    '45454545-4545-4545-4545-454545454545',
    '34343434-3434-3434-3434-343434343434',
    '14141414-1414-1414-1414-141414141414',
    '2026-08-01 10:00:00'
),
(
    '46464646-4646-4646-4646-464646464646',
    '34343434-3434-3434-3434-343434343434',
    '33333333-3333-3333-3333-333333333333',
    '2026-08-01 10:05:00'
),
(
    '47474747-4747-4747-4747-474747474747',
    '34343434-3434-3434-3434-343434343434',
    'cccccccc-cccc-cccc-cccc-cccccccccccc',
    '2026-08-01 10:10:00'
),

-- ------------------------------------------------------------
-- SPANISH A1
-- ------------------------------------------------------------
(
    '48484848-4848-4848-4848-484848484848',
    '35353535-3535-3535-3535-353535353535',
    'dddddddd-dddd-dddd-dddd-dddddddddddd',
    '2026-08-15 08:00:00'
),
(
    '49494949-4949-4949-4949-494949494949',
    '35353535-3535-3535-3535-353535353535',
    'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee',
    '2026-08-15 08:05:00'
),

-- ------------------------------------------------------------
-- FRENCH A2
-- ------------------------------------------------------------
(
    '50505050-5050-5050-5050-505050505050',
    '36363636-3636-3636-3636-363636363636',
    'ffffffff-ffff-ffff-ffff-ffffffffffff',
    '2026-08-15 09:00:00'
),
(
    '51515151-5151-5151-5151-515151515151',
    '36363636-3636-3636-3636-363636363636',
    '10101010-1010-1010-1010-101010101010',
    '2026-08-15 09:05:00'
)

ON CONFLICT (id) DO NOTHING;


-- ============================================================
-- ASSESSMENTS
-- ============================================================

INSERT INTO academico.assessment (
    id,
    title,
    max_score,
    date,
    limit_date,
    course_class_id
)
VALUES

-- ------------------------------------------------------------
-- ENGLISH A1
-- ------------------------------------------------------------
(
    '52525252-5252-5252-5252-525252525252',
    'Test 1',
    10,
    '2026-09-10',
    '2026-09-10',
    '32323232-3232-3232-3232-323232323232'
),
(
    '53535353-5353-5353-5353-535353535353',
    'Speaking',
    10,
    '2026-09-25',
    '2026-09-25',
    '32323232-3232-3232-3232-323232323232'
),
(
    '54545454-5454-5454-5454-545454545454',
    'Writing',
    10,
    '2026-10-10',
    '2026-10-10',
    '32323232-3232-3232-3232-323232323232'
),
(
    '55555556-5555-5555-5555-555555555555',
    'Final Exam',
    10,
    '2026-12-10',
    '2026-12-10',
    '32323232-3232-3232-3232-323232323232'
),

-- ------------------------------------------------------------
-- ENGLISH A2
-- ------------------------------------------------------------
(
    '56565656-5656-5656-5656-565656565656',
    'Test 1',
    10,
    '2026-09-12',
    '2026-09-12',
    '33333334-3333-3333-3333-333333333333'
),
(
    '57575757-5757-5757-5757-575757575757',
    'Listening',
    10,
    '2026-09-30',
    '2026-09-30',
    '33333334-3333-3333-3333-333333333333'
),
(
    '58585858-5858-5858-5858-585858585858',
    'Final Exam',
    10,
    '2026-12-12',
    '2026-12-12',
    '33333334-3333-3333-3333-333333333333'
),

-- ------------------------------------------------------------
-- ENGLISH B1
-- ------------------------------------------------------------
(
    '59595959-5959-5959-5959-595959595959',
    'Grammar Test',
    10,
    '2026-09-15',
    '2026-09-15',
    '34343434-3434-3434-3434-343434343434'
),
(
    '60606060-6060-6060-6060-606060606060',
    'Speaking',
    10,
    '2026-10-05',
    '2026-10-05',
    '34343434-3434-3434-3434-343434343434'
),
(
    '61616161-6161-6161-6161-616161616161',
    'Final Exam',
    10,
    '2026-12-15',
    '2026-12-15',
    '34343434-3434-3434-3434-343434343434'
),

-- ------------------------------------------------------------
-- SPANISH A1
-- ------------------------------------------------------------
(
    '62626262-6262-6262-6262-626262626262',
    'Prueba 1',
    10,
    '2026-09-20',
    '2026-09-20',
    '35353535-3535-3535-3535-353535353535'
),
(
    '63636363-6363-6363-6363-636363636363',
    'Oral',
    10,
    '2026-10-15',
    '2026-10-15',
    '35353535-3535-3535-3535-353535353535'
),

-- ------------------------------------------------------------
-- FRENCH A2
-- ------------------------------------------------------------
(
    '64646464-6464-6464-6464-646464646464',
    'Test 1',
    10,
    '2026-09-22',
    '2026-09-22',
    '36363636-3636-3636-3636-363636363636'
),
(
    '65656565-6565-6565-6565-656565656565',
    'Final Exam',
    10,
    '2026-12-18',
    '2026-12-18',
    '36363636-3636-3636-3636-363636363636'
)

ON CONFLICT (id) DO NOTHING;


-- ============================================================
-- GRADES
-- ============================================================

INSERT INTO academico.grade (
    id,
    user_id,
    assessment_id,
    score,
    feedback
)
VALUES

-- ------------------------------------------------------------
-- ENGLISH A1 - TEST 1
-- ------------------------------------------------------------
(
    '66666667-6666-6666-6666-666666666666',
    '33333333-3333-3333-3333-333333333333',
    '52525252-5252-5252-5252-525252525252',
    8.5,
    'Good performance.'
),
(
    '67676767-6767-6767-6767-676767676767',
    'cccccccc-cccc-cccc-cccc-cccccccccccc',
    '52525252-5252-5252-5252-525252525252',
    7.0,
    'Needs more practice.'
),
(
    '68686868-6868-6868-6868-686868686868',
    'dddddddd-dddd-dddd-dddd-dddddddddddd',
    '52525252-5252-5252-5252-525252525252',
    9.0,
    'Excellent.'
),
(
    '69696969-6969-6969-6969-696969696969',
    'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee',
    '52525252-5252-5252-5252-525252525252',
    6.5,
    'Keep practicing.'
),
(
    '70707070-7070-7070-7070-707070707070',
    'ffffffff-ffff-ffff-ffff-ffffffffffff',
    '52525252-5252-5252-5252-525252525252',
    10.0,
    'Outstanding performance.'
),

-- ------------------------------------------------------------
-- ENGLISH A1 - SPEAKING
-- ------------------------------------------------------------
(
    '71717171-7171-7171-7171-717171717171',
    '33333333-3333-3333-3333-333333333333',
    '53535353-5353-5353-5353-535353535353',
    8.0,
    'Good pronunciation.'
),
(
    '72727272-7272-7272-7272-727272727272',
    'cccccccc-cccc-cccc-cccc-cccccccccccc',
    '53535353-5353-5353-5353-535353535353',
    7.5,
    'Good progress.'
),
(
    '73737373-7373-7373-7373-737373737373',
    'dddddddd-dddd-dddd-dddd-dddddddddddd',
    '53535353-5353-5353-5353-535353535353',
    9.5,
    'Excellent speaking skills.'
),

-- ------------------------------------------------------------
-- ENGLISH A2 - TEST 1
-- ------------------------------------------------------------
(
    '74747474-7474-7474-7474-747474747474',
    '10101010-1010-1010-1010-101010101010',
    '56565656-5656-5656-5656-565656565656',
    8.0,
    'Good result.'
),
(
    '75757575-7575-7575-7575-757575757575',
    '12121212-1212-1212-1212-121212121212',
    '56565656-5656-5656-5656-565656565656',
    9.0,
    'Excellent result.'
),
(
    '76767676-7676-7676-7676-767676767676',
    '13131313-1313-1313-1313-131313131313',
    '56565656-5656-5656-5656-565656565656',
    6.0,
    'Needs improvement.'
),

-- ------------------------------------------------------------
-- ENGLISH B1 - GRAMMAR
-- ------------------------------------------------------------
(
    '77777778-7777-7777-7777-777777777777',
    '14141414-1414-1414-1414-141414141414',
    '59595959-5959-5959-5959-595959595959',
    9.0,
    'Very good grammar.'
),
(
    '78787878-7878-7878-7878-787878787878',
    '33333333-3333-3333-3333-333333333333',
    '59595959-5959-5959-5959-595959595959',
    7.5,
    'Good result.'
),
(
    '79797979-7979-7979-7979-797979797979',
    'cccccccc-cccc-cccc-cccc-cccccccccccc',
    '59595959-5959-5959-5959-595959595959',
    8.5,
    'Good performance.'
),

-- ------------------------------------------------------------
-- SPANISH A1
-- ------------------------------------------------------------
(
    '80808080-8080-8080-8080-808080808080',
    'dddddddd-dddd-dddd-dddd-dddddddddddd',
    '62626262-6262-6262-6262-626262626262',
    8.5,
    'Muy buen resultado.'
),
(
    '81818181-8181-8181-8181-818181818181',
    'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee',
    '62626262-6262-6262-6262-626262626262',
    7.0,
    'Buen trabajo.'
),

-- ------------------------------------------------------------
-- FRENCH A2
-- ------------------------------------------------------------
(
    '82828282-8282-8282-8282-828282828282',
    'ffffffff-ffff-ffff-ffff-ffffffffffff',
    '64646464-6464-6464-6464-646464646464',
    8.0,
    'Bon résultat.'
),
(
    '83838383-8383-8383-8383-838383838383',
    '10101010-1010-1010-1010-101010101010',
    '64646464-6464-6464-6464-646464646464',
    9.0,
    'Très bien.'
)

ON CONFLICT (id) DO NOTHING;


-- ============================================================
-- QUESTIONS
-- ============================================================

INSERT INTO academico.question (
    id,
    author_id,
    company_id,
    privacy,
    difficulty,
    question_type,
    header,
    body,
    footer,
    expected_answer,
    language,
    created_at,
    updated_at,
    deleted_at
)
VALUES

-- ------------------------------------------------------------
-- VERY EASY
-- ------------------------------------------------------------
(
    '84848484-8484-8484-8484-848484848484',
    '99999999-9999-9999-9999-999999999999',
    '11111111-1111-1111-1111-111111111111',
    0,
    'VERY_EASY',
    'MULTIPLE_CHOICE',
    'Basic English',
    'What is the correct translation of "Hello"?',
    NULL,
    'Olá',
    'ENGLISH',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    NULL
),
(
    '85858585-8585-8585-8585-858585858585',
    'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
    '11111111-1111-1111-1111-111111111111',
    0,
    'VERY_EASY',
    'MULTIPLE_CHOICE',
    'English Vocabulary',
    'Which word means "casa" in English?',
    NULL,
    'House',
    'ENGLISH',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    NULL
),

-- ------------------------------------------------------------
-- EASY
-- ------------------------------------------------------------
(
    '86868686-8686-8686-8686-868686868686',
    '99999999-9999-9999-9999-999999999999',
    '11111111-1111-1111-1111-111111111111',
    1,
    'EASY',
    'MULTIPLE_CHOICE',
    'Verb To Be',
    'Choose the correct option: She ___ a teacher.',
    NULL,
    'is',
    'ENGLISH',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    NULL
),
(
    '87878787-8787-8787-8787-878787878787',
    'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
    '11111111-1111-1111-1111-111111111111',
    1,
    'EASY',
    'MULTIPLE_CHOICE',
    'Spanish Vocabulary',
    'What is the Spanish word for "book"?',
    NULL,
    'Libro',
    'SPANISH',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    NULL
),

-- ------------------------------------------------------------
-- MEDIUM
-- ------------------------------------------------------------
(
    '88888889-8888-8888-8888-888888888888',
    '99999999-9999-9999-9999-999999999999',
    '11111111-1111-1111-1111-111111111111',
    1,
    'MEDIUM',
    'MULTIPLE_CHOICE',
    'Present Perfect',
    'Choose the correct sentence using the present perfect.',
    NULL,
    'I have finished my homework.',
    'ENGLISH',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    NULL
),
(
    '89898989-8989-8989-8989-898989898989',
    'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb',
    '11111111-1111-1111-1111-111111111111',
    2,
    'MEDIUM',
    'ESSAY_QUESTION',
    'Writing',
    'Write a short paragraph describing your daily routine.',
    NULL,
    'Answers should describe a coherent daily routine using appropriate vocabulary and grammar.',
    'ENGLISH',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    NULL
),

-- ------------------------------------------------------------
-- HARD
-- ------------------------------------------------------------
(
    '90909090-9090-9090-9090-909090909090',
    '99999999-9999-9999-9999-999999999999',
    '11111111-1111-1111-1111-111111111111',
    1,
    'HARD',
    'MULTIPLE_CHOICE',
    'Conditional Sentences',
    'Which sentence correctly uses the third conditional?',
    NULL,
    'If I had studied, I would have passed the exam.',
    'ENGLISH',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    NULL
),
(
    '91919191-9191-9191-9191-919191919191',
    'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
    '11111111-1111-1111-1111-111111111111',
    2,
    'HARD',
    'ESSAY_QUESTION',
    'Opinion Essay',
    'Discuss the advantages and disadvantages of learning a foreign language.',
    NULL,
    'A well-structured argument with clear examples, appropriate vocabulary and grammatical accuracy.',
    'ENGLISH',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    NULL
),

-- ------------------------------------------------------------
-- VERY HARD
-- ------------------------------------------------------------
(
    '92929292-9292-9292-9292-929292929292',
    'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb',
    '11111111-1111-1111-1111-111111111111',
    2,
    'VERY_HARD',
    'ESSAY_QUESTION',
    'Advanced Writing',
    'Analyze the role of language in shaping cultural identity.',
    NULL,
    'A sophisticated argument demonstrating advanced vocabulary, organization, critical thinking and grammatical accuracy.',
    'ENGLISH',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    NULL
),

-- ------------------------------------------------------------
-- PORTUGUESE
-- ------------------------------------------------------------
(
    '93939393-9393-9393-9393-939393939393',
    '99999999-9999-9999-9999-999999999999',
    '11111111-1111-1111-1111-111111111111',
    0,
    'EASY',
    'MULTIPLE_CHOICE',
    'Portuguese Grammar',
    'Qual é o plural correto de "cidadão"?',
    NULL,
    'cidadãos',
    'PORTUGUESE',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    NULL
),

-- ------------------------------------------------------------
-- FRENCH
-- ------------------------------------------------------------
(
    '94949494-9494-9494-9494-949494949494',
    'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
    '11111111-1111-1111-1111-111111111111',
    1,
    'MEDIUM',
    'MULTIPLE_CHOICE',
    'French Vocabulary',
    'What is the French translation of "school"?',
    NULL,
    'école',
    'FRENCH',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    NULL
),

-- ------------------------------------------------------------
-- DELETED QUESTION
-- ------------------------------------------------------------
(
    '95959595-9595-9595-9595-959595959595',
    '99999999-9999-9999-9999-999999999999',
    '11111111-1111-1111-1111-111111111111',
    1,
    'EASY',
    'MULTIPLE_CHOICE',
    'Deleted Question',
    'This question simulates a deleted question.',
    NULL,
    'Example',
    'ENGLISH',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
)

ON CONFLICT (id) DO NOTHING;


-- ============================================================
-- COMPANY SOCIAL NETWORK
-- ============================================================

INSERT INTO configuracao.company_social_network (
    id,
    company_id,
    social_network_id,
    url
)
VALUES
(
    '96969696-9696-9696-9696-969696969696',
    '11111111-1111-1111-1111-111111111111',
    '4b5a47f5-a815-4a1f-81c0-f57f6e3ef103',
    'https://instagram.com/lynxdemo'
),
(
    '97979797-9797-9797-9797-979797979797',
    '11111111-1111-1111-1111-111111111111',
    'e8519c5f-8851-4106-a4fd-47d2d911e360',
    'https://facebook.com/lynxdemo'
),
(
    '98989898-9898-9898-9898-989898989898',
    '11111111-1111-1111-1111-111111111111',
    '7c17c701-ad2b-45ed-906f-9cc8b099ec0f',
    'https://youtube.com/@lynxdemo'
),
(
    '99999990-9999-9999-9999-999999999999',
    '11111111-1111-1111-1111-111111111111',
    '7f4e0ac3-45d5-47cd-bd1e-aa7bba449d22',
    'https://tiktok.com/@lynxdemo'
),
(
    'a0a0a0a0-a0a0-a0a0-a0a0-a0a0a0a0a0a0',
    '11111111-1111-1111-1111-111111111111',
    '9634679c-d96e-40cc-b1f1-3f424ae18b6f',
    'https://linkedin.com/company/lynxdemo'
),
(
    'a1a1a1a1-a1a1-a1a1-a1a1-a1a1a1a1a1a1',
    '11111111-1111-1111-1111-111111111111',
    'f7148c57-0216-46a3-be10-6a60695af496',
    'https://wa.me/5585999999999'
)
ON CONFLICT (id) DO NOTHING;


-- ============================================================
-- IMPORTED FILE
-- ============================================================

INSERT INTO configuracao.imported_file (
    id,
    filename,
    status,
    created_at,
    finished_at
)
VALUES
(
    'a2a2a2a2-a2a2-a2a2-a2a2-a2a2a2a2a2a2',
    'students-import.xlsx',
    'SUCCESS',
    '2026-08-20 09:00:00',
    '2026-08-20 09:00:12'
),
(
    'a3a3a3a3-a3a3-a3a3-a3a3-a3a3a3a3a3a3',
    'questions-import.xlsx',
    'SUCCESS',
    '2026-08-21 10:30:00',
    '2026-08-21 10:31:45'
),
(
    'a4a4a4a4-a4a4-a4a4-a4a4-a4a4a4a4a4a4',
    'students-update.xlsx',
    'PROCESSING',
    '2026-09-01 14:00:00',
    CURRENT_TIMESTAMP
),
(
    'a5a5a5a5-a5a5-a5a5-a5a5-a5a5a5a5a5a5',
    'invalid-questions.xlsx',
    'ERROR',
    '2026-08-25 16:00:00',
    '2026-08-25 16:00:08'
),
(
    'a6a6a6a6-a6a6-a6a6-a6a6-a6a6a6a6a6a6',
    'teachers-import.xlsx',
    'PENDING',
    '2026-09-03 11:00:00',
    CURRENT_TIMESTAMP
)
ON CONFLICT (id) DO NOTHING;


-- ============================================================
-- REFRESH TOKEN
-- ============================================================
--
-- NÃO gerar refresh tokens artificiais aqui.
--
-- O token_hash deve corresponder ao hash de um refresh token
-- real gerado pela aplicação.
--
-- Para testar refresh token:
-- 1. Faça login normalmente.
-- 2. A aplicação deverá criar o registro em
--    seguranca.refresh_token.
-- 3. Utilize o refresh token gerado pela aplicação.
-- ============================================================


-- ============================================================
-- END OF SEED
-- ============================================================
