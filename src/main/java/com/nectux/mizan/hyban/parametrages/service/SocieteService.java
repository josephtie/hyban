package com.nectux.mizan.hyban.parametrages.service;


import com.nectux.mizan.hyban.parametrages.entity.Societe;
import com.nectux.mizan.hyban.parametrages.dto.SocieteDTO;

import org.springframework.data.domain.Pageable;

public interface SocieteService {
	
	public Societe save(Societe mois);
	
	public Societe findbyRaisoncoc(String mois);
	
	public java.util.List<Societe> findtsmois();
	
	public SocieteDTO save(Long id,String raisonsoc,String sigle,String activitepp,String adress,String formjuri,String telephone,String bp,String commune, 
			String quartier,String rue,String lot,String sectpartiell,String centreImpot,String codeEts,String codeActivite, String 
			codeEmployeur,String cpteContrib,String nomcomptable,String telcomptable,String adrcomptable,Double txprest,Double txacctr,Double txretrait, Double txgratif,Long gratification);
	
	public Boolean delete(Long id);
	
	public int count();
	
	//public Societe authentification(String email, String password);
	
	public SocieteDTO loadSociete(Pageable pageable);
	
	public SocieteDTO loadSociete(Pageable pageable, String search);

}
