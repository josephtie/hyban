package com.nectux.mizan.hyban.rh.personnel.service;

import java.util.Date;

import org.springframework.data.domain.Pageable;

import com.nectux.mizan.hyban.rh.personnel.dto.ConjointDTO;

public interface ConjointService {
	
	public ConjointDTO save(Long id, Long idPersonnel, String matricule, String nom, Date dateNaissance, String lieuNaissance, String lieuResidence, String telephone, String email, char sexe);
	
	public ConjointDTO enable(Long id, Long idPersonnel);
	
	public ConjointDTO disable(Long id);
	
	public ConjointDTO delete(Long id);
	
	public ConjointDTO findConjoint(Long id);
	
	public ConjointDTO findConjoints();
	
	public ConjointDTO findConjointsByPersonnel(Long idPersonnel);
	
	public int count();
	
	public ConjointDTO loadConjoints(Pageable pageable);

}
