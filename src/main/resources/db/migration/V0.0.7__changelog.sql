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
    ("Odontograma prótesis bucal"),
    ("Plan de tratamiento"),
    ("Recibo"),
    ("Autorización de tratamiento"),
    ("Evaluación de prótesis parcial fija"),
    ("Carta de consentimiento informado para prótesis bucal");

INSERT INTO
    form_sections (form_name, fk_parent_section) -- 6 y 7
VALUES
    ("Cámara pulpar", 25),
    ("Zona apical", 25),
    ("Conducto radicular", 25),
    ("Número de conductos", 26),
    ("Proporción corona-raíz", 26);

INSERT INTO
    clinical_history_sections (
    fk_clinical_history_catalog,
    fk_form_section,
    section_order
)
VALUES
    (2, 1, 1),
    (2, 21, 2),
    (2, 22, 3),
    (2, 23, 4),
    (2, 24, 5),
    (2, 25, 6),
    (2, 26, 7),
    (2, 27, 8),
    (2, 28, 9),
    (2, 29, 10),
    (2, 30, 11),
    (2, 31, 12),
    (2, 32, 13);

INSERT INTO
    catalogs (catalog_name)
VALUES
    ("Cámara pulpar"), -- id: 6
    ("Zona apical"),
    ("Conducto radicular"),
    ("Número de conductos"),
    ("Proporción corona-raíz");

INSERT INTO
    catalog_options (fk_catalog, option_name)
VALUES
    (6, "Normal"),
    (6, "Amplio"),
    (6, "Estrecho"),
    (6, "Nódulos"),
    (6, "Calcificada"),
    (7, "Periodonto normal"),
    (7, "Periodonto ensanchado"),
    (7, "Reabsorción apical"),
    (7, "Cementosis"),
    (7, "Osteoesclerosis"),
    (8, "Normal"),
    (8, "Amplio"),
    (8, "Estrecho"),
    (8, "Agujas cálcicas"),
    (8, "Calcificado"),
    (9, "1"),
    (9, "2"),
    (9, "3"),
    (9, "4"),
    (10, "1 a 3"),
    (10, "1 a 2"),
    (10, "1 a 1"),
    (10, "Reabsorción interna"),
    (10, "Reabsorción externa"),
    (10, "Obturado");

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
    ("T/A", 20, 3, 2, true),
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
    ("Motivo de la consulta", 21, 8, 3, true),
    ("Padecimiento Actual", 21, 8, 4, true),
    ("¿Goza usted de buena salud?", 21, 1, 6, false),
    (
        "¿Está siendo atendido actualmente por un médico?",
        21,
        1,
        7,
        false
    ),
    (
        "Fecha de su último examen físico",
        21,
        9,
        1,
        true
    ),
    (
        "¿Está recibiendo en este momento cualquier tipo de medicación (prescrita o no prescrita) o droga? Si este es el caso, indique los nombres de los medicamentos y las razones por las cuales las usa.",
        21,
        5,
        8,
        false
    ),
    (
        "¿Hay alguna medicina que usted no pueda tomar?",
        21,
        5,
        9,
        false
    ),
    (
        "¿Alguna vez ha sufrido una reacción inusual a una droga/medicamento?",
        21,
        5,
        10,
        false
    ),
    (
        "¿Ha tenido complicaciones con la anestesia local?",
        21,
        5,
        11,
        false
    ),
    (
        "¿Existe alguna otra información que deba ser conocida acerca de su salud?",
        21,
        5,
        12,
        false
    ),
    (
        "¿Padece alguna enfermedad infecciosa? (Fiebre reumática, hepatitis, paludismo, sífilis):",
        21,
        5,
        13,
        false
    ),
    ("¿Sufre de ataques epilépticos?", 21, 1, 5, false),
    (
        "Acerca de sus consultas odontológicas previas",
        21,
        8,
        2,
        true
    ),
    (
        "En caso de ser mujer, ¿usted está embarazada? (especificar los meses)",
        21,
        5,
        14,
        false
    ),
    (   
        "¿Usa usted marca paso cardiaco?", 
        21, 
        5, 
        15, 
        false
    );

INSERT INTO
    questions (
    question_text,
    fk_form_section,
    fk_answer_type,
    question_order,
    required
)
VALUES
    ("Materia alba", 22, 1, 1, false),
    ("Placa bacteriana", 22, 1, 2, false),
    ("Sarro", 22, 1, 3, false),
    ("Gingivitis", 22, 1, 4, false),
    ("Bolsas periodontales", 22, 1, 5, false),
    ("Absceso periodontal", 22, 1, 6, false),
    ("Reabsorción ósea", 22, 1, 7, false),
    ("Movilidad dental", 22, 1, 8, false);

INSERT INTO
    questions (
    question_text,
    fk_form_section,
    fk_answer_type,
    question_order,
    required
)
values
    ("Piso de la boca", 23, 3, 1, true),
    ("Labios", 23, 3, 2, true),
    ("Paladar duro y blando", 23, 3, 3, true),
    ("Lengua", 23, 3, 4, true),
    ("Carrillos", 23, 3, 5, true),
    ("Proceso residual", 23, 3, 6, true),
    ("Áreas edéntulas", 23, 3, 7, true),
    ("Mucosa bucal:", 23, 3, 8, true),
    ("Articulación temporomandibular", 23, 3, 9, true);

INSERT INTO
    questions (
    question_text,
    fk_form_section,
    fk_answer_type,
    question_order,
    required
)
values
    ("Fecha de inicio", 28, 9, 1, true),
    ("Fecha de terminado", 28, 9, 2, true),
    ("Observaciones", 28, 8, 3, false),
    ("Control post-operatorio", 28, 8, 4, true);

INSERT INTO
    questions (
    question_text,
    fk_form_section,
    fk_answer_type,
    question_order,
    required
)
values
    ("Recibo", 29, 6, 1, true);

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
        30,
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
    ("Evolución", 31, 6, 1, true);

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
        "Carta de consentimiento informado para prótesis bucal",
        32,
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
    ("Cámara pulpar", 33, 4, 6, 1, true),
    ("Zona apical", 34, 4, 7, 2, true),
    ("Conducto radicular", 35, 4, 8, 3, true);


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
    ("Número de conductos", 36, 4, 9, 1, true),
    ("Proporción corona-raíz", 37, 4, 10, 2, true);

INSERT INTO
    questions (
    question_text,
    fk_form_section,
    fk_answer_type,
    question_order,
    required
)
values
    ("Dientes cariados", 24, 3, 1, true),
    ("Amalgamas", 24, 3, 2, true),
    ("Dientes ausentes", 24, 3, 3, true),
    ("Resinas", 24, 3, 4, true),
    ("Dientes obturados", 24, 3, 5, true),
    ("Incrustaciones", 24, 3, 6, true),
    ("Extracciones indicadas", 24, 3, 7, true),
    ("Prótesis fija", 24, 3, 8, true),
    ("Raíces", 24, 3, 9, true),
    ("Prótesis removible", 24, 3, 10, true);

INSERT INTO question_validations
(fk_question,
 fk_validation)
VALUES
    (111, 1), 
    (112, 6), 
    (113, 4), 
    (114, 3),
    (115, 8)
    ;
