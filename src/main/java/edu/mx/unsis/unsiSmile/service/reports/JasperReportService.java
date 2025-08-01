package edu.mx.unsis.unsiSmile.service.reports;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Map;

@Service
public class JasperReportService {
    
    public byte[] generatePdfReport(String reportPath, Map<String, Object> parameters) {
        try {
            ClassPathResource reportResource = new ClassPathResource(reportPath);
            ClassPathResource logoResource = new ClassPathResource("reports/logo.png");
            
            InputStream reportStream = reportResource.getInputStream();
            InputStream logoStream = logoResource.getInputStream();

            if (reportStream == null) {
                throw new AppException(ResponseMessages.ERROR_REPORT_NOT_FOUND + reportPath, HttpStatus.INTERNAL_SERVER_ERROR);
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
            throw new AppException(ResponseMessages.ERROR_GENERATING_REPORT + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public byte[] generatePdfReportWithDataSource(String reportPath, Map<String, Object> parameters, JRBeanCollectionDataSource dataSource) {
        try {
            ClassPathResource reportResource = new ClassPathResource(reportPath);
            ClassPathResource logoResource = new ClassPathResource("reports/logo.png");

            InputStream reportStream = reportResource.getInputStream();
            InputStream logoStream = logoResource.getInputStream();

            if (reportStream == null) {
                throw new AppException(ResponseMessages.ERROR_REPORT_NOT_FOUND + reportPath, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            if (!parameters.containsKey("logo")) {
                parameters.put("logo", logoStream);
            }

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint);

        } catch (Exception e) {
            throw new AppException(ResponseMessages.ERROR_GENERATING_REPORT + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}