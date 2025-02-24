package com.nectux.mizan.hyban.paie.repository;

import com.nectux.mizan.hyban.paie.entity.Pret;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface PretRepository extends CrudRepository<Pret, Long> {
	
	
	
	public java.util.List<Pret> findAll();
	
	public Page<Pret> findAll(Pageable pageable);
	
	

}
