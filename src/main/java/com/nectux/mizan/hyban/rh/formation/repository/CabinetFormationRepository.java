package com.nectux.mizan.hyban.rh.formation.repository;

import com.nectux.mizan.hyban.rh.formation.entity.CabinetFormation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CabinetFormationRepository extends CrudRepository<CabinetFormation, Long> {
	
	public List<CabinetFormation> findAll();
	
	public CabinetFormation findByNom(String nom);
	
	public int countByNom(String nom);

	public CabinetFormation findByIdNotAndNom(Long id, String nom);
	
	public Page<CabinetFormation> findAll(Pageable pageable);
	
	public Page<CabinetFormation> findByNomContainingOrAdresseContainingOrEmailContainingOrTelephoneContaining(Pageable pageable, String nom, String adresse, String email, String telephone);

}
