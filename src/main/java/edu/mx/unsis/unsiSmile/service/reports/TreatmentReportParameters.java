package edu.mx.unsis.unsiSmile.service.reports;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class TreatmentReportParameters {
    private String nameStudent;
    private String group;

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("nameStudent", nameStudent);
        map.put("group", group);
        return map;
    }
}