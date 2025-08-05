package com.nectux.mizan.hyban.rh.carriere.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

@Entity
@Component("typeSanction")
@Scope("prototype")
@Table(name="CGECI_RHPAIE_TYPE_SANCTION")
@SequenceGenerator(name="CGECI_RHPAIE_TYPE_SANCTION_SEQUENCE", sequenceName="CGECI_RHPAIE_TYPE_SANCTION_SEQ", initialValue=1, allocationSize=1)
public class TypeSanction extends Auditable {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CGECI_RHPAIE_TYPE_SANCTION_SEQUENCE")
	@Column(unique=true, nullable=false)
	private Long id;
	
	private String libelle;
	
	private String description;
	
	@JsonSerialize(using = CustomDateDeserializer.class)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
	private java.util.Date dateCreation;
	
	@Transient
	private String dCreation;
	
	@JsonSerialize(using = CustomDateDeserializer.class)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
	private java.util.Date dateModification;
	
	@Transient
	private String dModification;

	public TypeSanction() {
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public java.util.Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(java.util.Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public String getdCreation() {
		return dCreation;
	}

	public void setdCreation(String dCreation) {
		this.dCreation = dCreation;
	}

	public java.util.Date getDateModification() {
		return dateModification;
	}

	public void setDateModification(java.util.Date dateModification) {
		this.dateModification = dateModification;
	}

	public String getdModification() {
		return dModification;
	}

	public void setdModification(String dModification) {
		this.dModification = dModification;
	}

}
