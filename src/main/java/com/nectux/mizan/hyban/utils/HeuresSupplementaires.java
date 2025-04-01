package com.nectux.mizan.hyban.utils;

public class HeuresSupplementaires {



    /**
     * Calcule le taux horaire à partir du salaire mensuel en considérant 173.33h par mois.
     */
    public static double calculerTauxHoraire(double salaireMensuelBase) {
        double heuresMensuelles = 173.33;
        return salaireMensuelBase / heuresMensuelles;
    }

    /**
     * Calcule les montants des heures supplémentaires et retourne les résultats sous forme de tableau.
     */
    public static double[][] calculerHeureSupMensuel(double salaireHoraire, int heuresTravaillees15, int heuresTravaillees50, int heuresDimanchesFeries) {
        // Calcul des montants
        double montantHeuresSupp15 = heuresTravaillees15 * salaireHoraire * 0.15;
        double montantHeuresSupp50 = heuresTravaillees50 * salaireHoraire * 0.50;
        double montantHeuresFeries = heuresDimanchesFeries * salaireHoraire * 2; // +100%

        // Retour sous forme de tableau : {heures, montant}
        return new double[][]{
                {heuresTravaillees15, montantHeuresSupp15},
                {heuresTravaillees50, montantHeuresSupp50},
                {heuresDimanchesFeries, montantHeuresFeries}
        };
    }

}
