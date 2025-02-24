package com.nectux.mizan.hyban.parametrages.service;

import java.util.List;

import com.nectux.mizan.hyban.parametrages.dto.RubriqueDTO;
import com.nectux.mizan.hyban.parametrages.entity.Rubrique;

import org.springframework.data.domain.Pageable;

public interface RubriqueService {
	
	public RubriqueDTO save(Long id, String libelle, Integer etatImposition, Double taux, Double mtexedent, Boolean active,Boolean permanent);
	
	public RubriqueDTO delete(Long id);
	
	public RubriqueDTO findRubrique(Long id);
	
	public List<Rubrique> getRubriques();
	
	public List<Rubrique> getRubriquesActives();
	public List<Rubrique> getRubriquesActivesType(Integer etatImp);


	//public List<Rubrique> getBanquesEntprise();
	
	public RubriqueDTO findRubriques();
	
	public Rubrique findRubriqueID(Long id);
	
	public int count();
	
	public RubriqueDTO loadRubriques(Pageable pageable);
	
	public RubriqueDTO loadRubriques(Pageable pageable, String search);

}
