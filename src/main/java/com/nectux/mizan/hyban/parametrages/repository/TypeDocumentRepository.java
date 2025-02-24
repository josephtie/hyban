package com.nectux.mizan.hyban.parametrages.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.nectux.mizan.hyban.parametrages.entity.TypeDocument;

public interface TypeDocumentRepository extends CrudRepository<TypeDocument, Long> {
	
	public List<TypeDocument> findAll();
	
	public TypeDocument findByLibelle(String libelle);
	
	public TypeDocument findByIdNotAndLibelle(Long id, String libelle);
	
	public Page<TypeDocument> findAll(Pageable pageable);
	
	public Page<TypeDocument> findByLibelleLike(Pageable pageable, String libelle);

}
