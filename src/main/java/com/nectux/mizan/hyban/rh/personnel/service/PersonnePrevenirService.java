package com.nectux.mizan.hyban.rh.personnel.service;

import org.springframework.data.domain.Pageable;

import com.nectux.mizan.hyban.rh.personnel.dto.PersonnePrevenirDTO;

public interface PersonnePrevenirService {
	
	public PersonnePrevenirDTO save(Long id, Long idPersonnel, String filiation, String nom, String telephone, String email, char sexe);
	
	public PersonnePrevenirDTO enable(Long id, Long idPersonnel);
	
	public PersonnePrevenirDTO disable(Long id);
	
	public PersonnePrevenirDTO delete(Long id);
	
	public PersonnePrevenirDTO findPersonnePrevenir(Long id);
	
	public PersonnePrevenirDTO findPersonnesPrevenir();
	
	public PersonnePrevenirDTO findPersonnesPrevenirByPersonnel(Long idPersonnel);
	
	public int count();
	
	public PersonnePrevenirDTO loadPersonnesPrevenir(Pageable pageable);

}
