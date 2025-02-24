package com.nectux.mizan.hyban.parametrages.service;

import java.util.List;

import com.nectux.mizan.hyban.parametrages.entity.Banque;
import com.nectux.mizan.hyban.parametrages.entity.CpteVirmtBanque;
import com.nectux.mizan.hyban.parametrages.dto.CpteVirmtBanqueDTO;

import org.springframework.data.domain.Pageable;

public interface CpteVirmtBanqueService {
	
	public CpteVirmtBanqueDTO saver(Long id, String sigle, String libelle, Long codbanq, String numcptevirmt, String numguich,int ribcptevirmt);
	
	public CpteVirmtBanqueDTO delete(Long id);
	
	public CpteVirmtBanqueDTO findCpteVirmtBanque(Long id);
	
	public List<CpteVirmtBanque> getCpteVirmtBanques();
	
	public CpteVirmtBanqueDTO findCpteVirmtBanques();
	
	public CpteVirmtBanque findCpteVirmtBanquesId(Long id);
	
	public CpteVirmtBanque findCpteVirmtoFBanques(Banque banque);
	
	public int count();
	
	public CpteVirmtBanqueDTO loadCpteVirmtBanques(Pageable pageable);
	
	public CpteVirmtBanqueDTO loadCpteVirmtBanques(Pageable pageable, String search);

	public CpteVirmtBanqueDTO save(Long id, String sigle, String libelle,Long codbanq, String numcptevirmt);

}
