package com.nectux.mizan.hyban.paie.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nectux.mizan.hyban.paie.dto.StockCongeDTO;
import com.nectux.mizan.hyban.paie.entity.Conge;
import com.nectux.mizan.hyban.paie.entity.StockConge;
import com.nectux.mizan.hyban.paie.repository.StockCongeRepository;
import com.nectux.mizan.hyban.paie.service.StockCongeService;
import com.nectux.mizan.hyban.utils.DateManager;
import com.nectux.mizan.hyban.utils.Erreur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nectux.mizan.hyban.paie.repository.CongeRepository;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service("stockCongeService")
public class StockCongeServiceImpl implements StockCongeService {
	
	@Autowired private CongeRepository congeRepository;
	@Autowired private StockCongeRepository stockCongeRepository;
	
	private StringBuilder sb;
	private Erreur erreur;
	private List<Erreur> listErreur;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public StockConge save(StockConge stockConge) {
		// TODO Auto-generated method stub
		return stockCongeRepository.save(stockConge);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public StockCongeDTO save(Long id, Long idConge, String dateDepart, String dateRetour, Double montantVerse) {
		// TODO Auto-generated method stub
		StockCongeDTO stockCongeDTO = new StockCongeDTO();
		StockConge stockConge;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		try{
			Conge conge = congeRepository.findById(idConge)  .orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + idConge));
			int nombreJourCongeRestant = conge.getNombreJourCongeDu() - conge.getNombreJourCongePris();
			Double allocationCongeRestant = conge.getAllocationCongeNet() - conge.getAllocationCongeNetPris();
			if(id != null){
				stockConge = stockCongeRepository.findById(id)  .orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
				stockConge.setDateModification(new Date());
			} else {
				stockConge = new StockConge();
				stockConge.setDateCreation(new Date());
			}
			stockConge.setDateDepart(DateManager.stringToDate(dateDepart, "dd/MM/yyyy"));
			stockConge.setDateRetour(DateManager.stringToDate(dateRetour, "dd/MM/yyyy"));
			stockConge.setNombreJourPris(DateManager.dateDifference(stockConge.getDateDepart(), stockConge.getDateRetour()));
			stockConge.setNombreJourRestant(nombreJourCongeRestant - stockConge.getNombreJourPris());
			stockConge.setMontantVerse(montantVerse);
			stockConge.setMontantRestant(allocationCongeRestant - stockConge.getMontantRestant());
			stockConge.setConge(conge);
			
			if(nombreJourCongeRestant < stockConge.getNombreJourPris()){
				sb = new StringBuilder();
				erreur = new Erreur();
				erreur.setCode(10);
				erreur.setDescription("contrainte d'integrite non null non respectee");
				sb.append("le nombre de jour pris est superieur au nombre de jour restant");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
			}
			
			if(allocationCongeRestant < stockConge.getMontantVerse()){
				sb = new StringBuilder();
				erreur = new Erreur();
				erreur.setCode(10);
				erreur.setDescription("contrainte d'integrite non null non respectee");
				sb.append("le montant verse est superieur au montant restant");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
			}
			
			if(stockConge.getDateDepart() == null || stockConge.getDateDepart().equals("")){
				sb = new StringBuilder();
				erreur = new Erreur();
				erreur.setCode(10);
				erreur.setDescription("contrainte d'integrite non null non respectee");
				sb.append("le champ date depart est obligatoire");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
			}
			
			if(stockConge.getDateRetour() == null || stockConge.getDateRetour().equals("")){
				sb = new StringBuilder();
				erreur = new Erreur();
				erreur.setCode(10);
				erreur.setDescription("contrainte d'integrite non null non respectee");
				sb.append("le champ date retour est obligatoire");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
			}
			
			if(stockConge.getMontantVerse() == null){
				sb = new StringBuilder();
				erreur = new Erreur();
				erreur.setCode(10);
				erreur.setDescription("contrainte d'integrite non null non respectee");
				sb.append("le champ montant verse est obligatoire");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
			}
			
			if(listErreur.isEmpty()){
				stockConge = stockCongeRepository.save(stockConge);
				
				sb = new StringBuilder();
				sb.append(" enregistre avec succes");
				stockCongeDTO.setResult(true);
				stockCongeDTO.setStatus(true);
				stockCongeDTO.setRow(stockConge);
				stockCongeDTO.setRows(null);
				stockCongeDTO.setMessage(sb.toString());
				stockCongeDTO.setTotal(0);
				stockCongeDTO.setErrors(listErreur);
				
				conge.setNombreJourCongePris(conge.getNombreJourCongePris() + stockConge.getNombreJourPris());
				conge.setAllocationCongeNetPris(conge.getAllocationCongeNetPris() + stockConge.getMontantVerse());
				congeRepository.save(conge);
			} else {
				stockCongeDTO.setResult(false);
				stockCongeDTO.setStatus(false);
				stockCongeDTO.setRow(null);
				stockCongeDTO.setRows(null);
				stockCongeDTO.setMessage("voir liste erreur");
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
	@Transactional(rollbackFor = Exception.class)
	public StockCongeDTO delete(Long id) {
		// TODO Auto-generated method stub
		StockCongeDTO stockCongeDTO = new StockCongeDTO();
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		try{
			StockConge stockConge = stockCongeRepository.findById(id)  .orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
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
				//conge.setAllocationCongeNetPris(conge.getAllocationCongeNetPris() + stockConge.getMontantVerse());
				//conge.setNombreJourCongePris(conge.getNombreJourCongePris() + stockConge.getNombreJourPris());
			}
			
			if(listErreur.isEmpty()) {
				stockConge.getConge().setAllocationCongeNetPris(stockConge.getConge().getAllocationCongeNetPris() + stockConge.getMontantVerse());
				stockConge.getConge().setNombreJourCongePris(stockConge.getConge().getNombreJourCongePris() + stockConge.getNombreJourPris());
				congeRepository.save(stockConge.getConge());
				stockCongeRepository.delete(stockConge);
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
	public StockCongeDTO findStockConge(Long id) {
		// TODO Auto-generated method stub
		StockCongeDTO stockCongeDTO = new StockCongeDTO();
		StockConge stockConge;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		try{
			stockConge = stockCongeRepository.findById(id)  .orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
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
	public StockCongeDTO findStockConges() {
		// TODO Auto-generated method stub
		StockCongeDTO stockCongeDTO = new StockCongeDTO();
		List<StockConge> listStockConge = new ArrayList<StockConge>();
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		try{
			listStockConge = stockCongeRepository.findAll();
			if(listStockConge == null){
				stockCongeDTO.setResult(true);
				stockCongeDTO.setStatus(true);
				stockCongeDTO.setRow(null);
				stockCongeDTO.setRows(new ArrayList<StockConge>());
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
	public StockCongeDTO findStockCongesByConge(Long idConge) {
		// TODO Auto-generated method stub
		StockCongeDTO stockCongeDTO = new StockCongeDTO();
		List<StockConge> listStockConge = new ArrayList<StockConge>();
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		try{
			listStockConge = stockCongeRepository.findByCongeId(idConge);
			if(listStockConge == null){
				stockCongeDTO.setResult(true);
				stockCongeDTO.setStatus(true);
				stockCongeDTO.setRow(null);
				stockCongeDTO.setRows(new ArrayList<StockConge>());
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
	public StockCongeDTO findStockCongesByPersonnel(Long idPersonnel) {
		// TODO Auto-generated method stub
		StockCongeDTO stockCongeDTO = new StockCongeDTO();
		List<StockConge> listStockConge = new ArrayList<StockConge>();
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		try{
			listStockConge = stockCongeRepository.findByCongeContratPersonnelPersonnelId(idPersonnel);
			if(listStockConge == null){
				stockCongeDTO.setResult(true);
				stockCongeDTO.setStatus(true);
				stockCongeDTO.setRow(null);
				stockCongeDTO.setRows(new ArrayList<StockConge>());
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
		return (int) stockCongeRepository.count();
	}

	@Override
	public StockCongeDTO loadStockConges(Pageable pageable) {
		// TODO Auto-generated method stub
		StockCongeDTO stockCongeDTO = new StockCongeDTO();
		Page<StockConge> page = stockCongeRepository.findAll(pageable);
		stockCongeDTO.setResult(true);
		stockCongeDTO.setStatus(true);
		stockCongeDTO.setRows(page.getContent());
		stockCongeDTO.setTotal(page.getTotalElements());
		return stockCongeDTO;
	}

}
