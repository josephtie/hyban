package com.nectux.mizan.hyban.rh.formation.service.impl;

import com.nectux.mizan.hyban.personnel.repository.PersonnelRepository;
import com.nectux.mizan.hyban.rh.formation.dto.FormationPersonnelDTO;
import com.nectux.mizan.hyban.rh.formation.entity.Formation;
import com.nectux.mizan.hyban.rh.formation.entity.FormationPersonnel;
import com.nectux.mizan.hyban.rh.formation.repository.FormationPersonnelRepository;
import com.nectux.mizan.hyban.rh.formation.repository.FormationRepository;
import com.nectux.mizan.hyban.rh.formation.service.FormationPersonnelService;
import com.nectux.mizan.hyban.utils.Erreur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional
@Service("formationPersonnelService")
public class FormationPersonnelServiceImpl implements FormationPersonnelService {
	
	@Autowired
    private FormationRepository formationRepository;
	@Autowired
    private PersonnelRepository personnelRepository;
	@Autowired
    private FormationPersonnelRepository formationPersonnelRepository;
	
	private StringBuilder sb;
	private Erreur erreur;
	private List<Erreur> listErreur;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public FormationPersonnelDTO save(Long id, Long idFormation, Long idPersonnel) {
		// TODO Auto-generated method stub
		
		FormationPersonnelDTO formationPersonnelDTO = new FormationPersonnelDTO();
		FormationPersonnel formationPersonnel;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		
		try{ 
			if(id == null){
				formationPersonnel = new FormationPersonnel();
				formationPersonnel.setDateCreation(new Date());
			} else {
				formationPersonnel = formationPersonnelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
				formationPersonnel.setDateModification(new Date());
			}
			
			formationPersonnel.setPersonnel(personnelRepository.findById(idPersonnel).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + idPersonnel)));
			formationPersonnel.setFormation(formationRepository.findById(idFormation).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + idFormation)));
			
			if(listErreur.isEmpty()){
				formationPersonnel = formationPersonnelRepository.save(formationPersonnel);
				sb = new StringBuilder();
				sb.append("participant enregistre avec succes");
				formationPersonnelDTO.setResult(true);
				formationPersonnelDTO.setStatus(true);
				formationPersonnelDTO.setRow(formationPersonnel);
				formationPersonnelDTO.setRows(null);
				formationPersonnelDTO.setMessage(sb.toString());
				formationPersonnelDTO.setTotal(0);
				formationPersonnelDTO.setErrors(listErreur);
			} else {
				formationPersonnelDTO.setResult(false);
				formationPersonnelDTO.setStatus(false);
				formationPersonnelDTO.setRow(null);
				formationPersonnelDTO.setRows(null);
				formationPersonnelDTO.setMessage("voir liste erreur");
				formationPersonnelDTO.setTotal(0);
				formationPersonnelDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			formationPersonnelDTO.setResult(false);
			formationPersonnelDTO.setStatus(false);
			formationPersonnelDTO.setRow(null);
			formationPersonnelDTO.setRows(null);
			formationPersonnelDTO.setMessage(ex.getMessage());
			formationPersonnelDTO.setTotal(0);
			formationPersonnelDTO.setErrors(listErreur);
		}
		return formationPersonnelDTO;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public FormationPersonnelDTO save(String listPersonnel, int listPersonnelSize, Long idFormation){
		FormationPersonnelDTO formationPersonnelDTO = new FormationPersonnelDTO();
		try{
			String[] output = listPersonnel.split(" ");
			//List<Long> listPers = new ArrayList<Long>();
			FormationPersonnel formationPersonnel;
			Formation formation = formationRepository.findById(idFormation).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + idFormation));
			List<FormationPersonnel> listFormationPersonnel = new ArrayList<FormationPersonnel>();
			
			for(int i = 0; i < listPersonnelSize; i++){
				//listPers.add(Long.valueOf(output[i]));
				formationPersonnel = new FormationPersonnel();
				formationPersonnel.setFormation(formation);
				int finalI = i;
				formationPersonnel.setPersonnel(personnelRepository.findById(Long.valueOf(output[i])).orElseThrow(() -> new EntityNotFoundException("Personnel not found for id " + output[finalI] )));
				formationPersonnel.setDateCreation(new Date());
				listFormationPersonnel.add(formationPersonnel);
			}
			
			Iterable<FormationPersonnel> formationPersonnels =  formationPersonnelRepository.saveAll(listFormationPersonnel);
			
			listFormationPersonnel = new ArrayList<FormationPersonnel>();
			for(FormationPersonnel fp : formationPersonnels){
				listFormationPersonnel.add(fp);
			}
			
			sb = new StringBuilder();
			sb.append(listFormationPersonnel.size()).append(" participant(s) enregistre avec succes");
			formationPersonnelDTO.setResult(true);
			formationPersonnelDTO.setStatus(true);
			formationPersonnelDTO.setRow(null);
			formationPersonnelDTO.setRows(listFormationPersonnel);
			formationPersonnelDTO.setMessage(sb.toString());
			formationPersonnelDTO.setTotal(0);
			formationPersonnelDTO.setErrors(listErreur);
			
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			formationPersonnelDTO.setResult(false);
			formationPersonnelDTO.setStatus(false);
			formationPersonnelDTO.setRow(null);
			formationPersonnelDTO.setRows(null);
			formationPersonnelDTO.setMessage(ex.getMessage());
			formationPersonnelDTO.setTotal(0);
			formationPersonnelDTO.setErrors(listErreur);
		}
		return formationPersonnelDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public FormationPersonnelDTO delete(Long id) {
		// TODO Auto-generated method stub
		
		FormationPersonnelDTO formationPersonnelDTO = new FormationPersonnelDTO();
		FormationPersonnel formationPersonnel;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		
		try{ 
			formationPersonnel = formationPersonnelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			if(formationPersonnel == null){
				sb = new StringBuilder();
				erreur.setCode(12);
				erreur.setDescription("formation inexistante");
				sb.append("cette formation est inexistante dans le systeme");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
				
				formationPersonnelDTO.setResult(false);
				formationPersonnelDTO.setStatus(false);
				formationPersonnelDTO.setRow(null);
				formationPersonnelDTO.setRows(null);
				formationPersonnelDTO.setMessage("voir liste erreur");
				formationPersonnelDTO.setTotal(0);
				formationPersonnelDTO.setErrors(listErreur);
			} 
			
			if(listErreur.isEmpty()) {
				formationPersonnelRepository.delete(formationPersonnel);
				sb = new StringBuilder();
				sb.append("supprime avec succes");
				formationPersonnelDTO.setResult(true);
				formationPersonnelDTO.setStatus(true);
				formationPersonnelDTO.setRow(formationPersonnel);
				formationPersonnelDTO.setRows(null);
				formationPersonnelDTO.setMessage(sb.toString());
				formationPersonnelDTO.setTotal(0);
				formationPersonnelDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			formationPersonnelDTO.setResult(false);
			formationPersonnelDTO.setStatus(false);
			formationPersonnelDTO.setRow(null);
			formationPersonnelDTO.setRows(null);
			formationPersonnelDTO.setMessage(ex.getMessage());
			formationPersonnelDTO.setTotal(0);
			formationPersonnelDTO.setErrors(listErreur);
		}
		return formationPersonnelDTO;
	}

	@Override
	public FormationPersonnelDTO findFormationPersonnel(Long id) {
		// TODO Auto-generated method stub
		
		FormationPersonnelDTO formationPersonnelDTO = new FormationPersonnelDTO();
		FormationPersonnel formationPersonnel;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		
		try{ 

			formationPersonnel = formationPersonnelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			if(formationPersonnel == null){
				formationPersonnelDTO.setResult(true);
				formationPersonnelDTO.setStatus(true);
				formationPersonnelDTO.setRow(formationPersonnel);
				formationPersonnelDTO.setRows(null);
				formationPersonnelDTO.setMessage("participant inexistant dans le systeme");
				formationPersonnelDTO.setTotal(1);
				formationPersonnelDTO.setErrors(listErreur);
			} else {
				formationPersonnelDTO.setResult(true);
				formationPersonnelDTO.setStatus(true);
				formationPersonnelDTO.setRow(formationPersonnel);
				formationPersonnelDTO.setRows(null);
				formationPersonnelDTO.setMessage("participant trouve avec succes");
				formationPersonnelDTO.setTotal(1);
				formationPersonnelDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			formationPersonnelDTO.setResult(false);
			formationPersonnelDTO.setStatus(false);
			formationPersonnelDTO.setRow(null);
			formationPersonnelDTO.setRows(null);
			formationPersonnelDTO.setMessage(ex.getMessage());
			formationPersonnelDTO.setTotal(0);
			formationPersonnelDTO.setErrors(listErreur);
		}
		return formationPersonnelDTO;
	}
	
	@Override
	public FormationPersonnelDTO findFormationPersonnel(String listPersonnel, int listPersonnelSize, Long idFormation){
		FormationPersonnelDTO formationPersonnelDTO = new FormationPersonnelDTO();
		try{
			String[] output = listPersonnel.split(" ");
			//List<Long> listPers = new ArrayList<Long>();
			FormationPersonnel formationPersonnel;
			Formation formation = formationRepository.findById(idFormation).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + idFormation));
			List<FormationPersonnel> listFormationPersonnel = new ArrayList<FormationPersonnel>();
			
			for(int i = 0; i < listPersonnelSize; i++){
				//listPers.add(Long.valueOf(output[i]));
				formationPersonnel = new FormationPersonnel();
				formationPersonnel.setFormation(formation);
				int finalI = i;
				formationPersonnel.setPersonnel(personnelRepository.findById(Long.valueOf(output[i])).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + output[finalI])));
				formationPersonnel.setDateCreation(new Date());
				
				listFormationPersonnel.add(formationPersonnelRepository.findByPersonnelIdAndFormationId(Long.valueOf(output[i]), idFormation));
			}
			
			formationPersonnelDTO.setResult(true);
			formationPersonnelDTO.setStatus(true);
			formationPersonnelDTO.setRow(null);
			formationPersonnelDTO.setRows(listFormationPersonnel);
			formationPersonnelDTO.setMessage("participant trouve avec succes");
			formationPersonnelDTO.setTotal(listFormationPersonnel.size());
			formationPersonnelDTO.setErrors(listErreur);
			
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			formationPersonnelDTO.setResult(false);
			formationPersonnelDTO.setStatus(false);
			formationPersonnelDTO.setRow(null);
			formationPersonnelDTO.setRows(null);
			formationPersonnelDTO.setMessage(ex.getMessage());
			formationPersonnelDTO.setTotal(0);
			formationPersonnelDTO.setErrors(listErreur);
		}
		return formationPersonnelDTO;
	}

	@Override
	public FormationPersonnelDTO findFormationPersonnels() {
		// TODO Auto-generated method stub
		
		FormationPersonnelDTO formationPersonnelDTO = new FormationPersonnelDTO();
		List<FormationPersonnel> listFormationPersonnel;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		
		try{ 
			listFormationPersonnel = formationPersonnelRepository.findAll();
			if(listFormationPersonnel == null){
				formationPersonnelDTO.setResult(true);
				formationPersonnelDTO.setStatus(true);
				formationPersonnelDTO.setRow(null);
				formationPersonnelDTO.setRows(new ArrayList<FormationPersonnel>());
				formationPersonnelDTO.setMessage("aucun cabinet de formation trouve");
				formationPersonnelDTO.setTotal(0);
				formationPersonnelDTO.setErrors(listErreur);
			} else {
				int i = listFormationPersonnel.size();
				sb = new StringBuilder();
				sb.append(i).append(" participant(s) trouve(s) avec succes");
				formationPersonnelDTO.setResult(true);
				formationPersonnelDTO.setStatus(true);
				formationPersonnelDTO.setRow(null);
				formationPersonnelDTO.setRows(listFormationPersonnel);
				formationPersonnelDTO.setMessage(sb.toString());
				formationPersonnelDTO.setTotal(i);
				formationPersonnelDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			formationPersonnelDTO.setResult(false);
			formationPersonnelDTO.setStatus(false);
			formationPersonnelDTO.setRow(null);
			formationPersonnelDTO.setRows(null);
			formationPersonnelDTO.setMessage(ex.getMessage());
			formationPersonnelDTO.setTotal(0);
			formationPersonnelDTO.setErrors(listErreur);
		}
		return formationPersonnelDTO;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) formationPersonnelRepository.count();
	}

	@Override
	public FormationPersonnelDTO loadFormationPersonnels(Pageable pageable) {
		// TODO Auto-generated method stub
		FormationPersonnelDTO formationPersonnelDTO = new FormationPersonnelDTO();
		Page<FormationPersonnel> page = formationPersonnelRepository.findAll(pageable);
		formationPersonnelDTO.setResult(true);
		formationPersonnelDTO.setStatus(true);
		formationPersonnelDTO.setRows(page.getContent());
		formationPersonnelDTO.setTotal(page.getTotalElements());
		return formationPersonnelDTO;
	}
	@Override
	public FormationPersonnelDTO loadFormationPersonnelsduneFormation(Pageable pageable,Long idformation) {
		// TODO Auto-generated method stub
		FormationPersonnelDTO formationPersonnelDTO = new FormationPersonnelDTO();
		Page<FormationPersonnel> page = formationPersonnelRepository.findAllByFormationId(pageable,idformation);
		formationPersonnelDTO.setResult(true);
		formationPersonnelDTO.setStatus(true);
		formationPersonnelDTO.setRows(page.getContent());
		formationPersonnelDTO.setTotal(page.getTotalElements());
		return formationPersonnelDTO;
	}



	@Override
	public FormationPersonnelDTO loadFormationPersonnels(Pageable pageable, String formationIntitule,
			String personnelNom, String personnelPrenom) {
		// TODO Auto-generated method stub
		FormationPersonnelDTO formationPersonnelDTO = new FormationPersonnelDTO();
		Page<FormationPersonnel> page = formationPersonnelRepository.findByFormationIntituleContainingOrPersonnelNomContainingOrPersonnelPrenom(pageable, formationIntitule, personnelNom, personnelPrenom);
		formationPersonnelDTO.setResult(true);
		formationPersonnelDTO.setStatus(true);
		formationPersonnelDTO.setRows(page.getContent());
		formationPersonnelDTO.setTotal(page.getTotalElements());
		return formationPersonnelDTO;
	}


	@Override
	public FormationPersonnelDTO loadFormationPersonnelsduneFormation(Pageable pageable, Long idFormation, String formationIntitule, String personnelNom, String personnelPrenom) {
		// TODO Auto-generated method stub
		FormationPersonnelDTO formationPersonnelDTO = new FormationPersonnelDTO();
		Page<FormationPersonnel> page = formationPersonnelRepository.findByFormationIdAndFormationIntituleContainingOrPersonnelNomContainingOrPersonnelPrenom(pageable,idFormation, formationIntitule, personnelNom, personnelPrenom);
		formationPersonnelDTO.setResult(true);
		formationPersonnelDTO.setStatus(true);
		formationPersonnelDTO.setRows(page.getContent());
		formationPersonnelDTO.setTotal(page.getTotalElements());
		return formationPersonnelDTO;
	}
}
