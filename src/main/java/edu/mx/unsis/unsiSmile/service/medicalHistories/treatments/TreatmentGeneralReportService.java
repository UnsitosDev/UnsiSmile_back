package edu.mx.unsis.unsiSmile.service.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.response.StudentDashboardResponse;
import edu.mx.unsis.unsiSmile.dtos.response.TreatmentCountResponse;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.treatments.TreatmentDetailResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.model.students.StudentModel;
import edu.mx.unsis.unsiSmile.repository.students.IStudentRepository;
import edu.mx.unsis.unsiSmile.service.DashboardService;
import edu.mx.unsis.unsiSmile.service.reports.JasperReportService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TreatmentGeneralReportService {

    private final IStudentRepository studentRepository;
    private final DashboardService dashboardService;
    private final JasperReportService jasperReportService;
    private final TreatmentDetailService treatmentDetailService;


    @Transactional(readOnly = true)
    public ResponseEntity<byte[]> generateGeneralTreatmentReportByStudent(String idStudent) {
        try {
            // Obtenemos el estudiante
            StudentModel student = studentRepository.findById(idStudent)
                    .orElseThrow(() -> new AppException(ResponseMessages.STUDENT_NOT_FOUND, HttpStatus.NOT_FOUND));

            // Obtenemos el dashboard del estudiante que contiene los datos de tratamientos
            StudentDashboardResponse dashboard = dashboardService.getStudentDashboardMetrics(idStudent);
            
            // Obtenemos el grupo del estudiante
            String groupName = "";
            Page<TreatmentDetailResponse> treatmentsPage = treatmentDetailService.getAllTreatmentDetailsByStudentForReport(
                    Pageable.unpaged(), idStudent, null);
            List<TreatmentDetailResponse> treatments = new ArrayList<>(treatmentsPage.getContent());
            if (!treatments.isEmpty() && treatments.get(0).getStudent() != null && treatments.get(0).getStudent().getGroup() != null) {
                groupName = treatments.get(0).getStudent().getGroup();
            }

            // Preparamos los datos para el informe
            List<Map<String, Object>> reportDataList = prepareDataForGeneralReport(dashboard.getTreatments());

            // Configuramos los parámetros principales del reporte
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("nameStudent", student.getPerson().getFullName());
            parameters.put("group", groupName);

            // Generamos el PDF utilizando JasperReportService
            byte[] pdfBytes = jasperReportService.generatePdfReportWithDataSource(
                    "reports/treatment-general.jrxml",
                    parameters,
                    new JRBeanCollectionDataSource(reportDataList));

            // Devolvemos la respuesta con el PDF
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"treatment_general_report_" + idStudent + ".pdf\"")
                    .body(pdfBytes);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ResponseMessages.ERROR_WHILE_DOWNLOAD_FILE, HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    private List<Map<String, Object>> prepareDataForGeneralReport(TreatmentCountResponse treatments) {
        List<Map<String, Object>> dataList = new ArrayList<>();

        // Agregamos cada tipo de tratamiento a la lista si tiene un valor mayor a cero
        addTreatmentIfPresent(dataList, "Resinas", treatments.getResins());
        addTreatmentIfPresent(dataList, "Profilaxis", treatments.getProphylaxis());
        addTreatmentIfPresent(dataList, "Fluorosis", treatments.getFluorosis());
        addTreatmentIfPresent(dataList, "Selladores de fosetas y fisuras", treatments.getPitAndFissureSealers());
        addTreatmentIfPresent(dataList, "Exodoncias", treatments.getExtractions());
        addTreatmentIfPresent(dataList, "Prótesis removible", treatments.getRemovableProsthesis());
        addTreatmentIfPresent(dataList, "Prótesis fija", treatments.getProsthesisRemovable());
        addTreatmentIfPresent(dataList, "Prostodoncia", treatments.getProsthodontics());
        addTreatmentIfPresent(dataList, "Endodoncias", treatments.getRootCanals());
        addTreatmentIfPresent(dataList, "Raspado y alisado", treatments.getScrapedAndSmoothed());
        addTreatmentIfPresent(dataList, "Cerrado y abierto", treatments.getClosedAndOpen());
        addTreatmentIfPresent(dataList, "Cuña distal", treatments.getDistalWedges());
        addTreatmentIfPresent(dataList, "Pulpotomía y corona", treatments.getPulpotomyAndCrowns());
        addTreatmentIfPresent(dataList, "Pulpectomía y corona", treatments.getPulpectomyAndCrowns());

        // Si no hay datos, crear al menos una fila vacía
        if (dataList.isEmpty()) {
            Map<String, Object> emptyRow = new HashMap<>();
            emptyRow.put("treatmentName", "No hay tratamientos registrados");
            emptyRow.put("count", 0L);
            dataList.add(emptyRow);
        }

        return dataList;
    }

    private void addTreatmentIfPresent(List<Map<String, Object>> dataList, String treatmentName, Long count) {
        if (count != null && count > 0) {
            Map<String, Object> item = new HashMap<>();
            item.put("treatmentName", treatmentName);
            item.put("count", count);
            dataList.add(item);
        }
    }
}
