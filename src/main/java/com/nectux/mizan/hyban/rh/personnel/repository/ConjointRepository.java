package com.nectux.mizan.hyban.rh.personnel.repository;

import java.util.Date;
import java.util.List;

import com.nectux.mizan.hyban.rh.personnel.entity.Conjoint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface ConjointRepository extends CrudRepository<Conjoint, Long> {
	
	public List<Conjoint> findAll();
	
	public Conjoint findByMatricule(String matricule);
	
	public Conjoint findByIdNotAndMatricule(Long id, String matricule);
	
	public Conjoint findByPersonnelIdNotAndMatricule(Long idPersonnel, String matricule);
	
	public Conjoint findByNom(String nom);
	
	public Conjoint findByIdNotAndNom(Long id, String nom);
	
	public Conjoint findByPersonnelIdNotAndNom(Long idPersonnel, String nom);
	
	public Page<Conjoint> findAll(Pageable pageable);
	
	public Page<Conjoint> findByMatriculeContainingOrNomContainingOrLieuNaissanceContainingOrTelephoneContainingOrLieuResidenceContainingOrEmailContainingOrSexeOrDateNaissance(Pageable pageable, String matricule, String nom, String lieuNaissance, String telephone, String lieuResidence, String email, char sexe, Date dateNaissance);
	
	public Page<Conjoint> findByPersonnelId(Pageable pageable, Long idPersonnel);
	
	public Page<Conjoint> findByPersonnelIdAndMatriculeContainingOrNomContainingOrLieuNaissanceContainingOrTelephoneContainingOrLieuResidenceContainingOrEmailContainingOrSexeOrDateNaissance(Pageable pageable, Long idPersonnel, String matricule, String nom, String lieuNaissance, String telephone, String lieuResidence, String email, char sexe, Date dateNaissance);

	public List<Conjoint> findByPersonnelId(Long idPersonnel);

	public Conjoint findByActifTrueAndPersonnelId(Long personneId);

}
