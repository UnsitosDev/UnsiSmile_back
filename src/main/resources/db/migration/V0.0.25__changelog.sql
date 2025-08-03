CREATE TABLE
    medical_administrators (
                       employee_number VARCHAR(15) NOT NULL,
                       fk_person VARCHAR(20) NOT NULL,
                       fk_user CHAR(36) NOT NULL,
                       created_at DATETIME (6) DEFAULT NULL,
                       created_by VARCHAR(255) DEFAULT NULL,
                       status_key VARCHAR(255) DEFAULT NULL,
                       updated_at DATETIME (6) DEFAULT NULL,
                       updated_by VARCHAR(255) DEFAULT NULL,
                       PRIMARY KEY (employee_number),
                       FOREIGN KEY (fk_user) REFERENCES user_app (id),
                       FOREIGN KEY (fk_person) REFERENCES people (curp)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;