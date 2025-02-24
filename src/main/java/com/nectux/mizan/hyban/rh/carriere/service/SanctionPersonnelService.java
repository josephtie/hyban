package com.nectux.mizan.hyban.rh.carriere.service;

import org.springframework.data.domain.Pageable;

import com.nectux.mizan.hyban.rh.carriere.dto.SanctionPersonnelDTO;

public interface SanctionPersonnelService {
	
	public SanctionPersonnelDTO save(Long id, Long idPersonnel, Long idSanction, String dateDebut, String dateFin, String observation);
	
	public SanctionPersonnelDTO save(Long id, Long idTypeSanction, Long idPersonnel, String faute, String commentaire, String dateDebut, String dateFin, String observation);
	
	public SanctionPersonnelDTO delete(Long id);
	
	public SanctionPersonnelDTO findSanctionPersonnel(Long id);
	
	public SanctionPersonnelDTO findSanctionPersonnels();
	
	public SanctionPersonnelDTO findSanctionPersonnelsBySanction(Long idPoste);
	
	public SanctionPersonnelDTO findSanctionPersonnelsByPersonnel(Long idPersonnel);
	
	public int count();
	
	public SanctionPersonnelDTO loadSanctionPersonnels(Pageable pageable);
	
	public SanctionPersonnelDTO loadSanctionPersonnels(Pageable pageable, String nom, String prenom, String faute, String typeSanction);

}
