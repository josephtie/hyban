package com.nectux.mizan.hyban.rh.formation.repository;

import com.nectux.mizan.hyban.rh.formation.entity.Theme;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ThemeRepository extends CrudRepository<Theme, Long> {
	
	public List<Theme> findAll();
	
	public Theme findByIntitule(String intitule);
	
	public int countByIntitule(String intitule);

	public Theme findByIdNotAndIntitule(Long id, String intitule);
	
	public Page<Theme> findAll(Pageable pageable);
	
	public Page<Theme> findByIntituleContainingOrDescriptionContaining(Pageable pageable, String intitule, String description);

}
