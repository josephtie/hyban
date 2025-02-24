package com.nectux.mizan.hyban.paie.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nectux.mizan.hyban.paie.dto.MvtCongeDTO;
import com.nectux.mizan.hyban.paie.entity.MvtConge;
import com.nectux.mizan.hyban.paie.repository.MvtCongeRepository;
import com.nectux.mizan.hyban.paie.service.MvtCongeService;
import com.nectux.mizan.hyban.personnel.entity.Personnel;
import com.nectux.mizan.hyban.personnel.repository.PersonnelRepository;
import com.nectux.mizan.hyban.rh.absences.entity.Absences;
import com.nectux.mizan.hyban.rh.absences.repository.AbsencesPersonnelRepository;
import com.nectux.mizan.hyban.rh.absences.repository.AbsencesRepository;
import com.nectux.mizan.hyban.utils.DateManager;
import com.nectux.mizan.hyban.utils.Erreur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nectux.mizan.hyban.paie.repository.CongeRepository;
import com.nectux.mizan.hyban.rh.absences.entity.AbsencesPersonnel;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service("mvtCongeService")
public class MvtCongeServiceImpl implements MvtCongeService {
	
	@Autowired private CongeRepository congeRepository;
	@Autowired private PersonnelRepository personnelRepository;
	@Autowired private MvtCongeRepository mvtCongeRepository;
	@Autowired private AbsencesRepository absencesRepository;
	@Autowired private AbsencesPersonnelRepository absencesPersonnelRepository;
	
