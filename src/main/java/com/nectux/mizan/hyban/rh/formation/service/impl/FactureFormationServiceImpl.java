package com.nectux.mizan.hyban.rh.formation.service.impl;

import com.nectux.mizan.hyban.rh.formation.dto.FactureFormationDTO;
import com.nectux.mizan.hyban.rh.formation.entity.FactureFormation;
import com.nectux.mizan.hyban.rh.formation.repository.CabinetFormationRepository;
import com.nectux.mizan.hyban.rh.formation.repository.FactureFormationRepository;
import com.nectux.mizan.hyban.rh.formation.repository.FormationRepository;
import com.nectux.mizan.hyban.rh.formation.service.FactureFormationService;
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
@Service("factureFormationService")
public class FactureFormationServiceImpl implements FactureFormationService {
	
	@Autowired
    private FormationRepository formationRepository;
	@Autowired
    private CabinetFormationRepository cabinetFormationRepository;
	@Autowired
    private FactureFormationRepository factureFormationRepository;
	
	private StringBuilder sb;
	private Erreur erreur;
	private List<Erreur> listErreur;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public FactureFormationDTO save(Long id, Long idCabinet, Long idFormation, String reference, String dateEmission,
			Double montant, Boolean statut) {
		// TODO Auto-generated method stub
		
		FactureFormationDTO factureFormationDTO = new FactureFormationDTO();
		FactureFormation factureFormation;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		
		try{ 
			if(id == null){
				factureFormation = new FactureFormation();
				factureFormation.setDateCreation(new Date());
			} else {
				factureFormation = factureFormationRepository.findById(id) .orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
				factureFormation.setDateModification(new Date());
			}
			
			factureFormation.setReference(reference);
			factureFormation.setDateEmission(DateManager.stringToDate(dateEmission, "dd/MM/yyyy"));
			factureFormation.setMontant(montant);
			factureFormation.setStatut(statut);
			factureFormation.setFormation(formationRepository.findById(idFormation) .orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + idFormation)));
			factureFormation.setCabinetFormation(cabinetFormationRepository.findById(idCabinet) .orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + idCabinet)));
			
			if(listErreur.isEmpty()){
				factureFormation = factureFormationRepository.save(factureFormation);
				sb = new StringBuilder();
				sb.append(factureFormation.getReference()).append(" enregistre avec succes");
				factureFormationDTO.setResult(true);
				factureFormationDTO.setStatus(true);
				factureFormationDTO.setRow(factureFormation);
				factureFormationDTO.setRows(null);
				factureFormationDTO.setMessage(sb.toString());
				factureFormationDTO.setTotal(0);
				factureFormationDTO.setErrors(listErreur);
			} else {
				factureFormationDTO.setResult(false);
				factureFormationDTO.setStatus(false);
				factureFormationDTO.setRow(null);
				factureFormationDTO.setRows(null);
				factureFormationDTO.setMessage("voir liste erreur");
				factureFormationDTO.setTotal(0);
				factureFormationDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			factureFormationDTO.setResult(false);
			factureFormationDTO.setStatus(false);
			factureFormationDTO.setRow(null);
			factureFormationDTO.setRows(null);
			factureFormationDTO.setMessage(ex.getMessage());
			factureFormationDTO.setTotal(0);
			factureFormationDTO.setErrors(listErreur);
		}
		return factureFormationDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public FactureFormationDTO delete(Long id) {
		// TODO Auto-generated method stub
		
		FactureFormationDTO factureFormationDTO = new FactureFormationDTO();
		FactureFormation factureFormation;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		
		try{ 
			factureFormation = factureFormationRepository.findById(id) .orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			if(factureFormation == null){
				sb = new StringBuilder();
				erreur.setCode(12);
				erreur.setDescription("facture inexistant");
				sb.append("cette facture est inexistante dans le systeme");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
				
				factureFormationDTO.setResult(false);
				factureFormationDTO.setStatus(false);
				factureFormationDTO.setRow(null);
				factureFormationDTO.setRows(null);
				factureFormationDTO.setMessage("voir liste erreur");
				factureFormationDTO.setTotal(0);
				factureFormationDTO.setErrors(listErreur);
			} 
			
			if(listErreur.isEmpty()) {
				factureFormationRepository.delete(factureFormation);
				sb = new StringBuilder();
				sb.append(factureFormation.getReference()).append(" supprime avec succes");
				factureFormationDTO.setResult(true);
				factureFormationDTO.setStatus(true);
				factureFormationDTO.setRow(factureFormation);
				factureFormationDTO.setRows(null);
				factureFormationDTO.setMessage(sb.toString());
				factureFormationDTO.setTotal(0);
				factureFormationDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			factureFormationDTO.setResult(false);
			factureFormationDTO.setStatus(false);
			factureFormationDTO.setRow(null);
			factureFormationDTO.setRows(null);
			factureFormationDTO.setMessage(ex.getMessage());
			factureFormationDTO.setTotal(0);
			factureFormationDTO.setErrors(listErreur);
		}
		return factureFormationDTO;
	}

	@Override
	public FactureFormationDTO findFactureFormation(Long id) {
		// TODO Auto-generated method stub
		
		FactureFormationDTO factureFormationDTO = new FactureFormationDTO();
		FactureFormation factureFormation;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		
		try{
			factureFormation = factureFormationRepository.findById(id) .orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			if(factureFormation == null){
				factureFormationDTO.setResult(true);
				factureFormationDTO.setStatus(true);
				factureFormationDTO.setRow(factureFormation);
				factureFormationDTO.setRows(null);
				factureFormationDTO.setMessage("facture inexistante dans le systeme");
				factureFormationDTO.setTotal(1);
				factureFormationDTO.setErrors(listErreur);
			} else {
				factureFormationDTO.setResult(true);
				factureFormationDTO.setStatus(true);
				factureFormationDTO.setRow(factureFormation);
				factureFormationDTO.setRows(null);
				factureFormationDTO.setMessage("facture trouvee avec succes");
				factureFormationDTO.setTotal(1);
				factureFormationDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			factureFormationDTO.setResult(false);
			factureFormationDTO.setStatus(false);
			factureFormationDTO.setRow(null);
			factureFormationDTO.setRows(null);
			factureFormationDTO.setMessage(ex.getMessage());
			factureFormationDTO.setTotal(0);
			factureFormationDTO.setErrors(listErreur);
		}
		return factureFormationDTO;
	}

	@Override
	public FactureFormationDTO findFactureFormationByFormation(Long idFormation) {
		// TODO Auto-generated method stub
		FactureFormationDTO factureFormationDTO = new FactureFormationDTO();
		List<FactureFormation> listFactureFormation;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		try{
			listFactureFormation = factureFormationRepository.findByFormationId(idFormation);
			factureFormationDTO.setResult(true);
			factureFormationDTO.setStatus(true);
			factureFormationDTO.setRow(null);
			factureFormationDTO.setRows(listFactureFormation);
			//factureFormationDTO.setMessage(sb.toString());
			factureFormationDTO.setTotal(listFactureFormation.size());
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			factureFormationDTO.setResult(false);
			factureFormationDTO.setStatus(false);
			factureFormationDTO.setRow(null);
			factureFormationDTO.setRows(null);
			factureFormationDTO.setMessage(ex.getMessage());
			factureFormationDTO.setTotal(0);
			factureFormationDTO.setErrors(listErreur);
		}
		return factureFormationDTO;
	}

	@Override
	public FactureFormationDTO findFactureFormations() {
		// TODO Auto-generated method stub
		
		FactureFormationDTO factureFormationDTO = new FactureFormationDTO();
		List<FactureFormation> listFactureFormation;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		
		try{ 
			listFactureFormation = factureFormationRepository.findAll();
			if(listFactureFormation == null){
				factureFormationDTO.setResult(true);
				factureFormationDTO.setStatus(true);
				factureFormationDTO.setRow(null);
				factureFormationDTO.setRows(new ArrayList<FactureFormation>());
				factureFormationDTO.setMessage("aucune facture trouve");
				factureFormationDTO.setTotal(0);
				factureFormationDTO.setErrors(listErreur);
			} else {
				int i = listFactureFormation.size();
				sb = new StringBuilder();
				sb.append(i).append(" facture(s) trouve(s) avec succes");
				factureFormationDTO.setResult(true);
				factureFormationDTO.setStatus(true);
				factureFormationDTO.setRow(null);
				factureFormationDTO.setRows(listFactureFormation);
				factureFormationDTO.setMessage(sb.toString());
				factureFormationDTO.setTotal(i);
				factureFormationDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			factureFormationDTO.setResult(false);
			factureFormationDTO.setStatus(false);
			factureFormationDTO.setRow(null);
			factureFormationDTO.setRows(null);
			factureFormationDTO.setMessage(ex.getMessage());
			factureFormationDTO.setTotal(0);
			factureFormationDTO.setErrors(listErreur);
		}
		return factureFormationDTO;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) factureFormationRepository.count();
	}

	@Override
	public FactureFormationDTO loadFactureFormations(Pageable pageable) {
		// TODO Auto-generated method stub
		FactureFormationDTO factureFormationDTO = new FactureFormationDTO();
		Page<FactureFormation> page = factureFormationRepository.findAll(pageable);
		factureFormationDTO.setResult(true);
		factureFormationDTO.setStatus(true);
		factureFormationDTO.setRows(page.getContent());
		factureFormationDTO.setTotal(page.getTotalElements());
		return factureFormationDTO;
	}

	@Override
	public FactureFormationDTO loadFactureFormations(Pageable pageable, String reference, String nom, String intitule) {
		// TODO Auto-generated method stub
		FactureFormationDTO factureFormationDTO = new FactureFormationDTO();
		Page<FactureFormation> page = factureFormationRepository.findByReferenceContainingOrCabinetFormationNomContainingOrFormationIntituleContaining(pageable, reference, nom, intitule);
		factureFormationDTO.setResult(true);
		factureFormationDTO.setStatus(true);
		factureFormationDTO.setRows(page.getContent());
		factureFormationDTO.setTotal(page.getTotalElements());
		return factureFormationDTO;
	}

}
