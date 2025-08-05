CREATE TABLE
    professors (
        id_professor VARCHAR(15) NOT NULL,
        fk_person VARCHAR(20) NOT NULL,
        fk_user CHAR(36) NOT NULL,
        fk_career VARCHAR(3) NOT NULL,
        created_at DATETIME (6) DEFAULT NULL,
        created_by VARCHAR(255) DEFAULT NULL,
        status_key VARCHAR(255) DEFAULT NULL,
        updated_at DATETIME (6) DEFAULT NULL,
        updated_by VARCHAR(255) DEFAULT NULL,
        PRIMARY KEY (id_professor),
        FOREIGN KEY (fk_person) REFERENCES people (curp),
        FOREIGN KEY (fk_user) REFERENCES user_app (id),
        FOREIGN KEY (fk_career) REFERENCES careers (id_career)
    ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

CREATE TABLE
    professor_groups (
        id_professor_group BIGINT (20) NOT NULL AUTO_INCREMENT,
        fk_professor VARCHAR(15) NOT NULL,
        fk_group BIGINT (20) DEFAULT NULL,
        created_at DATETIME (6) DEFAULT NULL,
        created_by VARCHAR(255) DEFAULT NULL,
        status_key VARCHAR(255) DEFAULT NULL,
        updated_at DATETIME (6) DEFAULT NULL,
        updated_by VARCHAR(255) DEFAULT NULL,
        PRIMARY KEY (id_professor_group),
        FOREIGN KEY (fk_professor) REFERENCES professors (id_professor),
        FOREIGN KEY (fk_group) REFERENCES groups (id_group)
    ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

CREATE TABLE
    clinical_areas (
        id_clinical_area BIGINT (20) NOT NULL AUTO_INCREMENT,
        clinical_area VARCHAR(100) NOT NULL,
        created_at DATETIME (6) DEFAULT NULL,
        created_by VARCHAR(255) DEFAULT NULL,
        status_key VARCHAR(255) DEFAULT NULL,
        updated_at DATETIME (6) DEFAULT NULL,
        updated_by VARCHAR(255) DEFAULT NULL,
        PRIMARY KEY (id_clinical_area)
    ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

CREATE TABLE
    professor_clinical_areas (
        id_professor_clinical_area BIGINT (20) NOT NULL AUTO_INCREMENT,
        fk_clinical_area BIGINT (20) NOT NULL,
        fk_professor VARCHAR(15) NOT NULL,
        start_date DATE NOT NULL,
        end_date DATE DEFAULT NULL,
        created_at DATETIME (6) DEFAULT NULL,
        created_by VARCHAR(255) DEFAULT NULL,
        status_key VARCHAR(255) DEFAULT NULL,
        updated_at DATETIME (6) DEFAULT NULL,
        updated_by VARCHAR(255) DEFAULT NULL,
        PRIMARY KEY (id_professor_clinical_area),
        FOREIGN KEY (fk_clinical_area) REFERENCES clinical_areas (id_clinical_area),
        FOREIGN KEY (fk_professor) REFERENCES professors (id_professor)
    ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

-- *-*-*-*-*-*-*-*-* carreras *-*-*-*-*-*-*-*-*-
INSERT INTO
    careers (id_career, career)
VALUES
    ("13", "Licenciatura en Odontología");

-- *-*-*-*-*-*-*-*-* ciclos *-*-*-*-*-*-*-*-*-
INSERT INTO
    cycles (cycle_name)
VALUES
    ("A"),
    ("B");

CREATE TABLE
    student_groups (
        id_student_groups BIGINT (20) NOT NULL AUTO_INCREMENT,
        fk_student VARCHAR(255) DEFAULT NULL,
        fk_group BIGINT (20) DEFAULT NULL,
        created_at DATETIME (6) DEFAULT NULL,
        created_by VARCHAR(255) DEFAULT NULL,
        status_key VARCHAR(255) DEFAULT NULL,
        updated_at DATETIME (6) DEFAULT NULL,
        updated_by VARCHAR(255) DEFAULT NULL,
        PRIMARY KEY (id_student_groups),
        FOREIGN KEY (fk_student) REFERENCES students (enrollment),
        FOREIGN KEY (fk_group) REFERENCES groups (id_group)
    ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

-- Poblado administrador
INSERT INTO
    user_app (
        id,
        password,
        status,
        username,
        first_login,
        role_id
    )
VALUES
    (
        UUID(),
        '$2a$10$2B5649TYQWLvAU0NLYAKkuIH5TR3tQyy2bd.UC4YiV7DB18cSR0Oi',
        1,
        '1696',
        1,
        1
    );

INSERT INTO
    people (
        curp,
        first_name,
        second_name,
        first_lastname,
        second_lastname,
        birth_date,
        fk_gender,
        email,
        phone,
        created_at,
        status_key
    )
values
    (
        'LORD990222MOCPYM02',
        'Damary',
        null,
        'López',
        'Reyes',
        '1999-02-22',
        2,
        'damm.lopr@gmail.com',
        '9511690241',
        CURRENT_TIMESTAMP(6),
        'A'
    );

INSERT INTO
    clinical_areas (clinical_area, status_key)
VALUES
    ('Clínica integral', 'A'),
    ('Geriatría', 'A'),
    ('Exodoncia', 'A');

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
        '1696',
        'LORD990222MOCPYM02',
        (SELECT id FROM user_app WHERE username = '1696'),
        CURRENT_TIMESTAMP(6),
        NULL,
        'A',
        NULL,
        NULL
    );

-- catalogo estado civil
INSERT INTO
    catalogs (catalog_name)
VALUES
    ("Estados civiles");