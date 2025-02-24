package com.nectux.mizan.hyban.rh.absences.service;

import com.nectux.mizan.hyban.rh.absences.dto.AbsencesPersonnelDTO;

import org.springframework.data.domain.Pageable;

public interface AbsencesPersonnelService {
	
	public AbsencesPersonnelDTO save(Long id, Long idPersonnel, Long idabsence, String dateDebut, String dateFin, Double tempabsence, Double jourabsence, String observation, Boolean justifier, int sanctSal);
	
	public AbsencesPersonnelDTO saver(Long id, Long idPersonnel, Long idabsence, String dateDebut, String dateFin,Double tempabsence,Double jourabsence, String observation,Boolean justifier,int sanctSal);
	
	public AbsencesPersonnelDTO delete(Long id);
	
	public AbsencesPersonnelDTO findAbsencesPersonnel(Long id);
	
	public AbsencesPersonnelDTO findAbsencesPersonnels();
	
	public AbsencesPersonnelDTO findAbsencesPersonnelsByAbsence(Long idPoste);
	
	public AbsencesPersonnelDTO findAbsencesPersonnelsByPersonnel(Long idPersonnel);
	
	public int count();
	
	public AbsencesPersonnelDTO loadAbsencesPersonnels(Pageable pageable);
	
	public AbsencesPersonnelDTO loadAbsencesPersonnels(Pageable pageable, String nom, String prenom, String faute, String typeSanction);

}
