package com.nectux.mizan.hyban.rh.carriere.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.nectux.mizan.hyban.rh.carriere.entity.Affectation;

public interface AffectationRepository extends CrudRepository<Affectation, Long> {
	
	public List<Affectation> findAll();
	
	public List<Affectation> findByPosteId(Long idPoste);
	
	public List<Affectation> findByPersonnelId(Long idPoste);
	
	public Affectation findByPersonnelIdAndPosteIdAndDateDebutAndDateFin(Long idPersonnel, Long idPoste, Date dateDebut, Date dateFin);
	
	public int countByPersonnelIdAndPosteIdAndDateDebutAndDateFin(Long idPersonnel, Long idPoste, Date dateDebut, Date dateFin);
	
	public Page<Affectation> findAll(Pageable pageable);
	
	public Page<Affectation> findByPersonnelNomContainingOrPersonnelPrenomContainingOrPosteLibelleContaining(Pageable pageable, String nom, String prenom, String poste);

}
