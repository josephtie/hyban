package com.nectux.mizan.hyban.paie.dto;

import com.nectux.mizan.hyban.paie.entity.BulletinPaie;
import com.nectux.mizan.hyban.paie.entity.PrimePersonnel;
import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.personnel.entity.ContratPersonnel;

import javax.persistence.Transient;
import java.util.List;

public class LivreDePaieV2 {

    private String matricule;
    private String nomPrenom;
    private float nombrePart;
    private int anciennete;
    private double salaireBase;
    private double surSalaire;
    private double indemniteLogement;
    private double indemniteRepresentation;
    private double indemniteTransport;

    private double avance;
    private double pretAlios;
    private double brutNonImposable;
    private double cnpsSalariale;

    private double brutImposable;
    private double totalRetenue;
    private double netPayer;

    private double is;
    private double its;
    private double ta;
    private double fpc;
    private double fpcregul;
    private double prestationFamiliale;
    private double accidentTravail;
    private double retraite;
    private double totalPatronal;
    private double totalMasseSalariale;
    private double cumulNet;
    private double cumulIts;

    private int temptravail;
    private int moisdepresence;

    private List<PrimePersonnel> indemniteBrut;
    private List<PrimePersonnel> indemniteNonBrut;
    private List<PrimePersonnel> retenueMutuelle;
    private List<PrimePersonnel> gainsNet;
    private List<PrimePersonnel> retenueSociale;

    private BulletinPaie bullpaie;
    private ContratPersonnel contratPersonnel;
    private PeriodePaie periodePaie;
    private  Integer JourTravail ;

    @Transient
    private String mtindemniteRepresentation;
    private double cumulCnpsSalariale;

    // constructeur complet
    public LivreDePaieV2() {}

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNomPrenom() {
        return nomPrenom;
    }

    public void setNomPrenom(String nomPrenom) {
        this.nomPrenom = nomPrenom;
    }

    public float getNombrePart() {
        return nombrePart;
    }

    public void setNombrePart(float nombrePart) {
        this.nombrePart = nombrePart;
    }

    public int getAnciennete() {
        return anciennete;
    }

    public void setAnciennete(int anciennete) {
        this.anciennete = anciennete;
    }

    public double getSalaireBase() {
        return salaireBase;
    }

    public void setSalaireBase(double salaireBase) {
        this.salaireBase = salaireBase;
    }

    public double getSurSalaire() {
        return surSalaire;
    }

    public void setSurSalaire(double surSalaire) {
        this.surSalaire = surSalaire;
    }

    public double getIndemniteLogement() {
        return indemniteLogement;
    }

    public void setIndemniteLogement(double indemniteLogement) {
        this.indemniteLogement = indemniteLogement;
    }

    public double getAvance() {
        return avance;
    }

    public void setAvance(double avance) {
        this.avance = avance;
    }

    public double getPretAlios() {
        return pretAlios;
    }

    public void setPretAlios(double pretAlios) {
        this.pretAlios = pretAlios;
    }

    public double getBrutImposable() {
        return brutImposable;
    }

    public void setBrutImposable(double brutImposable) {
        this.brutImposable = brutImposable;
    }

    public double getTotalRetenue() {
        return totalRetenue;
    }

    public void setTotalRetenue(double totalRetenue) {
        this.totalRetenue = totalRetenue;
    }

    public double getNetPayer() {
        return netPayer;
    }

    public void setNetPayer(double netPayer) {
        this.netPayer = netPayer;
    }

    public double getIs() {
        return is;
    }

    public void setIs(double is) {
        this.is = is;
    }

    public double getTa() {
        return ta;
    }

    public void setTa(double ta) {
        this.ta = ta;
    }

    public double getFpc() {
        return fpc;
    }

    public void setFpc(double fpc) {
        this.fpc = fpc;
    }

    public double getFpcregul() {
        return fpcregul;
    }

    public void setFpcregul(double fpcregul) {
        this.fpcregul = fpcregul;
    }

    public double getPrestationFamiliale() {
        return prestationFamiliale;
    }

    public void setPrestationFamiliale(double prestationFamiliale) {
        this.prestationFamiliale = prestationFamiliale;
    }

    public double getAccidentTravail() {
        return accidentTravail;
    }

    public void setAccidentTravail(double accidentTravail) {
        this.accidentTravail = accidentTravail;
    }

    public double getRetraite() {
        return retraite;
    }

    public void setRetraite(double retraite) {
        this.retraite = retraite;
    }

