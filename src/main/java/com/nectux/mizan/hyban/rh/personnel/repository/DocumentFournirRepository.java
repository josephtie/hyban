package com.nectux.mizan.hyban.rh.personnel.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.nectux.mizan.hyban.rh.personnel.entity.DocumentFournir;

public interface DocumentFournirRepository extends CrudRepository<DocumentFournir, Long> {
	
	public List<DocumentFournir> findAll();
	
	public Page<DocumentFournir> findAll(Pageable pageable);
	
	public Page<DocumentFournir> findByNumeroPieceContainingOrLieuDelivranceContainingOrTypeDocumentLibelleContaining(Pageable pageable, String numeroPiece, String leuDelivrance, String typeDocumentLibelle);
	
	public Page<DocumentFournir> findByPersonnelId(Pageable pageable, Long idPersonnel);
	
	public Page<DocumentFournir> findByPersonnelIdAndNumeroPieceContainingOrLieuDelivranceContainingOrTypeDocumentLibelleContaining(Pageable pageable, Long idPersonnel, String numeroPiece, String leuDelivrance, String typeDocumentLibelle);

}
