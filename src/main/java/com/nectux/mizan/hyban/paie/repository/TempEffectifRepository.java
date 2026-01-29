package com.nectux.mizan.hyban.paie.repository;

import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.personnel.entity.Personnel;
import com.nectux.mizan.hyban.paie.entity.TempEffectif;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TempEffectifRepository extends CrudRepository<TempEffectif, Long> {
	
	
	
	public java.util.List<TempEffectif> findAll();
	
	public Page<TempEffectif> findAll(Pageable pageable);
	
	public java.util.List<TempEffectif> findByPeriodePaieId(Long idptret);
	

	
	public java.util.List<TempEffectif> findByPersonnelIdAndPeriodePaieId(Long idpers,Long idperiode);
	
	public TempEffectif findByPersonnelIdAndPeriodePaie(Long idpers,PeriodePaie idperiode);
	
	public TempEffectif findByPersonnelAndPeriodePaie(Personnel idpers, PeriodePaie idperiode);
	
	public Page<TempEffectif> findByPersonnelNomLike(Pageable pageable,String search);


    Optional<TempEffectif> findFirstByPersonnelIdAndPeriodePaieId(Long idPers, Long idPeriodDep);
}
