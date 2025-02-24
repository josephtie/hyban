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

import com.nectux.mizan.hyban.utils.CustomDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.nectux.mizan.hyban.personnel.entity.Personnel;
import com.nectux.mizan.hyban.utils.Utils;

@Entity
@Component("rubriqvariable")
@Scope("prototype")
@Table(name="CGECI_RHPAIE_RUBRIQVARIABLE")
@SequenceGenerator(name="CGECI_RHPAIE_RUBRIQVARIABLE_SEQUENCE", sequenceName="CGECI_RHPAIE_RUBRIQVARIABLE_SEQ", initialValue=1, allocationSize=1)
public class RubriqVariable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CGECI_RHPAIE_RUBRIQVARIABLE_SEQUENCE")
	@Column(unique=true, nullable=false)
	private Long id;
	
	private Double cn;
	
	private Double igr;
	
    private Double amao;
	
	private Double synaoni;
	
	private Double mugefci;
	
	private Double ivoireSante;
	
	private Double ivoirePrev;
	
	private Double diversgainsImp;
	
	private Double diversgains;
	
	private Double regularisation;
	
	
	@Transient
	private String mtregularisation;
	
	
	@Transient
	private String mtcn;
	@Transient
	private String mtigr;
	@Transient
	private String mtamao;
	@Transient
	private String mtsynaoni;
	@Transient
	private String mtivoireSante;
	@Transient
	private String mtivoirePrev;
	
	@Transient
	private String mtmugefci;
	
	@Transient
	private String mtdiversgainsImp;
	@Transient
	private String mtdiversgains;
	
	//private Boolean paye;
	

	@ManyToOne
	@JoinColumn(nullable=false)
	private Personnel personnel;
	
	/*@ManyToOne
	@JoinColumn(nullable=false)
	private PeriodePaie periodePaie;*/
	
	@JsonSerialize(using = CustomDateDeserializer.class)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private java.util.Date datedesaisie;
	
	@Transient
	private String dDatedesaisie;
	
	
	
	
	public RubriqVariable() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Double getCn() {
		return cn;
	}

	public void setCn(Double cn) {
		this.cn = cn;
	}

	public Double getIgr() {
		return igr;
	}

	public void setIgr(Double igr) {
		this.igr = igr;
	}

	public Double getAmao() {
		return amao;
	}

	public void setAmao(Double amao) {
		this.amao = amao;
	}

	public Double getSynaoni() {
		return synaoni;
	}

	public void setSynaoni(Double synaoni) {
		this.synaoni = synaoni;
	}

	public Double getIvoireSante() {
		return ivoireSante;
	}

	public void setIvoireSante(Double ivoireSante) {
		this.ivoireSante = ivoireSante;
	}

	public Double getIvoirePrev() {
		return ivoirePrev;
	}

	public void setIvoirePrev(Double ivoirePrev) {
		this.ivoirePrev = ivoirePrev;
	}

	public Double getDiversgainsImp() {
		return diversgainsImp;
	}

	public void setDiversgainsImp(Double diversgainsImp) {
		this.diversgainsImp = diversgainsImp;
	}

	public Double getDiversgains() {
		return diversgains;
	}

	public void setDiversgains(Double diversgains) {
		this.diversgains = diversgains;
	}

	public String getMtcn() {
		return mtcn=Utils.formattingAmount(cn);
	}

	public void setMtcn(String mtcn) {
		this.mtcn = mtcn;
	}

	public String getMtigr() {
		return mtigr=Utils.formattingAmount(igr);
	}

	public void setMtigr(String mtigr) {
		this.mtigr = mtigr;
	}

	public String getMtamao() {
		return mtamao=Utils.formattingAmount(amao);
	}

	public void setMtamao(String mtamao) {
		this.mtamao = mtamao;
	}

	public String getMtsynaoni() {
		return mtsynaoni=Utils.formattingAmount(synaoni);
	}

	public void setMtsynaoni(String mtsynaoni) {
		this.mtsynaoni = mtsynaoni;
	}

	public String getMtivoireSante() {
		return mtivoireSante=Utils.formattingAmount(ivoireSante);
	}

	public void setMtivoireSante(String mtivoireSante) {
		this.mtivoireSante = mtivoireSante;
	}

	public String getMtivoirePrev() {
		return mtivoirePrev=Utils.formattingAmount(ivoirePrev);
	}

	public void setMtivoirePrev(String mtivoirePrev) {
		this.mtivoirePrev = mtivoirePrev;
	}

	public String getMtdiversgainsImp() {
		return mtdiversgainsImp=Utils.formattingAmount(diversgainsImp);
	}

	public void setMtdiversgainsImp(String mtdiversgainsImp) {
		this.mtdiversgainsImp = mtdiversgainsImp;
	}

	public String getMtdiversgains() {
		return mtdiversgains=Utils.formattingAmount(diversgains);
	}

	public void setMtdiversgains(String mtdiversgains) {
		this.mtdiversgains = mtdiversgains;
	}

	public Double getMugefci() {
		return mugefci;
	}

	public void setMugefci(Double mugefci) {
		this.mugefci = mugefci;
	}

	public String getMtmugefci() {
		return mtmugefci=Utils.formattingAmount(mugefci);
	}

	public void setMtmugefci(String mtmugefci) {
		this.mtmugefci = mtmugefci;
	}

	public Double getRegularisation() {
		return regularisation;
	}

	public void setRegularisation(Double regularisation) {
		this.regularisation = regularisation;
	}

	public String getMtregularisation() {
		return mtregularisation=Utils.formattingAmount(regularisation);
	}

	public void setMtregularisation(String mtregularisation) {
		this.mtregularisation = mtregularisation;
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
