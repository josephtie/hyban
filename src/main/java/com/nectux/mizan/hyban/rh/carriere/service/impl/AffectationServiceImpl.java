package com.nectux.mizan.hyban.rh.carriere.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nectux.mizan.hyban.personnel.repository.FonctionRepository;
import com.nectux.mizan.hyban.rh.carriere.entity.Affectation;
import com.nectux.mizan.hyban.rh.carriere.repository.AffectationRepository;
import com.nectux.mizan.hyban.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nectux.mizan.hyban.personnel.repository.PersonnelRepository;
import com.nectux.mizan.hyban.rh.carriere.dto.AffectationDTO;
import com.nectux.mizan.hyban.rh.carriere.repository.PosteRepository;
import com.nectux.mizan.hyban.rh.carriere.service.AffectationService;
import com.nectux.mizan.hyban.utils.DateManager;
import com.nectux.mizan.hyban.utils.Erreur;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service("affectationService")
public class AffectationServiceImpl implements AffectationService {
	
	@Autowired private AffectationRepository affectationRepository;
	@Autowired private PersonnelRepository personneRepository;
	@Autowired private FonctionRepository posteRepository;
	
	private StringBuilder sb;
	private Erreur erreur;
	private List<Erreur> listErreur;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public AffectationDTO save(Long id, Long idPersonnel, Long idPoste, String dateDebut, String dateFin, String observation) {
		// TODO Auto-generated method stub
		AffectationDTO affectationDTO = new AffectationDTO();
		Affectation affectation;
		listErreur = new ArrayList<Erreur>();
		try{
			if(id == null){
				affectation = new Affectation();
				affectation.setDateCreation(new Date());
			} else {
				affectation = affectationRepository.findById(id) .orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
				affectation.setDateModification(new Date());
			}
			affectation.setObservation(observation);
			
			if(idPersonnel == null){
				sb = new StringBuilder();
				erreur = new Erreur();
				erreur.setCode(10);
				erreur.setDescription("contrainte d'integrite non null non respectee");
				sb.append("le personnel est obligatoire");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
			} else
				affectation.setPersonnel(personneRepository.findById(idPersonnel) .orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + idPersonnel)));
			
