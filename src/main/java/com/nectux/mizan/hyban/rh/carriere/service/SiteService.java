package com.nectux.mizan.hyban.rh.carriere.service;

import com.nectux.mizan.hyban.rh.carriere.dto.PosteDTO;
import com.nectux.mizan.hyban.rh.carriere.dto.SiteWorkDTO;
import com.nectux.mizan.hyban.rh.carriere.entity.Poste;
import com.nectux.mizan.hyban.rh.carriere.entity.Site;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SiteService {
	
	public SiteWorkDTO save(Long id, String libelle, String description);
	
	public SiteWorkDTO delete(Long id);
	
	public SiteWorkDTO findPoste(Long id);
	

	
	public List<Site> getPostes();
	
	public int count();

	public SiteWorkDTO loadPostes(Pageable pageable);
	
	public SiteWorkDTO loadPostes(Pageable pageable, String libelle, String description);

}
