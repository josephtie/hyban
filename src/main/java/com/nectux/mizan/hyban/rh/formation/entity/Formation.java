package com.nectux.mizan.hyban.rh.formation.entity;

import com.nectux.mizan.hyban.utils.CustomDateDeserializer;
import com.nectux.mizan.hyban.utils.DateManager;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Component("formation")
@Scope("prototype")
@Table(name="CGECI_RHPAIE_FORMATION")
@SequenceGenerator(name="CGECI_RHPAIE_FORMATION_SEQUENCE", sequenceName="CGECI_RHPAIE_FORMATION_SEQ", initialValue=1, allocationSize=1)
public class Formation {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CGECI_RHPAIE_FORMATION_SEQUENCE")
	@Column(unique=true, nullable=false)
	private Long id;
	
	private String intitule;
	
	private String lieu;
	
	private int nbreParticipant;
	
	private String plan;
	
	private String objectif;
	
	private String commentaire;
	
	@JsonSerialize(using = CustomDateDeserializer.class)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy")
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
	private java.util.Date datePrevue;
	
	@Transient
	private String dPrevue;
	
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
	@JoinColumn(nullable=true)
	private Theme theme;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private DemandeFormation demandeFormation;

	@ManyToOne
	@JoinColumn(nullable=true)
	private CabinetFormation cabinetFormation;

	public Formation() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIntitule() {
		return intitule;
	}

	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}

	public String getLieu() {
		return lieu;
	}

	public void setLieu(String lieu) {
		this.lieu = lieu;
	}

	public int getNbreParticipant() {
		return nbreParticipant;
	}

	public void setNbreParticipant(int nbreParticipant) {
		this.nbreParticipant = nbreParticipant;
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public String getObjectif() {
		return objectif;
	}

	public void setObjectif(String objectif) {
		this.objectif = objectif;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public java.util.Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(java.util.Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public String getdDebut() {
		return DateManager.dateToString(dateDebut, "dd/MM/yyyy");
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
		return DateManager.dateToString(dateFin, "dd/MM/yyyy");
	}

	public void setdFin(String dFin) {
		this.dFin = dFin;
	}

	public java.util.Date getDatePrevue() {
		return datePrevue;
	}

	public void setDatePrevue(java.util.Date datePrevue) {
		this.datePrevue = datePrevue;
	}

	public String getdPrevue() {
		return DateManager.dateToString(datePrevue, "dd/MM/yyyy");
	}

	public void setdPrevue(String dPrevue) {
		this.dPrevue = dPrevue;
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

	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}

	public DemandeFormation getDemandeFormation() {
		return demandeFormation;
	}

	public void setDemandeFormation(DemandeFormation demandeFormation) {
		this.demandeFormation = demandeFormation;
	}

	public CabinetFormation getCabinetFormation() {
		return cabinetFormation;
	}

	public void setCabinetFormation(CabinetFormation cabinetFormation) {
		this.cabinetFormation = cabinetFormation;
	}

	@Override
	public String toString() {
		return "Formation{" +
				"id=" + id +
				", intitule='" + intitule + '\'' +
				", lieu='" + lieu + '\'' +
				", nbreParticipant=" + nbreParticipant +
				", plan='" + plan + '\'' +
				", objectif='" + objectif + '\'' +
				", commentaire='" + commentaire + '\'' +
				", dateDebut=" + dateDebut +
				", dDebut='" + dDebut + '\'' +
				", dateFin=" + dateFin +
				", dFin='" + dFin + '\'' +
				", datePrevue=" + datePrevue +
				", dPrevue='" + dPrevue + '\'' +
				", dateCreation=" + dateCreation +
				", dCreation='" + dCreation + '\'' +
				", dateModification=" + dateModification +
				", dModification='" + dModification + '\'' +
				", theme=" + theme +
				", demandeFormation=" + demandeFormation +
				", cabinetFormation=" + cabinetFormation +
				'}';
	}

}
