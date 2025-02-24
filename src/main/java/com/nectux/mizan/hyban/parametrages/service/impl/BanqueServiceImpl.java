package com.nectux.mizan.hyban.parametrages.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nectux.mizan.hyban.parametrages.dto.BanqueDTO;
import com.nectux.mizan.hyban.parametrages.entity.Banque;
import com.nectux.mizan.hyban.parametrages.repository.BanqueRepository;
import com.nectux.mizan.hyban.parametrages.service.BanqueService;
import com.nectux.mizan.hyban.utils.Erreur;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service("banqueService")
public class BanqueServiceImpl implements BanqueService {
	
	@Autowired private BanqueRepository banqueRepository;
	
	private StringBuilder sb;
	private Erreur erreur;
	private List<Erreur> listErreur;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public BanqueDTO save(Long id, String sigle, String libelle, String codbanq,Boolean statut) {
		// TODO Auto-generated method stub
		BanqueDTO banqueDTO = new BanqueDTO();
		Banque banque;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		try{
			if(id != null){
				banque = banqueRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
				
			} else {
				banque = new Banque();
			}
			banque.setLibelle(libelle);
			banque.setSigle(sigle);
			banque.setCodbanq(codbanq);
			banque.setStatut(statut);
			
			if(banque.getLibelle() == null || banque.getLibelle().isEmpty()){
				sb = new StringBuilder();
				erreur.setCode(10);
				erreur.setDescription("contrainte d'integrite non null non respectee");
				sb.append("le champ libelle est obligatoire");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
			}
			
			if(banque.getSigle() == null || banque.getSigle().isEmpty()){
				sb = new StringBuilder();
				erreur.setCode(10);
				erreur.setDescription("contrainte d'integrite non null non respectee");
				sb.append("le champ libelle est obligatoire");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
			}
			
			if(banque.getStatut() == null ){
				sb = new StringBuilder();
				erreur.setCode(10);
				erreur.setDescription("contrainte d'integrite non null non respectee");
				sb.append("le champ libelle est obligatoire");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
			}
			
			if(banque.getCodbanq() == null ){
				sb = new StringBuilder();
				erreur.setCode(10);
				erreur.setDescription("contrainte d'integrite non null non respectee");
				sb.append("le champ libelle est obligatoire");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
			}
			
			if(banque.getId() == null){
				if(banqueRepository.findByLibelle(banque.getLibelle()) != null){
					sb = new StringBuilder();
					erreur.setCode(11);
					erreur.setDescription("contrainte de doublon non respectee");
					sb.append(libelle).append(" existe deja");
					erreur.setMessage(sb.toString());
					listErreur.add(erreur);
				}
			} else{
				if(banqueRepository.findByIdNotAndLibelle(banque.getId(), banque.getLibelle()) != null){
					sb = new StringBuilder();
					erreur.setCode(11);
					erreur.setDescription("contrainte de doublon non respectee");
					sb.append(libelle).append(" existe deja");
					erreur.setMessage(sb.toString());
					listErreur.add(erreur);
				}
			}
			
			if(banque.getId() == null){
				if(banqueRepository.findBySigle(banque.getSigle()) != null){
					sb = new StringBuilder();
					erreur.setCode(11);
					erreur.setDescription("contrainte de doublon non respectee");
					sb.append(sigle).append(" existe deja");
					erreur.setMessage(sb.toString());
					listErreur.add(erreur);
				}
			} else{
				if(banqueRepository.findByIdNotAndSigle(banque.getId(), banque.getSigle()) != null){
					sb = new StringBuilder();
					erreur.setCode(11);
					erreur.setDescription("contrainte de doublon non respectee");
					sb.append(sigle).append(" existe deja");
					erreur.setMessage(sb.toString());
					listErreur.add(erreur);
				}
			}
			
			if(banque.getId() == null){
				if(banqueRepository.findByCodbanq(banque.getCodbanq()) != null){
					sb = new StringBuilder();
					erreur.setCode(11);
					erreur.setDescription("contrainte de doublon non respectee");
					sb.append(sigle).append(" existe deja");
					erreur.setMessage(sb.toString());
					listErreur.add(erreur);
				}
			} else{
				if(banqueRepository.findByIdNotAndCodbanq(banque.getId(), banque.getCodbanq()) != null){
					sb = new StringBuilder();
					erreur.setCode(11);
					erreur.setDescription("contrainte de doublon non respectee");
					sb.append(sigle).append(" existe deja");
					erreur.setMessage(sb.toString());
					listErreur.add(erreur);
				}
			}
			
			
			if(listErreur.isEmpty()){
				banque = banqueRepository.save(banque);
				sb = new StringBuilder();
				sb.append(banque.getLibelle()).append(" enregistre avec succes");
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
	public BanqueDTO delete(Long id) {
		// TODO Auto-generated method stub
		BanqueDTO banqueDTO = new BanqueDTO();
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		try{
			Banque banque = banqueRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
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
				banqueRepository.delete(banque);
				sb = new StringBuilder();
				sb.append(banque.getLibelle()).append(" supprime avec succes");
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
	public BanqueDTO findBanque(Long id) {
		// TODO Auto-generated method stub
		BanqueDTO banqueDTO = new BanqueDTO();
		Banque banque;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		try{
			banque = banqueRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
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
	public BanqueDTO findBanques() {
		// TODO Auto-generated method stub
		BanqueDTO banqueDTO = new BanqueDTO();
		List<Banque> listBanque = new ArrayList<Banque>();
		try{
			listBanque = banqueRepository.findAll();
			if(listBanque == null){
				banqueDTO.setResult(true);
				banqueDTO.setStatus(true);
				banqueDTO.setRow(null);
				banqueDTO.setRows(new ArrayList<Banque>());
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
		return (int) banqueRepository.count();
	}

	@Override
	public BanqueDTO loadBanques(Pageable pageable) {
		// TODO Auto-generated method stub
		BanqueDTO banqueDTO = new BanqueDTO();
		Page<Banque> page = banqueRepository.findAll(pageable);
		banqueDTO.setResult(true);
		banqueDTO.setStatus(true);
		banqueDTO.setRows(page.getContent());
		banqueDTO.setTotal(page.getTotalElements());
		return banqueDTO;
	}

	@Override
	public BanqueDTO loadBanques(Pageable pageable, String search) {
		// TODO Auto-generated method stub
		BanqueDTO banqueDTO = new BanqueDTO();
		Page<Banque> page = banqueRepository.findByLibelleLike(pageable, search);
		banqueDTO.setResult(true);
		banqueDTO.setStatus(true);
		banqueDTO.setRows(page.getContent());
		banqueDTO.setTotal(page.getTotalElements());
		return banqueDTO;
	}

	@Override
	public List<Banque> getBanques() {
		// TODO Auto-generated method stub
		return banqueRepository.findAll();
	}

	@Override
	public Banque findBanquesID(Long id) {
		// TODO Auto-generated method stub
		return banqueRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
	}

	@Override
	public List<Banque> getBanquesEntprise() {
		// TODO Auto-generated method stub
		return banqueRepository.findByStatutTrue();
	}
	
}
