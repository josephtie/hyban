package com.nectux.mizan.hyban.rh.carriere.service;

import org.springframework.data.domain.Pageable;

import com.nectux.mizan.hyban.rh.carriere.dto.TypeSanctionDTO;
import com.nectux.mizan.hyban.rh.carriere.entity.TypeSanction;

public interface TypeSanctionService {
	
	public TypeSanctionDTO save(Long id, String libelle, String description);
	
	public TypeSanctionDTO delete(Long id);
	
	public TypeSanctionDTO findTypeSanction(Long id);
	
	public TypeSanctionDTO findTypeSanctions();
        
        public java.util.List<TypeSanction> findTypesSanction();
	
	public int count();
	
	public TypeSanctionDTO loadTypeSanctions(Pageable pageable);
	
	public TypeSanctionDTO loadTypeSanctions(Pageable pageable, String libelle, String description);

}
