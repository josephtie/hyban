package com.nectux.mizan.hyban.parametrages.repository;

import com.nectux.mizan.hyban.parametrages.entity.TypeContrat;
import org.springframework.data.repository.CrudRepository;

public interface TypeContratRepository extends CrudRepository<TypeContrat, Long> {
	
	public java.util.List<TypeContrat> findAll();

	public TypeContrat findByLibelle(String libelle);
}
