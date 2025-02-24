package com.nectux.mizan.hyban.parametrages.service;

import org.springframework.data.domain.Pageable;

import com.nectux.mizan.hyban.parametrages.dto.TypeDocumentDTO;

public interface TypeDocumentService {
	
	public TypeDocumentDTO save(Long id, String libelle);
	
	public TypeDocumentDTO delete(Long id);
	
	public TypeDocumentDTO findTypeDocument(Long id);
	
	public TypeDocumentDTO findTypesDocumments();
	
	public int count();
	
	public TypeDocumentDTO loadTypesDocuments(Pageable pageable);
	
	public TypeDocumentDTO loadTypesDocuments(Pageable pageable, String search);

}
