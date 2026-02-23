package com.nectux.mizan.hyban.paie.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

import com.nectux.mizan.hyban.parametrages.entity.Auditable;
import com.nectux.mizan.hyban.personnel.specifque.entity.Employee;
import com.nectux.mizan.hyban.utils.CustomDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.personnel.entity.Personnel;
import com.nectux.mizan.hyban.utils.Utils;

@Entity
@Component("pretPersonnel")
@Scope("prototype")
@Table(name="CGECI_RHPAIE_PRET_PERSONNEL")
@SequenceGenerator(name="CGECI_RHPAIE_PRET_PERSONNEL_SEQUENCE", sequenceName="CGECI_RHPAIE_PRET_PERSONNEL_SEQ", initialValue=1, allocationSize=1)
public class PretPersonnel extends Auditable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CGECI_RHPAIE_PRET_PERSONNEL_SEQUENCE")
	@Column(unique=true, nullable=false)
	private Long id;
	
	private Double montant;
	
	
	private Long echelonage;
	
	@Transient
	private String montantPret;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private Pret pret;
	
	@ManyToOne
	@JoinColumn(nullable=true)
	private Personnel personnel;


    @ManyToOne
    @JoinColumn(nullable=true)
    private Employee employee;
	
	@JsonSerialize(using = CustomDateDeserializer.class)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private java.util.Date dateEmprunt;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private PeriodePaie periode;
	
	@Transient
	private String dEmprunt;
	
	public PretPersonnel() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getMontant() {
		return montant;
	}

	public void setMontant(Double montant) {
		this.montant = montant;
	}

	public String getMontantPret() {
		montantPret = Utils.formattingAmount(montant);
		return montantPret;
	}

	public void setMontantPret(String montantPret) {
		this.montantPret = montantPret;
	}

	public Pret getPret() {
		return pret;
	}

	public void setPret(Pret pret) {
		this.pret = pret;
	}

	public Personnel getPersonnel() {
		return personnel;
	}

	public void setPersonnel(Personnel personnel) {
		this.personnel = personnel;
	}

	public java.util.Date getDateEmprunt() {
		return dateEmprunt;
	}

	public void setDateEmprunt(java.util.Date dateEmprunt) {
		this.dateEmprunt = dateEmprunt;
	}

	public String getdEmprunt() {
		dEmprunt = Utils.dateToString(dateEmprunt, "dd/MM/yyyy");
		return dEmprunt;
	}

	public void setdEmprunt(String dEmprunt) {
		this.dEmprunt = dEmprunt;
	}

	public Long getEchelonage() {
		return echelonage;
	}

	public void setEchelonage(Long echelonage) {
		this.echelonage = echelonage;
	}

	public PeriodePaie getPeriode() {
		return periode;
	}

	public void setPeriode(PeriodePaie periode) {
		this.periode = periode;
	}

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "PretPersonnel{" +
                "id=" + id +
                ", montant=" + montant +
                ", echelonage=" + echelonage +
                ", montantPret='" + montantPret + '\'' +
                ", pret=" + pret +
                ", personnel=" + personnel +
                ", employee=" + employee +
                ", dateEmprunt=" + dateEmprunt +
                ", periode=" + periode +
                ", dEmprunt='" + dEmprunt + '\'' +
                '}';
    }
}
