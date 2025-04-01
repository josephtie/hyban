package com.nectux.mizan.hyban.utils;

public class ITSCalculator {
    // Définition des tranches d'imposition et des taux correspondants
    private static final double[][] TRANCHES = {
            {0, 75_000, 0.0},       // 0% jusqu'à 75 000 FCFA
            {75_001, 150_000, 0.10}, // 10% entre 75 001 et 150 000 FCFA
            {150_001, 300_000, 0.15}, // 15% entre 150 001 et 300 000 FCFA
            {300_001, 600_000, 0.20}, // 20% entre 300 001 et 600 000 FCFA
            {600_001, 1_000_000, 0.25}, // 25% entre 600 001 et 1 000 000 FCFA
            {1_000_001, Double.MAX_VALUE, 0.30} // 30% au-delà de 1 000 000 FCFA
    };

    public static double calculerITS(double revenuImposable) {
        double impots = 0.0;

        for (double[] tranche : TRANCHES) {
            double bas = tranche[0];
            double haut = tranche[1];
            double taux = tranche[2];

            if (revenuImposable > bas) {
                double taxable = Math.min(revenuImposable, haut) - bas;
                impots += taxable * taux;
            } else {
                break;
            }
        }

        return impots;
    }

//    public static void main(String[] args) {
//        double salaireBrut = 1_200_000; // Exemple de salaire brut mensuel en FCFA
//        double abattement = 0.2; // Abattement de 20 %
//        double revenuImposable = salaireBrut * (1 - abattement);
//
//        double its = calculerITS(revenuImposable);
//        System.out.println("Salaire brut : " + salaireBrut + " FCFA");
//        System.out.println("Revenu imposable après abattement : " + revenuImposable + " FCFA");
//        System.out.println("ITS à payer : " + its + " FCFA");
//    }
}
