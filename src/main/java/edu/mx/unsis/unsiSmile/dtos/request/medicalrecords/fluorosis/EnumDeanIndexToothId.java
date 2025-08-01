package edu.mx.unsis.unsiSmile.dtos.request.medicalrecords.fluorosis;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumDeanIndexToothId {
    D13("13"),
    D12("12"),
    D11("11"),
    D21("21"),
    D22("22"),
    D23("23");
    private final String idTooth;

    public static EnumDeanIndexToothId getCodeFromDeanIndexIdTooth(String idTooth) {
        for (EnumDeanIndexToothId tooth : EnumDeanIndexToothId.values()) {
            if (tooth.getIdTooth().equals(idTooth)) {
                return tooth;
            }
        }
        return null;
    }
}
