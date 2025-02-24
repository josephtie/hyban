package com.nectux.mizan.hyban.parametrages.service;

import java.util.Date;
import java.util.List;

import com.nectux.mizan.hyban.parametrages.entity.Mois;
import org.springframework.data.domain.Pageable;

import com.nectux.mizan.hyban.parametrages.dto.PlanningCongeDTO;
import com.nectux.mizan.hyban.parametrages.entity.Exercice;
import com.nectux.mizan.hyban.parametrages.entity.PlanningConge;
import com.nectux.mizan.hyban.personnel.entity.ContratPersonnel;

public interface PlanningCongeService {
	
	
	public PlanningConge save(PlanningConge planningConge);

	public PlanningCongeDTO save(Long id, Date stringToDate);
	
	public PlanningCongeDTO save(Long id, Date dateDepart, ContratPersonnel contratPersonnel, Exercice exercice);
	
	public Boolean delete(Long id);
	
	public PlanningConge findPlanningConge(Long id);
	
	public List<PlanningConge> findPlanningConges();
	
	public PlanningCongeDTO loadPlanningConge(Pageable pageable);
	
	public PlanningCongeDTO loadPlanningConge(Pageable pageable, String search);
	
	public void generate(List<PlanningConge> planningCongeList, Exercice exercice) throws Exception;
	public List<PlanningConge> rechercherByAgenceMoisAnnee( Mois mois, Exercice annee);
}
