CREATE TABLE status_clinical_history (
    id_status_clinical_history BIGINT(20) AUTO_INCREMENT PRIMARY KEY,
    status VARCHAR(100) NOT NULL,
    message LONGTEXT DEFAULT NULL,
    fk_patient_clinical_history BIGINT(20) NOT NULL,
    created_at DATETIME(6) DEFAULT NULL,
    created_by VARCHAR(255) DEFAULT NULL,
    status_key VARCHAR(255) DEFAULT NULL,
    updated_at DATETIME(6) DEFAULT NULL,
    updated_by VARCHAR(255) DEFAULT NULL,
    FOREIGN KEY (fk_patient_clinical_history) REFERENCES patient_clinical_histories(id_patient_clinical_history)
);
