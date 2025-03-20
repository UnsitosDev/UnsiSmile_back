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
    
    public byte[] generatePdfReport(String reportPath, Map<String, Object> parameters) {
        try {
            InputStream jasperStream = new ClassPathResource(reportPath).getInputStream();
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e) {
            throw new AppException("Error al generar el reporte PDF: " + e.getMessage(), 
                                 HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
