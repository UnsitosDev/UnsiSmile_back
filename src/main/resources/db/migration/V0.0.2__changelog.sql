CREATE TABLE careers (
                         id_career BIGINT(20) NOT NULL AUTO_INCREMENT,
                         career VARCHAR(255) NOT NULL,
                         PRIMARY KEY (id_career),
                         UNIQUE (career)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE cycles (
                        id_cycle BIGINT(20) NOT NULL AUTO_INCREMENT,
                        cycle_name VARCHAR(255) NOT NULL,
                        status BIT(1) NOT NULL,
                        PRIMARY KEY (id_cycle),
                        UNIQUE KEY UK_a25l70ifwyh0blql4041ckiwq (cycle_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE ethnic_groups (
                               id_ethnic_group BIGINT(20) NOT NULL,
                               ethnic_group VARCHAR(100) DEFAULT NULL,
                               PRIMARY KEY (id_ethnic_group),
                               UNIQUE KEY UK_bjgrghisvu03pru7u2m1lds75 (ethnic_group)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE genders (
                         id_gender BIGINT(20) NOT NULL AUTO_INCREMENT,
                         gender VARCHAR(100) DEFAULT NULL,
                         PRIMARY KEY (id_gender),
                         UNIQUE KEY UK_k38wg3gipsif7ipes44cf5ak5 (gender)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE guardians (
                           id_guardian BIGINT(20) NOT NULL AUTO_INCREMENT,
                           email VARCHAR(50) DEFAULT NULL,
                           first_name VARCHAR(50) DEFAULT NULL,
                           last_name VARCHAR(50) DEFAULT NULL,
                           phone VARCHAR(20) DEFAULT NULL,
                           PRIMARY KEY (id_guardian),
                           UNIQUE KEY UK_c4gd8eub8nnr1e5nhvuapmasm (phone)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE housings (
                          id_housing VARCHAR(2) NOT NULL,
                          category TEXT DEFAULT NULL,
                          PRIMARY KEY (id_housing)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE marital_statuses (
                                  id_marital_status BIGINT(20) NOT NULL,
                                  marital_status VARCHAR(100) DEFAULT NULL,
                                  PRIMARY KEY (id_marital_status),
                                  UNIQUE KEY UK_95a7iwl5gxn6hu82lvenflrjb (marital_status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE nationalities (
                               id_nationality BIGINT(20) NOT NULL,
                               nationality VARCHAR(100) DEFAULT NULL,
                               PRIMARY KEY (id_nationality),
                               UNIQUE KEY UK_12dw9wtufmiacyb4mpq9iuqtu (nationality)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE occupations (
                             id_occupation BIGINT(20) NOT NULL,
                             occupation VARCHAR(100) DEFAULT NULL,
                             PRIMARY KEY (id_occupation),
                             UNIQUE KEY UK_elbul8ai2woincjektcvtwb2q (occupation)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE religions (
                           id_religion BIGINT(20) NOT NULL,
                           religion VARCHAR(100) DEFAULT NULL,
                           PRIMARY KEY (id_religion),
                           UNIQUE KEY UK_rm2yg4113s5nhv6hqqw98r65v (religion)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE roles (
                       id BIGINT(20) NOT NULL AUTO_INCREMENT,
                       role ENUM('ROLE_ADMIN', 'ROLE_STUDENT') DEFAULT NULL,
                       PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE states (
                        id_state VARCHAR(2) NOT NULL,
                        name VARCHAR(50) NOT NULL,
                        PRIMARY KEY (id_state)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE municipalities (
                                id_municipality VARCHAR(4) NOT NULL,
                                name VARCHAR(50) NOT NULL,
                                fk_state VARCHAR(2) NOT NULL,
                                PRIMARY KEY (id_municipality),
                                KEY FKglgwbymcj9rjapun2otyp4n1w (fk_state),
                                CONSTRAINT FKglgwbymcj9rjapun2otyp4n1w FOREIGN KEY (fk_state) REFERENCES states (id_state)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE localities (
                            id_locality VARCHAR(5) NOT NULL,
                            name VARCHAR(50) NOT NULL,
                            postal_code VARCHAR(5) DEFAULT NULL,
                            fk_municipality VARCHAR(4) NOT NULL,
                            PRIMARY KEY (id_locality),
                            KEY FKgecd32as33bbo2yh06etek0rp (fk_municipality),
                            CONSTRAINT FKgecd32as33bbo2yh06etek0rp FOREIGN KEY (fk_municipality) REFERENCES municipalities (id_municipality)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE neighborhoods (
                               id_neighborhood BIGINT(20) NOT NULL AUTO_INCREMENT,
                               name VARCHAR(50) NOT NULL,
                               fk_locality VARCHAR(5) NOT NULL,
                               PRIMARY KEY (id_neighborhood),
                               KEY FKcnju14or3j0n7jsu0983icv60 (fk_locality),
                               CONSTRAINT FKcnju14or3j0n7jsu0983icv60 FOREIGN KEY (fk_locality) REFERENCES localities (id_locality)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE streets (
                         id_street BIGINT(20) NOT NULL AUTO_INCREMENT,
                         name VARCHAR(50) NOT NULL,
                         fk_neighborhood BIGINT(20) NOT NULL,
                         PRIMARY KEY (id_street),
                         KEY FKqdulr8m8up4rj2d5lpgm3nvp8 (fk_neighborhood),
                         CONSTRAINT FKqdulr8m8up4rj2d5lpgm3nvp8 FOREIGN KEY (fk_neighborhood) REFERENCES neighborhoods (id_neighborhood)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE user_app (
                          id BINARY(16) NOT NULL,
                          password VARCHAR(255) DEFAULT NULL,
                          status BIT(1) NOT NULL,
                          username VARCHAR(255) NOT NULL,
                          role_id BIGINT(20) DEFAULT NULL,
                          PRIMARY KEY (id),
                          UNIQUE KEY UK65kue06vu2g78mxpdfke453e5 (username),
                          KEY FKbl4c0iar4dskgpsjiydh639i6 (role_id),
                          CONSTRAINT FKbl4c0iar4dskgpsjiydh639i6 FOREIGN KEY (role_id) REFERENCES roles (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE people (
                        curp VARCHAR(20) NOT NULL,
                        birth_date DATE NOT NULL,
                        email VARCHAR(200) DEFAULT NULL,
                        first_lastname VARCHAR(50) NOT NULL,
                        first_name VARCHAR(50) NOT NULL,
                        phone VARCHAR(10) DEFAULT NULL,
                        second_lastname VARCHAR(50) DEFAULT NULL,
                        second_name VARCHAR(50) DEFAULT NULL,
                        fk_gender BIGINT(20) DEFAULT NULL,
                        PRIMARY KEY (curp),
                        UNIQUE KEY UK_sw73blrfiqs1etfk8qecdieyx (email),
                        KEY FKf6hmgwsansmue1xrjuvnx4wpl (fk_gender),
                        CONSTRAINT FKf6hmgwsansmue1xrjuvnx4wpl FOREIGN KEY (fk_gender) REFERENCES genders (id_gender)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE addresses (
                           id_address BIGINT NOT NULL AUTO_INCREMENT,
                           interior_number VARCHAR(4) DEFAULT NULL,
                           street_number VARCHAR(4) DEFAULT NULL,
                           fk_housing VARCHAR(2) NOT NULL,
                           fk_street BIGINT NOT NULL,
                           PRIMARY KEY (id_address),
                           FOREIGN KEY (fk_housing) REFERENCES housings (id_housing),
                           FOREIGN KEY (fk_street) REFERENCES streets (id_street)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE administrators (
                                employee_number VARCHAR(15) NOT NULL,
                                fk_person VARCHAR(20) NOT NULL,
                                fk_user BINARY(16) NOT NULL,
                                PRIMARY KEY (employee_number),
                                FOREIGN KEY (fk_user) REFERENCES user_app (id),
                                FOREIGN KEY (fk_person) REFERENCES people (curp)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE groups (
                        id_group BIGINT(20) NOT NULL AUTO_INCREMENT,
                        group_name VARCHAR(255) NOT NULL,
                        fk_career BIGINT(20) DEFAULT NULL,
                        PRIMARY KEY (id_group),
                        UNIQUE KEY UK_7o859iyhxd19rv4hywgdvu2v4 (group_name),
                        KEY FK2r3h2ds7hdwal6t2gdwbs7xq9 (fk_career),
                        CONSTRAINT FK2r3h2ds7hdwal6t2gdwbs7xq9 FOREIGN KEY (fk_career) REFERENCES careers (id_career)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE patients (
                          id_patient BINARY(16) NOT NULL,
                          admission_date DATE DEFAULT NULL,
                          has_disability BIT(1) DEFAULT NULL,
                          is_minor BIT(1) DEFAULT NULL,
                          medical_record_number BIGINT(20),
                          fk_address BIGINT(20) NOT NULL,
                          fk_ethnic_group BIGINT(20) DEFAULT NULL,
                          fk_guardian BIGINT(20) DEFAULT NULL,
                          fk_marital_status BIGINT(20) DEFAULT NULL,
                          fk_nationality BIGINT(20) DEFAULT NULL,
                          fk_occupation BIGINT(20) DEFAULT NULL,
                          fk_person VARCHAR(20) NOT NULL,
                          fk_religion BIGINT(20) DEFAULT NULL,
                          PRIMARY KEY (id_patient),
                          KEY FKdlvldb52keb39yb2ffnxvhucy (fk_address),
                          KEY FKnk3agsyv8f6urfguu5clisowq (fk_ethnic_group),
                          KEY FK16emrlmo0abbj6c3m5kq5wtoe (fk_guardian),
                          KEY FKj6xaq25sn4vx5vht39rbdyc4 (fk_marital_status),
                          KEY FKhc3qyee3pq9n7emkj30pl8m12 (fk_nationality),
                          KEY FKqgn35dn1jxc9gsgbcqjohu8nt (fk_occupation),
                          KEY FK5eavkxggptydymedyrrxejw8h (fk_person),
                          KEY FKp915937atqguh5h4s2am7d1d9 (fk_religion),
                          CONSTRAINT FK16emrlmo0abbj6c3m5kq5wtoe FOREIGN KEY (fk_guardian) REFERENCES guardians (id_guardian),
                          CONSTRAINT FK5eavkxggptydymedyrrxejw8h FOREIGN KEY (fk_person) REFERENCES people (curp),
                          CONSTRAINT FKdlvldb52keb39yb2ffnxvhucy FOREIGN KEY (fk_address) REFERENCES addresses (id_address),
                          CONSTRAINT FKhc3qyee3pq9n7emkj30pl8m12 FOREIGN KEY (fk_nationality) REFERENCES nationalities (id_nationality),
                          CONSTRAINT FKj6xaq25sn4vx5vht39rbdyc4 FOREIGN KEY (fk_marital_status) REFERENCES marital_statuses (id_marital_status),
                          CONSTRAINT FKnk3agsyv8f6urfguu5clisowq FOREIGN KEY (fk_ethnic_group) REFERENCES ethnic_groups (id_ethnic_group),
                          CONSTRAINT FKp915937atqguh5h4s2am7d1d9 FOREIGN KEY (fk_religion) REFERENCES religions (id_religion),
                          CONSTRAINT FKqgn35dn1jxc9gsgbcqjohu8nt FOREIGN KEY (fk_occupation) REFERENCES occupations (id_occupation)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE semesters (
                           id_semester BIGINT(20) NOT NULL AUTO_INCREMENT,
                           fk_cycle BIGINT(20) DEFAULT NULL,
                           fk_group BIGINT(20) DEFAULT NULL,
                           PRIMARY KEY (id_semester),
                           KEY FK1jd8yrqhktltrn0mjc2mabd7t (fk_cycle),
                           KEY FK7xw362v78hao37tpjgwdecc2s (fk_group),
                           CONSTRAINT FK1jd8yrqhktltrn0mjc2mabd7t FOREIGN KEY (fk_cycle) REFERENCES cycles (id_cycle),
                           CONSTRAINT FK7xw362v78hao37tpjgwdecc2s FOREIGN KEY (fk_group) REFERENCES groups (id_group)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE students (
                          enrollment VARCHAR(255) NOT NULL,
                          fk_person VARCHAR(20) DEFAULT NULL,
                          fk_user BINARY(16) NOT NULL,
                          created_at DATETIME(6) DEFAULT NULL,
                          created_by VARCHAR(255) DEFAULT NULL,
                          status_key VARCHAR(255) DEFAULT NULL,
                          updated_at DATETIME(6) DEFAULT NULL,
                          updated_by VARCHAR(255) DEFAULT NULL,
                          PRIMARY KEY (enrollment),
                          UNIQUE KEY UK_36kncqxr7ko2svrs9fat2ilrm (fk_user),
                          UNIQUE KEY UK_im7xtdqrxu8ba7g7wgph2hc9b (fk_person),
                          CONSTRAINT FKerha93sft5akhl5oexjg9vasy FOREIGN KEY (fk_person) REFERENCES people (curp),
                          CONSTRAINT FKqks2kvph1quvyaysfucpmx4l FOREIGN KEY (fk_user) REFERENCES user_app (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE student_patient (
                                 id_student_patient BIGINT(20) NOT NULL AUTO_INCREMENT,
                                 fk_patient BINARY(16) DEFAULT NULL,
                                 fk_student VARCHAR(255) DEFAULT NULL,
                                 PRIMARY KEY (id_student_patient),
                                 KEY FKghwwjdkti37jnwnwrtiepotgr (fk_patient),
                                 KEY FKl4jl7bcm6vqrmlcg1l8ir6bo1 (fk_student),
                                 CONSTRAINT FKghwwjdkti37jnwnwrtiepotgr FOREIGN KEY (fk_patient) REFERENCES patients (id_patient),
                                 CONSTRAINT FKl4jl7bcm6vqrmlcg1l8ir6bo1 FOREIGN KEY (fk_student) REFERENCES students (enrollment)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE students_semesters (
                                    id_student_semester BIGINT(20) NOT NULL AUTO_INCREMENT,
                                    fk_semester BIGINT(20) DEFAULT NULL,
                                    fk_student VARCHAR(255) DEFAULT NULL,
                                    PRIMARY KEY (id_student_semester),
                                    KEY FK1ngs2h47y6743rfr8fk9gi0bw (fk_semester),
                                    KEY FKlpipqdy7xqasfotfpfhjxmure (fk_student),
                                    CONSTRAINT FK1ngs2h47y6743rfr8fk9gi0bw FOREIGN KEY (fk_semester) REFERENCES semesters (id_semester),
                                    CONSTRAINT FKlpipqdy7xqasfotfpfhjxmure FOREIGN KEY (fk_student) REFERENCES students (enrollment)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;