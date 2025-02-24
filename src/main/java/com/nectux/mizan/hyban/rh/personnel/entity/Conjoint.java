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

import com.nectux.mizan.hyban.utils.CustomDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


import com.nectux.mizan.hyban.personnel.entity.Personnel;
import com.nectux.mizan.hyban.utils.Utils;

@Entity
@Component("conjoint")
@Scope("prototype")
@Table(name="CGECI_RHPAIE_CONJOINT")
@SequenceGenerator(name="CGECI_RHPAIE_CONJOINT_SEQUENCE", sequenceName="CGECI_RHPAIE_CONJOINT_SEQ", initialValue=1, allocationSize=1)
public class Conjoint {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CGECI_RHPAIE_CONJOINT_SEQUENCE")
	@Column(unique=true, nullable=false)
	private Long id;
	
	@Column(unique=true, nullable=false)
	private String matricule;
	
	@Column(unique=false, nullable=false)
	private String nom;
	
	@Column(unique=false, nullable=true)
	@JsonSerialize(using = CustomDateDeserializer.class)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private java.util.Date dateNaissance;
	
	@Transient
	private String dNaissance;
	
	@Column(unique=false, nullable=true)
	private String lieuNaissance;
	
	@Column(unique=false, nullable=true)
	private String lieuResidence;
	
	@Column(unique=false, nullable=true)
	private String telephone;
	
	@Column(unique=false, nullable=true)
	private String email;
	
	@Column(unique=false, nullable=false)
	private char sexe;
	
	@Column(unique=true, nullable=true)
	private String photo;
	
	@Transient
	private MultipartFile fileData;
	
	@Column(unique=false, nullable=false)
	private Boolean actif;
	
	@Transient
	private String statut;
	
	@ManyToOne
	@JoinColumn(nullable = false, unique=false)
	private Personnel personnel;

	public Conjoint() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMatricule() {
		return matricule;
	}

	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public java.util.Date getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(java.util.Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}
	
	public String getdNaissance() {
		dNaissance = Utils.dateToString(dateNaissance, "dd/MM/yyyy");
		return dNaissance;
	}

	public void setdNaissance(String dNaissance) {
		this.dNaissance = dNaissance;
	}

	public String getLieuNaissance() {
		return lieuNaissance;
	}

	public void setLieuNaissance(String lieuNaissance) {
		this.lieuNaissance = lieuNaissance;
	}

	public String getLieuResidence() {
		return lieuResidence;
	}

	public void setLieuResidence(String lieuResidence) {
		this.lieuResidence = lieuResidence;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public char getSexe() {
		return sexe;
	}

	public void setSexe(char sexe) {
		this.sexe = sexe;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public MultipartFile getFileData() {
		return fileData;
	}

	public void setFileData(MultipartFile fileData) {
		this.fileData = fileData;
	}

	public Boolean getActif() {
		return actif;
	}

	public void setActif(Boolean actif) {
		this.actif = actif;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public Personnel getPersonnel() {
		return personnel;
	}

	public void setPersonnel(Personnel personnel) {
		this.personnel = personnel;
	}

	@Override
	public String toString() {
		return "Conjoint [id=" + id + ", matricule=" + matricule + ", nom=" + nom + ", dateNaissance=" + dateNaissance
				+ ", lieuNaissance=" + lieuNaissance + ", lieuResidence=" + lieuResidence + ", telephone=" + telephone
				+ ", email=" + email + ", sexe=" + sexe + ", photo=" + photo + ", fileData=" + fileData + ", actif="
				+ actif + ", statut=" + statut + ", personnel=" + personnel + "]";
	}

}
