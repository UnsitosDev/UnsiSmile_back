CREATE TABLE form_components (
    id_form_component BIGINT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    created_at DATETIME(6) DEFAULT NULL,
    created_by VARCHAR(255) DEFAULT NULL,
    status_key VARCHAR(255) DEFAULT NULL,
    updated_at DATETIME(6) DEFAULT NULL,
    updated_by VARCHAR(255) DEFAULT NULL
);

CREATE TABLE form_component_tooth_conditions (
    id_form_component_tooth_condition BIGINT AUTO_INCREMENT PRIMARY KEY,
    fk_form_component BIGINT NOT NULL,
    fk_tooth_condition BIGINT NOT NULL,
    created_at DATETIME(6) DEFAULT NULL,
    created_by VARCHAR(255) DEFAULT NULL,
    status_key VARCHAR(255) DEFAULT NULL,
    updated_at DATETIME(6) DEFAULT NULL,
    updated_by VARCHAR(255) DEFAULT NULL,
    FOREIGN KEY (fk_form_component) REFERENCES form_components(id_form_component),
    FOREIGN KEY (fk_tooth_condition) REFERENCES tooth_conditions(id_tooth_condition)
);

CREATE TABLE form_component_tooth_faces (
    id_form_component_tooth_face BIGINT AUTO_INCREMENT PRIMARY KEY,
    fk_form_component BIGINT NOT NULL,
    fk_tooth_face VARCHAR(3) NOT NULL,
    created_at DATETIME(6) DEFAULT NULL,
    created_by VARCHAR(255) DEFAULT NULL,
    status_key VARCHAR(255) DEFAULT NULL,
    updated_at DATETIME(6) DEFAULT NULL,
    updated_by VARCHAR(255) DEFAULT NULL,
    FOREIGN KEY (fk_form_component) REFERENCES form_components(id_form_component),
    FOREIGN KEY (fk_tooth_face) REFERENCES tooth_faces(id_tooth_face)
);

CREATE TABLE form_component_toothface_conditions (
    id_form_component_toothface_condition BIGINT AUTO_INCREMENT PRIMARY KEY,
    fk_form_component BIGINT NOT NULL,
    fk_toothface_condition BIGINT NOT NULL,
    created_at DATETIME(6) DEFAULT NULL,
    created_by VARCHAR(255) DEFAULT NULL,
    status_key VARCHAR(255) DEFAULT NULL,
    updated_at DATETIME(6) DEFAULT NULL,
    updated_by VARCHAR(255) DEFAULT NULL,
    FOREIGN KEY (fk_form_component) REFERENCES form_components(id_form_component),
    FOREIGN KEY (fk_toothface_condition) REFERENCES toothface_conditions(id_toothface_conditions)
);

INSERT INTO
    form_components (description)
VALUES ("Odontograma"), ("Profilaxis Dental"), ("Fluorosis");
select *
from form_components;
INSERT INTO form_component_tooth_conditions (fk_form_component, fk_tooth_condition)
VALUES
    (1, 1),
    (1, 2),
    (1, 3),
    (1, 4),
    (1, 5),
    (1, 6),
    (1, 7),
    (1, 8),
    (1, 9),
    (1, 10),
    (1, 11),
    (1, 12);

INSERT INTO form_component_tooth_faces (fk_form_component, fk_tooth_face)
VALUES
    (1, "1"),
    (1, "2"),
    (1, "3"),
    (1, "4"),
    (1, "5");

INSERT INTO form_component_toothface_conditions (fk_form_component, fk_toothface_condition)
VALUES
    (1, 1),
    (1, 2),
    (1, 3),
    (1, 4);