package com.nectux.mizan.hyban.rh.personnel.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nectux.mizan.hyban.personnel.entity.Personnel;
import com.nectux.mizan.hyban.personnel.repository.PersonnelRepository;
import com.nectux.mizan.hyban.rh.personnel.dto.EnfantDTO;
import com.nectux.mizan.hyban.rh.personnel.entity.Enfant;
import com.nectux.mizan.hyban.rh.personnel.repository.EnfantRepository;
import com.nectux.mizan.hyban.rh.personnel.service.EnfantService;
import com.nectux.mizan.hyban.utils.Erreur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service("enfantService")
public class EnfantServiceImpl implements EnfantService {
	
	@Autowired private EnfantRepository enfantRepository;
	@Autowired private PersonnelRepository personneRepository;
	
	private StringBuilder sb;
	private Erreur erreur;
	private List<Erreur> listErreur;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public EnfantDTO save(Long id, Long idPersonnel, String matricule, String nom, Date dateNaissance, String lieuNaissance, char sexe) {
		// TODO Auto-generated method stub
		EnfantDTO enfantDTO = new EnfantDTO();
		Enfant enfant;
		listErreur = new ArrayList<Erreur>();
		try{
			Personnel personnel = personneRepository.findById(idPersonnel).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			if(id != null){
				enfant = enfantRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			} else {
				enfant = new Enfant();
			}
			
			enfant.setMatricule(matricule);
			enfant.setNom(nom);
			enfant.setDateNaissance(dateNaissance);
			enfant.setLieuNaissance(lieuNaissance);
			enfant.setSexe(sexe);
			enfant.setPersonnel(personnel);
			
			if(enfant.getNom() == null || enfant.getNom().equals("")){
				sb = new StringBuilder();
				erreur = new Erreur();
				erreur.setCode(10);
				erreur.setDescription("contrainte d'integrite non null non respectee");
				sb.append("le champ nom est obligatoire");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
			}
			
			if(enfant.getPersonnel() == null){
				sb = new StringBuilder();
				erreur = new Erreur();
				erreur.setCode(10);
				erreur.setDescription("contrainte d'integrite non null non respectee");
				sb.append("le champ personnel est obligatoire");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
			}
			
			if(id == null){
				if((enfant.getMatricule() != null && enfantRepository.findByMatricule(matricule) != null) || (enfant.getMatricule() != null && enfantRepository.findByMatricule(matricule) != null)){
					sb = new StringBuilder();
					erreur = new Erreur();
					erreur.setCode(11);
					erreur.setDescription("contrainte de doublon non respectee");
					sb.append("le matricule ").append(matricule).append(" existe deja");
					erreur.setMessage(sb.toString());
					listErreur.add(erreur);
				}
				if(enfantRepository.findByPersonnelIdAndNom(enfant.getPersonnel().getId(), enfant.getNom()) != null){
					sb = new StringBuilder();
					erreur = new Erreur();
					erreur.setCode(11);
					erreur.setDescription("contrainte de doublon non respectee");
					sb.append("enfant ").append(nom).append(" deja enregistre pour le personnel ").append(enfant.getPersonnel().getNom());
					erreur.setMessage(sb.toString());
					listErreur.add(erreur);
				}
			} else{
				/*if(enfantRepository.findByPersonnelIdNotAndNom(enfant.getPersonnel().getId(), enfant.getNom()) != null){
					sb = new StringBuilder();
					erreur.setCode(11);
					erreur.setDescription("contrainte de doublon non respectee");
					sb.append("enfant ").append(nom).append(" deja enregistre pour le personnel ").append(enfant.getPersonnel().getNom());
					erreur.setMessage(sb.toString());
					listErreur.add(erreur);
				}*/
			}
			
			if(listErreur.isEmpty()){
				enfant = enfantRepository.save(enfant);
				sb = new StringBuilder();
				sb.append(enfant.getNom()).append(" enregistre avec succes");
				enfantDTO.setResult(true);
				enfantDTO.setStatus(true);
				enfantDTO.setRow(enfant);
				enfantDTO.setRows(null);
				enfantDTO.setMessage(sb.toString());
				enfantDTO.setTotal(0);
				enfantDTO.setErrors(listErreur);
			} else {
				enfantDTO.setResult(false);
				enfantDTO.setStatus(false);
				enfantDTO.setRow(null);
				enfantDTO.setRows(null);
				enfantDTO.setMessage("voir liste erreur");
				enfantDTO.setTotal(0);
				enfantDTO.setErrors(listErreur);
			}
			
		} catch(Exception ex){
			erreur = new Erreur();
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			enfantDTO.setResult(false);
			enfantDTO.setStatus(false);
			enfantDTO.setRow(null);
			enfantDTO.setRows(null);
			enfantDTO.setMessage(ex.getMessage());
			enfantDTO.setTotal(0);
			enfantDTO.setErrors(listErreur);
		}
		return enfantDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public EnfantDTO delete(Long id) {
		// TODO Auto-generated method stub
		EnfantDTO enfantDTO = new EnfantDTO();
		listErreur = new ArrayList<Erreur>();
		try{
			Enfant enfant = enfantRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			if(enfant == null){
				sb = new StringBuilder();
				erreur = new Erreur();
				erreur.setCode(12);
				erreur.setDescription("enfant inexistant");
				sb.append("cet enfant est inexistant dans le systeme");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
				
				enfantDTO.setResult(false);
				enfantDTO.setStatus(false);
				enfantDTO.setRow(null);
				enfantDTO.setRows(null);
				enfantDTO.setMessage("voir liste erreur");
				enfantDTO.setTotal(0);
				enfantDTO.setErrors(listErreur);
			} 
			
			if(listErreur.isEmpty()) {
				enfantRepository.delete(enfant);
				sb = new StringBuilder();
				sb.append(enfant.getNom()).append(" supprime avec succes");
				enfantDTO.setResult(true);
				enfantDTO.setStatus(true);
				enfantDTO.setRow(enfant);
				enfantDTO.setRows(null);
				enfantDTO.setMessage(sb.toString());
				enfantDTO.setTotal(0);
				enfantDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur = new Erreur();
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			enfantDTO.setResult(false);
			enfantDTO.setStatus(false);
			enfantDTO.setRow(null);
			enfantDTO.setRows(null);
			enfantDTO.setMessage(ex.getMessage());
			enfantDTO.setTotal(0);
			enfantDTO.setErrors(listErreur);
		}
		return enfantDTO;
	}

	@Override
	public EnfantDTO findEnfant(Long id) {
		// TODO Auto-generated method stub
		EnfantDTO enfantDTO = new EnfantDTO();
		Enfant enfant;
		listErreur = new ArrayList<Erreur>();
		try{
			enfant = enfantRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			if(enfant == null){
				enfantDTO.setResult(true);
				enfantDTO.setStatus(true);
				enfantDTO.setRow(enfant);
				enfantDTO.setRows(null);
				enfantDTO.setMessage("enfant inexistant dans le systeme");
				enfantDTO.setTotal(1);
				enfantDTO.setErrors(listErreur);
			} else {
				enfantDTO.setResult(true);
				enfantDTO.setStatus(true);
				enfantDTO.setRow(enfant);
				enfantDTO.setRows(null);
				enfantDTO.setMessage("enfant trouve avec succes");
				enfantDTO.setTotal(1);
				enfantDTO.setErrors(listErreur);
			}
		} catch (Exception ex){
			erreur = new Erreur();
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			enfantDTO.setResult(true);
			enfantDTO.setStatus(true);
			enfantDTO.setRow(null);
			enfantDTO.setRows(null);
			enfantDTO.setMessage(ex.getMessage());
			enfantDTO.setTotal(0);
			enfantDTO.setErrors(listErreur);
		}
		return enfantDTO;
	}

	@Override
	public EnfantDTO findEnfants() {
		// TODO Auto-generated method stub
		EnfantDTO enfantDTO = new EnfantDTO();
		List<Enfant> listEnfant = new ArrayList<Enfant>();
		try{
			listEnfant = enfantRepository.findAll();
			if(listEnfant == null){
				enfantDTO.setResult(true);
				enfantDTO.setStatus(true);
				enfantDTO.setRow(null);
				enfantDTO.setRows(new ArrayList<Enfant>());
				enfantDTO.setMessage("aucun enfant trouve");
				enfantDTO.setTotal(0);
				enfantDTO.setErrors(listErreur);
			} else {
				int i = listEnfant.size();
				sb = new StringBuilder();
				sb.append(i).append(" enfant(s) trouve(s) avec succes");
				enfantDTO.setResult(true);
				enfantDTO.setStatus(true);
				enfantDTO.setRow(null);
				enfantDTO.setRows(listEnfant);
				enfantDTO.setMessage(sb.toString());
				enfantDTO.setTotal(i);
				enfantDTO.setErrors(listErreur);
			}
		} catch (Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			
			enfantDTO.setResult(false);
			enfantDTO.setStatus(false);
			enfantDTO.setRow(null);
			enfantDTO.setRows(listEnfant);
			enfantDTO.setMessage(ex.getMessage());
			enfantDTO.setTotal(0);
			enfantDTO.setErrors(listErreur);
		}
		return enfantDTO;
	}

	@Override
	public EnfantDTO findEnfantsByPersonnel(Long idPersonnel) {
		// TODO Auto-generated method stub
		EnfantDTO enfantDTO = new EnfantDTO();
		List<Enfant> listEnfant = new ArrayList<Enfant>();
		try{
			listEnfant = enfantRepository.findByPersonnelId(idPersonnel);
			if(listEnfant == null){
				enfantDTO.setResult(true);
				enfantDTO.setStatus(true);
				enfantDTO.setRow(null);
				enfantDTO.setRows(new ArrayList<Enfant>());
				enfantDTO.setMessage("aucun enfant trouve");
				enfantDTO.setTotal(0);
				enfantDTO.setErrors(listErreur);
			} else {
				int i = listEnfant.size();
				sb = new StringBuilder();
				sb.append(i).append(" enfant(s) trouve(s) avec succes");
				enfantDTO.setResult(true);
				enfantDTO.setStatus(true);
				enfantDTO.setRow(null);
				enfantDTO.setRows(listEnfant);
				enfantDTO.setMessage(sb.toString());
				enfantDTO.setTotal(i);
				enfantDTO.setErrors(listErreur);
			}
		} catch (Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			
			enfantDTO.setResult(false);
			enfantDTO.setStatus(false);
			enfantDTO.setRow(null);
			enfantDTO.setRows(listEnfant);
			enfantDTO.setMessage(ex.getMessage());
			enfantDTO.setTotal(0);
			enfantDTO.setErrors(listErreur);
		}
		return enfantDTO;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) enfantRepository.count();
	}

	@Override
	public EnfantDTO loadEnfants(Pageable pageable) {
		// TODO Auto-generated method stub
		EnfantDTO enfantDTO = new EnfantDTO();
		Page<Enfant> page = enfantRepository.findAll(pageable);
		enfantDTO.setResult(true);
		enfantDTO.setStatus(true);
		enfantDTO.setRows(page.getContent());
		enfantDTO.setTotal(page.getTotalElements());
		return enfantDTO;
	}

	/*@Override
	public EnfantDTO loadEnfants(Pageable pageable, String search) {
		// TODO Auto-generated method stub
		EnfantDTO enfantDTO = new EnfantDTO();
		Page<Enfant> page = enfantRepository.findByMatriculeContainingOrNomContainingOrLieuNaissanceContainingOrSexeOrDateNaissance(pageable, search, search, search, search, dateNaissance);
		enfantDTO.setResult(true);
		enfantDTO.setStatus(true);
		enfantDTO.setRows(page.getContent());
		enfantDTO.setTotal(page.getTotalElements());
		return enfantDTO;
	}*/

}
