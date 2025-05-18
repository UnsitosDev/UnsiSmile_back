package edu.mx.unsis.unsiSmile.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {
    // Status active
    public static final String ACTIVE = "A";
    public static final String INACTIVE = "I";

    // Answer Type
    public static final String CATALOG = "CATALOG";

    //upload files path
    public static final String UPLOAD_PATH = "uploads/";

    public static final String ODONTOGRAM = "Odontograma";
    public static final String PROPHYLAXIS = "Profilaxis Dental";
    public static final String FLUOROSIS =  "Fluorosis";

    public static final String TOOTH = "Diente";

    public static final String GENERAL_MEDICAL_RECORD = "General";

    // Email
    public static final String RECOVERY_PASSWORD = "RECOVERY PASSWORD";
    public static final String RECOVERY_PASSWORD_SUBJECT = "Recuperación de contraseña";
    public static final String RECOVERY_PASSWORD_TEXT_BODY = "Tu código de recuperación es: {code}\nEste código expirará en 10 minutos.";
}
