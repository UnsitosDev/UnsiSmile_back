CREATE TABLE
    professors (
        id_professor VARCHAR(15) NOT NULL,
        fk_person VARCHAR(20) NOT NULL,
        fk_user CHAR(36) NOT NULL,
        fk_career VARCHAR(3) NOT NULL,
        created_at DATETIME(6) DEFAULT NULL,
        created_by VARCHAR(255) DEFAULT NULL,
        status_key VARCHAR(255) DEFAULT NULL,
        updated_at DATETIME(6) DEFAULT NULL,
        updated_by VARCHAR(255) DEFAULT NULL,
        PRIMARY KEY (id_professor),
        FOREIGN KEY (fk_person) REFERENCES people (curp),
        FOREIGN KEY (fk_user) REFERENCES user_app (id),
        FOREIGN KEY (fk_career) REFERENCES careers (id_career)
    ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

CREATE TABLE
    professor_groups(
        id_professor_groups BIGINT (20) NOT NULL AUTO_INCREMENT,
        fk_professor VARCHAR(15) NOT NULL,
        fk_group BIGINT(20) DEFAULT NULL,
        created_at DATETIME(6) DEFAULT NULL,
        created_by VARCHAR(255) DEFAULT NULL,
        status_key VARCHAR(255) DEFAULT NULL,
        updated_at DATETIME(6) DEFAULT NULL,
        updated_by VARCHAR(255) DEFAULT NULL,
        PRIMARY KEY (id_professor_groups),
        FOREIGN KEY (fk_professor) REFERENCES professors (id_professor),
        FOREIGN KEY (fk_group) REFERENCES groups (id_group)
    ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

CREATE TABLE clinical_areas (
    id_clinical_area BIGINT(20) NOT NULL AUTO_INCREMENT,
    clinical_area VARCHAR(100) NOT NULL,
    created_at DATETIME(6) DEFAULT NULL,
    created_by VARCHAR(255) DEFAULT NULL,
    status_key VARCHAR(255) DEFAULT NULL,
    updated_at DATETIME(6) DEFAULT NULL,
    updated_by VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (id_clinical_area)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

CREATE TABLE professor_responsibility (
    id_professor_responsibility BIGINT(20) NOT NULL AUTO_INCREMENT,
    fk_clinical_area BIGINT(20) NOT NULL,
    fk_professor VARCHAR(15) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE DEFAULT NULL,
    created_at DATETIME(6) DEFAULT NULL,
    created_by VARCHAR(255) DEFAULT NULL,
    status_key VARCHAR(255) DEFAULT NULL,
    updated_at DATETIME(6) DEFAULT NULL,
    updated_by VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (id_professor_responsibility),
    FOREIGN KEY (fk_clinical_area) REFERENCES clinical_areas (id_clinical_area),
    FOREIGN KEY (fk_professor) REFERENCES professors (id_professor)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;
