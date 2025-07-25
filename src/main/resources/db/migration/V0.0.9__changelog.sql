-- *-*-*-*-*-*-*-*-* HISTORIA CLÍNICA DE OPERATORIA DENTAL *-*-*-*-*-*-*-*-*-
INSERT INTO
    form_sections (id_form_section, form_name, requires_review)
VALUES
    ("ECBA-01", "Exploración de la cavidad bucal y anexos", false),
    ("E-01", "Evidencias", false),
    ("CCIOD-01", "Carta de consentimiento informado para operatoria dental", false);

INSERT INTO
    medical_record_sections (
    fk_medical_record_catalog,
    fk_form_section,
    section_order
)
VALUES
    (4, "SV-01", 1),
    (4, "I-01", 2),
    (4, "APP-02", 3),
    (4, "ECB-01", 4),
    (4, "PT-01", 5),
    (4, "R-01", 6),
    (4, "CCIOD-01", 7), -- CARTA DE CONSENTIMIENTO INFORMADO PARA OPERATORIA DENTAL
    (4, "E-01", 8)
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
    ("Tejidos blandos", "ECBA-01", 8, 1, true),
    ("Articulación temporomandibular", "ECBA-01",8, 2, true),
    ("Diagnóstico", "ECBA-01",8, 3, true),
    ("Estudio de laboratorio y gabinete", "ECBA-01",8, 4, true),
    ("Indicaciones de interconsulta médica u odontológica", "ECBA-01",8, 5, true),
    ("Evidencias visuales", "E-01", 6, 6, true);