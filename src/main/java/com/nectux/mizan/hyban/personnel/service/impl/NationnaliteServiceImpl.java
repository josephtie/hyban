package com.nectux.mizan.hyban.personnel.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nectux.mizan.hyban.personnel.entity.Nationnalite;
import com.nectux.mizan.hyban.personnel.repository.NationnaliteRepository;
import com.nectux.mizan.hyban.personnel.service.NationnaliteService;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service("nationnaliteService")
public class NationnaliteServiceImpl implements NationnaliteService {
	
	@Autowired NationnaliteRepository nationnaliteRepository;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Nationnalite save(Nationnalite nationnalite) {
		// TODO Auto-generated method stub
		return nationnaliteRepository.save(nationnalite);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean delete(Long id) {
		// TODO Auto-generated method stub
		Nationnalite nationnalite = nationnaliteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
		if(nationnalite == null)
			return false;
		nationnaliteRepository.delete(nationnalite);
		return true;
	}

	@Override
	public Nationnalite findNationnalite(Long id) {
		// TODO Auto-generated method stub
		return nationnaliteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
	}

	@Override
	public Nationnalite findByLibelle(String libelle) {
		// TODO Auto-generated method stub
		return nationnaliteRepository.findByLibelle(libelle);
	}

	@Override
	public List<Nationnalite> findNationnalites() {
		// TODO Auto-generated method stub
		return nationnaliteRepository.findAll();
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) nationnaliteRepository.count();
	}

}
