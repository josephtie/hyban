package com.nectux.mizan.hyban.paie.service.impl;

import java.util.List;

import com.nectux.mizan.hyban.paie.entity.Pret;
import com.nectux.mizan.hyban.paie.repository.PretRepository;
import com.nectux.mizan.hyban.paie.service.PretService;
import com.nectux.mizan.hyban.paie.dto.PretDTO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service("pretService")
public class PretServiceImpl implements PretService {
	
	private static final Logger logger = LogManager.getLogger(PretServiceImpl.class);
	
	//@Autowired private PretRepository pretRepository;
	@Autowired private PretRepository pretRepository;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Pret save(Pret pret) {
		// TODO Auto-generated method stub
		pret = pretRepository.save(pret);
	
		return pret;
	}

/*	@Override
	@Transactional(rollbackFor = Exception.class)
	public PretDTO savedto(Long id, String annee) {
		// TODO Auto-generated method stub
		PretDTO exerciceDTO = new PretDTO();
		try{
			Pret exercice = new Pret();
		
			if(id != null)
				exercice = pretRepository.findOne(id);
			exercice.setAnnee(annee);
			exercice=pretRepository.save(exercice);
			exerciceDTO.setRow(exercice);;
			exerciceDTO.setResult("success");
			logger.info(new StringBuilder().append(">>>>> ").append(exercice.toString()).append(" ENREGISTRE AVEC SUCCES").toString());
		} catch(Exception ex){
			exerciceDTO.setResult("failed");
			logger.error(ex.getMessage());
			logger.error(new StringBuilder().append(">>>>>  ERREUR SUR ENREGISTREMENT UTILISATEUR [NOM : ").append(annee).toString());
			ex.getStackTrace();
		}
		return exerciceDTO;
	}
*/
	@Override
	public Boolean delete(Long id) {
		// TODO Auto-generated method stub
		Pret pret = pretRepository.findById(id) .orElseThrow(() -> new EntityNotFoundException("PeriodePaie not found for id " + id));
		if(pret == null)
			return false;
		
	
		pretRepository.delete(pret);
		return true;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) pretRepository.count();
	}

	@Override
	public PretDTO loadPret(Pageable pageable) {
		// TODO Auto-generated method stub
		PretDTO pretDTO = new PretDTO();
		Page<Pret> page = pretRepository.findAll(pageable);
		pretDTO.setRows(page.getContent());
		pretDTO.setTotal(page.getTotalElements());
		logger.info(new StringBuilder().append(">>>>> UTILISATEURS CHARGES AVEC SUCCES").toString());
		return pretDTO;
	}

	@Override
	public PretDTO loadPret(Pageable pageable, String search) {
		// TODO Auto-generated method stub
		/*PretDTO utilisateurDTO = new PretDTO();
		Page<Pret> page = pretRepository.findByAnneeLike(pageable, search);
		utilisateurDTO.setRows(page.getContent());
		utilisateurDTO.setTotal(page.getTotalElements());
		logger.info(new StringBuilder().append(">>>>> UTILISATEURS CHARGES AVEC SUCCES").toString());*/
		return null;
	}

	@Override
	public List<Pret> listdesPret() {
		// TODO Auto-generated method stub
		return pretRepository.findAll();
	}

	
}
