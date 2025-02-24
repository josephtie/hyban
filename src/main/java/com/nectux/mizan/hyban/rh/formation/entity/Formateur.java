package com.nectux.mizan.hyban.rh.formation.entity;

import com.nectux.mizan.hyban.utils.CustomDateDeserializer;
import com.nectux.mizan.hyban.utils.DateManager;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Component("formateur")
@Scope("prototype")
@Table(name="CGECI_RHPAIE_FORMATEUR")
@SequenceGenerator(name="CGECI_RHPAIE_FORMATEUR_SEQUENCE", sequenceName="CGECI_RHPAIE_FORMATEUR_SEQ", initialValue=1, allocationSize=1)
public class Formateur {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CGECI_RHPAIE_FORMATEUR_SEQUENCE")
	@Column(unique=true, nullable=false)
	private Long id;
	
	private String civilite;
	
	private char sexe;
	
	private String nomComplet;
	
	private int situationMat;
	
	@Transient
	private String situationMatrimonial;
	
	@JsonSerialize(using = CustomDateDeserializer.class)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private java.util.Date dateNaissance;
	
	@Transient
	private String dNaissance;
	
	private String lieuNaissance;
	
	private String fixe;
	
	private String mobile;
	
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
	private CabinetFormation cabinetFormation;

	public Formateur() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCivilite() {
		return civilite;
	}

	public void setCivilite(String civilite) {
		this.civilite = civilite;
	}

	public char getSexe() {
		return sexe;
	}

	public void setSexe(char sexe) {
		this.sexe = sexe;
	}

	public String getNomComplet() {
		return nomComplet;
	}

	public void setNomComplet(String nomComplet) {
		this.nomComplet = nomComplet;
	}

	public int getSituationMat() {
		return situationMat;
	}

	public void setSituationMat(int situationMat) {
		this.situationMat = situationMat;
	}

	public String getSituationMatrimonial() {
		if(situationMat == 1)
			return "Marie(e)";
		else if(situationMat == 2)
			return "Celibataire";
		else if(situationMat == 3)
			return "Divorce(e)";
		else
			return "Veuf(ve)";
	}

	public void setSituationMatrimonial(String situationMatrimonial) {
		this.situationMatrimonial = situationMatrimonial;
	}

	public java.util.Date getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(java.util.Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public String getdNaissance() {
		return DateManager.dateToString(dateNaissance, "dd/MM/yyyy");
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

	public String getFixe() {
		return fixe;
	}

	public void setFixe(String fixe) {
		this.fixe = fixe;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	public CabinetFormation getCabinetFormation() {
		return cabinetFormation;
	}

	public void setCabinetFormation(CabinetFormation cabinetFormation) {
		this.cabinetFormation = cabinetFormation;
	}

	@Override
	public String toString() {
		return "Formateur [id=" + id + ", civilite=" + civilite + ", sexe=" + sexe + ", nomComplet=" + nomComplet
				+ ", situationMat=" + situationMat + ", situationMatrimonial=" + situationMatrimonial
				+ ", dateNaissance=" + dateNaissance + ", dNaissance=" + dNaissance + ", lieuNaissance=" + lieuNaissance
				+ ", fixe=" + fixe + ", mobile=" + mobile + ", dateCreation=" + dateCreation + ", dCreation="
				+ dCreation + ", dateModification=" + dateModification + ", dModification=" + dModification
				+ ", cabinetFormation=" + cabinetFormation + "]";
	}

}
