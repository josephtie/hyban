package com.nectux.mizan.hyban.paie.service;

import com.nectux.mizan.hyban.paie.dto.PretPersonnelDTO;
import com.nectux.mizan.hyban.paie.entity.PretPersonnel;

import org.springframework.data.domain.Pageable;

public interface PretPersonnelService {
	
	public PretPersonnel save(PretPersonnel pretPersonnel);
	
	public PretPersonnel findpret(Long idPret);
	public PretPersonnelDTO saver(Double montant,Long echelonage,Long idPret,Long idPers,String dEmprunt,Long idPeriodDep);
	
	
	public PretPersonnelDTO update(Long idPretpers,Double montant,Long echelonage,Long idPret,Long idPers,String dEmprunt,Long idPeriodDep);

	public java.util.List<PretPersonnel> listdespretpersonnel();
	public Boolean delete(Long id);
	public int count();
	public PretPersonnelDTO PretPersonneldupersonnel(Long Idpers);
	public PretPersonnelDTO loadPretPersonnel(Pageable pageable);
	public PretPersonnelDTO loadPretPersonnel(Pageable pageable, String search);

}
