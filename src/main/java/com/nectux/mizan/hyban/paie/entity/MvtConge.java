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

import com.nectux.mizan.hyban.utils.CustomDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.nectux.mizan.hyban.personnel.entity.Personnel;
import com.nectux.mizan.hyban.utils.DateManager;
import com.nectux.mizan.hyban.utils.Utils;

@Entity
@Component("mvtconge")
@Scope("prototype")
@Table(name="CGECI_RHPAIE_MVTCONGE")
@SequenceGenerator(name="CGECI_RHPAIE_MVTCONGE_SEQUENCE", sequenceName="CGECI_RHPAIE_MVTCONGE_SEQ", initialValue=1, allocationSize=1)
public class MvtConge {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CGECI_RHPAIE_MVTCONGE_SEQUENCE")
	@Column(unique=true, nullable=false)
	private Long id;
	
	@JsonSerialize(using = CustomDateDeserializer.class)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private java.util.Date dateDepart;
	
	@Transient
	private String dDepart;
	
	@JsonSerialize(using = CustomDateDeserializer.class)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private java.util.Date dateRetour;
	
	@Transient
	private String dRetour;
	
	private int nombreJourRestant; 
	

	
    private Double montantVerse; // 12 dernier mois
	
	@Transient
	private String mtnVerse;
	

	@Transient
    private int nombreJourPris;
	
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
	private Personnel personnel;
	
	public MvtConge() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public java.util.Date getDateDepart() {
		return dateDepart;
	}

	public void setDateDepart(java.util.Date dateDepart) {
		this.dateDepart = dateDepart;
	}

	public String getdDepart() {
		return Utils.dateToString(dateDepart, "dd/MM/yyyy");
	}

	public void setdDepart(String dDepart) {
		this.dDepart = dDepart;
	}

	public java.util.Date getDateRetour() {
		return dateRetour;
	}

	public void setDateRetour(java.util.Date dateRetour) {
		this.dateRetour = dateRetour;
	}

	public String getdRetour() {
		return Utils.dateToString(dateRetour, "dd/MM/yyyy");
	}

	public void setdRetour(String dRetour) {
		this.dRetour = dRetour;
	}

	public int getNombreJourRestant() {
		return nombreJourRestant;
	}

	public void setNombreJourRestant(int nombreJourRestant) {
		this.nombreJourRestant = nombreJourRestant;
	}

	public int getNombreJourPris() {
		return nombreJourPris=DateManager.dateDifference(dateDepart,dateRetour);
	}

	public void setNombreJourPris(int nombreJourPris) {
		this.nombreJourPris = nombreJourPris;
	}

	public Double getMontantVerse() {
		return montantVerse;
	}

	public void setMontantVerse(Double montantVerse) {
		this.montantVerse = montantVerse;
	}

	public String getMtnVerse() {
		return Utils.formattingAmount(montantVerse);
	}

	public void setMtnVerse(String mtnVerse) {
		this.mtnVerse = mtnVerse;
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

	public Personnel getPersonnel() {
		return personnel;
	}

	public void setPersonnel(Personnel personnel) {
		this.personnel = personnel;
	}

	@Override
	public String toString() {
		return "MvtConge [id=" + id + ", dateDepart=" + dateDepart
				+ ", dDepart=" + dDepart + ", dateRetour=" + dateRetour
				+ ", dRetour=" + dRetour + ", nombreJourRestant="
				+ nombreJourRestant + ", montantVerse=" + montantVerse
				+ ", mtnVerse=" + mtnVerse + ", nombreJourPris="
				+ nombreJourPris + ", dateCreation=" + dateCreation
				+ ", dCreation=" + dCreation + ", dateModification="
				+ dateModification + ", dModification=" + dModification + "]";
	}



	
	
}
