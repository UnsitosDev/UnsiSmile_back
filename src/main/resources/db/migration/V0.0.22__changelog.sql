-- Archivos generales
CREATE TABLE general_files (
    id_file CHAR(36) PRIMARY KEY NOT NULL,
    url VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    extension VARCHAR(6) NOT NULL,
    created_at DATETIME(6),
    created_by VARCHAR(255) DEFAULT NULL,
    status_key VARCHAR(255) DEFAULT 'ACTIVE',
    updated_at DATETIME(6),
    updated_by VARCHAR(255) DEFAULT NULL
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;