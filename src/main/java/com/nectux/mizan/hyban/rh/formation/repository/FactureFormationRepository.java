package com.nectux.mizan.hyban.rh.formation.repository;

import com.nectux.mizan.hyban.rh.formation.entity.FactureFormation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FactureFormationRepository extends CrudRepository<FactureFormation, Long> {
	
	public List<FactureFormation> findAll();
	
	public List<FactureFormation> findByFormationId(Long formationId);
	
	public FactureFormation findByReferenceAndCabinetFormationIdAndFormationId(String reference, Long cabinetFormationId, Long formationId);
	
	public int countByReferenceAndCabinetFormationIdAndFormationId(String reference, Long cabinetFormationId, Long formationId);

	public FactureFormation findByIdNotAndReferenceAndCabinetFormationIdAndFormationId(Long id, String reference, Long cabinetFormationId, Long formationId);
	
	public Page<FactureFormation> findAll(Pageable pageable);
	
	public Page<FactureFormation> findByReferenceContainingOrCabinetFormationNomContainingOrFormationIntituleContaining(Pageable pageable, String reference, String nom, String intitule);

}
