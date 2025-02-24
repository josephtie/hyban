package com.nectux.mizan.hyban.paie.service;

import org.springframework.data.domain.Pageable;

import com.nectux.mizan.hyban.paie.dto.MvtCongeDTO;
import com.nectux.mizan.hyban.paie.entity.MvtConge;

public interface MvtCongeService {
	
	public MvtConge save(MvtConge stockConge);
	
	public MvtCongeDTO save(Long id, Long idConge, String dateDepart, String dateRetour, Double montantVerse);
	
	public MvtCongeDTO delete(Long id);
	
	public MvtCongeDTO findMvtConge(Long id);
	
	public MvtCongeDTO findMvtCongePrisMemDate(String dateDepart,String dateRetour);
	
	public MvtCongeDTO findMvtConges();
	
	public MvtCongeDTO findMvtCongesByConge(Long idConge);
	
	public MvtCongeDTO findMvtCongesByPersonnel(Long idPersonnel);
	
	public int count();
	
	public MvtCongeDTO loadMvtConges(Pageable pageable);

}
