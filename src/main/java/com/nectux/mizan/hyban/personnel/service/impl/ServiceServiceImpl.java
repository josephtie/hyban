package com.nectux.mizan.hyban.personnel.service.impl;

import java.util.List;

import com.nectux.mizan.hyban.personnel.entity.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.nectux.mizan.hyban.parametrages.entity.TypeService;
import com.nectux.mizan.hyban.parametrages.repository.TypeServiceRepository;
import com.nectux.mizan.hyban.personnel.dto.ServiceDTO;
import com.nectux.mizan.hyban.personnel.repository.ServiceRepository;
import com.nectux.mizan.hyban.personnel.service.ServiceService;

import javax.persistence.EntityNotFoundException;

@Transactional
@org.springframework.stereotype.Service("serviceService")
public class ServiceServiceImpl implements ServiceService {

	@Autowired ServiceRepository serviceRepository;
	@Autowired TypeServiceRepository typeServiceRepository;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Service save(Service service) {
		return serviceRepository.save(service);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ServiceDTO save(Long id, String libelle, Long idDepartement, Long idDirection, Long idTypeService) {
		ServiceDTO serviceDTO = new ServiceDTO();
		Service service = new Service();
		try{
			if(id != null)
				service = serviceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			service.setLibelle(libelle);
			service.setTypeService(typeServiceRepository.findById(idTypeService).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + idTypeService)));;
			
			switch(idTypeService.intValue()){
			case 1 : //Direction
				service.setServiceParent(null);
				break;
			case 2 : //Departement
				service.setServiceParent(serviceRepository.findById(idDirection).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + idDirection)));
				break;
			case 3 : //Service
				service.setServiceParent(serviceRepository.findById(idDepartement).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + idDepartement)));
				break;
			default :
				service.setServiceParent(null);
				break;
			}
			
			service = serviceRepository.save(service);
			serviceDTO.setRow(service);
			serviceDTO.setResult("succes");
		} catch(Exception ex){
			serviceDTO.setResult("erreur");
			if(libelle == null)
				serviceDTO.setResult("erreur_champ_obligatoire");
			ex.printStackTrace();
		}
		return serviceDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean delete(Long id) {
		// TODO Auto-generated method stub
		Service service = serviceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
		if(service == null)
			return false;
		serviceRepository.delete(service);
		return true;
	}

	@Override
	public Service findService(Long id) {
		// TODO Auto-generated method stub
		return serviceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
	}

	@Override
	public Service findByLibelle(String libelle) {
		// TODO Auto-generated method stub
		return serviceRepository.findByLibelle(libelle);
	}

	@Override
	public List<Service> findByTypeService(TypeService typeService) {
		return serviceRepository.findByTypeService(typeService);
	}

	@Override
	public List<Service> findServices() {
		return serviceRepository.findAll();
	}

	@Override
	public int count() {
		return (int) serviceRepository.count();
	}

	@Override
	public ServiceDTO loadService(Pageable pageable) {
		ServiceDTO serviceDTO = new ServiceDTO();
		Page<Service> page = serviceRepository.findAll(pageable);
		serviceDTO.setRows(page.getContent());
		serviceDTO.setTotal(page.getTotalElements());
		//logger.info(new StringBuilder().append(">>>>> CATEGORIES CHARGES AVEC SUCCES").toString());
		return serviceDTO;
	}

	@Override
	public ServiceDTO loadService(Pageable pageable, String search) {
		ServiceDTO serviceDTO = new ServiceDTO();
		Page<Service> page = serviceRepository.findByLibelleIgnoreCaseContaining(pageable, search);
		serviceDTO.setRows(page.getContent());
		serviceDTO.setTotal(page.getTotalElements());
		//logger.info(new StringBuilder().append(">>>>> CATEGORIES CHARGES AVEC SUCCES").toString());
		return serviceDTO;
	}

	@Override
	public List<Service> findByServiceParent(Long idServiceParent) {
		return serviceRepository.findByServiceParentId(idServiceParent);
	}

	@Override
	public List<Service> findByTypeServiceId(Long idTypeService) {
		return serviceRepository.findByTypeServiceId(idTypeService);
	}

}
