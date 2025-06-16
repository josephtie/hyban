package com.nectux.mizan.hyban.rh.carriere.entity;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Component("site")
@Scope("prototype")
@Table(name="CGECI_RHPAIE_SITE")
@SequenceGenerator(name="CGECI_RHPAIE_SITE_SEQUENCE", sequenceName="CGECI_RHPAIE_SITE_SEQ", initialValue=1, allocationSize=1)
public class Site {


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SITE_SEQ_GEN")
	@SequenceGenerator(name = "SITE_SEQ_GEN", sequenceName = "CGECI_RHPAIE_SITE_SEQ", allocationSize = 1)
	private Long id;

	@Column(unique=true, nullable=false)
	private String libelle;

	public Site() {
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
		return "SiteWork{" +
				"id=" + id +
				", libelle='" + libelle + '\'' +
				'}';
	}
}
