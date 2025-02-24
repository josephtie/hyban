package com.nectux.mizan.hyban.rh.personnel.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nectux.mizan.hyban.personnel.repository.PersonnelRepository;
import com.nectux.mizan.hyban.rh.personnel.dto.PointageDTO;
import com.nectux.mizan.hyban.rh.personnel.entity.Pointage;
import com.nectux.mizan.hyban.rh.personnel.repository.PointageRepository;
import com.nectux.mizan.hyban.rh.personnel.service.PointageService;
import com.nectux.mizan.hyban.utils.DateManager;
import com.nectux.mizan.hyban.utils.Erreur;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service("pointageService")
public class PointageServiceImpl implements PointageService {
	
	@Autowired private PointageRepository pointageRepository;
	@Autowired private PersonnelRepository personnelRepository;
	
	private StringBuilder sb;
	private Erreur erreur;
	private List<Erreur> listErreur;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public PointageDTO save(Long id, Long idPersonnel, String datePointage, String heureArrivee, String heureDepart, String heurePause, String heureReprise, String description) {
		// TODO Auto-generated method stub
		
		PointageDTO pointageDTO = new PointageDTO();
		Pointage pointage;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		
		try{
			if(id == null){
				pointage = new Pointage();
				pointage.setDateCreation(new Date());
			} else {
				pointage = pointageRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
				pointage.setDateModification(new Date());
			}
			
			pointage.setDescription(description);
			
			if(datePointage == null || datePointage.trim().equals("")){
				sb = new StringBuilder();
				erreur = new Erreur();
				erreur.setCode(10);
				erreur.setDescription("contrainte d'integrite non null non respectee");
				sb.append("la date de debut est obligatoire");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
			} else 
				pointage.setDatePointage(DateManager.stringToDate(datePointage, "dd/MM/yyyy"));
			
			if(heureArrivee == null || heureArrivee.trim().equals("")){
				sb = new StringBuilder();
				erreur = new Erreur();
				erreur.setCode(10);
				erreur.setDescription("contrainte d'integrite non null non respectee");
				sb.append("l'heure d'arrivee est obligatoire");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
			} else 
				pointage.setHeureArrivee(DateManager.stringToDate(heureArrivee, "dd/MM/yyyy HH:mm"));
			
			if(heureDepart != null && !heureDepart.trim().equals(""))
				pointage.setHeureDepart(DateManager.stringToDate(heureDepart, "dd/MM/yyyy HH:mm"));
			
			if(heurePause != null && !heurePause.trim().equals(""))
				pointage.setHeurePause(DateManager.stringToDate(heurePause, "dd/MM/yyyy HH:mm"));
			
			if(heureReprise != null && !heureReprise.trim().equals(""))
				pointage.setHeureReprise(DateManager.stringToDate(heureReprise, "dd/MM/yyyy HH:mm"));
			
			if(idPersonnel == null){
				sb = new StringBuilder();
				erreur = new Erreur();
				erreur.setCode(10);
				erreur.setDescription("contrainte d'integrite non null non respectee");
				sb.append("le personnel est obligatoire");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
			} else
				pointage.setPersonnel(personnelRepository.findById(idPersonnel).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + idPersonnel)));
			
			if(id == null){
				if(pointageRepository.findByPersonnelIdAndDatePointage(idPersonnel, pointage.getDatePointage()) != null){
					sb = new StringBuilder();
					erreur = new Erreur();
					erreur.setCode(10);
					erreur.setDescription("contrainte de doublon non respectee");
					sb.append("ce pointage existe deja");
					erreur.setMessage(sb.toString());
					listErreur.add(erreur);
				}
			} else{
				
			}
			
