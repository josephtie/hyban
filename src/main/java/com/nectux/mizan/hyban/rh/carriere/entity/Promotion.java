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

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nectux.mizan.hyban.parametrages.entity.Auditable;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.nectux.mizan.hyban.utils.CustomDateDeserializer;
import com.nectux.mizan.hyban.utils.DateManager;

@Entity
@Component("promotion")
@Scope("prototype")
@Table(name="CGECI_RHPAIE_PROMOTION")
@SequenceGenerator(name="CGECI_RHPAIE_PROMOTION_SEQUENCE", sequenceName="CGECI_RHPAIE_PROMOTION_SEQ", initialValue=1, allocationSize=1)
public class Promotion extends Auditable {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CGECI_RHPAIE_PROMOTION_SEQUENCE")
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

	public Promotion() {
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
		return DateManager.dateToString(dateCreation, "dd/MM/yyyy HH:mm:ss");
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
		return DateManager.dateToString(dateModification, "dd/MM/yyyy HH:mm:ss");
	}

	public void setdModification(String dModification) {
		this.dModification = dModification;
	}

	@Override
	public String toString() {
		return "Promotion [id=" + id + ", libelle=" + libelle + ", description=" + description + ", dateCreation="
				+ dateCreation + ", dCreation=" + dCreation + ", dateModification=" + dateModification
				+ ", dModification=" + dModification + "]";
	}

}
