package com.nectux.mizan.hyban.personnel.entity;

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
@Component("nationnalite")
@Scope("prototype")
@Table(name="CGECI_RHPAIE_NATIONNALITE")
@SequenceGenerator(name="CGECI_RHPAIE_NATIONNALITE_SEQUENCE", sequenceName="CGECI_RHPAIE_NATIONNALITE_SEQ", initialValue=1, allocationSize=1)
public class Nationnalite {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CGECI_RHPAIE_NATIONNALITE_SEQUENCE")
	@Column(unique=true, nullable=false)
	private Long id;
	
	private String libelle;

	public Nationnalite() {
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

	@Override
	public String toString() {
		return "Fonction [id=" + id + ", libelle=" + libelle + "]";
	}
	
}
