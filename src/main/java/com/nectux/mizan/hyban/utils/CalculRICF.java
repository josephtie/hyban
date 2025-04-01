package com.nectux.mizan.hyban.utils;

public class CalculRICF {

    public static double getRICF(double nombreParts) {
        if (nombreParts <= 1) {
            return 0;
        } else if (nombreParts == 1.5) {
            return 66000;
        } else if (nombreParts == 2) {
            return 132000;
        } else if (nombreParts == 2.5) {
            return 198000;
        } else if (nombreParts == 3) {
            return 264000;
        } else if (nombreParts == 3.5) {
            return 330000;
        } else if (nombreParts == 4) {
            return 396000;
        } else if (nombreParts == 4.5) {
            return 462000;
        } else {
            return 528000; // Plafond maximal pour 5 parts et plus
        }
    }
}
