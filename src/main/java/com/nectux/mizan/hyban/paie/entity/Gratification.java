package com.nectux.mizan.hyban.paie.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.nectux.mizan.hyban.parametrages.entity.Auditable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.personnel.entity.ContratPersonnel;
import com.nectux.mizan.hyban.utils.Utils;

@Entity
@Component("gratification")
@Scope("prototype")
@Table(name="CGECI_RHPAIE_GRATIFICATION")
@SequenceGenerator(name="CGECI_RHPAIE_GRATIFICATION_SEQUENCE", sequenceName="CGECI_RHPAIE_GRATIFICATION_SEQ", initialValue=1, allocationSize=1)
public class Gratification extends Auditable {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CGECI_RHPAIE_GRATIFICATION_SEQUENCE")
	@Column(unique=true, nullable=false)
	private Long id;
	
	private Double nombrePart;
	
	@Transient
	private String nbrePart;
	
	private Double gratificationBase;
	
	@Transient
	private String montantGratificationBase;
	
	private Double sursalaire;
	
	@Transient
	private String montantSursalaire;
	
	private Double tauxGratification;
	
	@Transient
	private String montantTauxGratification;
	
	private Double indemniteTransport;
	
	@Transient
	private String indemniteTransp;
	
	private Double its;
	
	@Transient
	private String montantIts;
	
	private Double cn;
	
	@Transient
	private String montantCn;
	
	private Double igr;
	
	@Transient
	private String montantIgr;
	
	private Double cnps;
	
	@Transient
	private String montantCnps;
	
	private Double impotSalaire;
	
	@Transient
	private String montantIs;
	
	private Double ta;
	
	@Transient
	private String montantTa;
	
	private Double fpc;
	
	@Transient
	private String montantFpc;
	
	private Double prestationFamiliale;
	
	@Transient
	private String prestationFamil;
	
	private Double accidentTravail;
	
	@Transient
	private String accidentTrav;
	
	private Double retraite;
	
	@Transient
	private String montantRetraite;
	
	private Double indemniteFinCarriere;
	
	@Transient
	private String montantIndemniteFinCarriere;
	
	private Double totalPatronal;
	
	@Transient
	private String montantTotalPatronal;
	
	private Double totalMasseSalariale;
	
	@Transient
	private String montantTotalMasseSalariale;
	
	private Double brutImposable;
	
	@Transient
	private String montantBrutImposable;
	
	private Double netPayer;
	
	@Transient
	private String montantNetPayer;
	
	private Double totalRetenueFiscale;
	
	@Transient
	private String montantTotalRetenueFiscale;
	
	private Double totalRetenue;
	
	@Transient
	private String montantTotalRetenu;
	
	private Double totalBrut;
	
	@Transient
	private String montantTotalBrut;
	
	private Double totalChargeFiscalePatronale;
	
	@Transient
	private String montantcumulIts;
	
	@Transient
	private String montantcumulCn;
	
	private String montantcumulIgr;
	
	@Transient
	private String montantcumulCnpsSal;
	
	@Transient
	private String montantTotalChargeFiscalePatronale;
	
	private Double totalChargeSocialePatronale;
	
	@Transient
	private String montantTotalChargeSocialePatronale;
	
	private Double totalMasseSalarialeMensuelle;
	
	@Transient
	private String montantTotalMasseSalarialeMensuelle;
	
	private Double totalMasseSalarialeAnnuelle;
	
	@Transient
	private String montantTotalMasseSalarialeAnnuelle;
	
	
	@Transient
	private List<ImprimBulletinPaie> listImprimBulletinPaie;
	
	@Transient
	private List<ImprimBulletinPaie> listImprimBulletinPaieEngagement;
	
	@Transient
	private List<ImprimBulletinPaie> listImprimBulletinPaieIndemniteNonImp;
	
	/*private Boolean statut;
	
	@Transient
	private String enSommeil;*/
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private ContratPersonnel contratPersonnel;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private PeriodePaie periodePaie;
	
	
	public Gratification() {
		super();
	}

	

