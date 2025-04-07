package edu.mx.unsis.unsiSmile.common;

public class ResponseMessages {
    public static final String SUCCESS = "Operación exitosa";
    public static final String ERROR = "Ocurrió un error";
    public static final String NOT_FOUND = "No se encontró el recurso solicitado";
    public static final String BAD_REQUEST = "Petición incorrecta";
    public static final String UNAUTHORIZED = "No autorizado";
    public static final String FORBIDDEN = "Prohibido";
    public static final String INTERNAL_SERVER_ERROR = "Error interno del servidor";
    public static final String METHOD_NOT_ALLOWED = "Método no permitido";
    public static final String CONFLICT = "Conflicto";
    public static final String UNSUPPORTED_MEDIA_TYPE = "Tipo de medio no soportado";
    public static final String TOO_MANY_REQUESTS = "Demasiadas peticiones";
    public static final String SERVICE_UNAVAILABLE = "Servicio no disponible";
    public static final String GONE = "Recurso no disponible";
    public static final String PAYLOAD_TOO_LARGE = "Carga útil demasiado grande";
    public static final String NOT_IMPLEMENTED = "No implementado";
    public static final String NOT_ACCEPTABLE = "No aceptable";
    public static final String REQUEST_TIMEOUT = "Tiempo de espera agotado";
    public static final String CREATED = "Recurso creado";
    public static final String NO_CONTENT = "Sin contenido";
    public static final String RESET_CONTENT = "Contenido restablecido";
    public static final String MOVED_PERMANENTLY = "Movido permanentemente";
    public static final String MOVED_TEMPORARILY = "Movido temporalmente";
    public static final String FOUND = "Encontrado";
    public static final String SEE_OTHER = "Ver otro";
    public static final String TEMPORARY_REDIRECT = "Redirección temporal";
    public static final String PERMANENT_REDIRECT = "Redirección permanente";
    public static final String BAD_GATEWAY = "Puerta de enlace incorrecta";
    public static final String GATEWAY_TIMEOUT = "Tiempo de espera de la puerta de enlace";
    public static final String VERSION_NOT_SUPPORTED = "Versión no soportada";
    public static final String NETWORK_AUTHENTICATION_REQUIRED = "Se requiere autenticación de red";
    public static final String PRECONDITION_FAILED = "Falló la condición previa";
    public static final String LOGIN_ERROR = "Usuario o contraseña incorrectos";
    public static final String USER_NOT_FOUND = "Usuario no registrado";
    public static final String USER_DISABLED = "Usuario deshabilitado";
    public static final String USER_ALREADY_EXISTS = "El usuario ya existe";
    public static final String EMAIL_ALREADY_EXISTS = "El correo electrónico ya está registrado";
    public static final String BAD_PASSWORD = "Contraseña incorrecta";
    public static final String PASSWORDS_DO_NOT_MATCH = "Las nuevas contraseñas no coinciden";
    public static final String PASSWORD_UPDATED = "Contrase\u00F1a actualizada";
    public static final String PASSWORD_UPDATE_ERROR = "Error al actualizar la contraseña";

    public static final String PASSWORD_SAME_AS_OLD = "La nueva contraseña no puede ser la misma que la contraseña actual";
    public static final String PASSWORD_INVALID_CHARACTER = "Caracter inválido o no reconocido en la contraseña";
    public static final String PASSWORD_WEAK_LENGTH = "Contraseña débil: debe incluir al menos 8 caracteres";
    public static final String PASSWORD_WEAK_LOWERCASE = "Contraseña débil: debe incluir al menos una letra minúscula";
    public static final String PASSWORD_WEAK_UPPERCASE = "Contraseña débil: debe incluir al menos una letra mayúscula";
    public static final String PASSWORD_WEAK_NUMBER = "Contraseña débil: debe incluir al menos un número";
    public static final String PASSWORD_WEAK_SPECIAL_CHAR = "Contraseña débil: debe incluir al menos un carácter especial (@$!%*?&)";
    public static final String PASSWORD_INVALID_SPECIFIC_CHAR = "Carácter inválido en la contraseña: '%s' no está permitido";

