package com.nectux.mizan.hyban.rh.carriere.repository;

import java.util.List;

import com.nectux.mizan.hyban.rh.carriere.entity.Promotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface PromotionRepository extends CrudRepository<Promotion, Long> {
	
	public List<Promotion> findAll();
	
	public Promotion findByLibelle(String libelle);
	
	public int countByLibelle(String libelle);

	public Promotion findByIdNotAndLibelle(Long id, String libelle);
	
	public Page<Promotion> findAll(Pageable pageable);
	
	public Page<Promotion> findByLibelleContainingOrDescriptionContaining(Pageable pageable, String libelle, String description);

}
