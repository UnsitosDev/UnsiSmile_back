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
    ("Archivos requeridos para autorización", 55, 7, 1, false, null);

CREATE TABLE refresh_tokens (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    token VARCHAR(255) NOT NULL UNIQUE,
    expiry_date TIMESTAMP NOT NULL,
    user_id CHAR(36) NOT NULL,
    CONSTRAINT FK_refresh_tokens_user FOREIGN KEY (user_id) REFERENCES user_app(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;