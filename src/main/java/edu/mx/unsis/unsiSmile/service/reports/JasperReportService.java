package edu.mx.unsis.unsiSmile.service.reports;

import edu.mx.unsis.unsiSmile.exceptions.AppException;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Map;

@Service
public class JasperReportService {
    
    private static final String ERROR_REPORT_NOT_FOUND = "No se pudo encontrar el archivo de reporte: ";
    private static final String ERROR_GENERATING_REPORT = "Error al generar el reporte PDF: ";
    
    public byte[] generatePdfReport(String reportPath, Map<String, Object> parameters) {
        try {
            ClassPathResource reportResource = new ClassPathResource(reportPath);
            ClassPathResource logoResource = new ClassPathResource("reports/logo.png");
            
            InputStream reportStream = reportResource.getInputStream();
            InputStream logoStream = logoResource.getInputStream();

            if (reportStream == null) {
                throw new AppException(ERROR_REPORT_NOT_FOUND + reportPath, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            // Compilar el reporte
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
            
            // Asegurarnos que el logo existe
            if (!parameters.containsKey("logo")) {
                parameters.put("logo", logoStream);
            }
            
            // Llenar el reporte con los parámetros
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            
            return JasperExportManager.exportReportToPdf(jasperPrint);
            
        } catch (Exception e) {
            throw new AppException(ERROR_GENERATING_REPORT + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