    public static final String PATIENT_NOT_FOUND = "Paciente no encontrado";
    public static final String ERROR_CREATING_PROGRESS_NOTE = "Error al crear la nota de evolución. ";
    public static final String ERROR_FETCHING_PROGRESS_NOTES = "Error al obtener las notas de evolución del paciente.";
    public static final String PATIENT_NOT_FOUND_PROGRESS_NOTES = "No se encontraron notas de evolución para el paciente con ID: ";
    public static final String ERROR_REPORT_NOT_FOUND = "No se pudo encontrar el archivo de notas de evolución: ";
    public static final String ERROR_GENERATING_REPORT = "Error al generar el PDF de notas de evolución: ";
    public static final String PATIENT_ID_CANNOT_BE_EMPTY = "El ID del paciente no puede estar vacío.";
    public static final String REQUEST_CANNOT_BE_NULL = "El request no puede ser nulL.";

    public static final String STUDENT_NOT_FOUND = "Estudiante no encontrado";
    public static final String STUDENT_GROUP_NOT_FOUND = "El estudiante no está registrado en el grupo.";
    public static final String PROFESSOR_CLINICAL_AREA_NOT_FOUND = "Profesor del área clínica no encontrado";
    public static final String FAILED_TO_UPDATE_STUDENT = "Error al actualizar el estudiante";

    public static final String FILE_NOT_FOUND = "Archivo no encontrado.";
    public static final String ERROR_WHILE_DOWNLOAD_FILE = "Error durante la descarga del archivo.";

    public static final String PROFESSOR_NOT_FOUND = "Profesor no encontrado";
    public static final String INVALID_ROLE = "Rol no válido";
    public static final String ROLE_NOT_FOUND = "Rol no encontrado";
  
    public static final String PERSON_NOT_FOUND = "Persona no encontrada";
    public static final String PERSON_REQUEST_NULL = "PersonRequest no puede ser nulo";
    public static final String PERSON_ALREADY_EXISTS = "La persona con CURP %s ya existe";
    public static final String FAILED_CREATE_PERSON = "Error al crear la persona";
    public static final String FAILED_FETCH_PERSON = "Error al obtener la persona";
    public static final String PERSON_NOT_FOUND_EMAIL = "Persona no encontrada con email: %s";
    public static final String FAILED_FETCH_PERSONS = "Error al obtener las personas";
    public static final String UPDATED_PERSON_REQUEST_NULL = "Updated PersonRequest no puede ser nulo";
    public static final String FAILED_UPDATE_PERSON = "Error al actualizar la persona";
    public static final String PERSON_NOT_FOUND_ID = "Persona no encontrada con ID: %s";
    public static final String FAILED_DELETE_PERSON = "Error al eliminar la persona";

    // ADDRESS VALIDATION REQUEST
    public static final String HOUSING_NULL = "La vivienda no puede ser nula.";
    public static final String ID_HOUSING_BLANK = "El ID de la vivienda no puede estar vacío.";
    public static final String ID_HOUSING_SIZE = "El ID de la vivienda debe tener 2 caracteres.";
    public static final String CATEGORY_BLANK = "La categoría de la vivienda no puede estar vacía.";
    public static final String HOUSING_CREATE_FAILED = "Error al buscar o crear la vivienda";
    public static final String HOUSING_NOT_FOUND = "No se encontró la vivienda con el ID proporcionado.";
    public static final String HOUSING_FETCH_FAILED = "Error al intentar obtener la vivienda.";

    public static final String STREET_NULL = "La calle no puede ser nula.";
    public static final String STREET_NUMBER_BLANK = "El número de la calle no puede estar vacío.";
    public static final String STREET_NUMBER_SIZE = "El número de la calle debe tener como máximo 2 caracteres.";
    public static final String INTERIOR_NUMBER_SIZE = "El número interior debe tener como máximo 2 caracteres.";
    public static final String STREET_NAME_BLANK = "El nombre de la calle no puede estar vacío.";
    public static final String STREET_NAME_NULL = "El nombre de la calle no puede ser nulo.";
    public static final String STREET_NAME_SIZE = "El nombre de la calle debe tener como máximo 50 caracteres.";
    public static final String STREET_CREATE_FAILED = "Error al buscar o crear la calle";

