-- *-*-*-*-*-*-*-*-* carreras *-*-*-*-*-*-*-*-*-
INSERT INTO
    careers (id_career, career)
VALUES
    ("13", "Licenciatura en Odontología")
;

-- *-*-*-*-*-*-*-*-* ciclos *-*-*-*-*-*-*-*-*-
INSERT INTO
    cycles (cycle_name)
VALUES
    ("A"),
    ("B");

CREATE TABLE
    student_groups (
        id_student_groups BIGINT (20) NOT NULL AUTO_INCREMENT,
        fk_student VARCHAR(255) DEFAULT NULL,
        fk_group BIGINT(20) DEFAULT NULL,
        created_at DATETIME(6) DEFAULT NULL,
        created_by VARCHAR(255) DEFAULT NULL,
        status_key VARCHAR(255) DEFAULT NULL,
        updated_at DATETIME(6) DEFAULT NULL,
        updated_by VARCHAR(255) DEFAULT NULL,
        PRIMARY KEY (id_student_groups),
        FOREIGN KEY (fk_student) REFERENCES students (enrollment),
        FOREIGN KEY (fk_group) REFERENCES groups (id_group)
    ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;