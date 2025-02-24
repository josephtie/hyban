package com.nectux.mizan.hyban.personnel.service;

import com.nectux.mizan.hyban.parametrages.entity.TypeService;
import com.nectux.mizan.hyban.personnel.dto.ServiceDTO;
import com.nectux.mizan.hyban.personnel.entity.Service;
import org.springframework.data.domain.Pageable;

public interface ServiceService {
	
	public Service save(Service service);
	
	public ServiceDTO save(Long id, String libelle, Long idDepartement, Long idDirection, Long idTypeService);
	
	public Boolean delete(Long id);
	
	public Service findService(Long id);
	
	public Service findByLibelle(String libelle);
	
	public java.util.List<Service> findByTypeService(TypeService typeService);
	
	public java.util.List<Service> findByServiceParent(Long idServiceParent);
		
	public java.util.List<Service> findServices();
	
	public int count();

	public ServiceDTO loadService(Pageable pageable);

	public ServiceDTO loadService(Pageable pageable, String search);

	public java.util.List<Service> findByTypeServiceId(Long idTypeService);

}
