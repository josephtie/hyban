package com.nectux.mizan.hyban.rh.carriere.service.impl;

import com.nectux.mizan.hyban.rh.carriere.dto.SiteWorkDTO;
import com.nectux.mizan.hyban.rh.carriere.entity.Poste;
import com.nectux.mizan.hyban.rh.carriere.entity.Site;
import com.nectux.mizan.hyban.rh.carriere.repository.PosteRepository;
import com.nectux.mizan.hyban.rh.carriere.repository.SiteWorkRepository;
import com.nectux.mizan.hyban.rh.carriere.service.PosteService;
import com.nectux.mizan.hyban.rh.carriere.service.SiteService;
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
@Service("siteService")
public class SiteServiceImpl implements SiteService {
	
	@Autowired private SiteWorkRepository posteRepository;
	
	private StringBuilder sb;
	private Erreur erreur;
	private List<Erreur> listErreur;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public SiteWorkDTO save(Long id, String libelle, String description) {
		// TODO Auto-generated method stub
		
		SiteWorkDTO SiteWorkDTO = new SiteWorkDTO();
		Site site;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		
		try{
			if(id == null){
				site = new Site();
			//	site.setDateCreation(new Date());
			} else {
				site = posteRepository.findById(id) .orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
				//site.setDateModification(new Date());
			}
			site.setLibelle(libelle);
			site.setDescription(description);
			
			if(id == null){
				if(posteRepository.findByLibelle(site.getLibelle()) != null){
					sb = new StringBuilder();
					erreur.setCode(10);
					erreur.setDescription("contrainte de doublon non respecte");
					sb.append("ce poste existe deja");
					erreur.setMessage(sb.toString());
					listErreur.add(erreur);
				}
			} else {
				if(posteRepository.findByIdNotAndLibelle(id, site.getLibelle()) != null){
					sb = new StringBuilder();
					erreur.setCode(10);
					erreur.setDescription("contrainte de doublon non respecte");
					sb.append("ce poste existe deja");
					erreur.setMessage(sb.toString());
					listErreur.add(erreur);
				}
			}
			
			if(site.getLibelle() == null || site.getLibelle().trim().equals("")){
				sb = new StringBuilder();
				erreur.setCode(10);
				erreur.setDescription("contrainte d'integrite non null non respectee");
				sb.append("le champ libelle est obligatoire");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
			} 
			
			if(listErreur.isEmpty()){
				site = posteRepository.save(site);
				sb = new StringBuilder();
				sb.append(site.getLibelle()).append(" enregistre avec succes");
                SiteWorkDTO.setResult(true);
                SiteWorkDTO.setStatus(true);
                SiteWorkDTO.setRow(site);
                SiteWorkDTO.setRows(null);
                SiteWorkDTO.setMessage(sb.toString());
                SiteWorkDTO.setTotal(0);
                SiteWorkDTO.setErrors(listErreur);
			} else {
                SiteWorkDTO.setResult(false);
                SiteWorkDTO.setStatus(false);
                SiteWorkDTO.setRow(null);
                SiteWorkDTO.setRows(null);
                SiteWorkDTO.setMessage("voir liste erreur");
                SiteWorkDTO.setTotal(0);
                SiteWorkDTO.setErrors(listErreur);
			}
			
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
            SiteWorkDTO.setResult(false);
            SiteWorkDTO.setStatus(false);
            SiteWorkDTO.setRow(null);
            SiteWorkDTO.setRows(null);
            SiteWorkDTO.setMessage(ex.getMessage());
            SiteWorkDTO.setTotal(0);
            SiteWorkDTO.setErrors(listErreur);
		}
		return SiteWorkDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public SiteWorkDTO delete(Long id) {
		// TODO Auto-generated method stub
		
		SiteWorkDTO siteWorkDTO = new SiteWorkDTO();
        Site poste;
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
				
				siteWorkDTO.setResult(false);
				siteWorkDTO.setStatus(false);
				siteWorkDTO.setRow(null);
				siteWorkDTO.setRows(null);
				siteWorkDTO.setMessage("voir liste erreur");
				siteWorkDTO.setTotal(0);
				siteWorkDTO.setErrors(listErreur);
			} 
			
			if(listErreur.isEmpty()) {
				posteRepository.delete(poste);
				sb = new StringBuilder();
				sb.append(poste.getLibelle()).append(" supprime avec succes");
				siteWorkDTO.setResult(true);
				siteWorkDTO.setStatus(true);
				siteWorkDTO.setRow(poste);
				siteWorkDTO.setRows(null);
				siteWorkDTO.setMessage(sb.toString());
				siteWorkDTO.setTotal(0);
				siteWorkDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			siteWorkDTO.setResult(false);
			siteWorkDTO.setStatus(false);
			siteWorkDTO.setRow(null);
			siteWorkDTO.setRows(null);
			siteWorkDTO.setMessage(ex.getMessage());
			siteWorkDTO.setTotal(0);
			siteWorkDTO.setErrors(listErreur);
		}
		return siteWorkDTO;
	}

