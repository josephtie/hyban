package com.nectux.mizan.hyban.personnel.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nectux.mizan.hyban.parametrages.entity.Auditable;
import com.nectux.mizan.hyban.utils.CustomDateDeserializer;
import com.nectux.mizan.hyban.utils.Utils;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Component("contratDateFinHistorique")
@Scope("prototype")
@Table(name = "CGECI_RHPAIE_CONTRAT_DATE_FIN_HIST")
@SequenceGenerator(name = "CGECI_RHPAIE_CONTRAT_DATE_FIN_HIST_SEQ", sequenceName = "CGECI_RHPAIE_CONTRAT_DATE_FIN_HIST_SEQ", initialValue = 1, allocationSize = 1)
public class ContratDateFinHistorique extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CGECI_RHPAIE_CONTRAT_DATE_FIN_HIST_SEQ")
    @Column(unique = true, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "contrat_personnel_id", nullable = false)
    private ContratPersonnel contratPersonnel;

    @JsonSerialize(using = CustomDateDeserializer.class)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private java.util.Date ancienneDateFin;

    @Transient
    private String ancienneDFin;

    @JsonSerialize(using = CustomDateDeserializer.class)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private java.util.Date nouvelleDateFin;

    @Transient
    private String nouvelleDFin;

    private String motif;

    public ContratDateFinHistorique() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ContratPersonnel getContratPersonnel() {
        return contratPersonnel;
    }

    public void setContratPersonnel(ContratPersonnel contratPersonnel) {
        this.contratPersonnel = contratPersonnel;
    }

    public java.util.Date getAncienneDateFin() {
        return ancienneDateFin;
    }

    public void setAncienneDateFin(java.util.Date ancienneDateFin) {
        this.ancienneDateFin = ancienneDateFin;
    }

    public String getAncienneDFin() {
        ancienneDFin = Utils.dateToString(ancienneDateFin, "dd/MM/yyyy");
        return ancienneDFin;
    }

    public void setAncienneDFin(String ancienneDFin) {
        this.ancienneDFin = ancienneDFin;
    }

    public java.util.Date getNouvelleDateFin() {
        return nouvelleDateFin;
    }

    public void setNouvelleDateFin(java.util.Date nouvelleDateFin) {
        this.nouvelleDateFin = nouvelleDateFin;
    }

    public String getNouvelleDFin() {
        nouvelleDFin = Utils.dateToString(nouvelleDateFin, "dd/MM/yyyy");
        return nouvelleDFin;
    }

    public void setNouvelleDFin(String nouvelleDFin) {
        this.nouvelleDFin = nouvelleDFin;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    @Override
    public String toString() {
        return "ContratDateFinHistorique{" +
                "id=" + id +
                ", contratPersonnel=" + (contratPersonnel != null ? contratPersonnel.getId() : null) +
                ", ancienneDateFin=" + ancienneDateFin +
                ", nouvelleDateFin=" + nouvelleDateFin +
                ", motif='" + motif + '\'' +
                '}';
    }
}
