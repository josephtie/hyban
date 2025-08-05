package com.nectux.mizan.hyban.personnel.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.nectux.mizan.hyban.parametrages.entity.Auditable;
import com.nectux.mizan.hyban.utils.Utils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Entity
@Component("categorie")
@Scope("prototype")
@Table(name="CGECI_RHPAIE_CATEGORIE")
@SequenceGenerator(name="CGECI_RHPAIE_CATEGORIE_SEQUENCE", sequenceName="CGECI_RHPAIE_CATEGORIE_SEQ", initialValue=1, allocationSize=1)
public class Categorie extends Auditable {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CGECI_RHPAIE_CATEGORIE_SEQUENCE")
	@Column(unique=true, nullable=false)
	private Long id;
	
	@Column(unique=false, nullable=false)
	private String libelle;
	
	@Column(nullable=false)
	private Double salaireDeBase;
	
	@Transient
	private String salaireBase;
	
	private Double indemniteLogement;
	
	@Transient
	private String montantIndemniteLogement;

	public Categorie() {
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

	public Double getSalaireDeBase() {
		return salaireDeBase;
	}

	public void setSalaireDeBase(Double salaireDeBase) {
		this.salaireDeBase = salaireDeBase;
	}

	public String getSalaireBase() {
		salaireBase = Utils.formattingAmount(salaireDeBase);
		return salaireBase;
	}

	public void setSalaireBase(String salaireBase) {
		this.salaireBase = salaireBase;
	}

	public Double getIndemniteLogement() {
		return indemniteLogement;
	}

	public void setIndemniteLogement(Double indemniteLogement) {
		this.indemniteLogement = indemniteLogement;
	}

	public String getMontantIndemniteLogement() {
		montantIndemniteLogement=Utils.formattingAmount(indemniteLogement);
		return montantIndemniteLogement;
	}

	public void setMontantIndemniteLogement(String montantIndemniteLogement) {
		this.montantIndemniteLogement = montantIndemniteLogement;
	}


	

	
	
}
