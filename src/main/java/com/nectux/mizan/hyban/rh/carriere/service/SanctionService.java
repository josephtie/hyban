package com.nectux.mizan.hyban.rh.carriere.service;

import java.util.List;

import com.nectux.mizan.hyban.rh.carriere.entity.Sanction;
import org.springframework.data.domain.Pageable;

import com.nectux.mizan.hyban.rh.carriere.dto.SanctionDTO;

public interface SanctionService {
	
	public SanctionDTO save(Long id, Long idTypeSanction, String faute, String commentaire);
	
	public SanctionDTO delete(Long id);
	
	public SanctionDTO findSanction(Long id);
	
	public SanctionDTO findSanctions();
	
	public List<Sanction> getSanctions();
	
	public int count();
	
	public SanctionDTO loadSanctions(Pageable pageable);
	
	public SanctionDTO loadSanctions(Pageable pageable, String libelle, String description);

}
