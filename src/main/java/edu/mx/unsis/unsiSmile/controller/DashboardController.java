package edu.mx.unsis.unsiSmile.controller;

import edu.mx.unsis.unsiSmile.dtos.response.AdminDashboardResponse;
import edu.mx.unsis.unsiSmile.dtos.response.ProfessorDashboardResponse;
import edu.mx.unsis.unsiSmile.dtos.response.StudentDashboardResponse;
import edu.mx.unsis.unsiSmile.service.DashboardService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Dashboard")
@RestController
@RequestMapping("/unsismile/api/v1/dashboards")
@AllArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;

    @GetMapping("/students")
    public ResponseEntity<StudentDashboardResponse> getStudentDashboard(@RequestParam String enrollment) {
        StudentDashboardResponse dashboard = dashboardService.getStudentDashboardMetrics(enrollment);
        return ResponseEntity.ok(dashboard);
    }

    @GetMapping("/professors")
    public ResponseEntity<ProfessorDashboardResponse> getProfessorDashboard(@RequestParam String employeeNumber) {
        ProfessorDashboardResponse dashboard = dashboardService.getProfessorDashboardMetrics(employeeNumber);
        return ResponseEntity.ok(dashboard);
    }

    @GetMapping("/admins")
    public ResponseEntity<AdminDashboardResponse> getAdminDashboard() {
        AdminDashboardResponse dashboard = dashboardService.getAdminDashboardMetrics();
        return ResponseEntity.ok(dashboard);
    }
}