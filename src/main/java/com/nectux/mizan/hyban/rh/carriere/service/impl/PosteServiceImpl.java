package com.nectux.mizan.hyban.rh.carriere.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nectux.mizan.hyban.rh.carriere.dto.PosteDTO;
import com.nectux.mizan.hyban.rh.carriere.entity.Poste;
import com.nectux.mizan.hyban.rh.carriere.repository.PosteRepository;
import com.nectux.mizan.hyban.rh.carriere.service.PosteService;
import com.nectux.mizan.hyban.utils.Erreur;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service("posteService")
public class PosteServiceImpl implements PosteService {
	
	@Autowired private PosteRepository posteRepository;
	
	private StringBuilder sb;
	private Erreur erreur;
	private List<Erreur> listErreur;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public PosteDTO save(Long id, String libelle, String description) {
		// TODO Auto-generated method stub
		
		PosteDTO posteDTO = new PosteDTO();
		Poste poste;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		
		try{
			if(id == null){
				poste = new Poste();
				poste.setDateCreation(new Date());
			} else {
				poste = posteRepository.findById(id) .orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
				poste.setDateModification(new Date());
			}
			poste.setLibelle(libelle);
			poste.setDescription(description);
			
			if(id == null){
				if(posteRepository.findByLibelle(poste.getLibelle()) != null){
					sb = new StringBuilder();
					erreur.setCode(10);
					erreur.setDescription("contrainte de doublon non respecte");
					sb.append("ce poste existe deja");
					erreur.setMessage(sb.toString());
					listErreur.add(erreur);
				}
			} else {
				if(posteRepository.findByIdNotAndLibelle(id, poste.getLibelle()) != null){
					sb = new StringBuilder();
					erreur.setCode(10);
					erreur.setDescription("contrainte de doublon non respecte");
					sb.append("ce poste existe deja");
					erreur.setMessage(sb.toString());
					listErreur.add(erreur);
				}
			}
			
			if(poste.getLibelle() == null || poste.getLibelle().trim().equals("")){
				sb = new StringBuilder();
				erreur.setCode(10);
				erreur.setDescription("contrainte d'integrite non null non respectee");
				sb.append("le champ libelle est obligatoire");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
			} 
			
			if(listErreur.isEmpty()){
				poste = posteRepository.save(poste);
				sb = new StringBuilder();
				sb.append(poste.getLibelle()).append(" enregistre avec succes");
				posteDTO.setResult(true);
				posteDTO.setStatus(true);
				posteDTO.setRow(poste);
				posteDTO.setRows(null);
				posteDTO.setMessage(sb.toString());
				posteDTO.setTotal(0);
				posteDTO.setErrors(listErreur);
			} else {
				posteDTO.setResult(false);
				posteDTO.setStatus(false);
				posteDTO.setRow(null);
				posteDTO.setRows(null);
				posteDTO.setMessage("voir liste erreur");
				posteDTO.setTotal(0);
				posteDTO.setErrors(listErreur);
			}
			
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			posteDTO.setResult(false);
			posteDTO.setStatus(false);
			posteDTO.setRow(null);
			posteDTO.setRows(null);
			posteDTO.setMessage(ex.getMessage());
			posteDTO.setTotal(0);
			posteDTO.setErrors(listErreur);
		}
		return posteDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public PosteDTO delete(Long id) {
		// TODO Auto-generated method stub
		
		PosteDTO posteDTO = new PosteDTO();
		Poste poste;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		
		try{
			poste = posteRepository.findById(id) .orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			if(poste == null){
				sb = new StringBuilder();
				erreur.setCode(12);
				erreur.setDescription("poste inexistant");
				sb.append("ce poste est inexistant dans le systeme");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
				
				posteDTO.setResult(false);
				posteDTO.setStatus(false);
				posteDTO.setRow(null);
				posteDTO.setRows(null);
				posteDTO.setMessage("voir liste erreur");
				posteDTO.setTotal(0);
				posteDTO.setErrors(listErreur);
			} 
			
			if(listErreur.isEmpty()) {
				posteRepository.delete(poste);
				sb = new StringBuilder();
				sb.append(poste.getLibelle()).append(" supprime avec succes");
				posteDTO.setResult(true);
				posteDTO.setStatus(true);
				posteDTO.setRow(poste);
				posteDTO.setRows(null);
				posteDTO.setMessage(sb.toString());
				posteDTO.setTotal(0);
				posteDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			posteDTO.setResult(false);
			posteDTO.setStatus(false);
			posteDTO.setRow(null);
			posteDTO.setRows(null);
			posteDTO.setMessage(ex.getMessage());
			posteDTO.setTotal(0);
			posteDTO.setErrors(listErreur);
		}
		return posteDTO;
	}

	@Override
	public PosteDTO findPoste(Long id) {
		// TODO Auto-generated method stub
		
		PosteDTO posteDTO = new PosteDTO();
		Poste poste;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		
		try{
			poste = posteRepository.findById(id) .orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			if(poste == null){
				posteDTO.setResult(true);
				posteDTO.setStatus(true);
				posteDTO.setRow(poste);
				posteDTO.setRows(null);
				posteDTO.setMessage("poste inexistant dans le systeme");
				posteDTO.setTotal(1);
				posteDTO.setErrors(listErreur);
			} else {
				posteDTO.setResult(true);
				posteDTO.setStatus(true);
				posteDTO.setRow(poste);
				posteDTO.setRows(null);
				posteDTO.setMessage("poste trouve avec succes");
				posteDTO.setTotal(1);
				posteDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			posteDTO.setResult(false);
			posteDTO.setStatus(false);
			posteDTO.setRow(null);
			posteDTO.setRows(null);
			posteDTO.setMessage(ex.getMessage());
			posteDTO.setTotal(0);
			posteDTO.setErrors(listErreur);
		}
		return posteDTO;
	}

	@Override
	public PosteDTO findPostes() {
		// TODO Auto-generated method stub
		
		PosteDTO posteDTO = new PosteDTO();
		List<Poste> listPoste = new ArrayList<Poste>();
		
		try{
			listPoste = posteRepository.findAll();
			if(listPoste == null){
				posteDTO.setResult(true);
				posteDTO.setStatus(true);
				posteDTO.setRow(null);
				posteDTO.setRows(new ArrayList<Poste>());
				posteDTO.setMessage("aucun poste trouve");
				posteDTO.setTotal(0);
				posteDTO.setErrors(listErreur);
			} else {
				int i = listPoste.size();
				sb = new StringBuilder();
				sb.append(i).append(" poste(s) trouve(s) avec succes");
				posteDTO.setResult(true);
				posteDTO.setStatus(true);
				posteDTO.setRow(null);
				posteDTO.setRows(listPoste);
				posteDTO.setMessage(sb.toString());
				posteDTO.setTotal(i);
				posteDTO.setErrors(listErreur);
			}
		} catch (Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			
			posteDTO.setResult(false);
			posteDTO.setStatus(false);
			posteDTO.setRow(null);
			posteDTO.setRows(listPoste);
			posteDTO.setMessage(ex.getMessage());
			posteDTO.setTotal(0);
			posteDTO.setErrors(listErreur);
		}
		return posteDTO;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) posteRepository.count();
	}

	@Override
	public PosteDTO loadPostes(Pageable pageable) {
		// TODO Auto-generated method stub
		PosteDTO posteDTO = new PosteDTO();
		Page<Poste> page = posteRepository.findAll(pageable);
		posteDTO.setResult(true);
		posteDTO.setStatus(true);
		posteDTO.setRows(page.getContent());
		posteDTO.setTotal(page.getTotalElements());
		return posteDTO;
	}

	@Override
	public PosteDTO loadPostes(Pageable pageable, String libelle, String description) {
		// TODO Auto-generated method stub
		PosteDTO posteDTO = new PosteDTO();
		Page<Poste> page = posteRepository.findByLibelleContainingOrDescriptionContaining(pageable, libelle, description);
		posteDTO.setResult(true);
		posteDTO.setStatus(true);
		posteDTO.setRows(page.getContent());
		posteDTO.setTotal(page.getTotalElements());
		return posteDTO;
	}

    @Override
    public List<Poste> getPostes() {
        return posteRepository.findAll();
    }

}
