
-- *-*-*-*-*-*-*-*-* HISTORIA CLÍNICA DE ODONTOLOGÍA PREVENTIVA Y SALUD PÚBLICA *-*-*-*-*-*-*-*-*-
INSERT INTO
    form_sections (form_name)
VALUES
    ("Datos personales"),
    ("Profilaxis Dental"), -- tiene hijos
    ("Profilaxis Dental - Sesiones"),
    ("Fluorosis"),
    ("Barniz o selladores de fosetas y fisuras"),
    ("Observaciones"); -- tiene hijos


INSERT INTO
    form_sections (form_name, fk_parent_section) 
VALUES  
    ("Indice de Higiene Oral Simplificado (IHOS)", 49),
    ("Profilaxis / Detartraje", 53);

INSERT INTO clinical_history_sections
(fk_clinical_history_catalog,
 fk_form_section,
 section_order)
VALUES
    (6, 48, 1),
    (6, 49, 2),
    (6, 50, 3),
    (6, 51, 3),
    (6, 52, 4),
    (6, 53, 5);

-- catalogo estado civil

INSERT INTO catalogs
(catalog_name)
VALUES
    ("Estados civiles"),
    ("Catedráticos responsables de área");


-- datos personales
INSERT INTO 
    questions (
    question_text,
    fk_form_section,
    fk_answer_type,
    fk_catalog,
    question_order,
    required,
    placeholder
)
VALUES
    ("Estado civil de los padres (si es menor de edad)", 48, 4, 12, 1, false, null),
    ("Nombre del pediatra o médico familiar", 48, 3, null, 2, false, "Nombre");

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
    ("Diente 16", 54, 3, 1, false, "Código"),
    ("Diente 11", 54, 3, 2, false, "Código"),
    ("Diente 26", 54, 3, 3, false, "Código"),
    ("Diente 46", 54, 3, 4, false, "Código"),
    ("Diente 31", 54, 3, 5, false, "Código"),
    ("Diente 36", 54, 3, 6, false, "Código");

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
    ("Fotografias Sesiones", 50, 6, 1, false);
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
    ("Diente 13", 51, 3, 1, false, "Código"),
    ("Diente 12", 51, 3, 2, false, "Código"),
    ("Diente 11", 51, 3, 3, false, "Código"),
    ("Diente 21", 51, 3, 4, false, "Código"),
    ("Diente 22", 51, 3, 5, false, "Código"),
    ("Diente 23", 51, 3, 6, false, "Código");

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
    ("Autorización", 52, 3, 1, false, "Descripción"),
    ("Aislamiento relativo", 52, 3, 2, false, "Descripción"),
    ("Limpieza de superficie", 52, 3, 3, false, "Descripción"),
    ("Grabado", 52, 3, 4, false, "Descripción"),
    ("Lavado y secado", 52, 3, 5, false, "Descripción"),
    ("Sellador de foseta y fisura", 52, 3, 6, false, "Descripción"),
    ("Terminado", 52, 3, 7, false, "Descripción"),
     ("Fotografias Barniz o selladores de fosetas y fisuras", 52, 6, 8, false, null);

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
    ("Fecha Aplicación Tópica de Flúor ", 53, 9, 1, false, null),
    ("Nombre del operador", 53, 3, 2, false, null),
    ("Nombre del asistente", 53, 3, 3, false, null),
    ("Docente que autoriza", 53, 4, 4, true, 13);

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
    ("Enseño al paciente las caras teñidas por la pastilla reveladora", 55, 1, 1, false, null, null),
    ("Explico al paciente uso de la pastilla reveladora", 55, 1, 2, false, null, null),
    ("Elimino la placa dentobacteriana o cálculo de los dientes", 55, 1, 3, false, null, null),
    ("Explico al paciente técnica de cepillado", 55, 1, 4, false, null, null);

