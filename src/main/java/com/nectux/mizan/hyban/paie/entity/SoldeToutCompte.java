package com.nectux.mizan.hyban.paie.entity;

import com.nectux.mizan.hyban.personnel.entity.ContratPersonnel;

import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class SoldeToutCompte {

    public double salaireMois;
    public double indemnConge;
    public double indemnPreavis;
    public double indemnLicenciement;
    public double primes;
    public double treiziemeMois;
    public double heuresSupp;
    public double avantagesNature;
    public double acomptes;
    public double totalBrut;
    public double totalNet;


    public double getTotalNet() {
        return totalNet;
    }

    public void setTotalNet(double totalNet) {
        this.totalNet = totalNet;
    }

    public double getTotalBrut() {
        return totalBrut;
    }

    public void setTotalBrut(double totalBrut) {
        this.totalBrut = totalBrut;
    }

    public double getAcomptes() {
        return acomptes;
    }

    public void setAcomptes(double acomptes) {
        this.acomptes = acomptes;
    }

    public double getAvantagesNature() {
        return avantagesNature;
    }

    public void setAvantagesNature(double avantagesNature) {
        this.avantagesNature = avantagesNature;
    }

    public double getHeuresSupp() {
        return heuresSupp;
    }

    public void setHeuresSupp(double heuresSupp) {
        this.heuresSupp = heuresSupp;
    }

    public double getTreiziemeMois() {
        return treiziemeMois;
    }

    public void setTreiziemeMois(double treiziemeMois) {
        this.treiziemeMois = treiziemeMois;
    }

    public double getPrimes() {
        return primes;
    }

    public void setPrimes(double primes) {
        this.primes = primes;
    }

    public double getIndemnLicenciement() {
        return indemnLicenciement;
    }

    public void setIndemnLicenciement(double indemnLicenciement) {
        this.indemnLicenciement = indemnLicenciement;
    }

    public double getIndemnPreavis() {
        return indemnPreavis;
    }

    public void setIndemnPreavis(double indemnPreavis) {
        this.indemnPreavis = indemnPreavis;
    }

    public double getIndemnConge() {
        return indemnConge;
    }

    public void setIndemnConge(double indemnConge) {
        this.indemnConge = indemnConge;
    }

    public double getSalaireMois() {
        return salaireMois;
    }

    public void setSalaireMois(double salaireMois) {
        this.salaireMois = salaireMois;
    }

    public SoldeToutCompte calculer(ContratPersonnel c) {
        SoldeToutCompte r = new SoldeToutCompte();

//        int joursTravailles = c.getDateFin().getDayOfMonth();
//        r.salaireMois = (c.getSalaireMensuel() / 30.0) * joursTravailles;
//
//        r.indemnConge = (c.getSalaireMensuel() / 30.0) * c.getJoursCongesNonPris();
//
//        r.indemnPreavis = (c.getSalaireMensuel() / 30.0) * c.getJoursPreavisNonEffectues();
//
//        r.indemnLicenciement = calculIndemniteLicenciement(c);
//
//        r.primes = c.getPrimes();
//
//        r.treiziemeMois = (c.getSalaireMensuel() / 12.0) * c.getMoisTravaillesDansAnnee();
//
//        r.heuresSupp = c.getHeuresSupp() * c.getTauxHoraire() * 1.25; // Majoration 25%
//
//        r.avantagesNature = c.getValeurAvantageNature();
//
//        r.acomptes = c.getAcomptes();

        r.totalBrut = r.salaireMois + r.indemnConge + r.indemnPreavis + r.indemnLicenciement
                + r.primes + r.treiziemeMois + r.heuresSupp + r.avantagesNature;

        r.totalNet = r.totalBrut - r.acomptes;

        return r;
    }

    private double calculIndemniteLicenciement(ContratPersonnel c) {
        long anciennete = ChronoUnit.YEARS.between(c.getDateDebut().toInstant().atZone(ZoneId.systemDefault())
                .toLocalDate(), c.getDateFin().toInstant().atZone(ZoneId.systemDefault())
                .toLocalDate());
        double salaireMoyen = c.getNetAPayer(); // Pour simplifier
        if (anciennete <= 5) {
            return salaireMoyen * 0.25 * anciennete;
        } else {
            return (salaireMoyen * 0.25 * 5) + (salaireMoyen * 0.33 * (anciennete - 5));
        }
    }
}

