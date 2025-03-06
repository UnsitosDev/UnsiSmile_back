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
    public static final String USER_NOT_FOUND = "Usuario no encontrado";
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
    public static final String PATIENT_ID_CANNOT_BE_EMPTY = "El ID del paciente no puede estar vacío.";
    public static final String REQUEST_CANNOT_BE_NULL = "El request no puede ser nulL.";

    public static final String STUDENT_NOT_FOUND = "Estudiante no encontrado";
    public static final String PROFESSOR_CLINICAL_AREA_NOT_FOUND = "Profesor del área clínica no encontrado";

    public static final String FILE_NOT_FOUND = "Archivo no encontrado.";
    public static final String ERROR_WHILE_DOWNLOAD_FILE = "Error durante la descarga del archivo.";

    public static final String PROFESSOR_NOT_FOUND = "Profesor no encontrado";
    public static final String INVALID_ROLE = "Rol no válido";
  
    public static final String PERSON_NOT_FOUND = "Persona no encontrada";

    // ADDRESS VALIDATION REQUEST
    public static final String HOUSING_NULL = "La vivienda no puede ser nula.";
    public static final String ID_HOUSING_BLANK = "El ID de la vivienda no puede estar vacío.";
    public static final String ID_HOUSING_SIZE = "El ID de la vivienda debe tener 2 caracteres.";
    public static final String CATEGORY_BLANK = "La categoría de la vivienda no puede estar vacía.";
    public static final String HOUSING_CREATE_FAILED = "Error al buscar o crear la vivienda";

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

    public static final String ADMINISTRATOR_NOT_FOUND = "Administrador no encontrado con el número de empleado: ";
    public static final String ERROR_CREATING_ADMINISTRATOR = "Error al crear el administrador.";
    public static final String ERROR_UPDATING_ADMINISTRATOR = "Error al actualizar el administrador.";

    public static final String CURP_LENGTH_MISMATCH = "La CURP generada no coincide en longitud con la CURP proporcionada.";
    public static final String CURP_DATA_MISMATCH = "La CURP generada no coincide con los datos proporcionados.";
}
