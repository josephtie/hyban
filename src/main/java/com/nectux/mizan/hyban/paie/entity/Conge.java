package com.nectux.mizan.hyban.paie.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.nectux.mizan.hyban.parametrages.entity.Auditable;
import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.personnel.entity.ContratPersonnel;
import com.nectux.mizan.hyban.utils.CustomDateDeserializer;
import com.nectux.mizan.hyban.utils.Utils;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

@Entity
@Component("conge")
@Scope("prototype")
@Table(name="CGECI_RHPAIE_CONGE")
@SequenceGenerator(name="CGECI_RHPAIE_CONGE_SEQUENCE", sequenceName="CGECI_RHPAIE_CONGE_SEQ", initialValue=1, allocationSize=1)
public class Conge extends Auditable {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CGECI_RHPAIE_CONGE_SEQUENCE")
	@Column(unique=true, nullable=false)
	private Long id;
	
	@JsonSerialize(using = CustomDateDeserializer.class)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date dateDepart;
	
	@Transient
	private String dDepart;
	
	@JsonSerialize(using = CustomDateDeserializer.class)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date dateRetour;
	
	@Transient
	private String dRetour;
	
	private Double nombrePart;
	
	@Transient
	private String nbrePart;
	
	private int tempsPresenceEffectif;
	
	private Double salaireMoyenMensuel; // 12 dernier mois
	
	 private Integer moisOfpresence;
	 
	private Integer tempsOfpresence;
	
	@Transient
	private String montantSalaireMoyenMensuel;
	
	private Double indemniteRepresentationMoyenMensuelle;
	
	@Transient
	private String indemniteRepresentMoyenMensuel;
	
	private int nombreJourCongeDu;
	
	private int nombreJourCongePris;
	
	private Double provisionConge;
	
	@Transient
	private String montantProvisionConge;
	
	@Transient
	private String jouradditionel;
	
	private Double baseImposableAllocationConge;
	
	@Transient
	private String montantBaseImposableAllocationConge;
	
	private Double impotSalaire;
	
	@Transient
	private String montantIs;
	
	private Double ta;
	
	@Transient
	private String montantTa;
	
	private Double fpc;
	
	@Transient
	private String montantFpc;
	
	private Double totalProvisionImpot;
	
	@Transient
	private String montantTotalProvisionImpot;
	
	private Double prestationFamiliale;
	
	@Transient
	private String prestationFamil;
	
	private Double accidentTravail;
	
	@Transient
	private String accidentTrav;
	
	private Double retraite;
	
	@Transient
	private String montantRetraite;
	
	private Double totalProvisionChargeSocial;
	
	@Transient
	private String montantTotalProvisionChargeSocial;
	
	private Double totalChargePatronale;
	
	@Transient
	private String montantTotalChargePatronale;
	
	private Double its;
	
	@Transient
	private String montantITS;
	
	private Double cn;
	
	@Transient
	private String montantCN;
	
	private Double igr;
	
	@Transient
	private String montantIGR;
	
	@Transient
	private String tpsdepresence;
	
	@Transient
	private String nbcongedu;
	
	private Double cnps;
	
	@Transient
	private String montantCNPS;
	
	private Double totalRetenue;
	
	@Transient
	private String montantTotalRetenue;
	
	private Double allocationCongeNet;
	
	@Transient
	private String montantAllocationCongeNet;
	
	private Double allocationCongeNetPris;
	
	@Transient
	private String montantAllocationCongeNetPris;
	
	@Transient
	private List<ImprimBulletinPaie> listImprimBulletinPaie;
	
	@Transient
	private List<ImprimBulletinPaie> listImprimBulletinPaieEngagement;
	
	@Transient
	private List<ImprimBulletinPaie> listImprimBulletinPaieIndemniteNonImp;
	
	@Transient
	private String montantcumulIts;
	
	@Transient
	private String montantNetPayer;
	
	@Transient
	private String montantcumulCn;
	
