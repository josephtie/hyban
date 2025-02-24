package com.nectux.mizan.hyban.parametrages.service;

import com.nectux.mizan.hyban.parametrages.dto.PeriodePaieDTO;
import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;

import java.util.List;

import org.springframework.data.domain.Pageable;

public interface PeriodePaieService {
	
	public PeriodePaie save(PeriodePaie periodePaie);
	
	public PeriodePaieDTO save(Long id, String datedeb, String datefin,Long mois,Long annee);
	
	public PeriodePaieDTO genererPeriode(Long mois,String annee);
	
	public Boolean delete(Long id);
	
	public PeriodePaie findPeriodePaie(Long id) ;
	
	
	public PeriodePaie findPeriodePaiebydate(String date) ;
	
	public List<PeriodePaie> findByAnneeparAnnee(String date);
	/*public PeriodePaie findPeriodePaieFirst() ;
	
	
	
	public PeriodePaie findPeriodePaieLast() ;*/
	
	public int count();
	
	public List<PeriodePaie> listperiodesupAuPret();
	
	public List<PeriodePaie> listAllperiode();
	
	public PeriodePaie findPeriodeactive();
	
	public PeriodePaieDTO loadPeriodePaie(Pageable pageable);
	
	public PeriodePaieDTO loadPeriodePaie(Pageable pageable, String search);
	
	public List<PeriodePaie> findPeriodeCloture();
	
	
	public List<PeriodePaie> findPeriodedAnnee(String annee);

	List<PeriodePaie> findPeriodeOuverte();
	
	

}
