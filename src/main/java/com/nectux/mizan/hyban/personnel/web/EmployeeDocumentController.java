package com.nectux.mizan.hyban.personnel.web;

import com.nectux.mizan.hyban.personnel.dto.EmployeeDocumentDTO;
import com.nectux.mizan.hyban.personnel.entity.DocumentType;
import com.nectux.mizan.hyban.personnel.entity.EmployeeDocument;
import com.nectux.mizan.hyban.personnel.entity.Personnel;
import com.nectux.mizan.hyban.personnel.entity.StorageLocation;
import com.nectux.mizan.hyban.personnel.repository.DocumentTypeRepository;
import com.nectux.mizan.hyban.personnel.repository.EmployeeDocumentRepository;
import com.nectux.mizan.hyban.personnel.repository.PersonnelRepository;
import com.nectux.mizan.hyban.personnel.repository.StorageLocationRepository;
import com.nectux.mizan.hyban.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/personnel/documents")
public class EmployeeDocumentController {


    @Autowired
    private PersonnelRepository personnelRepository;
    @Autowired
    private EmployeeDocumentRepository repository;

    @Autowired
    private DocumentTypeRepository documentTypeRepository;

    @Autowired
    private StorageLocationRepository storageLocationRepository;

    @GetMapping
    public List<EmployeeDocument> all() {
        return repository.findAll();
    }

    @PostMapping
    public EmployeeDocument create(@RequestBody EmployeeDocument doc) {
        return repository.save(doc);
    }

    @GetMapping("/employeId")
    public EmployeeDocumentDTO byEmployee( @RequestParam("idpersonnel") Long idPersonnel) {
     List<EmployeeDocument>   dtos= repository.findByPersonnelId(idPersonnel);
//            .stream()
//            .filter(d -> d.getPersonnel().getId().equals(idPersonnel))
//            .toList();

        EmployeeDocumentDTO response = new EmployeeDocumentDTO();
        response.setRows(dtos);
        response.setResult(true);
        return response;
    }



        @Value("${app.upload-dir}")
        private String uploadBasePath;

        @PostMapping("/upload")
        public EmployeeDocumentDTO uploadDocument(
                @RequestParam("fichierDocument") MultipartFile uploadfile,
                @RequestParam("idPersonnel") Long employeeId,
                @RequestParam("idDocument") Long typeId,
                @RequestParam("dateDepot") String dtedepot,
                @RequestParam("statutpresent") Boolean statutpresent,
                @RequestParam("numeroReference") String numeroReference,
                @RequestParam("idStorage") Long locationId,
                @RequestParam(value = "description", required = false) String description,HttpServletRequest request) throws Exception {

            // Récupération des entités
            EmployeeDocumentDTO employeeDocumentDTO=new EmployeeDocumentDTO();
            DocumentType type = documentTypeRepository.findById(typeId).orElseThrow();
            StorageLocation location = storageLocationRepository.findById(locationId).orElseThrow();
            Personnel pers = personnelRepository.findById(employeeId).orElseThrow();

            // Nettoyage du nom pour le chemin
            String safeLibelle = location.getNom().replaceAll("[^a-zA-Z0-9_-]", "_");

            // Construction du chemin complet (relatif à la racine de l'application)
            // Détection de l'environnement (local vs Linux)
            String reportsPath;
            if (Files.exists(Paths.get("src/main/resources/uploads"))) {
                // Mode développement : accès direct au répertoire des ressources
                reportsPath = "src/main/resources/uploads/documents";
            } else {
                reportsPath = request.getSession().getServletContext().getRealPath( "hyban/uploads/documents");
            }

            File folder = Paths.get(reportsPath, safeLibelle, String.valueOf(pers.getMatricule())).toAbsolutePath().toFile();
            if (!folder.exists()) folder.mkdirs();

            // Création du fichier
            String extension = uploadfile.getOriginalFilename().substring(uploadfile.getOriginalFilename().lastIndexOf("."));
            String uniqueName = pers.getMatricule() + "_"+ uploadfile.getOriginalFilename().toString() + extension;
            File dest = new File(folder, uniqueName);
            uploadfile.transferTo(dest);

            // Chemin relatif pour stockage en base (à partir de `uploads/`)
            String relativePath = Paths.get(uploadBasePath, safeLibelle, String.valueOf(employeeId), uniqueName).toString();

            // Enregistrement en base
            EmployeeDocument document = new EmployeeDocument();
            document.setPersonnel(pers);
            document.setDocumentType(type);
            document.setDateDepot(Utils.stringToDate(dtedepot,"dd/MM/yyyy"));
            document.setStorageLocation(location);
            document.setUrlFichier(relativePath); // Chemin relatif
            document.setRemarques(description);
            document.setNumeroReference(numeroReference);
            document.setPresent(statutpresent);

            document=repository.save(document);
            Map<String, Object> response = new HashMap<>();
            response.put("result", true);
            response.put("message", "Fichier sauvegardé : " + relativePath);
            response.put("filePath", relativePath);
            employeeDocumentDTO.setResult(true);
            employeeDocumentDTO.setRow(document);
            return employeeDocumentDTO;
        }



    @GetMapping("/download")
    public ResponseEntity<Resource> downloadDocument(@RequestParam("idpersonnel") Long employeeId,HttpServletRequest request) throws IOException {
        EmployeeDocument doc = repository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Document introuvable avec id = " + employeeId));

        String relativePath = doc.getUrlFichier(); // ex: uploads/documents/Embauche/331/file.docx
        Path absolutePath;

        // 1. Détection environnement local
        Path localUploadsPath = Paths.get("src/main/resources/uploads");
        if (Files.exists(localUploadsPath)) {
            absolutePath = Paths.get("src/main/resources").resolve(relativePath);
        }
        // 2. Environnement de production : Tomcat (webapps/hyban/uploads/...)
        else {
            // Récupère le chemin absolu à partir du contexte web
            String realBasePath = request.getServletContext().getRealPath("/");
            if (realBasePath != null) {
                absolutePath = Paths.get(realBasePath).resolve(relativePath);
            } else {
                throw new IllegalStateException("Impossible de déterminer le chemin d'accès en production.");
            }
        }

        if (!Files.exists(absolutePath)) {
            throw new FileNotFoundException("Fichier introuvable : " + absolutePath.toString());
        }

        Resource fileResource = new UrlResource(absolutePath.toUri());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + absolutePath.getFileName().toString() + "\"")
                .body(fileResource);
    }



}



