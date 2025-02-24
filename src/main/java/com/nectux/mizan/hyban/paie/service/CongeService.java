package com.nectux.mizan.hyban.paie.service;

import com.nectux.mizan.hyban.paie.dto.CongeDTO;
import com.nectux.mizan.hyban.paie.entity.Conge;
import com.nectux.mizan.hyban.parametrages.entity.Exercice;
import com.nectux.mizan.hyban.parametrages.entity.Mois;
import com.nectux.mizan.hyban.personnel.entity.ContratPersonnel;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;

public interface CongeService {
	
	public Conge save(Conge conge);
	
	public CongeDTO genererBulletinConge(Long id) throws Exception;
	
	public Boolean delete(Long id);
	
	public Conge findconge(Long id);
	
	public int count();
	
	public CongeDTO loadCongeProvisional(Pageable pageable) throws Exception;
	
	public CongeDTO loadCongeProvisional(Pageable pageable, String search);

	public List<Conge> loadLivrePaieConge();
	
	public int countnbreJrdu(Date dateRetourDernierConge,Date dateDepartConge,ContratPersonnel Contratp);
	public List<Conge> rechercherByAgenceMoisAnnee(Mois mois, Exercice annee);
	public List<Conge> loadLivrePaieCongePeriode(Long id);
}
