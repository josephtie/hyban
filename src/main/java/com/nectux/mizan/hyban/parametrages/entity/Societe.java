package com.nectux.mizan.hyban.parametrages.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;



@Entity
@Component("societe")
@Scope("prototype")
@Table(name="CGECI_RHPAIE_SOCIETE")
@SequenceGenerator(name="CGECI_RHPAIE_SOCIETE_SEQUENCE", sequenceName="CGECI_RHPAIE_SOCIETE_SEQ", initialValue=1, allocationSize=1)
public class Societe {

	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CGECI_RHPAIE_SOCIETE_SEQUENCE")
	@Column(unique=true, nullable=false)
	private Long id;
	
	private String raisonsoc;
	
	
	private String sigle;
	
	private String activitepp;
	
	private String adress;
	
	
	private String formjuri;
	
	private String telephone;
	
	private String bp;
	
	private String commune;
	
	private String quartier;
	
	private String rue;
	
	
	private String lot;
	
	private String sectpartiell;
	
	
	private String centreImpot;
	
	private String codeEts;
	
	
	private String codeActivite;
	
	private String codeEmployeur;
	
	private String cpteContrib;
	
	
	private Double txretraite;
	
	
	private Double txacctr;
	
	
	private Double txprest;
	
	
	
    private String nomcomptable;
	
	
	private String telcomptable;
	
	
	private String adrcomptable;



	private Double txgratif;

	private Long gratification;

	@Transient
	private String gratifica;

	@Transient
	private MultipartFile fileData;
	
	private String urlLogo;
	

	public Societe() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public String getRaisonsoc() {
		return raisonsoc;
	}

	public void setRaisonsoc(String raisonsoc) {
		this.raisonsoc = raisonsoc;
	}

	public String getSigle() {
		return sigle;
	}

	public void setSigle(String sigle) {
		this.sigle = sigle;
	}

	public String getActivitepp() {
		return activitepp;
	}

	public void setActivitepp(String activitepp) {
		this.activitepp = activitepp;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getFormjuri() {
		return formjuri;
	}

	public void setFormjuri(String formjuri) {
		this.formjuri = formjuri;
	}

	public String getBp() {
		return bp;
	}

	public void setBp(String bp) {
		this.bp = bp;
	}

	public String getCommune() {
		return commune;
	}

	public void setCommune(String commune) {
		this.commune = commune;
	}

	public String getQuartier() {
		return quartier;
	}

	public void setQuartier(String quartier) {
		this.quartier = quartier;
	}

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getLot() {
		return lot;
	}

	public void setLot(String lot) {
		this.lot = lot;
	}

	public String getSectpartiell() {
		return sectpartiell;
	}

	public void setSectpartiell(String sectpartiell) {
		this.sectpartiell = sectpartiell;
	}

	public String getCentreImpot() {
		return centreImpot;
	}

	public void setCentreImpot(String centreImpot) {
		this.centreImpot = centreImpot;
	}

	public String getCodeEts() {
		return codeEts;
	}

	public void setCodeEts(String codeEts) {
		this.codeEts = codeEts;
	}

	public String getCodeActivite() {
		return codeActivite;
	}

	public void setCodeActivite(String codeActivite) {
		this.codeActivite = codeActivite;
	}

	public String getCodeEmployeur() {
		return codeEmployeur;
	}

	public void setCodeEmployeur(String codeEmployeur) {
		this.codeEmployeur = codeEmployeur;
	}

	public String getCpteContrib() {
		return cpteContrib;
	}

	public void setCpteContrib(String cpteContrib) {
		this.cpteContrib = cpteContrib;
	}


	public String getNomcomptable() {
		return nomcomptable;
	}

	public void setNomcomptable(String nomcomptable) {
		this.nomcomptable = nomcomptable;
	}

	public String getTelcomptable() {
		return telcomptable;
	}

	public void setTelcomptable(String telcomptable) {
		this.telcomptable = telcomptable;
	}

	public String getAdrcomptable() {
		return adrcomptable;
	}

	public void setAdrcomptable(String adrcomptable) {
		this.adrcomptable = adrcomptable;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Double getTxretraite() {
		return txretraite;
	}

	public void setTxretraite(Double txretraite) {
		this.txretraite = txretraite;
	}

	public Double getTxacctr() {
		return txacctr;
	}

	public void setTxacctr(Double txacctr) {
		this.txacctr = txacctr;
	}

	public Double getTxprest() {
		return txprest;
	}

	public void setTxprest(Double txprest) {
		this.txprest = txprest;
	}


	public MultipartFile getFileData() {
		return fileData;
	}


	public void setFileData(MultipartFile fileData) {
		this.fileData = fileData;
	}


	public String getUrlLogo() {
		return urlLogo;
	}


	public void setUrlLogo(String urlLogo) {
		this.urlLogo = urlLogo;
	}

	public Double getTxgratif() {
		return txgratif;
	}

	public void setTxgratif(Double txgratif) {
		this.txgratif = txgratif;
	}

	public Long getGratification() {
		return gratification;
	}

	public void setGratification(Long gratification) {
		this.gratification = gratification;
	}

	public String getGratifica() {
		boolean gop;

		if(gratification==null)
			gratifica="";
		if(gratification==1)
			gratifica = "SALAIRE CATEGORIEL";

		if(gratification==2)
			gratifica = "SALAIRE NET A PAYER";


		return gratifica;
	}

	public void setGratifica(String gratifica) {
		this.gratifica = gratifica;
	}

	public static boolean isNullorZero(Integer i){
		return 0 == ( i == null ? 0 : i);
	}
}
