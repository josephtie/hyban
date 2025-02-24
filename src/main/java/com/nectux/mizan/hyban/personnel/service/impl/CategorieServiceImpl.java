package com.nectux.mizan.hyban.personnel.service.impl;

import java.util.List;

import com.nectux.mizan.hyban.personnel.entity.Categorie;
import com.nectux.mizan.hyban.personnel.service.CategorieService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nectux.mizan.hyban.personnel.dto.CategorieDTO;
import com.nectux.mizan.hyban.personnel.repository.CategorieRepository;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service("categorieService")
public class CategorieServiceImpl implements CategorieService {
	
	private static final Logger logger = LogManager.getLogger(CategorieServiceImpl.class);
	
	@Autowired CategorieRepository categorieRepository;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Categorie save(Categorie categorie) {
		// TODO Auto-generated method stub
		return categorieRepository.save(categorie);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public CategorieDTO save(Long id, String libelle, Double salaireDeBase, Double indemniteLogement) {
		// TODO Auto-generated method stub
		CategorieDTO categorieDTO = new CategorieDTO();
		Categorie categorie = new Categorie();
		try{
			if(id != null)
				categorie = categorieRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			categorie.setLibelle(libelle);
			categorie.setSalaireDeBase(salaireDeBase);
			categorie.setIndemniteLogement(indemniteLogement);
			categorie = categorieRepository.save(categorie);
			categorieDTO.setRow(categorie);
			categorieDTO.setResult("succes");
		} catch(Exception ex){
			categorieDTO.setResult("erreur");
			if(libelle == null || salaireDeBase == null)
				categorieDTO.setResult("erreur_champ_obligatoire");
			ex.printStackTrace();
		}
		return categorieDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean delete(Long id) {
		// TODO Auto-generated method stub
		Categorie categorie = categorieRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
		if(categorie == null)
			return false;
		categorieRepository.delete(categorie);
		return true;
	}

	@Override
	public Categorie findCategorie(Long id) {
		// TODO Auto-generated method stub
		return categorieRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
	}

	@Override
	public Categorie findByLibelle(String libelle) {
		// TODO Auto-generated method stub
		return categorieRepository.findByLibelle(libelle);
	}

	@Override
	public List<Categorie> findCategories() {
		// TODO Auto-generated method stub
		return categorieRepository.findAll();
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) categorieRepository.count();
	}

	@Override
	public CategorieDTO loadCategorie(Pageable pageable) {
		// TODO Auto-generated method stub
		CategorieDTO categorieDTO = new CategorieDTO();
		Page<Categorie> page = categorieRepository.findAll(pageable);
		categorieDTO.setRows(page.getContent());
		categorieDTO.setTotal(page.getTotalElements());
		logger.info(new StringBuilder().append(">>>>> CATEGORIES CHARGES AVEC SUCCES").toString());
		return categorieDTO;
	}

	@Override
	public CategorieDTO loadCategorie(Pageable pageable, String search) {
		// TODO Auto-generated method stub
		CategorieDTO utilisateurDTO = new CategorieDTO();
		Page<Categorie> page = categorieRepository.findByLibelleLike(pageable, search);
		utilisateurDTO.setRows(page.getContent());
		utilisateurDTO.setTotal(page.getTotalElements());
		logger.info(new StringBuilder().append(">>>>> CATEGORIES CHARGES AVEC SUCCES").toString());
		return utilisateurDTO;
	}

}
