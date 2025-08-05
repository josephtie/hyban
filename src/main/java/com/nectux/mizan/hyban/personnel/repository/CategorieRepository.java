package com.nectux.mizan.hyban.personnel.repository;

import com.nectux.mizan.hyban.personnel.entity.Categorie;

import com.nectux.mizan.hyban.rh.absences.entity.Absences;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface CategorieRepository extends CrudRepository<Categorie, Long> , JpaSpecificationExecutor<Categorie> {
	
	public java.util.List<Categorie> findAll();
	
	public Page<Categorie> findAll(Pageable pageable);

	public Page<Categorie> findByLibelleLike(Pageable pageable, String libelle);

	public Categorie findByLibelle(String libelle);
}
