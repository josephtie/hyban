package com.nectux.mizan.hyban.rh.carriere.repository;

import java.util.List;

import com.nectux.mizan.hyban.rh.carriere.entity.Sanction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface SanctionRepository extends CrudRepository<Sanction, Long> {
	
	public List<Sanction> findAll();
	
	public Sanction findByTypeSanctionLibelle(String libelle);
	
	public int countByTypeSanctionLibelle(String libelle);

	public Sanction findByFaute(String faute);

	public Sanction findByIdNotAndFaute(Long id, String faute);

	public Sanction findByIdNotAndTypeSanctionLibelle(Long id, String libelle);
	
	public Page<Sanction> findAll(Pageable pageable);
	
	public Page<Sanction> findByTypeSanctionLibelleContainingAndFauteContaining(Pageable pageable, String typeSanction, String faute);

}
