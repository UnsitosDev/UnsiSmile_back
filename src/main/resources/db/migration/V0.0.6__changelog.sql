
INSERT INTO medical_record_catalogs
(medical_record_name)
VALUES
    ("General"),
    ("Prótesis bucal"),
    ("Periodoncia"),
    ("Operatoria dental"),
    ("Cirugía bucal"),
    ("Odontología preventiva y salud pública"),
    ("Endodoncia"),
    ("Pulpotomía"),
    ("Pulpectomía");

-- secciones padre
INSERT INTO form_sections
(id_form_section, form_name, requires_review)
VALUES
    ("SV-01", "Signos vitales", false),
    ("EF-01", "Exámen facial", false),
    ("AH-01", "Antecedentes heredofamiliares", false),
    ("APP-01", "Antecedentes personales no patológicos", false),
    ("APP-02", "Antecedentes personales patológicos", false),
    ("MBI-01", "Medición de bolsas inicial", false),
    ("EC-01", "Exámen clínico", false),
    ("AF-01", "Análisis funcional", false),
    ("PP-01", "Postura del paciente", false),
    ("EB-01", "Exámen bucal", false),
    ("AR-01", "Análisis radiográfico", false),
    ("MEF-01", "Modelos de estudio de fotografías", false),
    ("ELB-01", "Estudio de laboratorio/biopsia", false),
    ("IM-01", "Interconsulta médica", false),
    ("CI-01", "Consentimiento informado", false),
    ("NE-01", "Nota de evolución", false)
;

-- secciones hijas
INSERT INTO form_sections
(id_form_section, form_name, fk_parent_section, requires_review)
VALUES
    ("CA-01", "Clasificación de angle", "EB-01", false)
;

-- insert para las secciones de historia clínica general
INSERT INTO medical_record_sections
(fk_medical_record_catalog,
 fk_form_section,
 section_order)
VALUES
    (1,"SV-01", 1),
    (1,"EF-01", 2),
    (1,"AH-01", 3),
    (1,"APP-01", 4),
    (1,"APP-02", 5),
    (1,"MBI-01", 8),
    (1,"EC-01", 9),
    (1,"AF-01", 10),
    (1,"PP-01", 11),
    (1,"EB-01", 12),
    (1,"AR-01", 13),
    (1,"MEF-01", 14),
    (1,"ELB-01", 15),
    (1,"IM-01", 16),
    (1,"CI-01", 17),
    (1,"NE-01", 18)
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
(fk_catalog, option_name, status_key)
VALUES
    ( 1, "Recto", "A"),
    ( 1, "Cóncavo", "A"),
    ( 1, "Convexo", "A"),
    ( 2, "Braquifacial", "A"),
    ( 2, "Normofacial", "A"),
    ( 2, "Dolicofacial", "A"),
    (3, "Clase I", "A"),
    (3, "Clase II", "A"),
    (3, "Clase III", "A"),
    (4, "Ladrillo", "A"),
    (4, "Adobe", "A"),
    (4, "Madera", "A"),
    (4, "Lámina", "A"),
    (4, "Otro", "A")
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
    ("Peso (kg)", "SV-01", 2, 1, true, "Ingrese su peso en kilogramos"),
    ("Estatura (m)", "SV-01", 2, 2, true, "Ingrese su estatura en metros"),
    ("Temperatura (°C)", "SV-01", 2, 3, true, "Ingrese su temperatura corporal"),
    ("Frecuencia cardíaca (lpm)", "SV-01", 2, 4, true, "Ingrese su FC (latidos por minuto)"),
    ("Frecuencia respiratoria (rpm)", "SV-01", 2, 5, true, "Ingrese su FR (respiraciones por minuto)"),
    ("Presión arterial (T/A)", "SV-01", 3, 6, true, "Ingrese su PA (Ej. 120/80)"),
    ("Saturación de oxígeno (%)", "SV-01", 2, 7, true, "Ingrese su saturación de oxígeno");

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
    ("Perfil", "EF-01", 4, 1, 1, true, null),
    ("Frente", "EF-01", 4, 2, 2, true, null),
    ("Señas particulares", "EF-01", 3, null, 3, true, "Anote características distintivas")
;

