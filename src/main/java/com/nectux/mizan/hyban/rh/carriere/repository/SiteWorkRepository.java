package com.nectux.mizan.hyban.rh.carriere.repository;

import com.nectux.mizan.hyban.rh.carriere.entity.Poste;
import com.nectux.mizan.hyban.rh.carriere.entity.Site;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SiteWorkRepository extends CrudRepository<Site, Long> {
	
	public List<Site> findAll();
	
	public Site findByLibelle(String libelle);
	
	public int countByLibelle(String libelle);

	public Poste findByIdNotAndLibelle(Long id, String libelle);
	
	public Page<Site> findAll(Pageable pageable);
	
	public Page<Site> findByLibelleContaining(Pageable pageable, String libelle);
}