	private StringBuilder sb;
	private Erreur erreur;
	private List<Erreur> listErreur;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public MvtConge save(MvtConge mvtconge) {
		// TODO Auto-generated method stub
		return mvtCongeRepository.save(mvtconge);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public MvtCongeDTO save(Long id, Long idPerso, String dateDepart, String dateRetour, Double montantVerse) {
		// TODO Auto-generated method stub
		MvtCongeDTO mvtCongeDTO = new MvtCongeDTO();
		MvtConge mvtConge;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		try{
			Personnel personnel = personnelRepository.findById(idPerso).orElseThrow(() -> new EntityNotFoundException("Personnel not found for id " + id));
		
			if(id != null){
				mvtConge = mvtCongeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Personnel not found for id " + id));
				mvtConge.setDateModification(new Date());
			} else {
				mvtConge = new MvtConge();
				mvtConge.setDateCreation(new Date());
			}
			mvtConge.setDateDepart(DateManager.stringToDate(dateDepart, "dd/MM/yyyy"));
			mvtConge.setDateRetour(DateManager.stringToDate(dateRetour, "dd/MM/yyyy"));
			mvtConge.setNombreJourPris(DateManager.dateDifference(mvtConge.getDateDepart(), mvtConge.getDateRetour()));			
			mvtConge.setMontantVerse(montantVerse);		
			mvtConge.setPersonnel(personnel);
			
			if(mvtConge.getDateDepart() == null || mvtConge.getDateDepart().equals("")){
				sb = new StringBuilder();
				erreur = new Erreur();
				erreur.setCode(10);
				erreur.setDescription("contrainte d'integrite non null non respectee");
				sb.append("le champ date depart est obligatoire");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
			}
			
			if(mvtConge.getDateRetour() == null || mvtConge.getDateRetour().equals("")){
				sb = new StringBuilder();
				erreur = new Erreur();
				erreur.setCode(10);
				erreur.setDescription("contrainte d'integrite non null non respectee");
				sb.append("le champ date retour est obligatoire");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
			}
			
			if(mvtConge.getMontantVerse() == null){
				sb = new StringBuilder();
				erreur = new Erreur();
				erreur.setCode(10);
				erreur.setDescription("contrainte d'integrite non null non respectee");
				sb.append("le champ montant verse est obligatoire");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
			}
			
			/*if(mvtConge.getPersonnel().getNombreJourdu() <= 0 ){
				sb = new StringBuilder();
				erreur = new Erreur();
				erreur.setCode(10);
				erreur.setDescription("contrainte d'integrite non null non respectee");
				sb.append("le champ montant verse est obligatoire");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
			}*/
			
			if(listErreur.isEmpty()){
				mvtConge = mvtCongeRepository.save(mvtConge);
				
				sb = new StringBuilder();
				sb.append(" enregistre avec succes");
				mvtCongeDTO.setResult(true);
				mvtCongeDTO.setStatus(true);
				mvtCongeDTO.setRow(mvtConge);
				mvtCongeDTO.setRows(null);
				mvtCongeDTO.setMessage(sb.toString());
				mvtCongeDTO.setTotal(0);
				mvtCongeDTO.setErrors(listErreur);
				
				personnel.setNombreJourdu(personnel.getNombreJourdu() - mvtConge.getNombreJourPris());
				personnel.setMtcongedu(personnel.getMtcongedu() - mvtConge.getMontantVerse());
				personnelRepository.save(personnel);
				
				AbsencesPersonnel absPerson = new AbsencesPersonnel();
				absPerson.setDateCreation(new Date());
				absPerson.setStatut(true);
				absPerson.setDateDebut(mvtConge.getDateDepart());
				absPerson.setDateRet(mvtConge.getDateRetour());
				absPerson.setJoursabsence((double)mvtConge.getNombreJourPris());
				Absences absences=absencesRepository.findById(1L).orElseThrow(() -> new EntityNotFoundException("ContratPersonnel not found for id " + id));
				absPerson.setAbsences(absences);
				absPerson.setPersonnel(personnel);
			
				absencesPersonnelRepository.save(absPerson);
				
				
			} else {
				mvtCongeDTO.setResult(false);
				mvtCongeDTO.setStatus(false);
				mvtCongeDTO.setRow(null);
				mvtCongeDTO.setRows(null);
				mvtCongeDTO.setMessage("voir liste erreur");
				mvtCongeDTO.setTotal(0);
				mvtCongeDTO.setErrors(listErreur);
			}
			
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			mvtCongeDTO.setResult(false);
			mvtCongeDTO.setStatus(false);
			mvtCongeDTO.setRow(null);
			mvtCongeDTO.setRows(null);
			mvtCongeDTO.setMessage(ex.getMessage());
			mvtCongeDTO.setTotal(0);
			mvtCongeDTO.setErrors(listErreur);
		}
		return mvtCongeDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public MvtCongeDTO delete(Long id) {
		// TODO Auto-generated method stub
		MvtCongeDTO stockCongeDTO = new MvtCongeDTO();
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		try{
			MvtConge stockConge = mvtCongeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("MvtConge not found for id " + id));
			if(stockConge == null){
				sb = new StringBuilder();
				erreur.setCode(12);
				erreur.setDescription("stock conge inexistant");
				sb.append("ce stock conge est inexistant dans le systeme");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
				
				stockCongeDTO.setResult(false);
				stockCongeDTO.setStatus(false);
				stockCongeDTO.setRow(null);
				stockCongeDTO.setRows(null);
				stockCongeDTO.setMessage("voir liste erreur");
				stockCongeDTO.setTotal(0);
				stockCongeDTO.setErrors(listErreur);
			} else {
				
			}
			
			if(listErreur.isEmpty()) {
				stockConge.getPersonnel().setNombreJourdu(stockConge.getPersonnel().getNombreJourdu() + stockConge.getNombreJourPris());
				stockConge.getPersonnel().setMtcongedu(stockConge.getPersonnel().getMtcongedu() + stockConge.getMontantVerse());
				personnelRepository.save(stockConge.getPersonnel());
				mvtCongeRepository.delete(stockConge);
				sb = new StringBuilder();
				sb.append(" supprime avec succes");
				stockCongeDTO.setResult(true);
				stockCongeDTO.setStatus(true);
				stockCongeDTO.setRow(stockConge);
				stockCongeDTO.setRows(null);
				stockCongeDTO.setMessage(sb.toString());
				stockCongeDTO.setTotal(0);
				stockCongeDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			stockCongeDTO.setResult(false);
			stockCongeDTO.setStatus(false);
			stockCongeDTO.setRow(null);
			stockCongeDTO.setRows(null);
			stockCongeDTO.setMessage(ex.getMessage());
			stockCongeDTO.setTotal(0);
			stockCongeDTO.setErrors(listErreur);
		}
		return stockCongeDTO;
	}

	@Override
	public MvtCongeDTO findMvtConge(Long id) {
		// TODO Auto-generated method stub
		MvtCongeDTO stockCongeDTO = new MvtCongeDTO();
		MvtConge stockConge;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		try{
			stockConge = mvtCongeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("MvtConge not found for id " + id));

			if(stockConge == null){
				stockCongeDTO.setResult(true);
				stockCongeDTO.setStatus(true);
				stockCongeDTO.setRow(stockConge);
				stockCongeDTO.setRows(null);
				stockCongeDTO.setMessage("stock conge inexistant dans le systeme");
				stockCongeDTO.setTotal(1);
				stockCongeDTO.setErrors(listErreur);
			} else {
				stockCongeDTO.setResult(true);
				stockCongeDTO.setStatus(true);
				stockCongeDTO.setRow(stockConge);
				stockCongeDTO.setRows(null);
				stockCongeDTO.setMessage("stock conge trouve avec succes");
				stockCongeDTO.setTotal(1);
				stockCongeDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			stockCongeDTO.setResult(false);
			stockCongeDTO.setStatus(false);
			stockCongeDTO.setRow(null);
			stockCongeDTO.setRows(null);
			stockCongeDTO.setMessage(ex.getMessage());
			stockCongeDTO.setTotal(0);
			stockCongeDTO.setErrors(listErreur);
		}
		return stockCongeDTO;
	}

	@Override
	public MvtCongeDTO findMvtConges() {
		// TODO Auto-generated method stub
		MvtCongeDTO stockCongeDTO = new MvtCongeDTO();
		List<MvtConge> listStockConge = new ArrayList<MvtConge>();
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		try{
			listStockConge = mvtCongeRepository.findAll();
			if(listStockConge == null){
				stockCongeDTO.setResult(true);
				stockCongeDTO.setStatus(true);
				stockCongeDTO.setRow(null);
				stockCongeDTO.setRows(new ArrayList<MvtConge>());
				stockCongeDTO.setMessage("aucun stock conge trouve");
				stockCongeDTO.setTotal(0);
				stockCongeDTO.setErrors(listErreur);
			} else {
				int i = listStockConge.size();
				sb = new StringBuilder();
				sb.append(i).append(" stock(s) conge(s) trouve(s) avec succes");
				stockCongeDTO.setResult(true);
				stockCongeDTO.setStatus(true);
				stockCongeDTO.setRow(null);
				stockCongeDTO.setRows(listStockConge);
				stockCongeDTO.setMessage(sb.toString());
				stockCongeDTO.setTotal(i);
				stockCongeDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			stockCongeDTO.setResult(false);
			stockCongeDTO.setStatus(false);
			stockCongeDTO.setRow(null);
			stockCongeDTO.setRows(null);
			stockCongeDTO.setMessage(ex.getMessage());
			stockCongeDTO.setTotal(0);
			stockCongeDTO.setErrors(listErreur);
		}
		return stockCongeDTO;
	}

	@Override
	public MvtCongeDTO findMvtCongesByConge(Long idConge) {
		// TODO Auto-generated method stub
		MvtCongeDTO stockCongeDTO = new MvtCongeDTO();
		List<MvtConge> listStockConge = new ArrayList<MvtConge>();
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		try{
			listStockConge = mvtCongeRepository.findByPersonnelId(idConge);
			if(listStockConge == null){
				stockCongeDTO.setResult(true);
				stockCongeDTO.setStatus(true);
				stockCongeDTO.setRow(null);
				stockCongeDTO.setRows(new ArrayList<MvtConge>());
				stockCongeDTO.setMessage("aucun stock conge trouve");
				stockCongeDTO.setTotal(0);
				stockCongeDTO.setErrors(listErreur);
			} else {
				int i = listStockConge.size();
				sb = new StringBuilder();
				sb.append(i).append(" stock(s) conge(s) trouve(s) avec succes");
				stockCongeDTO.setResult(true);
				stockCongeDTO.setStatus(true);
				stockCongeDTO.setRow(null);
				stockCongeDTO.setRows(listStockConge);
				stockCongeDTO.setMessage(sb.toString());
				stockCongeDTO.setTotal(i);
				stockCongeDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			stockCongeDTO.setResult(false);
			stockCongeDTO.setStatus(false);
			stockCongeDTO.setRow(null);
			stockCongeDTO.setRows(null);
			stockCongeDTO.setMessage(ex.getMessage());
			stockCongeDTO.setTotal(0);
			stockCongeDTO.setErrors(listErreur);
		}
		return stockCongeDTO;
	}

	@Override
	public MvtCongeDTO findMvtCongesByPersonnel(Long idPersonnel) {
		// TODO Auto-generated method stub
		MvtCongeDTO stockCongeDTO = new MvtCongeDTO();
		List<MvtConge> listStockConge = new ArrayList<MvtConge>();
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		try{
			listStockConge = mvtCongeRepository.findByPersonnelId(idPersonnel);
			if(listStockConge == null){
				stockCongeDTO.setResult(true);
				stockCongeDTO.setStatus(true);
				stockCongeDTO.setRow(null);
				stockCongeDTO.setRows(new ArrayList<MvtConge>());
				stockCongeDTO.setMessage("aucun stock conge trouve");
				stockCongeDTO.setTotal(0);
				stockCongeDTO.setErrors(listErreur);
			} else {
				int i = listStockConge.size();
				sb = new StringBuilder();
				sb.append(i).append(" stock (s) conge(s) trouve(s) avec succes");
				stockCongeDTO.setResult(true);
				stockCongeDTO.setStatus(true);
				stockCongeDTO.setRow(null);
				stockCongeDTO.setRows(listStockConge);
				stockCongeDTO.setMessage(sb.toString());
				stockCongeDTO.setTotal(i);
				stockCongeDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			stockCongeDTO.setResult(false);
			stockCongeDTO.setStatus(false);
			stockCongeDTO.setRow(null);
			stockCongeDTO.setRows(null);
			stockCongeDTO.setMessage(ex.getMessage());
			stockCongeDTO.setTotal(0);
			stockCongeDTO.setErrors(listErreur);
		}
		return stockCongeDTO;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) mvtCongeRepository.count();
	}

	@Override
	public MvtCongeDTO loadMvtConges(Pageable pageable) {
		// TODO Auto-generated method stub
		MvtCongeDTO stockCongeDTO = new MvtCongeDTO();
		Page<MvtConge> page = mvtCongeRepository.findAll(pageable);
		stockCongeDTO.setResult(true);
		stockCongeDTO.setStatus(true);
		stockCongeDTO.setRows(page.getContent());
		stockCongeDTO.setTotal(page.getTotalElements());
		return stockCongeDTO;
	}

	@Override
	public MvtCongeDTO findMvtCongePrisMemDate(String dateDepart,
			String dateRetour) {
		// TODO Auto-generated method stub
		return null;
	}

}
