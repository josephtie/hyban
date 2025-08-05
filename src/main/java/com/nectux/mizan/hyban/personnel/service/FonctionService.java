package com.nectux.mizan.hyban.personnel.service;

import com.nectux.mizan.hyban.paie.dto.BulletinPaieDTO;
import org.springframework.data.domain.Pageable;

import com.nectux.mizan.hyban.personnel.dto.FonctionDTO;
import com.nectux.mizan.hyban.personnel.entity.Fonction;

import java.util.Map;

public interface FonctionService {
	
	public Fonction save(Fonction fonction);
	
	public FonctionDTO save(Long id, String libelle);
	
	public Boolean delete(Long id);
	
	public Fonction findFonction(Long id);
	
	public Fonction findByLibelle(String libelle);
	
	public java.util.List<Fonction> findFonctions();
	
	public int count();

	FonctionDTO findAllfilter(Map<String,String> filters, Pageable pageable);
	public FonctionDTO loadFonction(Pageable pageable);
	
	public FonctionDTO loadFonction(Pageable pageable, String search);

}
