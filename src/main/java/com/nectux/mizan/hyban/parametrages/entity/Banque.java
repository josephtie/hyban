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
@Component("banque")
@Scope("prototype")
@Table(name="CGECI_RHPAIE_BANQUE")
@SequenceGenerator(name="CGECI_RHPAIE_BANQUE_SEQUENCE", sequenceName="CGECI_RHPAIE_BANQUE_SEQ", initialValue=1, allocationSize=1)
public class Banque extends Auditable{

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CGECI_RHPAIE_BANQUE_SEQUENCE")
	@Column(unique=true, nullable=false)
	private Long id;
	
	@Column(unique=true, nullable=false)
	private String libelle;
	
	@Column( nullable=false)
	private String sigle;
	
	@Column(nullable=false)
	private String codbanq;
	
	@Column( nullable=true)
	private String numguich;
	
	@Column( nullable=true)
	private String numcpte;
	
	@Column( nullable=true)
	private Integer rib;
	
	private Boolean statut;

	public Banque() {
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

	public String getSigle() {
		return sigle;
	}

	public void setSigle(String sigle) {
		this.sigle = sigle;
	}

	public String getCodbanq() {
		return codbanq;
	}

	public void setCodbanq(String codbanq) {
		this.codbanq = codbanq;
	}

	public String getNumguich() {
		return numguich;
	}

	public void setNumguich(String numguich) {
		this.numguich = numguich;
	}

	public Integer getRib() {
		return rib;
	}

	public void setRib(Integer rib) {
		this.rib = rib;
	}

	
	public String getNumcpte() {
		return numcpte;
	}

	public void setNumcpte(String numcpte) {
		this.numcpte = numcpte;
	}

	
	
	public Boolean getStatut() {
		return statut;
	}

	public void setStatut(Boolean statut) {
		this.statut = statut;
	}

	@Override
	public String toString() {
		return "Banque [id=" + id + ", libelle=" + libelle + ", sigle=" + sigle
				+ ", codbanq=" + codbanq + ", numguich=" + numguich
				+ ", numcpte=" + numcpte + ", rib=" + rib + ", statut="
				+ statut + "]";
	}

	
}