	@Override
	public SiteWorkDTO findPoste(Long id) {
		// TODO Auto-generated method stub
		
		SiteWorkDTO siteWorkDTO = new SiteWorkDTO();
		Site poste;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		
		try{
			poste = posteRepository.findById(id) .orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			if(poste == null){
				siteWorkDTO.setResult(true);
				siteWorkDTO.setStatus(true);
				siteWorkDTO.setRow(poste);
				siteWorkDTO.setRows(null);
				siteWorkDTO.setMessage("poste inexistant dans le systeme");
				siteWorkDTO.setTotal(1);
				siteWorkDTO.setErrors(listErreur);
			} else {
				siteWorkDTO.setResult(true);
				siteWorkDTO.setStatus(true);
				siteWorkDTO.setRow(poste);
				siteWorkDTO.setRows(null);
				siteWorkDTO.setMessage("poste trouve avec succes");
				siteWorkDTO.setTotal(1);
				siteWorkDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			siteWorkDTO.setResult(false);
			siteWorkDTO.setStatus(false);
			siteWorkDTO.setRow(null);
			siteWorkDTO.setRows(null);
			siteWorkDTO.setMessage(ex.getMessage());
			siteWorkDTO.setTotal(0);
			siteWorkDTO.setErrors(listErreur);
		}
		return siteWorkDTO;
	}



//    @Override
//	public SiteWorkDTO getPostes() {
//		// TODO Auto-generated method stub
//
//		SiteWorkDTO siteWorkDTO = new SiteWorkDTO();
//		List<Site> listPoste = new ArrayList<Site>();
//
//		try{
//			listPoste = posteRepository.findAll();
//			if(listPoste == null){
//				siteWorkDTO.setResult(true);
//				siteWorkDTO.setStatus(true);
//				siteWorkDTO.setRow(null);
//				siteWorkDTO.setRows(new ArrayList<Site>());
//				siteWorkDTO.setMessage("aucun poste trouve");
//				siteWorkDTO.setTotal(0);
//				siteWorkDTO.setErrors(listErreur);
//			} else {
//				int i = listPoste.size();
//				sb = new StringBuilder();
//				sb.append(i).append(" poste(s) trouve(s) avec succes");
//				siteWorkDTO.setResult(true);
//				siteWorkDTO.setStatus(true);
//				siteWorkDTO.setRow(null);
//				siteWorkDTO.setRows(listPoste);
//				siteWorkDTO.setMessage(sb.toString());
//				siteWorkDTO.setTotal(i);
//				siteWorkDTO.setErrors(listErreur);
//			}
//		} catch (Exception ex){
//			erreur.setCode(20);
//			erreur.setDescription("exception capturee");
//			listErreur.add(erreur);
//			ex.printStackTrace();
//
//			siteWorkDTO.setResult(false);
//			siteWorkDTO.setStatus(false);
//			siteWorkDTO.setRow(null);
//			siteWorkDTO.setRows(listPoste);
//			siteWorkDTO.setMessage(ex.getMessage());
//			siteWorkDTO.setTotal(0);
//			siteWorkDTO.setErrors(listErreur);
//		}
//		return siteWorkDTO;
//	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) posteRepository.count();
	}

	@Override
	public SiteWorkDTO loadPostes(Pageable pageable) {
		// TODO Auto-generated method stub
		SiteWorkDTO siteWorkDTO = new SiteWorkDTO();
		Page<Site> page = posteRepository.findAll(pageable);
		siteWorkDTO.setResult(true);
		siteWorkDTO.setStatus(true);
		siteWorkDTO.setRows(page.getContent());
		siteWorkDTO.setTotal(page.getTotalElements());
		return siteWorkDTO;
	}

	@Override
	public SiteWorkDTO loadPostes(Pageable pageable, String libelle, String description) {
		// TODO Auto-generated method stub
		SiteWorkDTO siteWorkDTO = new SiteWorkDTO();
		Page<Site> page = posteRepository.findByLibelleContaining(pageable, libelle);
		siteWorkDTO.setResult(true);
		siteWorkDTO.setStatus(true);
		siteWorkDTO.setRows(page.getContent());
		siteWorkDTO.setTotal(page.getTotalElements());
		return siteWorkDTO;
	}

    @Override
    public List<Site> getPostes() {

        return posteRepository.findAll();
    }

}
