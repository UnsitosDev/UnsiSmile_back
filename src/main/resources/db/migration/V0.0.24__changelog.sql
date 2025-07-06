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