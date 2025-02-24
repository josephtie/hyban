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

import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.utils.CustomDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.nectux.mizan.hyban.parametrages.entity.Rubrique;
import com.nectux.mizan.hyban.personnel.entity.ContratPersonnel;
import com.nectux.mizan.hyban.utils.Utils;

@Entity
@Component("primePersonnel")
@Scope("prototype")
@Table(name="CGECI_RHPAIE_PRIME_PERSONNEL")
@SequenceGenerator(name="CGECI_RHPAIE_PRIME_PERSONNEL_SEQUENCE", sequenceName="CGECI_RHPAIE_PRIME_PERSONNEL_SEQ", initialValue=1, allocationSize=1)
public class PrimePersonnel {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CGECI_RHPAIE_PRIME_PERSONNEL_SEQUENCE")
	@Column(unique=true, nullable=false)
	private Long id;
	
	private Double montant;
	
	
	private Integer valeur;
	

	
	@ManyToOne
	@JoinColumn(nullable=false)
	private Rubrique prime;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private ContratPersonnel contratPersonnel;
	
	@JsonSerialize(using = CustomDateDeserializer.class)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private java.util.Date dateSaisie;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private PeriodePaie periodePaie;
	
	@Transient
	private String mtmontant;
	
	@Transient
	private String dSaisie;
	

	
	public PrimePersonnel() {
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


	
	public PeriodePaie getPeriode() {
		return periodePaie;
	}

	public void setPeriode(PeriodePaie periodePaie) {
		this.periodePaie = periodePaie;
	}

	public Integer getValeur() {
		return valeur;
	}

	public void setValeur(Integer valeur) {
		this.valeur = valeur;
	}

	public Rubrique getPrime() {
		return prime;
	}

	public void setPrime(Rubrique prime) {
		this.prime = prime;
	}

	public ContratPersonnel getContratPersonnel() {
		return contratPersonnel;
	}

	public void setContratPersonnel(ContratPersonnel contratPersonnel) {
		this.contratPersonnel = contratPersonnel;
	}

	public java.util.Date getDateSaisie() {
		return dateSaisie;
	}

	public void setDateSaisie(java.util.Date dateSaisie) {
		this.dateSaisie = dateSaisie;
	}

	public String getMtmontant() {
		return mtmontant=Utils.formattingAmount(montant);
	}

	public void setMtmontant(String mtmontant) {
		this.mtmontant = mtmontant;
	}

	public String getdSaisie() {
		return dSaisie=Utils.dateToString(dateSaisie, "dd/MM/yyyy");
	}

	public void setdSaisie(String dSaisie) {
		this.dSaisie = dSaisie;
	}

	


	
}
