package com.nectux.mizan.hyban.personnel.entity;

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
import com.nectux.mizan.hyban.parametrages.entity.TypeContrat;
import com.nectux.mizan.hyban.utils.CustomDateDeserializer;
import com.nectux.mizan.hyban.utils.Utils;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

@Entity
@Component("contratPersonnel")
@Scope("prototype")
@Table(name="CGECI_RHPAIE_CONTRAT_PERSONNEL")
@SequenceGenerator(name="CGECI_RHPAIE_CONTRAT_PERSONNEL_SEQUENCE", sequenceName="CGECI_RHPAIE_CONTRAT_PERSONNEL_SEQ", initialValue=1, allocationSize=1)
public class ContratPersonnel extends Auditable {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CGECI_RHPAIE_CONTRAT_PERSONNEL_SEQUENCE")
	@Column(unique=true, nullable=false)
	private Long id;
		
	private Boolean statut;
	
	private Boolean depart;

	private Boolean soldeCalcule;
	@ManyToOne
	@JoinColumn(nullable=false)
	private Categorie categorie;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private Personnel personnel;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private Fonction fonction;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private TypeContrat typeContrat;
	
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
	private java.util.Date dateMod;
	
	@Transient
	private String dMod;
	
	private Double netAPayer;
	
	@Transient
	private String netPayer;
	
	private Double indemniteLogement;
	
	@Transient
	private String indemniteLogmt;
	
    private Double indemniteRepresent;    
	
	@Transient
	private String indemniteReprt;
	
	   /*	private Double indemniteResp;
		
	@Transient
	private String indemRespons;*/
	
	
	private String ObservCtrat;
    private Double indemniteTransport;
	
	@Transient
	private String indemniteTranspt;
	
	
	private Double sursalaire;
	
	@Transient
	private String sursal;
	
	private int ancienneteInitial;

	public ContratPersonnel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public Boolean getStatut() {
		return statut;
	}

	public void setStatut(Boolean statut) {
		this.statut = statut;
	}

	public void setNetAPayer(Double netAPayer) {
		this.netAPayer = netAPayer;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public Personnel getPersonnel() {
		return personnel;
	}

	public void setPersonnel(Personnel personnel) {
		this.personnel = personnel;
	}

	public Fonction getFonction() {
		return fonction;
	}

	public void setFonction(Fonction fonction) {
		this.fonction = fonction;
	}

	public TypeContrat getTypeContrat() {
		return typeContrat;
	}

	public void setTypeContrat(TypeContrat typeContrat) {
		this.typeContrat = typeContrat;
	}

	public java.util.Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(java.util.Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public String getdDebut() {
		dDebut = Utils.dateToString(dateDebut, "dd/MM/yyyy");
		return dDebut;
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
		dFin = Utils.dateToString(dateFin, "dd/MM/yyyy");
		return dFin;
	}

	public void setdFin(String dFin) {
		this.dFin = dFin;
	}

	public java.util.Date getDateMod() {
		return dateMod;
	}

	public void setDateMod(java.util.Date dateMod) {
		this.dateMod = dateMod;
	}

	public String getdMod() {
		return dMod=Utils.dateToString(dateMod, "dd/MM/yyyy");
	}

	public void setdMod(String dMod) {
		this.dMod = dMod;
	}

	public double getNetAPayer() {
		return netAPayer;
	}

	public void setNetAPayer(double netAPayer) {
		this.netAPayer = netAPayer;
	}

	public String getNetPayer() {
		netPayer = Utils.formattingAmount(netAPayer);
		return netPayer;
	}

	public void setNetPayer(String netPayer) {
		this.netPayer = netPayer;
	}

	public Double getSursalaire() {
		return sursalaire;
	}

	public void setSursalaire(Double sursalaire) {
		this.sursalaire = sursalaire;
	}

	public String getSursal() {
		return sursal=Utils.formattingAmount(sursalaire);
	}

	public void setSursal(String sursal) {
		this.sursal = sursal;
	}

	public Double getIndemniteLogement() {
		return indemniteLogement;
	}

	public void setIndemniteLogement(Double indemniteLogement) {
		this.indemniteLogement = indemniteLogement;
	}

	public String getIndemniteLogmt() {
		indemniteLogmt = Utils.formattingAmount(indemniteLogement);
		return indemniteLogmt;
	}

	public void setIndemniteLogmt(String indemniteLogmt) {
		this.indemniteLogmt = indemniteLogmt;
	}

	public int getAncienneteInitial() {
		return ancienneteInitial;
	}

	public void setAncienneteInitial(int ancienneteInitial) {
		this.ancienneteInitial = ancienneteInitial;
	}

public Double getIndemniteRepresent() {
		return indemniteRepresent;
	}

	public void setIndemniteRepresent(Double indemniteRepresent) {
		this.indemniteRepresent = indemniteRepresent;
	}

	public String getIndemniteReprt() {
		return indemniteReprt;
	}

	public void setIndemniteReprt(String indemniteReprt) {
		this.indemniteReprt = indemniteReprt;
	}

	public Double getIndemniteTransport() {
		return indemniteTransport;
	}

	public void setIndemniteTransport(Double indemniteTransport) {
		this.indemniteTransport = indemniteTransport;
	}

	public String getIndemniteTranspt() {
		return indemniteTranspt;
	}

	public void setIndemniteTranspt(String indemniteTranspt) {
		this.indemniteTranspt = indemniteTranspt;
	}

	public Boolean getSoldeCalcule() {
		return soldeCalcule;
	}

	public void setSoldeCalcule(Boolean soldeCalcule) {
		this.soldeCalcule = soldeCalcule;
	}

	@Override
	public String toString() {
		return "ContratPersonnel{" +
				"id=" + id +
				", statut=" + statut +
				", depart=" + depart +
				", soldeCalcule=" + soldeCalcule +
				", categorie=" + categorie +
				", personnel=" + personnel +
				", fonction=" + fonction +
				", typeContrat=" + typeContrat +
				", dateDebut=" + dateDebut +
				", dDebut='" + dDebut + '\'' +
				", dateFin=" + dateFin +
				", dFin='" + dFin + '\'' +
				", dateMod=" + dateMod +
				", dMod='" + dMod + '\'' +
				", netAPayer=" + netAPayer +
				", netPayer='" + netPayer + '\'' +
				", indemniteLogement=" + indemniteLogement +
				", indemniteLogmt='" + indemniteLogmt + '\'' +
				", indemniteRepresent=" + indemniteRepresent +
				", indemniteReprt='" + indemniteReprt + '\'' +
				", ObservCtrat='" + ObservCtrat + '\'' +
				", indemniteTransport=" + indemniteTransport +
				", indemniteTranspt='" + indemniteTranspt + '\'' +
				", sursalaire=" + sursalaire +
				", sursal='" + sursal + '\'' +
				", ancienneteInitial=" + ancienneteInitial +
				'}';
	}

	public Boolean getDepart() {
		return depart;
	}

	public void setDepart(Boolean depart) {
		this.depart = depart;
	}

	public String getObservCtrat() {
		return ObservCtrat;
	}

	public void setObservCtrat(String observCtrat) {
		ObservCtrat = observCtrat;
	}

	

	/*public Double getIndemniteResp() {
		return indemniteResp;
	}

	public void setIndemniteResp(Double indemniteResp) {
		this.indemniteResp = indemniteResp;
	}

	public String getIndemRespons() {
		return indemRespons=Utils.formattingAmount(indemniteResp);
	}

	public void setIndemRespons(String indemRespons) {
		this.indemRespons = indemRespons;
	}*/

	

	
}
