package com.nectux.mizan.hyban.rh.formation.repository;

import com.nectux.mizan.hyban.rh.formation.entity.DemandeFormation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DemandeFormationRepository extends CrudRepository<DemandeFormation, Long> {
	
	public List<DemandeFormation> findAll();
	
	public DemandeFormation findByServiceIdAndObjet(Long serviceId, String objet);
	
	public int countByServiceIdAndObjet(Long serviceId, String objet);

	public DemandeFormation findByIdNotAndServiceIdAndObjet(Long id, Long serviceId, String objet);
	
	public Page<DemandeFormation> findAll(Pageable pageable);
	public Page<DemandeFormation> findAllByEtatDde(Pageable pageable, Integer etat);
	public List<DemandeFormation> findAllByEtatDde(Integer etat);

	public Page<DemandeFormation> findByObjetContainingOrServiceLibelleContainingOrCommentaireContaining(Pageable pageable, String objet, String serviceLibelle, String commentaire);

	Page<DemandeFormation> findByEtatDdeAndObjetContainingOrServiceLibelleContainingOrCommentaireContaining(Pageable pageable, Integer etat, String objet, String serviceLibelle, String commentaire);
}
