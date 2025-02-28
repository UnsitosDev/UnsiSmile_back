CREATE TABLE progress_notes (
                                id_progress_note CHAR(36) NOT NULL,
                                url VARCHAR(255) NOT NULL,
                                extention VARCHAR(6) NOT NULL,
                                fk_patient CHAR(36) NOT NULL,
                                created_at DATETIME(6) DEFAULT NULL,
                                created_by VARCHAR(255) DEFAULT NULL,
                                status_key VARCHAR(255) DEFAULT NULL,
                                updated_at DATETIME(6) DEFAULT NULL,
                                updated_by VARCHAR(255) DEFAULT NULL,
                                PRIMARY KEY (id_progress_note),
                                FOREIGN KEY (fk_patient) REFERENCES patients(id_patient)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;
