package com.nectux.mizan.hyban.rh.carriere.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.nectux.mizan.hyban.rh.carriere.entity.PromotionPersonnel;

public interface PromotionPersonnelRepository extends CrudRepository<PromotionPersonnel, Long> {
	
	public List<PromotionPersonnel> findAll();
	
	public List<PromotionPersonnel> findByPromotionId(Long idPromotion);
	
	public List<PromotionPersonnel> findByPersonnelId(Long idPersonnel);
	
	public PromotionPersonnel findByPersonnelIdAndPromotionIdAndDatePromotion(Long idPersonnel, Long idPromotion, Date datePromotion);
	
	public int countByPersonnelIdAndPromotionIdAndDatePromotion(Long idPersonnel, Long idPromotion, Date datePromotion);
	
	public Page<PromotionPersonnel> findAll(Pageable pageable);
	
	public Page<PromotionPersonnel> findByPersonnelNomContainingOrPersonnelPrenomContainingOrPromotionLibelle(Pageable pageable, String nom, String prenom, String libelle);

}
