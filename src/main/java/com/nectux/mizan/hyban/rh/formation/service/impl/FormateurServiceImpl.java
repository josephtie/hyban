package com.nectux.mizan.hyban.rh.formation.service.impl;

import com.nectux.mizan.hyban.rh.formation.dto.FormateurDTO;
import com.nectux.mizan.hyban.rh.formation.entity.Formateur;
import com.nectux.mizan.hyban.rh.formation.repository.CabinetFormationRepository;
import com.nectux.mizan.hyban.rh.formation.repository.FormateurRepository;
import com.nectux.mizan.hyban.rh.formation.service.FormateurService;
import com.nectux.mizan.hyban.utils.DateManager;
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
@Service("formateurService")
public class FormateurServiceImpl implements FormateurService {
	
	@Autowired
    private FormateurRepository formateurRepository;
	@Autowired
    private CabinetFormationRepository cabinetFormationRepository;
	
	private StringBuilder sb;
	private Erreur erreur;
	private List<Erreur> listErreur;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public FormateurDTO save(Long id, Long cabinetFormationId, String nom, String civilite, char sexe,
			int situationMatrimonial, String dateNaissance, String lieuNaissance, String fixe, String mobile) {
		// TODO Auto-generated method stub
		
		FormateurDTO formateurDTO = new FormateurDTO();
		Formateur formateur;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		
		try{ 
			if(id == null){
				formateur = new Formateur();
				formateur.setDateCreation(new Date());
			} else {
				formateur = formateurRepository.findById(id) .orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
				formateur.setDateModification(new Date());
			}
			
			formateur.setNomComplet(nom);
			formateur.setCivilite(civilite);
			formateur.setSexe(sexe);
			formateur.setSituationMat(situationMatrimonial);
			formateur.setDateNaissance(DateManager.stringToDate(dateNaissance, "dd/MM/yyyy"));
			formateur.setLieuNaissance(lieuNaissance);
			formateur.setFixe(fixe);
			formateur.setMobile(mobile);
			formateur.setCabinetFormation(cabinetFormationRepository.findById(cabinetFormationId) .orElseThrow(() -> new EntityNotFoundException("Cabinet formation not found for id " + cabinetFormationId)));
			
			if(listErreur.isEmpty()){
				formateur = formateurRepository.save(formateur);
				sb = new StringBuilder();
				sb.append(formateur.getNomComplet()).append(" enregistre avec succes");
				formateurDTO.setResult(true);
				formateurDTO.setStatus(true);
				formateurDTO.setRow(formateur);
				formateurDTO.setRows(null);
				formateurDTO.setMessage(sb.toString());
				formateurDTO.setTotal(0);
				formateurDTO.setErrors(listErreur);
			} else {
				formateurDTO.setResult(false);
				formateurDTO.setStatus(false);
				formateurDTO.setRow(null);
				formateurDTO.setRows(null);
				formateurDTO.setMessage("voir liste erreur");
				formateurDTO.setTotal(0);
				formateurDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			formateurDTO.setResult(false);
			formateurDTO.setStatus(false);
			formateurDTO.setRow(null);
			formateurDTO.setRows(null);
			formateurDTO.setMessage(ex.getMessage());
			formateurDTO.setTotal(0);
			formateurDTO.setErrors(listErreur);
		}
		return formateurDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public FormateurDTO delete(Long id) {
		// TODO Auto-generated method stub
		
		FormateurDTO formateurDTO = new FormateurDTO();
		Formateur formateur;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		
		try{ 
			formateur = formateurRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			if(formateur == null){
				sb = new StringBuilder();
				erreur.setCode(12);
				erreur.setDescription("formateur inexistant");
				sb.append("ce formateur est inexistant dans le systeme");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
				
				formateurDTO.setResult(false);
				formateurDTO.setStatus(false);
				formateurDTO.setRow(null);
				formateurDTO.setRows(null);
				formateurDTO.setMessage("voir liste erreur");
				formateurDTO.setTotal(0);
				formateurDTO.setErrors(listErreur);
			} 
			
			if(listErreur.isEmpty()) {
				formateurRepository.delete(formateur);
				sb = new StringBuilder();
				sb.append(formateur.getNomComplet()).append(" supprime avec succes");
				formateurDTO.setResult(true);
				formateurDTO.setStatus(true);
				formateurDTO.setRow(formateur);
				formateurDTO.setRows(null);
				formateurDTO.setMessage(sb.toString());
				formateurDTO.setTotal(0);
				formateurDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			formateurDTO.setResult(false);
			formateurDTO.setStatus(false);
			formateurDTO.setRow(null);
			formateurDTO.setRows(null);
			formateurDTO.setMessage(ex.getMessage());
			formateurDTO.setTotal(0);
			formateurDTO.setErrors(listErreur);
		}
		return formateurDTO;
	}

	@Override
	public FormateurDTO findFormateur(Long id) {
		// TODO Auto-generated method stub
		
		FormateurDTO formateurDTO = new FormateurDTO();
		Formateur formateur;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		
		try{ 
			formateur = formateurRepository.findById(id) .orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			if(formateur == null){
				formateurDTO.setResult(true);
				formateurDTO.setStatus(true);
				formateurDTO.setRow(formateur);
				formateurDTO.setRows(null);
				formateurDTO.setMessage("formateur inexistant dans le systeme");
				formateurDTO.setTotal(1);
				formateurDTO.setErrors(listErreur);
			} else {
				formateurDTO.setResult(true);
				formateurDTO.setStatus(true);
				formateurDTO.setRow(formateur);
				formateurDTO.setRows(null);
				formateurDTO.setMessage("formateur trouve avec succes");
				formateurDTO.setTotal(1);
				formateurDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			formateurDTO.setResult(false);
			formateurDTO.setStatus(false);
			formateurDTO.setRow(null);
			formateurDTO.setRows(null);
			formateurDTO.setMessage(ex.getMessage());
			formateurDTO.setTotal(0);
			formateurDTO.setErrors(listErreur);
		}
		return formateurDTO;
	}

	@Override
	public FormateurDTO findFormateurs() {
		// TODO Auto-generated method stub
		
		FormateurDTO formateurDTO = new FormateurDTO();
		List<Formateur> listFormateur;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		
		try{ 
			listFormateur = formateurRepository.findAll();
			if(listFormateur == null){
				formateurDTO.setResult(true);
				formateurDTO.setStatus(true);
				formateurDTO.setRow(null);
				formateurDTO.setRows(new ArrayList<Formateur>());
				formateurDTO.setMessage("aucun formateur trouve");
				formateurDTO.setTotal(0);
				formateurDTO.setErrors(listErreur);
			} else {
				int i = listFormateur.size();
				sb = new StringBuilder();
				sb.append(i).append(" formateur(s) trouve(s) avec succes");
				formateurDTO.setResult(true);
				formateurDTO.setStatus(true);
				formateurDTO.setRow(null);
				formateurDTO.setRows(listFormateur);
				formateurDTO.setMessage(sb.toString());
				formateurDTO.setTotal(i);
				formateurDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			formateurDTO.setResult(false);
			formateurDTO.setStatus(false);
			formateurDTO.setRow(null);
			formateurDTO.setRows(null);
			formateurDTO.setMessage(ex.getMessage());
			formateurDTO.setTotal(0);
			formateurDTO.setErrors(listErreur);
		}
		return formateurDTO;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) formateurRepository.count();
	}

	@Override
	public FormateurDTO loadFormateurs(Pageable pageable) {
		// TODO Auto-generated method stub
		FormateurDTO formateurDTO = new FormateurDTO();
		Page<Formateur> page = formateurRepository.findAll(pageable);
		formateurDTO.setResult(true);
		formateurDTO.setStatus(true);
		formateurDTO.setRows(page.getContent());
		formateurDTO.setTotal(page.getTotalElements());
		return formateurDTO;
	}

	@Override
	public FormateurDTO loadFormateurs(Pageable pageable, String nom, String civilite, String dateNaissance,
			String lieuNaissance, String fixe, String mobile, String nomCabinetFormation) {
		// TODO Auto-generated method stub
		FormateurDTO formateurDTO = new FormateurDTO();
		//Page<Formateur> page = formateurRepository.findByAttributes(pageable, nom, dateNaissance, lieuNaissance, fixe, mobile, nomCabinetFormation);
		formateurDTO.setResult(true);
		formateurDTO.setStatus(true);
		//formateurDTO.setRows(page.getContent());
	//	formateurDTO.setTotal(page.getTotalElements());
		return formateurDTO;
	}

}
