-- 1. Tabla principal para la evaluación periodontal global
CREATE TABLE
    periodontograms (
        id_periodontogram BIGINT PRIMARY KEY AUTO_INCREMENT,
        fk_patient CHAR(36) NOT NULL,
        plaque_index DECIMAL(5, 2),
        bleeding_index DECIMAL(5, 2),
        evaluation_date DATETIME NOT NULL,
        notes TEXT,
        created_at DATETIME (6) DEFAULT NULL,
        created_by VARCHAR(255) DEFAULT NULL,
        status_key VARCHAR(255) DEFAULT NULL,
        updated_at DATETIME (6) DEFAULT NULL,
        updated_by VARCHAR(255) DEFAULT NULL,
        fk_form_section BIGINT NOT NULL,
        FOREIGN KEY (fk_patient) REFERENCES patients (id_patient),
        CONSTRAINT FK_periodontograms_form_sections FOREIGN KEY (fk_form_section) REFERENCES form_sections (id_form_section),
        UNIQUE (fk_patient, fk_form_section)
    ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

-- 2. Tabla secundaria para registrar los datos de cada diente evaluado
CREATE TABLE
    tooth_evaluations (
        id_tooth_evaluation BIGINT PRIMARY KEY AUTO_INCREMENT,
        fk_periodontogram BIGINT NOT NULL,
        id_tooth VARCHAR(3) NOT NULL,
        mobility INT,
        created_at DATETIME (6) DEFAULT NULL,
        created_by VARCHAR(255) DEFAULT NULL,
        status_key VARCHAR(255) DEFAULT NULL,
        updated_at DATETIME (6) DEFAULT NULL,
        updated_by VARCHAR(255) DEFAULT NULL,
        CONSTRAINT FK_tooth_eval_periodontograms FOREIGN KEY (fk_periodontogram) REFERENCES periodontograms (id_periodontogram),
        CONSTRAINT FK_tooth_eval_teeth FOREIGN KEY (id_tooth) REFERENCES teeth (id_tooth)
    ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

-- 3. Tabla para las superficies de cada diente evaluado
CREATE TABLE
    surface_evaluations (
        id_surface_evaluation BIGINT PRIMARY KEY AUTO_INCREMENT,
        fk_tooth_evaluation BIGINT NOT NULL,
        surface ENUM (
            'VESTIBULAR',
            'PALATINO',
            'LINGUAL',
            'VESTIBULAR_INFERIOR'
        ) NOT NULL,
        created_at DATETIME (6) DEFAULT NULL,
        created_by VARCHAR(255) DEFAULT NULL,
        status_key VARCHAR(255) DEFAULT NULL,
        updated_at DATETIME (6) DEFAULT NULL,
        updated_by VARCHAR(255) DEFAULT NULL,
        FOREIGN KEY (fk_tooth_evaluation) REFERENCES tooth_evaluations (id_tooth_evaluation)
    ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

-- 4. Tabla para registrar las mediciones en cada superficie
CREATE TABLE
    surface_measurements (
        id_surface_measurement BIGINT PRIMARY KEY AUTO_INCREMENT,
        tooth_position ENUM ('MESIAL', 'CENTRAL', 'DISTAL') NOT NULL,
        pocket_depth DECIMAL(5, 2),
        lesion_level DECIMAL(5, 2),
        plaque BOOLEAN,
        bleeding BOOLEAN,
        calculus BOOLEAN,   
        created_at DATETIME (6) DEFAULT NULL,
        created_by VARCHAR(255) DEFAULT NULL,
        status_key VARCHAR(255) DEFAULT NULL,
        updated_at DATETIME (6) DEFAULT NULL,
        updated_by VARCHAR(255) DEFAULT NULL,
        fk_surface_evaluation BIGINT NOT NULL,
        FOREIGN KEY (fk_surface_evaluation) REFERENCES surface_evaluations (id_surface_evaluation)
    ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;