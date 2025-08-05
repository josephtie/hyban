package com.nectux.mizan.hyban.personnel.entity;

import com.nectux.mizan.hyban.parametrages.entity.Auditable;
import com.nectux.mizan.hyban.utils.Utils;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
public class EmployeeDocument extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private DocumentType documentType;

    @ManyToOne
    private StorageLocation storageLocation;

    @ManyToOne
    private Personnel  personnel;

    private Date dateDepot;
    private String numeroReference;
    private boolean present;
    private String remarques;
    private String urlFichier;

    @Transient
    private String ddatedepot;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public DocumentType getDocumentType() { return documentType; }
    public void setDocumentType(DocumentType documentType) { this.documentType = documentType; }
    public StorageLocation getStorageLocation() { return storageLocation; }
    public void setStorageLocation(StorageLocation storageLocation) { this.storageLocation = storageLocation; }

    public Date getDateDepot() { return dateDepot; }
    public void setDateDepot(Date dateDepot) { this.dateDepot = dateDepot; }
    public String getNumeroReference() { return numeroReference; }
    public void setNumeroReference(String numeroReference) { this.numeroReference = numeroReference; }
    public boolean isPresent() { return present; }
    public void setPresent(boolean present) { this.present = present; }
    public String getRemarques() { return remarques; }
    public void setRemarques(String remarques) { this.remarques = remarques; }
    public String getUrlFichier() { return urlFichier; }
    public void setUrlFichier(String urlFichier) { this.urlFichier = urlFichier; }

    public String getDdatedepot() {
        return ddatedepot = Utils.dateToString(dateDepot, "dd/MM/yyyy");
     //   return ddatedepot;
    }

    public void setDdatedepot(String ddatedepot) {
        this.ddatedepot = ddatedepot;
    }

    public Personnel getPersonnel() {
        return personnel;
    }

    public void setPersonnel(Personnel personnel) {
        this.personnel = personnel;
    }
}