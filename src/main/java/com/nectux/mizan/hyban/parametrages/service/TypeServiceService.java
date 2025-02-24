package com.nectux.mizan.hyban.parametrages.service;

import com.nectux.mizan.hyban.parametrages.entity.TypeService;

public interface TypeServiceService {
	
	public TypeService save(TypeService typeService);
	
	public Boolean delete(Long id);
	
	public TypeService findTypeService(Long id);
	
	public TypeService findByLibelle(String libelle);
	
	public java.util.List<TypeService> findTypeServices();
	
	public int count();

}
