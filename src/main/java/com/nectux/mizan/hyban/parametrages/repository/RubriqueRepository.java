package com.nectux.mizan.hyban.parametrages.repository;

import java.util.List;

import com.nectux.mizan.hyban.parametrages.entity.Rubrique;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface RubriqueRepository extends CrudRepository<Rubrique, Long> {
	
	public List<Rubrique> findAll();
	
	public List<Rubrique> findByActiveTrue();
	
	public Rubrique findByLibelle(String libelle);
	

	
	public Page<Rubrique> findAll(Pageable pageable);
	
	public Page<Rubrique> findByLibelleIgnoreCaseContaining(Pageable pageable, String libelle);

    List<Rubrique> findByActiveTrueAndEtatImposition(Integer etatImp);
}
