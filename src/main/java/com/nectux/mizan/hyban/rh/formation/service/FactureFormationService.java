package com.nectux.mizan.hyban.rh.formation.service;

import com.nectux.mizan.hyban.rh.formation.dto.FactureFormationDTO;
import org.springframework.data.domain.Pageable;

public interface FactureFormationService {
	
	public FactureFormationDTO save(Long id, Long idCabinet, Long idFormation, String reference, String emission, Double montant, Boolean statut);
	
	public FactureFormationDTO delete(Long id);
	
	public FactureFormationDTO findFactureFormation(Long id);
	
	public FactureFormationDTO findFactureFormationByFormation(Long idFormation);
	
	public FactureFormationDTO findFactureFormations();
	
	public int count();
	
	public FactureFormationDTO loadFactureFormations(Pageable pageable);
	
	public FactureFormationDTO loadFactureFormations(Pageable pageable, String reference, String nom, String intitule);

}
