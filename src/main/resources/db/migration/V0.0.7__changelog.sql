INSERT INTO clinical_history_catalogs
(clinical_history_name)
VALUES
    ("General"),
    ("Prótesis bucal"),
    ("Periodoncia"),
    ("Operatoria dental"),
    ("Cirugía bucal");

-- secciones padre
INSERT INTO form_sections
(form_name)
VALUES
    ("Signos vitales"),
    ("Exámen facial"),
    ("Antecedentes Heredofamiliares"),
    ("Antecedentes personales no patológicos"),
    ("Antecedentes personales patológicos"),
    ("Odontograma inicial"),
    ("Odontograma final"),
    ("Medición de bolsas inicial"),
    ("Exámen clínico"),
    ("Análisis funcional"),
    ("Postura del paciente"),
    ("Exámen bucal"),
    ("Análisis radiográfico"),
    ("Modelos de estudio de fotografías"),
    ("Estudio de laboratorio/biopsia"),
    ("Interconsulta médica"),
    ("Consentimiento informado"),
    ("Nota de evolución")
;

-- secciones hijas
INSERT INTO form_sections
(form_name, fk_parent_section)
VALUES
    ("Clasificación de Angle", 12)
;
select * from form_sections;
-- insert para las secciones de historia clínica general
INSERT INTO clinical_history_sections
(fk_clinical_history_catalog,
 fk_form_section,
 section_order)
VALUES
    (1,1, 1),
    (1,2, 2),
    (1,3, 3),
    (1,4, 4),
    (1,5, 5),
    (1,6, 6),
    (1,7, 7),
    (1,8, 8),
    (1,9, 9),
    (1,10, 10),
    (1,11, 11),
    (1,12, 12),
    (1,13, 13),
    (1,14, 14),
    (1,15, 15),
    (1,16, 16),
    (1,17, 17),
    (1,18, 18)
;

INSERT INTO answer_types
(answer_type)
VALUES
    ("BOOLEAN"),
    ("NUMERIC"),
    ("SHORT_TEXT"),
    ("CATALOG"),
    ("MULTIVALUED"),
    ("PHOTO"),
    ("FILE"),
    ("LONG_TEXT")
;


INSERT INTO catalogs
(catalog_name)
VALUES
    ("Perfil facial"),
    ("Morfología facial"),
    ("Clases de angles")
;


INSERT INTO catalog_options
(fk_catalog,
 option_name)
VALUES
    ( 1, "Recto"),
    ( 1, "Cóncavo"),
    ( 1, "Convexo"),
    ( 2, "Braquifacial"),
    ( 2, "Normofacial"),
    ( 2, "Dolicofacial"),
    (3, "Clase I"),
    (3, "Clase II"),
    (3, "Clase III")
;


-- (1,"BOOLEAN"), (2,"NUMERIC"), (3, "TEXT"), (4, "CATALOG"), (5, "MULTIVALUED"), (6,"FILE")

-- Signos vitales
INSERT INTO questions
(question_text,
 fk_form_section,
 fk_answer_type,
 question_order,
 required)
VALUES
    ("Peso", 1, 2, 1, true),
    ("Estatura", 1, 2, 2, true),
    ("Temperatura", 1, 2, 3, true),
    ("Frecuencia cardiaca", 1, 2, 4, true),
    ("Frecuencia respiratoria", 1, 2, 5, true),
    ("Presión arterial", 1, 2, 6, true),
    ("Saturación de oxigeno", 1, 2, 7, true);

-- Examen facial
INSERT INTO questions
(question_text,
 fk_form_section,
 fk_answer_type,
 fk_catalog,
 question_order,
 required)
VALUES
    ("Perfil", 2, 4, 1, 1, true),
    ("Frente", 2, 4, 2, 2, true),
    ("Señas particulares",2, 3, null, 3, true)
;

-- Antecedentes heredofamiliares
INSERT INTO questions
(question_text,
 fk_form_section,
 fk_answer_type,
 question_order,
 required)
