package com.nectux.mizan.hyban.rh.formation.entity;

import com.nectux.mizan.hyban.personnel.entity.Personnel;
import com.nectux.mizan.hyban.utils.CustomDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Component("formationPersonnel")
@Scope("prototype")
@Table(name="CGECI_RHPAIE_FORMATION_PERSONNEL")
@SequenceGenerator(name="CGECI_RHPAIE_FORMATION_PERSONNEL_SEQUENCE", sequenceName="CGECI_RHPAIE_FORMATION_PERSONNEL_SEQ", initialValue=1, allocationSize=1)
public class FormationPersonnel {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CGECI_RHPAIE_FORMATION_PERSONNEL_SEQUENCE")
	@Column(unique=true, nullable=false)
	private Long id;
	
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
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private Personnel personnel;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private Formation formation;

	public FormationPersonnel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Personnel getPersonnel() {
		return personnel;
	}

	public void setPersonnel(Personnel personnel) {
		this.personnel = personnel;
	}

	public Formation getFormation() {
		return formation;
	}

	public void setFormation(Formation formation) {
		this.formation = formation;
	}

	@Override
	public String toString() {
		return "FormationPersonnel [id=" + id + ", dateCreation=" + dateCreation + ", dCreation=" + dCreation
				+ ", dateModification=" + dateModification + ", dModification=" + dModification + ", personnel="
				+ personnel + ", formation=" + formation + "]";
	}

}
