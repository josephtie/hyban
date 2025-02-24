package com.nectux.mizan.hyban.parametrages.service;

import java.util.List;

import com.nectux.mizan.hyban.parametrages.dto.BanqueDTO;
import com.nectux.mizan.hyban.parametrages.entity.Banque;

import org.springframework.data.domain.Pageable;

public interface BanqueService {
	
	public BanqueDTO save(Long id, String sigle, String libelle, String codbanq,Boolean statut);
	
	public BanqueDTO delete(Long id);
	
	public BanqueDTO findBanque(Long id);
	
	public List<Banque> getBanques();
	
	
	public List<Banque> getBanquesEntprise();
	
	public BanqueDTO findBanques();
	
	public Banque findBanquesID(Long id);
	
	public int count();
	
	public BanqueDTO loadBanques(Pageable pageable);
	
	public BanqueDTO loadBanques(Pageable pageable, String search);

}
