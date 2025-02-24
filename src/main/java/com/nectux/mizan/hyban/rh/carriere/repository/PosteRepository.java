package com.nectux.mizan.hyban.rh.carriere.repository;

import java.util.List;

import com.nectux.mizan.hyban.rh.carriere.entity.Poste;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface PosteRepository extends CrudRepository<Poste, Long> {
	
	public List<Poste> findAll();
	
	public Poste findByLibelle(String libelle);
	
	public int countByLibelle(String libelle);

	public Poste findByIdNotAndLibelle(Long id, String libelle);
	
	public Page<Poste> findAll(Pageable pageable);
	
	public Page<Poste> findByLibelleContainingOrDescriptionContaining(Pageable pageable, String libelle, String description);
}
