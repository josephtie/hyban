package com.nectux.mizan.hyban.parametrages.service.impl;

import java.util.List;

import com.nectux.mizan.hyban.parametrages.dto.ExerciceDTO;
import com.nectux.mizan.hyban.parametrages.entity.Exercice;
import com.nectux.mizan.hyban.parametrages.repository.ExerciceRepository;
import com.nectux.mizan.hyban.parametrages.service.ExerciceService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service("exerciceService")
public class ExerciceServiceImpl implements ExerciceService {
	
	private static final Logger logger = LogManager.getLogger(ExerciceServiceImpl.class);
	
	@Autowired private ExerciceRepository exerciceRepository;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Exercice save(Exercice exercice) {
		// TODO Auto-generated method stub
		exercice = exerciceRepository.save(exercice);
	
		return exercice;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ExerciceDTO save(Long id, String annee) {
		// TODO Auto-generated method stub
		ExerciceDTO exerciceDTO = new ExerciceDTO();
		try{
			Exercice exercice = new Exercice();
		
			if(id != null)
				exercice = exerciceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			exercice.setAnnee(annee);
			exercice=exerciceRepository.save(exercice);
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

	@Override
	public Boolean delete(Long id) {
		// TODO Auto-generated method stub
		Exercice exercice = exerciceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
		if(exercice == null)
			return false;
		
	
		exerciceRepository.delete(exercice);
		return true;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) exerciceRepository.count();
	}

	@Override
	public ExerciceDTO loadExercice(Pageable pageable) {
		// TODO Auto-generated method stub
		ExerciceDTO utilisateurDTO = new ExerciceDTO();
		Page<Exercice> page = exerciceRepository.findAll(pageable);
		utilisateurDTO.setRows(page.getContent());
		utilisateurDTO.setTotal(page.getTotalElements());
		logger.info(new StringBuilder().append(">>>>> UTILISATEURS CHARGES AVEC SUCCES").toString());
		return utilisateurDTO;
	}

	@Override
	public ExerciceDTO loadExercice(Pageable pageable, String search) {
		// TODO Auto-generated method stub
		/*ExerciceDTO utilisateurDTO = new ExerciceDTO();
		Page<Exercice> page = exerciceRepository.findByAnneeLike(pageable, search);
		utilisateurDTO.setRows(page.getContent());
		utilisateurDTO.setTotal(page.getTotalElements());
		logger.info(new StringBuilder().append(">>>>> UTILISATEURS CHARGES AVEC SUCCES").toString());*/
		return null;
	}

	@Override
	public Exercice findExoactif() {
		// TODO Auto-generated method stub
		return exerciceRepository.findByExoCloture();
	}

	@Override
	public Exercice findExo(Long id) {
		// TODO Auto-generated method stub
		return exerciceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
	}

	@Override
	public List<Exercice> findArecuperer() {
		// TODO Auto-generated method stub
		return exerciceRepository.findArecuperer();
	}

	
}
