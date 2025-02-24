package com.nectux.mizan.hyban.rh.carriere.service;

import com.nectux.mizan.hyban.rh.carriere.dto.PromotionPersonnelDTO;
import org.springframework.data.domain.Pageable;

public interface PromotionPersonnelService {
	
	public PromotionPersonnelDTO save(Long id, Long idPersonnel, Long idPromotion, String datePromotion, String commentaire);
	
	public PromotionPersonnelDTO save(Long id, Long idPersonnel, String libelle, String description, String datePromotion);
	
	public PromotionPersonnelDTO delete(Long id);
	
	public PromotionPersonnelDTO findPromotionPersonnel(Long id);
	
	public PromotionPersonnelDTO findPromotionPersonnels();
	
	public PromotionPersonnelDTO findPromotionPersonnelsByPromotion(Long idPoste);
	
	public PromotionPersonnelDTO findPromotionPersonnelsByPersonnel(Long idPersonnel);
	
	public int count();
	
	public PromotionPersonnelDTO loadPromotionPersonnels(Pageable pageable);
	
	public PromotionPersonnelDTO loadPromotionPersonnels(Pageable pageable, String nom, String prenom, String libelle);

}
