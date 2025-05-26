CREATE TABLE review_status (
    id_review_status BIGINT(20) AUTO_INCREMENT PRIMARY KEY,
    status VARCHAR(100) NOT NULL,
    message LONGTEXT DEFAULT NULL,
    fk_patient_clinical_history BIGINT(20) NOT NULL,
    fk_form_section VARCHAR(100) NOT NULL,
    fk_professor_clinical_area BIGINT(20) NOT NULL,
    created_at DATETIME(6) DEFAULT NULL,
    created_by VARCHAR(255) DEFAULT NULL,
    status_key VARCHAR(255) DEFAULT NULL,
    updated_at DATETIME(6) DEFAULT NULL,
    updated_by VARCHAR(255) DEFAULT NULL,
    FOREIGN KEY (fk_patient_clinical_history) REFERENCES patient_clinical_histories(id_patient_clinical_history),
    FOREIGN KEY (fk_form_section) REFERENCES form_sections (id_form_section),
    FOREIGN KEY (fk_professor_clinical_area) REFERENCES professor_clinical_areas(id_professor_clinical_area)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;