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

import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.utils.Utils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


import com.nectux.mizan.hyban.personnel.entity.ContratPersonnel;

@Entity
@Component("bulletinPaies")
@Scope("prototype")
@Table(name="CGECI_RHPAIE_BULLETIN_PAIE")
@SequenceGenerator(name="CGECI_RHPAIE_BULLETIN_PAIE_SEQUENCE", sequenceName="CGECI_RHPAIE_BULLETIN_PAIE_SEQ", initialValue=1, allocationSize=1)
public class BulletinPaie {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CGECI_RHPAIE_BULLETIN_PAIE_SEQUENCE")
	@Column(unique=true, nullable=false)
	private Long id;
	
	private Float nombrePart;
	
	@Transient
	private String nbrePart;
	
	private int anciennete;
	
	
	@Transient
	private String montantAnciennete;
	
    private Double salairbase;
	
	@Transient
	private String montantSalairbase;
	
	
	 private Double primeanciennete;
	 
	 private Integer moisOfpresence;
	 
	private Integer tempsOfpresence;
		
    @Transient
	private String montantPrimeanciennete;
	
    private Double indemnitelogement;
	
    @Transient
	private String montantIndemnitelogement;
    
    private Double brutImposable;
		
	@Transient
	private String brutImpo;
	
	private Double autreIndemImposable;
	
	 @Transient
		private String montantautreIndemImposable;

	 private Double brutNonImposable;
		
	@Transient
	private String mtbrutNonImpo;

	private Double autrePrelevement;

	@Transient
	private String mtautrePrelevement;

	private Double regularisation;

	@Transient
	private String mtregularisation;

	private Double autreImposable;
	
	@Transient
	private String mtautreImposable;
	
	
	private Double autreNonImposable;
	
	@Transient
	private String mtautreNonImposable;
	
	
	
	private Double sursalaire;
	
	@Transient
	private String montantSursalaire;
	
	private Boolean calculer;
	
	private Boolean cloture;
	
	private Boolean congeAc;
	
	private Double its;
	
	private Double cumuljourTravail;
	
	private Double jourTravail;
	
	private Double temptravail;
	
	private Double cumulIts;
	@Transient
	private String montantcumulIts;
	
	private Double cumulCn;
	@Transient
	private String montantcumulCn;
	
	private Double cumulIgr;
	@Transient
	
	private String montantcumulIgr;
	
	private Double cumulCnpsSal;
	@Transient
	private String montantcumulCnpsSal;
	
	@Transient
	private String montantIts;
	
	private Double cn;
	
	@Transient
	private String montantCn;
	
	private Double igr;
	
	@Transient
	private String montantIgr;
	
	
    private Double totalretenuefiscal;
	
	@Transient
	private String montanttotalretenuefiscal;
	
	
	private Double avanceetacompte;
		
	@Transient
	private String montantavanceetacompte;
	
	private Double carec;
	
	@Transient
	private String montantcarec;
	
	
	@Transient
	private String tpsdepresence;
	
	@Transient
	private String nbcongedu;
	
	
   private Double totalbrut;
	
	@Transient
	private String montanttotalbrut;
	
    private Double totalmassesalarial;
	
	@Transient
	private String montanttotalmassesalarial;
	
	private Double	cumulRetenueNet;
	@Transient
	private String montantcumulRetenueNet;
	
	private Double cumulSalaireNet;
	@Transient
	private String montantcumulSalaireNet;
	
    private Double totalpatronal;
	
	@Transient
	private String montanttotalpatronal;
	
	
	private Double totalretenue;
	
	@Transient
	private String montanttotalretenue;
	
    private Double pretaloes;
	
	@Transient
	private String montantpretaloes;
	
	private Double cnps;
	
	@Transient
	private String montantCnps;
	
	
private Double basecnps;
	
	@Transient
	private String montantbaseCnps;
	
	
	
	private Double indemniteRepresentation;
	
	@Transient
	private String indemniteRepresent;
	
	
	private Double indemniteRespons;
	
	@Transient
	private String mtindemniteRespons;
	
	
	
	private Double indemniteTransport;
	
	@Transient
	private String indemniteTransp;
	
	
    private Double indemniteTransportImp;
	
	@Transient
	private String mtindemniteTranspImp;
	
    private Double netapayer;
	
	@Transient
	private String montantNetapayer;
	
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
	
