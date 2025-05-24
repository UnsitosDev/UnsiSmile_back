-- *-*-*-*-*-*-*-*-* HISTORIA CLÍNICA DE PRÓTESIS BUCAL *-*-*-*-*-*-*-*-*-
INSERT INTO
    form_sections (id_form_section, form_name, requires_review)
VALUES
    ("IAS-01", "Interrogatorio por aparatos y sistemas", false),
    ("PA-01", "Padecimiento actual", false),
    ("RPIQ-01", "Recomendaciones previas a la intervensión quirúrgica", false),
    ("RDIQ-01", "Recomendaciones para despues de intervenciones quirúrgicas", false),
    ("CCICO-01", "Carta de consentimiento informado para cirugía oral", false)
;

INSERT INTO
    catalogs (catalog_name)
VALUES
    ("Tipo de Sangrado") -- id: 11
;

INSERT INTO
    catalog_options (fk_catalog, option_name, status_key)
VALUES
    (11, "Normal", "A"),
    (11, "Prolongado", "A");

INSERT INTO
    clinical_history_sections (
    fk_clinical_history_catalog,
    fk_form_section,
    section_order
)
VALUES
    (5, "SV-01", 1),
    (5, "AH-01", 2),
    (5, "IAS-01", 3),
    (5, "PA-01", 4),
    (5, "R-01", 5),
    (5, "RPIQ-01", 6),
    (5, "CCICO-01", 8)
;

-- interrogatorio por aparatos y sistemas
INSERT INTO
    questions (
    question_text,
    fk_form_section,
    fk_answer_type,
    question_order,
    required
)
values
    ("Aparato cardiovascular", "IAS-01", 3, 1, true),
    ("Aparato Digestivo", "IAS-01", 3, 2, true),
    ("Aparato Renal", "IAS-01", 3, 3, true),
    ("Sistema Nervioso", "IAS-01", 3, 4, true),
    ("Sistema Genital", "IAS-01", 3, 5, true)
;

-- padecimiento actual
INSERT INTO
    questions (
    question_text,
    fk_form_section,
    fk_answer_type,
    question_order,
    required
)
values
    ("Fecha de última consulta médica", "PA-01", 9, 1, true),
    ("Motivo de última consulta médica", "PA-01", 8, 2, true),
    ("Fecha de ultima consulta dental", "PA-01", 9, 3, true),
    ("Motivo de última consulta dental", "PA-01", 8, 4, true),
    ("Si es mujer, ¿Está embarazada?", "PA-01", 1, 5, false),
    ("Si es mujer, ¿Está lactando?", "PA-01", 1, 7, false),
    ("Examen Radiológico", "PA-01", 3, 8, true),
    ("Técnicas de Anestesia", "PA-01", 3, 9, true),
    ("Instrumental", "PA-01", 3, 10, true),
    ("Dientes Extraídos", "PA-01", 2, 11, true),
    ("Estado del Paciente", "PA-01", 3, 12, true)
;

INSERT INTO
    questions (
    question_text,
    fk_form_section,
    fk_answer_type,
    fk_catalog,
    question_order,
    required
)
VALUES
    ("Tipo de Sangrado", "PA-01", 4,11, 6, false); -- catalog

INSERT INTO
    questions (
    question_text,
    fk_form_section,
    fk_answer_type,
    question_order,
    required
)
values
    ("Recomendaciones previas a la intervensión quirúrgica", "RPIQ-01", 6, 1, true),
    ("Recomendaciones para despues de intervenciones quirúrgicas", "RDIQ-01", 6, 1, true),
    ("Carta de consentimiento informado para cirugía oral", "CCICO-01", 6, 1, true)
;
