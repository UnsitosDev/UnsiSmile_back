
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
    ("Antecedentes heredofamiliares"),
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
    ("Clasificación de angle", 12)
;

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
    ("LONG_TEXT"),
    ("DATE");
;


INSERT INTO catalogs
(catalog_name)
VALUES
    ("Perfil facial"),
    ("Morfología facial"),
    ("Clases de angles"),
    ("Material de vivienda")
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
    (3, "Clase III"),
    (4, "Ladrillo"),
    (4, "Adobe"),
    (4, "Madera"),
    (4, "Lámina"),
    (4, "Otro")
;


-- (1,"BOOLEAN"), (2,"NUMERIC"), (3, "TEXT"), (4, "CATALOG"), (5, "MULTIVALUED"), (6,"FILE")

-- Signos vitales
INSERT INTO questions
(question_text,
 fk_form_section,
 fk_answer_type,
 question_order,
 required,
 placeholder)
VALUES
    ("Peso (kg)", 1, 2, 1, true, "Ingrese su peso en kilogramos"),
    ("Estatura (m)", 1, 2, 2, true, "Ingrese su estatura en metros"),
    ("Temperatura (°C)", 1, 2, 3, true, "Ingrese su temperatura corporal"),
    ("Frecuencia cardíaca (lpm)", 1, 2, 4, true, "Ingrese su FC (latidos por minuto)"),
    ("Frecuencia respiratoria (rpm)", 1, 2, 5, true, "Ingrese su FR (respiraciones por minuto)"),
    ("Presión arterial (T/A)", 1, 3, 6, true, "Ingrese su PA (Ej. 120/80)"),
    ("Saturación de oxígeno (%)", 1, 2, 7, true, "Ingrese su saturación de oxígeno");

-- Examen facial
INSERT INTO questions
(question_text,
 fk_form_section,
 fk_answer_type,
 fk_catalog,
 question_order,
 required,
 placeholder)
VALUES
    ("Perfil", 2, 4, 1, 1, true, null),
    ("Frente", 2, 4, 2, 2, true, null),
    ("Señas particulares",2, 3, null, 3, true, "Anote características distintivas")
;

-- Antecedentes heredofamiliares
INSERT INTO questions
(question_text,
 fk_form_section,
 fk_answer_type,
 question_order,
 required)
VALUES
    ("Neoplasia (Cáncer)", 3, 5, 1, false),
    ("Diabetes", 3, 5, 2, false),
    ("Hipertensión Arterial", 3, 5, 3, false),
    ("Padecimientos mentales/neurológicos", 3, 5, 4, false),
    ("Obesidad", 3, 5, 5, false),
    ("Padecimientos hematológicos", 3, 5, 6, false),
    ("Malformaciones congénitas", 3, 5, 7, false),
    ("Problemas cardiacos", 3, 5, 8, false);

-- Antecedentes perso   nales no patológicos
INSERT INTO questions
(question_text,
 fk_form_section,
 fk_answer_type,
 question_order,
 required,
 placeholder,
 fk_catalog)
VALUES
    ("Come frutas y verduras", 4, 1, 1, false, null, null),
    ("Come carnes (Res, puerco o pollo)", 4, 1, 2, false, null, null),
    ("Come cereales (Pan, Cereal, etc.)", 4, 1, 3, false, null, null),
    ("Come alimentos chatarra (Dulces, botanas, etc.)", 4, 1, 4, false, null, null),
    ("Toma o bebe 2 litros de agua al día", 4, 1, 5, false, null, null),
    ("Toma o bebe uno o más refrescos al día", 4, 1, 6, false, null, null),
    ("Horas que duerme al día", 4, 2, 7, true, "Indique horas de sueño", null),
    ("¿Cuántas veces a la semana se baña?", 4, 2, 8, true, "Indique frecuencia de baño", null),
    ("¿Cuántas veces al día cepilla sus dientes?", 4, 2, 9, true, "Indique frecuencia de cepillado", null),
    ("¿Su vivienda tiene piso?", 4, 1, 10, false, null, null),
    ("Su vivienda esta hecha de:", 4, 4, 11, true, null, 4)
