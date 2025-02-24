/**
 * 
 */
package com.nectux.mizan.hyban.paie.dto;

import java.util.Date;

import javax.persistence.Transient;

import com.nectux.mizan.hyban.utils.Utils;

/**
 * @author Lag
 *
 */
public class DisaDTO {

	private String nom;
	private String prenoms;
	private String numcnps;
	private Date dateNaiss;
	private Date dateEmb;
	private Date dateDepart;
	private String typeSalarie;
	private String ordre;
	private String nomcomplet;
	private String nerieninscrire;
	private String emploiQlte;
	private String codeEmp;
	private String regimG;
	private String sexe;
	private String nationalite;	
	private String local;
	private String situation;
	private String nbrenfant;
	private String nbrpart;
	private String nbrJourdePaiement;
	private String mtbrutTotal;
	private String mtbrutavantage;	
	private String mtbrutavantagereel;
	private String mtcumulbrutpers;
	private String mtits;
	private String mtcn;
	private String mtigr;
	private String mtcnrs;
	private String mtfraisEmp;
	private String designation;
	private Double anciennete;
	private Double nbJourMoisTravail;
	
	private String periodePaie;
	
	private String contratPersonnel;
	
	@Transient
	private String mtnbJourMoisTravail;
	
	private Double salaireBrutAnnuelNonPlafon;
	
	@Transient
	private String mtsalaireBrutAnnuelNonPlafon;
	
	private Double nbMoisTravailAvecConge;
	
	@Transient
	private String mtnbMoisTravailAvecConge;
	
	private Double salaireAnnuelSoumisAuPfAt;
	
	@Transient
	private String mtsalaireAnnuelSoumisAuPfAt;
	
	private Double salaireAnnuelSoumisCnps;
	
	@Transient
	private String mtsalaireAnnuelSoumisCnps;
	
	private Double cotisationEntreprisePfAtCnps;
	
	@Transient
	private String mtcotisationEntreprisePfAtCnps;
	
	
	
	
	private String observation;


