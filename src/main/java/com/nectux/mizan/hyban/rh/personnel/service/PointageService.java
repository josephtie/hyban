package com.nectux.mizan.hyban.rh.personnel.service;

import java.util.List;

import com.nectux.mizan.hyban.rh.personnel.dto.PointageDTO;
import org.springframework.data.domain.Pageable;

public interface PointageService {
	
	public PointageDTO save(Long id, Long idPersonnel, String datePointage, String heureArrivee, String heureDepart, String heurePause, String heureReprise, String description);
	
	public PointageDTO delete(Long id);
	
	public PointageDTO findPointage(Long id);
	
	public PointageDTO findPointages();
	
	public PointageDTO findPointagesByPersonnel(Long idPersonnel);
	
	public PointageDTO findPointagesByPersonnelsAndDate(List<Long> listPersonnel, String date);
	
	public PointageDTO findPointagesByPersonnelAndDatePointage(Long idPersonnel, String datePointage);
	
	
	public PointageDTO findPointagesByPersonnelAndDatePointageBetween(Pageable pageable,Long idPersonnel, String datePointage, String datePointage1);
	
	
	public PointageDTO findPointagesByDatePointageBetween(Pageable pageable,String datePointage, String datePointage1);
	
	public int count();
	
	public PointageDTO loadPointages(Pageable pageable);
	
	public PointageDTO loadPointages(Pageable pageable, String nom, String prenom);

}
