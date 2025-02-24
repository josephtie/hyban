package com.nectux.mizan.hyban.rh.formation.service;

import com.nectux.mizan.hyban.rh.formation.dto.DemandeFormationDTO;
import org.springframework.data.domain.Pageable;

public interface DemandeFormationService {
	
	public DemandeFormationDTO save(Long id, Long idService, Long idAnnee, String objet, String commentaire, String dateDemande);

	public DemandeFormationDTO delete(Long id);

	public DemandeFormationDTO findDemandeFormation(Long id);

	public DemandeFormationDTO findDemandeFormations();
	public DemandeFormationDTO findDemandeFormationsValiid();

	public int count();

	public DemandeFormationDTO loadDemandeFormations(Pageable pageable);

	public DemandeFormationDTO loadDemandeFormations(Pageable pageable, String objet, String serviceLibelle, String commentaire);

	public  DemandeFormationDTO findDemandeFormationValide(Long id, String dateValide, Integer etat);

	public DemandeFormationDTO loadDemandeFormationsValid(Pageable pageRequest, Integer etat);
	public DemandeFormationDTO loadDemandeFormationsValid(Pageable pageable, Integer etat, String objet, String serviceLibelle, String commentaire);
}
