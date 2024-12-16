CREATE TABLE clinical_history_catalogs (
                                           id_clinical_history_catalog bigint(20) NOT NULL AUTO_INCREMENT,
                                           clinical_history_name varchar(100) NOT NULL,
                                           PRIMARY KEY (id_clinical_history_catalog)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- FormSections Table
CREATE TABLE form_sections (
                               id_form_section BIGINT(20) NOT NULL AUTO_INCREMENT,
                               form_name VARCHAR(100) NOT NULL,
                               fk_parent_section BIGINT(20) DEFAULT NULL,
                               PRIMARY KEY (id_form_section),
                               FOREIGN KEY (fk_parent_section) REFERENCES form_sections (id_form_section)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ClinicalHistorySections Table
CREATE TABLE clinical_history_sections (
                                           fk_clinical_history_catalog bigint(20) NOT NULL,
                                           fk_form_section bigint(20) NOT NULL,
                                           section_order bigint(20) DEFAULT null,
                                           PRIMARY KEY (fk_clinical_history_catalog, fk_form_section),
                                           KEY fk_form_section (fk_form_section),
                                           CONSTRAINT clinical_history_sections_ibfk_1 FOREIGN KEY (fk_clinical_history_catalog) REFERENCES clinical_history_catalogs (id_clinical_history_catalog),
                                           CONSTRAINT clinical_history_sections_ibfk_2 FOREIGN KEY (fk_form_section) REFERENCES form_sections (id_form_section)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Type answer Table
CREATE TABLE answer_types (
                              id_answer_type bigint(20) AUTO_INCREMENT,
                              answer_type VARCHAR(50) NOT NULL,
                              PRIMARY KEY (id_answer_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;;

-- Catalogs Table (for predefined answer catalogs)
CREATE TABLE catalogs (
                          id_catalog bigint(20) NOT NULL AUTO_INCREMENT,
                          catalog_name varchar(50) NOT NULL,
                          PRIMARY KEY (id_catalog)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- CatalogOptions Table (options within a predefined catalog)
CREATE TABLE catalog_options (
                                 id_catalog_option bigint(20) NOT NULL AUTO_INCREMENT,
                                 fk_catalog bigint(20) NOT NULL,
                                 option_name varchar(50) NOT NULL,
                                 PRIMARY KEY (id_catalog_option),
                                 KEY fk_catalog (fk_catalog),
                                 CONSTRAINT catalog_options_ibfk_1 FOREIGN KEY (fk_catalog) REFERENCES catalogs (id_catalog)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Validatinon Type Table
CREATE TABLE validation_types (
                                  id_validation_type BIGINT(20) NOT NULL AUTO_INCREMENT,
                                  validation_code VARCHAR(20) DEFAULT NULL,
                                  PRIMARY KEY (id_validation_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Validation Table
CREATE TABLE validations (
                             id_validation BIGINT(20) NOT NULL AUTO_INCREMENT,
                             validation_value VARCHAR(500) DEFAULT NULL,
                             validation_message VARCHAR(500) DEFAULT NULL,
                             fk_validation_type BIGINT(20) NOT NULL,
                             PRIMARY KEY (id_validation),
                             CONSTRAINT validation_ibfk_1 FOREIGN KEY (fk_validation_type) REFERENCES validation_types(id_validation_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Questions Table
CREATE TABLE questions (
                           id_question bigint(20) NOT NULL AUTO_INCREMENT,
                           question_text text NOT NULL,
                           placeholder text default null,
                           required tinyint(1) not null default false,
                           question_order bigint(20) not null,
                           fk_form_section bigint(20) NOT NULL,
                           fk_answer_type bigint(20) NOT NULL,
                           fk_catalog bigint(20) default null,
                           PRIMARY KEY (id_question),
                           KEY fk_form_section (fk_form_section),
                           KEY fk_answer_type (fk_answer_type),
                           CONSTRAINT questions_ibfk_1 FOREIGN KEY (fk_form_section) REFERENCES form_sections (id_form_section),
                           CONSTRAINT questions_ibfk_2 FOREIGN KEY (fk_answer_type) REFERENCES answer_types (id_answer_type),
                           CONSTRAINT questions_ibfk_3 FOREIGN KEY (fk_catalog) REFERENCES catalogs (id_catalog)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Relation question validation Table
CREATE TABLE question_validations (
                                      id_question_validation BIGINT(20) NOT NULL AUTO_INCREMENT,
                                      fk_question BIGINT(20) NOT NULL,
                                      fk_validation BIGINT(20) NOT NULL,
                                      PRIMARY KEY (id_question_validation),
                                      CONSTRAINT question_validations_ibfk_1 FOREIGN KEY (fk_question) REFERENCES questions(id_question),
                                      CONSTRAINT question_validations_ibfk_2 FOREIGN KEY (fk_validation) REFERENCES validations(id_validation)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Treatments Table
CREATE TABLE patient_clinical_histories (
                                            id_patient_clinical_history bigint(20) NOT NULL AUTO_INCREMENT,
                                            fk_clinical_history_catalog bigint(20) DEFAULT NULL,
                                            fk_patient VARCHAR(36) NOT NULL,
                                            date datetime DEFAULT NULL,
                                            PRIMARY KEY (id_patient_clinical_history),
                                            KEY fk_clinical_history_catalog (fk_clinical_history_catalog),
                                            KEY fk_patient (fk_patient),
                                            CONSTRAINT patient_clinical_histories_ibfk_1 FOREIGN KEY (fk_clinical_history_catalog) REFERENCES clinical_history_catalogs (id_clinical_history_catalog),
                                            CONSTRAINT patient_clinical_histories_ibfk_2 FOREIGN KEY (fk_patient) REFERENCES patients (id_patient)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Answers Table
CREATE TABLE answers (
                         id_answer BIGINT(20) NOT NULL AUTO_INCREMENT,
                         fk_patient_clinical_history BIGINT(20) DEFAULT NULL,
                         fk_question BIGINT(20) NOT NULL,
                         fk_patient VARCHAR(36) NOT NULL,
                         answer_boolean TINYINT(1) DEFAULT NULL,
                         answer_numeric DECIMAL(10,2) DEFAULT NULL,
                         answer_text TEXT DEFAULT NULL,
                         answer_date DATE DEFAULT NULL,
                         fk_option BIGINT(20) DEFAULT NULL,
                         is_file TINYINT(1) DEFAULT NULL,
                         PRIMARY KEY (id_answer),
                         KEY fk_patient_clinical_history (fk_patient_clinical_history),
                         KEY fk_question (fk_question),
                         KEY fk_option (fk_option),
                         CONSTRAINT answers_ibfk_1 FOREIGN KEY (fk_patient_clinical_history) REFERENCES patient_clinical_histories (id_patient_clinical_history),
                         CONSTRAINT answers_ibfk_2 FOREIGN KEY (fk_question) REFERENCES questions (id_question),
                         CONSTRAINT answers_ibfk_3 FOREIGN KEY (fk_option) REFERENCES catalog_options (id_catalog_option)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Files Table
CREATE TABLE files (
                       id_file VARCHAR(36) NOT NULL,
                       file_name VARCHAR(255) NOT NULL,
                       file_path VARCHAR(255) NOT NULL,
                       file_type VARCHAR(50) NOT NULL,
                       fk_answer BIGINT(20),
                       PRIMARY KEY (id_file),
                       KEY fk_answer (fk_answer),
                       CONSTRAINT fk_answer FOREIGN KEY (fk_answer) REFERENCES answers (id_answer) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
