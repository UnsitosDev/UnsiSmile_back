package edu.mx.unsis.unsiSmile.dtos.request.medicalHistories;

public enum TypeOdontogram {
    INITIAL_ODONTOGRAM(6L),
    FINAL_ODONTOGRAM(7L);


    private final Long value;

    TypeOdontogram(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }
}