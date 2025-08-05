package com.nectux.mizan.hyban.rh.absences.entity;

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
import com.nectux.mizan.hyban.personnel.entity.Personnel;
import com.nectux.mizan.hyban.utils.CustomDateDeserializer;
import com.nectux.mizan.hyban.utils.DateManager;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

@Entity
@Component("absencePersonnel")
@Scope("prototype")
@Table(name="CGECI_RHPAIE_ABSENCE_PERSONNEL")
@SequenceGenerator(name="CGECI_RHPAIE_ABSENCE_PERSONNEL_SEQUENCE", sequenceName="CGECI_RHPAIE_ABSENCE_PERSONNEL_SEQ", initialValue=1, allocationSize=1)
public class AbsencesPersonnel extends Auditable {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CGECI_RHPAIE_ABSENCE_PERSONNEL_SEQUENCE")
	@Column(unique=true, nullable=false)
	private Long id;
	
	private String observation;
	
	private Boolean statut;
	
	private int sanctionsalaire;
	
     private Double joursabsence;
	
	private Double heursabsence;

	
	@JsonSerialize(using = CustomDateDeserializer.class)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
	private java.util.Date dateDebut;
	
	@Transient
	private String dDebut;
	
	@JsonSerialize(using = CustomDateDeserializer.class)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
	private java.util.Date dateRet;
	
	@Transient
	private String dRet;
	
	@JsonSerialize(using = CustomDateDeserializer.class)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
	private java.util.Date dateCreation;
	
	@Transient
	private String dCreation;
	
	@JsonSerialize(using = CustomDateDeserializer.class)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
	private java.util.Date dateModification;
	
	@Transient
	private String dModification;
	
	@Transient
	private String impact;
	
	
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private Absences absences;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private Personnel personnel;

	public AbsencesPersonnel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	/*public CommonsMultipartFile getFileDataDemande() {
		return fileDataDemande;
	}

	public void setFileDataDemande(CommonsMultipartFile fileDataDemande) {
		this.fileDataDemande = fileDataDemande;
	}

	public String getUrlDemande() {
		return urlDemande;
	}

	public void setUrlDemande(String urlDemande) {
		this.urlDemande = urlDemande;
	}

	public CommonsMultipartFile getFileDataReponse() {
		return fileDataReponse;
	}

	public void setFileDataReponse(CommonsMultipartFile fileDataReponse) {
		this.fileDataReponse = fileDataReponse;
	}*/

	/*public String getUrlReponse() {
		return urlReponse;
	}

	public void setUrlReponse(String urlReponse) {
		this.urlReponse = urlReponse;
	}*/

	public java.util.Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(java.util.Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public String getdDebut() {
		return DateManager.dateToString(dateDebut, "dd/MM/yyyy HH:mm");
	}

	public void setdDebut(String dDebut) {
		this.dDebut = dDebut;
	}

	public java.util.Date getDateRet() {
		return dateRet;
	}

	public void setDateRet(java.util.Date dateRet) {
		this.dateRet = dateRet;
	}

	public String getdRet() {
		return DateManager.dateToString(dateRet, "dd/MM/yyyy HH:mm");
	}

	public void setdRet(String dFin) {
		this.dRet = dFin;
	}

	public java.util.Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(java.util.Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public String getdCreation() {
		return DateManager.dateToString(dateCreation, "dd/MM/yyyy HH:mm");
	}

	public void setdCreation(String dCreation) {
		this.dCreation = dCreation;
	}

	public java.util.Date getDateModification() {
		return dateModification;
	}

	public void setDateModification(java.util.Date dateModification) {
		this.dateModification = dateModification;
	}

	public String getdModification() {
		return DateManager.dateToString(dateModification, "dd/MM/yyyy HH:mm");
	}

	public void setdModification(String dModification) {
		this.dModification = dModification;
	}

	

	public Boolean getStatut() {
		return statut;
	}

	public void setStatut(Boolean statut) {
		this.statut = statut;
	}

	

	public int getSanctionsalaire() {
		return sanctionsalaire;
	}

	public void setSanctionsalaire(int sanctionsalaire) {
		this.sanctionsalaire = sanctionsalaire;
	}

	

	public Double getJoursabsence() {
		return joursabsence;
	}

	public void setJoursabsence(Double joursabsence) {
		this.joursabsence = joursabsence;
	}

	public Double getHeursabsence() {
		return heursabsence;
	}

	public void setHeursabsence(Double heursabsence) {
		this.heursabsence = heursabsence;
	}

	public String getImpact() {
		if(sanctionsalaire == 4)
			impact = "Salaire";
		else if(sanctionsalaire == 2)
			impact = "Conge";
		else if(sanctionsalaire == 3)
			impact = "Aucun";
		
		return impact;
	}

	public void setImpact(String impact) {
		this.impact = impact;
	}

	public Absences getAbsences() {
		return absences;
	}

	public void setAbsences(Absences absences) {
		this.absences = absences;
	}

	public Personnel getPersonnel() {
		return personnel;
	}

	public void setPersonnel(Personnel personnel) {
		this.personnel = personnel;
	}

	

	@Override
	public String toString() {
		return "AbsencesPersonnel [id=" + id + ", observation=" + observation
				+ ", dateDebut=" + dateDebut + ", dDebut=" + dDebut
				+ ", dateRet=" + dateRet + ", dRet=" + dRet + ", dateCreation="
				+ dateCreation + ", dCreation=" + dCreation
				+ ", dateModification=" + dateModification + ", dModification="
				+ dModification + ", absences=" + absences + ", personnel="
				+ personnel + "]";
	}

	

}
