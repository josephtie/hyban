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
import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.personnel.entity.Personnel;
import com.nectux.mizan.hyban.utils.CustomDateDeserializer;
import com.nectux.mizan.hyban.utils.Utils;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

@Entity
@Component("tempseffectif")
@Scope("prototype")
@Table(name="CGECI_RHPAIE_TEMPEFFECTIF")
@SequenceGenerator(name="CGECI_RHPAIE_TEMPEFFECTIF_SEQUENCE", sequenceName="CGECI_RHPAIE_TEMPEFFECTIF_SEQ", initialValue=1, allocationSize=1)
public class TempEffectif extends Auditable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CGECI_RHPAIE_TEMPEFFECTIF_SEQUENCE")
	@Column(unique=true, nullable=false)
	private Long id;
	
	private Double jourspresence;
	
	private Double heurspresence;
	
	//private Boolean paye;
	

	@ManyToOne
	@JoinColumn(nullable=false)
	private Personnel personnel;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private PeriodePaie periodePaie;
	
	@JsonSerialize(using = CustomDateDeserializer.class)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private java.util.Date datedesaisie;
	
	@Transient
	private String dDatedesaisie;
	
	
	
	
	public TempEffectif() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	

	public PeriodePaie getPeriodePaie() {
		return periodePaie;
	}

	public void setPeriodePaie(PeriodePaie periodePaie) {
		this.periodePaie = periodePaie;
	}

	public Double getJourspresence() {
		return jourspresence;
	}

	public void setJourspresence(Double jourspresence) {
		this.jourspresence = jourspresence;
	}

	public Double getHeurspresence() {
		return heurspresence;
	}

	public void setHeurspresence(Double heurspresence) {
		this.heurspresence = heurspresence;
	}

	/*public Boolean getPaye() {
		return paye;
	}

	public void setPaye(Boolean paye) {
		this.paye = paye;
	}
*/
	public Personnel getPersonnel() {
		return personnel;
	}

	public void setPersonnel(Personnel personnel) {
		this.personnel = personnel;
	}

	public java.util.Date getDatedesaisie() {
		return datedesaisie;
	}

	public void setDatedesaisie(java.util.Date datedesaisie) {
		this.datedesaisie = datedesaisie;
	}

	public String getdDatedesaisie() {
		return dDatedesaisie= Utils.dateToString(datedesaisie, "dd/MM/yyyy");
	}

	public void setdDatedesaisie(String dDatedesaisie) {
		this.dDatedesaisie = dDatedesaisie;
	}

	@Override
	public String toString() {
		return "TempEffectif [id=" + id + ", jourspresence=" + jourspresence
				+ ", heurspresence=" + heurspresence + ", personnel=" + personnel + ", periodePaie=" + periodePaie
				+ ", datedesaisie=" + datedesaisie + ", dDatedesaisie="
				+ dDatedesaisie + "]";
	}

/*	public String getDDatedesaisie() {
		dDatedesaisie = Utils.dateToString(dateRemboursement, "dd/MM/yyyy");
		return dRemboursement;
	}

	public void setDDatedesaisie(String dDatedesaisie) {
		this.dDatedesaisie = dDatedesaisie;
	}
*/



}