    public double getTotalPatronal() {
        return totalPatronal;
    }

    public void setTotalPatronal(double totalPatronal) {
        this.totalPatronal = totalPatronal;
    }

    public double getTotalMasseSalariale() {
        return totalMasseSalariale;
    }

    public void setTotalMasseSalariale(double totalMasseSalariale) {
        this.totalMasseSalariale = totalMasseSalariale;
    }

    public int getTemptravail() {
        return temptravail;
    }

    public void setTemptravail(int temptravail) {
        this.temptravail = temptravail;
    }

    public int getMoisdepresence() {
        return moisdepresence;
    }

    public void setMoisdepresence(int moisdepresence) {
        this.moisdepresence = moisdepresence;
    }

    public List<PrimePersonnel> getIndemniteBrut() {
        return indemniteBrut;
    }

    public void setIndemniteBrut(List<PrimePersonnel> indemniteBrut) {
        this.indemniteBrut = indemniteBrut;
    }

    public List<PrimePersonnel> getIndemniteNonBrut() {
        return indemniteNonBrut;
    }

    public void setIndemniteNonBrut(List<PrimePersonnel> indemniteNonBrut) {
        this.indemniteNonBrut = indemniteNonBrut;
    }

    public List<PrimePersonnel> getRetenueMutuelle() {
        return retenueMutuelle;
    }

    public void setRetenueMutuelle(List<PrimePersonnel> retenueMutuelle) {
        this.retenueMutuelle = retenueMutuelle;
    }

    public List<PrimePersonnel> getGainsNet() {
        return gainsNet;
    }

    public void setGainsNet(List<PrimePersonnel> gainsNet) {
        this.gainsNet = gainsNet;
    }

    public List<PrimePersonnel> getRetenueSociale() {
        return retenueSociale;
    }

    public void setRetenueSociale(List<PrimePersonnel> retenueSociale) {
        this.retenueSociale = retenueSociale;
    }

    public BulletinPaie getBullpaie() {
        return bullpaie;
    }

    public void setBullpaie(BulletinPaie bullpaie) {
        this.bullpaie = bullpaie;
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

    public Integer getJourTravail() {
        return JourTravail;
    }

    public void setJourTravail(Integer jourTravail) {
        JourTravail = jourTravail;
    }


    public Double getIndemniteRepresentation() {
        return indemniteRepresentation;
    }

    public void setIndemniteRepresentation(Double indemniteRepresentation) {
        this.indemniteRepresentation = indemniteRepresentation;
    }

    public String getMtindemniteRepresentation() {
        return mtindemniteRepresentation;
    }

    public void setMtindemniteRepresentation(String mtindemniteRepresentation) {
        this.mtindemniteRepresentation = mtindemniteRepresentation;
    }

    public void setBrutNonImposable(double brutNonImposable) {
        this.brutNonImposable = brutNonImposable;
    }

    public double getBrutNonImposable() {
        return brutNonImposable;
    }

    public void setCnpsSalariale(double cnpsSalariale) {
        this.cnpsSalariale = cnpsSalariale;
    }

    public double getCnpsSalariale() {
        return cnpsSalariale;
    }

    public void setIts(double its) {
        this.its = its;
    }

    public double getIts() {
        return its;
    }

    public Double calculCNPS(Double basecnps){
        Double cnps = (basecnps ); //3000000
        if(cnps < 3375000.0)
            cnps = (basecnps ) * 6.3 / 100;
        else
            cnps = 3375000.0 * 6.3 / 100;
        return cnps;
    }

    public void setCumulNet(double cumulNet) {
        this.cumulNet = cumulNet;
    }

    public double getCumulNet() {
        return cumulNet;
    }

    public void setCumulIts(double cumulIts) {
        this.cumulIts = cumulIts;
    }

    public double getCumulIts() {
        return cumulIts;
    }

    public void setCumulCnpsSalariale(double cumulCnpsSalariale) {
        this.cumulCnpsSalariale = cumulCnpsSalariale;
    }

    public double getCumulCnpsSalariale() {
        return cumulCnpsSalariale;
    }

    public void setIndemniteRepresentation(double indemniteRepresentation) {
        this.indemniteRepresentation = indemniteRepresentation;
    }

    public double getIndemniteTransport() {
        return indemniteTransport;
    }

    public void setIndemniteTransport(double indemniteTransport) {
        this.indemniteTransport = indemniteTransport;
    }
}

