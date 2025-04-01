package com.nectux.mizan.hyban.paie.service;

import com.nectux.mizan.hyban.paie.dto.PrimePersonnelDTO;
import com.nectux.mizan.hyban.paie.entity.PrimePersonnel;

import org.springframework.data.domain.Pageable;

public interface PrimePersonnelService {
	
	public PrimePersonnel save(PrimePersonnel pretPersonnel);
	
	public PrimePersonnel findprimepersonnel(Long idPret);
	public PrimePersonnelDTO saver(Long id, Double montant, Long valeur, Long idPret, Long idCtrat, Long idPeriodDep);
	
	
//	public PrimePersonnelDTO update(Long idPretpers,Double montant,Long echelonage,Long idPret,Long idPers,String dEmprunt,Long idPeriodDep);

	public java.util.List<PrimePersonnel> listdesprimepersonnel();
	public java.util.List<PrimePersonnel> listdesprimepersonnelPeriode(Long idPeriod);
	
	public java.util.List<PrimePersonnel> listdesprimepersonnelPeriodePrime(Long idPeriod,Long idPrime);
	public java.util.List<PrimePersonnel> listdesprimepersonnelPeriodePrime(Long idPeriod,Long idPrime,Long idContrat);
	
	public PrimePersonnelDTO listdesprimepersonnelMoisEnPrime(Pageable pageable,Long idPeriod, Long idPers);
	
	public java.util.List<PrimePersonnel> listPrimeContratpersonnelperiode(Long idPers,Long idPeriod,Long idPrime);
	
	public PrimePersonnelDTO delete(Long id);
	public int count();
	
	//public PrimePersonnelDTO PrimePersonneldupersonnel(Long Idpers);	
	public PrimePersonnelDTO loadPrimePersonnel(Pageable pageable);
	
	public PrimePersonnelDTO loadPrimePersonnel(Pageable pageable, String search);

}
