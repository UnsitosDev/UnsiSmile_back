CREATE TABLE medical_record_digitizers (
    id_medical_record_digitizer BIGINT(20) AUTO_INCREMENT PRIMARY KEY,
    fk_student VARCHAR(255) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE DEFAULT NULL,

    created_at DATETIME(6) DEFAULT NULL,
    created_by VARCHAR(255) DEFAULT NULL,
    status_key VARCHAR(255) DEFAULT NULL,
    updated_at DATETIME(6) DEFAULT NULL,
    updated_by VARCHAR(255) DEFAULT NULL,

    FOREIGN KEY (fk_student) REFERENCES students(enrollment)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

CREATE TABLE digitizer_patients (
    id_digitizer_patient BIGINT(20) NOT NULL AUTO_INCREMENT,
    fk_patient CHAR(36) NOT NULL,
    fk_medical_record_digitizer BIGINT(20) NOT NULL,

    created_at DATETIME(6) DEFAULT NULL,
    created_by VARCHAR(255) DEFAULT NULL,
    status_key VARCHAR(255) DEFAULT NULL,
    updated_at DATETIME(6) DEFAULT NULL,
    updated_by VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (id_digitizer_patient),
    FOREIGN KEY (fk_patient) REFERENCES patients (id_patient),
    FOREIGN KEY (fk_medical_record_digitizer) REFERENCES medical_record_digitizers (id_medical_record_digitizer)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;