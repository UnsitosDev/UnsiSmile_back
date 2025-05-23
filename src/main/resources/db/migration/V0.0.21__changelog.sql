-- Fluorosis
CREATE TABLE fluorosis (
    id_fluorosis BIGINT AUTO_INCREMENT PRIMARY KEY,
    fk_treatment_detail BIGINT(20) NOT NULL,
    created_at DATETIME(6) DEFAULT NULL,
    created_by VARCHAR(255) DEFAULT NULL,
    status_key VARCHAR(255) DEFAULT NULL,
    updated_at DATETIME(6) DEFAULT NULL,
    updated_by VARCHAR(255) DEFAULT NULL,
    FOREIGN KEY (fk_treatment_detail) REFERENCES treatment_details (id_treatment_detail)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

CREATE TABLE fluorosis_tooth_condition_assignments (
    fluorosis_id BIGINT,
    tooth_id VARCHAR(3),
    tooth_condition_id BIGINT,
    created_at DATETIME(6) DEFAULT NULL,
    created_by VARCHAR(255) DEFAULT NULL,
    status_key VARCHAR(255) DEFAULT NULL,
    updated_at DATETIME(6) DEFAULT NULL,
    updated_by VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (fluorosis_id, tooth_id, tooth_condition_id),
    FOREIGN KEY (fluorosis_id) REFERENCES fluorosis (id_fluorosis),
    FOREIGN KEY (tooth_id) REFERENCES teeth (id_tooth),
    FOREIGN KEY (tooth_condition_id) REFERENCES tooth_conditions (id_tooth_condition)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

CREATE TABLE fluorosis_toothface_conditions_assignments (
    fluorosis_id BIGINT,
    tooth_face_id VARCHAR(3),
    toothface_condition_id BIGINT,
    tooth_id VARCHAR(3),
    created_at DATETIME(6) DEFAULT NULL,
    created_by VARCHAR(255) DEFAULT NULL,
    status_key VARCHAR(255) DEFAULT NULL,
    updated_at DATETIME(6) DEFAULT NULL,
    updated_by VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (
                 fluorosis_id,
                 tooth_face_id,
                 toothface_condition_id,
                 tooth_id
                ),
    FOREIGN KEY (fluorosis_id) REFERENCES fluorosis (id_fluorosis),
    FOREIGN KEY (tooth_face_id) REFERENCES tooth_faces (id_tooth_face),
    FOREIGN KEY (toothface_condition_id) REFERENCES toothface_conditions (id_toothface_conditions),
    FOREIGN KEY (tooth_id) REFERENCES teeth (id_tooth)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

INSERT INTO form_component_tooth_conditions (fk_form_component, fk_tooth_condition)
VALUES
    (3, 3),
    (3, 13);

INSERT INTO form_component_tooth_faces (fk_form_component, fk_tooth_face)
VALUES
    (3, "1"),
    (3, "2"),
    (3, "3"),
    (3, "4"),
    (3, "5");

INSERT INTO form_component_toothface_conditions (fk_form_component, fk_toothface_condition)
VALUES
    (3, 5);