VALUES
    ("Neoplasia (Cáncer)", 3, 5, 1, true),
    ("Diabetes", 3, 5, 2, true),
    ("Hipertensión Arterial", 3, 5, 3, true),
    ("Padecimientos mentales/neurológicos", 3, 5, 4, true),
    ("Obesidad", 3, 5, 5, true),
    ("Padecimientos hematológicos", 3, 5, 6, true),
    ("Malformaciones congénitas", 3, 5, 7, true),
    ("Problemas cardiacos", 3, 5, 8, true);

-- Antecedentes personales no patológicos
INSERT INTO questions
(question_text,
 fk_form_section,
 fk_answer_type,
 question_order,
 required)
VALUES
    ("Come frutas y verduras", 4, 1, 1, true),
    ("Come carnes (Res, puerco o pollo)", 4, 1, 2, true),
    ("Come cereales (Pan, Cereal, etc.)", 4, 1, 3, true),
    ("Come alimentos chatarra (Dulces, botanas, etc.)", 4, 1, 4, true),
    ("Toma o bebe 2 litros de agua al día", 4, 1, 5, true),
    ("Toma o bebe uno o más refrescos al día", 4, 1, 6, true),
    ("Horas que duerme al día", 4, 2, 7, true),
    ("¿Cuántas veces a la semana se baña?", 4, 2, 8, true),
    ("¿Cuántas veces al día cepilla sus dientes?", 4, 2, 9, true)
;

-- Antecedentes personales patológicos
INSERT INTO questions
(question_text,
 fk_form_section,
 fk_answer_type,
 question_order,
 required)
