package com.nectux.mizan.hyban.parametrages.entity;

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
import com.nectux.mizan.hyban.utils.DateManager;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.nectux.mizan.hyban.personnel.entity.ContratPersonnel;

@Entity
@Component("planningConge")
@Scope("prototype")
@Table(name="CGECI_RHPAIE_PLANNING_CONGE")
@SequenceGenerator(name="CGECI_RHPAIE_PLANNING_CONGE_SEQUENCE", sequenceName="CGECI_RHPAIE_PLANNING_CONGE_SEQ", initialValue=1, allocationSize=1)
public class PlanningConge {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CGECI_RHPAIE_PLANNING_CONGE_SEQUENCE")
	@Column(unique=true, nullable=false)
	private Long id;
	
	@JsonSerialize(using = CustomDateDeserializer.class)
	@Temporal(javax.persistence.TemporalType.DATE)
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private java.util.Date dateDepart;
	
	@Transient
	private String dDepart;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private ContratPersonnel contratPersonnel;
	
	private Boolean statut;
	
	@Transient
	private String statutValeur;
	
	/*@ManyToOne
	@JoinColumn(nullable=false)
	private Exercice exercice;*/

	
	@ManyToOne
	@JoinColumn(nullable=true)
	private PeriodePaie periodePaie;
	
	public PlanningConge() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public java.util.Date getDateDepart() {
		return dateDepart;
	}

	public void setDateDepart(java.util.Date dateDepart) {
		this.dateDepart = dateDepart;
	}

	public String getdDepart() {
		return DateManager.dateToString(dateDepart, "dd/MM/yyyy");
	}

	public void setdDepart(String dDepart) {
		this.dDepart = dDepart;
	}

	public Boolean getStatut() {
		return statut;
	}

	public void setStatut(Boolean statut) {
		this.statut = statut;
	}

	public String getStatutValeur() {
		if(statut)
			return "OUI";
		else
			return "NON";
	}

	public void setStatutValeur(String statutValeur) {
		this.statutValeur = statutValeur;
	}

	public ContratPersonnel getContratPersonnel() {
		return contratPersonnel;
	}

	public void setContratPersonnel(ContratPersonnel contratPersonnel) {
		this.contratPersonnel = contratPersonnel;
	}

	/*public Exercice getExercice() {
		return exercice;
	}

	public void setExercice(Exercice exercice) {
		this.exercice = exercice;
	}*/

	public PeriodePaie getPeriodePaie() {
		return periodePaie;
	}

	public void setPeriodePaie(PeriodePaie periodePaie) {
		this.periodePaie = periodePaie;
	}

	@Override
	public String toString() {
		return "PlanningConge [id=" + id + ", dateDepart=" + dateDepart
				+ ", dDepart=" + dDepart + ", contratPersonnel="
				+ contratPersonnel + ", statut=" + statut + ", statutValeur="
				+ statutValeur + ", periodePaie=" + periodePaie + "]";
	}
	
	
	
	

	

}
