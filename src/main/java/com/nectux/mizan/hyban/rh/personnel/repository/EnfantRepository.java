package com.nectux.mizan.hyban.rh.personnel.repository;

import java.util.Date;
import java.util.List;

import com.nectux.mizan.hyban.rh.personnel.entity.Enfant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface EnfantRepository extends CrudRepository<Enfant, Long> {
	
	public List<Enfant> findAll();
	
	public Enfant findByMatricule(String matricule);
	
	public Enfant findByIdNotAndMatricule(Long id, String matricule);
	
	public Enfant findByPersonnelIdNotAndMatricule(Long idPersonnel, String matricule);
	
	public Enfant findByNom(String nom);
	
	public Enfant findByIdAndNom(Long id, String nom);
	
	public Enfant findByPersonnelIdNotAndNom(Long idPersonnel, String nom);
	
	public Enfant findByPersonnelIdAndNom(Long idPersonnel, String nom);
	
	public Page<Enfant> findAll(Pageable pageable);
	
	public Page<Enfant> findByMatriculeContainingOrNomContainingOrLieuNaissanceContainingOrSexeOrDateNaissance(Pageable pageable, String matricule, String nom, String lieuNaissance, char sexe, Date dateNaissance);
	
	public Page<Enfant> findByPersonnelId(Pageable pageable, Long idPersonnel);
	
	public Page<Enfant> findByPersonnelIdAndMatriculeContainingOrNomContainingOrLieuNaissanceContainingOrSexeOrDateNaissance(Pageable pageable, Long idPersonnel, String matricule, String nom, String lieuNaissance, char sexe, Date dateNaissance);

	public List<Enfant> findByPersonnelId(Long idPersonnel);

}
