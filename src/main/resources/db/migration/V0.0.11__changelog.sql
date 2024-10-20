-- *-*-*-*-*-*-*-*-* HISTORIA CLÍNICA DE PRÓTESIS BUCAL *-*-*-*-*-*-*-*-*-
INSERT INTO
    answer_types (answer_type)
VALUES
    ("BROAD_DATE");

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
    ("Tipo de Sangrado") -- id: 10
;

INSERT INTO
    catalog_options (fk_catalog, option_name)
VALUES
    (10, "Normal"),
    (10, "Prolongado");

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
    (5, 45, 5),
    (5, 46, 6),
    (5, 47, 7)
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
    ("Fecha de última consulta médica (agregar motivo)", 44, 10, 1, true),
    ("Fecha de ultima consulta dental (agregar motivo)", 44, 10, 2, true),
    ("Si es mujer, ¿Está embarazada?", 44, 5, 3, true),
    ("Si es mujer, ¿Está lactando?", 44, 5, 5, true),
    ("Examen Radiológico", 44, 3, 6, true),
    ("Técnicas de Anestesia", 44, 3, 7, true),
    ("Instrumental", 44, 3, 8, true),
    ("Dientes Extraídos", 44, 2, 9, true),
    ("Estado del Paciente", 44, 3, 10, true)
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
    ("Tipo de Sangrado", 44, 4,10, 4, true); -- catalog

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