	/*public Gratification(Long id, Double nombrePart, Double gratificationBase, Double sursalaire, Double indemniteTransport, 
			Double its, Double cn, Double igr, Double cnps, Double impotSalaire, Double ta, Double fpc, Double prestationFamiliale,
			Double accidentTravail, Double retraite, Double indemniteFinCarriere, Double netPayer, Double brutImposable, Double totalPatronal, Double totalMasseSalariale, 
			ContratPersonnel contratPersonnel, PeriodePaie periodePaie) {
		super();
		this.id = id;
		this.nombrePart = nombrePart;
		this.gratificationBase = gratificationBase;
		this.sursalaire = sursalaire;
		this.indemniteTransport = indemniteTransport;
		this.its = its;
		this.cn = cn;
		this.igr = igr;
		this.cnps = cnps;
		this.impotSalaire = impotSalaire;
		this.ta = ta;
		this.fpc = fpc;
		this.prestationFamiliale = prestationFamiliale;
		this.accidentTravail = accidentTravail;
		this.retraite = retraite;
		this.indemniteFinCarriere = indemniteFinCarriere;
		this.brutImposable = brutImposable;
		this.netPayer = netPayer;
		this.totalPatronal = totalPatronal;
		this.totalMasseSalariale = totalMasseSalariale;
		this.contratPersonnel = contratPersonnel;
		this.periodePaie = periodePaie;
	}*/


