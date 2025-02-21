-- Formatos de consentimiento y/o asentimiento informado
ALTER TABLE answers MODIFY COLUMN fk_patient CHAR(36) NULL;

INSERT INTO
    form_sections (form_name)
VALUES
    ("Formatos Legales y Consentimientos");
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
    ("Archivos requeridos para autorización", 56, 7, 1, false, null);
