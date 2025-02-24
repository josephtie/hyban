package com.nectux.mizan.hyban.personnel.service;

import com.nectux.mizan.hyban.personnel.entity.Categorie;
import org.springframework.data.domain.Pageable;

import com.nectux.mizan.hyban.personnel.dto.CategorieDTO;

public interface CategorieService {
	
	public Categorie save(Categorie categorie);
	
	public CategorieDTO save(Long id, String libelle, Double salaireDeBase, Double indemniteLogement);
	
	public Boolean delete(Long id);
	
	public Categorie findCategorie(Long id);
	
	public Categorie findByLibelle(String libelle);
	
	public java.util.List<Categorie> findCategories();
	
	public int count();
	
	public CategorieDTO loadCategorie(Pageable pageable);
	
	public CategorieDTO loadCategorie(Pageable pageable, String search);

}