	public Gratification(Double nombrePart, Double gratificationBase, Double indemniteTransport, Double its, Double cn,
			Double igr, Double cnps, Double impotSalaire, Double ta, Double fpc, Double prestationFamiliale,
			Double accidentTravail, Double retraite, Double indemniteFinCarriere, Double totalPatronal,
			/*Double totalMasseSalariale, */Double brutImposable, Double netPayer, Double totalRetenueFiscale,
			Double totalRetenue, Double totalBrut, Double totalChargeFiscalePatronale, Double totalChargeSocialePatronale, Double totalMasseSalarialeMensuelle,
			Double totalMasseSalarialeAnnuelle, ContratPersonnel contratPersonnel, PeriodePaie periodePaie) {
		super();
		this.nombrePart = nombrePart;
		this.gratificationBase = gratificationBase;
		this.indemniteTransport = indemniteTransport;
		this.its = its;
		this.cn = cn;
		this.igr = igr;
		this.cnps = cnps;
		this.impotSalaire = impotSalaire;
		this.ta = ta;
		this.fpc = fpc;
		this.prestationFamiliale = prestationFamiliale;
		this.accidentTravail = accidentTravail;
		this.retraite = retraite;
		this.indemniteFinCarriere = indemniteFinCarriere;
		this.totalPatronal = totalPatronal;
		//this.totalMasseSalariale = totalMasseSalariale;
		this.brutImposable = brutImposable;
		this.netPayer = netPayer;
		this.totalRetenueFiscale = totalRetenueFiscale;
		this.totalRetenue = totalRetenue;
		this.totalBrut = totalBrut;
		this.totalChargeFiscalePatronale = totalChargeFiscalePatronale;
		this.totalChargeSocialePatronale = totalChargeSocialePatronale;
		this.totalMasseSalarialeMensuelle = totalMasseSalarialeMensuelle;
		this.totalMasseSalarialeAnnuelle = totalMasseSalarialeAnnuelle;
		this.contratPersonnel = contratPersonnel;
		this.periodePaie = periodePaie;
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Double getNombrePart() {
		return nombrePart;
	}


	public void setNombrePart(Double nombrePart) {
		this.nombrePart = nombrePart;
	}


	public String getNbrePart() {
		nbrePart = Utils.formattingAmount(nombrePart);
		return nbrePart;
	}


	public void setNbrePart(String nbrePart) {
		this.nbrePart = nbrePart;
	}


	public Double getGratificationBase() {
		return gratificationBase;
	}


	public void setGratificationBase(Double gratificationBase) {
		this.gratificationBase = gratificationBase;
	}


	public String getMontantGratificationBase() {
		montantGratificationBase = Utils.formattingAmount(gratificationBase);
		return montantGratificationBase;
	}


	public void setMontantGratificationBase(String montantGratificationBase) {
		this.montantGratificationBase = montantGratificationBase;
	}


	public Double getTauxGratification() {
		return tauxGratification;
	}


	public void setTauxGratification(Double tauxGratification) {
		this.tauxGratification = tauxGratification;
	}


	public String getMontantTauxGratification() {
		montantTauxGratification = Utils.formattingAmount(tauxGratification);
		return montantTauxGratification;
	}


	public void setMontantTauxGratification(String montantTauxGratification) {
		this.montantTauxGratification = montantTauxGratification;
	}


	public Double getIndemniteTransport() {
		return indemniteTransport;
	}


	public void setIndemniteTransport(Double indemniteTransport) {
		this.indemniteTransport = indemniteTransport;
	}


	public String getIndemniteTransp() {
		indemniteTransp = Utils.formattingAmount(indemniteTransport);
		return indemniteTransp;
	}


	public void setIndemniteTransp(String indemniteTransp) {
		this.indemniteTransp = indemniteTransp;
	}


	public Double getIts() {
		return its;
	}


	public void setIts(Double its) {
		this.its = its;
	}


	public String getMontantIts() {
		montantIts = Utils.formattingAmount(its);
		return montantIts;
	}


	public void setMontantIts(String montantIts) {
		this.montantIts = montantIts;
	}


	public Double getCn() {
		return cn;
	}


	public void setCn(Double cn) {
		this.cn = cn;
	}


	public String getMontantCn() {
		montantCn = Utils.formattingAmount(cn);
		return montantCn;
	}


	public void setMontantCn(String montantCn) {
		this.montantCn = montantCn;
	}


	public Double getIgr() {
		return igr;
	}


	public void setIgr(Double igr) {
		this.igr = igr;
	}


	public String getMontantIgr() {
		montantIgr = Utils.formattingAmount(igr);
		return montantIgr;
	}


	public void setMontantIgr(String montantIgr) {
		this.montantIgr = montantIgr;
	}


	public Double getCnps() {
		return cnps;
	}


	public void setCnps(Double cnps) {
		this.cnps = cnps;
	}


	public String getMontantCnps() {
		montantCnps = Utils.formattingAmount(cnps);
		return montantCnps;
	}


	public void setMontantCnps(String montantCnps) {
		this.montantCnps = montantCnps;
	}

	public Double getImpotSalaire() {
		return impotSalaire;
	}


	public void setImpotSalaire(Double impotSalaire) {
		this.impotSalaire = impotSalaire;
	}


	public String getMontantIs() {
		montantIs = Utils.formattingAmount(impotSalaire);
		return montantIs;
	}


	public void setMontantIs(String montantIs) {
		this.montantIs = montantIs;
	}


	public Double getTa() {
		return ta;
	}


	public void setTa(Double ta) {
		this.ta = ta;
	}


	public String getMontantTa() {
		montantTa = Utils.formattingAmount(ta);
		return montantTa;
	}


	public void setMontantTa(String montantTa) {
		this.montantTa = montantTa;
	}


	public Double getFpc() {
		return fpc;
	}


	public void setFpc(Double fpc) {
		this.fpc = fpc;
	}


	public String getMontantFpc() {
		montantFpc = Utils.formattingAmount(fpc);
		return montantFpc;
	}


	public void setMontantFpc(String montantFpc) {
		this.montantFpc = montantFpc;
	}


	public Double getPrestationFamiliale() {
		return prestationFamiliale;
	}


	public void setPrestationFamiliale(Double prestationFamiliale) {
		this.prestationFamiliale = prestationFamiliale;
	}


	public String getPrestationFamil() {
		prestationFamil = Utils.formattingAmount(prestationFamiliale);
		return prestationFamil;
	}


	public void setPrestationFamil(String prestationFamil) {
		this.prestationFamil = prestationFamil;
	}


	public Double getAccidentTravail() {
		return accidentTravail;
	}


	public void setAccidentTravail(Double accidentTravail) {
		this.accidentTravail = accidentTravail;
	}


	public String getAccidentTrav() {
		accidentTrav = Utils.formattingAmount(accidentTravail);
		return accidentTrav;
	}


	public void setAccidentTrav(String accidentTrav) {
		this.accidentTrav = accidentTrav;
	}


	public Double getRetraite() {
		return retraite;
	}


	public void setRetraite(Double retraite) {
		this.retraite = retraite;
	}


	public String getMontantRetraite() {
		montantRetraite = Utils.formattingAmount(retraite);
		return montantRetraite;
	}


	public void setMontantRetraite(String montantRetraite) {
		this.montantRetraite = montantRetraite;
	}


	public Double getIndemniteFinCarriere() {
		return indemniteFinCarriere;
	}


	public void setIndemniteFinCarriere(Double indemniteFinCarriere) {
		this.indemniteFinCarriere = indemniteFinCarriere;
	}


	public String getMontantIndemniteFinCarriere() {
		montantIndemniteFinCarriere = Utils.formattingAmount(indemniteFinCarriere);
		return montantIndemniteFinCarriere;
	}


	public void setMontantIndemniteFinCarriere(String montantIndemniteFinCarriere) {
		this.montantIndemniteFinCarriere = montantIndemniteFinCarriere;
	}


	public ContratPersonnel getContratPersonnel() {
		return contratPersonnel;
	}


	public void setContratPersonnel(ContratPersonnel contratPersonnel) {
		this.contratPersonnel = contratPersonnel;
	}


	public PeriodePaie getPeriodePaie() {
		return periodePaie;
	}


	public void setPeriodePaie(PeriodePaie periodePaie) {
		this.periodePaie = periodePaie;
	}


	public Double getSursalaire() {
		return sursalaire;
	}

	public void setSursalaire(Double sursalaire) {
		this.sursalaire = sursalaire;
	}

	public String getMontantSursalaire() {
		montantTa = Utils.formattingAmount(sursalaire);
		return montantTa;
	}

	public void setMontantSursalaire(String montantSursalaire) {
		this.montantSursalaire = montantSursalaire;
	}

	public Double getTotalPatronal() {
		return totalPatronal;
	}

	public void setTotalPatronal(Double totalPatronal) {
		this.totalPatronal = totalPatronal;
	}

	public String getMontantTotalPatronal() {
		montantTotalPatronal = Utils.formattingAmount(totalPatronal);
		return montantTotalPatronal;
	}

	public void setMontantTotalPatronal(String montantTotalPatronal) {
		this.montantTotalPatronal = montantTotalPatronal;
	}

	public Double getTotalMasseSalariale() {
		return totalMasseSalariale;
	}

	public void setTotalMasseSalariale(Double totalMassaeSalariale) {
		this.totalMasseSalariale = totalMassaeSalariale;
	}

	public String getMontantTotalMasseSalariale() {
		montantTotalMasseSalariale = Utils.formattingAmount(totalMasseSalariale);
		return montantTotalMasseSalariale;
	}

	public void setMontantTotalMasseSalariale(String montantTotalMassaeSalariale) {
		this.montantTotalMasseSalariale = montantTotalMassaeSalariale;
	}

	public Double getBrutImposable() {
		return brutImposable;
	}

	public void setBrutImposable(Double brutImposable) {
		this.brutImposable = brutImposable;
	}

	public String getMontantBrutImposable() {
		montantBrutImposable = Utils.formattingAmount(brutImposable);
		return montantBrutImposable;
	}

	public void setMontantBrutImposable(String montantBrutImposable) {
		this.montantBrutImposable = montantBrutImposable;
	}

	public Double getNetPayer() {
		return netPayer;
	}

	public void setNetPayer(Double netPayer) {
		this.netPayer = netPayer;
	}

	public String getMontantNetPayer() {
		montantNetPayer = Utils.formattingAmount(netPayer);
		return montantNetPayer;
	}

	public void setMontantNetPayer(String montantNetPayer) {
		this.montantNetPayer = montantNetPayer;
	}

	public Double getTotalRetenueFiscale() {
		return totalRetenueFiscale;
	}

	public void setTotalRetenueFiscale(Double totalRetenueFiscale) {
		this.totalRetenueFiscale = totalRetenueFiscale;
	}

	public String getMontantTotalRetenueFiscale() {
		montantTotalRetenueFiscale = Utils.formattingAmount(totalRetenueFiscale);
		return montantTotalRetenueFiscale;
	}

	public void setMontantTotalRetenueFiscale(String montantTotalRetenueFiscale) {
		this.montantTotalRetenueFiscale = montantTotalRetenueFiscale;
	}

	public Double getTotalRetenue() {
		return totalRetenue;
	}

	public void setTotalRetenue(Double totalRetenue) {
		this.totalRetenue = totalRetenue;
	}

	public String getMontantTotalRetenu() {
		montantTotalRetenu = Utils.formattingAmount(totalRetenue);
		return montantTotalRetenu;
	}

	public void setMontantTotalRetenu(String montantTotalRetenu) {
		this.montantTotalRetenu = montantTotalRetenu;
	}

	public Double getTotalBrut() {
		return totalBrut;
	}

	public void setTotalBrut(Double totalBrut) {
		this.totalBrut = totalBrut;
	}

	public String getMontantTotalBrut() {
		montantTotalBrut = Utils.formattingAmount(totalBrut);
		return montantTotalBrut;
	}

	public void setMontantTotalBrut(String montantTotalBrut) {
		this.montantTotalBrut = montantTotalBrut;
	}

	public Double getTotalChargeFiscalePatronale() {
		return totalChargeFiscalePatronale;
	}

	public void setTotalChargeFiscalePatronale(Double total) {
		this.totalChargeFiscalePatronale = total;
	}

	public String getMontantTotalChargeFiscalePatronale() {
		montantTotalChargeFiscalePatronale = Utils.formattingAmount(totalChargeFiscalePatronale);
		return montantTotalChargeFiscalePatronale;
	}

	public void setMontantTotalChargeFiscalePatronale(String montantTotal) {
		this.montantTotalChargeFiscalePatronale = montantTotal;
	}

	public Double getTotalChargeSocialePatronale() {
		return totalChargeSocialePatronale;
	}

	public void setTotalChargeSocialePatronale(Double totalChargeSocialePatronale) {
		this.totalChargeSocialePatronale = totalChargeSocialePatronale;
	}

	public String getMontantTotalChargeSocialePatronale() {
		montantTotalChargeSocialePatronale = Utils.formattingAmount(totalChargeSocialePatronale);
		return montantTotalChargeSocialePatronale;
	}

	public void setMontantTotalChargeSocialePatronale(String montantTotalChargeSocialePatronale) {
		this.montantTotalChargeSocialePatronale = montantTotalChargeSocialePatronale;
	}

	public Double getTotalMasseSalarialeMensuelle() {
		return totalMasseSalarialeMensuelle;
	}

	public void setTotalMasseSalarialeMensuelle(Double totalMasseSalarialeMensuelle) {
		this.totalMasseSalarialeMensuelle = totalMasseSalarialeMensuelle;
	}

	public String getMontantTotalMasseSalarialeMensuelle() {
		montantTotalMasseSalarialeMensuelle = Utils.formattingAmount(totalMasseSalarialeMensuelle);
		return montantTotalMasseSalarialeMensuelle;
	}

	public void setMontantTotalMasseSalarialeMensuelle(String montantTotalMasseSalarialeMensuelle) {
		this.montantTotalMasseSalarialeMensuelle = montantTotalMasseSalarialeMensuelle;
	}

	public Double getTotalMasseSalarialeAnnuelle() {
		return totalMasseSalarialeAnnuelle;
	}

	public void setTotalMasseSalarialeAnnuelle(Double totalMasseSalarialeAnnuelle) {
		this.totalMasseSalarialeAnnuelle = totalMasseSalarialeAnnuelle;
	}

	public String getMontantTotalMasseSalarialeAnnuelle() {
		montantTotalMasseSalarialeAnnuelle = Utils.formattingAmount(totalMasseSalarialeAnnuelle);
		return montantTotalMasseSalarialeAnnuelle;
	}

	public void setMontantTotalMasseSalarialeAnnuelle(String montantTotalMasseSalarialeAnnuelle) {
		this.montantTotalMasseSalarialeAnnuelle = montantTotalMasseSalarialeAnnuelle;
	}

	/*public Boolean getStatut() {
		return statut;
	}

	public void setStatut(Boolean statut) {
		this.statut = statut;
	}

	public String getEnSommeil() {
		if(statut)
			return "NON";
		return "OUI";
	}

	public void setEnSommeil(String enSommeil) {
		this.enSommeil = enSommeil;
	}*/

	@Override
	public String toString() {
		return "Gratification [id=" + id + ", nombrePart=" + nombrePart + ", nbrePart=" + nbrePart
				+ ", gratificationBase=" + gratificationBase + ", montantGratificationBase=" + montantGratificationBase
				+ ", tauxGratification=" + tauxGratification + ", montantTauxGratification=" + montantTauxGratification
				+ ", indemniteTransport=" + indemniteTransport + ", indemniteTransp=" + indemniteTransp + ", its=" + its
				+ ", montantIts=" + montantIts + ", cn=" + cn + ", montantCn=" + montantCn + ", igr=" + igr
				+ ", montantIgr=" + montantIgr + ", cnps=" + cnps + ", montantCnps=" + montantCnps + ", impotSalaire="
				+ impotSalaire + ", montantIs=" + montantIs + ", ta=" + ta + ", montantTa=" + montantTa + ", fpc=" + fpc
				+ ", montantFpc=" + montantFpc + ", prestationFamiliale=" + prestationFamiliale + ", prestationFamil="
				+ prestationFamil + ", accidentTravail=" + accidentTravail + ", accidentTrav=" + accidentTrav
				+ ", retraite=" + retraite + ", montantRetraite=" + montantRetraite + ", indemniteFinCarriere="
				+ indemniteFinCarriere + ", montantIndemniteFinCarriere=" + montantIndemniteFinCarriere
				+ ", contratPersonnel=" + contratPersonnel + ", periodePaie=" + periodePaie + "]";
	}



	public List<ImprimBulletinPaie> getListImprimBulletinPaie() {
		return listImprimBulletinPaie;
	}



	public void setListImprimBulletinPaie(
			List<ImprimBulletinPaie> listImprimBulletinPaie) {
		this.listImprimBulletinPaie = listImprimBulletinPaie;
	}



	public List<ImprimBulletinPaie> getListImprimBulletinPaieEngagement() {
		return listImprimBulletinPaieEngagement;
	}



	public void setListImprimBulletinPaieEngagement(
			List<ImprimBulletinPaie> listImprimBulletinPaieEngagement) {
		this.listImprimBulletinPaieEngagement = listImprimBulletinPaieEngagement;
	}



	public List<ImprimBulletinPaie> getListImprimBulletinPaieIndemniteNonImp() {
		return listImprimBulletinPaieIndemniteNonImp;
	}



	public void setListImprimBulletinPaieIndemniteNonImp(
			List<ImprimBulletinPaie> listImprimBulletinPaieIndemniteNonImp) {
		this.listImprimBulletinPaieIndemniteNonImp = listImprimBulletinPaieIndemniteNonImp;
	}



	public String getMontantcumulIts() {
		return montantcumulIts;
	}



	public void setMontantcumulIts(String montantcumulIts) {
		this.montantcumulIts = montantcumulIts;
	}



	public String getMontantcumulCn() {
		return montantcumulCn;
	}



	public void setMontantcumulCn(String montantcumulCn) {
		this.montantcumulCn = montantcumulCn;
	}



	public String getMontantcumulIgr() {
		return montantcumulIgr;
	}



	public void setMontantcumulIgr(String montantcumulIgr) {
		this.montantcumulIgr = montantcumulIgr;
	}



	public String getMontantcumulCnpsSal() {
		return montantcumulCnpsSal;
	}



	public void setMontantcumulCnpsSal(String montantcumulCnpsSal) {
		this.montantcumulCnpsSal = montantcumulCnpsSal;
	}
	
}
