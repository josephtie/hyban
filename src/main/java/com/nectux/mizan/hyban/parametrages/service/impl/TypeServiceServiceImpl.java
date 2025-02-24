package com.nectux.mizan.hyban.parametrages.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nectux.mizan.hyban.parametrages.entity.TypeService;
import com.nectux.mizan.hyban.parametrages.repository.TypeServiceRepository;
import com.nectux.mizan.hyban.parametrages.service.TypeServiceService;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service("typeServiceService")
public class TypeServiceServiceImpl implements TypeServiceService {

	@Autowired TypeServiceRepository typeServiceRepository;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public TypeService save(TypeService typeService) {
		// TODO Auto-generated method stub
		return typeServiceRepository.save(typeService);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean delete(Long id) {
		// TODO Auto-generated method stub
		TypeService typeService = typeServiceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
		if(typeService == null)
			return false;
		typeServiceRepository.delete(typeService);
		return true;
	}

	@Override
	public TypeService findTypeService(Long id) {
		// TODO Auto-generated method stub
		return typeServiceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
	}

	@Override
	public List<TypeService> findTypeServices() {
		// TODO Auto-generated method stub
		return typeServiceRepository.findAll();
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) typeServiceRepository.count();
	}

	@Override
	public TypeService findByLibelle(String libelle) {
		// TODO Auto-generated method stub
		return typeServiceRepository.findByLibelle(libelle);
	}

}