    public static final String NEIGHBORHOOD_NULL = "El vecindario no puede ser nulo.";
    public static final String NEIGHBORHOOD_NAME_BLANK = "El nombre del vecindario no puede estar vacío.";
    public static final String NEIGHBORHOOD_NAME_NULL = "El nombre del vecindario no puede ser nulo.";
    public static final String NEIGHBORHOOD_NAME_SIZE = "El nombre del vecindario debe tener como máximo 50 caracteres.";
    public static final String NEIGHBORHOOD_CREATE_FAILED = "Error al buscar o crear el vecindario";
    public static final String NEIGHBORHOODS_FETCH_FAILED = "Error al obtener los vecindarios";

    public static final String LOCALITY_NULL = "La localidad no puede ser nula.";
    public static final String LOCALITY_NAME_BLANK = "El nombre de la localidad no puede estar vacío.";
    public static final String LOCALITY_NAME_NULL = "El nombre de la localidad no puede ser nulo.";
    public static final String LOCALITY_NAME_SIZE = "El nombre de la localidad debe tener como máximo 50 caracteres.";
    public static final String POSTAL_CODE_BLANK = "El código postal no puede estar vacío.";
    public static final String POSTAL_CODE_NULL = "El código postal no puede ser nulo.";
    public static final String POSTAL_CODE_SIZE = "El código postal debe tener 5 caracteres.";
    public static final String LOCALITY_CREATE_FAILED = "Error al buscar o crear la localidad";

    public static final String MUNICIPALITY_NULL = "El municipio no puede ser nulo.";
    public static final String MUNICIPALITY_ID_BLANK = "El ID del municipio no puede estar vacío.";
    public static final String MUNICIPALITY_ID_SIZE = "El ID del municipio debe tener 4 caracteres.";
    public static final String MUNICIPALITY_NAME_BLANK = "El nombre del municipio no puede estar vacío.";
    public static final String MUNICIPALITY_NAME_SIZE = "El nombre del municipio debe tener como máximo 50 caracteres.";
    public static final String MUNICIPALITY_CREATE_FAILED = "Error al buscar o crear el municipio";

    public static final String STATE_NULL = "El estado no puede ser nulo.";
    public static final String STATE_ID_BLANK = "El ID del estado no puede estar vacío.";
    public static final String STATE_ID_SIZE = "El ID del estado debe tener 2 caracteres.";
    public static final String STATE_NAME_BLANK = "El nombre del estado no puede estar vacío.";
    public static final String STATE_NAME_SIZE = "El nombre del estado debe tener como máximo 50 caracteres.";
    public static final String STATE_NOT_FOUND = "El estado que desea vincular no existe";
    public static final String STATE_FIND_FAILED = "Error al buscar el estado";

    public static final String ADDRESS_REQUEST_NULL = "La solicitud de dirección no puede ser nula";
    public static final String ADDRESS_CREATE_FAILED = "Error al buscar o crear la dirección";

    public static final String CANNOT_ENABLE_CLINICAL_AREA_PROFESSOR_INACTIVE = "No se puede habilitar el área clínica porque el profesor está inactivo.";
    public static final String FAILED_TO_ENABLE_PROFESSOR_CLINICAL_AREA = "Error al habilitar el área clínica del profesor.";
    public static final String CATALOG_NOT_FOUND = "Catálogo no encontrado.";

    public static final String CATALOG_OPTION_NOT_FOUND= "Opción de catálogo no encontrada";

    public static final String GROUP_NOT_FOUND = "Grupo no encontrado";
    public static final String ERROR_CREATING_STUDENT_GROUP = "Ocurrió un error al agregar un estudiante a un grupo.";

    public static final String INVALID_INPUT = "La entrada no es válida. Debe ser un número o una cadena.";
    public static final String FAILED_FETCH_STUDENTS_IN_GROUPS = "Error al obtener los estudiantes en los grupos.";
    public static final String FAILED_TO_FETCH_STUDENTS = "Error al obtener los estudiantes.";

