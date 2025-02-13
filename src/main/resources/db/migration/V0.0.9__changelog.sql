-- *-*-*-*-*-*-*-*-* HISTORIA CLÍNICA DE OPERATORIA DENTAL *-*-*-*-*-*-*-*-*-
INSERT INTO
    form_sections (form_name)
VALUES
    ("Exploración de la cavidad bucal y anexos"),
    (
        "Carta de concentimiento informado para periodoncia"
    );

INSERT INTO
    clinical_history_sections (
    fk_clinical_history_catalog,
    fk_form_section,
    section_order
)
VALUES
    (4, 1, 1),
    (4, 21, 2),
    (4, 5, 3), -- antecedentes personales patológicos
    (4, 41, 4),
    (4, 28, 5), -- recibo
    (4, 42, 6)
-- CARTA DE CONSENTIMIENTO INFORMADO PARA OPERATORIA DENTAL
;

INSERT INTO
    questions (
    question_text,
    fk_form_section,
    fk_answer_type,
    question_order,
    required
)
values
    ("Tejidos blandos", 41, 8, 1, true),
    ("Articulación temporomandibular", 41,8, 2, true),
    ("Diagnóstico", 41,8, 3, true),
    ("Estudio de laboratorio y gabinete", 41,8, 4, true),
    ("Indicaciones de interconsulta médica u odontológica", 41,8, 5, true);

INSERT INTO
    questions (
    question_text,
    fk_form_section,
    fk_answer_type,
    question_order,
    required
)
values
    (
        "Carta de concentimiento informado para operatoria dental",
        42,
        6,
        1,
        true
    )
;