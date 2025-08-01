package edu.mx.unsis.unsiSmile.model.medicalrecords;

import lombok.Getter;

@Getter
public enum EMedicalRecords {
        GENERAL(1, "General"),
        PROTESIS_BUCAL(2, "Prótesis bucal"),
        PERIODONCIA(3, "Periodoncia"),
        OPERATORIA_DENTAL(4, "Operatoria dental"),
        CIRUGIA_BUCAL(5, "Cirugía bucal"),
        ODONTOLOGIA_PREVENTIVA(6, "Odontología preventiva y salud pública"),
        ENDODONCIA(7, "Endodoncia"),
        PULPOTOMIA(8, "Pulpotomía"),
        PULPECTOMIA(9, "Pulpectomía");

        private final int id;
        private final String description;

        EMedicalRecords(int id, String description) {
            this.id = id;
            this.description = description;
        }
}