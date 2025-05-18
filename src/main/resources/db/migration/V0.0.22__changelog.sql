-- Códigos de verificación
CREATE TABLE otp_tokens (
    id CHAR(36) PRIMARY KEY,
    code VARCHAR(6) NOT NULL,
    purpose VARCHAR(30) NOT NULL,            -- Tipo: 'password_reset', '2fa', 'email_verification'
    expires_at TIMESTAMP NOT NULL,
    is_used BOOLEAN DEFAULT FALSE NOT NULL,
    attempts TINYINT DEFAULT 0 NOT NULL,     -- Intentos fallidos

    email VARCHAR(255) NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    INDEX idx_code_email (code, email)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;