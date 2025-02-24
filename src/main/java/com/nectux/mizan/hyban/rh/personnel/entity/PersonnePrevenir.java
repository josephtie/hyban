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
import javax.persistence.Transient;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.nectux.mizan.hyban.personnel.entity.Personnel;

@Entity
@Component("personnePrevenir")
@Scope("prototype")
@Table(name="CGECI_RHPAIE_PERSONNE_PREVENIR")
@SequenceGenerator(name="CGECI_RHPAIE_PERSONNE_PREVENIR_SEQUENCE", sequenceName="CGECI_RHPAIE_PERSONNE_PREVENIR_SEQ", initialValue=1, allocationSize=1)
public class PersonnePrevenir {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CGECI_RHPAIE_PERSONNE_PREVENIR_SEQUENCE")
	@Column(unique=true, nullable=false)
	private Long id;
	
	@Column(unique=false, nullable=false)
	private String nom;
	
	@Column(unique=true, nullable=false)
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
	
	@Column(unique=false, nullable=false)
	private String filiation;
	
	@ManyToOne
	@JoinColumn(nullable = false, unique=false)
	private Personnel personnel;

	public PersonnePrevenir() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
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

	public String getFiliation() {
		return filiation;
	}

	public void setFiliation(String filiation) {
		this.filiation = filiation;
	}

	public Personnel getPersonnel() {
		return personnel;
	}

	public void setPersonnel(Personnel personnel) {
		this.personnel = personnel;
	}

	@Override
	public String toString() {
		return "PersonnePrevenir [id=" + id + ", nom=" + nom + ", telephone=" + telephone + ", email=" + email
				+ ", sexe=" + sexe + ", photo=" + photo + ", fileData=" + fileData + ", actif=" + actif + ", statut="
				+ statut + ", filiation=" + filiation + ", personnel=" + personnel + "]";
	}

}
