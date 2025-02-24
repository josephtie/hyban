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
@Component("mois")
@Scope("prototype")
@Table(name="CGECI_RHPAIE_MOIS")
@SequenceGenerator(name="CGECI_RHPAIE_MOIS_SEQUENCE", sequenceName="CGECI_RHPAIE_MOIS_SEQ", initialValue=1, allocationSize=1)
public class Mois {

	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CGECI_RHPAIE_MOIS_SEQUENCE")
	@Column(unique=true, nullable=false)
	private Long id;
	
	private String mois;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getMois() {
		return mois;
	}

	public void setMois(String mois) {
		this.mois = mois;
	}

	

	public Mois() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Mois [id=" + id + ", mois=" + mois + "]";
	}
	
	
	
}
