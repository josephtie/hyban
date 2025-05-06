package com.nectux.mizan.hyban.paie.entity;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import javax.persistence.Transient;

import com.nectux.mizan.hyban.utils.Utils;


public class ImprimBulletinPaie implements Serializable {
	private static final long serialVersionUID = 1L;

	private String libelle;
	
	private String code;
	
	private Double gain;
	@Transient
	private String mtgain;
	
	private Double retenue;
	@Transient
	private String mtretenue;
	
	private Double base;
	@Transient
	private String mtbase;
	
	private Double taux;
	
	private Double gainPatron;
	@Transient
	private String mtgainPatron;
	
	private Double retenuePatron;
	@Transient
	private String mtretenuePatron;
	
	private Double tauxPatron;
	
	public ImprimBulletinPaie() {
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public Double getGain() {
		return gain;
	}

	public void setGain(Double gain) {
		this.gain = gain;
	}

	public Double getRetenue() {
		return retenue;
	}

	public void setRetenue(Double retenue) {
		this.retenue = retenue;
	}

	public Double getBase() {
		return base;
	}

	public void setBase(Double base) {
		this.base = base;
	}

	public Double getTaux() {
		return taux;
	}

	public void setTaux(Double taux) {
		this.taux = taux;
	}

	public Double getGainPatron() {
		return gainPatron;
	}

	public void setGainPatron(Double gainPatron) {
		this.gainPatron = gainPatron;
	}

	public Double getRetenuePatron() {
		return retenuePatron;
	}

	public void setRetenuePatron(Double retenuePatron) {
		this.retenuePatron = retenuePatron;
	}

	public Double getTauxPatron() {
		return tauxPatron;
	}

	public void setTauxPatron(Double tauxPatron) {
		this.tauxPatron = tauxPatron;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMtgain() {
		
		return mtgain= Utils.formattingAmount(gain);
	}

	public void setMtgain(String mtgain) {
		this.mtgain = mtgain;
	}

	public String getMtretenue() {
		
		return mtretenue= Utils.formattingAmount(retenue);
	}

	public void setMtretenue(String mtretenue) {
		this.mtretenue = mtretenue;
	}

	public String getMtbase() {
		
		return mtbase= Utils.formattingAmount(base);
	}

	public void setMtbase(String mtbase) {
		this.mtbase = mtbase;
	}

	public String getMtgainPatron() {
		
		return mtgainPatron= Utils.formattingAmount(gainPatron);
		
	}

	public void setMtgainPatron(String mtgainPatron) {
		this.mtgainPatron = mtgainPatron;
	}

	public String getMtretenuePatron() {
		
		return mtretenuePatron= Utils.formattingAmount(retenuePatron);
	}

	public void setMtretenuePatron(String mtretenuePatron) {
		this.mtretenuePatron = mtretenuePatron;
	}


	public ImprimBulletinPaie(String libelle, Double base, Double gain, Double taux) {
		this.libelle = libelle;
		this.base = base;
		this.gain = gain;
		this.taux = taux;
	}


}