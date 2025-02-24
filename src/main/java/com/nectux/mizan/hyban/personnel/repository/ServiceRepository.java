package com.nectux.mizan.hyban.personnel.repository;

import java.util.List;

import com.nectux.mizan.hyban.parametrages.entity.TypeService;
import com.nectux.mizan.hyban.personnel.entity.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface ServiceRepository extends CrudRepository<Service, Long> {
	
	public List<Service> findAll();
	
	public List<Service> findByTypeService(TypeService typeService);
	
	public List<Service> findByTypeServiceId(Long idTypeService);

	public Service findByLibelle(String libelle);

	public List<Service> findByServiceParentId(Long idServiceParent);

	public Page<Service> findByLibelleLike(Pageable pageable, String search);

	public Page<Service> findAll(Pageable pageable);

	Page<Service> findByLibelleIgnoreCaseContaining(Pageable pageable, String search);

	//List<Service> findByServicesParentId(Long id);
}