	@Transient
	private List<ImprimBulletinPaie> listImprimBulletinPaie;
	
	@Transient
	private List<ImprimBulletinPaie> listImprimBulletinPaieEngagement;
	
	@Transient
	private List<ImprimBulletinPaie> listImprimBulletinPaieIndemniteNonImp;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private ContratPersonnel contratPersonnel;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private PeriodePaie periodePaie;
	
	public BulletinPaie() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Float getNombrePart() {
		return nombrePart;
	}

	public void setNombrePart(Float nombrePart) {
		this.nombrePart = nombrePart;
	}

	

	public String getNbrePart() {
		return nbrePart;
	}

	public String getMontantAnciennete() {
		return montantAnciennete;
	}

	public void setNbrePart(String nbrePart) {
		this.nbrePart = nbrePart;
	}

	public int getAnciennete() {
		return anciennete;
	}

	public void setAnciennete(int anciennete) {
		this.anciennete = anciennete;
	}

	

	public void setMontantAnciennete(String montantAnciennete) {
		this.montantAnciennete = montantAnciennete;
	}

	public Double getBrutImposable() {
		return brutImposable;
	}

	public void setBrutImposable(Double brutImposable) {
		this.brutImposable = brutImposable;
	}

	public String getBrutImpo() {
		brutImpo = Utils.formattingAmount(brutImposable);
		return brutImpo;
	}

	public void setBrutImpo(String brutImpo) {
		this.brutImpo = brutImpo;
	}

	public Double getSursalaire() {
		return sursalaire;
	}

	public void setSursalaire(Double sursalaire) {
		this.sursalaire = sursalaire;
	}

	public String getMontantSursalaire() {
		montantSursalaire = Utils.formattingAmount(sursalaire);
		return montantSursalaire;
	}

