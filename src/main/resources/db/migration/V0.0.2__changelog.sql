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

CREATE TABLE closed_questions_pathological_antecedents (
                                                           id_closed_question BIGINT(20) NOT NULL AUTO_INCREMENT,
                                                           question VARCHAR(500) NOT NULL,
                                                           PRIMARY KEY (id_closed_question)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE dental_code (
                             id_dental_code BIGINT(20) NOT NULL AUTO_INCREMENT,
                             adult BIT(1) NOT NULL,
                             code VARCHAR(3) NOT NULL,
                             PRIMARY KEY (id_dental_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE educational_degree (
                                    id_educational_degree BIGINT(20) NOT NULL AUTO_INCREMENT,
                                    degree_name VARCHAR(255) NOT NULL,
                                    PRIMARY KEY (id_educational_degree)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE ethnic_groups (
                               id_ethnic_group BIGINT(20) NOT NULL,
                               ethnic_group VARCHAR(100) DEFAULT NULL,
                               PRIMARY KEY (id_ethnic_group),
                               UNIQUE KEY UK_bjgrghisvu03pru7u2m1lds75 (ethnic_group)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE facial_front (
                              id_facial_front BIGINT(20) NOT NULL AUTO_INCREMENT,
                              facial_front VARCHAR(50) NOT NULL,
                              PRIMARY KEY (id_facial_front),
                              UNIQUE KEY UK_95ato13i2a0dt7a43r1hf8q6x (facial_front)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE facial_profile (
                                id_facial_profile BIGINT(20) NOT NULL AUTO_INCREMENT,
                                facial_profile VARCHAR(50) NOT NULL,
                                PRIMARY KEY (id_facial_profile),
                                UNIQUE KEY UK_95s2blu4ra1gvwwe185m6fi9h (facial_profile)
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

CREATE TABLE hereditary_family_history_questions (
                                                     id_question BIGINT(20) NOT NULL AUTO_INCREMENT,
                                                     question VARCHAR(255) DEFAULT NULL,
                                                     PRIMARY KEY (id_question)
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

CREATE TABLE open_questions_pathological_antecedents (
                                                         id_open_question BIGINT(20) NOT NULL AUTO_INCREMENT,
                                                         question VARCHAR(300) NOT NULL,
                                                         PRIMARY KEY (id_open_question)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE periodontogram (
                                id_periodontogram BIGINT(20) NOT NULL AUTO_INCREMENT,
                                date DATE DEFAULT NULL,
                                description TEXT DEFAULT NULL,
                                PRIMARY KEY (id_periodontogram)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE regions_measurement_pockets (
                                             id_regions_measurement_pockets BIGINT(20) NOT NULL AUTO_INCREMENT,
                                             region VARCHAR(100) NOT NULL,
                                             PRIMARY KEY (id_regions_measurement_pockets)
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

CREATE TABLE tooth_condition (
                                 id_tooth_condition BIGINT(20) NOT NULL AUTO_INCREMENT,
                                 description VARCHAR(50) NOT NULL,
                                 PRIMARY KEY (id_tooth_condition)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE tooth_regions_periodontogram (
                                              id_tooth_regions_periodontogram BIGINT(20) NOT NULL AUTO_INCREMENT,
                                              region VARCHAR(2) NOT NULL,
                                              PRIMARY KEY (id_tooth_regions_periodontogram)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE vital_signs (
                             id_vital_signs BIGINT(20) NOT NULL AUTO_INCREMENT,
                             blood_pressure FLOAT DEFAULT NULL,
                             glucose FLOAT DEFAULT NULL,
                             heart_rate FLOAT DEFAULT NULL,
                             height FLOAT DEFAULT NULL,
                             oxygen_saturation FLOAT DEFAULT NULL,
                             pulse FLOAT DEFAULT NULL,
                             respiratory_rate FLOAT DEFAULT NULL,
                             temperature FLOAT DEFAULT NULL,
                             weight FLOAT DEFAULT NULL,
                             PRIMARY KEY (id_vital_signs)
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

CREATE TABLE tooth (
                       id_tooth BIGINT(20) NOT NULL AUTO_INCREMENT,
                       creation_date DATE NOT NULL,
                       PRIMARY KEY (id_tooth)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE odontogram (
                            id_odontogram BIGINT(20) NOT NULL,
                            creation_date DATE NOT NULL,
                            description TEXT DEFAULT NULL,
                            id_tooth_condition_rel BIGINT(20) NOT NULL AUTO_INCREMENT,
                            fk_tooth BIGINT(20) DEFAULT NULL,
                            fk_tooth_condition BIGINT(20) DEFAULT NULL,
                            PRIMARY KEY (id_tooth_condition_rel),
                            KEY FKeql1aem48mfkjgnoloxu3m33e (fk_tooth),
                            KEY FKl9lphq4do6di4p4g0tcg166jq (fk_tooth_condition),
                            CONSTRAINT FKeql1aem48mfkjgnoloxu3m33e FOREIGN KEY (fk_tooth) REFERENCES tooth (id_tooth),
                            CONSTRAINT FKl9lphq4do6di4p4g0tcg166jq FOREIGN KEY (fk_tooth_condition) REFERENCES tooth_condition (id_tooth_condition)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE facial_exam (
                             id_facial_exam BIGINT(20) NOT NULL AUTO_INCREMENT,
                             distinguishing_features TEXT DEFAULT NULL,
                             fk_front BIGINT(20) NOT NULL,
                             fk_profile BIGINT(20) NOT NULL,
                             PRIMARY KEY (id_facial_exam),
                             KEY FK2p7qr0ifl0hh3is5wx7bawgph (fk_front),
                             KEY FK56fj7jfigix26bpxb7m0yrof2 (fk_profile),
                             CONSTRAINT FK2p7qr0ifl0hh3is5wx7bawgph FOREIGN KEY (fk_front) REFERENCES facial_front (id_facial_front),
                             CONSTRAINT FK56fj7jfigix26bpxb7m0yrof2 FOREIGN KEY (fk_profile) REFERENCES facial_profile (id_facial_profile)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE housing_material (
                                  housing_material_id BIGINT(20) NOT NULL AUTO_INCREMENT,
                                  material VARCHAR(100) DEFAULT NULL,
                                  PRIMARY KEY (housing_material_id),
                                  UNIQUE KEY UK_4u47e64c9yx4qwivw3xrmk1c2 (material)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE non_pathological_personal_antecedents (
                                                       id_non_pathological_personal_antecedents BIGINT(20) NOT NULL AUTO_INCREMENT,
                                                       daily_sleep_hours BIGINT(20) DEFAULT NULL,
                                                       drinks_sodas BIT(1) DEFAULT NULL,
                                                       drinks_water_daily BIT(1) DEFAULT NULL,
                                                       eats_cereals BIT(1) DEFAULT NULL,
                                                       eats_fruits_vegetables BIT(1) DEFAULT NULL,
                                                       eats_junk_food BIT(1) DEFAULT NULL,
                                                       eats_meat BIT(1) DEFAULT NULL,
                                                       house_with_floor BIT(1) DEFAULT NULL,
                                                       times_bathes_per_week BIGINT(20) DEFAULT NULL,
                                                       times_brushes_teeth_per_day BIGINT(20) DEFAULT NULL,
                                                       fk_housing_material BIGINT(20) DEFAULT NULL,
                                                       PRIMARY KEY (id_non_pathological_personal_antecedents),
                                                       KEY FKpscpuyjf9d5wpicd3pfsx40ib (fk_housing_material),
                                                       CONSTRAINT FKpscpuyjf9d5wpicd3pfsx40ib FOREIGN KEY (fk_housing_material) REFERENCES housing_material (housing_material_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE medical_histories (
                                   id_medical_history BIGINT(20) NOT NULL AUTO_INCREMENT,
                                   fk_facial_exam BIGINT(20) DEFAULT NULL,
                                   fk_final_odontogram BIGINT(20) DEFAULT NULL,
                                   fk_initial_odontogram BIGINT(20) DEFAULT NULL,
                                   fk_non_pathological_personal_antecedents BIGINT(20) DEFAULT NULL,
                                   fk_vital_signs BIGINT(20) DEFAULT NULL,
                                   PRIMARY KEY (id_medical_history),
                                   UNIQUE KEY UK_9ps2xp750fcj3f9pa216fie2b (fk_vital_signs),
                                   KEY FKhdu4a46cif3nqcc5s68r0w7bn (fk_facial_exam),
                                   KEY FKgo8hepew1xjgf8saygcxios6c (fk_final_odontogram),
                                   KEY FKrhhi1yjakls4yd90dwdrwrjl8 (fk_initial_odontogram),
                                   KEY FKpsuybpkd2wlp514s7dsfqmdtu (fk_non_pathological_personal_antecedents),
                                   CONSTRAINT FK96gliu18alyh4wjf0xee48cwf FOREIGN KEY (fk_vital_signs) REFERENCES vital_signs (id_vital_signs),
                                   CONSTRAINT FKgo8hepew1xjgf8saygcxios6c FOREIGN KEY (fk_final_odontogram) REFERENCES odontogram (id_tooth_condition_rel),
                                   CONSTRAINT FKhdu4a46cif3nqcc5s68r0w7bn FOREIGN KEY (fk_facial_exam) REFERENCES facial_exam (id_facial_exam),
                                   CONSTRAINT FKpsuybpkd2wlp514s7dsfqmdtu FOREIGN KEY (fk_non_pathological_personal_antecedents) REFERENCES non_pathological_personal_antecedents (id_non_pathological_personal_antecedents),
                                   CONSTRAINT FKrhhi1yjakls4yd90dwdrwrjl8 FOREIGN KEY (fk_initial_odontogram) REFERENCES odontogram (id_tooth_condition_rel)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE closed_answers_non_pathological (
                                                 id_closed_answer_non_pathological BIGINT NOT NULL AUTO_INCREMENT,
                                                 answer TEXT DEFAULT NULL,
                                                 fk_closed_questions_id BIGINT DEFAULT NULL,
                                                 fk_medical_histories BIGINT DEFAULT NULL,
                                                 PRIMARY KEY (id_closed_answer_non_pathological),
                                                 FOREIGN KEY (fk_closed_questions_id) REFERENCES closed_questions_pathological_antecedents (id_closed_question),
                                                 FOREIGN KEY (fk_medical_histories) REFERENCES medical_histories (id_medical_history)
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

CREATE TABLE hereditary_family_history (
                                           id_family_history BIGINT(20) NOT NULL AUTO_INCREMENT,
                                           main_response ENUM('SI', 'NO') NOT NULL,
                                           response_detail TEXT DEFAULT NULL,
                                           fk_question BIGINT(20) DEFAULT NULL,
                                           PRIMARY KEY (id_family_history),
                                           KEY FKssc4smwdhbp5cy5i0l7ewpk7n (fk_question),
                                           CONSTRAINT FKssc4smwdhbp5cy5i0l7ewpk7n FOREIGN KEY (fk_question) REFERENCES hereditary_family_history_questions (id_question)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE open_answers_non_pathological (
                                               id_open_answer_non_pathological BIGINT(20) NOT NULL AUTO_INCREMENT,
                                               answer TEXT DEFAULT NULL,
                                               fk_medical_histories BIGINT(20) DEFAULT NULL,
                                               fk_open_questions_id BIGINT(20) DEFAULT NULL,
                                               PRIMARY KEY (id_open_answer_non_pathological),
                                               KEY FKt845lgk3b351vby55olhotqha (fk_medical_histories),
                                               KEY FKtohb3s54uqw4eefjt75ik40n9 (fk_open_questions_id),
                                               CONSTRAINT FKt845lgk3b351vby55olhotqha FOREIGN KEY (fk_medical_histories) REFERENCES medical_histories (id_medical_history),
                                               CONSTRAINT FKtohb3s54uqw4eefjt75ik40n9 FOREIGN KEY (fk_open_questions_id) REFERENCES open_questions_pathological_antecedents (id_open_question)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE patients (
                          id_patient BIGINT(20) NOT NULL AUTO_INCREMENT,
                          admission_date DATE DEFAULT NULL,
                          has_disability BIT(1) DEFAULT NULL,
                          is_minor BIT(1) DEFAULT NULL,
                          fk_address BIGINT(20) NOT NULL,
                          fk_ethnic_group BIGINT(20) DEFAULT NULL,
                          fk_guardian BIGINT(20) DEFAULT NULL,
                          fk_marital_status BIGINT(20) DEFAULT NULL,
                          fk_medical_history BIGINT(20) DEFAULT NULL,
                          fk_nationality BIGINT(20) DEFAULT NULL,
                          fk_occupation BIGINT(20) DEFAULT NULL,
                          fk_person VARCHAR(20) NOT NULL,
                          fk_religion BIGINT(20) DEFAULT NULL,
                          PRIMARY KEY (id_patient),
                          KEY FKdlvldb52keb39yb2ffnxvhucy (fk_address),
                          KEY FKnk3agsyv8f6urfguu5clisowq (fk_ethnic_group),
                          KEY FK16emrlmo0abbj6c3m5kq5wtoe (fk_guardian),
                          KEY FKj6xaq25sn4vx5vht39rbdyc4 (fk_marital_status),
                          KEY FK4pvbm0k7mrhj4859h5o6fnno (fk_medical_history),
                          KEY FKhc3qyee3pq9n7emkj30pl8m12 (fk_nationality),
                          KEY FKqgn35dn1jxc9gsgbcqjohu8nt (fk_occupation),
                          KEY FK5eavkxggptydymedyrrxejw8h (fk_person),
                          KEY FKp915937atqguh5h4s2am7d1d9 (fk_religion),
                          CONSTRAINT FK16emrlmo0abbj6c3m5kq5wtoe FOREIGN KEY (fk_guardian) REFERENCES guardians (id_guardian),
                          CONSTRAINT FK4pvbm0k7mrhj4859h5o6fnno FOREIGN KEY (fk_medical_history) REFERENCES medical_histories (id_medical_history),
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
                          PRIMARY KEY (enrollment),
                          UNIQUE KEY UK_36kncqxr7ko2svrs9fat2ilrm (fk_user),
                          UNIQUE KEY UK_im7xtdqrxu8ba7g7wgph2hc9b (fk_person),
                          CONSTRAINT FKerha93sft5akhl5oexjg9vasy FOREIGN KEY (fk_person) REFERENCES people (curp),
                          CONSTRAINT FKqks2kvph1quvyaysfucpmx4l FOREIGN KEY (fk_user) REFERENCES user_app (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE student_patient (
                                 id_student_patient BIGINT(20) NOT NULL AUTO_INCREMENT,
                                 fk_patient BIGINT(20) DEFAULT NULL,
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

CREATE TABLE tooth_face (
                            id_tooth_face BIGINT(20) NOT NULL AUTO_INCREMENT,
                            creation_date DATE NOT NULL,
                            fk_tooth BIGINT(20) DEFAULT NULL,
                            PRIMARY KEY (id_tooth_face),
                            KEY FKitfki8xb05cy5bg4ee6xkrs52 (fk_tooth),
                            CONSTRAINT FKitfki8xb05cy5bg4ee6xkrs52 FOREIGN KEY (fk_tooth) REFERENCES tooth (id_tooth)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE pocket_measurement_detail (
                                           id_pocket_measurement_detail BIGINT(20) NOT NULL AUTO_INCREMENT,
                                           measurement FLOAT DEFAULT NULL,
                                           fk_dental_code BIGINT(20) DEFAULT NULL,
                                           fk_periodontogram BIGINT(20) DEFAULT NULL,
                                           fk_regions_measurement_pockets BIGINT(20) DEFAULT NULL,
                                           fk_tooth_regions_periodontogram BIGINT(20) DEFAULT NULL,
                                           PRIMARY KEY (id_pocket_measurement_detail),
                                           KEY FKl566ufqp8am4e3h4q2yhrsy1p (fk_dental_code),
                                           KEY FK2tr9vew0odec4sickcskorob1 (fk_periodontogram),
                                           KEY FK5aon02kq03rgqokrvjyxph8vd (fk_regions_measurement_pockets),
                                           KEY FKqr35mgumyne1v0jing4ml65ut (fk_tooth_regions_periodontogram),
                                           CONSTRAINT FK2tr9vew0odec4sickcskorob1 FOREIGN KEY (fk_periodontogram) REFERENCES periodontogram (id_periodontogram),
                                           CONSTRAINT FK5aon02kq03rgqokrvjyxph8vd FOREIGN KEY (fk_regions_measurement_pockets) REFERENCES regions_measurement_pockets (id_regions_measurement_pockets),
                                           CONSTRAINT FKl566ufqp8am4e3h4q2yhrsy1p FOREIGN KEY (fk_dental_code) REFERENCES dental_code (id_dental_code),
                                           CONSTRAINT FKqr35mgumyne1v0jing4ml65ut FOREIGN KEY (fk_tooth_regions_periodontogram) REFERENCES tooth_regions_periodontogram (id_tooth_regions_periodontogram)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
