package com.nectux.mizan.hyban.paie.repository;

import com.nectux.mizan.hyban.paie.entity.RubriqVariable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface RubriqVariableRepository extends CrudRepository<RubriqVariable, Long> {
	
	
	
	public java.util.List<RubriqVariable> findAll();
	
	public Page<RubriqVariable> findAll(Pageable pageable);
	
//	public java.util.List<RubriqVariable> findByPeriodePaieId(Long idptret);
	

	
//	public java.util.List<RubriqVariable> findByPersonnelIdAndPeriodePaieId(Long idpers,Long idperiode);
	
	public RubriqVariable findByPersonnelId(Long idpers);
	
//	public RubriqVariable findByPersonnelAndPeriodePaie(Personnel idpers,PeriodePaie idperiode);
	
	public Page<RubriqVariable> findByPersonnelNomLike(Pageable pageable,String search);
	
	

}
