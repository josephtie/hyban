package com.nectux.mizan.hyban.rh.carriere.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nectux.mizan.hyban.personnel.repository.FonctionRepository;
import com.nectux.mizan.hyban.personnel.repository.PersonnelRepository;
import com.nectux.mizan.hyban.rh.carriere.dto.PromotionPersonnelDTO;
import com.nectux.mizan.hyban.rh.carriere.entity.PromotionPersonnel;
import com.nectux.mizan.hyban.rh.carriere.repository.PromotionPersonnelRepository;
import com.nectux.mizan.hyban.rh.carriere.repository.PromotionRepository;
import com.nectux.mizan.hyban.rh.carriere.service.PromotionPersonnelService;
import com.nectux.mizan.hyban.utils.DateManager;
import com.nectux.mizan.hyban.utils.Erreur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service("promotionPersonnelService")
public class PromotionPersonnelServiceImpl implements PromotionPersonnelService {
	
	@Autowired private PersonnelRepository personneRepository;
	@Autowired private FonctionRepository promotionRepository;
	@Autowired private PromotionPersonnelRepository promotionPersonnelRepository;
	
	private StringBuilder sb;
	private Erreur erreur;
	private List<Erreur> listErreur;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public PromotionPersonnelDTO save(Long id, Long idPersonnel, Long idPromotion, String datePromotion, String commentaire) {
		// TODO Auto-generated method stub
		
		PromotionPersonnelDTO promotionPersonnelDTO = new PromotionPersonnelDTO();
		PromotionPersonnel promotionPersonnel;
		listErreur = new ArrayList<Erreur>();
		
		try{
			if(id == null){
				promotionPersonnel = new PromotionPersonnel();
				promotionPersonnel.setDateCreation(new Date());
			} else {
				promotionPersonnel = promotionPersonnelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
				promotionPersonnel.setDateModification(new Date());
			}
			promotionPersonnel.setCommentaire(commentaire);
			
			if(idPersonnel == null){
				sb = new StringBuilder();
				erreur = new Erreur();
				erreur.setCode(10);
				erreur.setDescription("contrainte d'integrite non null non respectee");
				sb.append("le personnel est obligatoire");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
			} else
				promotionPersonnel.setPersonnel(personneRepository.findById(idPersonnel).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + idPersonnel)));
			
