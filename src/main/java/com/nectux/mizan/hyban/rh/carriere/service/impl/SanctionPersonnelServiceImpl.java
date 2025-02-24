package com.nectux.mizan.hyban.rh.carriere.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nectux.mizan.hyban.personnel.repository.PersonnelRepository;
import com.nectux.mizan.hyban.rh.carriere.dto.SanctionPersonnelDTO;
import com.nectux.mizan.hyban.rh.carriere.entity.SanctionPersonnel;
import com.nectux.mizan.hyban.rh.carriere.repository.SanctionPersonnelRepository;
import com.nectux.mizan.hyban.rh.carriere.repository.SanctionRepository;
import com.nectux.mizan.hyban.rh.carriere.service.SanctionPersonnelService;
import com.nectux.mizan.hyban.utils.DateManager;
import com.nectux.mizan.hyban.utils.Erreur;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service("sanctionPersonnelService")
public class SanctionPersonnelServiceImpl implements SanctionPersonnelService {
	
	@Autowired private SanctionRepository sanctionRepository;
	@Autowired private PersonnelRepository personnelRepository;
	//@Autowired private TypeSanctionRepository typeSanctionRepository;
	@Autowired private SanctionPersonnelRepository sanctionPersonnelRepository;
	
	private StringBuilder sb;
	private Erreur erreur;
	private List<Erreur> listErreur;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public SanctionPersonnelDTO save(Long id, Long idPersonnel, Long idSanction, String dateDebut, String dateFin, String observation) {
		// TODO Auto-generated method stub
		SanctionPersonnelDTO sanctionPersonnelDTO = new SanctionPersonnelDTO();
		listErreur = new ArrayList<Erreur>();
		SanctionPersonnel sanctionPersonnel;
		try{
			if(id == null){
				sanctionPersonnel = new SanctionPersonnel();
				sanctionPersonnel.setDateCreation(new Date());
			} else {
				sanctionPersonnel = sanctionPersonnelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
				sanctionPersonnel.setDateModification(new Date());
			}
			sanctionPersonnel.setObservation(observation);
			
			if(idPersonnel == null){
				sb = new StringBuilder();
				erreur = new Erreur();
				erreur.setCode(10);
				erreur.setDescription("contrainte d'integrite non null non respectee");
				sb.append("le personnel est obligatoire");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
			} else 
				sanctionPersonnel.setPersonnel(personnelRepository.findById(idPersonnel).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + idPersonnel)));
			
