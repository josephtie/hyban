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

import com.nectux.mizan.hyban.personnel.entity.Fonction;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.nectux.mizan.hyban.personnel.entity.Personnel;
import com.nectux.mizan.hyban.utils.CustomDateDeserializer;
import com.nectux.mizan.hyban.utils.DateManager;

@Entity
@Component("affectation")
@Scope("prototype")
@Table(name="CGECI_RHPAIE_AFFECTATION")
@SequenceGenerator(name="CGECI_RHPAIE_AFFECTATION_SEQUENCE", sequenceName="CGECI_RHPAIE_AFFECTATION_SEQ", initialValue=1, allocationSize=1)
public class Affectation {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CGECI_RHPAIE_AFFECTATION_SEQUENCE")
	@Column(unique=true, nullable=false)
	private Long id;
	
	@Transient
	private MultipartFile fileData;
	
	private String urlDocument;
	
	private String observation;
	
	@JsonSerialize(using = CustomDateDeserializer.class)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy")
	@Column(nullable = false)
	private java.util.Date dateDebut;
	
	@Transient
	private String dDebut;
	
	@JsonSerialize(using = CustomDateDeserializer.class)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private java.util.Date dateFin;
	
	@Transient
	private String dFin;
	
	@JsonSerialize(using = CustomDateDeserializer.class)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private java.util.Date dateCreation;
	
	@Transient
	private String dCreation;
	
	@JsonSerialize(using = CustomDateDeserializer.class)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private java.util.Date dateModification;
	
	@Transient
	private String dModification;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private Fonction poste;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private Personnel personnel;

	private Boolean statut;

	@ManyToOne
	@JoinColumn(name = "site_id", nullable = true)
	private Site site;

	public Affectation() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MultipartFile getFileData() {
		return fileData;
	}

	public void setFileData(MultipartFile fileData) {
		this.fileData = fileData;
	}

	public String getUrlDocument() {
		return urlDocument;
	}

	public void setUrlDocument(String urlDocument) {
		this.urlDocument = urlDocument;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public java.util.Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(java.util.Date dateAffectation) {
		this.dateDebut = dateAffectation;
	}

	public String getdDebut() {
		return DateManager.dateToString(dateDebut, "dd/MM/yyyy");
	}

	public void setdDebut(String dAffectation) {
		this.dDebut = dAffectation;
	}

	public java.util.Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(java.util.Date dateFin) {
		this.dateFin = dateFin;
	}

	public String getdFin() {
		return DateManager.dateToString(dateFin, "dd/MM/yyyy");
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
		return DateManager.dateToString(dateCreation, "dd/MM/yyyy");
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
		return DateManager.dateToString(dateModification, "dd/MM/yyyy");
	}

	public void setdModification(String dModification) {
		this.dModification = dModification;
	}

	public Fonction getPoste() {
		return poste;
	}

	public void setPoste(Fonction poste) {
		this.poste = poste;
	}

	public Personnel getPersonnel() {
		return personnel;
	}

	public void setPersonnel(Personnel personnel) {
		this.personnel = personnel;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public Boolean getStatut() {
		return statut;
	}

	public void setStatut(Boolean statut) {
		this.statut = statut;
	}

	@Override
	public String toString() {
		return "Affectation{" +
				"id=" + id +
				", fileData=" + fileData +
				", urlDocument='" + urlDocument + '\'' +
				", observation='" + observation + '\'' +
				", dateDebut=" + dateDebut +
				", dDebut='" + dDebut + '\'' +
				", dateFin=" + dateFin +
				", dFin='" + dFin + '\'' +
				", dateCreation=" + dateCreation +
				", dCreation='" + dCreation + '\'' +
				", dateModification=" + dateModification +
				", dModification='" + dModification + '\'' +
				", poste=" + poste +
				", personnel=" + personnel +
				", statut=" + statut +
				", site=" + site +
				'}';
	}

}
