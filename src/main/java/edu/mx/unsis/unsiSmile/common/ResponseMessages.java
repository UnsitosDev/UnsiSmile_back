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

    public static final String PATIENT_NOT_FOUND = "Paciente no encontrado";
    public static final String ERROR_CREATING_PROGRESS_NOTE = "Error al crear la nota de evolución. ";
    public static final String ERROR_FETCHING_PROGRESS_NOTES = "Error al obtener las notas de evolución del paciente.";
    public static final String PATIENT_NOT_FOUND_PROGRESS_NOTES = "No se encontraron notas de evolución para el paciente con ID: ";
    public static final String PATIENT_ID_CANNOT_BE_EMPTY = "El ID del paciente no puede estar vacío.";
    public static final String REQUEST_CANNOT_BE_NULL = "El request no puede ser nulL.";

    public static final String STUDENT_NOT_FOUND = "Estudiante no encontrado";
    public static final String PROFESSOR_CLINICAL_AREA_NOT_FOUND = "Profesor del área clínica no encontrado,";

    public static final String FILE_NOT_FOUND = "Archivo no encontrado.";
    public static final String ERROR_WHILE_DOWNLOAD_FILE = "Error durante la descarga del archivo.";
}