	private String montantcumulIgr;
	
	@Transient
	private String montantcumulCnpsSal;
	@Transient
	private String alocnetpayer;
	
	
	@Transient
	private int reeljrdu;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private ContratPersonnel contratPersonnel;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private PeriodePaie periodePaie;
	
	public Conge() {
		super();
	}

	public Conge(Date dateDepart, Date dateRetour, Double nombrePart, int tempsPresenceEffectif,
			Double salaireMoyenMensuel, Double indemniteRepresentationMoyenMensuelle, int nombreJourCongeDu,
			Double provisionConge, Double baseImposableAllocationConge, Double impotSalaire, Double ta, Double fpc,
			Double totalProvisionImpot, Double prestationFamiliale, Double accidentTravail, Double retraite,
			Double totalProvisionChargeSocial, Double totalChargePatronale, 
			Double its, Double cn, Double igr, Double cnps,Double totalRetenue, Double allocationCongeNet, 
			ContratPersonnel contratPersonnel, PeriodePaie periodePaie) {
		super();
		
		this.nombreJourCongePris = 0;
		this.allocationCongeNetPris = 0.0;
		
		this.dateDepart = dateDepart;
		this.dateRetour = dateRetour;
		this.nombrePart = nombrePart;
		this.tempsPresenceEffectif = tempsPresenceEffectif;
		this.salaireMoyenMensuel = salaireMoyenMensuel;
		this.indemniteRepresentationMoyenMensuelle = indemniteRepresentationMoyenMensuelle;
		this.nombreJourCongeDu = nombreJourCongeDu;
		this.provisionConge = provisionConge;
		this.baseImposableAllocationConge = baseImposableAllocationConge;
		this.impotSalaire = impotSalaire;
		this.ta = ta;
		this.fpc = fpc;
		this.totalProvisionImpot = totalProvisionImpot;
		this.prestationFamiliale = prestationFamiliale;
		this.accidentTravail = accidentTravail;
		this.retraite = retraite;
		this.totalProvisionChargeSocial = totalProvisionChargeSocial;
		this.totalChargePatronale = totalChargePatronale;
		this.contratPersonnel = contratPersonnel;
		this.periodePaie = periodePaie;
		
		this.its = its;
		this.cn = cn;
		this.igr = igr;
		this.cnps = cnps;
		this.totalRetenue = totalRetenue;
		this.allocationCongeNet = allocationCongeNet;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDateDepart() {
		return dateDepart;
	}

	public void setDateDepart(Date dateDepart) {
		this.dateDepart = dateDepart;
	}

	public String getdDepart() {
		dDepart = Utils.dateToString(dateDepart, "dd/MM/yyyy");
		return dDepart;
	}

	public void setdDepart(String dDepart) {
		this.dDepart = dDepart;
	}

	public Date getDateRetour() {
		return dateRetour;
	}

	public void setDateRetour(Date dateRetour) {
		this.dateRetour = dateRetour;
	}

	public String getdRetour() {
		dRetour = Utils.dateToString(dateRetour, "dd/MM/yyyy");
		return dRetour;
	}

	public void setdRetour(String dRetour) {
		this.dRetour = dRetour;
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

	public Double getSalaireMoyenMensuel() {
		return salaireMoyenMensuel;
	}

	public void setSalaireMoyenMensuel(Double salaireMoyenMensuel) {
		this.salaireMoyenMensuel = salaireMoyenMensuel;
	}

	public String getMontantSalaireMoyenMensuel() {
		montantSalaireMoyenMensuel = Utils.formattingAmount(salaireMoyenMensuel);
		return montantSalaireMoyenMensuel;
	}

	public void setMontantSalaireMoyenMensuel(String montantSalaireMoyenMensuel) {
		this.montantSalaireMoyenMensuel = montantSalaireMoyenMensuel;
	}

	public Double getIndemniteRepresentationMoyenMensuelle() {
		return indemniteRepresentationMoyenMensuelle;
	}

	public void setIndemniteRepresentationMoyenMensuelle(Double indemniteRepresentationMoyenMensuelle) {
		this.indemniteRepresentationMoyenMensuelle = indemniteRepresentationMoyenMensuelle;
	}

	public String getIndemniteRepresentMoyenMensuel() {
		indemniteRepresentMoyenMensuel = Utils.formattingAmount(indemniteRepresentationMoyenMensuelle);
		return indemniteRepresentMoyenMensuel;
	}

	public void setIndemniteRepresentMoyenMensuel(String indemniteRepresentMoyenMensuel) {
		this.indemniteRepresentMoyenMensuel = indemniteRepresentMoyenMensuel;
	}

	public int getNombreJourCongeDu() {
		return nombreJourCongeDu;
	}

	public void setNombreJourCongeDu(int nombreJourCongeDu) {
		this.nombreJourCongeDu = nombreJourCongeDu;
	}

	public int getNombreJourCongePris() {
		return nombreJourCongePris;
	}

	public void setNombreJourCongePris(int nombreJourCongePris) {
		this.nombreJourCongePris = nombreJourCongePris;
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

	public String getTpsdepresence() {
		return tpsdepresence;
	}

	public void setTpsdepresence(String tpsdepresence) {
		this.tpsdepresence = tpsdepresence;
	}

	public String getNbcongedu() {
		return nbcongedu;
	}

	public void setNbcongedu(String nbcongedu) {
		this.nbcongedu = nbcongedu;
	}

	public String getAlocnetpayer() {
		return alocnetpayer;
	}

	public void setAlocnetpayer(String alocnetpayer) {
		this.alocnetpayer = alocnetpayer;
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

	public int getTempsPresenceEffectif() {
		return tempsPresenceEffectif;
	}

	public void setTempsPresenceEffectif(int tempsPresenceEffectif) {
		this.tempsPresenceEffectif = tempsPresenceEffectif;
	}

	public Double getProvisionConge() {
		return provisionConge;
	}

	public void setProvisionConge(Double provisionConge) {
		this.provisionConge = provisionConge;
	}

	public String getMontantProvisionConge() {
		return Utils.formattingAmount(provisionConge);
	}

	public void setMontantProvisionConge(String montantProvisionConge) {
		this.montantProvisionConge = montantProvisionConge;
	}

	public Double getBaseImposableAllocationConge() {
		return baseImposableAllocationConge;
	}

	public void setBaseImposableAllocationConge(Double baseImposableAllocationConge) {
		this.baseImposableAllocationConge = baseImposableAllocationConge;
	}

	public String getMontantBaseImposableAllocationConge() {
		return Utils.formattingAmount(baseImposableAllocationConge);
	}

	public void setMontantBaseImposableAllocationConge(String montantBaseImposableAllocationConge) {
		this.montantBaseImposableAllocationConge = montantBaseImposableAllocationConge;
	}

	public Double getTotalProvisionImpot() {
		return totalProvisionImpot;
	}

	public void setTotalProvisionImpot(Double totalProvisionImpot) {
		this.totalProvisionImpot = totalProvisionImpot;
	}

	public String getMontantTotalProvisionImpot() {
		return Utils.formattingAmount(totalProvisionImpot);
	}

	public void setMontantTotalProvisionImpot(String montantTotalProvisionImpot) {
		this.montantTotalProvisionImpot = montantTotalProvisionImpot;
	}

	public Double getTotalProvisionChargeSocial() {
		return totalProvisionChargeSocial;
	}

	public void setTotalProvisionChargeSocial(Double totalProvisionChargeSocial) {
		this.totalProvisionChargeSocial = totalProvisionChargeSocial;
	}

	public String getMontantTotalProvisionChargeSocial() {
		return Utils.formattingAmount(totalProvisionChargeSocial);
	}

	public void setMontantTotalProvisionChargeSocial(String montantTotalProvisionChargeSocial) {
		this.montantTotalProvisionChargeSocial = montantTotalProvisionChargeSocial;
	}

	public Double getTotalChargePatronale() {
		return totalChargePatronale;
	}

	public void setTotalChargePatronale(Double totalChargePatronale) {
		this.totalChargePatronale = totalChargePatronale;
	}

	public String getMontantTotalChargePatronale() {
		return Utils.formattingAmount(totalChargePatronale);
	}

	public void setMontantTotalChargePatronale(String montantTotalChargePatronale) {
		this.montantTotalChargePatronale = montantTotalChargePatronale;
	}

	public Double getIts() {
		return its;
	}

	public void setIts(Double its) {
		this.its = its;
	}

	public String getMontantITS() {
		return Utils.formattingAmount(its);
	}

	public void setMontantITS(String montantITS) {
		this.montantITS = montantITS;
	}

	public Double getCn() {
		return cn;
	}

	public void setCn(Double cn) {
		this.cn = cn;
	}

	public String getMontantCN() {
		return Utils.formattingAmount(cn);
	}

	public void setMontantCN(String montantCN) {
		this.montantCN = montantCN;
	}

	public Double getIgr() {
		return igr;
	}

	public void setIgr(Double igr) {
		this.igr = igr;
	}

	public String getMontantIGR() {
		return Utils.formattingAmount(igr);
	}

	public void setMontantIGR(String montantIGR) {
		this.montantIGR = montantIGR;
	}

	public Double getTotalRetenue() {
		return totalRetenue;
	}

	public void setTotalRetenue(Double totalRetenue) {
		this.totalRetenue = totalRetenue;
	}

	public String getMontantTotalRetenue() {
		return Utils.formattingAmount(totalRetenue);
	}

	public void setMontantTotalRetenue(String montantTotalRetenue) {
		this.montantTotalRetenue = montantTotalRetenue;
	}

	public Double getAllocationCongeNet() {
		return allocationCongeNet;
	}

	public void setAllocationCongeNet(Double allocationCongeNet) {
		this.allocationCongeNet = allocationCongeNet;
	}

	public String getMontantAllocationCongeNet() {
		return Utils.formattingAmount(allocationCongeNet);
	}

	public void setMontantAllocationCongeNet(String montantAllocationCongeNet) {
		this.montantAllocationCongeNet = montantAllocationCongeNet;
	}

	public Double getAllocationCongeNetPris() {
		return allocationCongeNetPris;
	}

	public void setAllocationCongeNetPris(Double allocationCongeNetPris) {
		this.allocationCongeNetPris = allocationCongeNetPris;
	}

	public String getMontantAllocationCongeNetPris() {
		return Utils.formattingAmount(allocationCongeNetPris);
	}

	public void setMontantAllocationCongeNetPris(String montantAllocationCongeNetPris) {
		this.montantAllocationCongeNetPris = montantAllocationCongeNetPris;
	}

	public Double getCnps() {
		return cnps;
	}

	public void setCnps(Double cnps) {
		this.cnps = cnps;
	}

	public String getMontantCNPS() {
		return montantCNPS;
	}

	public void setMontantCNPS(String montantCNPS) {
		this.montantCNPS = Utils.formattingAmount(cnps);
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

	public String getMontantNetPayer() {
		return montantNetPayer=montantAllocationCongeNet;
	}

	public void setMontantNetPayer(String montantNetPayer) {
		this.montantNetPayer = montantNetPayer;
	}

	public String getJouradditionel() {
		return jouradditionel;
	}

	public void setJouradditionel(String jouradditionel) {
		this.jouradditionel = jouradditionel;
	}

	
	
	public int getReeljrdu() {
		return reeljrdu;
	}

	public void setReeljrdu(int reeljrdu) {
		this.reeljrdu = reeljrdu;
	}

	public Integer getMoisOfpresence() {
		return moisOfpresence;
	}

	public void setMoisOfpresence(Integer moisOfpresence) {
		this.moisOfpresence = moisOfpresence;
	}

	public Integer getTempsOfpresence() {
		return tempsOfpresence;
	}

	public void setTempsOfpresence(Integer tempsOfpresence) {
		this.tempsOfpresence = tempsOfpresence;
	}

	@Override
	public String toString() {
		return "Conge [id=" + id + ", dateDepart=" + dateDepart + ", dDepart="
				+ dDepart + ", dateRetour=" + dateRetour + ", dRetour="
				+ dRetour + ", nombrePart=" + nombrePart + ", nbrePart="
				+ nbrePart + ", tempsPresenceEffectif=" + tempsPresenceEffectif
				+ ", salaireMoyenMensuel=" + salaireMoyenMensuel
				+ ", montantSalaireMoyenMensuel=" + montantSalaireMoyenMensuel
				+ ", indemniteRepresentationMoyenMensuelle="
				+ indemniteRepresentationMoyenMensuelle
				+ ", indemniteRepresentMoyenMensuel="
				+ indemniteRepresentMoyenMensuel + ", nombreJourCongeDu="
				+ nombreJourCongeDu + ", provisionConge=" + provisionConge
				+ ", montantProvisionConge=" + montantProvisionConge
				+ ", jouradditionel=" + jouradditionel
				+ ", baseImposableAllocationConge="
				+ baseImposableAllocationConge
				+ ", montantBaseImposableAllocationConge="
				+ montantBaseImposableAllocationConge + ", impotSalaire="
				+ impotSalaire + ", montantIs=" + montantIs + ", ta=" + ta
				+ ", montantTa=" + montantTa + ", fpc=" + fpc + ", montantFpc="
				+ montantFpc + ", totalProvisionImpot=" + totalProvisionImpot
				+ ", montantTotalProvisionImpot=" + montantTotalProvisionImpot
				+ ", prestationFamiliale=" + prestationFamiliale
				+ ", prestationFamil=" + prestationFamil + ", accidentTravail="
				+ accidentTravail + ", accidentTrav=" + accidentTrav
				+ ", retraite=" + retraite + ", montantRetraite="
				+ montantRetraite + ", totalProvisionChargeSocial="
				+ totalProvisionChargeSocial
				+ ", montantTotalProvisionChargeSocial="
				+ montantTotalProvisionChargeSocial + ", totalChargePatronale="
				+ totalChargePatronale + ", montantTotalChargePatronale="
				+ montantTotalChargePatronale + ", its=" + its
				+ ", montantITS=" + montantITS + ", cn=" + cn + ", montantCN="
				+ montantCN + ", igr=" + igr + ", montantIGR=" + montantIGR
				+ ", tpsdepresence=" + tpsdepresence + ", nbcongedu="
				+ nbcongedu + ", cnps=" + cnps + ", montantCNPS=" + montantCNPS
				+ ", totalRetenue=" + totalRetenue + ", montantTotalRetenue="
				+ montantTotalRetenue + ", allocationCongeNet="
				+ allocationCongeNet + ", montantAllocationCongeNet="
				+ montantAllocationCongeNet + ", listImprimBulletinPaie="
				+ listImprimBulletinPaie
				+ ", listImprimBulletinPaieEngagement="
				+ listImprimBulletinPaieEngagement
				+ ", listImprimBulletinPaieIndemniteNonImp="
				+ listImprimBulletinPaieIndemniteNonImp + ", montantcumulIts="
				+ montantcumulIts + ", montantNetPayer=" + montantNetPayer
				+ ", montantcumulCn=" + montantcumulCn + ", montantcumulIgr="
				+ montantcumulIgr + ", montantcumulCnpsSal="
				+ montantcumulCnpsSal + ", alocnetpayer=" + alocnetpayer
				+ ", contratPersonnel=" + contratPersonnel + ", periodePaie="
				+ periodePaie + "]";
	}
}
