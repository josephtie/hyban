package com.nectux.mizan.hyban.personnel.specifque.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nectux.mizan.hyban.parametrages.entity.Auditable;
import com.nectux.mizan.hyban.personnel.entity.Fonction;
import com.nectux.mizan.hyban.personnel.specifque.enums.SpecialContractType;
import com.nectux.mizan.hyban.utils.CustomDateDeserializer;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "special_contracts")
public class SpecialContract extends Auditable {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Enumerated(EnumType.STRING)
    private SpecialContractType typeContrat;

    private String modepaiement;

    @ManyToOne(optional = false)
    @JoinColumn(name = "fonction_id")
    private Fonction fonction;

    private String paiementNumber;

    @JsonSerialize(using = CustomDateDeserializer.class)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="dd/MM/yyyy")
    private java.util.Date dateDebut;

    @Transient
    private String dDeb;

    @JsonSerialize(using = CustomDateDeserializer.class)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="dd/MM/yyyy")
    private java.util.Date dateFin;

    @Transient
    private String dFin;

    @Column(precision = 15, scale = 2)
    private BigDecimal remunerationForfaitaire;

    private Boolean actif = true;

    // getters & setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public SpecialContractType getTypeContrat() {
        return typeContrat;
    }

    public void setTypeContrat(SpecialContractType typeContrat) {
        this.typeContrat = typeContrat;
    }

    public String getModepaiement() {
        return modepaiement;
    }

    public void setModepaiement(String modepaiement) {
        this.modepaiement = modepaiement;
    }

    public String getPaiementNumber() {
        return paiementNumber;
    }

    public void setPaiementNumber(String paiementNumber) {
        this.paiementNumber = paiementNumber;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getdDeb() {
        return dDeb;
    }

    public void setdDeb(String dDeb) {
        this.dDeb = dDeb;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public String getdFin() {
        return dFin;
    }

    public void setdFin(String dFin) {
        this.dFin = dFin;
    }

    public BigDecimal getRemunerationForfaitaire() {
        return remunerationForfaitaire;
    }

    public void setRemunerationForfaitaire(BigDecimal remunerationForfaitaire) {
        this.remunerationForfaitaire = remunerationForfaitaire;
    }

    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    public Fonction getFonction() {
        return fonction;
    }

    public void setFonction(Fonction fonction) {
        this.fonction = fonction;
    }

    @Override
    public String toString() {
        return "SpecialContract{" +
                "id=" + id +
                ", employee=" + employee +
                ", typeContrat=" + typeContrat +
                ", modepaiement='" + modepaiement + '\'' +
                ", paiementNumber='" + paiementNumber + '\'' +
                ", dateDebut=" + dateDebut +
                ", dDeb='" + dDeb + '\'' +
                ", dateFin=" + dateFin +
                ", dFin='" + dFin + '\'' +
                ", remunerationForfaitaire=" + remunerationForfaitaire +
                ", actif=" + actif +
                '}';
    }
}