	public void setMontantSursalaire(String montantSursalaire) {
		this.montantSursalaire = montantSursalaire;
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

	public Double getIndemniteRepresentation() {
		return indemniteRepresentation;
	}

	public void setIndemniteRepresentation(Double indemniteRepresentation) {
		this.indemniteRepresentation = indemniteRepresentation;
	}

	public String getIndemniteRepresent() {
		indemniteRepresent = Utils.formattingAmount(indemniteRepresentation);
		return indemniteRepresent;
	}

	public void setIndemniteRepresent(String indemniteRepresent) {
		this.indemniteRepresent = indemniteRepresent;
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

	public Boolean getCalculer() {
		return calculer;
	}

	public void setCalculer(Boolean calculer) {
		this.calculer = calculer;
	}

	public Boolean getCloture() {
		return cloture;
	}

	public void setCloture(Boolean cloture) {
		this.cloture = cloture;
	}

	public Double getSalairbase() {
		return salairbase;
	}

	public void setSalairbase(Double salairbase) {
		this.salairbase = salairbase;
	}

	public String getMontantSalairbase() {
		montantSalairbase = Utils.formattingAmount(salairbase);
		return montantSalairbase;
	}

	public void setMontantSalairbase(String montantSalairbase) {
		this.montantSalairbase = montantSalairbase;
		
	}

	public Double getPrimeanciennete() {
		return primeanciennete;
	}

	public void setPrimeanciennete(Double primeanciennete) {
		this.primeanciennete = primeanciennete;
	}

	public String getMontantPrimeanciennete() {
		
		montantPrimeanciennete = Utils.formattingAmount(primeanciennete);
		return montantPrimeanciennete;
	}

	public void setMontantPrimeanciennete(String montantPrimeanciennete) {
		this.montantPrimeanciennete = montantPrimeanciennete;
	}

	public Double getIndemnitelogement() {
		return indemnitelogement;
	}

	public void setIndemnitelogement(Double indemnitelogement) {
		this.indemnitelogement = indemnitelogement;
	}

	public String getMontantIndemnitelogement() {
		return montantIndemnitelogement= Utils.formattingAmount(indemnitelogement);
	}

	public void setMontantIndemnitelogement(String montantIndemnitelogement) {
		this.montantIndemnitelogement = montantIndemnitelogement;
	}

	public Double getTotalretenuefiscal() {
		return totalretenuefiscal;
	}

	public void setTotalretenuefiscal(Double totalretenuefiscal) {
		this.totalretenuefiscal = totalretenuefiscal;
	}

	public String getMontanttotalretenuefiscal() {
		return montanttotalretenuefiscal= Utils.formattingAmount(totalretenuefiscal);
	}

	public void setMontanttotalretenuefiscal(String montanttotalretenuefiscal) {
		this.montanttotalretenuefiscal = montanttotalretenuefiscal;
	}

	public Double getAvanceetacompte() {
		return avanceetacompte;
	}

	public void setAvanceetacompte(Double avanceetacompte) {
		this.avanceetacompte = avanceetacompte;
	}

	public String getMontantavanceetacompte() {
		return montantavanceetacompte= Utils.formattingAmount(avanceetacompte);
	}

	public void setMontantavanceetacompte(String montantavanceetacompte) {
		this.montantavanceetacompte = montantavanceetacompte;
	}

	public Double getCarec() {
		return carec;
	}

	public void setCarec(Double carec) {
		this.carec = carec;
	}

	public String getMontantcarec() {
		return montantcarec= Utils.formattingAmount(carec);
	}

	public void setMontantcarec(String montantcarec) {
		this.montantcarec = montantcarec;
	}

	public Double getTotalmassesalarial() {
		return totalmassesalarial;
	}

	public void setTotalmassesalarial(Double totalmassesalarial) {
		this.totalmassesalarial = totalmassesalarial;
	}

	public String getMontanttotalmassesalarial() {
		return montanttotalmassesalarial= Utils.formattingAmount(totalmassesalarial);
	}

	public void setMontanttotalmassesalarial(String montanttotalmassesalarial) {
		this.montanttotalmassesalarial = montanttotalmassesalarial;
	}

	public Double getTotalpatronal() {
		return totalpatronal;
	}

	public void setTotalpatronal(Double totalpatronal) {
		this.totalpatronal = totalpatronal;
	}

	public String getMontanttotalpatronal() {
		return montanttotalpatronal= Utils.formattingAmount(totalpatronal);
	}

	public void setMontanttotalpatronal(String montanttotalpatronal) {
		this.montanttotalpatronal = montanttotalpatronal;
	}

	public Double getTotalretenue() {
		return totalretenue;
	}

	public void setTotalretenue(Double totalretenue) {
		this.totalretenue = totalretenue;
	}

	public String getMontanttotalretenue() {
		return montanttotalretenue= Utils.formattingAmount(totalretenue);
	}

	public void setMontanttotalretenue(String montanttotalretenue) {
		this.montanttotalretenue = montanttotalretenue;
	}

	public Double getPretaloes() {
		return pretaloes;
	}

	public void setPretaloes(Double pretaloes) {
		this.pretaloes = pretaloes;
	}

	public String getMontantpretaloes() {
		return montantpretaloes= Utils.formattingAmount(pretaloes);
	}

	public void setMontantpretaloes(String montantpretaloes) {
		this.montantpretaloes = montantpretaloes;
	}

	public Double getNetapayer() {
		return netapayer;
	}

	public void setNetapayer(Double netapayer) {
		this.netapayer = netapayer;
	}

	public String getMontantNetapayer() {
		return montantNetapayer= Utils.formattingAmount(netapayer);
	}

	public void setMontantNetapayer(String montantNetapayer) {
		this.montantNetapayer = montantNetapayer;
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

	
	
	
	public Double getIndemniteRespons() {
		return indemniteRespons;
	}

	public void setIndemniteRespons(Double indemniteRespons) {
		this.indemniteRespons = indemniteRespons;
	}

	public String getMtindemniteRespons() {
		return mtindemniteRespons=Utils.formattingAmount(indemniteRespons);
	}

	public void setMtindemniteRespons(String mtindemniteRespons) {
		this.mtindemniteRespons = mtindemniteRespons;
	}

	public Double getIndemniteTransportImp() {
		return indemniteTransportImp;
	}

	public void setIndemniteTransportImp(Double indemniteTransportImp) {
		this.indemniteTransportImp = indemniteTransportImp;
	}

	

	public String getMtindemniteTranspImp() {
		return mtindemniteTranspImp=Utils.formattingAmount(indemniteTransportImp);
	}

	public void setMtindemniteTranspImp(String mtindemniteTranspImp) {
		this.mtindemniteTranspImp = mtindemniteTranspImp;
	}

	public Double getBrutNonImposable() {
		return brutNonImposable;
	}

	public void setBrutNonImposable(Double brutNonImposable) {
		this.brutNonImposable = brutNonImposable;
	}

	public String getMtbrutNonImpo() {
		return mtbrutNonImpo=Utils.formattingAmount(brutNonImposable);
	}

	public void setMtbrutNonImpo(String mtbrutNonImpo) {
		this.mtbrutNonImpo = mtbrutNonImpo;
	}

	@Override
	public String toString() {
		return "BulletinPaie{" +
				"id=" + id +
				", nombrePart=" + nombrePart +
				", nbrePart='" + nbrePart + '\'' +
				", anciennete=" + anciennete +
				", montantAnciennete='" + montantAnciennete + '\'' +
				", salairbase=" + salairbase +
				", montantSalairbase='" + montantSalairbase + '\'' +
				", primeanciennete=" + primeanciennete +
				", moisOfpresence=" + moisOfpresence +
				", tempsOfpresence=" + tempsOfpresence +
				", montantPrimeanciennete='" + montantPrimeanciennete + '\'' +
				", indemnitelogement=" + indemnitelogement +
				", montantIndemnitelogement='" + montantIndemnitelogement + '\'' +
				", brutImposable=" + brutImposable +
				", brutImpo='" + brutImpo + '\'' +
				", autreIndemImposable=" + autreIndemImposable +
				", montantautreIndemImposable='" + montantautreIndemImposable + '\'' +
				", brutNonImposable=" + brutNonImposable +
				", mtbrutNonImpo='" + mtbrutNonImpo + '\'' +
				", autrePrelevement=" + autrePrelevement +
				", mtautrePrelevement='" + mtautrePrelevement + '\'' +
				", regularisation=" + regularisation +
				", mtregularisation='" + mtregularisation + '\'' +
				", autreImposable=" + autreImposable +
				", mtautreImposable='" + mtautreImposable + '\'' +
				", autreNonImposable=" + autreNonImposable +
				", mtautreNonImposable='" + mtautreNonImposable + '\'' +
				", sursalaire=" + sursalaire +
				", montantSursalaire='" + montantSursalaire + '\'' +
				", calculer=" + calculer +
				", cloture=" + cloture +
				", congeAc=" + congeAc +
				", its=" + its +
				", cumuljourTravail=" + cumuljourTravail +
				", jourTravail=" + jourTravail +
				", temptravail=" + temptravail +
				", cumulIts=" + cumulIts +
				", montantcumulIts='" + montantcumulIts + '\'' +
				", cumulCn=" + cumulCn +
				", montantcumulCn='" + montantcumulCn + '\'' +
				", cumulIgr=" + cumulIgr +
				", montantcumulIgr='" + montantcumulIgr + '\'' +
				", cumulCnpsSal=" + cumulCnpsSal +
				", montantcumulCnpsSal='" + montantcumulCnpsSal + '\'' +
				", montantIts='" + montantIts + '\'' +
				", cn=" + cn +
				", montantCn='" + montantCn + '\'' +
				", igr=" + igr +
				", montantIgr='" + montantIgr + '\'' +
				", totalretenuefiscal=" + totalretenuefiscal +
				", montanttotalretenuefiscal='" + montanttotalretenuefiscal + '\'' +
				", avanceetacompte=" + avanceetacompte +
				", montantavanceetacompte='" + montantavanceetacompte + '\'' +
				", carec=" + carec +
				", montantcarec='" + montantcarec + '\'' +
				", tpsdepresence='" + tpsdepresence + '\'' +
				", nbcongedu='" + nbcongedu + '\'' +
				", totalbrut=" + totalbrut +
				", montanttotalbrut='" + montanttotalbrut + '\'' +
				", totalmassesalarial=" + totalmassesalarial +
				", montanttotalmassesalarial='" + montanttotalmassesalarial + '\'' +
				", cumulRetenueNet=" + cumulRetenueNet +
				", montantcumulRetenueNet='" + montantcumulRetenueNet + '\'' +
				", cumulSalaireNet=" + cumulSalaireNet +
				", montantcumulSalaireNet='" + montantcumulSalaireNet + '\'' +
				", totalpatronal=" + totalpatronal +
				", montanttotalpatronal='" + montanttotalpatronal + '\'' +
				", totalretenue=" + totalretenue +
				", montanttotalretenue='" + montanttotalretenue + '\'' +
				", pretaloes=" + pretaloes +
				", montantpretaloes='" + montantpretaloes + '\'' +
				", cnps=" + cnps +
				", montantCnps='" + montantCnps + '\'' +
				", basecnps=" + basecnps +
				", montantbaseCnps='" + montantbaseCnps + '\'' +
				", indemniteRepresentation=" + indemniteRepresentation +
				", indemniteRepresent='" + indemniteRepresent + '\'' +
				", indemniteRespons=" + indemniteRespons +
				", mtindemniteRespons='" + mtindemniteRespons + '\'' +
				", indemniteTransport=" + indemniteTransport +
				", indemniteTransp='" + indemniteTransp + '\'' +
				", indemniteTransportImp=" + indemniteTransportImp +
				", mtindemniteTranspImp='" + mtindemniteTranspImp + '\'' +
				", netapayer=" + netapayer +
				", montantNetapayer='" + montantNetapayer + '\'' +
				", impotSalaire=" + impotSalaire +
				", montantIs='" + montantIs + '\'' +
				", ta=" + ta +
				", montantTa='" + montantTa + '\'' +
				", fpc=" + fpc +
				", montantFpc='" + montantFpc + '\'' +
				", prestationFamiliale=" + prestationFamiliale +
				", prestationFamil='" + prestationFamil + '\'' +
				", accidentTravail=" + accidentTravail +
				", accidentTrav='" + accidentTrav + '\'' +
				", retraite=" + retraite +
				", montantRetraite='" + montantRetraite + '\'' +
				", listImprimBulletinPaie=" + listImprimBulletinPaie +
				", listImprimBulletinPaieEngagement=" + listImprimBulletinPaieEngagement +
				", listImprimBulletinPaieIndemniteNonImp=" + listImprimBulletinPaieIndemniteNonImp +
				", contratPersonnel=" + contratPersonnel +
				", periodePaie=" + periodePaie +
				'}';
	}

	public Double getTotalbrut() {
		return totalbrut;
	}

	public void setTotalbrut(Double totalbrut) {
		this.totalbrut = totalbrut;
	}

	public String getMontanttotalbrut() {
		return montanttotalbrut= Utils.formattingAmount(totalbrut);
	}

	public void setMontanttotalbrut(String montanttotalbrut) {
		this.montanttotalbrut = montanttotalbrut;
	}

	public Double getCumulIts() {
		return cumulIts;
	}

	public void setCumulIts(Double cumulIts) {
		this.cumulIts = cumulIts;
	}

	public Double getCumulCn() {
		return cumulCn;
	}

	public void setCumulCn(Double cumulCn) {
		this.cumulCn = cumulCn;
	}

	public Double getCumulIgr() {
		return cumulIgr;
	}

	public void setCumulIgr(Double cumulIgr) {
		this.cumulIgr = cumulIgr;
	}

	public Double getCumulCnpsSal() {
		return cumulCnpsSal;
	}

	public void setCumulCnpsSal(Double cumulCnpsSal) {
		this.cumulCnpsSal = cumulCnpsSal;
	}

	public String getMontantcumulIts() {
		
		return montantcumulIts= Utils.formattingAmount(cumulIts);
	}

	public void setMontantcumulIts(String montantcumulIts) {
		this.montantcumulIts = montantcumulIts;
	}

	public String getMontantcumulCn() {		
		return montantcumulCn= Utils.formattingAmount(cumulCn);
	}

	public void setMontantcumulCn(String montantcumulCn) {
		this.montantcumulCn = montantcumulCn;
	}

	public String getMontantcumulIgr() {
		
		return montantcumulIgr= Utils.formattingAmount(cumulIgr);
	}

	public void setMontantcumulIgr(String montantcumulIgr) {
		this.montantcumulIgr = montantcumulIgr;
	}

	public String getMontantcumulCnpsSal() {
		
		return montantcumulCnpsSal= Utils.formattingAmount(cumulCnpsSal);
	}

	public void setMontantcumulCnpsSal(String montantcumulCnpsSal) {
		this.montantcumulCnpsSal = montantcumulCnpsSal;
	}

	public Double getJourTravail() {
		return jourTravail;
	}

	public void setJourTravail(Double jourTravail) {
		this.jourTravail = jourTravail;
	}

	public Double getTemptravail() {
		return temptravail;
	}

	public void setTemptravail(Double temptravail) {
		this.temptravail = temptravail;
	}

	public Double getCumuljourTravail() {
		return cumuljourTravail;
	}

	public void setCumuljourTravail(Double cumuljourTravail) {
		this.cumuljourTravail = cumuljourTravail;
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

	

	public Integer getTempsOfpresence() {
		return tempsOfpresence;
	}

	public void setTempsOfpresence(Integer tempsOfpresence) {
		this.tempsOfpresence = tempsOfpresence;
	}

	public Integer getMoisOfpresence() {
		return moisOfpresence;
	}

	public void setMoisOfpresence(Integer moisOfpresence) {
		this.moisOfpresence = moisOfpresence;
	}

	public Boolean getCongeAc() {
		return congeAc;
	}

	public void setCongeAc(Boolean congeAc) {
		this.congeAc = congeAc;
	}

	public Double getBasecnps() {
		return basecnps;
	}

	public void setBasecnps(Double basecnps) {
		this.basecnps = basecnps;
	}

	public String getMontantbaseCnps() {
		return montantbaseCnps=Utils.formattingAmount(basecnps);
	}

	public void setMontantbaseCnps(String montantbaseCnps) {
		this.montantbaseCnps = montantbaseCnps;
	}

	public Double getAutreImposable() {
		return autreImposable;
	}

	public void setAutreImposable(Double autreImposable) {
		this.autreImposable = autreImposable;
	}

	public String getMtautreImposable() {
		return mtautreImposable;
	}

	public void setMtautreImposable(String mtautreImposable) {
		this.mtautreImposable =Utils.formattingAmount(autreImposable);
	}

	public Double getAutreNonImposable() {
		return autreNonImposable;
	}

	public void setAutreNonImposable(Double autreNonImposable) {
		this.autreNonImposable = autreNonImposable;
	}

	public String getMtautreNonImposable() {
		return mtautreNonImposable=Utils.formattingAmount(autreNonImposable);
	}

	public void setMtautreNonImposable(String mtautreNonImposable) {
		this.mtautreNonImposable = mtautreNonImposable;
	}

	public Double getCumulRetenueNet() {
		return cumulRetenueNet;
	}

	public void setCumulRetenueNet(Double cumulRetenueNet) {
		this.cumulRetenueNet = cumulRetenueNet;
	}

	public String getMontantcumulRetenueNet() {
		return montantcumulRetenueNet=Utils.formattingAmount(cumulRetenueNet);
	}

	public void setMontantcumulRetenueNet(String montantcumulRetenueNet) {
		this.montantcumulRetenueNet = montantcumulRetenueNet;
	}

	public Double getCumulSalaireNet() {
		return cumulSalaireNet;
	}

	public void setCumulSalaireNet(Double cumulSalaireNet) {
		this.cumulSalaireNet = cumulSalaireNet;
	}

	public String getMontantcumulSalaireNet() {
		return montantcumulSalaireNet=Utils.formattingAmount(cumulSalaireNet);
	}

	public void setMontantcumulSalaireNet(String montantcumulSalaireNet) {
		this.montantcumulSalaireNet = montantcumulSalaireNet;
	}

	public Double getAutreIndemImposable() {
		return autreIndemImposable;
	}

	public void setAutreIndemImposable(Double autreIndemImposable) {
		this.autreIndemImposable = autreIndemImposable;
	}

	public String getMontantautreIndemImposable() {
		return montantautreIndemImposable=Utils.formattingAmount(autreIndemImposable);
	}

	public void setMontantautreIndemImposable(String montantautreIndemImposable) {
		this.montantautreIndemImposable = montantautreIndemImposable;
	}

	public Double getAutrePrelevement() {
		return autrePrelevement;
	}

	public void setAutrePrelevement(Double autrePrelevement) {
		this.autrePrelevement = autrePrelevement;
	}

	public String getMtautrePrelevement() {
		return mtautrePrelevement;
	}

	public void setMtautrePrelevement(String mtautrePrelevement) {
		this.mtautrePrelevement = mtautrePrelevement;
	}

	public Double getRegularisation() {
		return regularisation;
	}

	public void setRegularisation(Double regularisation) {
		this.regularisation = regularisation;
	}

	public String getMtregularisation() {
		return mtregularisation;
	}

	public void setMtregularisation(String mtregularisation) {
		this.mtregularisation = mtregularisation;
	}
}
