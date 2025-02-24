package com.nectux.mizan.hyban.rh.formation.service.impl;

import com.nectux.mizan.hyban.rh.formation.dto.FormationDTO;
import com.nectux.mizan.hyban.rh.formation.entity.Formation;
import com.nectux.mizan.hyban.rh.formation.repository.CabinetFormationRepository;
import com.nectux.mizan.hyban.rh.formation.repository.DemandeFormationRepository;
import com.nectux.mizan.hyban.rh.formation.repository.FormationRepository;
import com.nectux.mizan.hyban.rh.formation.repository.ThemeRepository;
import com.nectux.mizan.hyban.rh.formation.service.FormationService;
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
@Service("formationService")
public class FormationServiceImpl implements FormationService {
	
	@Autowired
    private ThemeRepository themeRepository;
	@Autowired
    private FormationRepository formationRepository;
	@Autowired
    private DemandeFormationRepository demandeFormationRepository;
	@Autowired
    private CabinetFormationRepository cabinetFormationRepository;

	private StringBuilder sb;
	private Erreur erreur;
	private List<Erreur> listErreur;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public FormationDTO save(Long id, Long idTheme, Long idDemandeFormation, Long idCabinetFormation,String intitule, String dateDebut,
			String dateFin, String datePrevue, String lieu, int participant, String planFormation, String objectif, String commentaire) {
		
		FormationDTO formationDTO = new FormationDTO();
		Formation formation;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		
		try{ 
			if(id == null){
				formation = new Formation();
				formation.setDateCreation(new Date());
			} else {
				formation = formationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
				formation.setDateModification(new Date());
			}
			
			formation.setIntitule(intitule);
			formation.setDateDebut(DateManager.stringToDate(dateDebut, "dd/MM/yyyy"));
			formation.setDateFin(DateManager.stringToDate(dateFin, "dd/MM/yyyy"));
			formation.setDatePrevue(DateManager.stringToDate(datePrevue, "dd/MM/yyyy"));
			formation.setLieu(lieu);
			formation.setNbreParticipant(participant);
			formation.setPlan(planFormation);
			formation.setObjectif(objectif);
			formation.setCommentaire(commentaire);
			if(idTheme==null)
			formation.setTheme(null);
			else
				formation.setTheme(themeRepository.findById(idTheme).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + idTheme)));

