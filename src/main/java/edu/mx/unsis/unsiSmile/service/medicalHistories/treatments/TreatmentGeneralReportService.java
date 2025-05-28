package edu.mx.unsis.unsiSmile.service.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.response.AdminDashboardResponse;
import edu.mx.unsis.unsiSmile.dtos.response.TreatmentCountResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.service.DashboardService;
import edu.mx.unsis.unsiSmile.service.reports.JasperReportService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
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

    private final DashboardService dashboardService;
    private final JasperReportService jasperReportService;

    @Transactional(readOnly = true)
    public ResponseEntity<byte[]> generateGeneralTreatmentReport() {
        try {
            // Obtenemos los datos del dashboard de administrador que contiene datos generales
            AdminDashboardResponse dashboard = dashboardService.getAdminDashboardMetrics();

            // Preparamos los datos para el informe
            List<Map<String, Object>> reportDataList = prepareDataForGeneralReport(dashboard.getTreatments());

            // Configuramos los parámetros principales del reporte
            Map<String, Object> parameters = new HashMap<>();
            // Ya no se pasa el parámetro de treatment pues lo eliminamos del template

            // Generamos el PDF utilizando JasperReportService
            byte[] pdfBytes = jasperReportService.generatePdfReportWithDataSource(
                    "reports/treatment-general.jrxml",
                    parameters,
                    new JRBeanCollectionDataSource(reportDataList));

            // Devolvemos la respuesta con el PDF
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"treatment_general_report.pdf\"")
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
