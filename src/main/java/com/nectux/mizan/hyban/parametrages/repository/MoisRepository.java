package com.nectux.mizan.hyban.parametrages.repository;

import com.nectux.mizan.hyban.parametrages.entity.Mois;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface MoisRepository extends CrudRepository<Mois, Long> {
	
	
	
	public java.util.List<Mois> findAll();
	
	public Page<Mois> findAll(Pageable pageable);
	
	public Mois findByMois(String mois);
	

	//public List<Mois> findByMoisLike(String search);

	//public Page<Mois> findByMoisLike(Pageable pageable, String search);

}
