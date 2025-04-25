CREATE TABLE
    professors (
        id_professor VARCHAR(15) NOT NULL,
        fk_person VARCHAR(20) NOT NULL,
        fk_user CHAR(36) NOT NULL,
        fk_career VARCHAR(3) NOT NULL,
        created_at DATETIME(6) DEFAULT NULL,
        created_by VARCHAR(255) DEFAULT NULL,
        status_key VARCHAR(255) DEFAULT NULL,
        updated_at DATETIME(6) DEFAULT NULL,
        updated_by VARCHAR(255) DEFAULT NULL,
        PRIMARY KEY (id_professor),
        FOREIGN KEY (fk_person) REFERENCES people (curp),
        FOREIGN KEY (fk_user) REFERENCES user_app (id),
        FOREIGN KEY (fk_career) REFERENCES careers (id_career)
    ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

CREATE TABLE
    professor_groups(
        id_professor_group BIGINT (20) NOT NULL AUTO_INCREMENT,
        fk_professor VARCHAR(15) NOT NULL,
        fk_group BIGINT(20) DEFAULT NULL,
        created_at DATETIME(6) DEFAULT NULL,
        created_by VARCHAR(255) DEFAULT NULL,
        status_key VARCHAR(255) DEFAULT NULL,
        updated_at DATETIME(6) DEFAULT NULL,
        updated_by VARCHAR(255) DEFAULT NULL,
        PRIMARY KEY (id_professor_group),
        FOREIGN KEY (fk_professor) REFERENCES professors (id_professor),
        FOREIGN KEY (fk_group) REFERENCES groups (id_group)
    ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

CREATE TABLE clinical_areas (
    id_clinical_area BIGINT(20) NOT NULL AUTO_INCREMENT,
    clinical_area VARCHAR(100) NOT NULL,
    created_at DATETIME(6) DEFAULT NULL,
    created_by VARCHAR(255) DEFAULT NULL,
    status_key VARCHAR(255) DEFAULT NULL,
    updated_at DATETIME(6) DEFAULT NULL,
    updated_by VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (id_clinical_area)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

CREATE TABLE professor_clinical_areas (
    id_professor_clinical_area BIGINT(20) NOT NULL AUTO_INCREMENT,
    fk_clinical_area BIGINT(20) NOT NULL,
    fk_professor VARCHAR(15) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE DEFAULT NULL,
    created_at DATETIME(6) DEFAULT NULL,
    created_by VARCHAR(255) DEFAULT NULL,
    status_key VARCHAR(255) DEFAULT NULL,
    updated_at DATETIME(6) DEFAULT NULL,
    updated_by VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (id_professor_clinical_area),
    FOREIGN KEY (fk_clinical_area) REFERENCES clinical_areas (id_clinical_area),
    FOREIGN KEY (fk_professor) REFERENCES professors (id_professor)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

-- Poblado profesores y áreas clínicas
INSERT INTO
    user_app (id, password, status, username, first_login, role_id)
VALUES ('ac04114a-5be2-4f2b-95f9-88e2badce85d', '$2a$10$4nOcZULVdDegRx.hWRkKduiHY36SXP6rEl5cySrnqJSvzPrWMc5Ki', 1, '1701', 0, 4),
       ('e1cc88bb-85d5-43bc-824d-83d5739cf071', '$2a$10$0YRsGWVqlAE3bGjKLldBheEFm4LZGUytm1lGiSlCYw8KjqgNIlwu.', 1, '1574', 0, 4),
       ('737a531d-7067-4b56-8528-c5c32ed79425', '$2a$10$VBTfBqVSdipn/LFTnEXZgugGX0og0jaqvSs0r3YmDUJdDhsTCniVG', 1, '1691', 0, 4),
       ('f3694095-748f-4a04-b5e0-272bb5558127', '$2a$10$cCAHlSXaOuEP/.hQStPFfeDzOFcWzNmT/7Fuezv0IyuHmDvRkRI1y', 1, '1709', 0, 4),
       ('413f36e6-11ac-457c-9aa5-b8f1e475f65a', '$2a$10$2B5649TYQWLvAU0NLYAKkuIH5TR3tQyy2bd.UC4YiV7DB18cSR0Oi', 1, '1696', 0, 4),
       ('53f0d8c9-dcd7-4384-af27-6599f352dfe9', '$2a$10$HN8vhrvPkFskQkYLdNAyeuzs2iIdD8LGTwF1Qv9maC6IAzoRcIJhu', 1, '1695', 0, 4);

INSERT INTO
    people (curp, first_name, second_name, first_lastname, second_lastname, birth_date, fk_gender, email, phone, status_key)
values ('JAHB850330MOCCRT06', 'Beatriz', null, 'Jacinto', 'Hernández', '1985-03-30', 2, 'beatrizjh85@gmail.com', '9512495995', 'A'),
       ('MOGL821019MOCRNR08', 'Laura', 'Alejandra', 'Mora', 'González', '1982-10-19', 2, 'lauraalejandramora82@gmail.com', '9514714787', 'A'),
       ('QUOM980619MDFRRY06', 'Mayra', 'Leticia', 'Quiroz', 'Ortega', '1998-06-19', 2, 'quirozleticia22@gmail.com', '5568767440', 'A'),
       ('JIST950918MMCMNN03', 'Tania', 'Madai', 'Jiménez', 'Sánchez', '1995-09-18', 2, 'madaysanchez1995@gmail.com', '5554736982', 'A'),
       ('LORD990222MOCPYM02', 'Damary', null, 'López', 'Reyes', '1999-02-22', 2, 'damm.lopr@gmail.com', '9511690241', 'A'),
       ('JUMP990823HOCRJD03', 'Pedro', 'Eleazar', 'Juárez', 'Mejía', '1999-08-23', 1, 'eleazmej10@gmail.com', '9513661237', 'A');

INSERT INTO
    professors (id_professor, fk_person, fk_user, fk_career, status_key)
values ('1701', 'JAHB850330MOCCRT06', 'ac04114a-5be2-4f2b-95f9-88e2badce85d', '13', 'A'),
       ('1574', 'MOGL821019MOCRNR08', 'e1cc88bb-85d5-43bc-824d-83d5739cf071', '13', 'A'),
       ('1691', 'QUOM980619MDFRRY06', '737a531d-7067-4b56-8528-c5c32ed79425', '13', 'A'),
       ('1709', 'JIST950918MMCMNN03', 'f3694095-748f-4a04-b5e0-272bb5558127', '13', 'A'),
       ('1696', 'LORD990222MOCPYM02', '413f36e6-11ac-457c-9aa5-b8f1e475f65a', '13', 'A'),
       ('1695', 'JUMP990823HOCRJD03', '53f0d8c9-dcd7-4384-af27-6599f352dfe9', '13', 'A');

INSERT INTO
    clinical_areas (clinical_area, status_key)
VALUES ('Clínica integral', 'A'),
       ('Geriatría', 'A'),
       ('Exodoncia', 'A');

INSERT INTO
    professor_clinical_areas (fk_clinical_area, fk_professor, start_date, status_key)
VALUES (1, '1696', '2025-04-16', 'A'),
       (1, '1695', '2025-04-16', 'A'),
       (1, '1691', '2025-04-16', 'A'),
       (1, '1701', '2025-04-16', 'A'),
       (1, '1574', '2025-04-16', 'A'),
       (2, '1709', '2025-04-16', 'A'),
       (3, '1691', '2025-04-16', 'A');

INSERT INTO
    catalog_options (fk_catalog, option_name, status_key)
VALUES
    (13, '1701', 'A'),
    (13, '1574', 'A'),
    (13, '1691', 'A'),
    (13, '1709', 'A'),
    (13, '1696', 'A'),
    (13, '1695', 'A');