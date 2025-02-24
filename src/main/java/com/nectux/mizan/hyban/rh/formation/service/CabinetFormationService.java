package com.nectux.mizan.hyban.rh.formation.service;

import com.nectux.mizan.hyban.rh.formation.dto.CabinetFormationDTO;
import org.springframework.data.domain.Pageable;

public interface CabinetFormationService {
	
	public CabinetFormationDTO save(Long id, String nom, String adresse, String email, String telephone);
	
	public CabinetFormationDTO delete(Long id);
	
	public CabinetFormationDTO findCabinetFormation(Long id);
	
	public CabinetFormationDTO findCabinetFormations();
	
	public int count();
	
	public CabinetFormationDTO loadCabinetFormations(Pageable pageable);
	
	public CabinetFormationDTO loadCabinetFormations(Pageable pageable, String nom, String adresse, String email, String telephone);

}
