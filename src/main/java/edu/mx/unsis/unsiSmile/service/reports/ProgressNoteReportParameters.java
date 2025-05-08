package edu.mx.unsis.unsiSmile.service.reports;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ProgressNoteReportParameters {
    private String name;
    private java.sql.Date birthDate;
    private int age;
    private String gender;
    private String origin;
    private int idProgressNote;
    private String bloodPressure;
    private int temperature;
    private int heartRate;
    private int respirationRate;
    private int oxygenSaturation;
    private String diagnosis;
    private String prognosis;
    private String treatment;
    private String indications;
    private String creationDate;
    private String student;
    private String professor;

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("birthDate", birthDate);
        map.put("age", String.valueOf(age)); // Convertir a String
        map.put("gender", gender);
        map.put("origin", origin);
        map.put("idProgressNote", idProgressNote);
        map.put("bloodPressure", bloodPressure);
        map.put("temperature", temperature);
        map.put("heartRate", heartRate);
        map.put("respirationRate", respirationRate);
        map.put("oxygenSaturation", oxygenSaturation);
        map.put("diagnosis", diagnosis);
        map.put("prognosis", prognosis);
        map.put("treatment", treatment);
        map.put("indications", indications);
        map.put("creationDate", creationDate);
        map.put("student", student);
        map.put("professor", professor);
        return map;
    }
}
