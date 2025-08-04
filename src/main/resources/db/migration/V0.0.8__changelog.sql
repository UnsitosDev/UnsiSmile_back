-- *-*-*-*-*-*-*-*-* (3) HISTORIA CLÍNICA DE PERIODONCIA *-*-*-*-*-*-*-*-*-

INSERT INTO
    form_sections (id_form_section, form_name)
VALUES
    ("HEP-01", "Hoja de evaluación de periodoncia"),
    ("P-01", "Periodontograma"),
    ("CCIP-01", "Carta de concentimiento informado para periodoncia")
;

INSERT INTO
    medical_record_sections (
    fk_medical_record_catalog,
    fk_form_section,
    section_order
)
VALUES
    (3, "SV-01", 1),
    (3, "I-01", 2),
    (3, "EP-01", 3),
    (3, "ECB-01", 4),
    (3, "PT-01", 5),
    (3, "R-01", 6),
    (3, "HEP-01", 7),
    (3, "P-01", 8),
    (3, "CCIPB-01", 9)
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
    ("Hoja de evalucación de periodoncia", "HEP-01", 6, 1, true);

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
    ("Carta de concentimiento informado", "CCIP-01", 6, 1, true);