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

import com.nectux.mizan.hyban.parametrages.entity.Auditable;
import com.nectux.mizan.hyban.utils.CustomDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.utils.Utils;

@Entity
@Component("echelonnement")
@Scope("prototype")
@Table(name="CGECI_RHPAIE_ECHELONNEMENT")
@SequenceGenerator(name="CGECI_RHPAIE_ECHELONNEMENT_SEQUENCE", sequenceName="CGECI_RHPAIE_ECHELONNEMENT_SEQ", initialValue=1, allocationSize=1)
public class Echelonnement extends Auditable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CGECI_RHPAIE_ECHELONNEMENT_SEQUENCE")
	@Column(unique=true, nullable=false)
	private Long id;
	
	private Double montant;
	
	private Boolean paye;
	
	@Transient
	private String montantEch;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private PretPersonnel pretPersonnel;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private PeriodePaie periodePaie;
	
	@JsonSerialize(using = CustomDateDeserializer.class)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private java.util.Date dateRemboursement;
	
	@Transient
	private String dRemboursement;
	
	
	@Transient
	private String libpayer;
	
	@Transient
	private String message;
	
	public Echelonnement() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getMontant() {
		return montant;
	}

	public void setMontant(Double montant) {
		this.montant = montant;
	}

	public String getMontantEch() {
		montantEch = Utils.formattingAmount(montant);
		return montantEch;
	}

	public void setMontantEch(String montantEch) {
		this.montantEch = montantEch;
	}

	public PretPersonnel getPretPersonnel() {
		return pretPersonnel;
	}

	public void setPretPersonnel(PretPersonnel pretPersonnel) {
		this.pretPersonnel = pretPersonnel;
	}

	public PeriodePaie getPeriodePaie() {
		return periodePaie;
	}

	public void setPeriodePaie(PeriodePaie periodePaie) {
		this.periodePaie = periodePaie;
	}

	public java.util.Date getDateRemboursement() {
		return dateRemboursement;
	}

	public void setDateRemboursement(java.util.Date dateRemboursement) {
		this.dateRemboursement = dateRemboursement;
	}

	public String getdRemboursement() {
		dRemboursement = Utils.dateToString(dateRemboursement, "dd/MM/yyyy");
		return dRemboursement;
	}

	public void setdRemboursement(String dRemboursement) {
		this.dRemboursement = dRemboursement;
	}

	public Boolean getPaye() {
		return paye;
	}

	public void setPaye(Boolean paye) {
		this.paye = paye;
	}

	public String getLibpayer() {
		if(paye==true)
			libpayer="OUI";
		else
			libpayer="NON";
		return libpayer;
	}

	public void setLibpayer(String libpayer) {
		this.libpayer = libpayer;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Echelonnement [id=" + id + ", montant=" + montant + ", paye="
				+ paye + ", montantEch=" + montantEch + ", pretPersonnel="
				+ pretPersonnel + ", periodePaie=" + periodePaie
				+ ", dateRemboursement=" + dateRemboursement
				+ ", dRemboursement=" + dRemboursement + ", libpayer="
				+ libpayer + ", message=" + message + "]";
	}
}
