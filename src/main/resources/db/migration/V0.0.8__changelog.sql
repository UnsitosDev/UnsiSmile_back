INSERT INTO
    answer_types (answer_type)
VALUES
    ("DATE");

-- *-*-*-*-*-*-*-*-* (2) HISTORIA CLÍNICA DE PRÓTESIS BUCAL *-*-*-*-*-*-*-*-*-
-- ("BOOLEAN") 1, ("NUMERIC") 2, ("SHORT_TEXT") 3, ("CATALOG") 4, ("MULTIVALUED") 5, ("PHOTO") 6, ("FILE") 7, ("LONG_TEXT") 8, ("DATE") 9
-- Secciones padre:
INSERT INTO
    form_sections (form_name)
VALUES
    ("Signos vitales"),
    ("Interrogatorio"),
    ("Examen parodontal"),
    ("Exploración de la cavidad bucal"),
    ("Exámen de dientes pilares"),
    ("Exámen radiográfico de dientes pilares"), -- tiene hijos
    ("Exámen de organo dentario"), -- tiene hijos
    ("Plan de tratamiento"),
    ("Recibo"),
    ("Autorización de tratamiento"),
    ("Evaluación de prótesis parcial fija"),
    (
        "CARTA DE CONSENTIMIENTO INFORMADO PARA PRÓTESIS BUCAL"
    );

INSERT INTO
    form_sections (form_name, fk_parent_section) -- 6 y 7
VALUES
    ("Cámara Pulpar", 6),
    ("Zona Apical", 6),
    ("Conducto Radicular", 6),
    ("Número de conductos", 7),
    ("Proporción Corona-Raíz", 7),
    ("Odontograma", 7);

-- insert para las secciones de historia clínica general
INSERT INTO
    clinical_history_sections (
    fk_clinical_history_catalog,
    fk_form_section,
    section_order
)
VALUES
    (2, 20, 1),
    (2, 21, 2),
    (2, 22, 3),
    (2, 23, 4),
    (2, 24, 5),
    (2, 32, 6),
    (2, 33, 7),
    (2, 27, 8),
    (2, 28, 9),
    (2, 29, 10),
    (2, 30, 11),
    (2, 31, 12);

INSERT INTO
    catalogs (catalog_name)
VALUES
    ("Cámara pulpar"), -- id: 5
    ("Zona apical"),
    ("Conducto radicular"),
    ("Numero de conductos"),
    ("Proporción Corona-Raíz");

INSERT INTO
    catalog_options (fk_catalog, option_name)
VALUES
    (5, "Normal"),
    (5, "Amplio"),
    (5, "Estrecho"),
    (5, "Nódulos"),
    (5, "Calcificada"),
    (6, "Periodonto normal"),
    (6, "Periodonto ensanchado"),
    (6, "Reabsorción apical"),
    (6, "Cementosis"),
    (6, "Osteoesclerosis"),
    (7, "Normal"),
    (7, "Amplio"),
    (7, "Estrecho"),
    (7, "Agujas cálcicas"),
    (7, "Calcificado"),
    (8, "1"),
    (8, "2"),
    (8, "3"),
    (8, "4"),
    (9, "1 a 3"),
    (9, "1 a 2"),
    (9, "1 a 1"),
    (9, "Reabsorción interna"),
    (9, "Reabsorción externa"),
    (9, "Obturado");

-- ("BOOLEAN") 1, ("NUMERIC") 2, ("SHORT_TEXT") 3,  ("CATALOG") 4, ("MULTIVALUED") 5, ("PHOTO") 6, ("FILE") 7, ("LONG_TEXT") 8, ("DATE") 9
-- SIGNOS VITALES
INSERT INTO
    questions (
    question_text,
    fk_form_section,
    fk_answer_type,
    question_order,
    required
)
VALUES
    ("Peso", 20, 2, 1, true),
    ("T/A", 20, 2, 2, true),
    ("Pulso", 20, 2, 3, true),
    ("Temperatura", 20, 2, 4, true),
    ("Glucosa", 20, 2, 5, true);

