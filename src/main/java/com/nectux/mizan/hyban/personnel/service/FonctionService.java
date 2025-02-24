package com.nectux.mizan.hyban.personnel.service;

import org.springframework.data.domain.Pageable;

import com.nectux.mizan.hyban.personnel.dto.FonctionDTO;
import com.nectux.mizan.hyban.personnel.entity.Fonction;

public interface FonctionService {
	
	public Fonction save(Fonction fonction);
	
	public FonctionDTO save(Long id, String libelle);
	
	public Boolean delete(Long id);
	
	public Fonction findFonction(Long id);
	
	public Fonction findByLibelle(String libelle);
	
	public java.util.List<Fonction> findFonctions();
	
	public int count();
	
	public FonctionDTO loadFonction(Pageable pageable);
	
	public FonctionDTO loadFonction(Pageable pageable, String search);

}
