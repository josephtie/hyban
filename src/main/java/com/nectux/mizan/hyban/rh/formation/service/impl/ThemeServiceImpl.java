package com.nectux.mizan.hyban.rh.formation.service.impl;

import com.nectux.mizan.hyban.rh.formation.dto.ThemeDTO;
import com.nectux.mizan.hyban.rh.formation.entity.Theme;
import com.nectux.mizan.hyban.rh.formation.repository.ThemeRepository;
import com.nectux.mizan.hyban.rh.formation.service.ThemeService;
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
@Service("themeService")
public class ThemeServiceImpl implements ThemeService {
	
	@Autowired
    private ThemeRepository themeRepository;
	
	private StringBuilder sb;
	private Erreur erreur;
	private List<Erreur> listErreur;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ThemeDTO save(Long id, String intitule, String description) {
		// TODO Auto-generated method stub
		
		ThemeDTO themeDTO = new ThemeDTO();
		Theme theme;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		
		try{ 
			if(id == null){
				theme = new Theme();
				theme.setDateCreation(new Date());
			} else {
				theme = themeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
				theme.setDateModification(new Date());
			}
			
			theme.setIntitule(intitule);
			theme.setDescription(description);
			
			if(listErreur.isEmpty()){
				theme = themeRepository.save(theme);
				sb = new StringBuilder();
				sb.append(theme.getIntitule()).append(" enregistre avec succes");
				themeDTO.setResult(true);
				themeDTO.setStatus(true);
				themeDTO.setRow(theme);
				themeDTO.setRows(null);
				themeDTO.setMessage(sb.toString());
				themeDTO.setTotal(0);
				themeDTO.setErrors(listErreur);
			} else {
				themeDTO.setResult(false);
				themeDTO.setStatus(false);
				themeDTO.setRow(null);
				themeDTO.setRows(null);
				themeDTO.setMessage("voir liste erreur");
				themeDTO.setTotal(0);
				themeDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			themeDTO.setResult(false);
			themeDTO.setStatus(false);
			themeDTO.setRow(null);
			themeDTO.setRows(null);
			themeDTO.setMessage(ex.getMessage());
			themeDTO.setTotal(0);
			themeDTO.setErrors(listErreur);
		}
		return themeDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ThemeDTO delete(Long id) {
		// TODO Auto-generated method stub
		
		ThemeDTO themeDTO = new ThemeDTO();
		Theme theme;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		
		try{ 
			theme = themeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			if(theme == null){
				sb = new StringBuilder();
				erreur.setCode(12);
				erreur.setDescription("theme inexistant");
				sb.append("ce theme est inexistant dans le systeme");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
				
				themeDTO.setResult(false);
				themeDTO.setStatus(false);
				themeDTO.setRow(null);
				themeDTO.setRows(null);
				themeDTO.setMessage("voir liste erreur");
				themeDTO.setTotal(0);
				themeDTO.setErrors(listErreur);
			} 
			
			if(listErreur.isEmpty()) {
				themeRepository.delete(theme);
				sb = new StringBuilder();
				sb.append(theme.getIntitule()).append(" supprime avec succes");
				themeDTO.setResult(true);
				themeDTO.setStatus(true);
				themeDTO.setRow(theme);
				themeDTO.setRows(null);
				themeDTO.setMessage(sb.toString());
				themeDTO.setTotal(0);
				themeDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			themeDTO.setResult(false);
			themeDTO.setStatus(false);
			themeDTO.setRow(null);
			themeDTO.setRows(null);
			themeDTO.setMessage(ex.getMessage());
			themeDTO.setTotal(0);
			themeDTO.setErrors(listErreur);
		}
		return themeDTO;
	}

	@Override
	public ThemeDTO findTheme(Long id) {
		// TODO Auto-generated method stub
		
		ThemeDTO themeDTO = new ThemeDTO();
		Theme theme;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		
		try{ 
			theme = themeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			if(theme == null){
				themeDTO.setResult(true);
				themeDTO.setStatus(true);
				themeDTO.setRow(theme);
				themeDTO.setRows(null);
				themeDTO.setMessage("theme inexistant dans le systeme");
				themeDTO.setTotal(1);
				themeDTO.setErrors(listErreur);
			} else {
				themeDTO.setResult(true);
				themeDTO.setStatus(true);
				themeDTO.setRow(theme);
				themeDTO.setRows(null);
				themeDTO.setMessage("theme trouve avec succes");
				themeDTO.setTotal(1);
				themeDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			themeDTO.setResult(false);
			themeDTO.setStatus(false);
			themeDTO.setRow(null);
			themeDTO.setRows(null);
			themeDTO.setMessage(ex.getMessage());
			themeDTO.setTotal(0);
			themeDTO.setErrors(listErreur);
		}
		return themeDTO;
	}

	@Override
	public ThemeDTO findThemes() {
		// TODO Auto-generated method stub
		
		ThemeDTO themeDTO = new ThemeDTO();
		List<Theme> listTheme;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		
		try{ 
			listTheme = themeRepository.findAll();
			if(listTheme == null){
				themeDTO.setResult(true);
				themeDTO.setStatus(true);
				themeDTO.setRow(null);
				themeDTO.setRows(new ArrayList<Theme>());
				themeDTO.setMessage("aucun theme trouve");
				themeDTO.setTotal(0);
				themeDTO.setErrors(listErreur);
			} else {
				int i = listTheme.size();
				sb = new StringBuilder();
				sb.append(i).append(" theme(s) trouve(s) avec succes");
				themeDTO.setResult(true);
				themeDTO.setStatus(true);
				themeDTO.setRow(null);
				themeDTO.setRows(listTheme);
				themeDTO.setMessage(sb.toString());
				themeDTO.setTotal(i);
				themeDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			themeDTO.setResult(false);
			themeDTO.setStatus(false);
			themeDTO.setRow(null);
			themeDTO.setRows(null);
			themeDTO.setMessage(ex.getMessage());
			themeDTO.setTotal(0);
			themeDTO.setErrors(listErreur);
		}
		return themeDTO;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) themeRepository.count();
	}

	@Override
	public ThemeDTO loadThemes(Pageable pageable) {
		// TODO Auto-generated method stub
		ThemeDTO themeDTO = new ThemeDTO();
		Page<Theme> page = themeRepository.findAll(pageable);
		themeDTO.setResult(true);
		themeDTO.setStatus(true);
		themeDTO.setRows(page.getContent());
		themeDTO.setTotal(page.getTotalElements());
		return themeDTO;
	}

	@Override
	public ThemeDTO loadThemes(Pageable pageable, String intitule, String description) {
		// TODO Auto-generated method stub
		ThemeDTO themeDTO = new ThemeDTO();
		Page<Theme> page = themeRepository.findByIntituleContainingOrDescriptionContaining(pageable, intitule, description);
		themeDTO.setResult(true);
		themeDTO.setStatus(true);
		themeDTO.setRows(page.getContent());
		themeDTO.setTotal(page.getTotalElements());
		return themeDTO;
	}

}
