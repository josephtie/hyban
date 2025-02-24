package com.nectux.mizan.hyban.rh.carriere.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nectux.mizan.hyban.rh.carriere.entity.Sanction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.nectux.mizan.hyban.rh.carriere.dto.SanctionDTO;
import com.nectux.mizan.hyban.rh.carriere.repository.SanctionRepository;
import com.nectux.mizan.hyban.rh.carriere.repository.TypeSanctionRepository;
import com.nectux.mizan.hyban.rh.carriere.service.SanctionService;
import com.nectux.mizan.hyban.utils.Erreur;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service("sanctionService")
public class SanctionServiceImpl implements SanctionService {
	
	@Autowired private SanctionRepository sanctionRepository;
	@Autowired private TypeSanctionRepository typeSanctionRepository;
	
	private StringBuilder sb;
	private Erreur erreur;
	private List<Erreur> listErreur;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public SanctionDTO save(Long id, Long idTypeSanction, String faute, String commentaire) {
		// TODO Auto-generated method stub
		SanctionDTO sanctionDTO = new SanctionDTO();
		Sanction sanction;
		listErreur = new ArrayList<Erreur>();
		try{
			if(id == null){
				sanction = new Sanction();
				sanction.setDateCreation(new Date());
			} else {
				sanction = sanctionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
				sanction.setDateModification(new Date());
			}
			sanction.setFaute(faute);
			sanction.setCommentaire(commentaire);
			
			if(faute == null){
				sb = new StringBuilder();
				erreur = new Erreur();
				erreur.setCode(10);
				erreur.setDescription("contrainte d'integrite non null non respectee");
				sb.append("la faute est obligatoire");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
			} 
			
			if(idTypeSanction == null){
				sb = new StringBuilder();
				erreur = new Erreur();
				erreur.setCode(10);
				erreur.setDescription("contrainte d'integrite non null non respectee");
				sb.append("le type de sanction est obligatoire");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
			} else{
				sanction.setTypeSanction(typeSanctionRepository.findById(idTypeSanction).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id)));
	     	}
			/*if(id == null){
				if(sanctionRepository.findByFaute(sanction.getFaute()) != null){
					sb = new StringBuilder();
					erreur = new Erreur();
					erreur.setCode(10);
					erreur.setDescription("contrainte de doublon non respecte");
					sb.append("cette sanction existe deja");
					erreur.setMessage(sb.toString());
					listErreur.add(erreur);
				}
			} else {
				if(sanctionRepository.findByIdNotAndFaute(id, sanction.getFaute()) != null){
					sb = new StringBuilder();
					erreur = new Erreur();
					erreur.setCode(10);
					erreur.setDescription("contrainte de doublon non respecte");
					sb.append("cette sanction existe deja");
					erreur.setMessage(sb.toString());
					listErreur.add(erreur);
				}
			}*/
			
