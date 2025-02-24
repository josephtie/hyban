package com.nectux.mizan.hyban.rh.absences.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.nectux.mizan.hyban.rh.absences.entity.AbsencesPersonnel;

public interface AbsencesPersonnelRepository extends CrudRepository<AbsencesPersonnel, Long> {
	
	public List<AbsencesPersonnel> findAll();
	
	public List<AbsencesPersonnel> findByAbsencesId(Long idSanction);
	
	public List<AbsencesPersonnel> findByPersonnelId(Long idPersonnel);
	
	public AbsencesPersonnel findByPersonnelIdAndAbsencesIdAndDateDebutAndDateRet(Long idPersonnel, Long idAbsences, Date dateDebut, Date dateFin);
	
	public int countByPersonnelIdAndAbsencesIdAndDateDebutAndDateRet(Long idPersonnel, Long idPoste, Date dateDebut, Date dateFin);
	
	public Page<AbsencesPersonnel> findAll(Pageable pageable);
	
	public Page<AbsencesPersonnel> findByPersonnelNomContainingOrPersonnelPrenomContainingOrAbsencesFauteContaining(Pageable pageable, String nom, String prenom, String faute, String typeSanction);
	
	

}
