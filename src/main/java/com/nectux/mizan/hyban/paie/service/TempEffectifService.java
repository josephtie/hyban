package com.nectux.mizan.hyban.paie.service;

import com.nectux.mizan.hyban.paie.dto.TempEffectifDTO;
import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.paie.entity.TempEffectif;

import org.springframework.data.domain.Pageable;

public interface TempEffectifService {
	
	public TempEffectif save(TempEffectif tempEffectif);
	
	public TempEffectifDTO saver(Double temptravail, Double jourtravail, Long idPers, Long idPeriodDep);
	
	
	//public TempEffectif update(Long  id,Long periodId);
	
	public  java.util.List<TempEffectif> veriftempeffectif(Long  idpers,Long periodId);
	
	public  TempEffectif findtempsAgentPeriode(Long  idpers,PeriodePaie period);
	
	public TempEffectif findtempeffectif(Long idpretpersonel);
	
	public Boolean delete(Long id);
	
	public int count();
	
	public TempEffectifDTO loadTempEffectif(Pageable pageable);
	
	public TempEffectifDTO loadTempEffectif(Pageable pageable, String search);

    TempEffectifDTO saverEmp(Double temptravail, Double jourtravail, String idPers, Long idPeriodDep);
}
