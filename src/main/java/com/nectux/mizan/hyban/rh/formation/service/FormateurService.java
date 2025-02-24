package com.nectux.mizan.hyban.rh.formation.service;

import com.nectux.mizan.hyban.rh.formation.dto.FormateurDTO;
import org.springframework.data.domain.Pageable;

public interface FormateurService {
	
	public FormateurDTO save(Long id, Long cabinetFormationId, String nom, String civilite, char sexe, int situationMatrimonial, String dateNaissance, String lieuNaissance, String fixe, String mobile);
	
	public FormateurDTO delete(Long id);
	
	public FormateurDTO findFormateur(Long id);
	
	public FormateurDTO findFormateurs();
	
	public int count();
	
	public FormateurDTO loadFormateurs(Pageable pageable);
	
	public FormateurDTO loadFormateurs(Pageable pageable, String nom, String civilite, String dateNaissance, String lieuNaissance, String fixe, String mobile, String nomCabinetFormation);

}
