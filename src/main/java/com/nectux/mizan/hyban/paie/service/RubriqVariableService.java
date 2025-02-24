package com.nectux.mizan.hyban.paie.service;

import com.nectux.mizan.hyban.paie.dto.RubriqVariableDTO;
import com.nectux.mizan.hyban.paie.entity.RubriqVariable;

import org.springframework.data.domain.Pageable;

public interface RubriqVariableService {
	
	public RubriqVariable save(RubriqVariable rubriqVariable);
	
	public RubriqVariableDTO saver(Double cn, Double igr, Double amao, Double synaoni, Double mugefci, Double ivoireSante, Double ivoirePrev, Double diversgainsImp, Double diversgains, Double regularisation, Long idpers);
	
	
	//public TempEffectif update(Long  id,Long periodId);
	
/*	public  java.util.List<RubriqVariable> verifrubriqVariable(Long  idpers);*/
	
	//public  RubriqVariable findrubriqVariableAgentPeriode(Long  idpers,PeriodePaie period);
	
	public RubriqVariable findrubriqVariable(Long idpretpersonel);
	
	public Boolean delete(Long id);
	
	public int count();
	
	public RubriqVariableDTO loadRubriqVariable(Pageable pageable);
	
	public RubriqVariableDTO loadRubriqVariable(Pageable pageable, String search);

}
