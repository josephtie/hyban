package com.nectux.mizan.hyban.personnel.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class RequestCategory implements Serializable {
    private Long id;
    private String libelle;
    private Double salaireDeBase;
    private Double indemniteLogement;

    // Champs transitoires côté UI (optionnel)
    private String salaireBase;
    private String montantIndemniteLogement;
}
