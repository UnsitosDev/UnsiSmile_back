-- Tratamientos
CREATE TABLE scope_types (
    id_scope_type BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    created_at DATETIME(6) DEFAULT NULL,
    created_by VARCHAR(255) DEFAULT NULL,
    status_key VARCHAR(255) DEFAULT NULL,
    updated_at DATETIME(6) DEFAULT NULL,
    updated_by VARCHAR(255) DEFAULT NULL
);

CREATE TABLE treatment_scopes (
    id_scope BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    fk_scope_type BIGINT(20) NOT NULL,
    scope_name VARCHAR(100) NOT NULL,
    created_at DATETIME(6) DEFAULT NULL,
    created_by VARCHAR(255) DEFAULT NULL,
    status_key VARCHAR(255) DEFAULT NULL,
    updated_at DATETIME(6) DEFAULT NULL,
    updated_by VARCHAR(255) DEFAULT NULL,
    FOREIGN KEY (fk_scope_type) REFERENCES scope_types(id_scope_type)
);

CREATE TABLE treatments (
    id_treatment BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    fk_treatment_scope BIGINT(20) NOT NULL,
    cost DECIMAL(10,2),
    created_at DATETIME(6) DEFAULT NULL,
    created_by VARCHAR(255) DEFAULT NULL,
    status_key VARCHAR(255) DEFAULT NULL,
    updated_at DATETIME(6) DEFAULT NULL,
    updated_by VARCHAR(255) DEFAULT NULL,
    fk_clinical_history_catalog BIGINT(20) DEFAULT NULL,
    FOREIGN KEY (fk_treatment_scope) REFERENCES treatment_scopes(id_scope),
    FOREIGN KEY (fk_clinical_history_catalog) REFERENCES clinical_history_catalogs(id_clinical_history_catalog)
);

CREATE TABLE treatment_details (
    id_treatment_detail BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    fk_patient_clinical_history BIGINT(20) NOT NULL,
    fk_treatment BIGINT(20) NOT NULL,
    fk_treatment_scope BIGINT(20) DEFAULT NULL,
    treatment_date DATETIME(6) NOT NULL,

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
    FOREIGN KEY (fk_treatment_scope) REFERENCES treatment_scopes(id_scope),
    FOREIGN KEY (fk_professor) REFERENCES professors(id_professor)
);

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
);

-- Agregar las HC que faltan según los tratamientos listados
INSERT INTO clinical_history_catalogs (clinical_history_name) VALUES
    ('Endodoncia'),
    ('Pulpotomía'),
    ('Pulpectomía');
-- Poblado de scope_types (ya está hecho en tu modelo inicial)
INSERT INTO scope_types (name) VALUES                               
    ('Diente'), 
    ('Cuadrante'), 
    ('Prótesis'), 
    ('General');

INSERT INTO treatment_scopes (fk_scope_type, scope_name)
VALUES
    (1, 'Diente'),
    (2, 'Cuadrante 1'),
    (2, 'Cuadrante 2'),
    (2, 'Cuadrante 3'),
    (2, 'Cuadrante 4'),
    (3, 'Prótesis removible'),
    (3, 'Prótesis fija'),
    (3, 'Prostodoncia'),
    (4, 'General');

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