			if(idPoste == null){
				sb = new StringBuilder();
				erreur = new Erreur();
				erreur.setCode(10);
				erreur.setDescription("contrainte d'integrite non null non respectee");
				sb.append("le poste est obligatoire");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
			} else 
				affectation.setPoste(posteRepository.findById(idPoste) .orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + idPoste)));
			
			if(dateDebut == null || dateDebut.trim().equals("")){
				sb = new StringBuilder();
				erreur = new Erreur();
				erreur.setCode(10);
				erreur.setDescription("contrainte d'integrite non null non respectee");
				sb.append("la date de debut est obligatoire");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
			} else 
				affectation.setDateDebut(Utils.stringToDate(dateDebut, "dd/MM/yyyy"));
			
			if(dateFin != null && !dateFin.trim().equals(""))
				affectation.setDateFin(Utils.stringToDate(dateFin, "dd/MM/yyyy"));
			
			if(id == null){
				if(affectationRepository.findByPersonnelIdAndPosteIdAndDateDebutAndDateFin(idPersonnel, idPoste, affectation.getDateDebut(), affectation.getDateFin()) != null){
					sb = new StringBuilder();
					erreur = new Erreur();
					erreur.setCode(10);
					erreur.setDescription("contrainte de doublon non non respectee");
					sb.append("cette affectation existe deja");
					erreur.setMessage(sb.toString());
					listErreur.add(erreur);
					
				}
			} else{
				
			}
			
			if(listErreur.isEmpty()){
				affectation = affectationRepository.save(affectation);
				sb = new StringBuilder();
				sb.append(" affectation enregistree avec succes");
				affectationDTO.setResult(true);
				affectationDTO.setStatus(true);
				affectationDTO.setRow(affectation);
				affectationDTO.setRows(null);
				affectationDTO.setMessage(sb.toString());
				affectationDTO.setTotal(0);
				affectationDTO.setErrors(listErreur);
			} else {
				affectationDTO.setResult(false);
				affectationDTO.setStatus(false);
				affectationDTO.setRow(null);
				affectationDTO.setRows(null);
				affectationDTO.setMessage("voir liste erreur");
				affectationDTO.setTotal(0);
				affectationDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			affectationDTO.setResult(false);
			affectationDTO.setStatus(false);
			affectationDTO.setRow(null);
			affectationDTO.setRows(null);
			affectationDTO.setMessage(ex.getMessage());
			affectationDTO.setTotal(0);
			affectationDTO.setErrors(listErreur);
		}
		return affectationDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public AffectationDTO delete(Long id) {
		// TODO Auto-generated method stub
		AffectationDTO affectationDTO = new AffectationDTO();
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		try{
			Affectation affectation = affectationRepository.findById(id) .orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			if(affectation == null){
				sb = new StringBuilder();
				erreur.setCode(12);
				erreur.setDescription("affectation inexistante");
				sb.append("cette affectation est inexistante dans le systeme");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
				
				affectationDTO.setResult(false);
				affectationDTO.setStatus(false);
				affectationDTO.setRow(null);
				affectationDTO.setRows(null);
				affectationDTO.setMessage("voir liste erreur");
				affectationDTO.setTotal(0);
				affectationDTO.setErrors(listErreur);
			} 
			
			if(listErreur.isEmpty()) {
				affectationRepository.delete(affectation);
				sb = new StringBuilder();
				sb.append(" affectation supprimee avec succes");
				affectationDTO.setResult(true);
				affectationDTO.setStatus(true);
				affectationDTO.setRow(affectation);
				affectationDTO.setRows(null);
				affectationDTO.setMessage(sb.toString());
				affectationDTO.setTotal(0);
				affectationDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			affectationDTO.setResult(false);
			affectationDTO.setStatus(false);
			affectationDTO.setRow(null);
			affectationDTO.setRows(null);
			affectationDTO.setMessage(ex.getMessage());
			affectationDTO.setTotal(0);
			affectationDTO.setErrors(listErreur);
		}
		return affectationDTO;
	}

	@Override
	public AffectationDTO findAffectation(Long id) {
		// TODO Auto-generated method stub
		AffectationDTO affectationDTO = new AffectationDTO();
		Affectation affectation;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		try{
			affectation = affectationRepository.findById(id) .orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			if(affectation == null){
				affectationDTO.setResult(true);
				affectationDTO.setStatus(true);
				affectationDTO.setRow(affectation);
				affectationDTO.setRows(null);
				affectationDTO.setMessage("affectation inexistante dans le systeme");
				affectationDTO.setTotal(1);
				affectationDTO.setErrors(listErreur);
			} else {
				affectationDTO.setResult(true);
				affectationDTO.setStatus(true);
				affectationDTO.setRow(affectation);
				affectationDTO.setRows(null);
				affectationDTO.setMessage("affectation trouvee avec succes");
				affectationDTO.setTotal(1);
				affectationDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			affectationDTO.setResult(false);
			affectationDTO.setStatus(false);
			affectationDTO.setRow(null);
			affectationDTO.setRows(null);
			affectationDTO.setMessage(ex.getMessage());
			affectationDTO.setTotal(0);
			affectationDTO.setErrors(listErreur);
		}
		return affectationDTO;
	}

	@Override
	public AffectationDTO findAffectations() {
		// TODO Auto-generated method stub
		AffectationDTO affectationDTO = new AffectationDTO();
		List<Affectation> listAffectation = new ArrayList<Affectation>();
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		try{
			listAffectation = affectationRepository.findAll();
			if(listAffectation == null){
				affectationDTO.setResult(true);
				affectationDTO.setStatus(true);
				affectationDTO.setRow(null);
				affectationDTO.setRows(new ArrayList<Affectation>());
				affectationDTO.setMessage("aucune affectation trouvee");
				affectationDTO.setTotal(0);
				affectationDTO.setErrors(listErreur);
			} else {
				int i = listAffectation.size();
				sb = new StringBuilder();
				sb.append(i).append(" affectation(s) trouvee(s) avec succes");
				affectationDTO.setResult(true);
				affectationDTO.setStatus(true);
				affectationDTO.setRow(null);
				affectationDTO.setRows(listAffectation);
				affectationDTO.setMessage(sb.toString());
				affectationDTO.setTotal(i);
				affectationDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			affectationDTO.setResult(false);
			affectationDTO.setStatus(false);
			affectationDTO.setRow(null);
			affectationDTO.setRows(null);
			affectationDTO.setMessage(ex.getMessage());
			affectationDTO.setTotal(0);
			affectationDTO.setErrors(listErreur);
		}
		return affectationDTO;
	}

	@Override
	public AffectationDTO findAffectationsByPersonnel(Long idPersonnel) {
		// TODO Auto-generated method stub
		AffectationDTO affectationDTO = new AffectationDTO();
		List<Affectation> listAffectation = new ArrayList<Affectation>();
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		try{
			listAffectation = affectationRepository.findByPersonnelId(idPersonnel);
			if(listAffectation == null){
				affectationDTO.setResult(true);
				affectationDTO.setStatus(true);
				affectationDTO.setRow(null);
				affectationDTO.setRows(new ArrayList<Affectation>());
				affectationDTO.setMessage("aucune affectation trouvee");
				affectationDTO.setTotal(0);
				affectationDTO.setErrors(listErreur);
			} else {
				int i = listAffectation.size();
				sb = new StringBuilder();
				sb.append(i).append(" affectation(s) trouvee(s) avec succes");
				affectationDTO.setResult(true);
				affectationDTO.setStatus(true);
				affectationDTO.setRow(null);
				affectationDTO.setRows(listAffectation);
				affectationDTO.setMessage(sb.toString());
				affectationDTO.setTotal(i);
				affectationDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			affectationDTO.setResult(false);
			affectationDTO.setStatus(false);
			affectationDTO.setRow(null);
			affectationDTO.setRows(null);
			affectationDTO.setMessage(ex.getMessage());
			affectationDTO.setTotal(0);
			affectationDTO.setErrors(listErreur);
		}
		return affectationDTO;
	}

	@Override
	public AffectationDTO findAffectationsByPoste(Long idPoste) {
		// TODO Auto-generated method stub
		AffectationDTO affectationDTO = new AffectationDTO();
		List<Affectation> listAffectation = new ArrayList<Affectation>();
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		try{
			listAffectation = affectationRepository.findByPosteId(idPoste);
			if(listAffectation == null){
				affectationDTO.setResult(true);
				affectationDTO.setStatus(true);
				affectationDTO.setRow(null);
				affectationDTO.setRows(new ArrayList<Affectation>());
				affectationDTO.setMessage("aucune affectation trouvee");
				affectationDTO.setTotal(0);
				affectationDTO.setErrors(listErreur);
			} else {
				int i = listAffectation.size();
				sb = new StringBuilder();
				sb.append(i).append(" affectation(s) trouvee(s) avec succes");
				affectationDTO.setResult(true);
				affectationDTO.setStatus(true);
				affectationDTO.setRow(null);
				affectationDTO.setRows(listAffectation);
				affectationDTO.setMessage(sb.toString());
				affectationDTO.setTotal(i);
				affectationDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			affectationDTO.setResult(false);
			affectationDTO.setStatus(false);
			affectationDTO.setRow(null);
			affectationDTO.setRows(null);
			affectationDTO.setMessage(ex.getMessage());
			affectationDTO.setTotal(0);
			affectationDTO.setErrors(listErreur);
		}
		return affectationDTO;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) affectationRepository.count();
	}

	@Override
	public AffectationDTO loadAffectations(Pageable pageable) {
		// TODO Auto-generated method stub
		AffectationDTO affectationDTO = new AffectationDTO();
		Page<Affectation> page = affectationRepository.findAll(pageable);
		affectationDTO.setResult(true);
		affectationDTO.setStatus(true);
		affectationDTO.setRows(page.getContent());
		affectationDTO.setTotal(page.getTotalElements());
		return affectationDTO;
	}

	@Override
	public AffectationDTO loadAffectations(Pageable pageable, String nom, String prenom, String poste) {
		// TODO Auto-generated method stub
		AffectationDTO affectationDTO = new AffectationDTO();
		Page<Affectation> page = affectationRepository.findByPersonnelNomContainingOrPersonnelPrenomContainingOrPosteLibelleContaining(pageable, nom, prenom, poste);
		affectationDTO.setResult(true);
		affectationDTO.setStatus(true);
		affectationDTO.setRows(page.getContent());
		affectationDTO.setTotal(page.getTotalElements());
		return affectationDTO;
	}

}
