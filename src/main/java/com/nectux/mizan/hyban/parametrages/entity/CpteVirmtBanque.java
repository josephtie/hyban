package com.nectux.mizan.hyban.parametrages.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Entity
@Component("cptvirmtbanque")
@Scope("prototype")
@Table(name="CGECI_RHPAIE_CPTEVIRMTBANQUE")
@SequenceGenerator(name="CGECI_RHPAIE_CPTEVIRMTBANQUE_SEQUENCE", sequenceName="CGECI_RHPAIE_CPTEVIRMTBANQUE_SEQ", initialValue=1, allocationSize=1)
public class CpteVirmtBanque {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CGECI_RHPAIE_CPTEVIRMTBANQUE_SEQUENCE")
	@Column(unique=true, nullable=false)
	private Long id;
	
	
	
	@Column( nullable=true)
	private String numguichCpteVirmt;
	
	@Column( nullable=true)
	private String numcpteCpteVirmt;
	
	@Column( nullable=true)
	private String donneurOrdCpteVirmt;
	
	
	@Column( nullable=true)
	private String codEtablVirmt;
	
	@Column( nullable=true)
	private Integer ribCpteVirmt;
	
	@ManyToOne
	@JoinColumn(unique=false)
	private Banque bank;

	public CpteVirmtBanque() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumguichCpteVirmt() {
		return numguichCpteVirmt;
	}

	public void setNumguichCpteVirmt(String numguichCpteVirmt) {
		this.numguichCpteVirmt = numguichCpteVirmt;
	}

	public String getNumcpteCpteVirmt() {
		return numcpteCpteVirmt;
	}

	public void setNumcpteCpteVirmt(String numcpteCpteVirmt) {
		this.numcpteCpteVirmt = numcpteCpteVirmt;
	}

	public String getDonneurOrdCpteVirmt() {
		return donneurOrdCpteVirmt;
	}

	public void setDonneurOrdCpteVirmt(String donneurOrdCpteVirmt) {
		this.donneurOrdCpteVirmt = donneurOrdCpteVirmt;
	}

	public String getCodEtablVirmt() {
		return codEtablVirmt;
	}

	public void setCodEtablVirmt(String codEtablVirmt) {
		this.codEtablVirmt = codEtablVirmt;
	}

	public Integer getRibCpteVirmt() {
		return ribCpteVirmt;
	}

	public void setRibCpteVirmt(Integer ribCpteVirmt) {
		this.ribCpteVirmt = ribCpteVirmt;
	}

	public Banque getBank() {
		return bank;
	}

	public void setBank(Banque bank) {
		this.bank = bank;
	}

	@Override
	public String toString() {
		return "CpteVirmtBanque [id=" + id + ", numguichCpteVirmt="
				+ numguichCpteVirmt + ", numcpteCpteVirmt=" + numcpteCpteVirmt
				+ ", donneurOrdCpteVirmt=" + donneurOrdCpteVirmt
				+ ", codEtablVirmt=" + codEtablVirmt + ", ribCpteVirmt="
				+ ribCpteVirmt + ", bank=" + bank + "]";
	}

	

	

	

	
}
