package com.nectux.mizan.hyban.paie.repository;

import com.nectux.mizan.hyban.paie.dto.PrimePersonnelDTO;
import com.nectux.mizan.hyban.paie.entity.PrimePersonnel;
import com.nectux.mizan.hyban.personnel.entity.Personnel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PrimePersonnelRepository extends CrudRepository<PrimePersonnel, Long> {
	
	
	
	public List<PrimePersonnel> findAll();
	
	public List<PrimePersonnel> findByPeriodePaieId(Long IdPerPaie);
	
	public Page<PrimePersonnel> findByContratPersonnelPersonnelIdAndPeriodePaieId(Pageable pageable,Long IdPers,Long IdPerPaie);

	public Page<PrimePersonnel> findByContratPersonnelIdAndPeriodePaieId(Pageable pageable,Long IdPers,Long IdPerPaie);
	public List<PrimePersonnel> findByPrimeIdAndPeriodePaieId(Long IdPrimes,Long IdPerPaie);
	
	public List<PrimePersonnel> findByContratPersonnelPersonnelId(Personnel person);
	public List<PrimePersonnel> findByContratPersonnelPersonnelIdAndPeriodePaieIdAndPrimeId(Long IdPers,Long IdPerPaie,Long IdPrime);
	public List<PrimePersonnel> findByContratPersonnelPersonnelIdAndPeriodePaieId(Long IdPers,Long IdPerPaie);
	
	public PrimePersonnelDTO findByContratPersonnelPersonnelId(Long Idpers);
	
	public Page<PrimePersonnel> findAll(Pageable pageable);

    List<PrimePersonnel> findByContratPersonnelIdAndPeriodePaieIdAndPrimeId(Long idContrat, Long idPeriod, Long idPrime);

    List<PrimePersonnel> findByContratPersonnelIdAndPeriodePaieId(Long idContrat, Long idPeriod);

//	public Page<PrimePersonnel> findByPersonnelNomLike(Pageable pageable,String search);
	
	

}
