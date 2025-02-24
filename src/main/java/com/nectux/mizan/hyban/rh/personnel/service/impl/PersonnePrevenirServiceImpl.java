package com.nectux.mizan.hyban.rh.personnel.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.nectux.mizan.hyban.personnel.repository.PersonnelRepository;
import com.nectux.mizan.hyban.rh.personnel.dto.PersonnePrevenirDTO;
import com.nectux.mizan.hyban.rh.personnel.service.PersonnePrevenirService;
import com.nectux.mizan.hyban.utils.Erreur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nectux.mizan.hyban.personnel.entity.Personnel;
import com.nectux.mizan.hyban.rh.personnel.entity.PersonnePrevenir;
import com.nectux.mizan.hyban.rh.personnel.repository.PersonnePrevenirRepository;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service("personnePrevenirService")
public class PersonnePrevenirServiceImpl implements PersonnePrevenirService {
	
	@Autowired private PersonnelRepository personneRepository;
	@Autowired private PersonnePrevenirRepository personnePrevenirRepository;
	
	private StringBuilder sb;
	private Erreur erreur;
	private List<Erreur> listErreur;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public PersonnePrevenirDTO save(Long id, Long idPersonnel, String filiation, String nom, String telephone, String email, char sexe) {
		// TODO Auto-generated method stub
		PersonnePrevenirDTO personnePrevenirDTO = new PersonnePrevenirDTO();
		PersonnePrevenir personnePrevenir;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		try{
			Personnel personnel = personneRepository.findById(idPersonnel).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			if(id != null){
				personnePrevenir = personnePrevenirRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			} else {
				personnePrevenir = new PersonnePrevenir();
				personnePrevenir.setActif(true);
			}
			personnePrevenir.setFiliation(filiation);
			personnePrevenir.setNom(nom);
			personnePrevenir.setTelephone(telephone);
			personnePrevenir.setEmail(email);
			personnePrevenir.setSexe(sexe);
			personnePrevenir.setPersonnel(personnel);
			
			if(listErreur.isEmpty()){
				personnePrevenir = personnePrevenirRepository.save(personnePrevenir);
				sb = new StringBuilder();
				sb.append(personnePrevenir.getNom()).append(" enregistre avec succes");
				personnePrevenirDTO.setResult(true);
				personnePrevenirDTO.setStatus(true);
				personnePrevenirDTO.setRow(personnePrevenir);
				personnePrevenirDTO.setRows(null);
				personnePrevenirDTO.setMessage(sb.toString());
				personnePrevenirDTO.setTotal(0);
				personnePrevenirDTO.setErrors(listErreur);
			} else {
				personnePrevenirDTO.setResult(false);
				personnePrevenirDTO.setStatus(false);
				personnePrevenirDTO.setRow(null);
				personnePrevenirDTO.setRows(null);
				personnePrevenirDTO.setMessage("voir liste erreur");
				personnePrevenirDTO.setTotal(0);
				personnePrevenirDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			personnePrevenirDTO.setResult(false);
			personnePrevenirDTO.setStatus(false);
			personnePrevenirDTO.setRow(null);
			personnePrevenirDTO.setRows(null);
			personnePrevenirDTO.setMessage(ex.getMessage());
			personnePrevenirDTO.setTotal(0);
			personnePrevenirDTO.setErrors(listErreur);
		}
		return personnePrevenirDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public PersonnePrevenirDTO enable(Long id, Long idPersonnel) {
		// TODO Auto-generated method stub
		PersonnePrevenirDTO personnePrevenirDTO = new PersonnePrevenirDTO();
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		try{
			PersonnePrevenir conjoint = personnePrevenirRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			if(conjoint == null){
				sb = new StringBuilder();
				erreur.setCode(12);
				erreur.setDescription("conjoint inexistant");
				sb.append("cette personnee a prevenir est inexistante dans le systeme");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
				
				personnePrevenirDTO.setResult(false);
				personnePrevenirDTO.setStatus(false);
				personnePrevenirDTO.setRow(null);
				personnePrevenirDTO.setRows(null);
				personnePrevenirDTO.setMessage("voir liste erreur");
				personnePrevenirDTO.setTotal(0);
				personnePrevenirDTO.setErrors(listErreur);
			} 
			
			if(listErreur.isEmpty()) {
				PersonnePrevenir current = personnePrevenirRepository.findByActifTrueAndPersonnelId(idPersonnel);
				current.setActif(false);
				personnePrevenirRepository.save(current);
				
				conjoint.setActif(true);
				personnePrevenirRepository.save(conjoint);
				
				sb = new StringBuilder();
				sb.append(conjoint.getNom()).append(" active avec succes");
				personnePrevenirDTO.setResult(true);
				personnePrevenirDTO.setStatus(true);
				personnePrevenirDTO.setRow(conjoint);
				personnePrevenirDTO.setRows(null);
				personnePrevenirDTO.setMessage(sb.toString());
				personnePrevenirDTO.setTotal(0);
				personnePrevenirDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			personnePrevenirDTO.setResult(false);
			personnePrevenirDTO.setStatus(false);
			personnePrevenirDTO.setRow(null);
			personnePrevenirDTO.setRows(null);
			personnePrevenirDTO.setMessage(ex.getMessage());
			personnePrevenirDTO.setTotal(0);
			personnePrevenirDTO.setErrors(listErreur);
		}
		return personnePrevenirDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public PersonnePrevenirDTO disable(Long id) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		PersonnePrevenirDTO personnePrevenirDTO = new PersonnePrevenirDTO();
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		try{
			PersonnePrevenir conjoint = personnePrevenirRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			if(conjoint == null){
				sb = new StringBuilder();
				erreur.setCode(12);
				erreur.setDescription("conjoint inexistant");
				sb.append("cette personne a prevenir est inexistante dans le systeme");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
				
				personnePrevenirDTO.setResult(false);
				personnePrevenirDTO.setStatus(false);
				personnePrevenirDTO.setRow(null);
				personnePrevenirDTO.setRows(null);
				personnePrevenirDTO.setMessage("voir liste erreur");
				personnePrevenirDTO.setTotal(0);
				personnePrevenirDTO.setErrors(listErreur);
			} 
			
			if(listErreur.isEmpty()) {
				conjoint.setActif(false);
				personnePrevenirRepository.save(conjoint);
				sb = new StringBuilder();
				sb.append(conjoint.getNom()).append(" desactive avec succes");
				personnePrevenirDTO.setResult(true);
				personnePrevenirDTO.setStatus(true);
				personnePrevenirDTO.setRow(conjoint);
				personnePrevenirDTO.setRows(null);
				personnePrevenirDTO.setMessage(sb.toString());
				personnePrevenirDTO.setTotal(0);
				personnePrevenirDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			personnePrevenirDTO.setResult(false);
			personnePrevenirDTO.setStatus(false);
			personnePrevenirDTO.setRow(null);
			personnePrevenirDTO.setRows(null);
			personnePrevenirDTO.setMessage(ex.getMessage());
			personnePrevenirDTO.setTotal(0);
			personnePrevenirDTO.setErrors(listErreur);
		}
		return personnePrevenirDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public PersonnePrevenirDTO delete(Long id) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		PersonnePrevenirDTO personnePrevenirDTO = new PersonnePrevenirDTO();
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		try{
			PersonnePrevenir personnePrevenir = personnePrevenirRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			if(personnePrevenir == null){
				sb = new StringBuilder();
				erreur.setCode(12);
				erreur.setDescription("personnee a prevenir inexistant");
				sb.append("cette personne a prevenir est inexistante dans le systeme");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
				
				personnePrevenirDTO.setResult(false);
				personnePrevenirDTO.setStatus(false);
				personnePrevenirDTO.setRow(null);
				personnePrevenirDTO.setRows(null);
				personnePrevenirDTO.setMessage("voir liste erreur");
				personnePrevenirDTO.setTotal(0);
				personnePrevenirDTO.setErrors(listErreur);
			} 
			
			if(listErreur.isEmpty()) {
				personnePrevenirRepository.delete(personnePrevenir);
				sb = new StringBuilder();
				sb.append(personnePrevenir.getNom()).append(" supprime avec succes");
				personnePrevenirDTO.setResult(true);
				personnePrevenirDTO.setStatus(true);
				personnePrevenirDTO.setRow(personnePrevenir);
				personnePrevenirDTO.setRows(null);
				personnePrevenirDTO.setMessage(sb.toString());
				personnePrevenirDTO.setTotal(0);
				personnePrevenirDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			personnePrevenirDTO.setResult(false);
			personnePrevenirDTO.setStatus(false);
			personnePrevenirDTO.setRow(null);
			personnePrevenirDTO.setRows(null);
			personnePrevenirDTO.setMessage(ex.getMessage());
			personnePrevenirDTO.setTotal(0);
			personnePrevenirDTO.setErrors(listErreur);
		}
		return personnePrevenirDTO;
	}

	@Override
	public PersonnePrevenirDTO findPersonnePrevenir(Long id) {
		// TODO Auto-generated method stub
		PersonnePrevenirDTO personnePrevenirDTO = new PersonnePrevenirDTO();
		PersonnePrevenir conjoint;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		try{
			conjoint = personnePrevenirRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			if(conjoint == null){
				personnePrevenirDTO.setResult(true);
				personnePrevenirDTO.setStatus(true);
				personnePrevenirDTO.setRow(conjoint);
				personnePrevenirDTO.setRows(null);
				personnePrevenirDTO.setMessage("personne a prevenir inexistante dans le systeme");
				personnePrevenirDTO.setTotal(1);
				personnePrevenirDTO.setErrors(listErreur);
			} else {
				personnePrevenirDTO.setResult(true);
				personnePrevenirDTO.setStatus(true);
				personnePrevenirDTO.setRow(conjoint);
				personnePrevenirDTO.setRows(null);
				personnePrevenirDTO.setMessage("personne a prevenir trouvee avec succes");
				personnePrevenirDTO.setTotal(1);
				personnePrevenirDTO.setErrors(listErreur);
			}
		} catch (Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			personnePrevenirDTO.setResult(true);
			personnePrevenirDTO.setStatus(true);
			personnePrevenirDTO.setRow(null);
			personnePrevenirDTO.setRows(null);
			personnePrevenirDTO.setMessage(ex.getMessage());
			personnePrevenirDTO.setTotal(0);
			personnePrevenirDTO.setErrors(listErreur);
		}
		return personnePrevenirDTO;
	}

	@Override
	public PersonnePrevenirDTO findPersonnesPrevenir() {
		// TODO Auto-generated method stub
		PersonnePrevenirDTO personnePrevenirDTO = new PersonnePrevenirDTO();
		List<PersonnePrevenir> listPersonnePrevenir = new ArrayList<PersonnePrevenir>();
		try{
			listPersonnePrevenir = personnePrevenirRepository.findAll();
			if(listPersonnePrevenir == null){
				personnePrevenirDTO.setResult(true);
				personnePrevenirDTO.setStatus(true);
				personnePrevenirDTO.setRow(null);
				personnePrevenirDTO.setRows(new ArrayList<PersonnePrevenir>());
				personnePrevenirDTO.setMessage("aucune personnee a prevenir trouvee");
				personnePrevenirDTO.setTotal(0);
				personnePrevenirDTO.setErrors(listErreur);
			} else {
				int i = listPersonnePrevenir.size();
				sb = new StringBuilder();
				sb.append(i).append(" personnee(s) a prevenir trouvee(s) avec succes");
				personnePrevenirDTO.setResult(true);
				personnePrevenirDTO.setStatus(true);
				personnePrevenirDTO.setRow(null);
				personnePrevenirDTO.setRows(listPersonnePrevenir);
				personnePrevenirDTO.setMessage(sb.toString());
				personnePrevenirDTO.setTotal(i);
				personnePrevenirDTO.setErrors(listErreur);
			}
		} catch (Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			
			personnePrevenirDTO.setResult(false);
			personnePrevenirDTO.setStatus(false);
			personnePrevenirDTO.setRow(null);
			personnePrevenirDTO.setRows(listPersonnePrevenir);
			personnePrevenirDTO.setMessage(ex.getMessage());
			personnePrevenirDTO.setTotal(0);
			personnePrevenirDTO.setErrors(listErreur);
		}
		return personnePrevenirDTO;
	}

	@Override
	public PersonnePrevenirDTO findPersonnesPrevenirByPersonnel(Long idPersonnel) {
		// TODO Auto-generated method stub
		PersonnePrevenirDTO personnePrevenirDTO = new PersonnePrevenirDTO();
		List<PersonnePrevenir> listPersonnePrevenir = new ArrayList<PersonnePrevenir>();
		try{
			listPersonnePrevenir = personnePrevenirRepository.findByPersonnelId(idPersonnel);
			if(listPersonnePrevenir == null){
				personnePrevenirDTO.setResult(true);
				personnePrevenirDTO.setStatus(true);
				personnePrevenirDTO.setRow(null);
				personnePrevenirDTO.setRows(new ArrayList<PersonnePrevenir>());
				personnePrevenirDTO.setMessage("aucune personne a prevenir trouvee");
				personnePrevenirDTO.setTotal(0);
				personnePrevenirDTO.setErrors(listErreur);
			} else {
				int i = listPersonnePrevenir.size();
				sb = new StringBuilder();
				sb.append(i).append(" personne(s) a prevenir trouvee(s) avec succes");
				personnePrevenirDTO.setResult(true);
				personnePrevenirDTO.setStatus(true);
				personnePrevenirDTO.setRow(null);
				personnePrevenirDTO.setRows(listPersonnePrevenir);
				personnePrevenirDTO.setMessage(sb.toString());
				personnePrevenirDTO.setTotal(i);
				personnePrevenirDTO.setErrors(listErreur);
			}
		} catch (Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			
			personnePrevenirDTO.setResult(false);
			personnePrevenirDTO.setStatus(false);
			personnePrevenirDTO.setRow(null);
			personnePrevenirDTO.setRows(listPersonnePrevenir);
			personnePrevenirDTO.setMessage(ex.getMessage());
			personnePrevenirDTO.setTotal(0);
			personnePrevenirDTO.setErrors(listErreur);
		}
		return personnePrevenirDTO;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) personnePrevenirRepository.count();
	}

	@Override
	public PersonnePrevenirDTO loadPersonnesPrevenir(Pageable pageable) {
		// TODO Auto-generated method stub
		PersonnePrevenirDTO personnePrevenirDTO = new PersonnePrevenirDTO();
		Page<PersonnePrevenir> page = personnePrevenirRepository.findAll(pageable);
		personnePrevenirDTO.setResult(true);
		personnePrevenirDTO.setStatus(true);
		personnePrevenirDTO.setRows(page.getContent());
		personnePrevenirDTO.setTotal(page.getTotalElements());
		return personnePrevenirDTO;
	}

}
