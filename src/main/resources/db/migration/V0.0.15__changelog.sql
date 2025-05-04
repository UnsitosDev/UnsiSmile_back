-- Tratamientos
CREATE TABLE treatment_scopes (
    id_treatment_scope BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    created_at DATETIME(6) DEFAULT NULL,
    created_by VARCHAR(255) DEFAULT NULL,
    status_key VARCHAR(255) DEFAULT NULL,
    updated_at DATETIME(6) DEFAULT NULL,
    updated_by VARCHAR(255) DEFAULT NULL
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

CREATE TABLE treatments (
    id_treatment BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    fk_treatment_scope BIGINT(20) NOT NULL,
    cost DECIMAL(10,2) DEFAULT NULL,
    created_at DATETIME(6) DEFAULT NULL,
    created_by VARCHAR(255) DEFAULT NULL,
    status_key VARCHAR(255) DEFAULT NULL,
    updated_at DATETIME(6) DEFAULT NULL,
    updated_by VARCHAR(255) DEFAULT NULL,
    fk_clinical_history_catalog BIGINT(20) NOT NULL,
    FOREIGN KEY (fk_treatment_scope) REFERENCES treatment_scopes(id_treatment_scope),
    FOREIGN KEY (fk_clinical_history_catalog) REFERENCES clinical_history_catalogs(id_clinical_history_catalog)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

CREATE TABLE treatment_details (
    id_treatment_detail BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    fk_patient_clinical_history BIGINT(20) NOT NULL,
    fk_treatment BIGINT(20) NOT NULL,
    start_date DATETIME(6) NOT NULL,
    end_date DATETIME(6) DEFAULT NULL,

    fk_student_group BIGINT(20) NOT NULL, -- 'Para saber el nombre del alumno y el grupo donde se encontraba durante el tratamiento'
    fk_professor VARCHAR(15) DEFAULT NULL, -- 'Firma y nombre del profesor a cargo',
    status VARCHAR(50) DEFAULT NULL,

    created_at DATETIME(6) DEFAULT NULL,
    created_by VARCHAR(255) DEFAULT NULL,
    status_key VARCHAR(255) DEFAULT NULL,
    updated_at DATETIME(6) DEFAULT NULL,
    updated_by VARCHAR(255) DEFAULT NULL,
    FOREIGN KEY (fk_patient_clinical_history) REFERENCES patient_clinical_histories(id_patient_clinical_history),
    FOREIGN KEY (fk_treatment) REFERENCES treatments(id_treatment),
    FOREIGN KEY (fk_professor) REFERENCES professors(id_professor)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

CREATE TABLE treatment_detail_teeth (
    id_detail_tooth BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    fk_treatment_detail BIGINT(20) NOT NULL,
    fk_tooth VARCHAR(3) NOT NULL,
    created_at DATETIME(6) DEFAULT NULL,
    created_by VARCHAR(255) DEFAULT NULL,
    status_key VARCHAR(255) DEFAULT NULL,
    updated_at DATETIME(6) DEFAULT NULL,
    updated_by VARCHAR(255) DEFAULT NULL,
    FOREIGN KEY (fk_treatment_detail) REFERENCES treatment_details(id_treatment_detail),
    FOREIGN KEY (fk_tooth) REFERENCES teeth(id_tooth)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;



-- Poblado de treatment_scopes
INSERT INTO treatment_scopes (name) VALUES
    ('Diente'), 
    ('Cuadrante'), 
    ('Prótesis'), 
    ('General');

-- Poblado de treatments
INSERT INTO treatments (name, fk_treatment_scope, fk_clinical_history_catalog) VALUES
-- Operatoria dental
('Resinas', 1, 4),  -- Órgano dentario, HC Operatoria dental

-- Preventiva
('Profilaxis', 4, 6),          -- General, HC Preventiva
('Fluorosis', 4, 6),           -- General, HC Preventiva
('Selladores de fosetas y fisuras', 4, 6), -- General, HC Preventiva

-- Cirugía
('Exodoncias', 1, 5),          -- Órgano dentario, HC Cirugía bucal

-- Prótesis
('Prótesis removible', 3, 2), -- Por prótesis, HC Prótesis bucal
('Prótesis fija', 3, 2),      -- Por prótesis, HC Prótesis bucal
('Prostodoncia', 3, 2),       -- Por prótesis, HC Prótesis bucal

-- Endodoncia
('Endodoncias', 1, 7),        -- Órgano dentario, HC Endodoncia (nueva)

-- Periodoncia
('Raspado y alisado', 2, 3),  -- Cuadrante, HC Periodoncia
('Cerrado y abierto', 1, 3),   -- Órgano dentario, HC Periodoncia
('Cuña distal', 1, 3),         -- Órgano dentario, HC Periodoncia

-- Pulpotomía/Pulpectomía (nuevas HC)
('Pulpotomía y corona', 1, 8),  -- Órgano dentario, HC Pulpotomía (nueva)
('Pulpectomía y corona', 1, 9); -- Órgano dentario, HC Pulpectomía (nueva)
