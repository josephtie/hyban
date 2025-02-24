package com.nectux.mizan.hyban.paie.service;

import com.nectux.mizan.hyban.paie.dto.EchelonnementDTO;
import com.nectux.mizan.hyban.paie.entity.Echelonnement;

import org.springframework.data.domain.Pageable;

public interface EchelonnementService {
	
	public Echelonnement save(Echelonnement echelonnement);
	
	public Echelonnement update(Long  id,Long periodId);
	
	public  java.util.List<Echelonnement> reglerModalite(Long  idpers,Long periodId);
	
	public  java.util.List<Echelonnement> findModalitedepret(Long  idpret);
	
	public EchelonnementDTO findechelondupret(Long idpretpersonel);
	
	public Boolean delete(Long id);
	
	public int count();
	
	public EchelonnementDTO loadEchelonnement(Pageable pageable);
	
	public EchelonnementDTO loadEchelonnement(Pageable pageable, String search);

}
