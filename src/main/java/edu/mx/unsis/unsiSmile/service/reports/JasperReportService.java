package edu.mx.unsis.unsiSmile.service.reports;

import edu.mx.unsis.unsiSmile.exceptions.AppException;
import net.sf.jasperreports.engine.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Map;

@Service
public class JasperReportService {
    
    public byte[] generatePdfReport(String reportPath, Map<String, Object> parameters) {
        try {
            // Obtener el archivo JRXML como InputStream
            InputStream reportStream = getClass().getResourceAsStream("/reports/progress_note_report.jrxml");
            if (reportStream == null) {
                throw new AppException("No se pudo encontrar el archivo de reporte", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            // Compilar el archivo JRXML
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
            
            // Llenar el reporte con los parámetros
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            
            // Exportar a PDF
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e) {
            throw new AppException("Error al generar el reporte PDF: " + e.getMessage(), 
                                 HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
