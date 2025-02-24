package com.nectux.mizan.hyban.rh.personnel.repository;

import java.sql.Date;
import java.util.List;

import com.nectux.mizan.hyban.rh.personnel.entity.Pointage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface PointageRepository extends CrudRepository<Pointage, Long> {
	
	public List<Pointage> findAll();
	
	public List<Pointage> findByPersonnelId(Long idPersonnel);
	
	public Pointage findByPersonnelIdAndDatePointage(Long idPersonnel,java.util.Date datePointage);
	
	public Page<Pointage> findAll(Pageable pageable);
	
	public Page<Pointage> findByPersonnelId(Pageable pageable, Long idPersonnel);
	
	
	public Page<Pointage> findByPersonnelIdAndDatePointageBetween(Pageable pageable, Long idPersonnel, Date  ddep, Date dfin);
	
	public Page<Pointage> findByDatePointageBetween(Pageable pageable, Date  ddep, Date dfin);
	
	public Page<Pointage> findByPersonnelNomContainingOrPersonnelPrenomContaining(Pageable pageable, String nom, String prenom);

}
