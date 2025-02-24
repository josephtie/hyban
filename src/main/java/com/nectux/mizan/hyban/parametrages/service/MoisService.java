package com.nectux.mizan.hyban.parametrages.service;

import com.nectux.mizan.hyban.parametrages.dto.MoisDTO;
import com.nectux.mizan.hyban.parametrages.entity.Mois;

import org.springframework.data.domain.Pageable;

public interface MoisService {
	
	public Mois save(Mois mois);
	
	public Mois findbymois(String mois);
	
	public java.util.List<Mois> findtsmois();
	
	public MoisDTO save(Long id, String name);
	
	public Boolean delete(Long id);
	
	public int count();
	
	//public Mois authentification(String email, String password);
	
	public MoisDTO loadMois(Pageable pageable);
	
	public MoisDTO loadMois(Pageable pageable, String search);

}
