package edu.mx.unsis.unsiSmile.service.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.treatments.TreatmentDetailResponse;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.treatments.TreatmentDetailToothResponse;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.treatments.TreatmentResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.model.students.StudentModel;
import edu.mx.unsis.unsiSmile.service.students.StudentService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
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

import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TreatmentReportService {

    private final TreatmentDetailService treatmentDetailService;
    private final StudentService studentService;
    private final TreatmentService treatmentService;

    @Transactional(readOnly = true)
    public ResponseEntity<byte[]> generateTreatmentReportByStudent(String idStudent, Long idTreatment) {
        try {
            // Obtenemos el estudiante
            StudentModel student = studentService.getStudentModel(idStudent);

            // Obtenemos el nombre del tratamiento si se proporciona un idTreatment
            String treatmentName = "TODOS LOS TRATAMIENTOS";
            if (idTreatment != null) {
                try {
                    TreatmentResponse treatment = treatmentService.getTreatmentById(idTreatment);
                    treatmentName = treatment.getName().toUpperCase();
                } catch (Exception e) {
                    // Si no se puede obtener el tratamiento, mantenemos el valor por defecto
                }
            }

            // Obtenemos todos los tratamientos del estudiante usando el método para reportes
            Page<TreatmentDetailResponse> treatmentsPage = treatmentDetailService.getAllTreatmentDetailsByStudent(
                    Pageable.unpaged(), idStudent, idTreatment);

            List<TreatmentDetailResponse> treatments = new ArrayList<>(treatmentsPage.getContent());
            
            // Obtenemos el nombre del grupo del primer tratamiento, o vacío si no hay tratamientos
            String groupName = "";
            if (!treatments.isEmpty() && treatments.getFirst().getStudent() != null && treatments.getFirst().getStudent().getGroup() != null) {
                groupName = treatments.getFirst().getStudent().getGroup();
            }

            // Calcular si hay algún tratamiento con dientes asociados
            boolean showTeethColumn = treatments.stream()
                .anyMatch(t -> t.getTeeth() != null && !t.getTeeth().isEmpty());

            // Preparamos los datos para el informe
            List<Map<String, Object>> reportDataList = prepareDataForReport(treatments);

            // Configuramos los parámetros principales del reporte
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("nameStudent", student.getPerson().getFullName());
            parameters.put("group", groupName);
            parameters.put("treatment", treatmentName);
            parameters.put("showTeethColumn", showTeethColumn); // <-- aquí se pasa el parámetro
            parameters.put("logo", new ClassPathResource("reports/logo.png").getInputStream());

            // Generamos el PDF
            byte[] pdfBytes = generatePdfReport(parameters, reportDataList);

            // Devolvemos la respuesta con el PDF
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"treatment_report_" + idStudent + ".pdf\"")
                    .body(pdfBytes);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ResponseMessages.ERROR_WHILE_DOWNLOAD_FILE, HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    private List<Map<String, Object>> prepareDataForReport(List<TreatmentDetailResponse> treatments) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<Map<String, Object>> dataList = new ArrayList<>();

        for (TreatmentDetailResponse treatment : treatments) {
            // Solo incluir tratamientos finalizados
            if (!"FINISHED".equals(treatment.getStatus())) {
                continue;
            }
            
            // Si el tratamiento tiene dientes asociados, creamos una entrada por cada diente
            if (treatment.getTeeth() != null && !treatment.getTeeth().isEmpty()) {
                for (TreatmentDetailToothResponse tooth : treatment.getTeeth()) {
                    Map<String, Object> item = new HashMap<>();
                    item.put("teeth", tooth.getIdTooth()); // Usamos el ID del diente directamente
                    item.put("creationDate", treatment.getEndDate() != null ? 
                            treatment.getEndDate().format(dateFormatter) : "");
                    item.put("patientName", treatment.getPatient() != null ? treatment.getPatient().getName() : "");
                    item.put("patientClinicalHistoryId", treatment.getPatient() != null && treatment.getPatient().getMedicalRecordNumber() != null
                            ? String.valueOf(treatment.getPatient().getMedicalRecordNumber()) : "");
                    item.put("professorName", treatment.getReviewProfessor() != null ? treatment.getReviewProfessor().getProfessorName() : "");
                    dataList.add(item);
                }
            } else {
                // Si no tiene dientes, creamos una sola entrada para el tratamiento
                Map<String, Object> item = new HashMap<>();
                item.put("teeth", "N/A");
                item.put("creationDate", treatment.getEndDate() != null ? 
                        treatment.getEndDate().format(dateFormatter) : "");
                item.put("patientName", treatment.getPatient() != null ? treatment.getPatient().getName() : "");
                item.put("patientClinicalHistoryId", treatment.getPatient() != null && treatment.getPatient().getMedicalRecordNumber() != null
                        ? String.valueOf(treatment.getPatient().getMedicalRecordNumber()) : "");
                item.put("professorName", treatment.getReviewProfessor() != null ? treatment.getReviewProfessor().getProfessorName() : "");
                dataList.add(item);
            }
        }

        // Si no hay datos, crear al menos una fila vacía
        if (dataList.isEmpty()) {
            Map<String, Object> emptyRow = new HashMap<>();
            emptyRow.put("teeth", "");
            emptyRow.put("creationDate", "");
            emptyRow.put("patientName", "");
            emptyRow.put("patientClinicalHistoryId", "");
            emptyRow.put("professorName", "");
            dataList.add(emptyRow);
        }

        return dataList;
    }

    private byte[] generatePdfReport(Map<String, Object> parameters, List<Map<String, Object>> dataList) throws Exception {
        ClassPathResource reportResource = new ClassPathResource("reports/treatment.jrxml");
        
        try (InputStream reportStream = reportResource.getInputStream()) {
            // Compilamos el informe
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
            
            // Crear la fuente de datos a partir de la lista de mapas
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dataList);
            
            // Llenar el informe con los parámetros y la fuente de datos
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            
            // Exportar a PDF
            return net.sf.jasperreports.engine.JasperExportManager.exportReportToPdf(jasperPrint);
        }
    }
}
