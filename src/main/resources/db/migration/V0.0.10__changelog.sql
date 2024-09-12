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

-- insert para las secciones de historia clínica general
INSERT INTO clinical_history_sections
(fk_clinical_history_catalog,
 fk_form_section,
 section_order)
VALUES
    (1,2, 1),
    (1,3, 2),
    (1,4, 3),
    (1,5, 4),
    (1,6, 5),
    (1,7, 6),
    (1,8, 7),
    (1,9, 8),
    (1,10, 9),
    (1,11, 10),
    (1,13, 11),
    (1,14, 12),
    (1,15, 13),
    (1,16, 14),
    (1,17, 15),
    (1,18, 16),
    (1,19, 17)
;

INSERT INTO answer_types
(description)
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
    ("Morfología facial")
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
    ( 2, "Dolicofacial")
;


-- (1,"BOOLEAN"), (2,"NUMERIC"), (3, "TEXT"), (4, "CATALOG"), (4, "MULTIVALUED"), (5,"FILE")

-- Signos vitales
INSERT INTO questions
(question_text,
 fk_form_section,
 fk_answer_type)
VALUES
    ("Peso", 1, 2),
    ("Estatura", 1, 2),
    ("Temperatura", 1, 2),
    ("Frecuencia cardiaca", 1, 2),
    ("Frecuencia respiratoria", 1, 2),
    ("Presión arterial", 1, 2),
    ("Saturación de oxigeno", 1, 2);

-- Examen facial
INSERT INTO questions
(question_text,
 fk_form_section,
 fk_answer_type)
VALUES
    ("Perfil",2,4),
    ("Frente",2,4),
    ("Señas particulares",2,4)
;

-- Antecedentes heredofamiliares
INSERT INTO questions
(question_text,
 fk_form_section,
 fk_answer_type)
VALUES
    ("Neoplasia (Cáncer)", 3, 5),
    ("Diabetes", 3, 5),
    ("Hipertensión Arterial", 3, 5),
    ("Padecimientos mentales/neurológicos", 3, 5),
    ("Obesidad", 3, 5),
    ("Padecimientos hematológicos", 3, 5),
    ("Malformaciones congénitas", 3, 5),
    ("Problemas cardiacos", 3, 5);

-- Antecedentes personales no patológicos
INSERT INTO questions
(question_text,
 fk_form_section,
 fk_answer_type)
VALUES
    ("Come frutas y verduras", 4, 1),
    ("Come carnes (Res, puerco o pollo)", 4, 1),
    ("Come cereales (Pan, Cereal, etc.)", 4, 1),
    ("Come alimentos chatarra (Dulces, botanas, etc.)", 4, 1),
    ("Toma o bebe 2 litros de agua al día", 4, 1),
    ("Toma o bebe uno o más refrescos al día", 4, 1),
    ("Horas que duerme al día", 4, 2),
    ("¿Cuántas veces a la semana se baña?", 4, 2),
    ("¿Cuántas veces al día cepilla sus dientes?", 4, 2)
;

-- Antecedentes personales patológicos
INSERT INTO questions
(question_text,
 fk_form_section,
 fk_answer_type)
