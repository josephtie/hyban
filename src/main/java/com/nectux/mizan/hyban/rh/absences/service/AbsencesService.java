package com.nectux.mizan.hyban.rh.absences.service;

import java.util.List;

import com.nectux.mizan.hyban.rh.absences.dto.AbsencesDTO;
import com.nectux.mizan.hyban.rh.absences.entity.Absences;
import org.springframework.data.domain.Pageable;


public interface AbsencesService {
	
	public AbsencesDTO save(Long id, String faute,String type, String commentaire);
	
	public Absences saver(String faute, String commentaire);
	
	public AbsencesDTO delete(Long id);
	
	public AbsencesDTO findAbsence(Long id);
	
	public AbsencesDTO findAbsences();
	
	public List<Absences> getAbsences();
	
	public int count();
	
	public AbsencesDTO loadAbsences(Pageable pageable);
	
	public AbsencesDTO loadAbsences(Pageable pageable, String description);

}
