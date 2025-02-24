package com.nectux.mizan.hyban.rh.personnel.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nectux.mizan.hyban.rh.personnel.entity.Conjoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nectux.mizan.hyban.personnel.entity.Personnel;
import com.nectux.mizan.hyban.personnel.repository.PersonnelRepository;
import com.nectux.mizan.hyban.rh.personnel.dto.ConjointDTO;
import com.nectux.mizan.hyban.rh.personnel.repository.ConjointRepository;
import com.nectux.mizan.hyban.rh.personnel.service.ConjointService;
import com.nectux.mizan.hyban.utils.Erreur;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service("conjointService")
public class ConjointServiceImpl implements ConjointService {
	
	@Autowired private ConjointRepository conjointRepository;
	@Autowired private PersonnelRepository personneRepository;
	
	private StringBuilder sb;
	private Erreur erreur;
	private List<Erreur> listErreur;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ConjointDTO save(Long id, Long idPersonnel, String matricule, String nom, Date dateNaissance,
			String lieuNaissance, String lieuResidence, String telephone, String email, char sexe) {
		// TODO Auto-generated method stub
		ConjointDTO conjointDTO = new ConjointDTO();
		Conjoint conjoint;Personnel personnel=new Personnel();
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		try{
			 personnel = personneRepository.findById(idPersonnel).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			if(id != null){
				conjoint = conjointRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			} else {
				conjoint = new Conjoint();
				conjoint.setActif(true);
				Conjoint current = conjointRepository.findByActifTrueAndPersonnelId(idPersonnel);
				if(current != null){
					current.setActif(false);
					conjointRepository.save(current);
				}
			}
			conjoint.setMatricule(matricule);
			conjoint.setNom(nom);
			conjoint.setDateNaissance(dateNaissance);
			conjoint.setLieuNaissance(lieuNaissance);
			conjoint.setLieuResidence(lieuResidence);
			conjoint.setTelephone(telephone);
			conjoint.setEmail(email);
			conjoint.setSexe(sexe);
			conjoint.setPersonnel(personnel);
			
			if(listErreur.isEmpty()){
				
				conjoint = conjointRepository.save(conjoint);
				sb = new StringBuilder();
				sb.append(conjoint.getNom()).append(" enregistre avec succes");
				conjointDTO.setResult(true);
				conjointDTO.setStatus(true);
				conjointDTO.setRow(conjoint);
				conjointDTO.setRows(null);
				conjointDTO.setMessage(sb.toString());
				conjointDTO.setTotal(0);
				conjointDTO.setErrors(listErreur);
			} else {
				conjointDTO.setResult(false);
				conjointDTO.setStatus(false);
				conjointDTO.setRow(null);
				conjointDTO.setRows(null);
				conjointDTO.setMessage("voir liste erreur");
				conjointDTO.setTotal(0);
				conjointDTO.setErrors(listErreur);
			}
			
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			conjointDTO.setResult(false);
			conjointDTO.setStatus(false);
			conjointDTO.setRow(null);
			conjointDTO.setRows(null);
			conjointDTO.setMessage(ex.getMessage());
			conjointDTO.setTotal(0);
			conjointDTO.setErrors(listErreur);
		}
		return conjointDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ConjointDTO enable(Long id, Long idPersonnel) {
		// TODO Auto-generated method stub
		ConjointDTO conjointDTO = new ConjointDTO();
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		try{
			Conjoint conjoint = conjointRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			if(conjoint == null){
				sb = new StringBuilder();
				erreur.setCode(12);
				erreur.setDescription("conjoint inexistant");
				sb.append("ce conjoint est inexistant dans le systeme");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
				
				conjointDTO.setResult(false);
				conjointDTO.setStatus(false);
				conjointDTO.setRow(null);
				conjointDTO.setRows(null);
				conjointDTO.setMessage("voir liste erreur");
				conjointDTO.setTotal(0);
				conjointDTO.setErrors(listErreur);
			} 
			
			if(listErreur.isEmpty()) {
				Conjoint current = conjointRepository.findByActifTrueAndPersonnelId(idPersonnel);
				current.setActif(false);
				conjointRepository.save(current);
				
				conjoint.setActif(true);
				conjointRepository.save(conjoint);
				
				sb = new StringBuilder();
				sb.append(conjoint.getNom()).append(" active avec succes");
				conjointDTO.setResult(true);
				conjointDTO.setStatus(true);
				conjointDTO.setRow(conjoint);
				conjointDTO.setRows(null);
				conjointDTO.setMessage(sb.toString());
				conjointDTO.setTotal(0);
				conjointDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			conjointDTO.setResult(false);
			conjointDTO.setStatus(false);
			conjointDTO.setRow(null);
			conjointDTO.setRows(null);
			conjointDTO.setMessage(ex.getMessage());
			conjointDTO.setTotal(0);
			conjointDTO.setErrors(listErreur);
		}
		return conjointDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ConjointDTO disable(Long id) {
		// TODO Auto-generated method stub
		ConjointDTO conjointDTO = new ConjointDTO();
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		try{
			Conjoint conjoint = conjointRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			if(conjoint == null){
				sb = new StringBuilder();
				erreur.setCode(12);
				erreur.setDescription("conjoint inexistant");
				sb.append("ce conjoint est inexistant dans le systeme");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
				
				conjointDTO.setResult(false);
				conjointDTO.setStatus(false);
				conjointDTO.setRow(null);
				conjointDTO.setRows(null);
				conjointDTO.setMessage("voir liste erreur");
				conjointDTO.setTotal(0);
				conjointDTO.setErrors(listErreur);
			} 
			
			if(listErreur.isEmpty()) {
				conjoint.setActif(false);
				conjointRepository.save(conjoint);
				sb = new StringBuilder();
				sb.append(conjoint.getNom()).append(" desactive avec succes");
				conjointDTO.setResult(true);
				conjointDTO.setStatus(true);
				conjointDTO.setRow(conjoint);
				conjointDTO.setRows(null);
				conjointDTO.setMessage(sb.toString());
				conjointDTO.setTotal(0);
				conjointDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			conjointDTO.setResult(false);
			conjointDTO.setStatus(false);
			conjointDTO.setRow(null);
			conjointDTO.setRows(null);
			conjointDTO.setMessage(ex.getMessage());
			conjointDTO.setTotal(0);
			conjointDTO.setErrors(listErreur);
		}
		return conjointDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ConjointDTO delete(Long id) {
		// TODO Auto-generated method stub
		ConjointDTO conjointDTO = new ConjointDTO();
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		try{
			Conjoint conjoint = conjointRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			if(conjoint == null){
				sb = new StringBuilder();
				erreur.setCode(12);
				erreur.setDescription("conjoint inexistant");
				sb.append("ce conjoint est inexistant dans le systeme");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
				
				conjointDTO.setResult(false);
				conjointDTO.setStatus(false);
				conjointDTO.setRow(null);
				conjointDTO.setRows(null);
				conjointDTO.setMessage("voir liste erreur");
				conjointDTO.setTotal(0);
				conjointDTO.setErrors(listErreur);
			} 
			
			if(listErreur.isEmpty()) {
				conjointRepository.delete(conjoint);
				sb = new StringBuilder();
				sb.append(conjoint.getNom()).append(" supprime avec succes");
				conjointDTO.setResult(true);
				conjointDTO.setStatus(true);
				conjointDTO.setRow(conjoint);
				conjointDTO.setRows(null);
				conjointDTO.setMessage(sb.toString());
				conjointDTO.setTotal(0);
				conjointDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			conjointDTO.setResult(false);
			conjointDTO.setStatus(false);
			conjointDTO.setRow(null);
			conjointDTO.setRows(null);
			conjointDTO.setMessage(ex.getMessage());
			conjointDTO.setTotal(0);
			conjointDTO.setErrors(listErreur);
		}
		return conjointDTO;
	}

	@Override
	public ConjointDTO findConjoint(Long id) {
		// TODO Auto-generated method stub
		ConjointDTO conjointDTO = new ConjointDTO();
		Conjoint conjoint;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		try{
			conjoint = conjointRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			if(conjoint == null){
				conjointDTO.setResult(true);
				conjointDTO.setStatus(true);
				conjointDTO.setRow(conjoint);
				conjointDTO.setRows(null);
				conjointDTO.setMessage("cojoint inexistant dans le systeme");
				conjointDTO.setTotal(1);
				conjointDTO.setErrors(listErreur);
			} else {
				conjointDTO.setResult(true);
				conjointDTO.setStatus(true);
				conjointDTO.setRow(conjoint);
				conjointDTO.setRows(null);
				conjointDTO.setMessage("conjoint trouve avec succes");
				conjointDTO.setTotal(1);
				conjointDTO.setErrors(listErreur);
			}
		} catch (Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			conjointDTO.setResult(true);
			conjointDTO.setStatus(true);
			conjointDTO.setRow(null);
			conjointDTO.setRows(null);
			conjointDTO.setMessage(ex.getMessage());
			conjointDTO.setTotal(0);
			conjointDTO.setErrors(listErreur);
		}
		return conjointDTO;
	}

	@Override
	public ConjointDTO findConjoints() {
		// TODO Auto-generated method stub
		ConjointDTO conjointDTO = new ConjointDTO();
		List<Conjoint> listConjoint = new ArrayList<Conjoint>();
		try{
			listConjoint = conjointRepository.findAll();
			if(listConjoint == null){
				conjointDTO.setResult(true);
				conjointDTO.setStatus(true);
				conjointDTO.setRow(null);
				conjointDTO.setRows(new ArrayList<Conjoint>());
				conjointDTO.setMessage("aucun conjoint trouve");
				conjointDTO.setTotal(0);
				conjointDTO.setErrors(listErreur);
			} else {
				int i = listConjoint.size();
				sb = new StringBuilder();
				sb.append(i).append(" Conjoint(s) trouve(s) avec succes");
				conjointDTO.setResult(true);
				conjointDTO.setStatus(true);
				conjointDTO.setRow(null);
				conjointDTO.setRows(listConjoint);
				conjointDTO.setMessage(sb.toString());
				conjointDTO.setTotal(i);
				conjointDTO.setErrors(listErreur);
			}
		} catch (Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			
			conjointDTO.setResult(false);
			conjointDTO.setStatus(false);
			conjointDTO.setRow(null);
			conjointDTO.setRows(listConjoint);
			conjointDTO.setMessage(ex.getMessage());
			conjointDTO.setTotal(0);
			conjointDTO.setErrors(listErreur);
		}
		return conjointDTO;
	}

	@Override
	public ConjointDTO findConjointsByPersonnel(Long idPersonnel) {
		// TODO Auto-generated method stub
		ConjointDTO conjointDTO = new ConjointDTO();
		List<Conjoint> listConjoint = new ArrayList<Conjoint>();
		try{
			listConjoint = conjointRepository.findByPersonnelId(idPersonnel);
			if(listConjoint == null){
				conjointDTO.setResult(true);
				conjointDTO.setStatus(true);
				conjointDTO.setRow(null);
				conjointDTO.setRows(new ArrayList<Conjoint>());
				conjointDTO.setMessage("aucun conjoint trouve");
				conjointDTO.setTotal(0);
				conjointDTO.setErrors(listErreur);
			} else {
				int i = listConjoint.size();
				sb = new StringBuilder();
				sb.append(i).append(" conjoint(s) trouve(s) avec succes");
				conjointDTO.setResult(true);
				conjointDTO.setStatus(true);
				conjointDTO.setRow(null);
				conjointDTO.setRows(listConjoint);
				conjointDTO.setMessage(sb.toString());
				conjointDTO.setTotal(i);
				conjointDTO.setErrors(listErreur);
			}
		} catch (Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			
			conjointDTO.setResult(false);
			conjointDTO.setStatus(false);
			conjointDTO.setRow(null);
			conjointDTO.setRows(listConjoint);
			conjointDTO.setMessage(ex.getMessage());
			conjointDTO.setTotal(0);
			conjointDTO.setErrors(listErreur);
		}
		return conjointDTO;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) conjointRepository.count();
	}

	@Override
	public ConjointDTO loadConjoints(Pageable pageable) {
		// TODO Auto-generated method stub
		ConjointDTO conjointDTO = new ConjointDTO();
		Page<Conjoint> page = conjointRepository.findAll(pageable);
		conjointDTO.setResult(true);
		conjointDTO.setStatus(true);
		conjointDTO.setRows(page.getContent());
		conjointDTO.setTotal(page.getTotalElements());
		return conjointDTO;
	}

}
