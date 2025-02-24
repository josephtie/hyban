package com.nectux.mizan.hyban.rh.personnel.entity;

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

import com.nectux.mizan.hyban.personnel.entity.Personnel;
import com.nectux.mizan.hyban.utils.CustomDateDeserializer;
import com.nectux.mizan.hyban.utils.DateManager;
import com.nectux.mizan.hyban.utils.DifferenceDate;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

@Entity
@Component("pointage")
@Scope("prototype")
@Table(name = "CGECI_RHPAIE_POINTAGE")
@SequenceGenerator(name = "CGECI_RHPAIE_POINTAGE_SEQUENCE", sequenceName = "CGECI_RHPAIE_POINTAGE_SEQ", initialValue = 1, allocationSize = 1)
public class Pointage {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CGECI_RHPAIE_POINTAGE_SEQUENCE")
	@Column(unique = true, nullable = false)
	private Long id;

	@Column(unique = false, nullable = true)
	@JsonSerialize(using = CustomDateDeserializer.class)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private java.util.Date datePointage;

	@Transient
	private String dPointage;

	@Column(unique = false, nullable = true)
	@JsonSerialize(using = CustomDateDeserializer.class)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private java.util.Date heureArrivee;

	@Transient
	private String hArrivee;

	@Column(unique = false, nullable = true)
	@JsonSerialize(using = CustomDateDeserializer.class)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private java.util.Date heureDepart;

	@Transient
	private String hDepart;

	@Column(unique = false, nullable = true)
	@JsonSerialize(using = CustomDateDeserializer.class)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private java.util.Date heurePause;

	@Transient
	private String hPause;

	@Column(unique = false, nullable = true)
	@JsonSerialize(using = CustomDateDeserializer.class)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private java.util.Date heureReprise;

	@Transient
	private String hReprise;

	@Transient
	private String volumeHoraireJournalier;

	private String description;

	@JsonSerialize(using = CustomDateDeserializer.class)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private java.util.Date dateCreation;

	@Transient
	private String dCreation;

	@JsonSerialize(using = CustomDateDeserializer.class)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private java.util.Date dateModification;

	@Transient
	private String dModification;

	@ManyToOne
	@JoinColumn(nullable = false, unique = false)
	private Personnel personnel;

	public Pointage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public java.util.Date getDatePointage() {
		return datePointage;
	}

	public void setDatePointage(java.util.Date datePointage) {
		this.datePointage = datePointage;
	}

	public String getdPointage() {
		return DateManager.dateToString(datePointage, "dd/MM/yyyy");
	}

	public void setdPointage(String dPointage) {
		this.dPointage = dPointage;
	}

	public java.util.Date getHeureArrivee() {
		return heureArrivee;
	}

	public void setHeureArrivee(java.util.Date heureArrivee) {
		this.heureArrivee = heureArrivee;
	}

	public String gethArrivee() {
		return DateManager.dateToString(heureArrivee, "HH:mm");
	}

	public void sethArrivee(String hArrivee) {
		this.hArrivee = hArrivee;
	}

	public java.util.Date getHeureDepart() {
		return heureDepart;
	}

	public void setHeureDepart(java.util.Date heureDepart) {
		this.heureDepart = heureDepart;
	}

	public String gethDepart() {
		return DateManager.dateToString(heureDepart, "HH:mm");
	}

	public void sethDepart(String hDepart) {
		this.hDepart = hDepart;
	}

	public java.util.Date getHeurePause() {
		return heurePause;
	}

	public void setHeurePause(java.util.Date heurePause) {
		this.heurePause = heurePause;
	}

	public String gethPause() {
		return DateManager.dateToString(heurePause, "HH:mm");
	}

	public void sethPause(String hPause) {
		this.hPause = hPause;
	}

	public java.util.Date getHeureReprise() {
		return heureReprise;
	}

	public void setHeureReprise(java.util.Date heureReprise) {
		this.heureReprise = heureReprise;
	}

	public String gethReprise() {
		return DateManager.dateToString(heureReprise, "HH:mm");
	}

	public void sethReprise(String hReprise) {
		this.hReprise = hReprise;
	}

	public String getVolumeHoraireJournalier() {
         long volH=DifferenceDate.DifferenceHeure(heureArrivee,heurePause)+DifferenceDate.DifferenceHeure(heureReprise,heureDepart);
		return volumeHoraireJournalier= String.valueOf(volH);
	}

	public void setVolumeHoraireJournalier(String volumeHoraireJournalier) {
		this.volumeHoraireJournalier = volumeHoraireJournalier;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
		return "Pointage [id=" + id + ", datePointage=" + datePointage + ", dPointage=" + dPointage + ", heureArrivee="
				+ heureArrivee + ", hArrivee=" + hArrivee + ", heureDepart=" + heureDepart + ", hDepart=" + hDepart
				+ ", heurePause=" + heurePause + ", hPause=" + hPause + ", heureReprise=" + heureReprise + ", hReprise="
				+ hReprise + ", volumeHoraireJournalier=" + volumeHoraireJournalier + ", description=" + description
				+ ", dateCreation=" + dateCreation + ", dCreation=" + dCreation + ", dateModification="
				+ dateModification + ", dModification=" + dModification + ", personnel=" + personnel + "]";
	}

}
