package com.nectux.mizan.hyban.parametrages.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nectux.mizan.hyban.parametrages.entity.Mois;
import com.nectux.mizan.hyban.parametrages.repository.PlanningCongeRepository;
import com.nectux.mizan.hyban.parametrages.service.PlanningCongeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nectux.mizan.hyban.parametrages.dto.PlanningCongeDTO;
import com.nectux.mizan.hyban.parametrages.entity.Exercice;
import com.nectux.mizan.hyban.parametrages.entity.PlanningConge;
import com.nectux.mizan.hyban.personnel.entity.ContratPersonnel;
import com.nectux.mizan.hyban.personnel.service.impl.PersonnelServiceImpl;
import com.nectux.mizan.hyban.utils.DateManager;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service("planningCongeService")
public class PlanningCongeServiceImpl implements PlanningCongeService {
	
	private static final Logger logger = LogManager.getLogger(PersonnelServiceImpl.class);
	
	@Autowired private PlanningCongeRepository planningCongeRepository;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public PlanningConge save(PlanningConge planningConge) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public PlanningCongeDTO save(Long id, Date dateDepart) {
		// TODO Auto-generated method stub
		PlanningCongeDTO planningCongeDTO = new PlanningCongeDTO();
		try{
			PlanningConge planningConge = planningCongeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			planningConge.setDateDepart(dateDepart);
			planningConge = planningCongeRepository.save(planningConge);
			planningCongeDTO.setRow(planningConge);
			planningCongeDTO.setResult("success");
			logger.info(new StringBuilder().append(">>>>> ").append(planningConge.toString()).append(" ENREGISTRE AVEC SUCCES").toString());
		} catch(Exception ex){
			planningCongeDTO.setResult("failed");
			logger.error(ex.getMessage());
			logger.error(new StringBuilder().append(">>>>>  ERREUR SUR ENREGISTREMENT PLANNING CONGE"));
			ex.getStackTrace();
		}
		return planningCongeDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public PlanningCongeDTO save(Long id, Date dateDepart, ContratPersonnel contratPersonnel, Exercice exercice) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean delete(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlanningConge findPlanningConge(Long id) {
		// TODO Auto-generated method stub
		return planningCongeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
	}

	@Override
	public List<PlanningConge> findPlanningConges() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlanningCongeDTO loadPlanningConge(Pageable pageable) {
		// TODO Auto-generated method stub
		PlanningCongeDTO planningCongeDTO = new PlanningCongeDTO();
		Page<PlanningConge> pageImpianto=null;
		List<PlanningConge> mylist=new ArrayList<PlanningConge>();
		List<PlanningConge> mylist1=new ArrayList<PlanningConge>();
		mylist=planningCongeRepository.findByStatutTrueOrStatutFalseOrderByContratPersonnelPersonnelNomAsc();
		for(PlanningConge planningpersonnel : mylist){
			if( planningpersonnel.getContratPersonnel().getPersonnel().getRetraitEffect()==false)
				mylist1.add(planningpersonnel);
			else{}
		}
		int start = (int) pageable.getOffset();
		int end = (start + (int) pageable.getPageSize()) > mylist1.size() ? mylist1.size() : (start + pageable.getPageSize());
		pageImpianto=new PageImpl<PlanningConge>(mylist1.subList(start, end), pageable,mylist1.size());
		//Page<PlanningConge> page = planningCongeRepository.findByStatutTrueOrStatutFalseAndContratPersonnelStatutTrueAndContratPersonnelDepartFalseAndContratPersonnelPersonnelRetraitEffectFalseOrderByContratPersonnelPersonnelNomAsc(pageable);
		planningCongeDTO.setRows(pageImpianto.getContent());
		planningCongeDTO.setTotal(pageImpianto.getTotalElements());
		logger.info(new StringBuilder().append(">>>>> PLANNINGS CONGES CHARGES AVEC SUCCES").toString());
		return planningCongeDTO;
	}

	@Override
	public PlanningCongeDTO loadPlanningConge(Pageable pageable, String search) {
		// TODO Auto-generated method stub
		PlanningCongeDTO planningCongeDTO = new PlanningCongeDTO();
		Page<PlanningConge> pageImpianto=null;
		List<PlanningConge> mylist=new ArrayList<PlanningConge>();
		List<PlanningConge> mylist1=new ArrayList<PlanningConge>();
		mylist=planningCongeRepository.findByStatutTrueOrStatutFalseAndContratPersonnelDepartFalseAndContratPersonnelPersonnelNomIgnoreCaseContaining(search);
		for(PlanningConge planningpersonnel : mylist){
			if( planningpersonnel.getContratPersonnel().getPersonnel().getRetraitEffect()==false)
				mylist1.add(planningpersonnel);
			else{}
		}
		int start = (int)pageable.getOffset();
		int end = (start + (int)pageable.getPageSize()) > mylist1.size() ? mylist1.size() : (start + pageable.getPageSize());
		pageImpianto=new PageImpl<PlanningConge>(mylist.subList(start, end), pageable,mylist1.size());
		//Page<PlanningConge> page = planningCongeRepository.findByStatutTrueOrStatutFalseAndContratPersonnelStatutTrueAndContratPersonnelDepartFalseAndContratPersonnelPersonnelRetraitEffectFalseOrderByContratPersonnelPersonnelNomAsc(pageable);
		planningCongeDTO.setRows(pageImpianto.getContent());
		planningCongeDTO.setTotal(pageImpianto.getTotalElements());
		logger.info(new StringBuilder().append(">>>>> PLANNINGS CONGES CHARGES AVEC SUCCES").toString());
		return planningCongeDTO;
//		PlanningCongeDTO planningCongeDTO = new PlanningCongeDTO();
//		Page<PlanningConge> pageImpianto=null;
//		List<PlanningConge> mylist=new ArrayList<PlanningConge>();
//		List<PlanningConge> mylist1=new ArrayList<PlanningConge>();
//
//		Page<PlanningConge> page = planningCongeRepository.findByStatutTrueOrStatutFalseAndContratPersonnelStatutTrueAndContratPersonnelDepartFalseAndContratPersonnelPersonnelRetraitEffectFalseAndContratPersonnelPersonnelNomIgnoreCaseContainingOrderByContratPersonnelPersonnelNomAsc(pageable,search);
//		planningCongeDTO.setRows(page.getContent());
//		planningCongeDTO.setTotal(page.getTotalElements());
//		logger.info(new StringBuilder().append(">>>>> PLANNINGS CONGES CHARGES AVEC SUCCES").toString());
//		return planningCongeDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void generate(List<PlanningConge> planningCongeList, Exercice exercice) throws Exception {
		// TODO Auto-generated method stub
		List<PlanningConge> newPlanningCongeList = new ArrayList<PlanningConge>();
		for(PlanningConge planningConge : planningCongeList){
			planningConge.setDateDepart(DateManager.addingMonth(planningConge.getDateDepart(), 12));
		//	planningConge.setExercice(exercice);
			newPlanningCongeList.add(planningConge);
		}
		if(!newPlanningCongeList.isEmpty())
			planningCongeRepository.saveAll(newPlanningCongeList);
	}
	@Override
	public List<PlanningConge> rechercherByAgenceMoisAnnee(Mois mois, Exercice annee) {
		List<PlanningConge> listPlanning = new ArrayList<PlanningConge>();
		try{
			listPlanning = planningCongeRepository.rechercherParAgenceAnneeMois( mois.getId(), annee.getId());
			logger.info(listPlanning.size() + " planning(s) a / ont ete liste(s) avec succes");
		} catch(Exception ex){
			ex.getMessage();
			ex.getStackTrace();
			logger.error("une erreur a ete detectee lors de la recherche des planning");
		}
		return listPlanning;
	}
}
