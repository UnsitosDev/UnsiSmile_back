CREATE TABLE progress_notes (
                                id_progress_note CHAR(36) NOT NULL,
                                fk_patient CHAR(36) NOT NULL,
                                blood_pressure VARCHAR(10) NOT NULL,
                                temperature DECIMAL(5,2) NOT NULL,
                                heart_rate INT NOT NULL,
                                respiratory_rate INT NOT NULL,
                                oxygen_saturation DECIMAL(5,2) NOT NULL,
                                diagnosis TEXT NOT NULL,
                                prognosis TEXT NOT NULL,
                                treatment LONGTEXT NOT NULL,
                                indications LONGTEXT DEFAULT NULL,
                                fk_professor VARCHAR(15) NOT NULL,
                                created_at DATETIME(6),
                                created_by VARCHAR(255) DEFAULT NULL,
                                status_key VARCHAR(255) DEFAULT NULL,
                                updated_at DATETIME(6),
                                updated_by VARCHAR(255) DEFAULT NULL,
                                PRIMARY KEY (id_progress_note),
                                FOREIGN KEY (fk_patient) REFERENCES patients(id_patient),
                                FOREIGN KEY (fk_professor) REFERENCES professors(id_professor)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

CREATE TABLE progress_note_files (
                                     id_progress_note_file CHAR(36) PRIMARY KEY NOT NULL,
                                     url VARCHAR(255) NOT NULL,
                                     extension VARCHAR(6) NOT NULL,
                                     fk_progress_note CHAR(36) NOT NULL,
                                     CONSTRAINT fk_progress_note FOREIGN KEY (fk_progress_note) REFERENCES progress_notes(id_progress_note)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;