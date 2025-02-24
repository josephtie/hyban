package com.nectux.mizan.hyban.parametrages.repository;

import com.nectux.mizan.hyban.parametrages.entity.TypeService;
import org.springframework.data.repository.CrudRepository;

public interface TypeServiceRepository extends CrudRepository<TypeService, Long> {
	
	public java.util.List<TypeService> findAll();

	public TypeService findByLibelle(String libelle);
}
