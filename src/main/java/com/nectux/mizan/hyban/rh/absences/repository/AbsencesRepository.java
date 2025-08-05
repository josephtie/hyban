package com.nectux.mizan.hyban.rh.absences.repository;

import java.util.List;

import com.nectux.mizan.hyban.rh.absences.entity.Absences;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;


public interface AbsencesRepository extends CrudRepository<Absences, Long> , JpaSpecificationExecutor<Absences> {
	
	public List<Absences> findAll();
	


	public Absences findByFaute(String faute);

	
	public Page<Absences> findAll(Pageable pageable);
	
	public Page<Absences> findByFauteContaining(Pageable pageable, String faute);

}