			if(idPromotion == null){
				sb = new StringBuilder();
				erreur = new Erreur();
				erreur.setCode(10);
				erreur.setDescription("contrainte d'integrite non null non respectee");
				sb.append("la promotion est obligatoire");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
			} else 
				promotionPersonnel.setPromotion(promotionRepository.findById(idPromotion).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + idPromotion)));
			
			if(datePromotion == null || datePromotion.trim().equals("")){
				sb = new StringBuilder();
				erreur = new Erreur();
				erreur.setCode(10);
				erreur.setDescription("contrainte d'integrite non null non respectee");
				sb.append("la date de debut est obligatoire");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
			} else 
				promotionPersonnel.setDatePromotion(DateManager.stringToDate(datePromotion, "dd/MM/yyyy"));
			
			if(id == null){
				if(promotionPersonnelRepository.findByPersonnelIdAndPromotionIdAndDatePromotion(idPersonnel, idPromotion, promotionPersonnel.getDatePromotion()) != null){
					sb = new StringBuilder();
					erreur = new Erreur();
					erreur.setCode(10);
					erreur.setDescription("contrainte de doublon non non respectee");
					sb.append("cette promotion existe deja");
					erreur.setMessage(sb.toString());
					listErreur.add(erreur);
				}
			} else{
				
			}
			
			if(listErreur.isEmpty()){
				promotionPersonnel = promotionPersonnelRepository.save(promotionPersonnel);
				sb = new StringBuilder();
				sb.append(" promotion enregistree avec succes");
				promotionPersonnelDTO.setResult(true);
				promotionPersonnelDTO.setStatus(true);
				promotionPersonnelDTO.setRow(promotionPersonnel);
				promotionPersonnelDTO.setRows(null);
				promotionPersonnelDTO.setMessage(sb.toString());
				promotionPersonnelDTO.setTotal(0);
				promotionPersonnelDTO.setErrors(listErreur);
			} else {
				promotionPersonnelDTO.setResult(false);
				promotionPersonnelDTO.setStatus(false);
				promotionPersonnelDTO.setRow(null);
				promotionPersonnelDTO.setRows(null);
				promotionPersonnelDTO.setMessage("voir liste erreur");
				promotionPersonnelDTO.setTotal(0);
				promotionPersonnelDTO.setErrors(listErreur);
			}
			
		}catch (Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			promotionPersonnelDTO.setResult(false);
			promotionPersonnelDTO.setStatus(false);
			promotionPersonnelDTO.setRow(null);
			promotionPersonnelDTO.setRows(null);
			promotionPersonnelDTO.setMessage(ex.getMessage());
			promotionPersonnelDTO.setTotal(0);
			promotionPersonnelDTO.setErrors(listErreur);
		}
		return promotionPersonnelDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public PromotionPersonnelDTO save(Long id, Long idPersonnel, String libelle, String description,
			String datePromotion) {
		// TODO Auto-generated method stub
		
		PromotionPersonnelDTO promotionPersonnelDTO = new PromotionPersonnelDTO();
		listErreur = new ArrayList<Erreur>();
		
		try{
			
		}catch (Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			promotionPersonnelDTO.setResult(false);
			promotionPersonnelDTO.setStatus(false);
			promotionPersonnelDTO.setRow(null);
			promotionPersonnelDTO.setRows(null);
			promotionPersonnelDTO.setMessage(ex.getMessage());
			promotionPersonnelDTO.setTotal(0);
			promotionPersonnelDTO.setErrors(listErreur);
		}
		return promotionPersonnelDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public PromotionPersonnelDTO delete(Long id) {
		// TODO Auto-generated method stub
		PromotionPersonnelDTO promotionPersonnelDTO = new PromotionPersonnelDTO();
		listErreur = new ArrayList<Erreur>();
		
		try{
			PromotionPersonnel promotionPersonnel = promotionPersonnelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			if(promotionPersonnel == null){
				sb = new StringBuilder();
				erreur.setCode(12);
				erreur.setDescription("promotion inexistante");
				sb.append("cette promotion est inexistante dans le systeme");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
				
				promotionPersonnelDTO.setResult(false);
				promotionPersonnelDTO.setStatus(false);
				promotionPersonnelDTO.setRow(null);
				promotionPersonnelDTO.setRows(null);
				promotionPersonnelDTO.setMessage("voir liste erreur");
				promotionPersonnelDTO.setTotal(0);
				promotionPersonnelDTO.setErrors(listErreur);
			} 
			
			if(listErreur.isEmpty()) {
				promotionPersonnelRepository.delete(promotionPersonnel);
				sb = new StringBuilder();
				sb.append(" promotion supprimee avec succes");
				promotionPersonnelDTO.setResult(true);
				promotionPersonnelDTO.setStatus(true);
				promotionPersonnelDTO.setRow(promotionPersonnel);
				promotionPersonnelDTO.setRows(null);
				promotionPersonnelDTO.setMessage(sb.toString());
				promotionPersonnelDTO.setTotal(0);
				promotionPersonnelDTO.setErrors(listErreur);
			}
		}catch (Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			promotionPersonnelDTO.setResult(false);
			promotionPersonnelDTO.setStatus(false);
			promotionPersonnelDTO.setRow(null);
			promotionPersonnelDTO.setRows(null);
			promotionPersonnelDTO.setMessage(ex.getMessage());
			promotionPersonnelDTO.setTotal(0);
			promotionPersonnelDTO.setErrors(listErreur);
		}
		return promotionPersonnelDTO;
	}

	@Override
	public PromotionPersonnelDTO findPromotionPersonnel(Long id) {
		// TODO Auto-generated method stub
		
		PromotionPersonnelDTO promotionPersonnelDTO = new PromotionPersonnelDTO();
		PromotionPersonnel promotionPersonnel;
		listErreur = new ArrayList<Erreur>();
		
		try{
			promotionPersonnel = promotionPersonnelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			if(promotionPersonnel == null){
				promotionPersonnelDTO.setResult(true);
				promotionPersonnelDTO.setStatus(true);
				promotionPersonnelDTO.setRow(promotionPersonnel);
				promotionPersonnelDTO.setRows(null);
				promotionPersonnelDTO.setMessage("promotion inexistante dans le systeme");
				promotionPersonnelDTO.setTotal(1);
				promotionPersonnelDTO.setErrors(listErreur);
			} else {
				promotionPersonnelDTO.setResult(true);
				promotionPersonnelDTO.setStatus(true);
				promotionPersonnelDTO.setRow(promotionPersonnel);
				promotionPersonnelDTO.setRows(null);
				promotionPersonnelDTO.setMessage("promotionPersonnel trouvee avec succes");
				promotionPersonnelDTO.setTotal(1);
				promotionPersonnelDTO.setErrors(listErreur);
			}
		}catch (Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			promotionPersonnelDTO.setResult(false);
			promotionPersonnelDTO.setStatus(false);
			promotionPersonnelDTO.setRow(null);
			promotionPersonnelDTO.setRows(null);
			promotionPersonnelDTO.setMessage(ex.getMessage());
			promotionPersonnelDTO.setTotal(0);
			promotionPersonnelDTO.setErrors(listErreur);
		}
		return promotionPersonnelDTO;
	}

	@Override
	public PromotionPersonnelDTO findPromotionPersonnels() {
		// TODO Auto-generated method stub
		
		PromotionPersonnelDTO promotionPersonnelDTO = new PromotionPersonnelDTO();
		List<PromotionPersonnel> listPromotionPersonnel;
		listErreur = new ArrayList<Erreur>();
		
		try{
			listPromotionPersonnel = promotionPersonnelRepository.findAll();
			if(listPromotionPersonnel == null){
				promotionPersonnelDTO.setResult(true);
				promotionPersonnelDTO.setStatus(true);
				promotionPersonnelDTO.setRow(null);
				promotionPersonnelDTO.setRows(new ArrayList<PromotionPersonnel>());
				promotionPersonnelDTO.setMessage("aucune promotion trouvee");
				promotionPersonnelDTO.setTotal(0);
				promotionPersonnelDTO.setErrors(listErreur);
			} else {
				int i = listPromotionPersonnel.size();
				sb = new StringBuilder();
				sb.append(i).append(" promotion(s) trouvee(s) avec succes");
				promotionPersonnelDTO.setResult(true);
				promotionPersonnelDTO.setStatus(true);
				promotionPersonnelDTO.setRow(null);
				promotionPersonnelDTO.setRows(listPromotionPersonnel);
				promotionPersonnelDTO.setMessage(sb.toString());
				promotionPersonnelDTO.setTotal(i);
				promotionPersonnelDTO.setErrors(listErreur);
			}
		}catch (Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			promotionPersonnelDTO.setResult(false);
			promotionPersonnelDTO.setStatus(false);
			promotionPersonnelDTO.setRow(null);
			promotionPersonnelDTO.setRows(null);
			promotionPersonnelDTO.setMessage(ex.getMessage());
			promotionPersonnelDTO.setTotal(0);
			promotionPersonnelDTO.setErrors(listErreur);
		}
		return promotionPersonnelDTO;
	}

	@Override
	public PromotionPersonnelDTO findPromotionPersonnelsByPromotion(Long idPromotion) {
		// TODO Auto-generated method stub
		
		PromotionPersonnelDTO promotionPersonnelDTO = new PromotionPersonnelDTO();
		List<PromotionPersonnel> listPromotionPersonnel;
		listErreur = new ArrayList<Erreur>();
		
		try{
			listPromotionPersonnel = promotionPersonnelRepository.findByPromotionId(idPromotion);
			if(listPromotionPersonnel == null){
				promotionPersonnelDTO.setResult(true);
				promotionPersonnelDTO.setStatus(true);
				promotionPersonnelDTO.setRow(null);
				promotionPersonnelDTO.setRows(new ArrayList<PromotionPersonnel>());
				promotionPersonnelDTO.setMessage("aucune promotion trouvee");
				promotionPersonnelDTO.setTotal(0);
				promotionPersonnelDTO.setErrors(listErreur);
			} else {
				int i = listPromotionPersonnel.size();
				sb = new StringBuilder();
				sb.append(i).append(" promotion(s) trouvee(s) avec succes");
				promotionPersonnelDTO.setResult(true);
				promotionPersonnelDTO.setStatus(true);
				promotionPersonnelDTO.setRow(null);
				promotionPersonnelDTO.setRows(listPromotionPersonnel);
				promotionPersonnelDTO.setMessage(sb.toString());
				promotionPersonnelDTO.setTotal(i);
				promotionPersonnelDTO.setErrors(listErreur);
			}
		}catch (Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			promotionPersonnelDTO.setResult(false);
			promotionPersonnelDTO.setStatus(false);
			promotionPersonnelDTO.setRow(null);
			promotionPersonnelDTO.setRows(null);
			promotionPersonnelDTO.setMessage(ex.getMessage());
			promotionPersonnelDTO.setTotal(0);
			promotionPersonnelDTO.setErrors(listErreur);
		}
		return promotionPersonnelDTO;
	}

	@Override
	public PromotionPersonnelDTO findPromotionPersonnelsByPersonnel(Long idPersonnel) {
		// TODO Auto-generated method stub
		
		PromotionPersonnelDTO promotionPersonnelDTO = new PromotionPersonnelDTO();
		List<PromotionPersonnel> listPromotionPersonnel;
		listErreur = new ArrayList<Erreur>();
		
		try{
			listPromotionPersonnel = promotionPersonnelRepository.findByPersonnelId(idPersonnel);
			if(listPromotionPersonnel == null){
				promotionPersonnelDTO.setResult(true);
				promotionPersonnelDTO.setStatus(true);
				promotionPersonnelDTO.setRow(null);
				promotionPersonnelDTO.setRows(new ArrayList<PromotionPersonnel>());
				promotionPersonnelDTO.setMessage("aucune promotion trouvee");
				promotionPersonnelDTO.setTotal(0);
				promotionPersonnelDTO.setErrors(listErreur);
			} else {
				int i = listPromotionPersonnel.size();
				sb = new StringBuilder();
				sb.append(i).append(" promotion(s) trouvee(s) avec succes");
				promotionPersonnelDTO.setResult(true);
				promotionPersonnelDTO.setStatus(true);
				promotionPersonnelDTO.setRow(null);
				promotionPersonnelDTO.setRows(listPromotionPersonnel);
				promotionPersonnelDTO.setMessage(sb.toString());
				promotionPersonnelDTO.setTotal(i);
				promotionPersonnelDTO.setErrors(listErreur);
			}
		}catch (Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			promotionPersonnelDTO.setResult(false);
			promotionPersonnelDTO.setStatus(false);
			promotionPersonnelDTO.setRow(null);
			promotionPersonnelDTO.setRows(null);
			promotionPersonnelDTO.setMessage(ex.getMessage());
			promotionPersonnelDTO.setTotal(0);
			promotionPersonnelDTO.setErrors(listErreur);
		}
		return promotionPersonnelDTO;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) promotionPersonnelRepository.count();
	}

	@Override
	public PromotionPersonnelDTO loadPromotionPersonnels(Pageable pageable) {
		// TODO Auto-generated method stub
		PromotionPersonnelDTO promotionPersonnelDTO = new PromotionPersonnelDTO();
		Page<PromotionPersonnel> page = promotionPersonnelRepository.findAll(pageable);
		promotionPersonnelDTO.setResult(true);
		promotionPersonnelDTO.setStatus(true);
		promotionPersonnelDTO.setRows(page.getContent());
		promotionPersonnelDTO.setTotal(page.getTotalElements());
		return promotionPersonnelDTO;
	}

	@Override
	public PromotionPersonnelDTO loadPromotionPersonnels(Pageable pageable, String nom, String prenom, String libelle) {
		// TODO Auto-generated method stub
		PromotionPersonnelDTO promotionPersonnelDTO = new PromotionPersonnelDTO();
		Page<PromotionPersonnel> page = promotionPersonnelRepository.findByPersonnelNomContainingOrPersonnelPrenomContainingOrPromotionLibelle(pageable, nom, prenom, libelle);
		promotionPersonnelDTO.setResult(true);
		promotionPersonnelDTO.setStatus(true);
		promotionPersonnelDTO.setRows(page.getContent());
		promotionPersonnelDTO.setTotal(page.getTotalElements());
		return promotionPersonnelDTO;
	}

}
