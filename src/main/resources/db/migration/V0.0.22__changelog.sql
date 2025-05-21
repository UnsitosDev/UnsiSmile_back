CREATE TABLE dean_index (
                      id_dean_index BIGINT(20) AUTO_INCREMENT PRIMARY KEY,
                      fk_treatment_detail BIGINT(20) NOT NULL,
                      created_at DATETIME(6) DEFAULT NULL,
                      created_by VARCHAR(255) DEFAULT NULL,
                      status_key VARCHAR(255) DEFAULT NULL,
                      updated_at DATETIME(6) DEFAULT NULL,
                      updated_by VARCHAR(255) DEFAULT NULL,
                      FOREIGN KEY (fk_treatment_detail) REFERENCES treatment_details (id_treatment_detail)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

CREATE TABLE dean_index_tooth_code (
                                 id_dean_index_tooth_code BIGINT(20) AUTO_INCREMENT PRIMARY KEY,
                                 fk_tooth VARCHAR(3) NOT NULL,
                                 code INT NOT NULL,
                                 fk_dean_index BIGINT(20) NOT NULL,
                                 created_at DATETIME(6) DEFAULT NULL,
                                 created_by VARCHAR(255) DEFAULT NULL,
                                 status_key VARCHAR(255) DEFAULT NULL,
                                 updated_at DATETIME(6) DEFAULT NULL,
                                 updated_by VARCHAR(255) DEFAULT NULL,
                                 FOREIGN KEY (fk_dean_index) REFERENCES dean_index (id_dean_index),
                                 FOREIGN KEY (fk_tooth) REFERENCES teeth(id_tooth)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;