			if(listErreur.isEmpty()){
				sanction = sanctionRepository.save(sanction);
				sb = new StringBuilder();
				sb.append(" sanction enregistree avec succes");
				sanctionDTO.setResult(true);
				sanctionDTO.setStatus(true);
				sanctionDTO.setRow(sanction);
				sanctionDTO.setRows(null);
				sanctionDTO.setMessage(sb.toString());
				sanctionDTO.setTotal(0);
				sanctionDTO.setErrors(listErreur);
			} else {
				sanctionDTO.setResult(false);
				sanctionDTO.setStatus(false);
				sanctionDTO.setRow(null);
				sanctionDTO.setRows(null);
				sanctionDTO.setMessage("voir liste erreur");
				sanctionDTO.setTotal(0);
				sanctionDTO.setErrors(listErreur);
			}
			
		} catch(Exception ex){
			erreur = new Erreur();
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			sanctionDTO.setResult(false);
			sanctionDTO.setStatus(false);
			sanctionDTO.setRow(null);
			sanctionDTO.setRows(null);
			sanctionDTO.setMessage(ex.getMessage());
			sanctionDTO.setTotal(0);
			sanctionDTO.setErrors(listErreur);
		}
		return sanctionDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public SanctionDTO delete(Long id) {
		// TODO Auto-generated method stub
		SanctionDTO sanctionDTO = new SanctionDTO();
		Sanction sanction;
		listErreur = new ArrayList<Erreur>();
		try{
			sanction = sanctionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			if(sanction == null){
				sb = new StringBuilder();
				erreur.setCode(12);
				erreur.setDescription("sanction inexistante");
				sb.append("cette sanction est inexistante dans le systeme");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
				
				sanctionDTO.setResult(false);
				sanctionDTO.setStatus(false);
				sanctionDTO.setRow(null);
				sanctionDTO.setRows(null);
				sanctionDTO.setMessage("voir liste erreur");
				sanctionDTO.setTotal(0);
				sanctionDTO.setErrors(listErreur);
			} 
			
			if(listErreur.isEmpty()) {
				sanctionRepository.delete(sanction);
				sb = new StringBuilder();
				sb.append(" sanction supprimee avec succes");
				sanctionDTO.setResult(true);
				sanctionDTO.setStatus(true);
				sanctionDTO.setRow(sanction);
				sanctionDTO.setRows(null);
				sanctionDTO.setMessage(sb.toString());
				sanctionDTO.setTotal(0);
				sanctionDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur = new Erreur();
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			sanctionDTO.setResult(false);
			sanctionDTO.setStatus(false);
			sanctionDTO.setRow(null);
			sanctionDTO.setRows(null);
			sanctionDTO.setMessage(ex.getMessage());
			sanctionDTO.setTotal(0);
			sanctionDTO.setErrors(listErreur);
		}
		return sanctionDTO;
	}

	@Override
	public SanctionDTO findSanction(Long id) {
		// TODO Auto-generated method stub
		SanctionDTO sanctionDTO = new SanctionDTO();
		Sanction sanction;
		listErreur = new ArrayList<Erreur>();
		try{
			sanction = sanctionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			if(sanction == null){
				sanctionDTO.setResult(true);
				sanctionDTO.setStatus(true);
				sanctionDTO.setRow(sanction);
				sanctionDTO.setRows(null);
				sanctionDTO.setMessage("sanction inexistante dans le systeme");
				sanctionDTO.setTotal(1);
				sanctionDTO.setErrors(listErreur);
			} else {
				sanctionDTO.setResult(true);
				sanctionDTO.setStatus(true);
				sanctionDTO.setRow(sanction);
				sanctionDTO.setRows(null);
				sanctionDTO.setMessage("sanction trouvee avec succes");
				sanctionDTO.setTotal(1);
				sanctionDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur = new Erreur();
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			sanctionDTO.setResult(false);
			sanctionDTO.setStatus(false);
			sanctionDTO.setRow(null);
			sanctionDTO.setRows(null);
			sanctionDTO.setMessage(ex.getMessage());
			sanctionDTO.setTotal(0);
			sanctionDTO.setErrors(listErreur);
		}
		return sanctionDTO;
	}

	@Override
	public SanctionDTO findSanctions() {
		// TODO Auto-generated method stub
		SanctionDTO sanctionDTO = new SanctionDTO();
		List<Sanction> listSanction = new ArrayList<Sanction>();
		listErreur = new ArrayList<Erreur>();
		try{
			listSanction = sanctionRepository.findAll();
			if(listSanction == null){
				sanctionDTO.setResult(true);
				sanctionDTO.setStatus(true);
				sanctionDTO.setRow(null);
				sanctionDTO.setRows(new ArrayList<Sanction>());
				sanctionDTO.setMessage("aucune sanction trouvee");
				sanctionDTO.setTotal(0);
				sanctionDTO.setErrors(listErreur);
			} else {
				int i = listSanction.size();
				sb = new StringBuilder();
				sb.append(i).append(" sanction(s) trouvee(s) avec succes");
				sanctionDTO.setResult(true);
				sanctionDTO.setStatus(true);
				sanctionDTO.setRow(null);
				sanctionDTO.setRows(listSanction);
				sanctionDTO.setMessage(sb.toString());
				sanctionDTO.setTotal(i);
				sanctionDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur = new Erreur();
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			sanctionDTO.setResult(false);
			sanctionDTO.setStatus(false);
			sanctionDTO.setRow(null);
			sanctionDTO.setRows(null);
			sanctionDTO.setMessage(ex.getMessage());
			sanctionDTO.setTotal(0);
			sanctionDTO.setErrors(listErreur);
		}
		return sanctionDTO;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) sanctionRepository.count();
	}

	@Override
	public SanctionDTO loadSanctions(Pageable pageable) {
		// TODO Auto-generated method stub
		SanctionDTO sanctionDTO = new SanctionDTO();
		Page<Sanction> page = sanctionRepository.findAll(pageable);
		sanctionDTO.setResult(true);
		sanctionDTO.setStatus(true);
		sanctionDTO.setRows(page.getContent());
		sanctionDTO.setTotal(page.getTotalElements());
		return sanctionDTO;
	}

	@Override
	public SanctionDTO loadSanctions(Pageable pageable, String typeSanction, String faute) {
		// TODO Auto-generated method stub
		SanctionDTO sanctionDTO = new SanctionDTO();
		Page<Sanction> page = sanctionRepository.findByTypeSanctionLibelleContainingAndFauteContaining(pageable, typeSanction, faute);
		sanctionDTO.setResult(true);
		sanctionDTO.setStatus(true);
		sanctionDTO.setRows(page.getContent());
		sanctionDTO.setTotal(page.getTotalElements());
		return sanctionDTO;
	}

    @Override
    public List<Sanction> getSanctions() {
        return sanctionRepository.findAll();
    }

}