    public static final String NATIONALITY_NOT_FOUND = "Nacionalidad no encontrada";
    public static final String MARITAL_STATUS_NOT_FOUND = "Estado civil no encontrado";
    public static final String ETHNIC_GROUP_NOT_FOUND = "Grupo étnico no encontrado";
    public static final String RELIGION_NOT_FOUND = "Religión no encontrada";

    // PATIENT VALIDATION REQUEST
    public static final String NOT_NULL_HAS_DISABILITY = "El campo 'tiene discapacidad' no puede ser nulo";
    public static final String NOT_NULL_NATIONALITY = "El campo 'nacionalidad' no puede ser nulo";
    public static final String NOT_NULL_PERSON = "El campo 'persona' no puede ser nulo";
    public static final String NOT_NULL_ADDRESS = "El campo 'dirección' no puede ser nulo";
    public static final String NOT_NULL_MARITAL_STATUS = "El campo 'estado civil' no puede ser nulo";
    public static final String NOT_NULL_OCCUPATION = "El campo 'ocupación' no puede ser nulo";
    public static final String NOT_NULL_ETHNIC_GROUP = "El campo 'grupo étnico' no puede ser nulo";
    public static final String NOT_NULL_RELIGION = "El campo 'religión' no puede ser nulo";

    public static final String ERROR_CREATING_PATIENT = "Error al crear el paciente";

    public static final String OCCUPATION_NOT_FOUND = "No se pudo encontrar o crear la ocupación";
    public static final String ERROR_CREATING_OCCUPATION = "Error al crear la ocupación";

    public static final String GUARDIAN_NOT_FOUND = "Tutor no encontrado";
    public static final String GUARDIAN_UPDATE_FAILED = "Error al actualizar el tutor.";
    public static final String GUARDIAN_REQUEST_CANNOT_BE_NULL = "El objeto GuardianRequest no puede ser nulo.";

    // PERSON VALIDATION REQUEST
    public static final String NOT_BLANK_CURP = "El CURP no puede estar en blanco";
    public static final String SIZE_CURP = "El CURP debe tener exactamente 18 caracteres";
    public static final String INVALID_CURP_FORMAT = "CURP inválida: %s - Formato incorrecto";
    public static final String INVALID_CURP_BIRTHDATE = "CURP inválida: %s - Fecha de nacimiento no coincide";
    public static final String INVALID_CURP_GENDER = "CURP inválida: %s - Sexo no coincide";
    public static final String NOT_BLANK_FIRST_NAME = "El primer nombre no puede estar en blanco";
    public static final String SIZE_FIRST_NAME = "El primer nombre debe tener como máximo 50 caracteres";
    public static final String SIZE_SECOND_NAME = "El segundo nombre debe tener como máximo 50 caracteres";
    public static final String NOT_BLANK_FIRST_LAST_NAME = "El primer apellido no puede estar en blanco";
    public static final String SIZE_FIRST_LAST_NAME = "El primer apellido debe tener como máximo 50 caracteres";
    public static final String SIZE_SECOND_LAST_NAME = "El segundo apellido debe tener como máximo 50 caracteres";
    public static final String PATTERN_PHONE = "El número de teléfono debe tener 10 dígitos";
    public static final String NOT_NULL_BIRTH_DATE = "La fecha de nacimiento no puede ser nula";
    public static final String NOT_BLANK_EMAIL = "El correo electrónico no puede estar en blanco";
    public static final String VALID_EMAIL = "El correo electrónico debe ser válido";
    public static final String SIZE_EMAIL = "El correo electrónico debe tener como máximo 200 caracteres";

    // GENDER VALIDATION REQUEST
    public static final String NOT_NULL_GENDER = "El género no puede ser nulo";
    public static final String NOT_NULL_GENDER_FIELD = "El campo género no puede ser nulo";
    public static final String NOT_BLANK_GENDER_FIELD = "El campo género no puede estar en blanco";
    public static final String NOT_NULL_ID_GENDER_FIELD = "El campo ID género no puede ser nulo";

