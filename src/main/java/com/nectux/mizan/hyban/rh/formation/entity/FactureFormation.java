package com.nectux.mizan.hyban.rh.formation.entity;

import com.nectux.mizan.hyban.utils.CustomDateDeserializer;
import com.nectux.mizan.hyban.utils.TrxInfoManager;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Component("factureFormation")
@Scope("prototype")
@Table(name="CGECI_RHPAIE_FACTURE_FORMATION")
@SequenceGenerator(name="CGECI_RHPAIE_FACTURE_FORMATION_SEQUENCE", sequenceName="CGECI_RHPAIE_FACTURE_FORMATION_SEQ", initialValue=1, allocationSize=1)
public class FactureFormation {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CGECI_RHPAIE_FACTURE_FORMATION_SEQUENCE")
	@Column(unique=true, nullable=false)
	private Long id;
	
	private String reference;
	
	private Boolean statut;
	
	@Transient
	private String etat;
	
	private Double montant;
	
	@Transient
	private String mnt;
	
	@JsonSerialize(using = CustomDateDeserializer.class)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private java.util.Date dateEmission;
	
	@Transient
	private String dEmission;
	
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
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private Formation formation;

	public FactureFormation() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public Boolean getStatut() {
		return statut;
	}

	public void setStatut(Boolean statut) {
		this.statut = statut;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public Double getMontant() {
		return montant;
	}

	public void setMontant(Double montant) {
		this.montant = montant;
	}

	public String getMnt() {
		return TrxInfoManager.formattingAmount(montant);
	}

	public void setMnt(String mnt) {
		this.mnt = mnt;
	}

	public java.util.Date getDateEmission() {
		return dateEmission;
	}

	public void setDateEmission(java.util.Date dateEmission) {
		this.dateEmission = dateEmission;
	}

	public String getdEmission() {
		return dEmission;
	}

	public void setdEmission(String dEmission) {
		this.dEmission = dEmission;
	}

	public java.util.Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(java.util.Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public String getdCreation() {
		return dCreation;
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
		return dModification;
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

	public Formation getFormation() {
		return formation;
	}

	public void setFormation(Formation formation) {
		this.formation = formation;
	}

	@Override
	public String toString() {
		return "FactureFormation [id=" + id + ", reference=" + reference + ", statut=" + statut + ", etat=" + etat
				+ ", montant=" + montant + ", mnt=" + mnt + ", dateEmission=" + dateEmission + ", dEmission="
				+ dEmission + ", dateCreation=" + dateCreation + ", dCreation=" + dCreation + ", dateModification="
				+ dateModification + ", dModification=" + dModification + ", cabinetFormation=" + cabinetFormation
				+ ", formation=" + formation + "]";
	}

}
