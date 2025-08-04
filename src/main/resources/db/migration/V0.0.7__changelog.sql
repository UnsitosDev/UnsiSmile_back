-- *-*-*-*-*-*-*-*-* (2) HISTORIA CLÍNICA DE PRÓTESIS BUCAL *-*-*-*-*-*-*-*-*-
-- ("BOOLEAN") 1, ("NUMERIC") 2, ("SHORT_TEXT") 3, ("CATALOG") 4, ("MULTIVALUED") 5, ("PHOTO") 6, ("FILE") 7, ("LONG_TEXT") 8, ("DATE") 9
-- Secciones padre:
INSERT INTO
    form_sections (id_form_section, form_name, requires_review)
VALUES
    ("SV-02", "Signos vitales", false),
    ("I-01", "Interrogatorio", false),
    ("EP-01", "Examen parodontal", false),
    ("ECB-01", "Exploración de la cavidad bucal", false),
    ("EDP-01", "Exámen de dientes pilares", false),
    ("ERDP-01", "Exámen radiográfico de dientes pilares", false), -- tiene hijos
    ("EOD-01", "Exámen de organo dentario", false), -- tiene hijos
    ("OPB-01", "Odontograma prótesis bucal", false),
    ("PT-01", "Plan de tratamiento", false),
    ("R-01", "Recibo", false),
    ("AT-01", "Autorización de tratamiento", false),
    ("EPPF-01", "Evaluación de prótesis parcial fija", false),
    ("CCIPB-01", "Carta de consentimiento informado para prótesis bucal", false);

INSERT INTO
    form_sections (id_form_section, form_name, fk_parent_section, requires_review) -- 6 y 7
VALUES
    ("CP-01", "Cámara pulpar", "ERDP-01", false),
    ("ZA-01", "Zona apical", "ERDP-01", false),
    ("CR-01", "Conducto radicular", "ERDP-01", false),
    ("NC-01", "Número de conductos", "EOD-01", false),
    ("PCR-01", "Proporción corona-raíz", "EOD-01", false);

INSERT INTO
    medical_record_sections (
    fk_medical_record_catalog,
    fk_form_section,
    section_order
)
VALUES
    (2, "SV-02", 1),
    (2, "I-01", 2),
    (2, "EP-01", 3),
    (2, "ECB-01", 4),
    (2, "EDP-01", 5),
    (2, "ERDP-01", 6),
    (2, "EOD-01", 7),
    (2, "OPB-01", 8),
    (2, "PT-01", 9),
    (2, "R-01", 10),
    (2, "AT-01", 11),
    (2, "EPPF-01", 12),
    (2, "CCIPB-01", 13);

INSERT INTO
    catalogs (catalog_name)
VALUES
    ("Cámara pulpar"), -- id: 6
    ("Zona apical"),
    ("Conducto radicular"),
    ("Número de conductos"),
    ("Proporción corona-raíz");

INSERT INTO
    catalog_options (fk_catalog, option_name, status_key)
