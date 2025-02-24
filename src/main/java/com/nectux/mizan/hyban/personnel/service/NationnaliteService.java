package com.nectux.mizan.hyban.personnel.service;

import com.nectux.mizan.hyban.personnel.entity.Nationnalite;


public interface NationnaliteService {
	
	public Nationnalite save(Nationnalite nationnalite);
	
	public Boolean delete(Long id);
	
	public Nationnalite findNationnalite(Long id);
	
	public Nationnalite findByLibelle(String libelle);
	
	public java.util.List<Nationnalite> findNationnalites();
	
	public int count();

}
