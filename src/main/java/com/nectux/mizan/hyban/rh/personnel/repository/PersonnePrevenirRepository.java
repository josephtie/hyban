package com.nectux.mizan.hyban.rh.personnel.repository;

import java.util.List;

import com.nectux.mizan.hyban.rh.personnel.entity.PersonnePrevenir;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface PersonnePrevenirRepository extends CrudRepository<PersonnePrevenir, Long> {
	
	public List<PersonnePrevenir> findAll();
	
	public Page<PersonnePrevenir> findAll(Pageable pageable);
	
	public Page<PersonnePrevenir> findByNomContainingOrTelephoneContainingOrEmailContainingOrSexe(Pageable pageable, String nom, String telephone, String email, char sexe);
	
	public Page<PersonnePrevenir> findByPersonnelId(Pageable pageable, Long idPersonnel);
	
	public Page<PersonnePrevenir> findByPersonnelIdAndNomContainingOrTelephoneContainingOrEmailContainingOrSexe(Pageable pageable, Long idPersonnel, String nom, String lieuNaissance, String telephone, String email, char sexe);

	public List<PersonnePrevenir> findByPersonnelId(Long idPersonnel);

	public PersonnePrevenir findByActifTrueAndPersonnelId(Long idPersonnel);

}
