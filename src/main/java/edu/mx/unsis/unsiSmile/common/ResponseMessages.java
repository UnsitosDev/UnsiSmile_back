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
    public static final String PASSWORD_INVALID_CHARACTER = "Carácter inválido o no reconocido en la contraseña";
    public static final String PASSWORD_WEAK_LENGTH = "Contraseña débil: debe incluir al menos 8 caracteres";
    public static final String PASSWORD_WEAK_LOWERCASE = "Contraseña débil: debe incluir al menos una letra minúscula";
    public static final String PASSWORD_WEAK_UPPERCASE = "Contraseña débil: debe incluir al menos una letra mayúscula";
    public static final String PASSWORD_WEAK_NUMBER = "Contraseña débil: debe incluir al menos un número";
    public static final String PASSWORD_WEAK_SPECIAL_CHAR = "Contraseña débil: debe incluir al menos un carácter especial (@$!%*?&)";
    public static final String PASSWORD_INVALID_SPECIFIC_CHAR = "Carácter inválido en la contraseña: '%s' no está permitido";

    public static final String PERSON_NOT_FOUND = "Persona no encontrada";
    public static final String PROFESSOR_NOT_FOUND = "Profesor no encontrado con número de empleado: ";

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

    public static final String STUDENT_NOT_FOUND = "Estudiante no encontrado";
    public static final String GROUP_NOT_FOUND = "Grupo no encontrado";
    public static final String ERROR_CREATING_STUDENT_GROUP = "Ocurrió un error al agregar un estudiante a un grupo.";

    public static final String INVALID_INPUT = "La entrada no es válida. Debe ser un número o una cadena.";
    public static final String FAILED_FETCH_STUDENTS_IN_GROUPS = "Error al obtener los estudiantes en los grupos.";
    public static final String FAILED_TO_FETCH_STUDENTS = "Error al obtener los estudiantes.";
}