VALUES
    (6, "Normal", "A"),
    (6, "Amplio", "A"),
    (6, "Estrecho", "A"),
    (6, "Nódulos", "A"),
    (6, "Calcificada", "A"),
    (7, "Periodonto normal", "A"),
    (7, "Periodonto ensanchado", "A"),
    (7, "Reabsorción apical", "A"),
    (7, "Cementosis", "A"),
    (7, "Osteoesclerosis", "A"),
    (8, "Normal", "A"),
    (8, "Amplio", "A"),
    (8, "Estrecho", "A"),
    (8, "Agujas cálcicas", "A"),
    (8, "Calcificado", "A"),
    (9, "1", "A"),
    (9, "2", "A"),
    (9, "3", "A"),
    (9, "4", "A"),
    (10, "1 a 3", "A"),
    (10, "1 a 2", "A"),
    (10, "1 a 1", "A"),
    (10, "Reabsorción interna", "A"),
    (10, "Reabsorción externa", "A"),
    (10, "Obturado", "A");

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
    ("Peso", "SV-02", 2, 1, true),
    ("T/A", "SV-02", 3, 2, true),
    ("Pulso", "SV-02", 2, 3, true),
    ("Temperatura", "SV-02", 2, 4, true),
    ("Glucosa", "SV-02", 2, 5, true);

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
    ("Motivo de la consulta", "I-01", 8, 3, true),
    ("Padecimiento Actual", "I-01", 8, 4, true),
    ("¿Goza usted de buena salud?", "I-01", 1, 6, false),
    (
        "¿Está siendo atendido actualmente por un médico?",
        "I-01",
        1,
        7,
        false
    ),
    (
        "Fecha de su último examen físico",
        "I-01",
        9,
        1,
        true
    ),
    (
        "¿Está recibiendo en este momento cualquier tipo de medicación (prescrita o no prescrita) o droga? Si este es el caso, indique los nombres de los medicamentos y las razones por las cuales las usa.",
        "I-01",
        5,
        8,
        false
    ),
    (
        "¿Hay alguna medicina que usted no pueda tomar?",
        "I-01",
        5,
        9,
        false
    ),
    (
        "¿Alguna vez ha sufrido una reacción inusual a una droga/medicamento?",
        "I-01",
        5,
        10,
        false
    ),
    (
        "¿Ha tenido complicaciones con la anestesia local?",
        "I-01",
        5,
        11,
        false
    ),
    (
        "¿Existe alguna otra información que deba ser conocida acerca de su salud?",
        "I-01",
        5,
        12,
        false
    ),
    (
        "¿Padece alguna enfermedad infecciosa? (Fiebre reumática, hepatitis, paludismo, sífilis):",
        "I-01",
        5,
        13,
        false
    ),
    ("¿Sufre de ataques epilépticos?", "I-01", 1, 5, false),
    (
        "Acerca de sus consultas odontológicas previas",
        "I-01",
        8,
        2,
        true
    ),
    (
        "En caso de ser mujer, ¿usted está embarazada? (especificar los meses)",
        "I-01",
        5,
        14,
        false
    ),
    (   
        "¿Usa usted marca paso cardiaco?", 
        "I-01",
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
    ("Materia alba", "EP-01", 1, 1, false),
    ("Placa bacteriana", "EP-01", 1, 2, false),
    ("Sarro", "EP-01", 1, 3, false),
    ("Gingivitis", "EP-01", 1, 4, false),
    ("Bolsas periodontales", "EP-01", 1, 5, false),
    ("Absceso periodontal", "EP-01", 1, 6, false),
    ("Reabsorción ósea", "EP-01", 1, 7, false),
    ("Movilidad dental", "EP-01", 1, 8, false);

INSERT INTO
    questions (
    question_text,
    fk_form_section,
    fk_answer_type,
    question_order,
    required
)
values
    ("Piso de la boca", "ECB-01", 3, 1, true),
    ("Labios", "ECB-01", 3, 2, true),
    ("Paladar duro y blando", "ECB-01", 3, 3, true),
    ("Lengua", "ECB-01", 3, 4, true),
    ("Carrillos", "ECB-01", 3, 5, true),
    ("Proceso residual", "ECB-01", 3, 6, true),
    ("Áreas edéntulas", "ECB-01", 3, 7, true),
    ("Mucosa bucal:", "ECB-01", 3, 8, true),
    ("Articulación temporomandibular", "ECB-01", 3, 9, true),
    ("Tejidos Blandos", "ECB-01", 3, 10, true),
    ("Tejidos Óseos", "ECB-01", 3, 11, true),
    ("Diagnóstico", "ECB-01", 3, 12, true),
    ("Estudio de Laboratorio y Gabinete", "ECB-01", 3, 12, false),
    ("Indicaciones de interconsulta Médica u Odontológica", "ECB-01", 3, 13, false);

INSERT INTO
    questions (
    question_text,
    fk_form_section,
    fk_answer_type,
    question_order,
    required
)
values
    ("Fecha de inicio", "PT-01", 9, 1, true),
    ("Fecha de terminado", "PT-01", 9, 2, true),
    ("Observaciones", "PT-01", 8, 3, false),
    ("Control post-operatorio", "PT-01", 8, 4, true);

INSERT INTO
    questions (
    question_text,
    fk_form_section,
    fk_answer_type,
    question_order,
    required
)
values
    ("Recibo", "R-01", 6, 1, true);

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
        "AT-01",
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
    ("Evolución", "EPPF-01", 6, 1, true);

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
        "CCIPB-01",
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
    ("Cámara pulpar", "CP-01", 4, 6, 1, true),
    ("Zona apical", "ZA-01", 4, 7, 2, true),
    ("Conducto radicular", "CR-01", 4, 8, 3, true);


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
    ("Número de conductos", "NC-01", 4, 9, 1, true),
    ("Proporción corona-raíz", "PCR-01", 4, 10, 2, true);

INSERT INTO
    questions (
    question_text,
    fk_form_section,
    fk_answer_type,
    question_order,
    required
)
values
    ("Dientes cariados", "EDP-01", 3, 1, true),
    ("Amalgamas", "EDP-01", 3, 2, true),
    ("Dientes ausentes", "EDP-01", 3, 3, true),
    ("Resinas", "EDP-01", 3, 4, true),
    ("Dientes obturados", "EDP-01", 3, 5, true),
    ("Incrustaciones", "EDP-01", 3, 6, true),
    ("Extracciones indicadas", "EDP-01", 3, 7, true),
    ("Prótesis fija", "EDP-01", 3, 8, true),
    ("Raíces", "EDP-01", 3, 9, true),
    ("Prótesis removible", "EDP-01", 3, 10, true);

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