    // MARITAL STATUS VALIDATION REQUEST
    public static final String NOT_BLANK_MARITAL_STATUS_DESCRIPTION = "La descripción del estado civil no puede estar en blanco";
    public static final String MAX_SIZE_MARITAL_STATUS_DESCRIPTION = "La descripción del estado civil debe tener un máximo de 100 caracteres";

    // OCCUPATION VALIDATION REQUEST
    public static final String NOT_BLANK_OCCUPATION_DESCRIPTION = "La descripción de la ocupación no puede estar en blanco";
    public static final String MAX_SIZE_OCCUPATION_DESCRIPTION = "La descripción de la ocupación debe tener un máximo de 100 caracteres";

    // ETHNIC GROUP VALIDATION REQUEST
    public static final String NOT_BLANK_ETHNIC_GROUP_DESCRIPTION = "La descripción del grupo étnico no puede estar en blanco";
    public static final String MAX_SIZE_ETHNIC_GROUP_DESCRIPTION = "La descripción del grupo étnico debe tener un máximo de 100 caracteres";

    // RELIGION VALIDATION REQUEST
    public static final String NOT_BLANK_RELIGION_DESCRIPTION = "La descripción de la religión no puede estar en blanco";
    public static final String MAX_SIZE_RELIGION_DESCRIPTION = "La descripción de la religión debe tener un máximo de 100 caracteres";

    //public static final String ADMINISTRATOR_NOT_FOUND = "Administrador no encontrado con el número de empleado: ";
    public static final String ERROR_CREATING_ADMINISTRATOR = "Error al crear el administrador.";
    public static final String ERROR_UPDATING_ADMINISTRATOR = "Error al actualizar el administrador.";
    public static final String FAILED_CREATE_ADMINISTRATOR = "Error al crear el administrador";
    public static final String ADMINISTRATOR_NOT_FOUND = "Administrador no encontrado con número de empleado: %s";
    public static final String FAILED_FETCH_ADMINISTRATOR = "Error al obtener el administrador";
    public static final String ADMINISTRATOR_NOT_FOUND_FOR_USER = "Administrador no encontrado para el usuario: %s";
    public static final String FAILED_FETCH_ADMINISTRATORS = "Error al obtener la lista de administradores";
    public static final String FAILED_DELETE_ADMINISTRATOR = "Error al eliminar el administrador";
    public static final String FAILED_UPDATE_ADMINISTRATOR_STATUS = "Error al actualizar el estado del administrador";

    public static final String EMPLOYEE_NUMBER_NULL = "El campo número de empleado no puede estar vacío";
    public static final String EMPLOYEE_NUMBER_INVALID = "El número de empleado debe contener solo dígitos y tener entre 4 y 6 caracteres";
    public static final String PERSON_NULL = "El campo persona no puede estar vacío";
    public static final String CAREER_NULL = "El campo carrera no puede estar vacío";

    // STUDENT VALIDATION REQUEST
    public static final String NOT_NULL_ENROLLMENT = "El campo matrícula no puede estar vacío";
    public static final String ENROLLMENT_INVALID = "La matrícula debe contener exactamente 10 dígitos numéricos";
    public static final String NOT_NULL_GROUP = "El campo grupo no puede estar vacío";

    public static final String CAREER_REQUEST_CANNOT_BE_NULL = "CareerRequest no puede ser nulo";
    public static final String FAILED_TO_CREATE_CAREER = "Error al crear la carrera";
    public static final String CAREER_NOT_FOUND_ID = "Carrera no encontrada con ID: ";
    public static final String CAREER_NOT_FOUND_NAME = "Carrera no encontrada con NOMBRE: ";
    public static final String FAILED_TO_FETCH_CAREER = "Error al obtener la carrera";
    public static final String FAILED_TO_FETCH_CAREERS = "Error al obtener las carreras";
    public static final String FAILED_TO_UPDATE_CAREER = "Error al actualizar la carrera";
    public static final String FAILED_TO_DELETE_CAREER = "Error al eliminar la carrera";
    public static final String NOT_NULL_ID_CAREER = "El campo idCareer no puede ser nulo";
    public static final String NOT_BLANK_ID_CAREER = "El campo idCareer no puede estar vacío";
    public static final String NOT_NULL_CAREER = "El campo career no puede ser nulo";
    public static final String NOT_BLANK_CAREER = "El campo career no puede estar vacío";

