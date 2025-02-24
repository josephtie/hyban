package com.nectux.mizan.hyban.rh.carriere.service;

import java.util.List;

import com.nectux.mizan.hyban.rh.carriere.dto.PosteDTO;
import com.nectux.mizan.hyban.rh.carriere.entity.Poste;
import org.springframework.data.domain.Pageable;

public interface PosteService {
	
	public PosteDTO save(Long id, String libelle, String description);
	
	public PosteDTO delete(Long id);
	
	public PosteDTO findPoste(Long id);
	
	public PosteDTO findPostes();
	
	public List<Poste> getPostes();
	
	public int count();
	
	public PosteDTO loadPostes(Pageable pageable);
	
	public PosteDTO loadPostes(Pageable pageable, String libelle, String description);

}
