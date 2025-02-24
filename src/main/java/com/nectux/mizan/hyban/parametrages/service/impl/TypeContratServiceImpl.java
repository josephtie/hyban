package com.nectux.mizan.hyban.parametrages.service.impl;

import java.util.List;

import com.nectux.mizan.hyban.parametrages.entity.TypeContrat;
import com.nectux.mizan.hyban.parametrages.repository.TypeContratRepository;
import com.nectux.mizan.hyban.parametrages.service.TypeContratService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service("typeContratService")
public class TypeContratServiceImpl implements TypeContratService {

	@Autowired
    TypeContratRepository typeContratRepository;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public TypeContrat save(TypeContrat typeContrat) {
		// TODO Auto-generated method stub
		return typeContratRepository.save(typeContrat);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean delete(Long id) {
		// TODO Auto-generated method stub
		TypeContrat typeContrat = typeContratRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
		if(typeContrat == null)
			return false;
		typeContratRepository.delete(typeContrat);
		return true;
	}

	@Override
	public TypeContrat findTypeContrat(Long id) {
		// TODO Auto-generated method stub
		return typeContratRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
	}

	@Override
	public List<TypeContrat> findTypeContrats() {
		// TODO Auto-generated method stub
		return typeContratRepository.findAll();
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) typeContratRepository.count();
	}

	@Override
	public TypeContrat findByLibelle(String libelle) {
		// TODO Auto-generated method stub
		return typeContratRepository.findByLibelle(libelle);
	}

}
