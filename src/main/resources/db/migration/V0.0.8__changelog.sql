-- *-*-*-*-*-*-*-*-* (3) HISTORIA CLÍNICA DE PERIODONCIA *-*-*-*-*-*-*-*-*-

INSERT INTO
    form_sections (form_name)
VALUES
    ("Hoja de evaluación de periodoncia"),
    ("Periodontograma"),
    ("Carta de concentimiento informado para periodoncia")
;

INSERT INTO
    clinical_history_sections (
    fk_clinical_history_catalog,
    fk_form_section,
    section_order
)
VALUES
    (3, 20, 1),
    (3, 21, 2),
    (3, 22, 3),
    (3, 23, 4),
    (3, 27, 5),
    (3, 28, 6),
    (3, 38, 7),
    (3, 39, 8),
    (3, 40, 9)
;

-- HOJA DE EVALUACIÓN DE PERIODONCIA
INSERT INTO
    questions (
    question_text,
    fk_form_section,
    fk_answer_type,
    question_order,
    required
)
values
    ("Hoja de evalucación de periodoncia",38,6,1,true);

-- CARTA DE CONSENTIMIENTO INFORMADO PARA PERIODONCIA
INSERT INTO
    questions (
    question_text,
    fk_form_section,
    fk_answer_type,
    question_order,
    required
)
values
    ("Carta de concentimiento informado",40,6,1,true);