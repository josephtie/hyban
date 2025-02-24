package com.nectux.mizan.hyban.rh.carriere.service;

import java.util.List;

import com.nectux.mizan.hyban.rh.carriere.dto.PromotionDTO;
import com.nectux.mizan.hyban.rh.carriere.entity.Promotion;
import org.springframework.data.domain.Pageable;

public interface PromotionService {
	
	public PromotionDTO save(Long id, String libelle, String description);
	
	public PromotionDTO delete(Long id);
	
	public PromotionDTO findPromotion(Long id);
	
	public PromotionDTO findPromotions();
	
	public List<Promotion> getPromotions();
	
	public int count();
	
	public PromotionDTO loadPromotions(Pageable pageable);
	
	public PromotionDTO loadPromotions(Pageable pageable, String libelle, String description);

}