			if(listErreur.isEmpty()){
				pointage = pointageRepository.save(pointage);
				sb = new StringBuilder();
				sb.append(" enregistre avec succes");
				pointageDTO.setResult(true);
				pointageDTO.setStatus(true);
				pointageDTO.setRow(pointage);
				pointageDTO.setRows(null);
				pointageDTO.setMessage(sb.toString());
				pointageDTO.setTotal(0);
				pointageDTO.setErrors(listErreur);
			} else {
				pointageDTO.setResult(false);
				pointageDTO.setStatus(false);
				pointageDTO.setRow(null);
				pointageDTO.setRows(null);
				pointageDTO.setMessage("voir liste erreur");
				pointageDTO.setTotal(0);
				pointageDTO.setErrors(listErreur);
			}
			
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			pointageDTO.setResult(false);
			pointageDTO.setStatus(false);
			pointageDTO.setRow(null);
			pointageDTO.setRows(null);
			pointageDTO.setMessage(ex.getMessage());
			pointageDTO.setTotal(0);
			pointageDTO.setErrors(listErreur);
		}
		return pointageDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public PointageDTO delete(Long id) {
		// TODO Auto-generated method stub
		
		PointageDTO pointageDTO = new PointageDTO();
		Pointage pointage;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		
		try{
			pointage = pointageRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			if(pointage == null){
				sb = new StringBuilder();
				erreur.setCode(12);
				erreur.setDescription("pointage inexistant");
				sb.append("ce pointage est inexistant dans le systeme");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
				
				pointageDTO.setResult(false);
				pointageDTO.setStatus(false);
				pointageDTO.setRow(null);
				pointageDTO.setRows(null);
				pointageDTO.setMessage("voir liste erreur");
				pointageDTO.setTotal(0);
				pointageDTO.setErrors(listErreur);
			} 
			
			if(listErreur.isEmpty()) {
				pointageRepository.delete(pointage);
				sb = new StringBuilder();
				sb.append(" pointage supprime avec succes");
				pointageDTO.setResult(true);
				pointageDTO.setStatus(true);
				pointageDTO.setRow(pointage);
				pointageDTO.setRows(null);
				pointageDTO.setMessage(sb.toString());
				pointageDTO.setTotal(0);
				pointageDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			pointageDTO.setResult(false);
			pointageDTO.setStatus(false);
			pointageDTO.setRow(null);
			pointageDTO.setRows(null);
			pointageDTO.setMessage(ex.getMessage());
			pointageDTO.setTotal(0);
			pointageDTO.setErrors(listErreur);
		}
		return pointageDTO;
	}

	@Override
	public PointageDTO findPointage(Long id) {
		// TODO Auto-generated method stub
		
		PointageDTO pointageDTO = new PointageDTO();
		Pointage pointage;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		
		try{
			pointage = pointageRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			if(pointage == null){
				pointageDTO.setResult(true);
				pointageDTO.setStatus(true);
				pointageDTO.setRow(pointage);
				pointageDTO.setRows(null);
				pointageDTO.setMessage("pointage inexistant dans le systeme");
				pointageDTO.setTotal(1);
				pointageDTO.setErrors(listErreur);
			} else {
				pointageDTO.setResult(true);
				pointageDTO.setStatus(true);
				pointageDTO.setRow(pointage);
				pointageDTO.setRows(null);
				pointageDTO.setMessage("pointage trouve avec succes");
				pointageDTO.setTotal(1);
				pointageDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			pointageDTO.setResult(false);
			pointageDTO.setStatus(false);
			pointageDTO.setRow(null);
			pointageDTO.setRows(null);
			pointageDTO.setMessage(ex.getMessage());
			pointageDTO.setTotal(0);
			pointageDTO.setErrors(listErreur);
		}
		return pointageDTO;
	}

	@Override
	public PointageDTO findPointages() {
		// TODO Auto-generated method stub
		
		PointageDTO pointageDTO = new PointageDTO();
		List<Pointage> listPointage;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		
		try{
			listPointage = pointageRepository.findAll();
			if(listPointage == null){
				pointageDTO.setResult(true);
				pointageDTO.setStatus(true);
				pointageDTO.setRow(null);
				pointageDTO.setRows(new ArrayList<Pointage>());
				pointageDTO.setMessage("aucun pointage trouve");
				pointageDTO.setTotal(0);
				pointageDTO.setErrors(listErreur);
			} else {
				int i = listPointage.size();
				sb = new StringBuilder();
				sb.append(i).append(" pointage(s) trouve(s) avec succes");
				pointageDTO.setResult(true);
				pointageDTO.setStatus(true);
				pointageDTO.setRow(null);
				pointageDTO.setRows(listPointage);
				pointageDTO.setMessage(sb.toString());
				pointageDTO.setTotal(i);
				pointageDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			pointageDTO.setResult(false);
			pointageDTO.setStatus(false);
			pointageDTO.setRow(null);
			pointageDTO.setRows(null);
			pointageDTO.setMessage(ex.getMessage());
			pointageDTO.setTotal(0);
			pointageDTO.setErrors(listErreur);
		}
		return pointageDTO;
	}

	@Override
	public PointageDTO findPointagesByPersonnel(Long idPersonnel) {
		// TODO Auto-generated method stub
		
		PointageDTO pointageDTO = new PointageDTO();
		List<Pointage> listPointage;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		
		try{
			listPointage = pointageRepository.findByPersonnelId(idPersonnel);
			if(listPointage == null){
				pointageDTO.setResult(true);
				pointageDTO.setStatus(true);
				pointageDTO.setRow(null);
				pointageDTO.setRows(new ArrayList<Pointage>());
				pointageDTO.setMessage("aucun pointage trouvee");
				pointageDTO.setTotal(0);
				pointageDTO.setErrors(listErreur);
			} else {
				int i = listPointage.size();
				sb = new StringBuilder();
				sb.append(i).append(" pointage(s) trouve(s) avec succes");
				pointageDTO.setResult(true);
				pointageDTO.setStatus(true);
				pointageDTO.setRow(null);
				pointageDTO.setRows(listPointage);
				pointageDTO.setMessage(sb.toString());
				pointageDTO.setTotal(i);
				pointageDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			pointageDTO.setResult(false);
			pointageDTO.setStatus(false);
			pointageDTO.setRow(null);
			pointageDTO.setRows(null);
			pointageDTO.setMessage(ex.getMessage());
			pointageDTO.setTotal(0);
			pointageDTO.setErrors(listErreur);
		}
		return pointageDTO;
	}

	@Override
	public PointageDTO findPointagesByPersonnelsAndDate(List<Long> listPersonnel, String date) {
		// TODO Auto-generated method stub
		PointageDTO pointageDTO = new PointageDTO();
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		
		Pointage pointage = new Pointage();
		List<Pointage> listPointage = new ArrayList<Pointage>();
		try{
			Date datePointage = DateManager.stringToDate(date, "dd/MM/yyyy");
			for(Long idPersonnel : listPersonnel){
				pointage = new Pointage();
				pointage = pointageRepository.findByPersonnelIdAndDatePointage(idPersonnel, datePointage);
				listPointage.add(pointage);
			}
			pointageDTO.setResult(true);
			pointageDTO.setStatus(true);
			pointageDTO.setRow(null);
			pointageDTO.setRows(listPointage);
			pointageDTO.setMessage("");
			pointageDTO.setTotal(listPointage.size());
			pointageDTO.setErrors(listErreur);
		} catch(Exception ex){

			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			pointageDTO.setResult(false);
			pointageDTO.setStatus(false);
			pointageDTO.setRow(null);
			pointageDTO.setRows(null);
			pointageDTO.setMessage(ex.getMessage());
			pointageDTO.setTotal(0);
			pointageDTO.setErrors(listErreur);
		}
		return pointageDTO;
	}

	@Override
	public PointageDTO findPointagesByPersonnelAndDatePointage(Long idPersonnel, String datePointage) {
		// TODO Auto-generated method stub
		
		PointageDTO pointageDTO = new PointageDTO();
		Pointage pointage;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		
		try{
			pointage = pointageRepository.findByPersonnelIdAndDatePointage(idPersonnel, DateManager.stringToDate(datePointage, "HH:mm:ss"));
			if(pointage == null){
				pointageDTO.setResult(true);
				pointageDTO.setStatus(true);
				pointageDTO.setRow(pointage);
				pointageDTO.setRows(null);
				pointageDTO.setMessage("pointage inexistant dans le systeme");
				pointageDTO.setTotal(1);
				pointageDTO.setErrors(listErreur);
			} else {
				pointageDTO.setResult(true);
				pointageDTO.setStatus(true);
				pointageDTO.setRow(pointage);
				pointageDTO.setRows(null);
				pointageDTO.setMessage("pointage trouve avec succes");
				pointageDTO.setTotal(1);
				pointageDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			pointageDTO.setResult(false);
			pointageDTO.setStatus(false);
			pointageDTO.setRow(null);
			pointageDTO.setRows(null);
			pointageDTO.setMessage(ex.getMessage());
			pointageDTO.setTotal(0);
			pointageDTO.setErrors(listErreur);
		}
		return pointageDTO;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) pointageRepository.count();
	}

	
	@Override
	public PointageDTO loadPointages(Pageable pageable) {
		// TODO Auto-generated method stub
		PointageDTO pointageDTO = new PointageDTO();
		Page<Pointage> page = pointageRepository.findAll(pageable);
		pointageDTO.setResult(true);
		pointageDTO.setStatus(true);
		pointageDTO.setRows(page.getContent());
		pointageDTO.setTotal(page.getTotalElements());
		return pointageDTO;
	}

	@Override
	public PointageDTO loadPointages(Pageable pageable, String nom, String prenom) {
		// TODO Auto-generated method stub
		PointageDTO pointageDTO = new PointageDTO();
		Page<Pointage> page = pointageRepository.findByPersonnelNomContainingOrPersonnelPrenomContaining(pageable, nom, prenom);
		pointageDTO.setResult(true);
		pointageDTO.setStatus(true);
		pointageDTO.setRows(page.getContent());
		pointageDTO.setTotal(page.getTotalElements());
		return pointageDTO;
	}

	@Override
	public PointageDTO findPointagesByPersonnelAndDatePointageBetween(Pageable pageable,Long idPersonnel, String datePointage, String datePointage1) {
		// TODO Auto-generated method stub
		
		PointageDTO pointageDTO = new PointageDTO();
		Pointage pointage;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		Date dateDeb;Date dateFin;
		java.sql.Date dateDebE = null ;
		java.sql.Date dateFinE = null ;
		try {
			dateDeb = DateManager.stringToDate(datePointage, "dd/MM/yyyy");
			dateFin = DateManager.stringToDate(datePointage1, "dd/MM/yyyy");
			dateDebE= new java.sql.Date(dateDeb.getTime());
			dateFinE= new java.sql.Date(dateFin.getTime());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Page<Pointage> page = pointageRepository.findByPersonnelIdAndDatePointageBetween(pageable, 2L, dateDebE, dateFinE);
		pointageDTO.setRows(page.getContent());
		pointageDTO.setTotal(page.getTotalElements());
		//logger.info(new StringBuilder().append(">>>>> CONTRATS PERSONNELS CHARGES AVEC SUCCES").toString());
		return pointageDTO;
	}

	@Override
	public PointageDTO findPointagesByDatePointageBetween(Pageable pageable,String datePointage, String datePointage1) {
		// TODO Auto-generated method stub
		Date dateDeb;Date dateFin;
		java.sql.Date dateDebE = null ;
		java.sql.Date dateFinE = null ;
		try {
			dateDeb = DateManager.stringToDate(datePointage, "dd/MM/yyyy");
			dateFin = DateManager.stringToDate(datePointage1, "dd/MM/yyyy");
			dateDebE= new java.sql.Date(dateDeb.getTime());
			dateFinE= new java.sql.Date(dateFin.getTime());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PointageDTO contratPersonnelDTO = new PointageDTO();
		Page<Pointage> page = pointageRepository.findByDatePointageBetween(pageable,  dateDebE, dateFinE);
		contratPersonnelDTO.setRows(page.getContent());
		contratPersonnelDTO.setTotal(page.getTotalElements());
		//logger.info(new StringBuilder().append(">>>>> CONTRATS PERSONNELS CHARGES AVEC SUCCES").toString());
		return contratPersonnelDTO;
	}

}
