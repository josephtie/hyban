package com.nectux.mizan.hyban.parametrages.service.impl;
import java.util.ArrayList;
import java.util.List;

import com.nectux.mizan.hyban.parametrages.dto.CpteVirmtBanqueDTO;
import com.nectux.mizan.hyban.parametrages.entity.Banque;
import com.nectux.mizan.hyban.parametrages.entity.CpteVirmtBanque;
import com.nectux.mizan.hyban.parametrages.repository.BanqueRepository;
import com.nectux.mizan.hyban.parametrages.repository.CpteVirmtBanqueRepository;
import com.nectux.mizan.hyban.parametrages.service.CpteVirmtBanqueService;
import com.nectux.mizan.hyban.utils.Erreur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service("cptevirmtService")
public class CpteVirmtBanqueServiceImpl implements CpteVirmtBanqueService {
	

@Autowired private CpteVirmtBanqueRepository cpteVirmtBanqueRepository;
@Autowired private BanqueRepository banqueRepository;
	
	private StringBuilder sb;
	private Erreur erreur;
	private List<Erreur> listErreur;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public CpteVirmtBanqueDTO saver(Long id, String sigle, String libelle, Long codbanq, String numcptevirmt, String numguich, int ribcptevirmt) {
		// TODO Auto-generated method stub
		CpteVirmtBanqueDTO banqueDTO = new CpteVirmtBanqueDTO();
		CpteVirmtBanque banque;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		try{
			if(id != null){
				banque = cpteVirmtBanqueRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
				
			} else {
				banque = new CpteVirmtBanque();
			}
			banque.setBank(banqueRepository.findById(codbanq).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + codbanq)));
			banque.setCodEtablVirmt(sigle);
			banque.setDonneurOrdCpteVirmt(libelle);
			banque.setNumcpteCpteVirmt(numcptevirmt);;
			banque.setNumguichCpteVirmt(numguich);
			banque.setRibCpteVirmt(ribcptevirmt);
			//banque.setLibelle(libelle);
			
			if(banque.getCodEtablVirmt() == null || banque.getCodEtablVirmt().isEmpty()){
				sb = new StringBuilder();
				erreur.setCode(10);
				erreur.setDescription("contrainte d'integrite non null non respectee");
				sb.append("le champ libelle est obligatoire");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
			}
			
			if(banque.getNumcpteCpteVirmt() == null || banque.getNumcpteCpteVirmt().isEmpty()){
				sb = new StringBuilder();
				erreur.setCode(10);
				erreur.setDescription("contrainte d'integrite non null non respectee");
				sb.append("le champ libelle est obligatoire");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
			}
			
			if(banque.getNumguichCpteVirmt() == null || banque.getNumguichCpteVirmt().isEmpty()){
				sb = new StringBuilder();
				erreur.setCode(10);
				erreur.setDescription("contrainte d'integrite non null non respectee");
				sb.append("le champ libelle est obligatoire");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
			}
			
