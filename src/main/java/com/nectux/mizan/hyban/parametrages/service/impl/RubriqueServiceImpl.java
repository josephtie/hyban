package com.nectux.mizan.hyban.parametrages.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.nectux.mizan.hyban.parametrages.repository.RubriqueRepository;
import com.nectux.mizan.hyban.parametrages.dto.RubriqueDTO;
import com.nectux.mizan.hyban.parametrages.entity.Rubrique;
import com.nectux.mizan.hyban.parametrages.service.RubriqueService;
import com.nectux.mizan.hyban.utils.Erreur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service("rubriqueService")
public class RubriqueServiceImpl implements RubriqueService {
	
	@Autowired private RubriqueRepository rubriqueRepository;
	
	private StringBuilder sb;
	private Erreur erreur;
	private List<Erreur> listErreur;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public RubriqueDTO save(Long id, String libelle, Integer etatImposition, Double taux, Double mtExedent,  Boolean active, Boolean permanent, Boolean speciale) {
		// TODO Auto-generated method stub
		RubriqueDTO rubriqueDTO = new RubriqueDTO();
		Rubrique rubrique;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		try{
			if(id != null){
				rubrique = rubriqueRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
				
			} else {
				rubrique = new Rubrique();
			}
			rubrique.setLibelle(libelle);
			rubrique.setEtatImposition(etatImposition);
			rubrique.setTaux(taux);
			rubrique.setMtExedent(mtExedent);
			  if(active==null  )
			  rubrique.setActive(false);
			  else
			  rubrique.setActive(active);

			if(permanent==null  )
				rubrique.setPermanent(false);
			else
				rubrique.setPermanent(permanent);


            if(speciale==null  )
                rubrique.setSpeciale(false);
            else
                rubrique.setSpeciale(speciale);
			
			if(rubrique.getLibelle() == null || rubrique.getLibelle()==""){
				sb = new StringBuilder();
				erreur.setCode(10);
				erreur.setDescription("contrainte d'integrite non null non respectee");
				sb.append("le champ libelle est obligatoire");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
			}
			
			if(rubrique.getId() == null){
				if(rubriqueRepository.findByLibelle(rubrique.getLibelle()) != null){
					sb = new StringBuilder();
					erreur.setCode(11);
					erreur.setDescription("contrainte de doublon non respectee");
					sb.append(libelle).append(" existe deja");
					erreur.setMessage(sb.toString());
					listErreur.add(erreur);
				}
			} else{
				/*if(rubriqueRepository.findByIdNotAndLibelle(rubrique.getId(), rubrique.getLibelle()) != null){
					sb = new StringBuilder();
					erreur.setCode(11);
					erreur.setDescription("contrainte de doublon non respectee");
					sb.append(libelle).append(" existe deja");
					erreur.setMessage(sb.toString());
					listErreur.add(erreur);
				}*/
			}
			
		/*	if(rubrique.getTaux() == null ){
				rubrique.setTaux(0d);
				sb = new StringBuilder();
				erreur.setCode(10);
				erreur.setDescription("contrainte d'integrite non null non respectee");
				sb.append("le champ libelle est obligatoire");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
			}
			
			if(rubrique.getActive() == null ){
				sb = new StringBuilder();
				erreur.setCode(10);
				erreur.setDescription("contrainte d'integrite non null non respectee");
				sb.append("le champ Active est obligatoire");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
			}*/
			
			
			if(listErreur.isEmpty()){
				rubrique = rubriqueRepository.save(rubrique);
				sb = new StringBuilder();
				sb.append(rubrique.getLibelle()).append(" enregistre avec succes");
				rubriqueDTO.setResult(true);
				rubriqueDTO.setStatus(true);
				rubriqueDTO.setRow(rubrique);
				rubriqueDTO.setRows(null);
				rubriqueDTO.setMessage(sb.toString());
				rubriqueDTO.setTotal(0);
				rubriqueDTO.setErrors(listErreur);
			} else {
				rubriqueDTO.setResult(false);
				rubriqueDTO.setStatus(false);
				rubriqueDTO.setRow(null);
				rubriqueDTO.setRows(null);
				rubriqueDTO.setMessage("voir liste erreur");
				rubriqueDTO.setTotal(0);
				rubriqueDTO.setErrors(listErreur);
			}
			
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			rubriqueDTO.setResult(false);
			rubriqueDTO.setStatus(false);
			rubriqueDTO.setRow(null);
			rubriqueDTO.setRows(null);
			rubriqueDTO.setMessage(ex.getMessage());
			rubriqueDTO.setTotal(0);
			rubriqueDTO.setErrors(listErreur);
		}
		return rubriqueDTO;
	}