    //DASHBOARD
    public static final String ERROR_STUDENT_DASHBOARD = "Error al acceder a los datos del dashboard del estudiante";
    public static final String ERROR_PROFESSOR_DASHBOARD = "Error al acceder a los datos del dashboard del profesor";
    public static final String ERROR_ADMIN_DASHBOARD = "Error al acceder a los datos del dashboard del administrador";
  
    public static final String FORM_SECTION_REQUEST_NULL = "FormSectionRequest no puede ser nulo";
    public static final String FORM_SECTION_ID_NULL = "El ID no puede ser nulo";
    public static final String FORM_SECTION_NOT_FOUND = "Sección de formulario no encontrada con el ID: ";
    public static final String PATIENT_CLINICAL_HISTORY_NOT_FOUND = "Historial clínico del paciente no encontrado con el ID: ";
    public static final String PATIENT_CLINICAL_HISTORY_ID_NULL = "El ID de la historial clínico del paciente no puede ser nulo";
    public static final String NO_FORM_SECTIONS = "No se encontraron secciones de formulario";
    public static final String FAILED_SAVE_FORM_SECTION = "Error al guardar la sección de formulario debido a un error interno del servidor";
    public static final String FAILED_FIND_FORM_SECTION = "Error al buscar la sección de formulario con el ID: ";
    public static final String FAILED_FETCH_FORM_SECTIONS = "Error al obtener las secciones de formulario";
    public static final String FAILED_DELETE_FORM_SECTION = "Error al eliminar la sección de formulario con el ID: ";
    public static final String FAILED_FETCH_SUBFORM = "Error al obtener el modelo de la subsección del formulario";

    public static final String CLINICAL_HISTORY_SENT_TO_REVIEW = "La historia clínica ha sido enviada a revisión";
    public static final String ERROR_PROCESSING_STATUS = "Error al procesar el estado de la historia clínica";
    public static final String ERROR_FETCHING_STATUS = "Error al obtener el estado de la historia clínica";
    public static final String STATUS_NOT_FOUND = "Estado no encontrado para la historia clínica del paciente proporcionado";
    public static final String ERROR_SENDING_TO_REVIEW = "Error al enviar la historia clínica a revisión";
    public static final String ERROR_FETCHING_STATUS_LIST = "Error al obtener la lista de estados de la historia clínica";

    // Dental Prophylaxis Request
    public static final String PATIENT_ID_CANNOT_BE_NULL = "El ID del paciente no puede ser nulo";
    public static final String QUESTION_ID_CANNOT_BE_NULL = "El ID de la pregunta no puede ser nulo";
    public static final String CLINICAL_HISTORY_ID_CANNOT_BE_NULL = "El ID de la historia clínica no puede ser nulo";
    public static final String FORM_SECTION_ID_CANNOT_BE_NULL = "El ID de la sección del formulario no puede ser nulo";

    public static final String FAILED_FETCH_DENTAL_PROPHYLAXIS = "Error al obtener la profilaxis dental";
    public static final String DENTAL_PROPHYLAXIS_NOT_FOUND = "No se encontró la profilaxis dental con ID: ";
    public static final String FAILED_DELETE_DENTAL_PROPHYLAXIS = "Error al eliminar la profilaxis dental";
    public static final String DUPLICATE_ENTRY = "Entrada duplicada";
    public static final String DENTAL_PROPHYLAXIS_NOT_FOUND_BY_SECTION = "No se encontró la profilaxis dental con el ID de la sección del formulario: ";
    public static final String FAILED_FETCH_DENTAL_PROPHYLAXIS_BY_PATIENT = "Error al obtener la profilaxis dental por ID de paciente";

    public static final String INVALID_STATUS = "Estatus no válido: ";