			if(idSanction == null){
				sb = new StringBuilder();
				erreur = new Erreur();
				erreur.setCode(10);
				erreur.setDescription("contrainte d'integrite non null non respectee");
				sb.append("la sanction est obligatoire");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
			} else 
				sanctionPersonnel.setSanction(sanctionRepository.findById(idSanction).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + idSanction)));
			
			if(dateDebut == null || dateDebut.trim().equals("")){
				sb = new StringBuilder();
				erreur = new Erreur();
				erreur.setCode(10);
				erreur.setDescription("contrainte d'integrite non null non respectee");
				sb.append("la date de debut est obligatoire");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
			} else 
				sanctionPersonnel.setDateDebut(DateManager.stringToDate(dateDebut, "dd/MM/yyyy"));
			
			if(dateFin != null && !dateFin.trim().equals(""))
				sanctionPersonnel.setDateFin(DateManager.stringToDate(dateFin, "dd/MM/yyyy"));
			
			if(listErreur.isEmpty()){
				sanctionPersonnel = sanctionPersonnelRepository.save(sanctionPersonnel);
				sb = new StringBuilder();
				sb.append(" sanction enregistree avec succes");
				sanctionPersonnelDTO.setResult(true);
				sanctionPersonnelDTO.setStatus(true);
				sanctionPersonnelDTO.setRow(sanctionPersonnel);
				sanctionPersonnelDTO.setRows(null);
				sanctionPersonnelDTO.setMessage(sb.toString());
				sanctionPersonnelDTO.setTotal(0);
				sanctionPersonnelDTO.setErrors(listErreur);
			} else {
				sanctionPersonnelDTO.setResult(false);
				sanctionPersonnelDTO.setStatus(false);
				sanctionPersonnelDTO.setRow(null);
				sanctionPersonnelDTO.setRows(null);
				sanctionPersonnelDTO.setMessage("voir liste erreur");
				sanctionPersonnelDTO.setTotal(0);
				sanctionPersonnelDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur = new Erreur();
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			sanctionPersonnelDTO.setResult(false);
			sanctionPersonnelDTO.setStatus(false);
			sanctionPersonnelDTO.setRow(null);
			sanctionPersonnelDTO.setRows(null);
			sanctionPersonnelDTO.setMessage(ex.getMessage());
			sanctionPersonnelDTO.setTotal(0);
			sanctionPersonnelDTO.setErrors(listErreur);
		}
		return sanctionPersonnelDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public SanctionPersonnelDTO save(Long id, Long idTypeSanction, Long idPersonnel, String faute, String commentaire,
			String dateDebut, String dateFin, String observation) {
		// TODO Auto-generated method stub
		SanctionPersonnelDTO sanctionPersonnelDTO = new SanctionPersonnelDTO();
		listErreur = new ArrayList<Erreur>();
		try{

		} catch(Exception ex){
			erreur = new Erreur();
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			sanctionPersonnelDTO.setResult(false);
			sanctionPersonnelDTO.setStatus(false);
			sanctionPersonnelDTO.setRow(null);
			sanctionPersonnelDTO.setRows(null);
			sanctionPersonnelDTO.setMessage(ex.getMessage());
			sanctionPersonnelDTO.setTotal(0);
			sanctionPersonnelDTO.setErrors(listErreur);
		}
		return sanctionPersonnelDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public SanctionPersonnelDTO delete(Long id) {
		// TODO Auto-generated method stub
		SanctionPersonnelDTO sanctionPersonnelDTO = new SanctionPersonnelDTO();
		SanctionPersonnel sanctionPersonnel;
		listErreur = new ArrayList<Erreur>();
		try{
			sanctionPersonnel = sanctionPersonnelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			if(sanctionPersonnel == null){
				sb = new StringBuilder();
				erreur.setCode(12);
				erreur.setDescription("sanction inexistante");
				sb.append("cette sanction est inexistante dans le systeme");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
				
				sanctionPersonnelDTO.setResult(false);
				sanctionPersonnelDTO.setStatus(false);
				sanctionPersonnelDTO.setRow(null);
				sanctionPersonnelDTO.setRows(null);
				sanctionPersonnelDTO.setMessage("voir liste erreur");
				sanctionPersonnelDTO.setTotal(0);
				sanctionPersonnelDTO.setErrors(listErreur);
			} 
			
			if(listErreur.isEmpty()) {
				sanctionPersonnelRepository.delete(sanctionPersonnel);
				sb = new StringBuilder();
				sb.append(" affectation supprimee avec succes");
				sanctionPersonnelDTO.setResult(true);
				sanctionPersonnelDTO.setStatus(true);
				sanctionPersonnelDTO.setRow(sanctionPersonnel);
				sanctionPersonnelDTO.setRows(null);
				sanctionPersonnelDTO.setMessage(sb.toString());
				sanctionPersonnelDTO.setTotal(0);
				sanctionPersonnelDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur = new Erreur();
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			sanctionPersonnelDTO.setResult(false);
			sanctionPersonnelDTO.setStatus(false);
			sanctionPersonnelDTO.setRow(null);
			sanctionPersonnelDTO.setRows(null);
			sanctionPersonnelDTO.setMessage(ex.getMessage());
			sanctionPersonnelDTO.setTotal(0);
			sanctionPersonnelDTO.setErrors(listErreur);
		}
		return sanctionPersonnelDTO;
	}

	@Override
	public SanctionPersonnelDTO findSanctionPersonnel(Long id) {
		// TODO Auto-generated method stub
		SanctionPersonnelDTO sanctionPersonnelDTO = new SanctionPersonnelDTO();
		SanctionPersonnel sanctionPersonnel;
		listErreur = new ArrayList<Erreur>();
		try{
			sanctionPersonnel = sanctionPersonnelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			if(sanctionPersonnel == null){
				sanctionPersonnelDTO.setResult(true);
				sanctionPersonnelDTO.setStatus(true);
				sanctionPersonnelDTO.setRow(sanctionPersonnel);
				sanctionPersonnelDTO.setRows(null);
				sanctionPersonnelDTO.setMessage("sanction inexistante dans le systeme");
				sanctionPersonnelDTO.setTotal(1);
				sanctionPersonnelDTO.setErrors(listErreur);
			} else {
				sanctionPersonnelDTO.setResult(true);
				sanctionPersonnelDTO.setStatus(true);
				sanctionPersonnelDTO.setRow(sanctionPersonnel);
				sanctionPersonnelDTO.setRows(null);
				sanctionPersonnelDTO.setMessage("sanction trouvee avec succes");
				sanctionPersonnelDTO.setTotal(1);
				sanctionPersonnelDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur = new Erreur();
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			sanctionPersonnelDTO.setResult(false);
			sanctionPersonnelDTO.setStatus(false);
			sanctionPersonnelDTO.setRow(null);
			sanctionPersonnelDTO.setRows(null);
			sanctionPersonnelDTO.setMessage(ex.getMessage());
			sanctionPersonnelDTO.setTotal(0);
			sanctionPersonnelDTO.setErrors(listErreur);
		}
		return sanctionPersonnelDTO;
	}

	@Override
	public SanctionPersonnelDTO findSanctionPersonnels() {
		// TODO Auto-generated method stub
		SanctionPersonnelDTO sanctionPersonnelDTO = new SanctionPersonnelDTO();
		List<SanctionPersonnel> listSanctionPersonnel = new ArrayList<SanctionPersonnel>();
		listErreur = new ArrayList<Erreur>();
		try{
			listSanctionPersonnel = sanctionPersonnelRepository.findAll();
			if(listSanctionPersonnel == null){
				sanctionPersonnelDTO.setResult(true);
				sanctionPersonnelDTO.setStatus(true);
				sanctionPersonnelDTO.setRow(null);
				sanctionPersonnelDTO.setRows(new ArrayList<SanctionPersonnel>());
				sanctionPersonnelDTO.setMessage("aucune sanction trouvee");
				sanctionPersonnelDTO.setTotal(0);
				sanctionPersonnelDTO.setErrors(listErreur);
			} else {
				int i = listSanctionPersonnel.size();
				sb = new StringBuilder();
				sb.append(i).append(" sanction(s) trouvee(s) avec succes");
				sanctionPersonnelDTO.setResult(true);
				sanctionPersonnelDTO.setStatus(true);
				sanctionPersonnelDTO.setRow(null);
				sanctionPersonnelDTO.setRows(listSanctionPersonnel);
				sanctionPersonnelDTO.setMessage(sb.toString());
				sanctionPersonnelDTO.setTotal(i);
				sanctionPersonnelDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur = new Erreur();
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			sanctionPersonnelDTO.setResult(false);
			sanctionPersonnelDTO.setStatus(false);
			sanctionPersonnelDTO.setRow(null);
			sanctionPersonnelDTO.setRows(null);
			sanctionPersonnelDTO.setMessage(ex.getMessage());
			sanctionPersonnelDTO.setTotal(0);
			sanctionPersonnelDTO.setErrors(listErreur);
		}
		return sanctionPersonnelDTO;
	}

	@Override
	public SanctionPersonnelDTO findSanctionPersonnelsBySanction(Long idSanction) {
		// TODO Auto-generated method stub
		SanctionPersonnelDTO sanctionPersonnelDTO = new SanctionPersonnelDTO();
		List<SanctionPersonnel> listSanctionPersonnel = new ArrayList<SanctionPersonnel>();
		listErreur = new ArrayList<Erreur>();
		try{
			listSanctionPersonnel = sanctionPersonnelRepository.findBySanctionId(idSanction);
			if(listSanctionPersonnel == null){
				sanctionPersonnelDTO.setResult(true);
				sanctionPersonnelDTO.setStatus(true);
				sanctionPersonnelDTO.setRow(null);
				sanctionPersonnelDTO.setRows(new ArrayList<SanctionPersonnel>());
				sanctionPersonnelDTO.setMessage("aucune affectation trouvee");
				sanctionPersonnelDTO.setTotal(0);
				sanctionPersonnelDTO.setErrors(listErreur);
			} else {
				int i = listSanctionPersonnel.size();
				sb = new StringBuilder();
				sb.append(i).append(" sanction(s) trouvee(s) avec succes");
				sanctionPersonnelDTO.setResult(true);
				sanctionPersonnelDTO.setStatus(true);
				sanctionPersonnelDTO.setRow(null);
				sanctionPersonnelDTO.setRows(listSanctionPersonnel);
				sanctionPersonnelDTO.setMessage(sb.toString());
				sanctionPersonnelDTO.setTotal(i);
				sanctionPersonnelDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur = new Erreur();
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			sanctionPersonnelDTO.setResult(false);
			sanctionPersonnelDTO.setStatus(false);
			sanctionPersonnelDTO.setRow(null);
			sanctionPersonnelDTO.setRows(null);
			sanctionPersonnelDTO.setMessage(ex.getMessage());
			sanctionPersonnelDTO.setTotal(0);
			sanctionPersonnelDTO.setErrors(listErreur);
		}
		return sanctionPersonnelDTO;
	}

	@Override
	public SanctionPersonnelDTO findSanctionPersonnelsByPersonnel(Long idPersonnel) {
		// TODO Auto-generated method stub
		SanctionPersonnelDTO sanctionPersonnelDTO = new SanctionPersonnelDTO();
		List<SanctionPersonnel> listSanctionPersonnel = new ArrayList<SanctionPersonnel>();
		listErreur = new ArrayList<Erreur>();
		try{
			listSanctionPersonnel = sanctionPersonnelRepository.findByPersonnelId(idPersonnel);
			if(listSanctionPersonnel == null){
				sanctionPersonnelDTO.setResult(true);
				sanctionPersonnelDTO.setStatus(true);
				sanctionPersonnelDTO.setRow(null);
				sanctionPersonnelDTO.setRows(new ArrayList<SanctionPersonnel>());
				sanctionPersonnelDTO.setMessage("aucune affectation trouvee");
				sanctionPersonnelDTO.setTotal(0);
				sanctionPersonnelDTO.setErrors(listErreur);
			} else {
				int i = listSanctionPersonnel.size();
				sb = new StringBuilder();
				sb.append(i).append(" sanction(s) trouvee(s) avec succes");
				sanctionPersonnelDTO.setResult(true);
				sanctionPersonnelDTO.setStatus(true);
				sanctionPersonnelDTO.setRow(null);
				sanctionPersonnelDTO.setRows(listSanctionPersonnel);
				sanctionPersonnelDTO.setMessage(sb.toString());
				sanctionPersonnelDTO.setTotal(i);
				sanctionPersonnelDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur = new Erreur();
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			sanctionPersonnelDTO.setResult(false);
			sanctionPersonnelDTO.setStatus(false);
			sanctionPersonnelDTO.setRow(null);
			sanctionPersonnelDTO.setRows(null);
			sanctionPersonnelDTO.setMessage(ex.getMessage());
			sanctionPersonnelDTO.setTotal(0);
			sanctionPersonnelDTO.setErrors(listErreur);
		}
		return sanctionPersonnelDTO;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) sanctionPersonnelRepository.count();
	}

	@Override
	public SanctionPersonnelDTO loadSanctionPersonnels(Pageable pageable) {
		// TODO Auto-generated method stub
		SanctionPersonnelDTO sanctionPersonnelDTO = new SanctionPersonnelDTO();
		Page<SanctionPersonnel> page = sanctionPersonnelRepository.findAll(pageable);
		sanctionPersonnelDTO.setResult(true);
		sanctionPersonnelDTO.setStatus(true);
		sanctionPersonnelDTO.setRows(page.getContent());
		sanctionPersonnelDTO.setTotal(page.getTotalElements());
		return sanctionPersonnelDTO;
	}

	@Override
	public SanctionPersonnelDTO loadSanctionPersonnels(Pageable pageable, String nom, String prenom, String faute, String typeSanction) {
		// TODO Auto-generated method stub
		SanctionPersonnelDTO sanctionPersonnelDTO = new SanctionPersonnelDTO();
		Page<SanctionPersonnel> page = sanctionPersonnelRepository.findByPersonnelNomContainingOrPersonnelPrenomContainingOrSanctionFauteContainingOrSanctionTypeSanctionContaining(pageable, nom, prenom, faute, typeSanction);
		sanctionPersonnelDTO.setResult(true);
		sanctionPersonnelDTO.setStatus(true);
		sanctionPersonnelDTO.setRows(page.getContent());
		sanctionPersonnelDTO.setTotal(page.getTotalElements());
		return sanctionPersonnelDTO;
	}

}
