package com.nectux.mizan.hyban.paie.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nectux.mizan.hyban.paie.dto.TempEffectifDTO;
import com.nectux.mizan.hyban.paie.entity.TempEffectif;
import com.nectux.mizan.hyban.paie.repository.TempEffectifRepository;
import com.nectux.mizan.hyban.paie.service.TempEffectifService;
import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.parametrages.repository.PeriodePaieRepository;
import com.nectux.mizan.hyban.personnel.repository.PersonnelRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service("tempeffectifService")
public class TempEffectifServiceImpl implements TempEffectifService {
	
	private static final Logger logger = LogManager.getLogger(TempEffectifServiceImpl.class);
	
	@Autowired private PeriodePaieRepository periodePaieRepository;
	@Autowired private PersonnelRepository personnelRepository;
	@Autowired private TempEffectifRepository tempEffectifRepository;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public TempEffectif save(TempEffectif tempEffectif) {
		// TODO Auto-generated method stub
		tempEffectif = tempEffectifRepository.save(tempEffectif);
	
		return tempEffectif;
	}

	@Override
	public List<TempEffectif> veriftempeffectif(Long idpers, Long periodId) {
		// TODO Auto-generated method stub
		TempEffectifDTO tempEffectifDTO = new TempEffectifDTO();
		List<TempEffectif> list = tempEffectifRepository.findByPersonnelIdAndPeriodePaieId(idpers, periodId);
		tempEffectifDTO.setRows(list);
		tempEffectifDTO.setTotal(list.size());
		logger.info(new StringBuilder().append(">>>>> UTILISATEURS CHARGES AVEC SUCCES").toString());
		return list;
	}

	@Override
	public Boolean delete(Long id) {
		// TODO Auto-generated method stub
		TempEffectif tempEffectif = tempEffectifRepository.findById(id)  .orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
		if(tempEffectif == null)
			return false;
		
	
		tempEffectifRepository.delete(tempEffectif);
		return true;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) tempEffectifRepository.count();
	}

	@Override
	public TempEffectifDTO loadTempEffectif(Pageable pageable) {
		// TODO Auto-generated method stub
		TempEffectifDTO tempEffectifDTO = new TempEffectifDTO();
		Page<TempEffectif> page = tempEffectifRepository.findAll(pageable);
		tempEffectifDTO.setRows(page.getContent());
		tempEffectifDTO.setTotal(page.getTotalElements());
		logger.info(new StringBuilder().append(">>>>> UTILISATEURS CHARGES AVEC SUCCES").toString());
		return tempEffectifDTO;
	}

	@Override
	public TempEffectifDTO loadTempEffectif(Pageable pageable, String search) {
		// TODO Auto-generated method stub
		TempEffectifDTO tempEffectifDTO = new TempEffectifDTO();
		Page<TempEffectif> page = tempEffectifRepository.findByPersonnelNomLike(pageable, "%"+search+"%");
		tempEffectifDTO.setRows(page.getContent());
		tempEffectifDTO.setTotal(page.getTotalElements());
		logger.info(new StringBuilder().append(">>>>> UTILISATEURS CHARGES AVEC SUCCES").toString());
		return tempEffectifDTO;
	}

	@Override
	public TempEffectifDTO saver(Double temptravail, Double jourtravail,Long idPers, Long idPeriodDep) {
		// TODO Auto-generated method stub
		TempEffectifDTO tempeffectifDTO = new TempEffectifDTO();
		TempEffectif tempeffectif = new TempEffectif();
		List<TempEffectif> listtempeffectif = new ArrayList<TempEffectif>();
		try{
			//if(id != null)
			listtempeffectif = tempEffectifRepository.findByPersonnelIdAndPeriodePaieId(idPers, idPeriodDep);
			if(listtempeffectif.size()>0)
				tempeffectif.setId(listtempeffectif.get(0).getId());
			if(temptravail == null  )
				tempeffectif.setHeurspresence(173.33d);
			else
				tempeffectif.setHeurspresence(temptravail);

			if(jourtravail == null  )
				tempeffectif.setJourspresence(26d);
			else
			tempeffectif.setJourspresence(jourtravail);


			tempeffectif.setDatedesaisie(new Date());
			tempeffectif.setPersonnel(personnelRepository.findById(idPers)  .orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + idPers)));
			tempeffectif.setPeriodePaie(periodePaieRepository.findById(idPeriodDep)  .orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + idPeriodDep)));
			tempeffectif = tempEffectifRepository.save(tempeffectif);
			tempeffectifDTO.setRow(tempeffectif);
			tempeffectifDTO.setResult("success");
		} catch(Exception ex){
			tempeffectifDTO.setResult("erreur");
		
			ex.printStackTrace();
		}
		return tempeffectifDTO;
	}

	@Override
	public TempEffectif findtempeffectif(Long idpret) {
	
		return tempEffectifRepository.findById(idpret)  .orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + idpret));
	}

	@Override
	public TempEffectif findtempsAgentPeriode(Long idpers, PeriodePaie period) {
		// TODO Auto-generated method stub
		return tempEffectifRepository.findByPersonnelIdAndPeriodePaie(idpers, period);
	}



	
}