;

-- Antecedentes personales patológicos
INSERT INTO questions
(question_text,
 fk_form_section,
 fk_answer_type,
 question_order,
 required,
 placeholder
)
VALUES
    ("Tabaquismo", 5, 1, 1, true, null),
    ("Alcoholismo", 5, 1, 2, true, null),
    ("Otras sustancias psicoactivas o recreativas", 5, 1, 3, true, null),
    ("Perforaciones (Aretes, en mujeres además de los 2 aretes en cada oreja)", 5, 1, 4, true, null),
    ("Tatuajes", 5, 1, 5, true, null),
    ("Neoplasia (Cáncer)", 5, 1, 6, true, null),
    ("Diabetes", 5, 1, 7, true, null),
    ("Hipertensión Arterial", 5, 1, 8, true, null),
    ("Padecimientos mentales/convulsiones/desmayos/migraña/neuralgia", 5, 1, 9, true, null),
    ("Obesidad diagnosticada", 5, 1, 10, true, null),
    ("Padecimientos hematológicos/hemorrágicos/anemia/leucemia", 5, 1, 11, true, null),
    ("Malformaciones congénitas/ Síndromes", 5, 1, 12, true, null),
    ("Problemas cardiacos/ angina de pecho/ infarto/ tromboembolia/
marcapasos/ bypass", 5, 1, 13, true, null),
    ("Radioterapia/Quimioterapia", 5, 1, 14, true, null),
    ("Padecimientos reumatológicos/ artritis/ osteoporosis", 5, 1, 15, true, null),
    ("Enfermedades del riñón", 5, 1, 16, true, null),
    ("Enfermedades hepáticas/Hepatitis", 5, 1, 17, true, null),
    ("Enfermedades de transmisión sexual", 5, 1, 18, true, null),
    ("Hipertiroidismo/Hipotiroidismo", 5, 1, 19, true, null),
    ("Enfermedades de vías aéreas/asma", 5, 1, 20, true, null),
    ("Enfermedades digestivas", 5, 1, 21, true, null),
    ("Tuberculosis o vive con persona(s) con este padecimiento", 5, 1, 22, true, null),
    ("Enfermedades de la piel", 5, 1, 23, true, null),
    ("Trasplantes de órganos", 5, 1, 24, true, null),
    ("¿Has sido hospitalizado? ¿Cuál fue el motivo?(en mujeres también anotar datos de parto)", 5, 3, 25, true,
     "Indique motivo de hospitalización"),
    ("¿Ha tomado algún medicamento recientemente?¿Cuál y por qué motivo?", 5, 3, 26, true,
     "Indique medicamentos recientes"),
    ("¿Ha tenido algún problema con la anestesia dental o anestesia general?, ¿Cuál?", 5, 3, 27, true,
     "Indique problemas con anestesia"),
    ("¿Es alérgico a algún medicamento o sustancia?, ¿Cuál?", 5, 3, 28, true, "Indique alergias"),
    ("(Solo para mujeres) ¿Está embarazada?, Anotar meses de embarazo", 5, 3, 29, false, "Indique meses de embarazo"),
    ("Ampliar respuestas", 5, 3, 30, false, "Indique más detalles si es necesario"),
    ("Firma del paciente", 5, 6, 31, true, null);

-- Examen clínico
INSERT INTO questions
(question_text,
 fk_form_section,
 fk_answer_type,
 question_order,
 required,
 placeholder)
VALUES
    ("Paladar",9, 3, 1, true, "Descripción del paladar"),
    ("Istmo de las fauces",9, 3, 2, true, "Descripción del istmo"),
    ("Mucosa yugal",9, 3, 3, true, "Descripción de la mucosa"),
    ("Nódulos linfáticos",9, 3, 4, true, "Descripción de nódulos linfáticos"),
    ("Lengua",9, 3, 5, true, "Descripción de la lengua"),
    ("Piso de boca",9, 3, 6, true, "Descripción del piso de boca"),
    ("Labios",9, 3, 7, true, "Descripción de los labios"),
    ("Glándulas salivales",9, 3, 8, true, "Descripción de glándulas"),
    ("Encía",9, 3, 9, true, "Descripción de encías"),
    ("Frenillos",9, 3, 10, true, "Descripción de frenillos"),
    ("Saliva",9, 3, 11, true, "Descripción de saliva"),
    ("Otras señas particulares",9, 3, 12, false, "Indique otras características");

-- Análisis funcional
INSERT INTO questions
(question_text,
 fk_form_section,
 fk_answer_type,
 question_order,
 required,
 placeholder)
VALUES
    ("Deglución",10, 3, 1, true, "Descripción de deglución"),
    ("Fonación masticación",10, 3, 2, true, "Descripción de fonación"),
    ("Respiración",10, 3, 3, true, "Descripción de respiración"),
    ("Observaciones",10, 3, 4, false, "Observaciones generales");

-- Postura del paciente
INSERT INTO questions
(question_text,
 fk_form_section,
 fk_answer_type,
 question_order,
 required,
 placeholder)
VALUES
    ("ATM – Palpación", 11, 3, 1, true, "Descripción de palpación");

-- examen bucal
INSERT INTO questions
(question_text,
 fk_form_section,
 fk_answer_type,
 fk_catalog,
 question_order,
 required)
VALUES
    ("Relación molar: Derecha", 19, 4, 3, 2, true),
    ("Relación molar: Izquierda", 19, 4, 3, 1, true),
    ("Relación canina: Derecha", 19, 4, 3, 4, true),
    ("Relación canina: Izquierda", 19, 4, 3, 3, true)
;

-- Análisis radiográfico
INSERT INTO questions
(question_text,
 fk_form_section,
 fk_answer_type,
 question_order,
 required,
 placeholder)
VALUES
    ("Ortopantomografía", 13, 3, 1, false, "Descripción"),
    ("Lateral de cráneo", 13, 3, 1, false, "Descripción"),
    ("Serie periapical completa", 13, 3, 1, false, "Descripción"),
    ("Periapical individual", 13, 3, 1, false, "Descripción"),
    ("Oclusal superior", 13, 3, 1, false, "Descripción"),
    ("Oclusal inferior", 13, 3, 1, false, "Descripción"),
    ("Posteroanterior de cráneo (PA)", 13, 3, 1, false, "Descripción"),
    ("Anteroposterior de cráneo (AP)", 13, 3, 1, false, "Descripción"),
    ("Dígito palmar", 13, 3, 1, false, "Descripción"),
    ("Senos paranasales", 13, 3, 1, false, "Descripción"),
    ("Waters de cráneo", 13, 3, 1, false, "Descripción"),
    ("Submento vertex", 13, 3, 1, false, "Descripción"),
    ("Tomografía volumétrica completa", 13, 3, 1, false, "Descripción"),
    ("Otros (especifique)", 13, 3, 1, false, "Descripción")
;

-- Mmodelo de estudio y fotografías
INSERT INTO questions
(question_text,
 fk_form_section,
 fk_answer_type,
 question_order,
 required,
 placeholder)
VALUES
    ("Modelos de estudio", 14, 3, 1, true, "Descripción de modelos"),
    ("Tipo de arcada", 14, 3, 2, true, "Indique tipo de arcada"),
    ("Fotografías", 14, 6, 3, true, null)
;

-- Estudio de laboratotio biopsia

-- catalogos para modelos de estudio
INSERT INTO catalogs
(catalog_name)
VALUES
    ("Tipos de estudio de laboratorio")
;

INSERT INTO catalog_options
(fk_catalog,
 option_name)
VALUES
    (5, "Biometria hemática"),
    (5, "TP: Tiempo de protrombina"),
    (5, "TPT: Tiempo de tromboplastina parcial"),
    (5, "INR: Índice internacional normalizado"),
    (5, "TT: Tiempo de trombina")
;

INSERT INTO questions
(question_text,
 fk_form_section,
 fk_answer_type,
 question_order,
 required,
 placeholder)
VALUES
    ("Tipo de biopsia", 15, 3, 2, true, "Indique tipo de biopsia"),
    ("Región donde se realizó la biopsia", 15, 3, 3, true, "Indique región de la biopsia"),
    ("Laboratorio donde se envía el estudio", 15, 3, 4, true, "Indique laboratorio de estudio")
;

INSERT INTO questions
(question_text,
 fk_form_section,
 fk_answer_type,
 fk_catalog,
 question_order,
 required,
 placeholder)
VALUES
    ("Tipos de estudio de laboratorio", 15, 4, 5, 1, true, "Indique tipo de estudio")
;


-- interconsulta médica, como manejar la firma
INSERT INTO questions
(question_text,
 fk_form_section,
 fk_answer_type,
 question_order,
 required,
 placeholder)
VALUES
    ("Nombre del médico", 16, 3, 1, true, "Ingrese nombre del médico"),
    ("Razón de interconsulta", 16, 3, 2, true, "Indique razón de interconsulta"),
    ("Motivo de diagnóstico presuntivo", 16, 3, 3, true, "Indique diagnóstico presuntivo"),
    ("Motivo de envío y servicio al que se envía", 16, 3, 4, true, "Indique motivo de envío"),
    ("Diagnóstico", 16, 8, 5, true, "Indique diagnóstico"),
    ("Pronóstico", 16, 8, 6, true, "Indique pronóstico"),
    ("Tratamiento y manejo integral", 16, 8, 7, true, "Indique tratamiento integral"),
    ("Firma", 16, 6, 8, true, null)
;

INSERT INTO validation_types (validation_code)
VALUES
    ("REGEX"),
    ("MIN_VALUE"),
    ("MAX_VALUE"),
    ("MIN_LENGHT"),
    ("MAX_LENGHT");

INSERT INTO validations
(validation_value,
 validation_message,
 fk_validation_type
)
VALUES
     ("^(2|[1-9]\\d{1,2}|200)(\\.\\d{1,2})?$",
     "El peso debe estar entre 2 y 200 kg (Ej. 75.5, 100).", 1),
    ("^(0\\.[5-9]|1(\\.\\d{1,2})?|2(\\.[0-5]{1,2})?)$",
     "La estatura debe ser entre 0.5 y 2.5 metros m (Ej. 1.75, 2.5).", 1),
    ("^(3[0-9](\\.[0-9]{1,2})?|4[0-4](\\.[0-9]{1,2})?|45(\\.[0-9]{1,2})?)$",
     "La temperatura debe estar entre 30 y 45 °C (Ej. 36.5, 40).", 1),
    ("^(?:[4-9]\\d|1\\d{2}|200)$",
     "La FC debe estar entre 40 y 200 lpm (Ej. 75, 180.50).", 1),
    ("^(?:1[2-9]|2\\d|30)$",
     "Frecuencia respiratoria debe estar entre 12 y 30 rpm.", 1),
    ("^(90|[1-2]\\d{2})\\/(60|[6-9]\\d|1[0-1]\\d|120)$", "Presión arterial debe estar en formato sistólica/diastólica", 1),
    ("^(?:[9][0-9]|100)$", "Saturación de oxígeno debe estar entre 90 y 100%.", 1),
    ("1", "", 4),
    ("2", "", 4),
    ("6", "", 4),
    ("1", "", 5),
    ("2", "", 5),
    ("4", "", 5),
    ("5", "", 5),
    ("6", "", 5);

INSERT INTO question_validations
(fk_question,
 fk_validation)
VALUES
    (1, 1),
    (1, 8),
    (1, 14),
    (2, 2),
    (2, 8),
    (2, 13),
    (3, 3),
    (3, 8),
    (3, 14),
    (4, 4),
    (4, 9),
    (4, 14),
    (5, 5),
    (5, 9),
    (5, 14),
    (6, 6),
    (6, 10),
    (6, 15),
    (7, 7),
    (7, 8),
    (7, 14),
    (25, 8),
    (25, 12),
    (26, 8),
    (26, 12),
    (27, 8),
    (27, 11)
    ;