			if(banque.getRibCpteVirmt() == null ){
				sb = new StringBuilder();
				erreur.setCode(10);
				erreur.setDescription("contrainte d'integrite non null non respectee");
				sb.append("le champ libelle est obligatoire");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
			}
			
		
			
			
			if(listErreur.isEmpty()){
				banque = cpteVirmtBanqueRepository.save(banque);
				sb = new StringBuilder();
				sb.append(banque.getNumcpteCpteVirmt()).append(" enregistre avec succes");
				banqueDTO.setResult(true);
				banqueDTO.setStatus(true);
				banqueDTO.setRow(banque);
				banqueDTO.setRows(null);
				banqueDTO.setMessage(sb.toString());
				banqueDTO.setTotal(0);
				banqueDTO.setErrors(listErreur);
			} else {
				banqueDTO.setResult(false);
				banqueDTO.setStatus(false);
				banqueDTO.setRow(null);
				banqueDTO.setRows(null);
				banqueDTO.setMessage("voir liste erreur");
				banqueDTO.setTotal(0);
				banqueDTO.setErrors(listErreur);
			}
			
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			banqueDTO.setResult(false);
			banqueDTO.setStatus(false);
			banqueDTO.setRow(null);
			banqueDTO.setRows(null);
			banqueDTO.setMessage(ex.getMessage());
			banqueDTO.setTotal(0);
			banqueDTO.setErrors(listErreur);
		}
		return banqueDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public CpteVirmtBanqueDTO delete(Long id) {
		// TODO Auto-generated method stub
		CpteVirmtBanqueDTO banqueDTO = new CpteVirmtBanqueDTO();
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		try{
			CpteVirmtBanque banque = cpteVirmtBanqueRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			if(banque == null){
				sb = new StringBuilder();
				erreur.setCode(12);
				erreur.setDescription("type de document inexistant");
				sb.append("ce type de document est inexistant dans le systeme");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
				
				banqueDTO.setResult(false);
				banqueDTO.setStatus(false);
				banqueDTO.setRow(null);
				banqueDTO.setRows(null);
				banqueDTO.setMessage("voir liste erreur");
				banqueDTO.setTotal(0);
				banqueDTO.setErrors(listErreur);
			} 
			
			if(listErreur.isEmpty()) {
				cpteVirmtBanqueRepository.delete(banque);
				sb = new StringBuilder();
				sb.append(banque.getNumcpteCpteVirmt()).append(" supprime avec succes");
				banqueDTO.setResult(true);
				banqueDTO.setStatus(true);
				banqueDTO.setRow(banque);
				banqueDTO.setRows(null);
				banqueDTO.setMessage(sb.toString());
				banqueDTO.setTotal(0);
				banqueDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			banqueDTO.setResult(false);
			banqueDTO.setStatus(false);
			banqueDTO.setRow(null);
			banqueDTO.setRows(null);
			banqueDTO.setMessage(ex.getMessage());
			banqueDTO.setTotal(0);
			banqueDTO.setErrors(listErreur);
		}
		return banqueDTO;
	}

	@Override
	public CpteVirmtBanqueDTO findCpteVirmtBanque(Long id) {
		// TODO Auto-generated method stub
		CpteVirmtBanqueDTO banqueDTO = new CpteVirmtBanqueDTO();
		CpteVirmtBanque banque;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		try{
			banque = cpteVirmtBanqueRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			if(banque == null){
				banqueDTO.setResult(true);
				banqueDTO.setStatus(true);
				banqueDTO.setRow(banque);
				banqueDTO.setRows(null);
				banqueDTO.setMessage("objet inexistant dans le systeme");
				banqueDTO.setTotal(1);
				banqueDTO.setErrors(listErreur);
			} else {
				banqueDTO.setResult(true);
				banqueDTO.setStatus(true);
				banqueDTO.setRow(banque);
				banqueDTO.setRows(null);
				banqueDTO.setMessage("objet trouve avec succes");
				banqueDTO.setTotal(1);
				banqueDTO.setErrors(listErreur);
			}
		} catch (Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			banqueDTO.setResult(true);
			banqueDTO.setStatus(true);
			banqueDTO.setRow(null);
			banqueDTO.setRows(null);
			banqueDTO.setMessage(ex.getMessage());
			banqueDTO.setTotal(0);
			banqueDTO.setErrors(listErreur);
		}
		return banqueDTO;
	}

	@Override
	public CpteVirmtBanqueDTO findCpteVirmtBanques() {
		// TODO Auto-generated method stub
		CpteVirmtBanqueDTO banqueDTO = new CpteVirmtBanqueDTO();
		List<CpteVirmtBanque> listBanque = new ArrayList<CpteVirmtBanque>();
		try{
			listBanque = cpteVirmtBanqueRepository.findAll();
			if(listBanque == null){
				banqueDTO.setResult(true);
				banqueDTO.setStatus(true);
				banqueDTO.setRow(null);
				banqueDTO.setRows(new ArrayList<CpteVirmtBanque>());
				banqueDTO.setMessage("aucun objet trouve");
				banqueDTO.setTotal(0);
				banqueDTO.setErrors(listErreur);
			} else {
				int i = listBanque.size();
				sb = new StringBuilder();
				sb.append(i).append(" objet(s) trouve(s) avec succes");
				banqueDTO.setResult(true);
				banqueDTO.setStatus(true);
				banqueDTO.setRow(null);
				banqueDTO.setRows(listBanque);
				banqueDTO.setMessage(sb.toString());
				banqueDTO.setTotal(i);
				banqueDTO.setErrors(listErreur);
			}
		} catch (Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			
			banqueDTO.setResult(false);
			banqueDTO.setStatus(false);
			banqueDTO.setRow(null);
			banqueDTO.setRows(listBanque);
			banqueDTO.setMessage(ex.getMessage());
			banqueDTO.setTotal(0);
			banqueDTO.setErrors(listErreur);
		}
		return banqueDTO;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) cpteVirmtBanqueRepository.count();
	}

	@Override
	public CpteVirmtBanqueDTO loadCpteVirmtBanques(Pageable pageable) {
		// TODO Auto-generated method stub
		CpteVirmtBanqueDTO banqueDTO = new CpteVirmtBanqueDTO();
		Page<CpteVirmtBanque> page = cpteVirmtBanqueRepository.findAll(pageable);
		banqueDTO.setResult(true);
		banqueDTO.setStatus(true);
		banqueDTO.setRows(page.getContent());
		banqueDTO.setTotal(page.getTotalElements());
		return banqueDTO;
	}

	@Override
	public CpteVirmtBanqueDTO loadCpteVirmtBanques(Pageable pageable, String search) {
		// TODO Auto-generated method stub
		CpteVirmtBanqueDTO banqueDTO = new CpteVirmtBanqueDTO();
		Page<CpteVirmtBanque> page = cpteVirmtBanqueRepository.findByNumguichCpteVirmtLike(pageable, search);
		banqueDTO.setResult(true);
		banqueDTO.setStatus(true);
		banqueDTO.setRows(page.getContent());
		banqueDTO.setTotal(page.getTotalElements());
		return banqueDTO;
	}

	@Override
	public List<CpteVirmtBanque> getCpteVirmtBanques() {
		// TODO Auto-generated method stub
		return cpteVirmtBanqueRepository.findAll();
	}

	@Override
	public CpteVirmtBanqueDTO save(Long id, String sigle, String libelle,Long codbanq, String numcptevirmt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CpteVirmtBanque findCpteVirmtBanquesId(Long id) {
		// TODO Auto-generated method stub
		return cpteVirmtBanqueRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
	}

	@Override
	public CpteVirmtBanque findCpteVirmtoFBanques(Banque banque) {
		// TODO Auto-generated method stub
		return cpteVirmtBanqueRepository.findByBank(banque);
	}
}