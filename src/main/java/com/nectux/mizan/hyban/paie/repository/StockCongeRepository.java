package com.nectux.mizan.hyban.paie.repository;

import java.util.List;

import com.nectux.mizan.hyban.paie.entity.StockConge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface StockCongeRepository extends CrudRepository<StockConge, Long> {
	
	public List<StockConge> findAll();
	
	public Page<StockConge> findAll(Pageable pageable);

	public List<StockConge> findByCongeId(Long idConge);

	public List<StockConge> findByCongeContratPersonnelPersonnelId(Long idPersonnel);

}
