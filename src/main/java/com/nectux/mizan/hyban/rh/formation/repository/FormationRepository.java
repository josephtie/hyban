package com.nectux.mizan.hyban.rh.formation.repository;

import com.nectux.mizan.hyban.rh.formation.entity.Formation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface FormationRepository extends CrudRepository<Formation, Long> {
	
	public List<Formation> findAll();
	
	public Formation findByThemeIdAndDemandeFormationIdAndIntituleAndDateDebutAndDateFin(Long themeId, Long demandeFormationId, String intitule, Date dateDebut, Date dateFin);
	
	public Formation findByIdNotAndThemeIdAndDemandeFormationIdAndIntituleAndDateDebutAndDateFin(Long id, Long themeId, Long demandeFormationId, String intitule, Date dateDebut, Date dateFin);
	
	public Page<Formation> findAll(Pageable pageable);
	
	public Page<Formation> findByIntituleContainingOrLieuContainingOrThemeIntituleContainingOrDemandeFormationObjetContaining(Pageable pageable, String intitule, String lieu, String themeIntitule, String demandeFormationObjet);

}