			formation.setDemandeFormation(demandeFormationRepository.findById(idDemandeFormation).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + idDemandeFormation)));
			formation.setCabinetFormation(cabinetFormationRepository.findById(idCabinetFormation).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + idCabinetFormation)));

			if(listErreur.isEmpty()){
				formation = formationRepository.save(formation);
				sb = new StringBuilder();
				sb.append(formation.getIntitule()).append(" enregistre avec succes");
				formationDTO.setResult(true);
				formationDTO.setStatus(true);
				formationDTO.setRow(formation);
				formationDTO.setRows(null);
				formationDTO.setMessage(sb.toString());
				formationDTO.setTotal(0);
				formationDTO.setErrors(listErreur);
			} else {
				formationDTO.setResult(false);
				formationDTO.setStatus(false);
				formationDTO.setRow(null);
				formationDTO.setRows(null);
				formationDTO.setMessage("voir liste erreur");
				formationDTO.setTotal(0);
				formationDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			formationDTO.setResult(false);
			formationDTO.setStatus(false);
			formationDTO.setRow(null);
			formationDTO.setRows(null);
			formationDTO.setMessage(ex.getMessage());
			formationDTO.setTotal(0);
			formationDTO.setErrors(listErreur);
		}
		return formationDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public FormationDTO delete(Long id) {
		// TODO Auto-generated method stub
		
		FormationDTO formationDTO = new FormationDTO();
		Formation formation;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		
		try{ 
			formation = formationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			if(formation == null){
				sb = new StringBuilder();
				erreur.setCode(12);
				erreur.setDescription("formation inexistante");
				sb.append("cette formation est inexistante dans le systeme");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
				
				formationDTO.setResult(false);
				formationDTO.setStatus(false);
				formationDTO.setRow(null);
				formationDTO.setRows(null);
				formationDTO.setMessage("voir liste erreur");
				formationDTO.setTotal(0);
				formationDTO.setErrors(listErreur);
			} 
			
			if(listErreur.isEmpty()) {
				formationRepository.delete(formation);
				sb = new StringBuilder();
				sb.append(formation.getIntitule()).append(" supprimee avec succes");
				formationDTO.setResult(true);
				formationDTO.setStatus(true);
				formationDTO.setRow(formation);
				formationDTO.setRows(null);
				formationDTO.setMessage(sb.toString());
				formationDTO.setTotal(0);
				formationDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			formationDTO.setResult(false);
			formationDTO.setStatus(false);
			formationDTO.setRow(null);
			formationDTO.setRows(null);
			formationDTO.setMessage(ex.getMessage());
			formationDTO.setTotal(0);
			formationDTO.setErrors(listErreur);
		}
		return formationDTO;
	}

	@Override
	public FormationDTO findFormation(Long id) {
		// TODO Auto-generated method stub
		
		FormationDTO formationDTO = new FormationDTO();
		Formation formation;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		
		try{
			formation = formationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			if(formation == null){
				formationDTO.setResult(true);
				formationDTO.setStatus(true);
				formationDTO.setRow(formation);
				formationDTO.setRows(null);
				formationDTO.setMessage("formation inexistante dans le systeme");
				formationDTO.setTotal(1);
				formationDTO.setErrors(listErreur);
			} else {
				formationDTO.setResult(true);
				formationDTO.setStatus(true);
				formationDTO.setRow(formation);
				formationDTO.setRows(null);
				formationDTO.setMessage("formation trouvee avec succes");
				formationDTO.setTotal(1);
				formationDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			formationDTO.setResult(false);
			formationDTO.setStatus(false);
			formationDTO.setRow(null);
			formationDTO.setRows(null);
			formationDTO.setMessage(ex.getMessage());
			formationDTO.setTotal(0);
			formationDTO.setErrors(listErreur);
		}
		return formationDTO;
	}

	@Override
	public FormationDTO findFormations() {
		// TODO Auto-generated method stub
		
		FormationDTO formationDTO = new FormationDTO();
		List<Formation> listFormation;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		
		try{ 
			listFormation = formationRepository.findAll();
			if(listFormation == null){
				formationDTO.setResult(true);
				formationDTO.setStatus(true);
				formationDTO.setRow(null);
				formationDTO.setRows(new ArrayList<Formation>());
				formationDTO.setMessage("aucune formation trouve");
				formationDTO.setTotal(0);
				formationDTO.setErrors(listErreur);
			} else {
				int i = listFormation.size();
				sb = new StringBuilder();
				sb.append(i).append(" formation(s) trouvee(s) avec succes");
				formationDTO.setResult(true);
				formationDTO.setStatus(true);
				formationDTO.setRow(null);
				formationDTO.setRows(listFormation);
				formationDTO.setMessage(sb.toString());
				formationDTO.setTotal(i);
				formationDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			formationDTO.setResult(false);
			formationDTO.setStatus(false);
			formationDTO.setRow(null);
			formationDTO.setRows(null);
			formationDTO.setMessage(ex.getMessage());
			formationDTO.setTotal(0);
			formationDTO.setErrors(listErreur);
		}
		return formationDTO;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) formationRepository.count();
	}

	@Override
	public FormationDTO loadFormations(Pageable pageable) {
		// TODO Auto-generated method stub
		FormationDTO formationDTO = new FormationDTO();
		Page<Formation> page = formationRepository.findAll(pageable);
		formationDTO.setResult(true);
		formationDTO.setStatus(true);
		formationDTO.setRows(page.getContent());
		formationDTO.setTotal(page.getTotalElements());
		return formationDTO;
	}

	@Override
	public FormationDTO loadFormations(Pageable pageable, String intitule, String lieu, String themeIntitule,
			String demandeFormationObjet) {
		// TODO Auto-generated method stub
		FormationDTO formationDTO = new FormationDTO();
		Page<Formation> page = formationRepository.findByIntituleContainingOrLieuContainingOrThemeIntituleContainingOrDemandeFormationObjetContaining(pageable, intitule, lieu, themeIntitule, demandeFormationObjet);
		formationDTO.setResult(true);
		formationDTO.setStatus(true);
		formationDTO.setRows(page.getContent());
		formationDTO.setTotal(page.getTotalElements());
		return formationDTO;
	}

}