	public DisaDTO() {

	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public String getPrenoms() {
		return prenoms;
	}


	public void setPrenoms(String prenoms) {
		this.prenoms = prenoms;
	}


	public String getNumcnps() {
		return numcnps;
	}


	public void setNumcnps(String numcnps) {
		this.numcnps = numcnps;
	}


	public Date getDateNaiss() {
		return dateNaiss;
	}


	public void setDateNaiss(Date dateNaiss) {
		this.dateNaiss = dateNaiss;
	}


	public Date getDateEmb() {
		return dateEmb;
	}


	public void setDateEmb(Date dateEmb) {
		this.dateEmb = dateEmb;
	}


	public Date getDateDepart() {
		return dateDepart;
	}


	public void setDateDepart(Date dateDepart) {
		this.dateDepart = dateDepart;
	}


	public String getTypeSalarie() {
		return typeSalarie;
	}


	public void setTypeSalarie(String typeSalarie) {
		this.typeSalarie = typeSalarie;
	}




	public String getObservation() {
		return observation;
	}


	public void setObservation(String observation) {
		this.observation = observation;
	}


	public String getOrdre() {
		return ordre;
	}


	public void setOrdre(String ordre) {
		this.ordre = ordre;
	}


	public String getNomcomplet() {
		return nomcomplet;
	}


	public void setNomcomplet(String nomcomplet) {
		this.nomcomplet = nomcomplet;
	}


	public String getNerieninscrire() {
		return nerieninscrire;
	}


	public void setNerieninscrire(String nerieninscrire) {
		this.nerieninscrire = nerieninscrire;
	}


	public String getEmploiQlte() {
		return emploiQlte;
	}


	public void setEmploiQlte(String emploiQlte) {
		this.emploiQlte = emploiQlte;
	}


	public String getCodeEmp() {
		return codeEmp;
	}


	public void setCodeEmp(String codeEmp) {
		this.codeEmp = codeEmp;
	}


	public String getRegimG() {
		return regimG;
	}


	public void setRegimG(String regimG) {
		this.regimG = regimG;
	}


	public String getSexe() {
		return sexe;
	}


	public void setSexe(String sexe) {
		this.sexe = sexe;
	}


	public String getNationalite() {
		return nationalite;
	}


	public void setNationalite(String nationalite) {
		this.nationalite = nationalite;
	}


	public String getLocal() {
		return local;
	}


	public void setLocal(String local) {
		this.local = local;
	}


	public String getSituation() {
		return situation;
	}


	public void setSituation(String situation) {
		this.situation = situation;
	}


	public String getNbrenfant() {
		return nbrenfant;
	}


	public void setNbrenfant(String nbrenfant) {
		this.nbrenfant = nbrenfant;
	}


	public String getNbrpart() {
		return nbrpart;
	}


	public void setNbrpart(String nbrpart) {
		this.nbrpart = nbrpart;
	}


	public String getNbrJourdePaiement() {
		return nbrJourdePaiement;
	}


	public void setNbrJourdePaiement(String nbrJourdePaiement) {
		this.nbrJourdePaiement = nbrJourdePaiement;
	}


	public String getMtbrutTotal() {
		return mtbrutTotal;
	}


	public void setMtbrutTotal(String mtbrutTotal) {
		this.mtbrutTotal = mtbrutTotal;
	}


	public String getMtbrutavantage() {
		return mtbrutavantage;
	}


	public void setMtbrutavantage(String mtbrutavantage) {
		this.mtbrutavantage = mtbrutavantage;
	}


	public String getMtbrutavantagereel() {
		return mtbrutavantagereel;
	}


	public void setMtbrutavantagereel(String mtbrutavantagereel) {
		this.mtbrutavantagereel = mtbrutavantagereel;
	}


	public String getMtcumulbrutpers() {
		return mtcumulbrutpers;
	}


	public void setMtcumulbrutpers(String mtcumulbrutpers) {
		this.mtcumulbrutpers = mtcumulbrutpers;
	}


	public String getMtits() {
		return mtits;
	}


	public void setMtits(String mtits) {
		this.mtits = mtits;
	}


	public String getMtcn() {
		return mtcn;
	}


	public void setMtcn(String mtcn) {
		this.mtcn = mtcn;
	}


	public String getMtigr() {
		return mtigr;
	}


	public void setMtigr(String mtigr) {
		this.mtigr = mtigr;
	}


	public String getMtcnrs() {
		return mtcnrs;
	}


	public void setMtcnrs(String mtcnrs) {
		this.mtcnrs = mtcnrs;
	}


	public String getMtfraisEmp() {
		return mtfraisEmp;
	}


	public void setMtfraisEmp(String mtfraisEmp) {
		this.mtfraisEmp = mtfraisEmp;
	}


	public String getDesignation() {
		return designation;
	}


	public void setDesignation(String designation) {
		this.designation = designation;
	}


	public String getPeriodePaie() {
		return periodePaie;
	}


	public void setPeriodePaie(String periodePaie) {
		this.periodePaie = periodePaie;
	}


	@Override
	public String toString() {
		return "DisaDTO [nom=" + nom + ", prenoms=" + prenoms + ", numcnps="
				+ numcnps + ", dateNaiss=" + dateNaiss + ", dateEmb=" + dateEmb
				+ ", dateDepart=" + dateDepart + ", typeSalarie=" + typeSalarie
				+ ", ordre=" + ordre + ", nomcomplet=" + nomcomplet
				+ ", nerieninscrire=" + nerieninscrire + ", emploiQlte="
				+ emploiQlte + ", codeEmp=" + codeEmp + ", regimG=" + regimG
				+ ", sexe=" + sexe + ", nationalite=" + nationalite
				+ ", local=" + local + ", situation=" + situation
				+ ", nbrenfant=" + nbrenfant + ", nbrpart=" + nbrpart
				+ ", nbrJourdePaiement=" + nbrJourdePaiement + ", mtbrutTotal="
				+ mtbrutTotal + ", mtbrutavantage=" + mtbrutavantage
				+ ", mtbrutavantagereel=" + mtbrutavantagereel
				+ ", mtcumulbrutpers=" + mtcumulbrutpers + ", mtits=" + mtits
				+ ", mtcn=" + mtcn + ", mtigr=" + mtigr + ", mtcnrs=" + mtcnrs
				+ ", mtfraisEmp=" + mtfraisEmp + ", designation=" + designation
				+ ", anciennete=" + anciennete + ", nbJourMoisTravail="
				+ nbJourMoisTravail + ", periodePaie=" + periodePaie
				+ ", mtnbJourMoisTravail=" + mtnbJourMoisTravail
				+ ", salaireBrutAnnuelNonPlafon=" + salaireBrutAnnuelNonPlafon
				+ ", mtsalaireBrutAnnuelNonPlafon="
				+ mtsalaireBrutAnnuelNonPlafon + ", nbMoisTravailAvecConge="
				+ nbMoisTravailAvecConge + ", mtnbMoisTravailAvecConge="
				+ mtnbMoisTravailAvecConge + ", salaireAnnuelSoumisAuPfAt="
				+ salaireAnnuelSoumisAuPfAt + ", mtsalaireAnnuelSoumisAuPfAt="
				+ mtsalaireAnnuelSoumisAuPfAt + ", salaireAnnuelSoumisCnps="
				+ salaireAnnuelSoumisCnps + ", mtsalaireAnnuelSoumisCnps="
				+ mtsalaireAnnuelSoumisCnps + ", cotisationEntreprisePfAtCnps="
				+ cotisationEntreprisePfAtCnps
				+ ", mtcotisationEntreprisePfAtCnps="
				+ mtcotisationEntreprisePfAtCnps + ", observation="
				+ observation + "]";
	}


	public Double getAnciennete() {
		return anciennete;
	}


	public void setAnciennete(Double anciennete) {
		this.anciennete = anciennete;
	}


	public Double getNbJourMoisTravail() {
		return nbJourMoisTravail;
	}


	public void setNbJourMoisTravail(Double nbJourMoisTravail) {
		this.nbJourMoisTravail = nbJourMoisTravail;
	}


	public Double getSalaireBrutAnnuelNonPlafon() {
		return salaireBrutAnnuelNonPlafon;
	}


	public void setSalaireBrutAnnuelNonPlafon(Double salaireBrutAnnuelNonPlafon) {
		this.salaireBrutAnnuelNonPlafon = salaireBrutAnnuelNonPlafon;
	}


	public Double getNbMoisTravailAvecConge() {
		return nbMoisTravailAvecConge;
	}


	public void setNbMoisTravailAvecConge(Double nbMoisTravailAvecConge) {
		this.nbMoisTravailAvecConge = nbMoisTravailAvecConge;
	}


	public Double getSalaireAnnuelSoumisAuPfAt() {
		return salaireAnnuelSoumisAuPfAt;
	}


	public void setSalaireAnnuelSoumisAuPfAt(Double salaireAnnuelSoumisAuPfAt) {
		this.salaireAnnuelSoumisAuPfAt = salaireAnnuelSoumisAuPfAt;
	}


	public Double getSalaireAnnuelSoumisCnps() {
		return salaireAnnuelSoumisCnps;
	}


	public void setSalaireAnnuelSoumisCnps(Double salaireAnnuelSoumisCnps) {
		this.salaireAnnuelSoumisCnps = salaireAnnuelSoumisCnps;
	}


	public Double getCotisationEntreprisePfAtCnps() {
		return cotisationEntreprisePfAtCnps;
	}


	public void setCotisationEntreprisePfAtCnps(Double cotisationEntreprisePfAtCnps) {
		this.cotisationEntreprisePfAtCnps = cotisationEntreprisePfAtCnps;
	}


	public String getMtnbJourMoisTravail() {
		return mtnbJourMoisTravail=Utils.formattingAmount(nbJourMoisTravail);
	}


	public void setMtnbJourMoisTravail(String mtnbJourMoisTravail) {
		this.mtnbJourMoisTravail = mtnbJourMoisTravail;
	}


	public String getMtsalaireBrutAnnuelNonPlafon() {
		return mtsalaireBrutAnnuelNonPlafon=Utils.formattingAmount(salaireBrutAnnuelNonPlafon);
	}


	public void setMtsalaireBrutAnnuelNonPlafon(String mtsalaireBrutAnnuelNonPlafon) {
		this.mtsalaireBrutAnnuelNonPlafon = mtsalaireBrutAnnuelNonPlafon;
	}


	public String getMtnbMoisTravailAvecConge() {
		return mtnbMoisTravailAvecConge=Utils.formattingAmount(nbMoisTravailAvecConge);
	}


	public void setMtnbMoisTravailAvecConge(String mtnbMoisTravailAvecConge) {
		this.mtnbMoisTravailAvecConge = mtnbMoisTravailAvecConge;
	}


	public String getMtsalaireAnnuelSoumisAuPfAt() {
		return mtsalaireAnnuelSoumisAuPfAt=Utils.formattingAmount(salaireAnnuelSoumisAuPfAt);
	}


	public void setMtsalaireAnnuelSoumisAuPfAt(String mtsalaireAnnuelSoumisAuPfAt) {
		this.mtsalaireAnnuelSoumisAuPfAt = mtsalaireAnnuelSoumisAuPfAt;
	}


	public String getMtsalaireAnnuelSoumisCnps() {
		return mtsalaireAnnuelSoumisCnps=Utils.formattingAmount(salaireAnnuelSoumisCnps);
	}


	public void setMtsalaireAnnuelSoumisCnps(String mtsalaireAnnuelSoumisCnps) {
		this.mtsalaireAnnuelSoumisCnps = mtsalaireAnnuelSoumisCnps;
	}


	public String getMtcotisationEntreprisePfAtCnps() {
		return mtcotisationEntreprisePfAtCnps=Utils.formattingAmount(cotisationEntreprisePfAtCnps);
	}


	public void setMtcotisationEntreprisePfAtCnps(
			String mtcotisationEntreprisePfAtCnps) {
		this.mtcotisationEntreprisePfAtCnps = mtcotisationEntreprisePfAtCnps;
	}


	public String getContratPersonnel() {
		return contratPersonnel;
	}


	public void setContratPersonnel(String contratPersonnel) {
		this.contratPersonnel = contratPersonnel;
	}

	
	

}
