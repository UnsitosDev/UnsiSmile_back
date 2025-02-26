-- Script mejorado para poblar la base de datos
-- Creado: 2025-02-25
-- 1. Inserción de usuarios en la tabla user_app
INSERT INTO
    `user_app` (
        `id`,
        `password`,
        `status`,
        `username`,
        `first_login`,
        `role_id`
    )
VALUES
    -- Usuario estudiante 1 (role_id = 2)
    (
        'a1b2c3d4-e5f6-7g8h-9i10-j11k12l13m15',
        '$2a$10$PsXJlaqLHmD93aK6svRMruRtfMoRgoW6O6bbVjS91iedGsnRG.qdW',
        1,
        '2019060310',
        0,
        2
    ),
    -- Usuario administrador (role_id = 1)
    (
        '8d3213f2-362c-4c1a-be17-10e774ba362d',
        '$2a$10$Ubqh3CQEMOuar//DzeDkleZw7dBuojtg485aUdYOdCD1/e68ZcOLi',
        1,
        'E12345',
        1,
        1
    ),
    -- Usuario estudiante 2 (role_id = 2)
    (
        'a1b2c3d4-e5f6-7g8h-9i10-j11k12l13m14',
        '$2a$10$PsXJlaqLHmD93aK6svRMruRtfMoRgoW6O6bbVjS91iedGsnRG.qdW',
        1,
        '2023070425',
        1,
        2
    );

-- 2. Inserción de personas en la tabla people
INSERT INTO
    `people` (
        `curp`,
        `first_name`,
        `second_name`,
        `first_lastname`,
        `second_lastname`,
        `birth_date`,
        `fk_gender`,
        `email`,
        `phone`,
        `created_at`,
        `created_by`,
        `status_key`,
        `updated_at`,
        `updated_by`
    )
VALUES
    -- Administrador
    (
        'ABCD901215HDFRRN08',
        'Juan',
        'Carlos',
        'García',
        'Hernández',
        '1990-12-15',   
        1,
        'juancarlos.garcia@example.com',
        '5551234567',
        '2025-02-25 17:47:03.618100',
        '123e4567-e89b-12d3-a456-426614174000',
        'A',
        NULL,
        NULL
    ),
    -- Estudiante 1
    (
        'AOPS011028HOCNRLA9',
        'JUAN',
        'LUIS',
        'ANTONIO',
        'Martíez',
        '2003-06-04',
        1,
        'example@gmail.com',
        '5645123541',
        '2025-02-25 17:47:03.618100',
        '123e4567-e89b-12d3-a456-426614174000',
        'A',
        NULL,
        NULL
    ),
    -- Estudiante 2
    (
        'FIMJ011004HOCGRLA8',
        'Joel',
        'Francisco',
        'Figueroa',
        'Martinez',
        '2001-10-04',
        1,
        'froste@gmail.com',
        '5018221525',
        '2025-02-25 17:47:03.618100',
        '123e4567-e89b-12d3-a456-426614174000',
        'A',
        NULL,
        NULL
    );

-- 3. Inserción en la tabla administrators
INSERT INTO
    `administrators` (
        `employee_number`,
        `fk_person`,
        `fk_user`,
        `created_at`,
        `created_by`,
        `status_key`,
        `updated_at`,
        `updated_by`
    )
VALUES
    (
        'E12345',
        'ABCD901215HDFRRN08',
        '8d3213f2-362c-4c1a-be17-10e774ba362d',
        '2025-02-25 17:47:03.642645',
        '123e4567-e89b-12d3-a456-426614174000',
        'A',
        NULL,
        NULL
    );

-- 4. Inserción en la tabla students
-- Estudiante 1 - Joel Figueroa
INSERT INTO
    `students` (
        `enrollment`,
        `fk_person`,
        `fk_user`,
        `created_at`,
        `created_by`,
        `status_key`,
        `updated_at`,
        `updated_by`
    )
VALUES
    (
        '2019060310',
        'FIMJ011004HOCGRLA8',
        'a1b2c3d4-e5f6-7g8h-9i10-j11k12l13m15',
        '2025-02-25 17:47:03.618100',
        '123e4567-e89b-12d3-a456-426614174000',
        'A',
        NULL,
        NULL
    );

-- Estudiante 2 - Juan Antonio
INSERT INTO
    `students` (
        `enrollment`,
        `fk_person`,
        `fk_user`,
        `created_at`,
        `created_by`,
        `status_key`,
        `updated_at`,
        `updated_by`
    )
VALUES
    (
        '2023070425',
        'AOPS011028HOCNRLA9',
        'a1b2c3d4-e5f6-7g8h-9i10-j11k12l13m14',
        '2025-02-25 17:47:03.618100',
        '123e4567-e89b-12d3-a456-426614174000',
        'A',
        NULL,
        NULL
    );