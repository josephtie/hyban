package com.nectux.mizan.hyban.rh.carriere.service;

import org.springframework.data.domain.Pageable;

import com.nectux.mizan.hyban.rh.carriere.dto.AffectationDTO;

public interface AffectationService {
	
	public AffectationDTO save(Long id, Long idPersonnel, Long idPoste, Long idSite,Boolean present,String dateDebut, String dateFin, String observation);
	public AffectationDTO saveNew(Long id, Long idPersonnel, Long idPoste, Long idSite,Boolean present,String dateDebut, String dateFin, String observation);

	public AffectationDTO delete(Long id);
	
	public AffectationDTO findAffectation(Long id);
	
	public AffectationDTO findAffectations();
	
	public AffectationDTO findAffectationsByPoste(Long idPoste);
	
	public AffectationDTO findAffectationsByPersonnel(Long idPersonnel);
	
	public int count();
	
	public AffectationDTO loadAffectations(Pageable pageable);
	
	public AffectationDTO loadAffectations(Pageable pageable, String nom, String prenom, String poste);

}