-- Antecedentes heredofamiliares
INSERT INTO questions
(question_text,
 fk_form_section,
 fk_answer_type,
 question_order,
 required)
VALUES
    ("Neoplasia (Cáncer)", "AH-01", 5, 1, false),
    ("Diabetes", "AH-01", 5, 2, false),
    ("Hipertensión Arterial", "AH-01", 5, 3, false),
    ("Padecimientos mentales/neurológicos", "AH-01", 5, 4, false),
    ("Obesidad", "AH-01", 5, 5, false),
    ("Padecimientos hematológicos", "AH-01", 5, 6, false),
    ("Malformaciones congénitas", "AH-01", 5, 7, false),
    ("Problemas cardiacos", "AH-01", 5, 8, false);

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
    ("Come frutas y verduras", "APP-01", 1, 1, false, null, null),
    ("Come carnes (Res, puerco o pollo)", "APP-01", 1, 2, false, null, null),
    ("Come cereales (Pan, Cereal, etc.)", "APP-01", 1, 3, false, null, null),
    ("Come alimentos chatarra (Dulces, botanas, etc.)", "APP-01", 1, 4, false, null, null),
    ("Toma o bebe 2 litros de agua al día", "APP-01", 1, 5, false, null, null),
    ("Toma o bebe uno o más refrescos al día", "APP-01", 1, 6, false, null, null),
    ("Horas que duerme al día", "APP-01", 2, 7, true, "Indique horas de sueño", null),
    ("¿Cuántas veces a la semana se baña?", "APP-01", 2, 8, true, "Indique frecuencia de baño", null),
    ("¿Cuántas veces al día cepilla sus dientes?", "APP-01", 2, 9, true, "Indique frecuencia de cepillado", null),
    ("¿Su vivienda tiene piso?", "APP-01", 1, 10, false, null, null),
    ("Su vivienda esta hecha de:", "APP-01", 4, 11, true, null, 4)
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
    ("Tabaquismo", "APP-02", 1, 1, false, null),
    ("Alcoholismo", "APP-02", 1, 2, false, null),
    ("Otras sustancias psicoactivas o recreativas", "APP-02", 1, 3, false, null),
    ("Perforaciones (Aretes, en mujeres además de los 2 aretes en cada oreja)", "APP-02", 1, 4, false, null),
    ("Tatuajes", "APP-02", 1, 5, false, null),
    ("Neoplasia (Cáncer)", "APP-02", 1, 6, false, null),
    ("Diabetes", "APP-02", 1, 7, false, null),
    ("Hipertensión Arterial", "APP-02", 1, 8, false, null),
    ("Padecimientos mentales/convulsiones/desmayos/migraña/neuralgia", "APP-02", 1, 9, false, null),
    ("Obesidad diagnosticada", "APP-02", 1, 10, false, null),
    ("Padecimientos hematológicos/hemorrágicos/anemia/leucemia", "APP-02", 1, 11, false, null),
    ("Malformaciones congénitas/ Síndromes", "APP-02", 1, 12, false, null),
    ("Problemas cardiacos/ angina de pecho/ infarto/ tromboembolia/
marcapasos/ bypass", "APP-02", 1, 13, false, null),
    ("Radioterapia/Quimioterapia", "APP-02", 1, 14, false, null),
    ("Padecimientos reumatológicos/ artritis/ osteoporosis", "APP-02", 1, 15, false, null),
    ("Enfermedades del riñón", "APP-02", 1, 16, false, null),
    ("Enfermedades hepáticas/Hepatitis", "APP-02", 1, 17, false, null),
    ("Enfermedades de transmisión sexual", "APP-02", 1, 18, false, null),
    ("Hipertiroidismo/Hipotiroidismo", "APP-02", 1, 19, false, null),
    ("Enfermedades de vías aéreas/asma", "APP-02", 1, 20, false, null),
    ("Enfermedades digestivas", "APP-02", 1, 21, false, null),
    ("Tuberculosis o vive con persona(s) con este padecimiento", "APP-02", 1, 22, false, null),
    ("Enfermedades de la piel", "APP-02", 1, 23, false, null),
    ("Trasplantes de órganos", "APP-02", 1, 24, false, null),
    ("SIDA", "APP-02", 1, 25, false, null),
    ("Alergias", "APP-02", 1, 26, false, null),
    ("Lesiones en cabeza o cuello", "APP-02", 1, 27, false, null),
    ("Sinusitis", "APP-02", 1, 28, false, null),
    ("Fiebre Reumática", "APP-02", 1, 29, false, null),
    ("¿Has sido hospitalizado? ¿Cuál fue el motivo?(en mujeres también anotar datos de parto)", "APP-02", 3, 30, true,
     "Indique motivo de hospitalización"),
    ("¿Ha tomado algún medicamento recientemente?¿Cuál y por qué motivo?", "APP-02", 3, 31, true,
     "Indique medicamentos recientes"),
    ("¿Ha tenido algún problema con la anestesia dental o anestesia general?, ¿Cuál?", "APP-02", 3, 32, true,
     "Indique problemas con anestesia"),
    ("¿Es alérgico a algún medicamento o sustancia?, ¿Cuál?", "APP-02", 3, 33, true, "Indique alergias"),
    ("(Solo para mujeres) ¿Está embarazada?, Anotar meses de embarazo", "APP-02", 3, 34, false, "Indique meses de embarazo"),
    ("Ampliar respuestas", "APP-02", 3, 35, false, "Indique más detalles si es necesario"),
    ("Firma del paciente", "APP-02", 6, 36, true, null);

-- Examen clínico
INSERT INTO questions
(question_text,
 fk_form_section,
 fk_answer_type,
 question_order,
 required,
 placeholder)
VALUES
    ("Paladar", "EC-01", 3, 1, true, "Descripción del paladar"),
    ("Istmo de las fauces", "EC-01", 3, 2, true, "Descripción del istmo"),
    ("Mucosa yugal", "EC-01", 3, 3, true, "Descripción de la mucosa"),
    ("Nódulos linfáticos", "EC-01", 3, 4, true, "Descripción de nódulos linfáticos"),
    ("Lengua", "EC-01", 3, 5, true, "Descripción de la lengua"),
    ("Piso de boca", "EC-01", 3, 6, true, "Descripción del piso de boca"),
    ("Labios", "EC-01", 3, 7, true, "Descripción de los labios"),
    ("Glándulas salivales", "EC-01", 3, 8, true, "Descripción de glándulas"),
    ("Encía", "EC-01", 3, 9, true, "Descripción de encías"),
    ("Frenillos", "EC-01", 3, 10, true, "Descripción de frenillos"),
    ("Saliva", "EC-01", 3, 11, true, "Descripción de saliva"),
    ("Otras señas particulares", "EC-01", 3, 12, false, "Indique otras características");

-- Análisis funcional
INSERT INTO questions
(question_text,
 fk_form_section,
 fk_answer_type,
 question_order,
 required,
 placeholder)
VALUES
    ("Deglución", "AF-01", 3, 1, true, "Descripción de deglución"),
    ("Fonación/masticación", "AF-01", 3, 2, true, "Descripción de fonación"),
    ("Respiración", "AF-01", 3, 3, true, "Descripción de respiración"),
    ("Observaciones", "AF-01", 3, 4, false, "Observaciones generales");

-- Postura del paciente
INSERT INTO questions
(question_text,
 fk_form_section,
 fk_answer_type,
 question_order,
 required,
 placeholder)
VALUES
    ("ATM – Palpación", "PP-01", 3, 1, true, "Descripción de palpación");

-- examen bucal
INSERT INTO questions
(question_text,
 fk_form_section,
 fk_answer_type,
 fk_catalog,
 question_order,
 required)
VALUES
    ("Relación molar: Derecha", "CA-01", 4, 3, 2, true),
    ("Relación molar: Izquierda", "CA-01", 4, 3, 1, true),
    ("Relación canina: Derecha", "CA-01", 4, 3, 4, true),
    ("Relación canina: Izquierda", "CA-01", 4, 3, 3, true)
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
    ("Ortopantomografía", "AR-01", 3, 1, false, "Descripción"),
    ("Lateral de cráneo", "AR-01", 3, 1, false, "Descripción"),
    ("Serie periapical completa", "AR-01", 3, 1, false, "Descripción"),
    ("Periapical individual", "AR-01", 3, 1, false, "Descripción"),
    ("Oclusal superior", "AR-01", 3, 1, false, "Descripción"),
    ("Oclusal inferior", "AR-01", 3, 1, false, "Descripción"),
    ("Posteroanterior de cráneo (PA)", "AR-01", 3, 1, false, "Descripción"),
    ("Anteroposterior de cráneo (AP)", "AR-01", 3, 1, false, "Descripción"),
    ("Dígito palmar", "AR-01", 3, 1, false, "Descripción"),
    ("Senos paranasales", "AR-01", 3, 1, false, "Descripción"),
    ("Waters de cráneo", "AR-01", 3, 1, false, "Descripción"),
    ("Submento vertex", "AR-01", 3, 1, false, "Descripción"),
    ("Tomografía volumétrica completa", "AR-01", 3, 1, false, "Descripción"),
    ("Otros (especifique)", "AR-01", 3, 1, false, "Descripción")
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
    ("Modelos de estudio", "MEF-01", 3, 1, true, "Descripción de modelos"),
    ("Tipo de arcada", "MEF-01", 3, 2, true, "Indique tipo de arcada"),
    ("Fotografías", "MEF-01", 6, 3, true, null)
;

-- Estudio de laboratotio biopsia

-- catalogos para modelos de estudio
INSERT INTO catalogs
(catalog_name)
VALUES
    ("Tipos de estudio de laboratorio")
;

INSERT INTO catalog_options
(fk_catalog, option_name, status_key)
VALUES
    (5, "Biometria hemática", "A"),
    (5, "TP: Tiempo de protrombina", "A"),
    (5, "TPT: Tiempo de tromboplastina parcial", "A"),
    (5, "INR: Índice internacional normalizado", "A"),
    (5, "TT: Tiempo de trombina", "A")
;

INSERT INTO questions
(question_text,
 fk_form_section,
 fk_answer_type,
 question_order,
 required,
 placeholder)
VALUES
    ("Tipo de biopsia", "ELB-01", 3, 2, true, "Indique tipo de biopsia"),
    ("Región donde se realizó la biopsia", "ELB-01", 3, 3, true, "Indique región de la biopsia"),
    ("Laboratorio donde se envía el estudio", "ELB-01", 3, 4, true, "Indique laboratorio de estudio")
;

INSERT INTO questions
(question_text,
 fk_form_section,
 fk_answer_type,
 question_order,
 required,
 placeholder)
VALUES
("periodontograma", "MBI-01", 1, 1, false, "") -- pregunta que funciona como bandera para saber si en periodontograma ha sido llenado
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
    ("Tipos de estudio de laboratorio", "ELB-01", 4, 5, 1, true, "Indique tipo de estudio")
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
    ("Nombre del médico", "IM-01", 3, 1, true, "Ingrese nombre del médico"),
    ("Razón de interconsulta", "IM-01", 3, 2, true, "Indique razón de interconsulta"),
    ("Motivo de diagnóstico presuntivo", "IM-01", 3, 3, true, "Indique diagnóstico presuntivo"),
    ("Motivo de envío y servicio al que se envía", "IM-01", 3, 4, true, "Indique motivo de envío"),
    ("Diagnóstico", "IM-01", 8, 5, true, "Indique diagnóstico"),
    ("Pronóstico", "IM-01", 8, 6, true, "Indique pronóstico"),
    ("Tratamiento y manejo integral", "IM-01", 8, 7, true, "Indique tratamiento integral"),
    ("Firma", "IM-01", 6, 8, true, null)
;

-- consentimiento informado
INSERT INTO questions
(question_text,
 fk_form_section,
 fk_answer_type,
 question_order,
 required,
 placeholder)
VALUES
    ("Consentimiento informado", "CI-01", 6, 1, true, null)
;

-- nota de evolución
INSERT INTO questions
(question_text,
 fk_form_section,
 fk_answer_type,
 question_order,
 required,
 placeholder)
VALUES
    ("Nota de evolución", "NE-01", 6, 1, true, null)
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
    ("^(?:[0-9]|[1-9][0-9]|[1-5][0-9]{2}|600)(?:\\.\\d{1,2})?$","El valor de glucosa debe estar entre 0 y 600 mg/dL", 1),
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
