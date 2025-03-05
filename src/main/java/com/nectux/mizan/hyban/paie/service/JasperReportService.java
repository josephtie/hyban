package com.nectux.mizan.hyban.paie.service;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JasperReportService {

    public byte[] generateReport(String reportName, List<?> data, Map<String, Object> parameters) throws Exception {
        // Charger le rapport principal
        InputStream reportStream = new ClassPathResource("reports/" + reportName + ".jrxml").getInputStream();
        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

        // Ajouter les sous-rapports
        parameters.put("SUBREPORT_DIR", new ClassPathResource("reports/").getURL().toString());

        // Remplir le rapport avec des données
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        // Exporter en PDF
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}

