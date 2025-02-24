package com.nectux.mizan.hyban.rh.carriere.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nectux.mizan.hyban.rh.carriere.entity.Promotion;
import com.nectux.mizan.hyban.rh.carriere.repository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nectux.mizan.hyban.rh.carriere.dto.PromotionDTO;
import com.nectux.mizan.hyban.rh.carriere.service.PromotionService;
import com.nectux.mizan.hyban.utils.Erreur;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service("promotionService")
public class PromotionServiceImpl implements PromotionService {
	
	@Autowired private PromotionRepository promotionRepository;
	
	private StringBuilder sb;
	private Erreur erreur;
	private List<Erreur> listErreur;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public PromotionDTO save(Long id, String libelle, String description) {
		// TODO Auto-generated method stub
		
		PromotionDTO promotionDTO = new PromotionDTO();
		Promotion promotion;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		
		try{
			if(id == null){
				promotion = new Promotion();
				promotion.setDateCreation(new Date());
			} else {
				promotion = promotionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
				promotion.setDateModification(new Date());
			}
			promotion.setLibelle(libelle);
			promotion.setDescription(description);
			
			if(id == null){
				if(promotionRepository.findByLibelle(promotion.getLibelle()) != null){
					sb = new StringBuilder();
					erreur.setCode(10);
					erreur.setDescription("contrainte de doublon non respecte");
					sb.append("cette promotion existe deja");
					erreur.setMessage(sb.toString());
					listErreur.add(erreur);
				}
			} else {
				if(promotionRepository.findByIdNotAndLibelle(id, promotion.getLibelle()) != null){
					sb = new StringBuilder();
					erreur.setCode(10);
					erreur.setDescription("contrainte de doublon non respecte");
					sb.append("cette promotion existe deja");
					erreur.setMessage(sb.toString());
					listErreur.add(erreur);
				}
			}
			
			if(promotion.getLibelle() == null || promotion.getLibelle().trim().equals("")){
				sb = new StringBuilder();
				erreur.setCode(10);
				erreur.setDescription("contrainte d'integrite non null non respectee");
				sb.append("le champ libelle est obligatoire");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
			} 
			
			if(listErreur.isEmpty()){
				promotion = promotionRepository.save(promotion);
				sb = new StringBuilder();
				sb.append(promotion.getLibelle()).append(" enregistre avec succes");
				promotionDTO.setResult(true);
				promotionDTO.setStatus(true);
				promotionDTO.setRow(promotion);
				promotionDTO.setRows(null);
				promotionDTO.setMessage(sb.toString());
				promotionDTO.setTotal(0);
				promotionDTO.setErrors(listErreur);
			} else {
				promotionDTO.setResult(false);
				promotionDTO.setStatus(false);
				promotionDTO.setRow(null);
				promotionDTO.setRows(null);
				promotionDTO.setMessage("voir liste erreur");
				promotionDTO.setTotal(0);
				promotionDTO.setErrors(listErreur);
			}
			
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			promotionDTO.setResult(false);
			promotionDTO.setStatus(false);
			promotionDTO.setRow(null);
			promotionDTO.setRows(null);
			promotionDTO.setMessage(ex.getMessage());
			promotionDTO.setTotal(0);
			promotionDTO.setErrors(listErreur);
		}
		return promotionDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public PromotionDTO delete(Long id) {
		// TODO Auto-generated method stub
		
		PromotionDTO promotionDTO = new PromotionDTO();
		Promotion promotion;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		
		try{
			promotion = promotionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			if(promotion == null){
				sb = new StringBuilder();
				erreur.setCode(12);
				erreur.setDescription("promotion inexistant");
				sb.append("cette promotion est inexistant dans le systeme");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
				
				promotionDTO.setResult(false);
				promotionDTO.setStatus(false);
				promotionDTO.setRow(null);
				promotionDTO.setRows(null);
				promotionDTO.setMessage("voir liste erreur");
				promotionDTO.setTotal(0);
				promotionDTO.setErrors(listErreur);
			} 
			
			if(listErreur.isEmpty()) {
				promotionRepository.delete(promotion);
				sb = new StringBuilder();
				sb.append(promotion.getLibelle()).append(" supprime avec succes");
				promotionDTO.setResult(true);
				promotionDTO.setStatus(true);
				promotionDTO.setRow(promotion);
				promotionDTO.setRows(null);
				promotionDTO.setMessage(sb.toString());
				promotionDTO.setTotal(0);
				promotionDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			promotionDTO.setResult(false);
			promotionDTO.setStatus(false);
			promotionDTO.setRow(null);
			promotionDTO.setRows(null);
			promotionDTO.setMessage(ex.getMessage());
			promotionDTO.setTotal(0);
			promotionDTO.setErrors(listErreur);
		}
		return promotionDTO;
	}

	@Override
	public PromotionDTO findPromotion(Long id) {
		// TODO Auto-generated method stub
		
		PromotionDTO promotionDTO = new PromotionDTO();
		Promotion promotion;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		
		try{
			promotion = promotionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			if(promotion == null){
				promotionDTO.setResult(true);
				promotionDTO.setStatus(true);
				promotionDTO.setRow(promotion);
				promotionDTO.setRows(null);
				promotionDTO.setMessage("promotion inexistant dans le systeme");
				promotionDTO.setTotal(1);
				promotionDTO.setErrors(listErreur);
			} else {
				promotionDTO.setResult(true);
				promotionDTO.setStatus(true);
				promotionDTO.setRow(promotion);
				promotionDTO.setRows(null);
				promotionDTO.setMessage("promotion trouve avec succes");
				promotionDTO.setTotal(1);
				promotionDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			promotionDTO.setResult(false);
			promotionDTO.setStatus(false);
			promotionDTO.setRow(null);
			promotionDTO.setRows(null);
			promotionDTO.setMessage(ex.getMessage());
			promotionDTO.setTotal(0);
			promotionDTO.setErrors(listErreur);
		}
		return promotionDTO;
	}

	@Override
	public PromotionDTO findPromotions() {
		// TODO Auto-generated method stub
		
		PromotionDTO promotionDTO = new PromotionDTO();
		List<Promotion> listPromotion = new ArrayList<Promotion>();
		
		try{
			listPromotion = promotionRepository.findAll();
			if(listPromotion == null){
				promotionDTO.setResult(true);
				promotionDTO.setStatus(true);
				promotionDTO.setRow(null);
				promotionDTO.setRows(new ArrayList<Promotion>());
				promotionDTO.setMessage("aucun promotion trouve");
				promotionDTO.setTotal(0);
				promotionDTO.setErrors(listErreur);
			} else {
				int i = listPromotion.size();
				sb = new StringBuilder();
				sb.append(i).append(" promotion(s) trouve(s) avec succes");
				promotionDTO.setResult(true);
				promotionDTO.setStatus(true);
				promotionDTO.setRow(null);
				promotionDTO.setRows(listPromotion);
				promotionDTO.setMessage(sb.toString());
				promotionDTO.setTotal(i);
				promotionDTO.setErrors(listErreur);
			}
		} catch (Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			
			promotionDTO.setResult(false);
			promotionDTO.setStatus(false);
			promotionDTO.setRow(null);
			promotionDTO.setRows(listPromotion);
			promotionDTO.setMessage(ex.getMessage());
			promotionDTO.setTotal(0);
			promotionDTO.setErrors(listErreur);
		}
		return promotionDTO;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) promotionRepository.count();
	}

	@Override
	public PromotionDTO loadPromotions(Pageable pageable) {
		// TODO Auto-generated method stub
		PromotionDTO promotionDTO = new PromotionDTO();
		Page<Promotion> page = promotionRepository.findAll(pageable);
		promotionDTO.setResult(true);
		promotionDTO.setStatus(true);
		promotionDTO.setRows(page.getContent());
		promotionDTO.setTotal(page.getTotalElements());
		return promotionDTO;
	}

	@Override
	public PromotionDTO loadPromotions(Pageable pageable, String libelle, String description) {
		// TODO Auto-generated method stub
		PromotionDTO promotionDTO = new PromotionDTO();
		Page<Promotion> page = promotionRepository.findByLibelleContainingOrDescriptionContaining(pageable, libelle, description);
		promotionDTO.setResult(true);
		promotionDTO.setStatus(true);
		promotionDTO.setRows(page.getContent());
		promotionDTO.setTotal(page.getTotalElements());
		return promotionDTO;
	}

    @Override
    public List<Promotion> getPromotions() {
        return promotionRepository.findAll();
    }

}
