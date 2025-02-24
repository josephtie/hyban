package com.nectux.mizan.hyban.paie.repository;

import com.nectux.mizan.hyban.paie.dto.PretPersonnelDTO;
import com.nectux.mizan.hyban.paie.entity.PretPersonnel;
import com.nectux.mizan.hyban.personnel.entity.Personnel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface PretPersonnelRepository extends CrudRepository<PretPersonnel, Long> {
	
	
	
	public java.util.List<PretPersonnel> findAll();
	
	public java.util.List<PretPersonnel> findByPersonnel(Personnel personnel);
	
	public PretPersonnelDTO findByPersonnelId(Long Idpers);
	
	public Page<PretPersonnel> findAll(Pageable pageable);
	
	public Page<PretPersonnel> findByPersonnelNomLike(Pageable pageable,String search);
	
	

}
