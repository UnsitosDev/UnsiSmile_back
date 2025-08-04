
-- *-*-*-*-*-*-*-*-* HISTORIA CLÍNICA DE ODONTOLOGÍA PREVENTIVA Y SALUD PÚBLICA *-*-*-*-*-*-*-*-*-
INSERT INTO
    form_sections (id_form_section, form_name, requires_review)
VALUES
    ("PD-01", "Profilaxis Dental", false), -- tiene hijos
    ("F-01", "Fluorosis", false),
    ("BSFF-01", "Barniz o selladores de fosetas y fisuras", false),
    ("O-01", "Observaciones", false);-- tiene hijos


INSERT INTO
    form_sections (id_form_section, form_name, fk_parent_section, requires_review)
VALUES  
    ("IHOS-01", "Indice de Higiene Oral Simplificado (IHOS)", "PD-01", false);

INSERT INTO medical_record_sections
(fk_medical_record_catalog,
 fk_form_section,
 section_order)
VALUES
    (6, "PD-01", 1),
    (6, "F-01", 2),
    (6, "BSFF-01", 3),
    (6, "O-01", 4);

-- catalogo estado civil

INSERT INTO catalogs
(catalog_name)
VALUES
    ("Estados civiles");

INSERT INTO catalog_options
(fk_catalog, option_name, status_key)
VALUES
    (12, "Casados", "A"),
    (12, "Divorciados", "A"),
    (12, "Separados", "A"),
    (12, "Viudos", "A"),
    (12, "Unión libre", "A"),
    (12, "Solteros", "A");


-- profilaxis dental - Indice de Higiene Oral Simplificado (IHOS)
INSERT INTO 
    questions (
    question_text,
    fk_form_section,
    fk_answer_type,
    question_order,
    required,
    placeholder 
)
VALUES
    ("Diente 16", "IHOS-01", 3, 1, false, "Código"),
    ("Diente 11", "IHOS-01", 3, 2, false, "Código"),
    ("Diente 26", "IHOS-01", 3, 3, false, "Código"),
    ("Diente 46", "IHOS-01", 3, 4, false, "Código"),
    ("Diente 31", "IHOS-01", 3, 5, false, "Código"),
    ("Diente 36", "IHOS-01", 3, 6, false, "Código");

-- Fluorosis
INSERT INTO 
    questions (
    question_text,
    fk_form_section,
    fk_answer_type,
    question_order,
    required,
    placeholder 
)
VALUES
    ("Diente 13", "F-01", 3, 1, false, "Código"),
    ("Diente 12", "F-01", 3, 2, false, "Código"),
    ("Diente 11", "F-01", 3, 3, false, "Código"),
    ("Diente 21", "F-01", 3, 4, false, "Código"),
    ("Diente 22", "F-01", 3, 5, false, "Código"),
    ("Diente 23", "F-01", 3, 6, false, "Código");

-- BARNIZ O SELLADORES DE FOSETAS Y FISURAS
INSERT INTO 
    questions (
    question_text,
    fk_form_section,
    fk_answer_type,
    question_order,
    required,
    placeholder 
)
VALUES
    ("Autorización", "BSFF-01", 3, 1, false, "Descripción"),
    ("Aislamiento relativo", "BSFF-01", 3, 2, false, "Descripción"),
    ("Limpieza de superficie", "BSFF-01", 3, 3, false, "Descripción"),
    ("Grabado", "BSFF-01", 3, 4, false, "Descripción"),
    ("Lavado y secado", "BSFF-01", 3, 5, false, "Descripción"),
    ("Sellador de foseta y fisura", "BSFF-01", 3, 6, false, "Descripción"),
    ("Terminado", "BSFF-01", 3, 7, false, "Descripción"),
    ("Fotografias Barniz o selladores de fosetas y fisuras", "BSFF-01", 6, 8, false, "Descripción");

-- Observaciones

INSERT INTO 
    questions (
    question_text,
    fk_form_section,
    fk_answer_type,
    question_order,
    required,
    fk_catalog
)
VALUES
    ("Fecha Aplicación Tópica de Flúor ", "O-01", 9, 1, false, null),
    ("Nombre del operador", "O-01", 3, 2, false, null),
    ("Nombre del asistente", "O-01", 3, 3, false, null);

-- PROFILAXIS / DETARTRAJE
INSERT INTO questions
(question_text,
 fk_form_section,
 fk_answer_type,
 question_order,
 required,
 placeholder,
 fk_catalog)
VALUES
    ("Enseñó al paciente las caras teñidas por la pastilla reveladora", "IHOS-01", 1, 1, false, null, null),
    ("Explicó al paciente uso de la pastilla reveladora", "IHOS-01", 1, 2, false, null, null),
    ("Eliminó la placa dentobacteriana o cálculo de los dientes", "IHOS-01", 1, 3, false, null, null),
    ("Explicó al paciente técnica de cepillado", "IHOS-01", 1, 4, false, null, null);

