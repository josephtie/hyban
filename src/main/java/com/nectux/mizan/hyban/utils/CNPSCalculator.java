package com.nectux.mizan.hyban.utils;

public class CNPSCalculator {
    public static double[] calculerCNPS(double salaireBrut, double tauxRAT) {
        double plafondAVID = 2_700_000;
        double plafondAMO = 1_000_000;

        // Cotisations salariales
        double cotisationSalarialeAVID = Math.min(salaireBrut, plafondAVID) * 0.048;
        double cotisationSalarialeAMO = Math.min(salaireBrut, plafondAMO) * 0.0075;
        double totalSalarial = cotisationSalarialeAVID + cotisationSalarialeAMO;

        // Cotisations patronales
        double cotisationPatronalePrestationsFamiliales = salaireBrut * 0.0575;
        double cotisationPatronaleAVID = Math.min(salaireBrut, plafondAVID) * 0.077;
        double cotisationPatronaleAMO = Math.min(salaireBrut, plafondAMO) * 0.025;
        double totalPatronal = cotisationPatronalePrestationsFamiliales + cotisationPatronaleAVID +
                (salaireBrut * tauxRAT) + cotisationPatronaleAMO;

        // Retourner les valeurs sous forme de tableau
        return new double[]{
                cotisationSalarialeAVID, cotisationSalarialeAMO, totalSalarial,
                cotisationPatronalePrestationsFamiliales, cotisationPatronaleAVID, cotisationPatronaleAMO, totalPatronal
        };
    }

    public static void main(String[] args) {
        double salaireBrut = 1_500_000; // Salaire brut en FCFA
        double tauxRAT = 0.02; // Exemple : 2 % pour les risques d'accidents de travail

        double[] resultats = calculerCNPS(salaireBrut, tauxRAT);

        System.out.println("Cotisation Salariale AVID : " + resultats[0] + " FCFA");
        System.out.println("Cotisation Salariale AMO : " + resultats[1] + " FCFA");
        System.out.println("Total Cotisations Salariales : " + resultats[2] + " FCFA");
        System.out.println("Cotisation Patronale Prestations Familiales : " + resultats[3] + " FCFA");
        System.out.println("Cotisation Patronale AVID : " + resultats[4] + " FCFA");
        System.out.println("Cotisation Patronale AMO : " + resultats[5] + " FCFA");
        System.out.println("Total Cotisations Patronales : " + resultats[6] + " FCFA");
    }
}