    public static final String SEMESTER_NOT_FOUND = "Semestre no encontrado";
    public static final String CURRENT_SEMESTER_INACTIVE = "No hay semestre activo";

    public static final String GROUPS_NOT_FOUND = "No se encontraron grupos para la carrera con ID: ";

    // FormComponent Request
    public static final String NOT_NULL_DESCRIPTION_FIELD = "El campo descripción no puede ser nulo";
    public static final String NOT_BLANK_DESCRIPTION_FIELD = "El campo descripción no puede estar en blanco";

    public static final String FAILED_CREATE_FORM_COMPONENT = "Error al crear el componente";
    public static final String FORM_COMPONENT_NOT_FOUND = "Componente no encontrado con id: %s";
    public static final String FAILED_TO_FETCH_FORM_COMPONENT = "Error al obtener el componente";
    public static final String FAILED_TO_FETCH_FORM_COMPONENTS = "Error al obtener los componentes";
    public static final String FAILED_UPDATE_FORM_COMPONENT = "Error al actualizar el componente";
    public static final String FAILED_DELETE_FORM_COMPONENT = "Error al eliminar el componente";

    // FormComponentToothFace request
    public static final String NOT_NULL_ID_FORM_COMPONENT = "El campo idFormComponent no puede ser nulo";
    public static final String POSITIVE_ID_FORM_COMPONENT = "El campo idFormComponent debe ser mayor a 0";

    public static final String NOT_NULL_ID_TOOTH_FACE_CONDITION = "El campo idToothFaceCondition no puede ser nulo";
    public static final String POSITIVE_ID_TOOTH_FACE_CONDITION = "El campo idToothFaceCondition debe ser mayor a 0";

    public static final String FAILED_CREATE_FORM_COMPONENT_TOOTH_FACE_CONDITION = "Error al crear la relación de condición de cara de diente y el componente";
    public static final String FORM_COMPONENT_TOOTH_FACE_CONDITION_NOT_FOUND = "Condición de cara de diente del componente no encontrada con id: %s";
    public static final String FAILED_TO_FETCH_FORM_COMPONENT_TOOTH_FACE_CONDITION = "Error al obtener la condición de cara de diente del componente";
    public static final String FAILED_TO_FETCH_FORM_COMPONENTS_TOOTH_FACE_CONDITION = "Error al obtener las condiciones de cara de diente de los componentes";
    public static final String FAILED_UPDATE_FORM_COMPONENT_TOOTH_FACE_CONDITION = "Error al actualizar la condición de cara de diente del componente";
    public static final String FAILED_DELETE_FORM_COMPONENT_TOOTH_FACE_CONDITION = "Error al eliminar la condición de cara de diente del componente";

    // FormComponentToothCondition request
    public static final String NOT_NULL_ID_TOOTH_CONDITION = "El campo idToothCondition no puede ser nulo";
    public static final String POSITIVE_ID_TOOTH_CONDITION = "El campo idToothCondition debe ser mayor a 0";

    public static final String FAILED_CREATE_FORM_COMPONENT_TOOTH_CONDITION = "Error al crear la relación de condición de diente y el componente";
    public static final String FORM_COMPONENT_TOOTH_CONDITION_NOT_FOUND = "Condición de diente del componente no encontrada con id: %s";
    public static final String FAILED_TO_FETCH_FORM_COMPONENT_TOOTH_CONDITION = "Error al obtener la condición de diente del componente";
    public static final String FAILED_TO_FETCH_FORM_COMPONENTS_TOOTH_CONDITION = "Error al obtener las condiciones de diente de los componentes";
    public static final String FAILED_UPDATE_FORM_COMPONENT_TOOTH_CONDITION = "Error al actualizar la condición de diente del componente";
    public static final String FAILED_DELETE_FORM_COMPONENT_TOOTH_CONDITION = "Error al eliminar la condición de diente del componente";

    // ScopeType request
    public static final String NOT_NULL_SCOPE_TYPE_ID = "El campo idScopeType no puede ser nulo";
    public static final String POSITIVE_SCOPE_TYPE_ID = "El campo idScopeType debe ser mayor a 0";
    public static final String NOT_NULL_SCOPE_TYPE = "El campo tipo de alcance no puede ser nulo";
    public static final String NOT_BLANK_SCOPE_TYPE = "El campo tipo de alcance no puede estar vacío";

