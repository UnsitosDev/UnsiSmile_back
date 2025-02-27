-- *-*-*-*-*-*-*-*-* HISTORIA CLÍNICA DE PRÓTESIS BUCAL *-*-*-*-*-*-*-*-*-
INSERT INTO
    form_sections (form_name)
VALUES
    ("Interrogatorio por aparatos y sistemas"),
    ("Padecimiento actual"),
    ("Recomendaciones previas a la intervensión quirúrgica"),
    ("Recomendaciones para despues de intervenciones quirúrgicas"),
    ("Carta de consentimiento informado para cirugía oral")
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
    (5, 1, 1), 
    (5, 3, 2), 
    (5, 43, 3), 
    (5, 44, 4), 
    (5, 29, 5), 
    (5, 45, 6), 
    (5, 47, 8)
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
    ("Aparato cardiovascular", 43, 3, 1, true),
    ("Aparato Digestivo", 43, 3, 2, true),
    ("Aparato Renal", 43, 3, 3, true),
    ("Sistema Nervioso", 43, 3, 4, true),
    ("Sistema Genital", 43, 3, 5, true)
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
    ("Fecha de última consulta médica", 44, 9, 1, true),
    ("Motivo de última consulta médica", 44, 8, 2, true),
    ("Fecha de ultima consulta dental", 44, 9, 3, true),
    ("Motivo de última consulta dental", 44, 8, 4, true),
    ("Si es mujer, ¿Está embarazada?", 44, 1, 5, false),
    ("Si es mujer, ¿Está lactando?", 44, 1, 7, false),
    ("Examen Radiológico", 44, 3, 8, true),
    ("Técnicas de Anestesia", 44, 3, 9, true),
    ("Instrumental", 44, 3, 10, true),
    ("Dientes Extraídos", 44, 2, 11, true),
    ("Estado del Paciente", 44, 3, 12, true)
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
    ("Tipo de Sangrado", 44, 4,11, 6, false); -- catalog

INSERT INTO
    questions (
    question_text,
    fk_form_section,
    fk_answer_type,
    question_order,
    required
)
values
    ("Recomendaciones previas a la intervensión quirúrgica", 45, 6, 1, true),
    ("Recomendaciones para despues de intervenciones quirúrgicas", 46, 6, 1, true),
    ("Carta de consentimiento informado para cirugía oral", 47, 6, 1, true)
;
