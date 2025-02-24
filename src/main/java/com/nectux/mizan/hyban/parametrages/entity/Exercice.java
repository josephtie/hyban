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

@Entity
@Component("exercice")
@Scope("prototype")
@Table(name="CGECI_RHPAIE_EXERCICE")
@SequenceGenerator(name="CGECI_RHPAIE_EXERCICE_SEQUENCE", sequenceName="CGECI_RHPAIE_EXERCICE_SEQ", initialValue=1, allocationSize=1)
public class Exercice {

	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CGECI_RHPAIE_EXERCICE_SEQUENCE")
	@Column(unique=true, nullable=false)
	private Long id;
	
	private String annee;
	
	private Boolean cloture;
//	private Boolean delete;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAnnee() {
		return annee;
	}

	public void setAnnee(String annee) {
		this.annee = annee;
	}

	

	public Boolean getCloture() {
		return cloture;
	}

	public void setCloture(Boolean cloture) {
		this.cloture = cloture;
	}

	public Exercice() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Exercice [id=" + id + ", annee=" + annee + ", cloture="
				+ cloture + "]";
	}
	
	
	
}
