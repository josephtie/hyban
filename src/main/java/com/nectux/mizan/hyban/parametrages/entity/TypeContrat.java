package com.nectux.mizan.hyban.parametrages.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
/*
##############  Author : AFOLABI Jamal Deen
##############  Email  : jamaldeen25@gmail.com
##############  Date   : 02/02/2016 a 11:51
##############  File   : TypeContrat.java
################  Edit Part ###################
##############  Date   : 
##############  Author : 
 */

@Entity
@Component("typeContrat")
@Scope("prototype")
@Table(name="CGECI_RHPAIE_TYPE_CONTRAT")
@SequenceGenerator(name="CGECI_RHPAIE_TYPE_CONTRAT_SEQUENCE", sequenceName="CGECI_RHPAIE_TYPE_CONTRAT_SEQ", initialValue=1, allocationSize=1)

public class TypeContrat extends Auditable {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CGECI_RHPAIE_TYPE_CONTRAT_SEQUENCE")
	@Column(unique=true, nullable=false)
	private Long id;
	
	private String libelle;

	public TypeContrat() {
		super();
		// TODO Auto-generated constructor stub
	}

	//#########   Getters and Setters  #########
	
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

	@Override
	public String toString() {
		return "TypeContrat [id=" + id + ", libelle=" + libelle + "]";
	}
	
}
