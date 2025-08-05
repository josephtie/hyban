package com.nectux.mizan.hyban.parametrages.entity;

import java.util.Date;

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


import com.nectux.mizan.hyban.utils.DateManager;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

@Entity
@Component("periodepaie")
@Scope("prototype")
@Table(name="CGECI_RHPAIE_PERIODEPAIE")
@SequenceGenerator(name="CGECI_RHPAIE_PERIODEPAIE_SEQUENCE", sequenceName="CGECI_RHPAIE_PERIODEPAIE_SEQ", initialValue=1, allocationSize=1)
public class PeriodePaie extends Auditable {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CGECI_RHPAIE_PERIODEPAIE_SEQUENCE")
	@Column(unique=true, nullable=false)
	private Long id;
	
	@Column()
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date datedeb;
	
	@Transient
	private String ddeb;
	
	@Column()
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date datefin;
	
	@Transient
	private String dfin;
	
	
	
	
	@Column(nullable = true)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date datecloture;
	
	@Transient
	private String dcloture;
	
	@Transient
	private String affiche;
	
	@ManyToOne
	@JoinColumn(unique=false)
	private Exercice annee;
	
	@ManyToOne
	@JoinColumn(unique=false)
	private Mois mois;
	
   
	


	private Boolean cloture;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Date getDatedeb() {
		return datedeb;
	}


	public void setDatedeb(Date datedeb) {
		this.datedeb = datedeb;
	}


	public String getDdeb() {
		return ddeb= DateManager.dateToString(datedeb, "dd/MM/yyyy");
	}


	public void setDdeb(String ddeb) {
		this.ddeb = ddeb;
	}


	public Date getDatefin() {
		return datefin ;
	}


	public void setDatefin(Date datefin) {
		this.datefin = datefin;
	}


	public String getDfin() {
		return dfin= DateManager.dateToString(datefin, "dd/MM/yyyy");
	}


	public void setDfin(String dfin) {
		this.dfin = dfin;
	}


	

	public Date getDatecloture() {
		return datecloture;
	}


	public void setDatecloture(Date datecloture) {
		this.datecloture = datecloture;
	}


	public String getDcloture() {
		return dcloture= DateManager.dateToString(datecloture, "dd/MM/yyyy");
	}


	public void setDcloture(String dcloture) {
		this.dcloture = dcloture;
	}


	public String getAffiche() {
		affiche= mois.getMois() +" "+annee.getAnnee();
		return affiche;
	}


	public void setAffiche(String affiche) {
		this.affiche = affiche;
	}


	public Exercice getAnnee() {
		return annee;
	}


	public void setAnnee(Exercice annee) {
		this.annee = annee;
	}


	public Mois getMois() {
		return mois;
	}


	public void setMois(Mois mois) {
		this.mois = mois;
	}




	public Boolean getCloture() {
		return cloture;
	}


	public void setCloture(Boolean cloture) {
		this.cloture = cloture;
	}


	@Override
	public String toString() {
		return "PeriodePaie [id=" + id + ", datedeb=" + datedeb + ", ddeb="
				+ ddeb + ", datefin=" + datefin + ", dfin=" + dfin
				+ ", datecloture=" + datecloture + ", dcloture=" + dcloture
				+ ", affiche=" + affiche + ", annee=" + annee + ", mois="
				+ mois + ", cloture=" + cloture + "]";
	}


	public PeriodePaie() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