    public static final String FAILED_CREATE_SCOPE_TYPE = "Error al crear el tipo de alcance";
    public static final String SCOPE_TYPE_NAME_EXISTS = "El nombre del tipo de alcance ya existe";
    public static final String SCOPE_TYPE_NOT_FOUND = "No se encontró el tipo de alcance con ID: ";
    public static final String FAILED_FETCH_SCOPE_TYPE = "Error al obtener el tipo de alcance";
    public static final String FAILED_FETCH_SCOPE_TYPES = "Error al obtener los tipos de alcance";
    public static final String FAILED_UPDATE_SCOPE_TYPE = "Error al actualizar el tipo de alcance";
    public static final String SCOPE_TYPE_NAME_DUPLICATED = "El nuevo nombre del tipo de alcance ya existe";
    public static final String FAILED_DELETE_SCOPE_TYPE = "Error al eliminar el tipo de alcance";

    // TreatmentScope Request
    public static final String NOT_BLANK_SCOPE_NAME = "El nombre del alcance no puede estar vacío";
    public static final String NOT_NULL_SCOPE_NAME = "El nombre del alcance no puede ser nulo";

    public static final String FAILED_CREATE_TREATMENT_SCOPE = "Error al crear el alcance del tratamiento";
    public static final String TREATMENT_SCOPE_NAME_EXISTS = "El nombre del alcance del tratamiento ya existe";
    public static final String TREATMENT_SCOPE_NOT_FOUND = "No se encontró el alcance del tratamiento con ID: ";
    public static final String FAILED_FETCH_TREATMENT_SCOPE = "Error al obtener el alcance del tratamiento";
    public static final String FAILED_FETCH_TREATMENT_SCOPES = "Error al obtener los alcances del tratamiento";
    public static final String FAILED_FETCH_TREATMENT_SCOPES_BY_TYPE = "Error al obtener los alcances del tratamiento por tipo";
    public static final String FAILED_UPDATE_TREATMENT_SCOPE = "Error al actualizar el alcance del tratamiento";
    public static final String FAILED_DELETE_TREATMENT_SCOPE = "Error al eliminar el alcance del tratamiento";

    // Treatment Request
    public static final String NOT_BLANK_TREATMENT_NAME = "El nombre del tratamiento no puede estar vacío";
    public static final String MAX_LENGTH_TREATMENT_NAME = "El nombre del tratamiento debe tener como máximo 100 caracteres";

    public static final String DIGITS_TREATMENT_COST = "El costo debe tener hasta 8 dígitos enteros y 2 decimales";

    public static final String NOT_NULL_CATALOG_ID = "El ID del catálogo de historia clínica no puede ser nulo";
    public static final String POSITIVE_CATALOG_ID = "El ID del catálogo de historia clínica debe ser un número positivo";

    // Treatment Messages
    public static final String TREATMENT_NAME_EXISTS = "El nombre del tratamiento ya existe";
    public static final String TREATMENT_NOT_FOUND = "No se encontró el tratamiento con ID: ";
    public static final String FAILED_CREATE_TREATMENT = "Error al crear el tratamiento";
    public static final String FAILED_FETCH_TREATMENT = "Error al obtener el tratamiento";
    public static final String FAILED_FETCH_TREATMENTS = "Error al obtener los tratamientos";
    public static final String FAILED_FETCH_TREATMENTS_BY_SCOPE = "Error al obtener tratamientos por alcance";
    public static final String FAILED_FETCH_TREATMENTS_BY_HISTORY = "Error al obtener tratamientos por historial clínico";
    public static final String FAILED_UPDATE_TREATMENT = "Error al actualizar el tratamiento";
    public static final String FAILED_DELETE_TREATMENT = "Error al eliminar el tratamiento";
    public static final String CLINICAL_HISTORY_CATALOG_NOT_FOUND = "No se encontró la historia clínica con ID: %s";
}
