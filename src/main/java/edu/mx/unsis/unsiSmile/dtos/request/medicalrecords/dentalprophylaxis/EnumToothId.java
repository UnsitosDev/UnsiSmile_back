package edu.mx.unsis.unsiSmile.dtos.request.medicalrecords.dentalprophylaxis;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumToothId {
    D16("16"),
    D17("17"),
    D11("11"),
    D21("21"),
    D26("26"),
    D27("27"),
    D36("36"),
    D37("37"),
    D31("31"),
    D41("41"),
    D46("46"),
    D47("47");
    private final String idTooth;

    public static EnumToothId getCodeFromIdTooth(String idTooth) {
        for (EnumToothId tooth : EnumToothId.values()) {
            if (tooth.getIdTooth().equals(idTooth)) {
                return tooth;
            }
        }
        return null;
    }
}
