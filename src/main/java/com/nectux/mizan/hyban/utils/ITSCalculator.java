package com.nectux.mizan.hyban.utils;
public class ITSCalculator {

    private static final double[][] TRANCHES = {
            {0, 75_000, 0.0},
            {75_000, 240_000, 0.16},
            {240_000, 800_000, 0.21},
            {800_000, 2_400_000, 0.24},
            {2_400_000, 8_000_000, 0.28},
            {8_000_000, Double.MAX_VALUE, 0.32}
    };

    public static double calculerITS(double revenuImposable, boolean afficherDetails) {
        double impots = 0.0;

        for (double[] tranche : TRANCHES) {
            double bas = tranche[0];
            double haut = tranche[1];
            double taux = tranche[2];

            if (revenuImposable > bas) {
                double taxable = Math.min(revenuImposable, haut) - bas;
                double impotsTranche = taxable * taux;
                impots += impotsTranche;

                if (afficherDetails) {
                    System.out.printf("Tranche: %.0f - %.0f FCFA | Taux: %.0f%% | Taxable: %.0f FCFA | Impôt: %.2f FCFA%n",
                            bas, haut, taux * 100, taxable, impotsTranche);
                }
            } else {
                break;
            }
        }

        if (afficherDetails) {
            System.out.printf(">>> Total ITS dû: %.2f FCFA%n", impots);
        }

        return impots;
    }

    public static void main(String[] args) {
        double revenu = 2_055_237;
        double its = calculerITS(revenu, true);
    }
}
