package com.nectux.mizan.hyban.parametrages.service;

import com.nectux.mizan.hyban.parametrages.entity.TypeContrat;

public interface TypeContratService {
	
	public TypeContrat save(TypeContrat typeContrat);
	
	public Boolean delete(Long id);
	
	public TypeContrat findTypeContrat(Long id);
	
	public TypeContrat findByLibelle(String libelle);
	
	public java.util.List<TypeContrat> findTypeContrats();
	
	public int count();

}
