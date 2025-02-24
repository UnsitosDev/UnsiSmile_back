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

    // Cambio de contraseña
    public static final String CHANGE_PASSWORD = "Establece tu nueva contraseña de UnsiSmile";
}
