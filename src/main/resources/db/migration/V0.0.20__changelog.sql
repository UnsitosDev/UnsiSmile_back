INSERT INTO
    form_sections (form_name, requires_review)
VALUES ("Profilaxis dental", false);

INSERT INTO
    questions (question_text, fk_form_section, fk_answer_type, question_order, required)
values("Sección contestada", 56, 3, 1, true);

INSERT INTO
    tooth_conditions (description)
values ("Diente no presente"), -- profilaxis
       ("Resto radicular"); -- odontograma

INSERT INTO
    toothface_conditions (description)
values ("Marcado");

-- Profilaxis dental
CREATE TABLE dental_prophylaxis (
    id_dental_prophylaxis BIGINT AUTO_INCREMENT PRIMARY KEY,
    fk_patient CHAR(36) NOT NULL,
    fk_form_section BIGINT NOT NULL,
    created_at DATETIME(6) DEFAULT NULL,
    created_by VARCHAR(255) DEFAULT NULL,
    status_key VARCHAR(255) DEFAULT NULL,
    updated_at DATETIME(6) DEFAULT NULL,
    updated_by VARCHAR(255) DEFAULT NULL,
    CONSTRAINT FK_dental_prophylaxis_patients FOREIGN KEY (fk_patient) REFERENCES patients (id_patient),
    CONSTRAINT FK_dental_prophylaxis_form_sections FOREIGN KEY (fk_form_section) REFERENCES form_sections (id_form_section)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

CREATE TABLE prophylaxis_tooth_condition_assignments (
    dental_prophylaxis_id BIGINT,
    tooth_id VARCHAR(3),
    tooth_condition_id BIGINT,
    created_at DATETIME(6) DEFAULT NULL,
    created_by VARCHAR(255) DEFAULT NULL,
    status_key VARCHAR(255) DEFAULT NULL,
    updated_at DATETIME(6) DEFAULT NULL,
    updated_by VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (dental_prophylaxis_id, tooth_id, tooth_condition_id),
    FOREIGN KEY (dental_prophylaxis_id) REFERENCES dental_prophylaxis (id_dental_prophylaxis),
    FOREIGN KEY (tooth_id) REFERENCES teeth (id_tooth),
    FOREIGN KEY (tooth_condition_id) REFERENCES tooth_conditions (id_tooth_condition)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

CREATE TABLE prophylaxis_toothface_conditions_assignments (
    dental_prophylaxis_id BIGINT,
    tooth_face_id VARCHAR(3),
    toothface_condition_id BIGINT,
    tooth_id VARCHAR(3),
    created_at DATETIME(6) DEFAULT NULL,
    created_by VARCHAR(255) DEFAULT NULL,
    status_key VARCHAR(255) DEFAULT NULL,
    updated_at DATETIME(6) DEFAULT NULL,
    updated_by VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (
                 dental_prophylaxis_id,
                 tooth_face_id,
                 toothface_condition_id,
                 tooth_id
                ),
    FOREIGN KEY (dental_prophylaxis_id) REFERENCES dental_prophylaxis (id_dental_prophylaxis),
    FOREIGN KEY (tooth_face_id) REFERENCES tooth_faces (id_tooth_face),
    FOREIGN KEY (toothface_condition_id) REFERENCES toothface_conditions (id_toothface_conditions),
    FOREIGN KEY (tooth_id) REFERENCES teeth (id_tooth)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

INSERT INTO form_component_tooth_conditions (fk_form_component, fk_tooth_condition)
VALUES
    (2, 13),
    (2, 3);

INSERT INTO form_component_tooth_faces (fk_form_component, fk_tooth_face)
VALUES
    (2, "1"),
    (2, "2"),
    (2, "3"),
    (2, "4");

INSERT INTO form_component_toothface_conditions (fk_form_component, fk_toothface_condition)
VALUES
    (2, 5);

INSERT INTO form_component_tooth_conditions (fk_form_component, fk_tooth_condition)
VALUES
    (1, 14); -- Nueva condición para el odontograma