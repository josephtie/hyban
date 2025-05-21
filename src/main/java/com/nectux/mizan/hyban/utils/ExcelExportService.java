package com.nectux.mizan.hyban.utils;

import com.nectux.mizan.hyban.paie.entity.BulletinPaie;
import com.nectux.mizan.hyban.paie.repository.BulletinPaieRepository;
import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.personnel.entity.ContratPersonnel;
import com.nectux.mizan.hyban.personnel.entity.Personnel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ExcelExportService {

    @Autowired
    BulletinPaieRepository bulletinPaieRepository;
    private static final String EXPORT_DIR = "C:/exports/";







public File exporterBulletinsAvecRib(PeriodePaie periodePaie) throws IOException {
    String dateStr = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    File file = new File("C:/exports/paie_export_" + dateStr + ".xlsx");

    try (Workbook workbook = new XSSFWorkbook()) {
        Sheet sheet = workbook.createSheet("Bulletins");

        // Création des en-têtes
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Référence (Numéro ou IBAN)");
        header.createCell(1).setCellValue("Montant (FCFA)");
        header.createCell(2).setCellValue("Nom du Destinataire (obligatoire)");
        header.createCell(3).setCellValue("Commentaire (facultatif)");
        header.createCell(4).setCellValue("Type d’opération (obligatoire)");

        // Récupération des données
        List<BulletinPaie> bulletins = bulletinPaieRepository.findByPeriodePaieAndCalculer(periodePaie,true);

        int rowNum = 1;
        for (BulletinPaie bulletin : bulletins) {
            ContratPersonnel contrat = bulletin.getContratPersonnel();
            Personnel personnel = contrat.getPersonnel();

            String reference = personnel.getRib();
            if (reference == null || reference.isBlank()) {
                reference = personnel.getNumeroGuichet() + personnel.getNumeroCompte();
            }

            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(reference);
            row.createCell(1).setCellValue(bulletin.getNetapayer().doubleValue());
            row.createCell(2).setCellValue(personnel.getNom() + " " + personnel.getPrenom());
            row.createCell(3).setCellValue("Salaire " + bulletin.getPeriodePaie().getMois().getMois()+" "+bulletin.getPeriodePaie().getAnnee().getAnnee());
            row.createCell(4).setCellValue("Virement");
        }

        // Sauvegarde du fichier
        try (FileOutputStream fos = new FileOutputStream(file)) {
            workbook.write(fos);
        }
    }

    return file;
}
}
