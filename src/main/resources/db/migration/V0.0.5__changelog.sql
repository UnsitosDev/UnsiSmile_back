CREATE TABLE
    odontograms (
        id_odontogram BIGINT AUTO_INCREMENT PRIMARY KEY,
        fk_patient CHAR(36) NOT NULL,
        fk_form_section BIGINT NOT NULL unique,
        created_at DATETIME (6) DEFAULT NULL,
        created_by VARCHAR(255) DEFAULT NULL,
        status_key VARCHAR(255) DEFAULT NULL,
        updated_at DATETIME (6) DEFAULT NULL,
        updated_by VARCHAR(255) DEFAULT NULL,
        CONSTRAINT FK_odontograms_patients FOREIGN KEY (fk_patient) REFERENCES patients (id_patient),
        CONSTRAINT FK_odontograms_form_sections FOREIGN KEY (fk_form_section) REFERENCES form_sections (id_form_section)
    ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

CREATE TABLE
    tooth_faces (
        id_tooth_face VARCHAR(3) PRIMARY KEY,
        description VARCHAR(255) NOT NULL,
        created_at DATETIME (6) DEFAULT NULL,
        created_by VARCHAR(255) DEFAULT NULL,
        status_key VARCHAR(255) DEFAULT NULL,
        updated_at DATETIME (6) DEFAULT NULL,
        updated_by VARCHAR(255) DEFAULT NULL
    );

CREATE TABLE
    teeth (
        id_tooth VARCHAR(3) PRIMARY KEY,
        is_adult BOOLEAN,
        description VARCHAR(255) NOT NULL,
        created_at DATETIME (6) DEFAULT NULL,
        created_by VARCHAR(255) DEFAULT NULL,
        status_key VARCHAR(255) DEFAULT NULL,
        updated_at DATETIME (6) DEFAULT NULL,
        updated_by VARCHAR(255) DEFAULT NULL
    );

CREATE TABLE
    tooth_conditions (
        id_tooth_condition BIGINT AUTO_INCREMENT PRIMARY KEY,
        description VARCHAR(50) NOT NULL,
        created_at DATETIME (6) DEFAULT NULL,
        created_by VARCHAR(255) DEFAULT NULL,
        status_key VARCHAR(255) DEFAULT NULL,
        updated_at DATETIME (6) DEFAULT NULL,
        updated_by VARCHAR(255) DEFAULT NULL
    );

CREATE TABLE
    tooth_condition_assignments (
        odontogram_id BIGINT,
        tooth_id VARCHAR(3),
        tooth_condition_id BIGINT,
        created_at DATETIME (6) DEFAULT NULL,
        created_by VARCHAR(255) DEFAULT NULL,
        status_key VARCHAR(255) DEFAULT NULL,
        updated_at DATETIME (6) DEFAULT NULL,
        updated_by VARCHAR(255) DEFAULT NULL,
        PRIMARY KEY (odontogram_id, tooth_id, tooth_condition_id),
        FOREIGN KEY (odontogram_id) REFERENCES odontograms (id_odontogram),
        FOREIGN KEY (tooth_id) REFERENCES teeth (id_tooth),
        FOREIGN KEY (tooth_condition_id) REFERENCES tooth_conditions (id_tooth_condition)
    );

CREATE TABLE
    toothface_conditions (
        id_toothface_conditions BIGINT AUTO_INCREMENT PRIMARY KEY,
        description VARCHAR(50) NOT NULL,
        created_at DATETIME (6) DEFAULT NULL,
        created_by VARCHAR(255) DEFAULT NULL,
        status_key VARCHAR(255) DEFAULT NULL,
        updated_at DATETIME (6) DEFAULT NULL,
        updated_by VARCHAR(255) DEFAULT NULL
    );

CREATE TABLE
    toothface_conditions_assignments (
        odontogram_id BIGINT,
        tooth_face_id VARCHAR(3),
        toothface_condition_id BIGINT,
        tooth_id VARCHAR(3),
        created_at DATETIME (6) DEFAULT NULL,
        created_by VARCHAR(255) DEFAULT NULL,
        status_key VARCHAR(255) DEFAULT NULL,
        updated_at DATETIME (6) DEFAULT NULL,
        updated_by VARCHAR(255) DEFAULT NULL,
        PRIMARY KEY (
            odontogram_id,
            tooth_face_id,
            toothface_condition_id,
            tooth_id
        ),
        FOREIGN KEY (odontogram_id) REFERENCES odontograms (id_odontogram),
        FOREIGN KEY (tooth_face_id) REFERENCES tooth_faces (id_tooth_face),
        FOREIGN KEY (toothface_condition_id) REFERENCES toothface_conditions (id_toothface_conditions),
        FOREIGN KEY (tooth_id) REFERENCES teeth (id_tooth)
    );

INSERT INTO
    tooth_conditions (description)
VALUES
    ('Prótesis removible'),
    ('Puente'),
    ('Diente extraido'),
    ('Fístula'),
    ('Diente con fluorosis'),
    ('Diente con hipoplasia'),
    ('Diente en mal posición derecha'),
    ('Diente en mal posición izquierda');

INSERT INTO
    toothface_conditions (description)
values
    ('Diente parcialmente erupcionado'),
    ('Diente con corona'),
    ('Mantenedor de espacio con corona'),
    ('Mantenedor de espacio con banda'),
    ('Diente obturado con caries'),
    ('Diente obturado'),
    ('Diente cariado'),
    ('Diente con fractura');

INSERT INTO
    teeth (`description`, `is_adult`, `id_tooth`)
VALUES
    (1, TRUE, '18'),
    (2, TRUE, '17'),
    (3, TRUE, '16'),
    (4, TRUE, '15'),
    (5, TRUE, '14'),
    (6, TRUE, '13'),
    (7, TRUE, '12'),
    (8, TRUE, '11'),
    (9, TRUE, '28'),
    (10, TRUE, '27'),
    (11, TRUE, '26'),
    (12, TRUE, '25'),
    (13, TRUE, '24'),
    (14, TRUE, '23'),
    (15, TRUE, '22'),
    (16, TRUE, '21'),
    (17, TRUE, '38'),
    (18, TRUE, '37'),
    (19, TRUE, '36'),
    (20, TRUE, '35'),
    (21, TRUE, '34'),
    (22, TRUE, '33'),
    (23, TRUE, '32'),
    (24, TRUE, '31'),
    (25, TRUE, '48'),
    (26, TRUE, '47'),
    (27, TRUE, '46'),
    (28, TRUE, '45'),
    (29, TRUE, '44'),
    (30, TRUE, '43'),
    (31, TRUE, '42'),
    (32, TRUE, '41'),
    (33, FALSE, '55'),
    (34, FALSE, '54'),
    (35, FALSE, '53'),
    (36, FALSE, '52'),
    (37, FALSE, '51'),
    (38, FALSE, '65'),
    (39, FALSE, '64'),
    (40, FALSE, '63'),
    (41, FALSE, '62'),
    (42, FALSE, '61'),
    (43, FALSE, '75'),
    (44, FALSE, '74'),
    (45, FALSE, '73'),
    (46, FALSE, '72'),
    (47, FALSE, '71'),
    (48, FALSE, '85'),
    (49, FALSE, '84'),
    (50, FALSE, '83'),
    (51, FALSE, '82'),
    (52, FALSE, '81');

INSERT INTO
    tooth_faces (`id_tooth_face`, `description`)
VALUES
    ('1', "medio"),
    ('2', "arriba"),
    ('3', "derecha"),
    ('4', "abajo"),
    ('5', "izquierda");