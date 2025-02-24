package com.nectux.mizan.hyban.personnel.repository;

import com.nectux.mizan.hyban.personnel.entity.Nationnalite;

import org.springframework.data.repository.CrudRepository;

public interface NationnaliteRepository extends CrudRepository<Nationnalite, Long> {
	
	public java.util.List<Nationnalite> findAll();

	public Nationnalite findByLibelle(String libelle);
}