    @Override
	@Transactional(rollbackFor = Exception.class)
	public RubriqueDTO delete(Long id) {
		// TODO Auto-generated method stub
		RubriqueDTO rubriqueDTO = new RubriqueDTO();
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		try{
			Rubrique rubrique = rubriqueRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			if(rubrique == null){
				sb = new StringBuilder();
				erreur.setCode(12);
				erreur.setDescription("type de document inexistant");
				sb.append("ce type de document est inexistant dans le systeme");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
				
				rubriqueDTO.setResult(false);
				rubriqueDTO.setStatus(false);
				rubriqueDTO.setRow(null);
				rubriqueDTO.setRows(null);
				rubriqueDTO.setMessage("voir liste erreur");
				rubriqueDTO.setTotal(0);
				rubriqueDTO.setErrors(listErreur);
			} 
			
			if(listErreur.isEmpty()) {
				rubriqueRepository.delete(rubrique);
				sb = new StringBuilder();
				sb.append(rubrique.getLibelle()).append(" supprime avec succes");
				rubriqueDTO.setResult(true);
				rubriqueDTO.setStatus(true);
				rubriqueDTO.setRow(rubrique);
				rubriqueDTO.setRows(null);
				rubriqueDTO.setMessage(sb.toString());
				rubriqueDTO.setTotal(0);
				rubriqueDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			rubriqueDTO.setResult(false);
			rubriqueDTO.setStatus(false);
			rubriqueDTO.setRow(null);
			rubriqueDTO.setRows(null);
			rubriqueDTO.setMessage(ex.getMessage());
			rubriqueDTO.setTotal(0);
			rubriqueDTO.setErrors(listErreur);
		}
		return rubriqueDTO;
	}

	@Override
	public RubriqueDTO findRubrique(Long id) {
		// TODO Auto-generated method stub
		RubriqueDTO rubriqueDTO = new RubriqueDTO();
		Rubrique banque;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		try{
			banque = rubriqueRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			if(banque == null){
				rubriqueDTO.setResult(true);
				rubriqueDTO.setStatus(true);
				rubriqueDTO.setRow(banque);
				rubriqueDTO.setRows(null);
				rubriqueDTO.setMessage("objet inexistant dans le systeme");
				rubriqueDTO.setTotal(1);
				rubriqueDTO.setErrors(listErreur);
			} else {
				rubriqueDTO.setResult(true);
				rubriqueDTO.setStatus(true);
				rubriqueDTO.setRow(banque);
				rubriqueDTO.setRows(null);
				rubriqueDTO.setMessage("objet trouve avec succes");
				rubriqueDTO.setTotal(1);
				rubriqueDTO.setErrors(listErreur);
			}
		} catch (Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			rubriqueDTO.setResult(true);
			rubriqueDTO.setStatus(true);
			rubriqueDTO.setRow(null);
			rubriqueDTO.setRows(null);
			rubriqueDTO.setMessage(ex.getMessage());
			rubriqueDTO.setTotal(0);
			rubriqueDTO.setErrors(listErreur);
		}
		return rubriqueDTO;
	}

	@Override
	public RubriqueDTO findRubriques() {
		// TODO Auto-generated method stub
		RubriqueDTO rubriqueDTO = new RubriqueDTO();
		List<Rubrique> listRubrique = new ArrayList<Rubrique>();
		try{
			listRubrique = rubriqueRepository.findAll();
			if(listRubrique == null){
				rubriqueDTO.setResult(true);
				rubriqueDTO.setStatus(true);
				rubriqueDTO.setRow(null);
				rubriqueDTO.setRows(new ArrayList<Rubrique>());
				rubriqueDTO.setMessage("aucun objet trouve");
				rubriqueDTO.setTotal(0);
				rubriqueDTO.setErrors(listErreur);
			} else {
				int i = listRubrique.size();
				sb = new StringBuilder();
				sb.append(i).append(" objet(s) trouve(s) avec succes");
				rubriqueDTO.setResult(true);
				rubriqueDTO.setStatus(true);
				rubriqueDTO.setRow(null);
				rubriqueDTO.setRows(listRubrique);
				rubriqueDTO.setMessage(sb.toString());
				rubriqueDTO.setTotal(i);
				rubriqueDTO.setErrors(listErreur);
			}
		} catch (Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			
			rubriqueDTO.setResult(false);
			rubriqueDTO.setStatus(false);
			rubriqueDTO.setRow(null);
			rubriqueDTO.setRows(listRubrique);
			rubriqueDTO.setMessage(ex.getMessage());
			rubriqueDTO.setTotal(0);
			rubriqueDTO.setErrors(listErreur);
		}
		return rubriqueDTO;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) rubriqueRepository.count();
	}

	@Override
	public RubriqueDTO loadRubriques(Pageable pageable) {
		// TODO Auto-generated method stub
		RubriqueDTO rubriqueDTO = new RubriqueDTO();
		Page<Rubrique> page = rubriqueRepository.findAll(pageable);
		rubriqueDTO.setResult(true);
		rubriqueDTO.setStatus(true);
		rubriqueDTO.setRows(page.getContent());
		rubriqueDTO.setTotal(page.getTotalElements());
		return rubriqueDTO;
	}

	@Override
	public RubriqueDTO loadRubriques(Pageable pageable, String search) {
		// TODO Auto-generated method stub
		RubriqueDTO rubriqueDTO = new RubriqueDTO();
		Page<Rubrique> page = rubriqueRepository.findByLibelleIgnoreCaseContaining(pageable, search);
		rubriqueDTO.setResult(true);
		rubriqueDTO.setStatus(true);
		rubriqueDTO.setRows(page.getContent());
		rubriqueDTO.setTotal(page.getTotalElements());
		return rubriqueDTO;
	}

	@Override
	public List<Rubrique> getRubriques() {
		// TODO Auto-generated method stub
		return rubriqueRepository.findAll();
	}

	@Override
	public Rubrique findRubriqueID(Long id) {
		// TODO Auto-generated method stub
		return rubriqueRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
	}

	@Override
	public List<Rubrique> getRubriquesActives() {
		// TODO Auto-generated method stub
		return rubriqueRepository.findByActiveTrue();
	}


	public List<Rubrique> getRubriquesActivesType(Integer etatImp) {
		return rubriqueRepository.findByActiveTrueAndEtatImposition(etatImp);
	}



	/*@Override
	public List<Rubrique> getBanquesEntprise() {
		// TODO Auto-generated method stub
		return null;
	}*/

	/*@Override
	public List<Rubrique> getRubriquesEntprise() {
		// TODO Auto-generated method stub
		return rubriqueRepository.findByStatutTrue();
	}*/
	
}
