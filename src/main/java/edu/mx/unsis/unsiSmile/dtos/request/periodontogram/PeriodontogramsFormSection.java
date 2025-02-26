package edu.mx.unsis.unsiSmile.dtos.request.periodontogram;

//almacena los identificadores de las secciones de los formularios de los periodontogramas
public enum PeriodontogramsFormSection {
    //identificador de la seccion de historia clinica general
    GENERAL_CLINICAL_HISTORY(8L);

    private final long id;

    PeriodontogramsFormSection(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
