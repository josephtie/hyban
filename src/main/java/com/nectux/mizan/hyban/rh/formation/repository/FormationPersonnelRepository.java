package com.nectux.mizan.hyban.rh.formation.repository;

import com.nectux.mizan.hyban.rh.formation.entity.FormationPersonnel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FormationPersonnelRepository extends CrudRepository<FormationPersonnel, Long> {
	
	public List<FormationPersonnel> findAll();
	
	public FormationPersonnel findByPersonnelIdAndFormationId(Long personnelId, Long FormationId);
	
	public int countByPersonnelIdAndFormationId(Long personnelId, Long FormationId);

	public FormationPersonnel findByIdNotAndPersonnelIdAndFormationId(Long id, Long personnelId, Long FormationId);
	
	public Page<FormationPersonnel> findAll(Pageable pageable);
	public Page<FormationPersonnel> findAllByFormationId(Pageable pageable, Long FormationId);

	public Page<FormationPersonnel> findByFormationIntituleContainingOrPersonnelNomContainingOrPersonnelPrenom(Pageable pageable, String formationIntitule, String personnelNom, String personnelPrenom);
	public Page<FormationPersonnel> findByFormationIdAndFormationIntituleContainingOrPersonnelNomContainingOrPersonnelPrenom(Pageable pageable, Long FormationId, String formationIntitule, String personnelNom, String personnelPrenom);

}
