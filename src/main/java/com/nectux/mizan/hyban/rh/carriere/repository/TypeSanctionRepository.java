package com.nectux.mizan.hyban.rh.carriere.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.nectux.mizan.hyban.rh.carriere.entity.TypeSanction;

public interface TypeSanctionRepository extends CrudRepository<TypeSanction, Long> {
	
	public List<TypeSanction> findAll();
	
	public TypeSanction findByLibelle(String libelle);
	
	public int countByLibelle(String libelle);

	public TypeSanction findByIdNotAndLibelle(Long id, String libelle);
	
	public Page<TypeSanction> findAll(Pageable pageable);
	
	public Page<TypeSanction> findByLibelleContainingOrDescriptionContaining(Pageable pageable, String libelle, String description);
}
