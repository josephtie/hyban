package com.nectux.mizan.hyban.paie.service.impl;

import java.util.Date;

import com.nectux.mizan.hyban.paie.repository.RubriqVariableRepository;
import com.nectux.mizan.hyban.paie.dto.RubriqVariableDTO;
import com.nectux.mizan.hyban.paie.entity.RubriqVariable;
import com.nectux.mizan.hyban.paie.service.RubriqVariableService;
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
@Service("rubriqvariableService")
public class RubriqVariableServiceImpl implements RubriqVariableService {
	
private static final Logger logger = LogManager.getLogger(RubriqVariableServiceImpl.class);
	
	@Autowired private PeriodePaieRepository periodePaieRepository;
	@Autowired private PersonnelRepository personnelRepository;
	@Autowired private RubriqVariableRepository rubriqVariableRepository;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public RubriqVariable save(RubriqVariable tempEffectif) {
		// TODO Auto-generated method stub
		tempEffectif = rubriqVariableRepository.save(tempEffectif);
	
		return tempEffectif;
	}

	/*@Override
	public List<RubriqVariable> verifrubriqVariable(Long idpers) {
		// TODO Auto-generated method stub
		RubriqVariableDTO tempEffectifDTO = new RubriqVariableDTO();
		List<RubriqVariable> list = rubriqVariableRepository.findByPersonnelId(idpers);
		tempEffectifDTO.setRows(list);
		tempEffectifDTO.setTotal(list.size());
		logger.info(new StringBuilder().append(">>>>> UTILISATEURS CHARGES AVEC SUCCES").toString());
		return list;
	}*/

	@Override
	public Boolean delete(Long id) {
		// TODO Auto-generated method stub
		RubriqVariable tempEffectif = rubriqVariableRepository.findById(id)  .orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
		if(tempEffectif == null)
			return false;
		
	
		rubriqVariableRepository.delete(tempEffectif);
		return true;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) rubriqVariableRepository.count();
	}

	@Override
	public RubriqVariableDTO loadRubriqVariable(Pageable pageable) {
		// TODO Auto-generated method stub
		RubriqVariableDTO tempEffectifDTO = new RubriqVariableDTO();
		Page<RubriqVariable> page = rubriqVariableRepository.findAll(pageable);
		tempEffectifDTO.setRows(page.getContent());
		tempEffectifDTO.setTotal(page.getTotalElements());
		logger.info(new StringBuilder().append(">>>>> UTILISATEURS CHARGES AVEC SUCCES").toString());
		return tempEffectifDTO;
	}

	@Override
	public RubriqVariableDTO loadRubriqVariable(Pageable pageable, String search) {
		// TODO Auto-generated method stub
		RubriqVariableDTO tempEffectifDTO = new RubriqVariableDTO();
		Page<RubriqVariable> page = rubriqVariableRepository.findByPersonnelNomLike(pageable, "%"+search+"%");
		tempEffectifDTO.setRows(page.getContent());
		tempEffectifDTO.setTotal(page.getTotalElements());
		logger.info(new StringBuilder().append(">>>>> UTILISATEURS CHARGES AVEC SUCCES").toString());
		return tempEffectifDTO;
	}

	@Override
	public RubriqVariableDTO saver(Double cn,Double igr, Double amao,	Double synaoni,	Double mugefci, Double ivoireSante,Double ivoirePrev, Double diversgainsImp,Double diversgains,Double regularisation,Long idpers) {
		// TODO Auto-generated method stub
		RubriqVariableDTO tempeffectifDTO = new RubriqVariableDTO();
		RubriqVariable tempeffectif = new RubriqVariable();
		RubriqVariable listtempeffectif = new RubriqVariable();
		try{
			//if(id != null)
			listtempeffectif = rubriqVariableRepository.findByPersonnelId(idpers);
			if(listtempeffectif!=null)
				tempeffectif.setId(listtempeffectif.getId());
			
			if(synaoni==null || synaoni.equals(""))
			 tempeffectif.setSynaoni((double) 0);
			else
				tempeffectif.setSynaoni(synaoni);
			
			
			if(amao==null || amao.equals(""))
				 tempeffectif.setAmao((double) 0);
				else
					tempeffectif.setAmao(amao);
			
			if(ivoireSante==null || ivoireSante.equals(""))
			tempeffectif.setIvoireSante((double) 0);
			else
				tempeffectif.setIvoireSante(ivoireSante);
			
			if(cn==null || cn.equals(""))
			tempeffectif.setCn((double) 0);
			else
				tempeffectif.setCn(cn);
			
			if(igr==null || igr.equals(""))
			tempeffectif.setIgr((double) 0);
			else
				tempeffectif.setIgr(igr);
			
			if(ivoirePrev==null || ivoirePrev.equals(""))
			tempeffectif.setIvoirePrev((double) 0);
			else
				tempeffectif.setIvoirePrev(ivoirePrev);
			
			if(diversgains==null || diversgains.equals(""))
			tempeffectif.setDiversgains((double) 0);
			else
			tempeffectif.setDiversgains(diversgains);
			
			if(diversgainsImp==null || diversgainsImp.equals(""))
				tempeffectif.setDiversgainsImp((double) 0);
				else
				tempeffectif.setDiversgainsImp(diversgainsImp);
			
			if(mugefci==null || mugefci.equals(""))
				tempeffectif.setMugefci((double) 0);
				else
				tempeffectif.setMugefci(mugefci);
			
			if(regularisation==null || regularisation.equals(""))
				tempeffectif.setRegularisation((double) 0);
				else
				tempeffectif.setRegularisation(regularisation);
			
			tempeffectif.setDatedesaisie(new Date());
			tempeffectif.setPersonnel(personnelRepository.findById(idpers).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + idpers)));
			//tempeffectif.setPeriodePaie(periodePaieRepository.findOne(idperiodePaie));
			tempeffectif = rubriqVariableRepository.save(tempeffectif);
			tempeffectifDTO.setRow(tempeffectif);
			tempeffectifDTO.setResult("success");
		} catch(Exception ex){
			tempeffectifDTO.setResult("erreur");
		
			ex.printStackTrace();
		}
		return tempeffectifDTO;
	}

	@Override
	public RubriqVariable findrubriqVariable(Long idpret) {
	
		return rubriqVariableRepository.findById(idpret)  .orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + idpret));
	}

	/*@Override
	public RubriqVariable findrubriqVariableAgentPeriode(Long idpers, PeriodePaie period) {
		// TODO Auto-generated method stub
		return rubriqVariableRepository.findByPersonnelIdAndPeriodePaie(idpers, period);
	}*/



}
