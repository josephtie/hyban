package com.nectux.mizan.hyban.rh.carriere.entity;

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

import com.nectux.mizan.hyban.utils.CustomDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.nectux.mizan.hyban.personnel.entity.Personnel;
import com.nectux.mizan.hyban.utils.DateManager;

@Entity
@Component("sanctionPersonnel")
@Scope("prototype")
@Table(name="CGECI_RHPAIE_SANCTION_PERSONNEL")
@SequenceGenerator(name="CGECI_RHPAIE_SANCTION_PERSONNEL_SEQUENCE", sequenceName="CGECI_RHPAIE_SANCTION_PERSONNEL_SEQ", initialValue=1, allocationSize=1)
public class SanctionPersonnel {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CGECI_RHPAIE_SANCTION_PERSONNEL_SEQUENCE")
	@Column(unique=true, nullable=false)
	private Long id;
	
	private String observation;
	
	@Transient
	private MultipartFile fileDataDemande;
	
	private String urlDemande;
	
	@Transient
	private MultipartFile fileDataReponse;
	
	private String urlReponse;
	
	@JsonSerialize(using = CustomDateDeserializer.class)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
	private java.util.Date dateDebut;
	
	@Transient
	private String dDebut;
	
	@JsonSerialize(using = CustomDateDeserializer.class)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
	private java.util.Date dateFin;
	
	@Transient
	private String dFin;
	
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

	@ManyToOne
	@JoinColumn(nullable=false)
	private Sanction sanction;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private Personnel personnel;

	public SanctionPersonnel() {
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

	public MultipartFile getFileDataDemande() {
		return fileDataDemande;
	}

	public void setFileDataDemande(MultipartFile fileDataDemande) {
		this.fileDataDemande = fileDataDemande;
	}

	public String getUrlDemande() {
		return urlDemande;
	}

	public void setUrlDemande(String urlDemande) {
		this.urlDemande = urlDemande;
	}

	public MultipartFile getFileDataReponse() {
		return fileDataReponse;
	}

	public void setFileDataReponse(MultipartFile fileDataReponse) {
		this.fileDataReponse = fileDataReponse;
	}

	public String getUrlReponse() {
		return urlReponse;
	}

	public void setUrlReponse(String urlReponse) {
		this.urlReponse = urlReponse;
	}

	public java.util.Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(java.util.Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public String getdDebut() {
		return DateManager.dateToString(dateDebut, "dd/MM/yyyy HH:mm:ss");
	}

	public void setdDebut(String dDebut) {
		this.dDebut = dDebut;
	}

	public java.util.Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(java.util.Date dateFin) {
		this.dateFin = dateFin;
	}

	public String getdFin() {
		return DateManager.dateToString(dateFin, "dd/MM/yyyy HH:mm:ss");
	}

	public void setdFin(String dFin) {
		this.dFin = dFin;
	}

	public java.util.Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(java.util.Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public String getdCreation() {
		return DateManager.dateToString(dateCreation, "dd/MM/yyyy HH:mm:ss");
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
		return DateManager.dateToString(dateModification, "dd/MM/yyyy HH:mm:ss");
	}

	public void setdModification(String dModification) {
		this.dModification = dModification;
	}

	public Sanction getSanction() {
		return sanction;
	}

	public void setSanction(Sanction sanction) {
		this.sanction = sanction;
	}

	public Personnel getPersonnel() {
		return personnel;
	}

	public void setPersonnel(Personnel personnel) {
		this.personnel = personnel;
	}

	@Override
	public String toString() {
		return "SanctionPersonnel [id=" + id + ", observation=" + observation + ", fileDataDemande=" + fileDataDemande
				+ ", urlDemande=" + urlDemande + ", fileDataReponse=" + fileDataReponse + ", urlReponse=" + urlReponse
				+ ", dateDebut=" + dateDebut + ", dDebut=" + dDebut + ", dateFin=" + dateFin + ", dFin=" + dFin
				+ ", dateCreation=" + dateCreation + ", dCreation=" + dCreation + ", dateModification="
				+ dateModification + ", dModification=" + dModification + ", sanction=" + sanction + ", personnel="
				+ personnel + "]";
	}

}