-- INTERROGATIORIO
INSERT INTO
    questions (
    question_text,
    fk_form_section,
    fk_answer_type,
    question_order,
    required
)
VALUES
    ("Motivo de la consulta", 21, 8, 1, true),
    ("Padecimiento Actual", 21, 8, 2, true),
    ("¿Goza usted de buena salud?", 21, 1, 3, true),
    (
        "¿Está siendo atendido actualmente por un médico?",
        21,
        1,
        4,
        true
    ),
    (
        "Fecha de su último examen físico",
        21,
        9,
        5,
        true
    ),
    (
        "¿Está recibiendo en este momento cualquier tipo de medicación (prescrita o no prescrita) o droga? Si este es el caso, indique los nombres de los medicamentos y las razones por las cuales las usa.",
        21,
        5,
        6,
        true
    ),
    (
        "¿Hay alguna medicina que usted no pueda tomar?",
        21,
        5,
        7,
        true
    ),
    (
        "¿Alguna vez ha sufrido una reacción inusual a una droga/medicamento?",
        21,
        5,
        8,
        true
    ),
    (
        "¿Ha tenido complicaciones con la anestesia local?",
        21,
        5,
        9,
        true
    ),
    (
        "¿Existe alguna otra información que deba ser conocida acerca de su salud?",
        21,
        5,
        10,
        true
    ),
    (
        "¿Padece alguna enfermedad infecciosa? (Fiebre reumática, hepatitis, paludismo, sífilis):",
        21,
        5,
        11,
        true
    ),
    ("¿Sufre de ataques epilépticos?", 21, 1, 12, true),
    (
        "Acerca de sus consultas odontológicas previas",
        21,
        8,
        13,
        true
    ),
    (
        "En caso de ser mujer, ¿usted está embarazada? (especificar los meses)",
        21,
        5,
        14,
        true
    ),
    ("Materia alba", 22, 1, 1, true),
    ("Placa bacteriana", 22, 1, 2, true),
    ("Sarro", 22, 1, 3, true),
    ("Gingivitis", 22, 1, 4, true),
    ("Bolsas periodontales", 22, 1, 5, true),
    ("Absceso periodontal", 22, 1, 6, true),
    ("Reabsorción ósea", 22, 1, 7, true),
    ("Movilidad dental", 22, 1, 8, true);

INSERT INTO
    questions (
    question_text,
    fk_form_section,
    fk_answer_type,
    question_order,
    required
)
values
    ("Piso de la Boca", 23, 3, 1, true),
    ("Labios", 23, 3, 2, true),
    ("Paladar Duro y Blando", 23, 3, 3, true),
    ("Lengua", 23, 3, 4, true),
    ("Carrillos", 23, 3, 5, true),
    ("Proceso Residual", 23, 3, 6, true),
    ("Áreas Edéntulas", 23, 3, 7, true),
    ("Mucosa Bucal:", 23, 3, 8, true),
    ("Articulación Temporomandibular", 23, 3, 9, true);

INSERT INTO
    questions (
    question_text,
    fk_form_section,
    fk_answer_type,
    question_order,
    required
)
values
    ("Fecha de inicio", 27, 9, 1, true),
    ("Fecha de terminado", 27, 9, 2, true),
    ("Observaciones", 27, 8, 3, true),
    ("Control post-operatorio", 27, 8, 4, true);

INSERT INTO
    questions (
    question_text,
    fk_form_section,
    fk_answer_type,
    question_order,
    required
)
values
    ("Nombre del alumno", 28, 3, 1, true),
    ("Semestre", 28, 3, 2, true),
    ("Grupo", 28, 3, 3, true),
    ("Indicaciones", 28, 8, 4, true),
    ("No. De Recibo", 28, 2, 5, true),
    ("Costo total:", 28, 2, 6, true),
    (
        "Nombre del catedrático responsable",
        28,
        3,
        7,
        true
    ),
    ("C.D.", 28, 8, 8, true),
    ("Observaciones", 28, 8, 9, true);

INSERT INTO
    questions (
    question_text,
    fk_form_section,
    fk_answer_type,
    question_order,
    required
)
values
    (
        "Firma de autorización de tratamiento",
        29,
        6,
        1,
        true
    );

INSERT INTO
    questions (
    question_text,
    fk_form_section,
    fk_answer_type,
    question_order,
    required
)
values
    ("Evolución", 30, 6, 1, true);

INSERT INTO
    questions (
    question_text,
    fk_form_section,
    fk_answer_type,
    question_order,
    required
)
values
    (
        "CARTA DE CONSENTIMIENTO INFORMADO PARA PRÓTESIS BUCAL",
        31,
        6,
        1,
        true
    );

-- EXÁMEN RADIOGRÁFICO DE DIENTES PILARES
INSERT INTO
    questions (
    question_text,
    fk_form_section,
    fk_answer_type,
    fk_catalog,
    question_order,
    required
)
VALUES
    ("Cámara pulpar", 32, 4, 5, 1, true),
    ("Zona Apical", 32, 4, 6, 2, true),
    ("Conducto Radicular", 32, 4, 7, 3, true);

INSERT INTO
    questions (
    question_text,
    fk_form_section,
    fk_answer_type,
    fk_catalog,
    question_order,
    required
)
VALUES
    ("Numero de Conductos", 33, 4, 8, 1, true),
    ("Proporción Corona-Raíz", 33, 4, 9, 2, true);