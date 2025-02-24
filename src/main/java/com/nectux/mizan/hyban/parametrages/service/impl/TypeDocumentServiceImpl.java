package com.nectux.mizan.hyban.parametrages.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nectux.mizan.hyban.parametrages.dto.TypeDocumentDTO;
import com.nectux.mizan.hyban.parametrages.entity.TypeDocument;
import com.nectux.mizan.hyban.parametrages.repository.TypeDocumentRepository;
import com.nectux.mizan.hyban.parametrages.service.TypeDocumentService;
import com.nectux.mizan.hyban.utils.Erreur;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service("typeDocumentService")
public class TypeDocumentServiceImpl implements TypeDocumentService {
	
	@Autowired private TypeDocumentRepository typeDocumentRepository;
	
	private StringBuilder sb;
	private Erreur erreur;
	private List<Erreur> listErreur;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public TypeDocumentDTO save(Long id, String libelle) {
		// TODO Auto-generated method stub
		TypeDocumentDTO typeDocumentDTO = new TypeDocumentDTO();
		TypeDocument typeDocument;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		try{
			if(id != null){
				typeDocument = typeDocumentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
				
			} else {
				typeDocument = new TypeDocument();
			}
			typeDocument.setLibelle(libelle);
			
			if(typeDocument.getLibelle() == null || typeDocument.getLibelle().isEmpty()){
				sb = new StringBuilder();
				erreur.setCode(10);
				erreur.setDescription("contrainte d'integrite non null non respectee");
				sb.append("le champ libelle est obligatoire");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
			}
			
			if(typeDocument.getId() == null){
				if(typeDocumentRepository.findByLibelle(typeDocument.getLibelle()) != null){
					sb = new StringBuilder();
					erreur.setCode(11);
					erreur.setDescription("contrainte de doublon non respectee");
					sb.append(libelle).append(" existe deja");
					erreur.setMessage(sb.toString());
					listErreur.add(erreur);
				}
			} else{
				if(typeDocumentRepository.findByIdNotAndLibelle(typeDocument.getId(), typeDocument.getLibelle()) != null){
					sb = new StringBuilder();
					erreur.setCode(11);
					erreur.setDescription("contrainte de doublon non respectee");
					sb.append(libelle).append(" existe deja");
					erreur.setMessage(sb.toString());
					listErreur.add(erreur);
				}
			}
			
			if(listErreur.isEmpty()){
				typeDocument = typeDocumentRepository.save(typeDocument);
				sb = new StringBuilder();
				sb.append(typeDocument.getLibelle()).append(" enregistre avec succes");
				typeDocumentDTO.setResult(true);
				typeDocumentDTO.setStatus(true);
				typeDocumentDTO.setRow(typeDocument);
				typeDocumentDTO.setRows(null);
				typeDocumentDTO.setMessage(sb.toString());
				typeDocumentDTO.setTotal(0);
				typeDocumentDTO.setErrors(listErreur);
			} else {
				typeDocumentDTO.setResult(false);
				typeDocumentDTO.setStatus(false);
				typeDocumentDTO.setRow(null);
				typeDocumentDTO.setRows(null);
				typeDocumentDTO.setMessage("voir liste erreur");
				typeDocumentDTO.setTotal(0);
				typeDocumentDTO.setErrors(listErreur);
			}
			
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			typeDocumentDTO.setResult(false);
			typeDocumentDTO.setStatus(false);
			typeDocumentDTO.setRow(null);
			typeDocumentDTO.setRows(null);
			typeDocumentDTO.setMessage(ex.getMessage());
			typeDocumentDTO.setTotal(0);
			typeDocumentDTO.setErrors(listErreur);
		}
		return typeDocumentDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public TypeDocumentDTO delete(Long id) {
		// TODO Auto-generated method stub
		TypeDocumentDTO typeDocumentDTO = new TypeDocumentDTO();
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		try{
			TypeDocument typeDocument = typeDocumentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			if(typeDocument == null){
				sb = new StringBuilder();
				erreur.setCode(12);
				erreur.setDescription("type de document inexistant");
				sb.append("ce type de document est inexistant dans le systeme");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
				
				typeDocumentDTO.setResult(false);
				typeDocumentDTO.setStatus(false);
				typeDocumentDTO.setRow(null);
				typeDocumentDTO.setRows(null);
				typeDocumentDTO.setMessage("voir liste erreur");
				typeDocumentDTO.setTotal(0);
				typeDocumentDTO.setErrors(listErreur);
			} 
			
			if(listErreur.isEmpty()) {
				typeDocumentRepository.delete(typeDocument);
				sb = new StringBuilder();
				sb.append(typeDocument.getLibelle()).append(" supprime avec succes");
				typeDocumentDTO.setResult(true);
				typeDocumentDTO.setStatus(true);
				typeDocumentDTO.setRow(typeDocument);
				typeDocumentDTO.setRows(null);
				typeDocumentDTO.setMessage(sb.toString());
				typeDocumentDTO.setTotal(0);
				typeDocumentDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			typeDocumentDTO.setResult(false);
			typeDocumentDTO.setStatus(false);
			typeDocumentDTO.setRow(null);
			typeDocumentDTO.setRows(null);
			typeDocumentDTO.setMessage(ex.getMessage());
			typeDocumentDTO.setTotal(0);
			typeDocumentDTO.setErrors(listErreur);
		}
		return typeDocumentDTO;
	}

	@Override
	public TypeDocumentDTO findTypeDocument(Long id) {
		// TODO Auto-generated method stub
		TypeDocumentDTO typeDocumentDTO = new TypeDocumentDTO();
		TypeDocument typeDocument;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		try{
			typeDocument = typeDocumentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			if(typeDocument == null){
				typeDocumentDTO.setResult(true);
				typeDocumentDTO.setStatus(true);
				typeDocumentDTO.setRow(typeDocument);
				typeDocumentDTO.setRows(null);
				typeDocumentDTO.setMessage("objet inexistant dans le systeme");
				typeDocumentDTO.setTotal(1);
				typeDocumentDTO.setErrors(listErreur);
			} else {
				typeDocumentDTO.setResult(true);
				typeDocumentDTO.setStatus(true);
				typeDocumentDTO.setRow(typeDocument);
				typeDocumentDTO.setRows(null);
				typeDocumentDTO.setMessage("objet trouve avec succes");
				typeDocumentDTO.setTotal(1);
				typeDocumentDTO.setErrors(listErreur);
			}
		} catch (Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			typeDocumentDTO.setResult(true);
			typeDocumentDTO.setStatus(true);
			typeDocumentDTO.setRow(null);
			typeDocumentDTO.setRows(null);
			typeDocumentDTO.setMessage(ex.getMessage());
			typeDocumentDTO.setTotal(0);
			typeDocumentDTO.setErrors(listErreur);
		}
		return typeDocumentDTO;
	}

	@Override
	public TypeDocumentDTO findTypesDocumments() {
		// TODO Auto-generated method stub
		TypeDocumentDTO typeDocumentDTO = new TypeDocumentDTO();
		List<TypeDocument> listTypeDocument = new ArrayList<TypeDocument>();
		try{
			listTypeDocument = typeDocumentRepository.findAll();
			if(listTypeDocument == null){
				typeDocumentDTO.setResult(true);
				typeDocumentDTO.setStatus(true);
				typeDocumentDTO.setRow(null);
				typeDocumentDTO.setRows(new ArrayList<TypeDocument>());
				typeDocumentDTO.setMessage("aucun objet trouve");
				typeDocumentDTO.setTotal(0);
				typeDocumentDTO.setErrors(listErreur);
			} else {
				int i = listTypeDocument.size();
				sb = new StringBuilder();
				sb.append(i).append(" objet(s) trouve(s) avec succes");
				typeDocumentDTO.setResult(true);
				typeDocumentDTO.setStatus(true);
				typeDocumentDTO.setRow(null);
				typeDocumentDTO.setRows(listTypeDocument);
				typeDocumentDTO.setMessage(sb.toString());
				typeDocumentDTO.setTotal(i);
				typeDocumentDTO.setErrors(listErreur);
			}
		} catch (Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			
			typeDocumentDTO.setResult(false);
			typeDocumentDTO.setStatus(false);
			typeDocumentDTO.setRow(null);
			typeDocumentDTO.setRows(listTypeDocument);
			typeDocumentDTO.setMessage(ex.getMessage());
			typeDocumentDTO.setTotal(0);
			typeDocumentDTO.setErrors(listErreur);
		}
		return typeDocumentDTO;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) typeDocumentRepository.count();
	}

	@Override
	public TypeDocumentDTO loadTypesDocuments(Pageable pageable) {
		// TODO Auto-generated method stub
		TypeDocumentDTO typeDocumentDTO = new TypeDocumentDTO();
		Page<TypeDocument> page = typeDocumentRepository.findAll(pageable);
		typeDocumentDTO.setResult(true);
		typeDocumentDTO.setStatus(true);
		typeDocumentDTO.setRows(page.getContent());
		typeDocumentDTO.setTotal(page.getTotalElements());
		return typeDocumentDTO;
	}

	@Override
	public TypeDocumentDTO loadTypesDocuments(Pageable pageable, String search) {
		// TODO Auto-generated method stub
		TypeDocumentDTO typeDocumentDTO = new TypeDocumentDTO();
		Page<TypeDocument> page = typeDocumentRepository.findByLibelleLike(pageable, search);
		typeDocumentDTO.setResult(true);
		typeDocumentDTO.setStatus(true);
		typeDocumentDTO.setRows(page.getContent());
		typeDocumentDTO.setTotal(page.getTotalElements());
		return typeDocumentDTO;
	}

}
