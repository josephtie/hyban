package com.nectux.mizan.hyban.rh.formation.service;

import com.nectux.mizan.hyban.rh.formation.dto.FormationDTO;
import org.springframework.data.domain.Pageable;

public interface FormationService {
	
	public FormationDTO save(Long id, Long idTheme, Long idDemandeFormation, Long idCabinetFormation, String intitule, String dateDebut, String dateFin, String datePrevue, String lieu, int participant, String planFormation, String objectif, String commentaire);
	
	public FormationDTO delete(Long id);
	
	public FormationDTO findFormation(Long id);
	
	public FormationDTO findFormations();
	
	public int count();
	
	public FormationDTO loadFormations(Pageable pageable);
	
	public FormationDTO loadFormations(Pageable pageable, String intitule, String lieu, String themeIntitule, String demandeFormationObjet);

}
