package edu.mx.unsis.unsiSmile.dtos.response.dashboards;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfessorDashboardResponse {
    private Integer totalGroups;
    private Long totalStudents;
}