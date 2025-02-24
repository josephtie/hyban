package com.nectux.mizan.hyban.parametrages.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

import com.nectux.mizan.hyban.utils.CustomDateDeserializer;
import com.nectux.mizan.hyban.utils.Utils;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.context.annotation.Scope;
//import org.springframework.data.annotation.Transient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

@Entity
@Component("rubrique")
@Scope("prototype")
@Table(name="CGECI_RHPAIE_RUBRIQUE")
@SequenceGenerator(name="CGECI_RHPAIE_RUBRIQUE_SEQUENCE", sequenceName="CGECI_RHPAIE_RUBRIQUE_SEQ", initialValue=1, allocationSize=1)
public class Rubrique {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CGECI_RHPAIE_RUBRIQUE_SEQUENCE")
	@Column(unique=true, nullable=false)
	private Long id;
	
	@Column(unique=true, nullable=false)
	private String libelle;
	
	@Column( nullable=true)
	private Double taux;
	
	
	@Column( nullable=true)
	private Double mtExedent;
	
	
	@Transient
	private String strmtExedent;
	
	
	@Column(nullable=false)
	private Integer etatImposition;
	

	@JsonSerialize(using = CustomDateDeserializer.class)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private java.util.Date dateCreate;
	
	@Transient
	private String dCreate;
	
	@Transient
	private String stretatimposition;
	
	private Boolean active;

	private Boolean permanent;

	public Rubrique() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public Double getTaux() {
		return taux;
	}

	public void setTaux(Double taux) {
		this.taux = taux;
	}

	public Integer getEtatImposition() {
		return etatImposition;
	}

	public void setEtatImposition(Integer etatImposition) {
		this.etatImposition = etatImposition;
	}

	public java.util.Date getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(java.util.Date dateCreate) {
		this.dateCreate = dateCreate;
	}

	public String getdCreate() {
		return dCreate;
	}

	public void setdCreate(String dCreate) {
		this.dCreate = dCreate;
	}

	
	
	public Double getMtExedent() {
		return mtExedent;
	}

	public void setMtExedent(Double mtExedent) {
		this.mtExedent = mtExedent;
	}

	public String getStretatimposition() {
		if(etatImposition==null)
			stretatimposition="";
		if(etatImposition==1)
			stretatimposition="Imposable";
		if(etatImposition==2)
			stretatimposition="Non Imposable";
		if(etatImposition==3)
			stretatimposition="Imposable et Non Imposable";
		if(etatImposition==4)
			stretatimposition="Retenue Mutuelle";
		if(etatImposition==5)
			stretatimposition="Regularisation";
		return stretatimposition;
	}

	public void setStretatimposition(String stretatimposition) {
		this.stretatimposition = stretatimposition;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getStrmtExedent() {
		return strmtExedent=Utils.formattingAmount(mtExedent);
	}

	public void setStrmtExedent(String strmtExedent) {
		this.strmtExedent = strmtExedent;
	}

	public Boolean getPermanent() {
		return permanent;
	}

	public void setPermanent(Boolean permanent) {
		this.permanent = permanent;
	}


	@Override
	public String toString() {
		return "Rubrique{" +
				"id=" + id +
				", libelle='" + libelle + '\'' +
				", taux=" + taux +
				", mtExedent=" + mtExedent +
				", strmtExedent='" + strmtExedent + '\'' +
				", etatImposition=" + etatImposition +
				", dateCreate=" + dateCreate +
				", dCreate='" + dCreate + '\'' +
				", stretatimposition='" + stretatimposition + '\'' +
				", active=" + active +
				", permanent=" + permanent +
				'}';
	}
}