VALUES
    ("Tabaquismo", 5, 1, 1, true),
    ("Alcoholismo", 5, 1, 2, true),
    ("Otras sustancias psicoactivas o recreativas", 5, 1, 3, true),
    ("Perforaciones (Aretes, en mujeres además de los 2 aretes en cada oreja)", 5, 1, 4, true),
    ("Tatuajes", 5, 1, 5, true),
    ("Neoplasia (Cáncer)", 5, 1, 6, true),
    ("Diabetes", 5, 1, 7, true),
    ("Hipertensión Arterial", 5, 1, 8, true),
    ("Padecimientos mentales/convulsiones/desmayos/migraña/neuralgia", 5, 1, 9, true),
    ("Obesidad diagnosticada", 5, 1, 10, true),
    ("Padecimientos hematológicos/hemorrágicos/anemia/leucemia", 5, 1, 11, true),
    ("Malformaciones congénitas/ Síndromes", 5, 1, 12, true),
    ("Problemas cardiacos/ angina de pecho/ infarto/ tromboembolia/
marcapasos/ bypass", 5, 1, 13, true),
    ("Radioterapia/Quimioterapia", 5, 1, 14, true),
    ("Padecimientos reumatológicos/ artritis/ osteoporosis", 5, 1, 15, true),
    ("Enfermedades del riñón", 5, 1, 16, true),
    ("Enfermedades hepáticas/Hepatitis", 5, 1, 17, true),
    ("Enfermedades de transmisión sexual", 5, 1, 18, true),
    ("Hipertiroidismo/Hipotiroidismo", 5, 1, 19, true),
    ("Enfermedades de vías aéreas/asma", 5, 1, 20, true),
    ("Enfermedades digestivas", 5, 1, 21, true),
    ("Tuberculosis o vive con persona(s) con este padecimiento", 5, 1, 22, true),
    ("Enfermedades de la piel", 5, 1, 23, true),
    ("Trasplantes de órganos", 5, 1, 24, true),
    ("¿Has sido hospitalizado?¿Cual fue el motivo?(en mujeres también anotar datos de parto)", 5, 3, 25, true),
    ("¿Ha tomado algún medicamento recientemente?¿Cuál y por qué motivo?", 5, 3, 26, true),
    ("¿Ha tenido algún problema con la anestesia dental o anestesia general?, ¿Cuál?", 5, 3, 27, true),
    ("¿Es alérgico a algún medicamento o sustancia?, ¿Cuál?", 5, 3, 28, true),
    ("(Solo para mujeres) ¿Está embarazada?, Anotar meses de embarazo", 5, 3, 29, false),
    ("Ampliar respuestas", 5, 3, 30, false),
    ("Firma del paciente", 5, 6, 31, true);

-- Examen clínico
INSERT INTO questions
(question_text,
 fk_form_section,
 fk_answer_type,
 question_order,
 required)
VALUES
    ("Paladar",9, 3, 1, true),
    ("Istmo de las fauces",9, 3, 2, true),
    ("Mucosa yugal",9, 3, 3, true),
    ("Nódulos linfáticos",9, 3, 4, true),
    ("Lengua",9, 3, 5, true),
    ("Piso de boca",9, 3, 6, true),
    ("Labios",9, 3, 7, true),
    ("Glándulas salivales",9, 3, 8, true),
    ("Encía",9, 3, 9, true),
    ("Frenillos",9, 3, 10, true),
    ("Saliva",9, 3, 11, true),
    ("Otras señas particulares",9, 3, 12, false);

-- Análisis funcional
INSERT INTO questions
(question_text,
 fk_form_section,
 fk_answer_type,
 question_order,
 required)
VALUES
    ("Deglución",10, 3, 1, true),
    ("Fonación masticación",10, 3, 2, true),
    ("Respiración",10, 3, 3, true),
    ("Observaciones",10, 3, 4, false);

-- Postura del paciente
INSERT INTO questions
(question_text,
 fk_form_section,
 fk_answer_type,
 question_order,
 required)
VALUES
    ("ATM – Palpación", 11, 3, 1, true);

-- examen bucal
INSERT INTO questions
(question_text,
 fk_form_section,
 fk_answer_type,
 fk_catalog,
 question_order,
 required)
VALUES
    ("Relación molar: Derecha", 19, 4, 3, 1, true),
    ("Relación molar: Izquierda", 19, 4, 3, 2, true),
    ("Relación canina: Derecha", 19, 4, 3, 3, true),
    ("Relación canina: Izquierda", 19, 4, 3, 4, true)
;

-- Análisis radiográfico
INSERT INTO questions
(question_text,
 fk_form_section,
 fk_answer_type,
 question_order,
 required)
VALUES
    ("Periapical", 13, 3, 1, true),
    ("Cefálica lateral", 13, 3, 2, true),
    ("Panorámica", 13, 3, 3, true)
;

-- Mmodelo de estudio y fotografías
INSERT INTO questions
(question_text,
 fk_form_section,
 fk_answer_type,
 question_order,
 required)
VALUES
    ("Modelos de estudio", 14, 3, 1, true),
    ("Tipo de arcada", 14, 3, 2, true),
    ("Fotografías", 14, 6, 3, true)
;

-- Estudio de laboratotio biopsia
INSERT INTO questions
(question_text,
 fk_form_section,
 fk_answer_type,
 question_order,
 required)
VALUES
    ("Tipos de estudio de laboratorio", 15, 3, 1, true),
    ("Tipo de biopsia", 15, 3, 2, true),
    ("Región donde se realizó la biopsia", 15, 3, 3, true),
    ("Laboratorio donde se envía el estudio", 15, 3, 4, true)
;

-- interconsulta médica, como manejar la firma
INSERT INTO questions
(question_text,
 fk_form_section,
 fk_answer_type,
 question_order,
 required)
VALUES
    ("Nombre del médico", 16, 3, 1, true),
    ("Razón de interconsulta", 16, 3, 2, true),
    ("Motivo de diagnóstico presuntivo", 16, 3, 3, true),
    ("Motivo de envío y servicio al que se envía", 16, 3, 4, true),
    ("Diagnóstico", 16, 8, 5, true),
    ("Pronóstico", 16, 8, 6, true),
    ("Tratamiento y manejo integral", 16, 8, 7, true),
    ("Firma", 16, 6, 8, true)
;