VALUES
    ("Tabaquismo", 5, 1),
    ("Alcoholismo", 5, 1),
    ("Otras sustancias psicoactivas o recreativas", 5, 1),
    ("Perforaciones (Aretes, en mujeres además de los 2 aretes en cada oreja)", 5, 1),
    ("Tatuajes", 5, 1),
    ("Neoplasia (Cáncer)", 5, 1),
    ("Diabetes", 5, 1),
    ("Hipertensión Arterial", 5, 1),
    ("Padecimientos mentales/convulsiones/desmayos/migraña/neuralgia", 5, 1),
    ("Obesidad diagnosticada", 5, 1),
    ("Padecimientos hematológicos/hemorrágicos/anemia/leucemia", 5, 1),
    ("Malformaciones congénitas/ Síndromes", 5, 1),
    ("Problemas cardiacos/ angina de pecho/ infarto/ tromboembolia/
marcapasos/ bypass", 5, 1),
    ("Radioterapia/Quimioterapia", 5, 1),
    ("Padecimientos reumatológicos/ artritis/ osteoporosis", 5, 1),
    ("Enfermedades del riñón", 5, 1),
    ("Enfermedades hepáticas/Hepatitis", 5, 1),
    ("Enfermedades de transmisión sexual", 5, 1),
    ("Hipertiroidismo/Hipotiroidismo", 5, 1),
    ("Enfermedades de vías aéreas/asma", 5, 1),
    ("Enfermedades digestivas", 5, 1),
    ("Tuberculosis o vive con persona(s) con este padecimiento", 5, 1),
    ("Enfermedades de la piel", 5, 1),
    ("Trasplantes de órganos", 5, 1),
    ("¿Has sido hospitalizado?¿Cual fue el motivo?(en mujeres también anotar datos de parto)", 5, 3),
    ("¿Ha tomado algún medicamento recientemente?¿Cuál y por qué motivo?", 5, 3),
    ("¿Ha tenido algún problema con la anestesia dental o anestesia general?, ¿Cuál?", 5, 3),
    ("¿Es alérgico a algún medicamento o sustancia?, ¿Cuál?", 5, 3),
    ("(Solo para mujeres) ¿Está embarazada?, Anotar meses de embarazo", 5, 3),
    ("Ampliar respuestas", 5, 3),
    ("Firma del paciente", 5, 6);

-- Examen clínico
INSERT INTO questions
(question_text,
 fk_form_section,
 fk_answer_type)
VALUES
    ("Paladar",9, 3),
    ("Istmo de las fauces",9, 3),
    ("Mucosa yugal",9, 3),
    ("Nódulos linfáticos",9, 3),
    ("Lengua",9, 3),
    ("Piso de boca",9, 3),
    ("Labios",9, 3),
    ("Glándulas salivales",9, 3),
    ("Encía",9, 3),
    ("Frenillos",9, 3),
    ("Saliva",9, 3),
    ("Otras señas particulares",9, 3);

-- Análisis funcional
INSERT INTO questions
(question_text,
 fk_form_section,
 fk_answer_type)
VALUES
    ("Deglución",10, 3),
    ("Fonación masticación",10, 3),
    ("Respiración",10, 3),
    ("Observaciones",10, 3);

-- Postura del paciente
INSERT INTO questions
(question_text,
 fk_form_section,
 fk_answer_type)
VALUES
    ("ATM – Palpación", 11, 3);

-- examen bucal
INSERT INTO questions
(question_text,
 fk_form_section,
 fk_answer_type)
VALUES
    ("Relación molar: Derecha", 19, 4),
    ("Relación molar: Izquierda", 19, 4),
    ("Relación canina: Derecha", 19, 4),
    ("Relación canina: Izquierda", 19, 4)
;

-- Análisis radiográfico
INSERT INTO questions
(question_text,
 fk_form_section,
 fk_answer_type)
VALUES
    ("Periapical", 13, 3),
    ("Cefálica lateral", 13, 3),
    ("Panorámica", 13, 3)
;

-- Mmodelo de estudio y fotografías
INSERT INTO questions
(question_text,
 fk_form_section,
 fk_answer_type)
VALUES
    ("Modelos de estudio", 14, 3),
    ("Tipo de arcada", 14, 3),
    ("Fotografías", 14, 6)
;

-- Estudio de laboratotio biopsia
INSERT INTO questions
(question_text,
 fk_form_section,
 fk_answer_type)
VALUES
    ("Tipos de estudio de laboratorio", 15, 3),
    ("Tipo de biopsia", 15, 3),
    ("Región donde se realizó la biopsia", 15, 3),
    ("Laboratorio donde se envía el estudio", 15, 3)
;

-- interconsulta médica, como manejar la firma
INSERT INTO questions
(question_text,
 fk_form_section,
 fk_answer_type)
VALUES
    ("Nombre del médico", 16, 3),
    ("Razón de interconsulta", 16, 3),
    ("Motivo de diagnóstico presuntivo", 16, 3),
    ("Motivo de envío y servicio al que se envía", 16, 3),
    ("Diagnóstico", 16, 8),
    ("Pronóstico", 16, 8),
    ("Tratamiento y manejo integral", 16, 8),
    ("Firma", 16, 6)
;

ALTER TABLE odontograms
    ADD COLUMN fk_patient  bigint(20) NOT NULL,
ADD COLUMN fk_form_section bigint(20) NOT NULL,
ADD CONSTRAINT fk_patients FOREIGN KEY (fk_patient) REFERENCES patients(id_patient),
ADD CONSTRAINT fk_form_sections FOREIGN KEY (fk_form_section) REFERENCES form_sections(id_form_section);