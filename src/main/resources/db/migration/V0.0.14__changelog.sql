
-- *-*-*-*-*-*-*-*-* HISTORIA CLÍNICA DE ODONTOLOGÍA PREVENTIVA Y SALUD PÚBLICA *-*-*-*-*-*-*-*-*-
INSERT INTO
    form_sections (form_name)
VALUES
    ("Profilaxis Dental"), -- tiene hijos
    ("Profilaxis Dental - Sesiones"),
    ("Fluorosis"),
    ("Barniz o selladores de fosetas y fisuras"),
    ("Observaciones"); -- tiene hijos


INSERT INTO
    form_sections (form_name, fk_parent_section) 
VALUES  
    ("Indice de Higiene Oral Simplificado (IHOS)", 48),
    ("Profilaxis / Detartraje", 52);

INSERT INTO clinical_history_sections
(fk_clinical_history_catalog,
 fk_form_section,
 section_order)
VALUES
    (6, 48, 1),
    (6, 49, 2),
    (6, 50, 3),
    (6, 51, 3),
    (6, 52, 4);

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
    ("Diente 16", 53, 3, 1, false, "Código"),
    ("Diente 11", 53, 3, 2, false, "Código"),
    ("Diente 26", 53, 3, 3, false, "Código"),
    ("Diente 46", 53, 3, 4, false, "Código"),
    ("Diente 31", 53, 3, 5, false, "Código"),
    ("Diente 36", 53, 3, 6, false, "Código");

-- profilaxis dental sesiones 
INSERT INTO 
    questions (
    question_text,
    fk_form_section,
    fk_answer_type,
    question_order,
    required
)
VALUES
    ("Fotografias Sesiones", 49, 6, 1, false);
;

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
    ("Diente 13", 50, 3, 1, false, "Código"),
    ("Diente 12", 50, 3, 2, false, "Código"),
    ("Diente 11", 50, 3, 3, false, "Código"),
    ("Diente 21", 50, 3, 4, false, "Código"),
    ("Diente 22", 50, 3, 5, false, "Código"),
    ("Diente 23", 50, 3, 6, false, "Código");

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
    ("Autorización", 51, 3, 1, false, "Descripción"),
    ("Aislamiento relativo", 51, 3, 2, false, "Descripción"),
    ("Limpieza de superficie", 51, 3, 3, false, "Descripción"),
    ("Grabado", 51, 3, 4, false, "Descripción"),
    ("Lavado y secado", 51, 3, 5, false, "Descripción"),
    ("Sellador de foseta y fisura", 51, 3, 6, false, "Descripción"),
    ("Terminado", 51, 3, 7, false, "Descripción"),
    ("Fotografias Barniz o selladores de fosetas y fisuras", 51, 6, 8, false, null);

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
    ("Fecha Aplicación Tópica de Flúor ", 52, 9, 1, false, null),
    ("Nombre del operador", 52, 3, 2, false, null),
    ("Nombre del asistente", 52, 3, 3, false, null),
    ("Docente que autoriza", 52, 4, 4, true, 13);

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
    ("Enseño al paciente las caras teñidas por la pastilla reveladora", 54, 1, 1, false, null, null),
    ("Explico al paciente uso de la pastilla reveladora", 54, 1, 2, false, null, null),
    ("Elimino la placa dentobacteriana o cálculo de los dientes", 54, 1, 3, false, null, null),
    ("Explico al paciente técnica de cepillado", 54, 1, 4, false, null, null);

