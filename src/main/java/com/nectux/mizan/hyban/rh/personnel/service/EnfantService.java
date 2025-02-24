package com.nectux.mizan.hyban.rh.personnel.service;

import java.util.Date;

import org.springframework.data.domain.Pageable;
import com.nectux.mizan.hyban.rh.personnel.dto.EnfantDTO;

public interface EnfantService {
	
	public EnfantDTO save(Long id, Long idPersonnel, String matricule, String nom, Date dateNaissance, String lieuNaissance, char sexe);
	
	public EnfantDTO delete(Long id);
	
	public EnfantDTO findEnfant(Long id);
	
	public EnfantDTO findEnfants();
	
	public EnfantDTO findEnfantsByPersonnel(Long idPersonnel);
	
	public int count();
	
	public EnfantDTO loadEnfants(Pageable pageable);
	
	// public EnfantDTO loadEnfants(Pageable pageable, String search);

}
