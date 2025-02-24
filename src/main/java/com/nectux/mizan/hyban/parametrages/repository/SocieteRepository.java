package com.nectux.mizan.hyban.parametrages.repository;

import com.nectux.mizan.hyban.parametrages.entity.Societe;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface SocieteRepository extends CrudRepository<Societe, Long> {
	
	
	
	public java.util.List<Societe> findAll();
	
	public Page<Societe> findAll(Pageable pageable);
	public Societe findByRaisonsoc(String mois);
	

	//public List<Mois> findByMoisLike(String search);

	//public Page<Mois> findByMoisLike(Pageable pageable, String search);

}
