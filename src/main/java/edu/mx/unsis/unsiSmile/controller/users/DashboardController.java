package edu.mx.unsis.unsiSmile.controller.users;

import edu.mx.unsis.unsiSmile.dtos.response.users.dashboards.AdminDashboardResponse;
import edu.mx.unsis.unsiSmile.dtos.response.users.dashboards.ClinicalSupervisorDashboardResponse;
import edu.mx.unsis.unsiSmile.dtos.response.users.dashboards.ProfessorDashboardResponse;
import edu.mx.unsis.unsiSmile.dtos.response.users.dashboards.StudentDashboardResponse;
import edu.mx.unsis.unsiSmile.service.users.DashboardService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Dashboard")
@RestController
@RequestMapping("/unsismile/api/v1/dashboards")
@AllArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/students")
    public ResponseEntity<StudentDashboardResponse> getStudentDashboard() {
        StudentDashboardResponse dashboard = dashboardService.getStudentDashboard();
        return ResponseEntity.ok(dashboard);
    }

    @GetMapping("/professors")
    public ResponseEntity<ProfessorDashboardResponse> getProfessorDashboard() {
        ProfessorDashboardResponse dashboard = dashboardService.getProfessorDashboardMetrics();
        return ResponseEntity.ok(dashboard);
    }

    @GetMapping("/admins")
    public ResponseEntity<AdminDashboardResponse> getAdminDashboard() {
        AdminDashboardResponse dashboard = dashboardService.getAdminDashboardMetrics();
        return ResponseEntity.ok(dashboard);
    }

    @GetMapping("/clinical-supervisors")
    public ResponseEntity<ClinicalSupervisorDashboardResponse> getClinicalSupervisorDashboard() {
        ClinicalSupervisorDashboardResponse dashboard = dashboardService.getClinicalSupervisorDashboardMetrics();
        return ResponseEntity.ok(dashboard);
    }
}