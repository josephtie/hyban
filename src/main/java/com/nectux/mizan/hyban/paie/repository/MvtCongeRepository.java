package com.nectux.mizan.hyban.paie.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.nectux.mizan.hyban.paie.entity.MvtConge;

public interface MvtCongeRepository extends CrudRepository<MvtConge, Long> {
	
	public List<MvtConge> findAll();
	
	public Page<MvtConge> findAll(Pageable pageable);

	public List<MvtConge> findByPersonnelId(Long idConge);

	//public List<MvtConge> findByCongeContratPersonnelPersonnelId(Long idPersonnel);

}
