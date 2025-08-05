package com.nectux.mizan.hyban.personnel.service.impl;

import java.util.List;
import java.util.Map;

import com.nectux.mizan.hyban.personnel.dto.ContratPersonnelDTO;
import com.nectux.mizan.hyban.personnel.dto.FonctionDTO;
import com.nectux.mizan.hyban.personnel.entity.ContratPersonnel;
import com.nectux.mizan.hyban.personnel.entity.Fonction;
import com.nectux.mizan.hyban.personnel.repository.FonctionRepository;
import com.nectux.mizan.hyban.personnel.service.FonctionService;
import com.nectux.mizan.hyban.utils.GenericSpecifications;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service("fonctionService")
public class FonctionServiceImpl implements FonctionService {
	
	private static final Logger logger = LogManager.getLogger(FonctionServiceImpl.class);
	
	@Autowired
    FonctionRepository fonctionRepository;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Fonction save(Fonction fonction) {
		// TODO Auto-generated method stub
		return fonctionRepository.save(fonction);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public FonctionDTO save(Long id, String libelle) {
		// TODO Auto-generated method stub
		FonctionDTO fonctionDTO = new FonctionDTO();
		Fonction fonction = new Fonction();
		try{
			if(id != null)
				fonction = fonctionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			fonction.setLibelle(libelle);
			fonction = fonctionRepository.save(fonction);
			fonctionDTO.setRow(fonction);
			fonctionDTO.setResult("succes");
		} catch(Exception ex){
			fonctionDTO.setResult("erreur");
			if(libelle == null)
				fonctionDTO.setResult("erreur_champ_obligatoire");
			ex.printStackTrace();
		}
		return fonctionDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean delete(Long id) {
		// TODO Auto-generated method stub
		Fonction fonction = fonctionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
		if(fonction == null)
			return false;
		fonctionRepository.delete(fonction);
		return true;
	}

	@Override
	public Fonction findFonction(Long id) {
		// TODO Auto-generated method stub
		return fonctionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
	}

	@Override
	public Fonction findByLibelle(String libelle) {
		// TODO Auto-generated method stub
		return fonctionRepository.findByLibelle(libelle);
	}

	@Override
	public List<Fonction> findFonctions() {
		// TODO Auto-generated method stub
		return fonctionRepository.findAll();
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) fonctionRepository.count();
	}

	@Override
	public FonctionDTO findAllfilter(Map<String, String> filters, Pageable pageable) {
		FonctionDTO fonctionDTO = new FonctionDTO();
		Specification<Fonction> specification = GenericSpecifications.fromMap(filters);
		Page<Fonction> page = fonctionRepository.findAll(specification, pageable);
		fonctionDTO.setResult(true);
		fonctionDTO.setStatus(true);
		fonctionDTO.setRows(page.getContent());
		fonctionDTO.setTotal(page.getTotalElements());
		return fonctionDTO;
	}

	@Override
	public FonctionDTO loadFonction(Pageable pageable) {
		// TODO Auto-generated method stub
		FonctionDTO categorieDTO = new FonctionDTO();
		Page<Fonction> page = fonctionRepository.findAll(pageable);
		categorieDTO.setRows(page.getContent());
		categorieDTO.setTotal(page.getTotalElements());
		logger.info(new StringBuilder().append(">>>>> CATEGORIES CHARGES AVEC SUCCES").toString());
		return categorieDTO;
	}

	@Override
	public FonctionDTO loadFonction(Pageable pageable, String search) {
		// TODO Auto-generated method stub
		FonctionDTO utilisateurDTO = new FonctionDTO();
		Page<Fonction> page = fonctionRepository.findByLibelleIgnoreCaseContaining(pageable, search);
		utilisateurDTO.setRows(page.getContent());
		utilisateurDTO.setTotal(page.getTotalElements());
		logger.info(new StringBuilder().append(">>>>> CATEGORIES CHARGES AVEC SUCCES").toString());
		return utilisateurDTO;
	}

}
